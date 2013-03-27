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
package org.cloudsmith.geppetto.forge;

import org.cloudsmith.geppetto.forge.impl.ForgeModule;
import org.cloudsmith.geppetto.forge.impl.ForgePreferencesBean;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;

public class ForgeService {
	private static ForgeService defaultInstance;

	public static synchronized ForgeService getDefault() {
		if(defaultInstance == null)
			defaultInstance = new ForgeService();
		return defaultInstance;
	}

	private final Module forgeModule;

	private final Injector forgeInjector;

	private ForgeService() {
		// TODO: Provide this from command line or prefs store
		ForgePreferencesBean forgePreferences = new ForgePreferencesBean();
		forgePreferences.setBaseURL("http://forge-staging-api.puppetlabs.com/v2/");
		forgePreferences.setOAuthURL("http://forge-staging-api.puppetlabs.com/oauth/token");
		forgeModule = new ForgeModule(forgePreferences);
		this.forgeInjector = Guice.createInjector(forgeModule);
	}

	public ForgeService(ForgePreferences forgePreferences) {
		this(new ForgeModule(forgePreferences));
	}

	public ForgeService(Module... forgeModules) {
		this.forgeModule = forgeModules.length == 1
				? forgeModules[0]
				: Modules.combine(forgeModules);
		this.forgeInjector = Guice.createInjector(forgeModule);
	}

	public Injector getForgeInjector() {
		return forgeInjector;
	}

	public Module getForgeModule() {
		return forgeModule;
	}
}
