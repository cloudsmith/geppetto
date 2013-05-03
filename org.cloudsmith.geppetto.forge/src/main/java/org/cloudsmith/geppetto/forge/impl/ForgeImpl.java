/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.forge.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.cloudsmith.geppetto.common.os.FileUtils;
import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.diagnostic.ExceptionDiagnostic;
import org.cloudsmith.geppetto.diagnostic.FileDiagnostic;
import org.cloudsmith.geppetto.forge.AlreadyPublishedException;
import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.ERB;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.MetadataExtractor;
import org.cloudsmith.geppetto.forge.client.ForgeException;
import org.cloudsmith.geppetto.forge.util.Checksums;
import org.cloudsmith.geppetto.forge.util.ModuleUtils;
import org.cloudsmith.geppetto.forge.util.TarUtils;
import org.cloudsmith.geppetto.forge.util.TarUtils.FileCatcher;
import org.cloudsmith.geppetto.forge.v1.model.ModuleInfo;
import org.cloudsmith.geppetto.forge.v2.MetadataRepository;
import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.Module;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.forge.v2.model.Release;
import org.cloudsmith.geppetto.forge.v2.service.ModuleService;
import org.cloudsmith.geppetto.forge.v2.service.ReleaseService;
import org.cloudsmith.geppetto.semver.Version;
import org.cloudsmith.geppetto.semver.VersionRange;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
class ForgeImpl implements Forge {
	@Inject
	private ERB erb;

	@Inject(optional = true)
	private Cache cache;

	@Inject
	private Gson gson;

	@Inject(optional = true)
	private ModuleService moduleService;

	@Inject(optional = true)
	private org.cloudsmith.geppetto.forge.v1.service.ModuleService moduleServiceV1;

	@Inject(optional = true)
	private ReleaseService releaseService;

	@Inject(optional = true)
	private MetadataRepository metadataRepo;

	@Inject
	@Named(Forge.MODULE_FILE_FILTER)
	private FileFilter moduleFileFilter;

	@Inject
	Set<MetadataExtractor> metadataExtractors;

	private static Comparator<MetadataExtractor> extractorComparator = new Comparator<MetadataExtractor>() {
		@Override
		public int compare(MetadataExtractor a, MetadataExtractor b) {
			return a.getCardinal() - b.getCardinal();
		}
	};

	@Override
	public File build(File moduleSource, File destination, FileFilter fileFilter, Metadata[] resultingMetadata,
			Diagnostic result) throws IOException {
		if(fileFilter == null)
			fileFilter = moduleFileFilter;

		File[] extractedFrom = new File[1];

		Metadata md = createFromModuleDirectory(moduleSource, true, fileFilter, extractedFrom, result);
		if(result.getSeverity() >= Diagnostic.ERROR)
			return null;

		if(resultingMetadata != null)
			resultingMetadata[0] = md;

		if(md == null)
			// Metadata could not be read. Errors are in result
			return null;

		ModuleName fullName = md.getName();
		if(fullName == null || fullName.getOwner() == null || fullName.getName() == null) {
			result.addChild(new FileDiagnostic(
				Diagnostic.ERROR, Forge.PACKAGE, "A full name (user-module) must be specified in the Modulefile",
				extractedFrom[0]));
			return null;
		}

		Version ver = md.getVersion();
		if(ver == null) {
			result.addChild(new FileDiagnostic(
				Diagnostic.ERROR, Forge.PACKAGE, "A version must be specified in the Modulefile", extractedFrom[0]));
			return null;
		}

		for(File tst = destination; tst != null; tst = tst.getParentFile()) {
			if(fileFilter.accept(tst))
				// Destination folder might reside inside of the module when it
				// has been excluded.
				break;

			if(tst.equals(moduleSource))
				throw new IllegalArgumentException("Destination cannot reside within the module itself");
		}

		/**
		 * Copy the module to the location where it's being built. Ensure that it's
		 * an empty location prior to that.
		 */
		StringBuilder bld = new StringBuilder();
		ModuleUtils.buildFileName(fullName, ver, bld);
		String fullNameWithVersion = bld.toString();
		bld.append(".tar.gz");
		String zipArchiveName = bld.toString();

		File destModuleDir = new File(destination, fullNameWithVersion);
		if(!destination.mkdirs())
			FileUtils.rmR(destModuleDir);

		FileUtils.cpR(moduleSource, destModuleDir, fileFilter, false, true);

		File metadataJSON = new File(destModuleDir, "metadata.json");
		if(!metadataJSON.exists())
			saveJSONMetadata(md, metadataJSON);

		final File moduleArchive = new File(destination, zipArchiveName);
		OutputStream out = new GZIPOutputStream(new FileOutputStream(moduleArchive));

		// Pack closes its output
		TarUtils.pack(destModuleDir, out, null, true, null);

		return moduleArchive;
	}

	@Override
	public List<File> changes(File path, FileFilter fileFilter) throws IOException {
		if(fileFilter == null)
			fileFilter = moduleFileFilter;
		Metadata md = loadJSONMetadata(new File(path, "metadata.json"));
		List<File> result = new ArrayList<File>();
		Checksums.appendChangedFiles(md.getChecksums(), path, result, fileFilter);
		return result;
	}

	@Override
	public Metadata createFromModuleDirectory(File moduleDirectory, boolean includeTypesAndChecksums,
			FileFilter filter, File[] extractedFrom, Diagnostic result) throws IOException {

		if(filter == null)
			filter = moduleFileFilter;

		if(extractedFrom == null)
			extractedFrom = new File[1];

		Metadata md = null;
		for(MetadataExtractor extractor : getMetadataExtractors())
			if(extractor.canExtractFrom(moduleDirectory, filter)) {
				md = extractor.parseMetadata(moduleDirectory, includeTypesAndChecksums, filter, extractedFrom, result);
				break;
			}

		if(result == null)
			return md;

		if(md == null) {
			result.addChild(new Diagnostic(Diagnostic.ERROR, Forge.FORGE, "No Module Metadata found in directory " +
					moduleDirectory.getAbsolutePath()));
			return null;
		}

		ModuleName fullName = md.getName();
		if(fullName == null || fullName.getOwner() == null || fullName.getName() == null) {
			result.addChild(new FileDiagnostic(
				Diagnostic.ERROR, Forge.FORGE, "A full name (user-module) must be specified in the Modulefile",
				extractedFrom[0]));
		}

		Version ver = md.getVersion();
		if(ver == null) {
			result.addChild(new FileDiagnostic(
				Diagnostic.ERROR, Forge.FORGE, "A version must be specified in the Modulefile", extractedFrom[0]));
		}
		return md;
	}

	@Override
	public Collection<File> downloadDependencies(Iterable<Metadata> metadatas, File importedModulesDir,
			Diagnostic result) throws IOException {
		Set<Dependency> unresolvedCollector = new HashSet<Dependency>();
		Set<Release> releasesToDownload = resolveDependencies(metadatas, unresolvedCollector);
		for(Dependency unresolved : unresolvedCollector)
			result.addChild(new Diagnostic(Diagnostic.WARNING, Forge.FORGE, String.format(
				"Unable to resolve dependency: %s:%s", unresolved.getName(),
				unresolved.getVersionRequirement().toString())));

		if(!releasesToDownload.isEmpty()) {
			importedModulesDir.mkdirs();
			List<File> importedModuleLocations = new ArrayList<File>();

			for(Release release : releasesToDownload) {
				result.addChild(new Diagnostic(Diagnostic.INFO, Forge.FORGE, "Installing dependent module " +
						release.getFullName() + ':' + release.getVersion()));
				StringBuilder bld = new StringBuilder();
				ModuleUtils.buildFileName(release.getFullName(), release.getVersion(), bld);
				File moduleDir = new File(importedModulesDir, bld.toString());
				install(release, moduleDir, true, false);
				importedModuleLocations.add(moduleDir);
			}
			return importedModuleLocations;
		}

		if(unresolvedCollector.isEmpty())
			result.addChild(new Diagnostic(Diagnostic.INFO, Forge.FORGE, "No additional dependencies were detected"));
		return Collections.emptyList();
	}

	@Override
	public Collection<File> findModuleRoots(File modulesRoot, FileFilter fileFilter) {
		if(fileFilter == null)
			fileFilter = moduleFileFilter;
		return ModuleUtils.findModuleRoots(modulesRoot, fileFilter, getMetadataExtractors());
	}

	@Override
	public void generate(File destination, Metadata metadata) throws IOException {
		if(!(destination.mkdirs() || destination.exists()))
			throw new IOException(destination + " could not be created");

		InputStream input = Forge.class.getResourceAsStream("/templates/generator.zip");
		if(input == null)
			throw new FileNotFoundException("Unable to find zipped template for generate");

		ZipInputStream template = new ZipInputStream(input);
		try {
			installTemplate(metadata, destination, template);
		}
		finally {
			template.close();
		}
	}

	public List<MetadataExtractor> getMetadataExtractors() {
		ArrayList<MetadataExtractor> extractors = new ArrayList<MetadataExtractor>(metadataExtractors);
		Collections.sort(extractors, extractorComparator);
		return extractors;
	}

	@Override
	public Metadata getMetadataFromPackage(File builtModule) throws IOException {
		InputStream input = new FileInputStream(builtModule);
		try {
			final Metadata[] mdReceiver = new Metadata[1];
			TarUtils.unpack(new GZIPInputStream(input), null, true, new FileCatcher() {
				@Override
				public boolean accept(String fileName) {
					return "metadata.json".equals(fileName);
				}

				@Override
				public boolean catchData(String fileName, InputStream fileData) {
					InputStreamReader reader = new InputStreamReader(fileData);
					mdReceiver[0] = gson.fromJson(reader, Metadata.class);
					return true; // We're done. Skip further processing
				}
			});
			return mdReceiver[0];
		}
		finally {
			input.close();
		}
	}

	@Override
	public boolean hasModuleMetadata(File moduleDirectory, FileFilter filter) {
		if(filter == null)
			filter = moduleFileFilter;
		for(MetadataExtractor extractor : metadataExtractors)
			if(extractor.canExtractFrom(moduleDirectory, filter))
				return true;
		return false;
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
		return loadJSONMetadata(new File(destination, "metadata.json"));
	}

	@Override
	public Metadata install(Release release, File destination, boolean destinationIncludesTopFolder, boolean force)
			throws IOException {
		return install(
			release.getFullName(), VersionRange.exact(release.getVersion()), destination, destinationIncludesTopFolder,
			force);
	}

	private void installTemplate(Metadata metadata, File destinationBase, ZipInputStream template) throws IOException {

		ZipEntry zipEntry;
		while((zipEntry = template.getNextEntry()) != null) {
			String name = zipEntry.getName();
			if(zipEntry.isDirectory()) {
				File destination = new File(destinationBase, name);
				if(!destination.mkdirs())
					throw new IOException(destination + " could not be created");
				continue;
			}

			if(name.endsWith(".erb")) {
				File destination = new File(destinationBase, name.substring(0, name.length() - 4));
				BufferedReader reader = new BufferedReader(new InputStreamReader(template));
				BufferedWriter writer = new BufferedWriter(new FileWriter(destination));
				try {
					erb.generate(metadata, reader, writer);
				}
				finally {
					StreamUtil.close(writer);
				}
			}
			else {
				File destination = new File(destinationBase, name);
				FileUtils.cp(template, destination.getParentFile(), destination.getName());
			}
		}
	}

	@Override
	public boolean isMetadataFile(String source) {
		if(source != null) {
			for(MetadataExtractor extractor : metadataExtractors)
				if(source.equals(extractor.getPrimarySource()))
					return true;
		}
		return false;
	}

	public Metadata loadJSONMetadata(File jsonFile) throws IOException {
		Reader reader = new BufferedReader(new FileReader(jsonFile));
		try {
			Metadata md = gson.fromJson(reader, Metadata.class);
			return md;
		}
		finally {
			StreamUtil.close(reader);
		}
	}

	@Override
	public Metadata loadModulefile(File moduleFile, Diagnostic diagnostic) throws IOException {
		Metadata metadata = new Metadata();
		ModuleUtils.parseModulefile(moduleFile, metadata, diagnostic);
		return metadata;
	}

	@Override
	public void publish(File moduleArchive, boolean dryRun, Diagnostic result) throws IOException {
		if(releaseService == null)
			throw new UnsupportedOperationException(
				"Unable to publish since no release service is configured. Was a serviceURL provided in the preferences?");

		Metadata metadata = getMetadataFromPackage(moduleArchive);
		if(metadata == null)
			throw new ForgeException("No \"metadata.json\" found in archive: " + moduleArchive.getAbsolutePath());

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
			result.addChild(new Diagnostic(Diagnostic.INFO, Forge.PUBLISHER, "Module file " + moduleArchive.getName() +
					" would have been uploaded (but wasn't since this is a dry run)"));
			return;
		}

		InputStream gzInput = new FileInputStream(moduleArchive);
		try {
			ModuleName name = metadata.getName();
			releaseService.create(
				name.getOwner(), name.getName(), "Published using GitHub trigger", gzInput, moduleArchive.length());
			result.addChild(new Diagnostic(Diagnostic.INFO, Forge.PUBLISHER, "Module file " + moduleArchive.getName() +
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
				result.addChild(new Diagnostic(Diagnostic.WARNING, Forge.PUBLISHER, e.getMessage()));
				continue;
			}
			catch(ForgeException e) {
				result.addChild(new Diagnostic(Diagnostic.ERROR, Forge.PUBLISHER, e.getMessage()));
			}
			catch(Exception e) {
				result.addChild(new ExceptionDiagnostic(Diagnostic.ERROR, Forge.PUBLISHER, "Unable to publish module " +
						builtModule.getName(), e));
			}
			return;
		}

		if(noPublishingMade) {
			result.addChild(new Diagnostic(
				Diagnostic.INFO, Forge.PUBLISHER, "All modules have already been published at their current version"));
		}
	}

	@Override
	public Set<Release> resolveDependencies(Iterable<Metadata> metadatas, Set<Dependency> unresolvedCollector)
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
		Set<Release> releasesToDownload = new HashSet<Release>();
		if(!deps.isEmpty()) {
			if(metadataRepo == null)
				throw new UnsupportedOperationException(
					"Unable to resolve dependencies since no forge service is configured. Was a serviceURL provided in the preferences?");

			for(Dependency dep : deps)
				releasesToDownload.addAll(metadataRepo.deepResolve(dep, unresolvedCollector));
		}
		return releasesToDownload;
	}

	public void saveJSONMetadata(Metadata md, File jsonFile) throws IOException {
		Writer writer = new BufferedWriter(new FileWriter(jsonFile));
		try {
			gson.toJson(md, writer);
		}
		finally {
			StreamUtil.close(writer);
		}
	}

	@Override
	public void saveModulefile(Metadata md, File moduleFile) throws IOException {
		ModuleUtils.saveAsModulefile(md, moduleFile);
	}

	@Override
	public List<Module> search(String term) throws IOException {
		return moduleService.search(term, null);
	}

	@Override
	public List<ModuleInfo> search_v1(String term) throws IOException {
		return moduleServiceV1.search(term);
	}

	@Override
	public List<ModuleInfo> searchSince_v1(Date timestamp) throws IOException {
		return moduleServiceV1.search(timestamp);
	}
}
