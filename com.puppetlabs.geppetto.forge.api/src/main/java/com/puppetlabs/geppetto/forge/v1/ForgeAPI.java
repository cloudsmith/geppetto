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
package com.puppetlabs.geppetto.forge.v1;

import com.puppetlabs.geppetto.forge.v1.service.ModuleService;

import com.google.inject.Injector;

/**
 * This is the main entry point to the API. Sample usage:
 * 
 * <pre>
 * // Obtain preferences in some way. Command line options, property settings, etc. 
 * ForgeAPIPreferences prefs = ...;
 * 
 * Injector injector = Guice.createInjector(new ForgeHttpModule(prefs));
 * 
 * // Create a new forge instance
 * ForgeAPI forge = new ForgeAPI(injector);
 * 
 * // Use the forge instance to create a service.
 * ModuleService moduleService = forge.createModuleService();
 * 
 * // Use the service
 * List<ModuleInfo> stdLibReleases = moduleService.getModules("puppetlabs");
 * </pre>
 */
public class ForgeAPI {
	private final Injector injector;

	/**
	 * Create a new instance based on a Guice injector. This method
	 * is primary intended to be used by the test framework.
	 * 
	 * @param injector
	 *            Guice injector that provides the needed bindings.
	 */
	public ForgeAPI(Injector injector) {
		this.injector = injector;
	}

	/**
	 * Creates a new service that can be used when browsing modules, v1 style
	 * 
	 * @return The new v1 module service.
	 */
	public ModuleService createModuleService() {
		return injector.getInstance(ModuleService.class);
	}
}
