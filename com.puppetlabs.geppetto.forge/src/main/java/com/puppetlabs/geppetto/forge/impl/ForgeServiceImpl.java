/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.forge.impl;

import static com.puppetlabs.geppetto.diagnostic.Diagnostic.ERROR;
import static com.puppetlabs.geppetto.diagnostic.Diagnostic.INFO;
import static com.puppetlabs.geppetto.diagnostic.Diagnostic.WARNING;
import static com.puppetlabs.geppetto.forge.Forge.FORGE;
import static com.puppetlabs.geppetto.forge.Forge.METADATA_JSON_NAME;
import static com.puppetlabs.geppetto.forge.Forge.MODULE_FILE_FILTER;
import static com.puppetlabs.geppetto.forge.Forge.PUBLISHER;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.puppetlabs.geppetto.common.os.FileUtils;
import com.puppetlabs.geppetto.common.os.StreamUtil;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.ExceptionDiagnostic;
import com.puppetlabs.geppetto.forge.AlreadyPublishedException;
import com.puppetlabs.geppetto.forge.Cache;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.forge.ForgeService;
import com.puppetlabs.geppetto.forge.client.ForgeException;
import com.puppetlabs.geppetto.forge.model.Dependency;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.MetadataRepository;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.util.ModuleUtils;
import com.puppetlabs.geppetto.forge.util.TarUtils;
import com.puppetlabs.geppetto.forge.v2.model.Module;
import com.puppetlabs.geppetto.forge.v2.model.Release;
import com.puppetlabs.geppetto.forge.v2.service.ModuleService;
import com.puppetlabs.geppetto.forge.v2.service.ReleaseService;
import com.puppetlabs.geppetto.semver.VersionRange;

class ForgeServiceImpl implements ForgeService {
	@Inject
	private Cache cache;

	@Inject
	private ModuleService moduleService;

	@Inject
	private ReleaseService releaseService;

	@Inject
	private MetadataRepository metadataRepo;

	@Inject
	@Named(MODULE_FILE_FILTER)
	private FileFilter moduleFileFilter;

	@Inject
	private Forge forgeUtil;

	@Override
	public Collection<File> downloadDependencies(Iterable<Metadata> metadatas, File importedModulesDir,
			Diagnostic result) throws IOException {
		Set<Dependency> unresolvedCollector = new HashSet<Dependency>();
		Set<Metadata> releasesToDownload = resolveDependencies(metadatas, unresolvedCollector);
		for(Dependency unresolved : unresolvedCollector)
			result.addChild(new Diagnostic(WARNING, FORGE, String.format(
				"Unable to resolve dependency: %s:%s", unresolved.getName(),
				unresolved.getVersionRequirement().toString())));

		if(!releasesToDownload.isEmpty()) {
			importedModulesDir.mkdirs();
			List<File> importedModuleLocations = new ArrayList<File>();

			StringBuilder bld = new StringBuilder("Installing dependent module ");
			int pfxLen = bld.length();
			for(Metadata release : releasesToDownload) {
				bld.setLength(pfxLen);
				release.getName().toString(bld);
				bld.append(':');
				release.getVersion().toString(bld);
				result.addChild(new Diagnostic(INFO, FORGE, bld.toString()));

				bld.setLength(0);
				ModuleUtils.buildFileName(release.getName(), release.getVersion(), bld);
				File moduleDir = new File(importedModulesDir, bld.toString());
				install(release, moduleDir, true, false);
				importedModuleLocations.add(moduleDir);
			}
			return importedModuleLocations;
		}

		if(unresolvedCollector.isEmpty())
			result.addChild(new Diagnostic(INFO, FORGE, "No additional dependencies were detected"));
		return Collections.emptyList();
	}

	@Override
	public Metadata install(Metadata release, File destination, boolean destinationIncludesTopFolder, boolean force)
			throws IOException {
		return install(
			release.getName(), VersionRange.exact(release.getVersion()), destination, destinationIncludesTopFolder,
			force);
	}

	@Override
	public Metadata install(ModuleName moduleName, VersionRange range, File destination,
			boolean destinationIncludesTopFolder, boolean force) throws IOException {
		if(moduleService == null || cache == null)
			throw new UnsupportedOperationException(
				"Unable to install since no module service is configured. Was a serviceURL provided in the preferences?");

		List<Release> releases = moduleService.getReleases(moduleName.getOwner(), moduleName.getName(), null);
		if(releases.isEmpty())
			throw new FileNotFoundException("No releases found for module '" + moduleName + '\'');

		Release best = null;
		for(Release release : releases)
			if((best == null || release.getVersion().compareTo(best.getVersion()) > 0) &&
					(range == null || range.isIncluded(release.getVersion())))
				best = release;

		if(best == null)
			throw new FileNotFoundException("No releases matching '" + range + "' found for module '" + moduleName +
					'\'');

		if(!destinationIncludesTopFolder)
			// Use module name as the default
			destination = new File(destination, moduleName.getName());

		if(destination.exists()) {
			if(!force)
				throw new IOException("Destination folder is not empty: " + destination.getAbsolutePath());

			// Don't remove .project, .settings, .git, .svn, etc. if they are present.
			FileUtils.rmR(destination, FileUtils.DEFAULT_EXCLUDES);
		}

		File moduleFile = cache.retrieve(best.getFullName(), best.getVersion());

		// Unpack closes its input.
		TarUtils.unpack(new GZIPInputStream(new FileInputStream(moduleFile)), destination, true, null);
		return forgeUtil.loadJSONMetadata(new File(destination, METADATA_JSON_NAME));
	}

	@Override
	public void publish(File moduleArchive, boolean dryRun, Diagnostic result) throws IOException {
		if(releaseService == null)
			throw new UnsupportedOperationException(
				"Unable to publish since no release service is configured. Was a serviceURL provided in the preferences?");

		Metadata metadata = forgeUtil.getMetadataFromPackage(moduleArchive);
		if(metadata == null)
			throw new ForgeException("No \"metadata.json\" found in archive: " + moduleArchive.getAbsolutePath());

		if(metadata.getName() == null)
			throw new ForgeException("The \"metadata.json\" found in archive: " + moduleArchive.getAbsolutePath() +
					" has no name");

		if(metadata.getVersion() == null)
			throw new ForgeException("The \"metadata.json\" found in archive: " + moduleArchive.getAbsolutePath() +
					" has no version");

		try {
			if(metadataRepo.resolve(metadata.getName(), metadata.getVersion()) != null)
				throw new AlreadyPublishedException("Module " + metadata.getName() + ':' + metadata.getVersion() +
						" has already been published");
		}
		catch(HttpResponseException e) {
			// A SC_NOT_FOUND can be expected and is OK.
			if(e.getStatusCode() != HttpStatus.SC_NOT_FOUND)
				throw new ForgeException("Unable to check module existence on the forge: " + e.getMessage());
		}

		if(dryRun) {
			result.addChild(new Diagnostic(INFO, PUBLISHER, "Module file " + moduleArchive.getName() +
					" would have been uploaded (but wasn't since this is a dry run)"));
			return;
		}

		InputStream gzInput = new FileInputStream(moduleArchive);
		try {
			ModuleName name = metadata.getName();
			releaseService.create(
				name.getOwner(), name.getName(), "Published using GitHub trigger", gzInput, moduleArchive.length());
			result.addChild(new Diagnostic(INFO, PUBLISHER, "Module file " + moduleArchive.getName() +
					" has been uploaded"));
		}
		finally {
			StreamUtil.close(gzInput);
		}
	}

	public void publishAll(File[] builtModules, boolean dryRun, Diagnostic result) {
		boolean noPublishingMade = true;
		for(File builtModule : builtModules) {
			String name = builtModule.getName();
			if(!(name.endsWith(".tar.gz") || name.endsWith(".tgz")))
				continue;

			try {
				publish(builtModule, dryRun, result);
				noPublishingMade = false;
				continue;
			}
			catch(AlreadyPublishedException e) {
				result.addChild(new Diagnostic(WARNING, PUBLISHER, e.getMessage()));
				continue;
			}
			catch(ForgeException e) {
				result.addChild(new Diagnostic(ERROR, PUBLISHER, e.getMessage()));
			}
			catch(Exception e) {
				result.addChild(new ExceptionDiagnostic(ERROR, PUBLISHER, "Unable to publish module " +
						builtModule.getName(), e));
			}
			return;
		}

		if(noPublishingMade) {
			result.addChild(new Diagnostic(
				INFO, PUBLISHER, "All modules have already been published at their current version"));
		}
	}

	@Override
	public Set<Metadata> resolveDependencies(Iterable<Metadata> metadatas, Set<Dependency> unresolvedCollector)
			throws IOException {
		// Resolve missing dependencies
		Set<Dependency> deps = new HashSet<Dependency>();
		for(Metadata metadata : metadatas)
			deps.addAll(metadata.getDependencies());

		// Remove the dependencies that appoints modules that we have in the
		// workspace
		Iterator<Dependency> depsItor = deps.iterator();
		nextDep: while(depsItor.hasNext()) {
			Dependency dep = depsItor.next();
			for(Metadata metadata : metadatas)
				if(dep.matches(metadata)) {
					depsItor.remove();
					continue nextDep;
				}
		}

		// Resolve remaining dependencies
		Set<Metadata> releasesToDownload = new HashSet<Metadata>();
		if(!deps.isEmpty()) {
			if(metadataRepo == null)
				throw new UnsupportedOperationException(
					"Unable to resolve dependencies since no forge service is configured. Was a serviceURL provided in the preferences?");

			for(Dependency dep : deps)
				releasesToDownload.addAll(metadataRepo.deepResolve(dep, unresolvedCollector));
		}
		return releasesToDownload;
	}

	@Override
	public List<Module> search(String term) throws IOException {
		return moduleService.search(term, null);
	}
}
