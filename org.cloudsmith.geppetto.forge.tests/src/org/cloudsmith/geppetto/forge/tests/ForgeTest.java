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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.util.ModuleUtils;
import org.cloudsmith.geppetto.forge.v1.model.ModuleInfo;
import org.cloudsmith.geppetto.forge.v2.model.Module;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.junit.Before;
import org.junit.Test;

public class ForgeTest extends AbstractForgeTest {

	private static void assertExcludesMatch(String name) {
		assertTrue(
			"The name '" + name + "' does not match default excludes pattern",
			ModuleUtils.DEFAULT_EXCLUDES_PATTERN.matcher(name).matches());
	}

	private static void assertNotExcludesMatch(String name) {
		assertFalse(
			"The name '" + name + "' matches default excludes pattern",
			ModuleUtils.DEFAULT_EXCLUDES_PATTERN.matcher(name).matches());
	}

	private ForgeService fixture = null;

	@Test
	public void install() {
		try {
			File installFolder = getTestOutputFolder("stdlib-install", true);
			fixture.install(new ModuleName("puppetlabs/stdlib"), null, installFolder, false, true);
			File found = new File(installFolder, "stdlib");
			assertTrue("Installation did not produce the expected result", found.isDirectory());
		}
		catch(IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void searchModules() {
		try {
			List<Module> hits = fixture.search("rsync");
			assertFalse("No modules found matching 'rsync'", hits.isEmpty());
			for(Module mi : hits)
				System.out.println(mi.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void searchModules_v1() {
		try {
			List<ModuleInfo> hits = fixture.search_v1("rsync");
			assertFalse("No modules found matching 'rsync'", hits.isEmpty());
			for(ModuleInfo mi : hits)
				System.out.println(mi.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Before
	public void setUp() throws Exception {
		fixture = getForge();
	}

}
