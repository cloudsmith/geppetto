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
import java.io.IOException;
import java.util.List;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.cloudsmith.geppetto.common.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.common.diagnostic.DiagnosticType;
import org.cloudsmith.geppetto.forge.IncompleteException;

/**
 * Goal which performs basic validation.
 */
@Mojo(name = "package", requiresProject = false, defaultPhase = LifecyclePhase.PACKAGE)
public class Package extends AbstractForgeMojo {
	private File buildForge(File moduleSource, File destination, Diagnostic result) throws IOException {
		try {
			return getForge().build(moduleSource, destination, getFileFilter(), null);
		}
		catch(IncompleteException e) {
			result.addChild(new Diagnostic(Diagnostic.ERROR, DiagnosticType.PACKAGE, e.getMessage()));
			return null;
		}
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

		File builtModules = new File(getBuildDir(), "builtModules");
		if(!(builtModules.mkdirs() || builtModules.isDirectory())) {
			result.addChild(new Diagnostic(Diagnostic.ERROR, DiagnosticType.PACKAGE, "Unable to create directory" +
					builtModules.getPath()));
			return;
		}

		for(File moduleRoot : moduleRoots)
			buildForge(moduleRoot, builtModules, result);
	}
}
