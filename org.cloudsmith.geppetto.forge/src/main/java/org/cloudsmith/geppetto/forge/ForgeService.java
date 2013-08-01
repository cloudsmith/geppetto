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
	public static ForgePreferences getDefaultPreferences() {
		ForgePreferencesBean forgePreferences = new ForgePreferencesBean();
		forgePreferences.setBaseURL("http://forgeapi.puppetlabs.com");
		return forgePreferences;
	}

	public static Module getForgeModule(ForgePreferences forgePreferences, Module... overrides) {
		Module forgeModule = new ForgeModule(forgePreferences);
		if(overrides.length > 0)
			forgeModule = Modules.override(forgeModule).with(overrides);
		return forgeModule;
	}

	private final Injector forgeInjector;

	public ForgeService(ForgePreferences forgePreferences, Module... overrides) {
		this.forgeInjector = Guice.createInjector(getForgeModule(forgePreferences, overrides));
	}

	public Injector getForgeInjector() {
		return forgeInjector;
	}
}
