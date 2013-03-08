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
package org.cloudsmith.geppetto.forge.tests;

import static org.junit.Assert.fail;

import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.impl.ForgeModule;
import org.cloudsmith.geppetto.forge.impl.ForgePreferencesBean;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class AbstractForgeTest {
	private static String TEST_FORGE_URI = "http://forge-staging-api.puppetlabs.com/v2/";

	private static Injector injector;

	public static Cache getCache() {
		return getInjector().getInstance(Cache.class);
	}

	public static Forge getForge() {
		return getInjector().getInstance(Forge.class);
	}

	public static Gson getGson() {
		return getInjector().getInstance(Gson.class);
	}

	private synchronized static Injector getInjector() {
		if(injector == null) {
			ForgePreferencesBean forgePreferences = new ForgePreferencesBean();
			forgePreferences.setBaseURL(TEST_FORGE_URI);
			try {
				forgePreferences.setCacheLocation(ForgeTests.getTestOutputFolder("cachefolder", true).getAbsolutePath());
				injector = Guice.createInjector(new ForgeModule(forgePreferences));
			}
			catch(Exception e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		}
		return injector;
	}
}
