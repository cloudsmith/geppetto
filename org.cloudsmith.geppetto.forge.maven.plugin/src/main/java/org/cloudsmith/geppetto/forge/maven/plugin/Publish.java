/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.forge.maven.plugin;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.IncompleteException;
import org.cloudsmith.geppetto.forge.util.TarUtils;
import org.cloudsmith.geppetto.forge.v2.MetadataRepository;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.service.ReleaseService;
import org.cloudsmith.geppetto.pp.dsl.PPStandaloneSetup;
import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.cloudsmith.geppetto.ruby.jrubyparser.JRubyServices;
import org.cloudsmith.geppetto.validation.DiagnosticType;

/**
 * Goal which performs basic validation.
 */
@Mojo(name = "publish", requiresProject = false)
public class Publish extends AbstractForgeMojo {
	static final String ALREADY_PUBLISHED = "ALREADY_PUBLISHED";

	/**
	 * Set to <tt>true</tt> to enable validation using puppet-lint
	 */
	@Parameter(property = "forge.publish.dryrun")
	private boolean dryRun;

	private File buildForge(ForgeService forgeService, File moduleSource, File destination, String[] namesReceiver)
			throws IOException, IncompleteException {

		FileFilter filter = getFileFilter();
		File metadataJSON = new File(moduleSource, "metadata.json");
		org.cloudsmith.geppetto.forge.Metadata md;
		try {
			md = forgeService.loadJSONMetadata(metadataJSON);
		}
		catch(FileNotFoundException e) {
			md = forgeService.loadModule(moduleSource, filter);
			String fullName = md.getFullName();
			if(fullName == null)
				throw new IncompleteException("A full name (user-module) must be specified in the Modulefile");

			String ver = md.getVersion();
			if(ver == null)
				throw new IncompleteException("version must be specified in the Modulefile");

			md.saveJSONMetadata(metadataJSON);
		}

		namesReceiver[0] = md.getUser();
		namesReceiver[1] = md.getName();
		String fullNameWithVersion = md.getFullName() + '-' + md.getVersion();
		File moduleArchive = new File(destination, fullNameWithVersion + ".tar.gz");
		OutputStream out = new GZIPOutputStream(new FileOutputStream(moduleArchive));
		// Pack closes its output
		TarUtils.pack(moduleSource, out, filter, false, fullNameWithVersion);
		return moduleArchive;
	}

	@Override
	protected String getActionName() {
		return "Publishing";
	}

	@Override
	protected void invoke(Diagnostic result) throws Exception {
		ReleaseService releaseService = getForge().createReleaseService();

		List<File> moduleRoots = findModuleRoots();
		if(moduleRoots.isEmpty()) {
			result.addChild(new Diagnostic(Diagnostic.ERROR, DiagnosticType.PUBLISHER, "No modules found in repository"));
			return;
		}

		RubyHelper.setRubyServicesFactory(JRubyServices.FACTORY);
		PPStandaloneSetup.doSetup();
		MetadataRepository metadataRepo = getForge().createMetadataRepository();

		List<String> alreadyPublishedPaths = new ArrayList<String>();
		List<Metadata> metadatas = new ArrayList<Metadata>();
		for(File moduleRoot : moduleRoots) {
			Metadata metadata = getModuleMetadata(moduleRoot, result);
			try {
				if(metadataRepo.resolve(metadata.getName(), metadata.getVersion()) != null) {
					Diagnostic diag = new Diagnostic(Diagnostic.WARNING, DiagnosticType.PUBLISHER, "Module " +
							metadata.getName() + ':' + metadata.getVersion() + " has already been published");
					diag.setIssue(ALREADY_PUBLISHED);
					result.addChild(diag);
					alreadyPublishedPaths.add(moduleRoot.getAbsolutePath());
				}
			}
			catch(HttpResponseException e) {
				if(e.getStatusCode() != HttpStatus.SC_NOT_FOUND) {
					if(!dryRun)
						throw e;
					getLog().warn(e);
				}
				// This is expected. It just means that the module hasn't
				// been published yet.
			}
			catch(Exception e) {
				if(!dryRun)
					throw e;
				getLog().warn(e);
			}
			metadatas.add(metadata);
		}
		if(result.getSeverity() == Diagnostic.ERROR)
			return;

		int idx = moduleRoots.size();
		while(--idx >= 0)
			if(alreadyPublishedPaths.contains(moduleRoots.get(idx).getAbsolutePath()))
				moduleRoots.remove(idx);

		if(moduleRoots.isEmpty()) {
			result.addChild(new Diagnostic(
				Diagnostic.INFO, DiagnosticType.PUBLISHER,
				"All modules have already been published at their current version"));
			return;
		}

		ForgeService forgeService = ForgeFactory.eINSTANCE.createForgeService();
		String[] namesReceiver = new String[2];
		File builtModules = new File(getBuildDir(), "builtModules");
		if(!(builtModules.mkdirs() || builtModules.isDirectory())) {
			result.addChild(new Diagnostic(Diagnostic.ERROR, DiagnosticType.PUBLISHER, "Unable to create directory" +
					builtModules.getPath()));
			return;
		}

		for(File moduleRoot : moduleRoots) {
			File moduleArchive;
			try {
				moduleArchive = buildForge(forgeService, moduleRoot, builtModules, namesReceiver);
			}
			catch(IncompleteException e) {
				result.addChild(new Diagnostic(Diagnostic.ERROR, DiagnosticType.PUBLISHER, e.getMessage()));
				continue;
			}
			if(dryRun) {
				result.addChild(new Diagnostic(Diagnostic.INFO, DiagnosticType.PUBLISHER, "Module file " +
						moduleArchive.getName() + " would have been uploaded (but wasn't since this is a dry run)"));
			}
			else {
				InputStream gzInput = new FileInputStream(moduleArchive);
				try {
					releaseService.create(
						namesReceiver[0], namesReceiver[1], "Published using GitHub trigger", gzInput,
						moduleArchive.length());
					result.addChild(new Diagnostic(Diagnostic.INFO, DiagnosticType.PUBLISHER, "Module file " +
							moduleArchive.getName() + " has been uploaded"));
				}
				catch(HttpResponseException e) {
					result.addChild(new Diagnostic(
						Diagnostic.ERROR, DiagnosticType.PUBLISHER, "Unable to publish module " +
								moduleArchive.getName() + ":" + e.getMessage()));
					continue;
				}
				finally {
					StreamUtil.close(gzInput);
				}
			}
		}
	}
}
