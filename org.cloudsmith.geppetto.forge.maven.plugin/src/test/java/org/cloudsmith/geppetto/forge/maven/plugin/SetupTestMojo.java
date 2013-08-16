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
package org.cloudsmith.geppetto.forge.maven.plugin;

import static org.cloudsmith.geppetto.injectable.CommonModuleProvider.getCommonModule;

import java.io.IOException;
import java.util.Properties;

import org.cloudsmith.geppetto.forge.client.ForgeHttpModule;
import org.cloudsmith.geppetto.forge.client.GsonModule;
import org.cloudsmith.geppetto.forge.client.OAuthModule;
import org.cloudsmith.geppetto.forge.v2.ForgeAPI;
import org.cloudsmith.geppetto.forge.v2.service.ModuleService;
import org.cloudsmith.geppetto.forge.v2.service.ModuleTemplate;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Module;

public class SetupTestMojo extends AbstractForgeTestMojo {
	private static Module createCredentialsModule(Properties props, String login, String password) throws IOException {
		return new OAuthModule(
			props.getProperty("forge.oauth.clientID"), props.getProperty("forge.oauth.clientSecret"), login, password);
	}

	@Test
	public void createInitialModules() throws Exception {
		Properties props = AbstractForgeMojo.readForgeProperties();

		// Login using the primary login (bob)
		Module forgeModule = new ForgeHttpModule() {
			@Override
			protected String getBaseURL() {
				return System.getProperty("forge.base.url");
			}
		};

		ForgeAPI forge = new ForgeAPI(getCommonModule(), GsonModule.INSTANCE, createCredentialsModule(
			props, System.getProperty("forge.login"), System.getProperty("forge.password")), forgeModule);

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
		ForgeAPI secondForge = new ForgeAPI(getCommonModule(), GsonModule.INSTANCE, createCredentialsModule(
			props, System.getProperty("forge.login.second"), System.getProperty("forge.password.second")), forgeModule);
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
