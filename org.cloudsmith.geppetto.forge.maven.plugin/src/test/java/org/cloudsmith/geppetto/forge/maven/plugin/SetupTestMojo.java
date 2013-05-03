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
package org.cloudsmith.geppetto.forge.maven.plugin;

import java.util.Properties;

import org.cloudsmith.geppetto.forge.client.ForgeAPIPreferencesBean;
import org.cloudsmith.geppetto.forge.client.ForgeHttpModule;
import org.cloudsmith.geppetto.forge.v2.ForgeAPI;
import org.cloudsmith.geppetto.forge.v2.service.ModuleService;
import org.cloudsmith.geppetto.forge.v2.service.ModuleTemplate;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;

public class SetupTestMojo extends AbstractForgeTestMojo {
	private static ForgeAPIPreferencesBean createBasicForgePrefs() throws Exception {
		ForgeAPIPreferencesBean forgePrefs = new ForgeAPIPreferencesBean();

		Properties props = AbstractForgeMojo.readForgeProperties();
		forgePrefs.setOAuthClientId(props.getProperty("forge.oauth.clientID"));
		forgePrefs.setOAuthClientSecret(props.getProperty("forge.oauth.clientSecret"));
		forgePrefs.setBaseURL(System.getProperty("forge.base.url"));
		return forgePrefs;
	}

	@Test
	public void createInitialModules() throws Exception {
		// Login using the primary login (bob)
		ForgeAPIPreferencesBean forgePrefs = createBasicForgePrefs();
		forgePrefs.setLogin(System.getProperty("forge.login"));
		forgePrefs.setPassword(System.getProperty("forge.password"));
		ForgeAPI forge = new ForgeAPI(Guice.createInjector(new ForgeHttpModule(forgePrefs)));

		// Create the modules used in publishing tests
		ModuleService moduleService = forge.createModuleService();
		ModuleTemplate template = new ModuleTemplate();
		template.setName("test_module_a");
		template.setDescription("The module test_module_a is an integration test artifact");
		moduleService.create(template);

		template.setName("test_module_b");
		template.setDescription("The module test_module_b is an integration test artifact");
		moduleService.create(template);

		template.setName("test_module_c");
		template.setDescription("The module test_module_c is an integration test artifact");
		moduleService.create(template);

		// Login using the second login (ben)
		forgePrefs = createBasicForgePrefs();
		forgePrefs.setLogin(System.getProperty("forge.login.second"));
		forgePrefs.setPassword(System.getProperty("forge.password.second"));
		ForgeAPI secondForge = new ForgeAPI(Guice.createInjector(new ForgeHttpModule(forgePrefs)));
		moduleService = secondForge.createModuleService();

		// Create the module used for the wrong owner publishing test
		template.setName("test_module_wrong_owner");
		template.setDescription("The module test_module_wrong_owner is an integration test artifact");
		moduleService.create(template);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
}
