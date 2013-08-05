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
package org.cloudsmith.geppetto.forge;

import org.cloudsmith.geppetto.forge.impl.ForgeModule;
import org.cloudsmith.geppetto.forge.impl.ForgePreferencesBean;

import com.google.inject.Module;
import com.google.inject.util.Modules;

public class ForgeService {
	private static final ForgePreferences defaultPreferences;

	private static final Module defaultForgeModule;

	static {
		ForgePreferencesBean fp = new ForgePreferencesBean();
		fp.setBaseURL("http://forgeapi.puppetlabs.com");
		defaultPreferences = fp;
		defaultForgeModule = new ForgeModule(fp);
	}

	public static Module getDefaultModule() {
		return defaultForgeModule;
	}

	public static ForgePreferences getDefaultPreferences() {
		return defaultPreferences;
	}

	public static Module getForgeModule(ForgePreferences forgePreferences, Module... overrides) {
		Module forgeModule = forgePreferences == defaultPreferences
				? defaultForgeModule
				: new ForgeModule(forgePreferences);
		if(overrides.length > 0)
			forgeModule = Modules.override(forgeModule).with(overrides);
		return forgeModule;
	}
}
