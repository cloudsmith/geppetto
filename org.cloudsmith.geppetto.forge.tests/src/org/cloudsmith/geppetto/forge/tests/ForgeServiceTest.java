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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.forge.Repository;
import org.junit.Before;
import org.junit.Test;

public class ForgeServiceTest {

	private ForgeService fixture = null;

	@Before
	public void setUp() throws Exception {
		fixture = ForgeFactory.eINSTANCE.createForgeService();
	}

	@Test
	public void testCreateCache__File_Repository() {
		try {
			Repository repository = fixture.createRepository(URI.create("http://forge.puppetlabs.com"));
			File cacheFolder = ForgeTests.getTestOutputFolder("cachefolder", true);
			assertNotNull(fixture.createCache(cacheFolder, repository));
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateForge__Repository_Cache() {
		try {
			Repository repository = fixture.createRepository(URI.create("http://forge.puppetlabs.com"));
			File cacheFolder = ForgeTests.getTestOutputFolder("cachefolder", true);
			Cache cache = fixture.createCache(cacheFolder, repository);
			assertNotNull(fixture.createForge(repository, cache));
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateForge__URI() {
		assertNotNull(fixture.createForge(URI.create("http://forge.puppetlabs.com")));
	}

	@Test
	public void testCreateForge__URI_File() {
		try {
			assertNotNull(fixture.createForge(
				URI.create("http://forge.puppetlabs.com"), ForgeTests.getTestOutputFolder("cachefolder", true)));
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateMetadata__String() {
		assertNotNull(fixture.createMetadata("cloudsmith/testmodule"));
	}

	@Test
	public void testCreateRepository__URI() {
		assertNotNull(fixture.createRepository(URI.create("http://forge.puppetlabs.com")));
	}

	@Test
	public void testLoadJSONMetadata__File() {
		try {
			Metadata md = fixture.loadJSONMetadata(Activator.getTestData("puppetlabs-apache/metadata.json"));
			assertEquals("Unexpected module name", "puppetlabs-apache", md.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testLoadModule__File() {
		try {
			Metadata md = fixture.loadModule(Activator.getTestData("puppetlabs-apache"));
			assertEquals("Unexpected module name", "puppetlabs-apache", md.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testLoadModule__File2() {
		try {
			Metadata md = fixture.loadModule(Activator.getTestData("DavidSchmitt-collectd"));
			assertEquals("Unexpected module name", "DavidSchmitt-collectd", md.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testLoadModule__File3() {
		try {
			Metadata md = fixture.loadModule(Activator.getTestData("bobsh-iptables"));
			assertEquals("Unexpected module name", "bobsh-iptables", md.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testLoadModule__File4() {
		try {
			Metadata md = fixture.loadModule(Activator.getTestData("ghoneycutt-rsync"));
			assertEquals("Unexpected module name", "ghoneycutt-rsync", md.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testLoadModule__File5() {
		try {
			Metadata md = fixture.loadModule(Activator.getTestData("lab42-common"));
			assertEquals("Unexpected module name", "lab42-common", md.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

}
