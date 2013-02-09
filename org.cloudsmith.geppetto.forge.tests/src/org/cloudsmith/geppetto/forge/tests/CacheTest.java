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
import java.net.URI;

import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.Repository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CacheTest {

	static final String FILE_TO_TEST = "/system/releases/p/puppetlabs/puppetlabs-stdlib-2.3.1.tar.gz";

	private Cache fixture = null;

	@Before
	public void setUp() throws Exception {
		try {
			ForgeService service = ForgeFactory.eINSTANCE.createForgeService();
			Repository repository = service.createRepository(URI.create("http://forge.puppetlabs.com"));
			File cacheFolder = ForgeTests.getTestOutputFolder("cachefolder", true);
			fixture = service.createCache(cacheFolder, repository);
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@After
	public void tearDown() throws Exception {
		fixture = null;
	}

	@Test
	public void testClean() {
		try {
			File file = fixture.retrieve(FILE_TO_TEST);
			assertTrue("Retrieved file is not a file", file.isFile());
			fixture.clean();
			assertFalse("Clean did not remove the cached files", file.isFile());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testRetrieve__String() {
		try {
			File file = fixture.retrieve(FILE_TO_TEST);
			assertTrue("Retrieved file is not a file", file.isFile());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

}
