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
package com.puppetlabs.geppetto.forge.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.puppetlabs.geppetto.forge.ForgeService;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.v2.model.Module;

public class ForgeTest extends AbstractForgeTest {

	private ForgeService fixture = null;

	@Test
	public void install() {
		try {
			File installFolder = getTestOutputFolder("stdlib-install", true);
			fixture.install(ModuleName.fromString("puppetlabs/stdlib"), null, installFolder, false, true);
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

	@Before
	public void setUp() throws Exception {
		fixture = getForge();
	}

}
