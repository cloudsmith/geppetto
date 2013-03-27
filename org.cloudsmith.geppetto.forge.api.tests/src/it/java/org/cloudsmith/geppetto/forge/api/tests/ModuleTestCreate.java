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
package org.cloudsmith.geppetto.forge.api.tests;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.cloudsmith.geppetto.forge.api.tests.Module;
import org.cloudsmith.geppetto.forge.v2.service.ModuleService;
import org.cloudsmith.geppetto.forge.v2.service.ModuleTemplate;
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
