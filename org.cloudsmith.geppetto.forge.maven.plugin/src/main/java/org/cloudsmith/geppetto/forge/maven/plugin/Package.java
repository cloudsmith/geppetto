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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.util.TarUtils;
import org.cloudsmith.geppetto.validation.DiagnosticType;

/**
 * Goal which performs basic validation.
 */
@Mojo(name = "package", requiresProject = false, defaultPhase = LifecyclePhase.PACKAGE)
public class Package extends AbstractForgeMojo {
	private File buildForge(ForgeService forgeService, File moduleSource, File destination, String[] namesReceiver,
			Diagnostic result) throws IOException {

		FileFilter filter = getFileFilter();
		File metadataJSON = new File(moduleSource, "metadata.json");
		org.cloudsmith.geppetto.forge.Metadata md;
		try {
			md = forgeService.loadJSONMetadata(metadataJSON);
		}
		catch(FileNotFoundException e) {
			md = forgeService.loadModule(moduleSource, filter);
			String fullName = md.getFullName();
			if(fullName == null) {
				result.addChild(new Diagnostic(
					Diagnostic.ERROR, DiagnosticType.PACKAGE,
					"A full name (user-module) must be specified in the Modulefile"));
				return null;
			}

			String ver = md.getVersion();
			if(ver == null) {
				result.addChild(new Diagnostic(
					Diagnostic.ERROR, DiagnosticType.PACKAGE, "A version must be specified in the Modulefile"));
				return null;
			}
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
		return "Package";
	}

	@Override
	protected void invoke(Diagnostic result) throws Exception {
		List<File> moduleRoots = findModuleRoots();
		if(moduleRoots.isEmpty()) {
			result.addChild(new Diagnostic(Diagnostic.ERROR, DiagnosticType.PACKAGE, "No modules found in repository"));
			return;
		}

		ForgeService forgeService = ForgeFactory.eINSTANCE.createForgeService();
		String[] namesReceiver = new String[2];
		File builtModules = new File(getBuildDir(), "builtModules");
		if(!(builtModules.mkdirs() || builtModules.isDirectory())) {
			result.addChild(new Diagnostic(Diagnostic.ERROR, DiagnosticType.PACKAGE, "Unable to create directory" +
					builtModules.getPath()));
			return;
		}

		for(File moduleRoot : moduleRoots)
			buildForge(forgeService, moduleRoot, builtModules, namesReceiver, result);
	}
}
