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

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.cloudsmith.geppetto.common.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.common.diagnostic.DiagnosticType;

/**
 * Goal which performs basic validation.
 */
@Mojo(name = "publish", requiresProject = false, defaultPhase = LifecyclePhase.DEPLOY)
public class Publish extends AbstractForgeMojo {
	/**
	 * Set to <tt>true</tt> to enable validation using puppet-lint
	 */
	@Parameter(property = "forge.publish.dryrun")
	private boolean dryRun;

	@Override
	protected String getActionName() {
		return "Publishing";
	}

	@Override
	protected void invoke(Diagnostic result) throws Exception {
		File[] builtModules;
		File targetFile = getProject().getArtifact().getFile();
		if(targetFile == null) {
			File builtModulesDir = new File(getBuildDir(), "builtModules");
			builtModules = builtModulesDir.listFiles();
			if(builtModules == null || builtModules.length == 0) {
				result.addChild(new Diagnostic(
					Diagnostic.ERROR, DiagnosticType.PUBLISHER, "Unable find any packaged modules in " +
							builtModulesDir.getAbsolutePath()));
				return;
			}
		}
		else
			builtModules = new File[] { targetFile };

		getForge().publishAll(builtModules, dryRun, result);
	}
}
