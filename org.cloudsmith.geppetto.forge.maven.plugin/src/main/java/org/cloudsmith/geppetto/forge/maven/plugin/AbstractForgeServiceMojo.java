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

import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.client.ForgeHttpModule;
import org.cloudsmith.geppetto.forge.impl.ForgeServiceModule;

import com.google.inject.Module;

public abstract class AbstractForgeServiceMojo extends AbstractForgeMojo {
	/**
	 * The service URL of the Puppet ForgeAPI server
	 */
	@Parameter(property = "forge.serviceURL", defaultValue = "http://forgeapi.puppetlabs.com/")
	private String serviceURL;

	@Override
	protected void addModules(Diagnostic diagnostic, List<Module> modules) {
		super.addModules(diagnostic, modules);
		modules.add(new ForgeHttpModule() {
			@Override
			protected String getBaseURL() {
				return serviceURL;
			}
		});
		modules.add(new ForgeServiceModule());
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if(serviceURL == null)
			throw new MojoExecutionException("Missing required configuration parameter: 'serviceURL'");

		if(!serviceURL.endsWith("/"))
			serviceURL += "/";

		super.execute();
	}

	protected ForgeService getForge() {
		return getInjector().getInstance(ForgeService.class);
	}
}
