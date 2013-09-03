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
package com.puppetlabs.geppetto.forge.maven.plugin;

import static com.puppetlabs.geppetto.diagnostic.Diagnostic.ERROR;
import static com.puppetlabs.geppetto.forge.Forge.PACKAGE;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;

/**
 * Goal that builds the module gzipped tarball and optionally generates the <tt>metadata.json</tt> file.
 */
@Mojo(name = "package", requiresProject = false, defaultPhase = LifecyclePhase.PACKAGE)
public class Package extends AbstractForgeMojo {
	private File buildForge(File moduleSource, File destination, Diagnostic result) throws IOException {
		return getForgeUtil().build(moduleSource, destination, null, null, result);
	}

	@Override
	protected String getActionName() {
		return "Package";
	}

	@Override
	protected void invoke(Diagnostic result) throws Exception {
		Collection<File> moduleRoots = findModuleRoots();
		if(moduleRoots.isEmpty()) {
			result.addChild(new Diagnostic(ERROR, PACKAGE, "No modules found in repository"));
			return;
		}

		File buildDir = getBuildDir();
		buildDir.mkdirs();
		if(moduleRoots.size() == 1)
			getProject().getArtifact().setFile(buildForge(moduleRoots.iterator().next(), buildDir, result));
		else {
			File builtModules = new File(buildDir, "builtModules");
			if(!(builtModules.mkdir() || builtModules.isDirectory())) {
				result.addChild(new Diagnostic(ERROR, PACKAGE, "Unable to create directory" + builtModules.getPath()));
				return;
			}
			for(File moduleRoot : moduleRoots)
				buildForge(moduleRoot, builtModules, result);
		}
	}
}
