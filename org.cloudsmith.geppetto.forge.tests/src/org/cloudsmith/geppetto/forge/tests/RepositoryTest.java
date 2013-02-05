/**
 * Copyright (c) 2013 Cloudsmith Inc. and other contributors, as listed below.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.HttpMethod;
import org.cloudsmith.geppetto.forge.Repository;
import org.junit.Before;
import org.junit.Test;

public class RepositoryTest {

	private Repository fixture = null;

	@Before
	public void setUp() throws Exception {
		ForgeService service = ForgeFactory.eINSTANCE.createForgeService();
		fixture = service.createRepository(URI.create("http://forge.puppetlabs.com"));
	}

	@Test
	public void testCacheKey() {
		assertEquals(
			"Bad cache key", fixture.getCacheKey(),
			"http_forge_puppetlabs_com-75a31f1d6f1ef6eb63b4479b3512ee1508209a7f");
	}

	@Test
	public void testConnect__HttpMethod_String() {
		try {
			HttpURLConnection conn = fixture.connect(HttpMethod.HEAD, CacheTest.FILE_TO_TEST);
			assertEquals("Unexpected content type", "application/octet-stream", conn.getContentType());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}
}
