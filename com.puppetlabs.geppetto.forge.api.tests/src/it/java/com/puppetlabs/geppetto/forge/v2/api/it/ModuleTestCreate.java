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
package com.puppetlabs.geppetto.forge.v2.api.it;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import com.puppetlabs.geppetto.forge.api.it.ForgeAPITestBase;
import com.puppetlabs.geppetto.forge.v2.model.Module;
import com.puppetlabs.geppetto.forge.v2.service.ModuleService;
import com.puppetlabs.geppetto.forge.v2.service.ModuleTemplate;

import org.junit.Test;

/**
 * @author thhal
 * 
 */
public class ModuleTestCreate extends ForgeAPITestBase {
	@Test
	public void testCreate() throws IOException {
		ModuleService service = getTestUserForge().createModuleService();

		ModuleTemplate template = new ModuleTemplate();
		template.setOwner(TEST_USER);
		template.setName(TEST_MODULE);
		template.setDescription("A Dummy Test Module");
		Module newModule = service.create(template);
		assertNotNull("Null Module", newModule);
	}
}
