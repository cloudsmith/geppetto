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
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.forge.util.TarUtils;
import org.cloudsmith.geppetto.forge.v2.MetadataRepository;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.QName;
import org.cloudsmith.geppetto.forge.v2.service.ReleaseService;
import org.cloudsmith.geppetto.validation.DiagnosticType;

/**
 * Goal which performs basic validation.
 */
@Mojo(name = "publish", requiresProject = false, defaultPhase = LifecyclePhase.DEPLOY)
public class Publish extends AbstractForgeMojo {
	static final String ALREADY_PUBLISHED = "ALREADY_PUBLISHED";

	/**
	 * Set to <tt>true</tt> to enable validation using puppet-lint
	 */
	@Parameter(property = "forge.publish.dryrun")
	private boolean dryRun;

	@Override
	protected String getActionName() {
		return "Publishing";
	}

	/**
	 * @param builtModule
	 * @param result
	 * @return
	 */
	private Metadata getModuleMetadataFromPackage(File builtModule, Diagnostic result) {
		File mdDir = new File(getBuildDir(), "extractedMetadata");
		mdDir.mkdirs();
		try {
			InputStream input = new FileInputStream(builtModule);
			try {
				TarUtils.unpack(new GZIPInputStream(input), mdDir, false, new FileFilter() {
					@Override
					public boolean accept(File file) {
						return file.getName().equals("metadata.json");
					}
				});
			}
			finally {
				input.close();
			}
			String name = builtModule.getName();
			name = name.substring(0, name.length() - 7); // Strip off .tar.gz
			return getModuleMetadata(new File(mdDir, name), result);
		}
		catch(IOException e) {
			return null;
		}
	}

	@Override
	protected void invoke(Diagnostic result) throws Exception {
		ReleaseService releaseService = getForge().createReleaseService();

		File builtModulesDir = new File(getBuildDir(), "builtModules");
		File[] builtModules = builtModulesDir.listFiles();
		if(builtModules == null || builtModules.length == 0) {
			result.addChild(new Diagnostic(
				Diagnostic.ERROR, DiagnosticType.PUBLISHER, "Unable find any packaged modules in " +
						builtModulesDir.getAbsolutePath()));
			return;
		}

		Map<QName, File> notPublished = new HashMap<QName, File>();
		MetadataRepository metadataRepo = getForge().createMetadataRepository();
		for(File builtModule : builtModules) {
			Metadata metadata = getModuleMetadataFromPackage(builtModule, result);
			boolean alreadyPublished = false;
			try {
				alreadyPublished = metadataRepo.resolve(metadata.getName(), metadata.getVersion()) != null;
			}
			catch(HttpResponseException e) {
				// A SC_NOT_FOUND can be expected and is OK.
				if(e.getStatusCode() != HttpStatus.SC_NOT_FOUND) {
					if(!dryRun)
						throw e;
					getLog().warn("Unable to check module existence on the forge: " + e.getMessage());
				}
			}
			catch(Exception e) {
				if(!dryRun)
					throw e;
				getLog().warn(e);
			}
			if(alreadyPublished) {
				Diagnostic diag = new Diagnostic(Diagnostic.WARNING, DiagnosticType.PUBLISHER, "Module " +
						metadata.getName() + ':' + metadata.getVersion() + " has already been published");
				diag.setIssue(ALREADY_PUBLISHED);
				result.addChild(diag);
			}
			else
				notPublished.put(metadata.getName(), builtModule);
		}

		if(result.getSeverity() == Diagnostic.ERROR)
			return;

		if(notPublished.isEmpty()) {
			result.addChild(new Diagnostic(
				Diagnostic.INFO, DiagnosticType.PUBLISHER,
				"All modules have already been published at their current version"));
			return;
		}

		for(Map.Entry<QName, File> entry : notPublished.entrySet()) {
			File moduleArchive = entry.getValue();
			if(dryRun) {
				result.addChild(new Diagnostic(Diagnostic.INFO, DiagnosticType.PUBLISHER, "Module file " +
						moduleArchive.getName() + " would have been uploaded (but wasn't since this is a dry run)"));
			}
			else {
				InputStream gzInput = new FileInputStream(moduleArchive);
				try {
					QName name = entry.getKey();
					releaseService.create(
						name.getQualifier(), name.getName(), "Published using GitHub trigger", gzInput,
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
