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

import java.io.File;
import java.io.IOException;
import java.net.URI;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.forge.Repository;

/**
 * <!-- begin-user-doc --> A test case for the model object ' <em><b>Service</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.ForgeService#createCache(java.io.File, org.cloudsmith.geppetto.forge.Repository) <em>Create Cache</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.ForgeService#createForge(java.net.URI) <em>Create Forge</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.ForgeService#createForge(java.net.URI, java.io.File) <em>Create Forge</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.ForgeService#createForge(org.cloudsmith.geppetto.forge.Repository, org.cloudsmith.geppetto.forge.Cache)
 * <em>Create Forge</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.ForgeService#createMetadata(java.lang.String) <em>Create Metadata</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.ForgeService#createRepository(java.net.URI) <em>Create Repository</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.ForgeService#loadJSONMetadata(java.io.File) <em>Load JSON Metadata</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.ForgeService#loadModule(java.io.File) <em>Load Module</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ForgeServiceTest extends TestCase {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ForgeServiceTest.class);
	}

	/**
	 * The fixture for this Service test case.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected ForgeService fixture = null;

	/**
	 * Constructs a new Service test case with the given name. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ForgeServiceTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Service test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ForgeService getFixture() {
		return fixture;
	}

	/**
	 * Sets the fixture for this Service test case.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected void setFixture(ForgeService fixture) {
		this.fixture = fixture;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ForgeFactory.eINSTANCE.createForgeService());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.ForgeService#createCache(java.io.File, org.cloudsmith.geppetto.forge.Repository)
	 * <em>Create Cache</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.ForgeService#createCache(java.io.File, org.cloudsmith.geppetto.forge.Repository)
	 * @generated NOT
	 */
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

	/**
	 * Tests the '
	 * {@link org.cloudsmith.geppetto.forge.ForgeService#createForge(org.cloudsmith.geppetto.forge.Repository, org.cloudsmith.geppetto.forge.Cache)
	 * <em>Create Forge</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.ForgeService#createForge(org.cloudsmith.geppetto.forge.Repository, org.cloudsmith.geppetto.forge.Cache)
	 * @generated NOT
	 */
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

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.ForgeService#createForge(java.net.URI)
	 * <em>Create Forge</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.ForgeService#createForge(java.net.URI)
	 * @generated NOT
	 */
	public void testCreateForge__URI() {
		assertNotNull(fixture.createForge(URI.create("http://forge.puppetlabs.com")));
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.ForgeService#createForge(java.net.URI, java.io.File)
	 * <em>Create Forge</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.ForgeService#createForge(java.net.URI, java.io.File)
	 * @generated NOT
	 */
	public void testCreateForge__URI_File() {
		try {
			assertNotNull(fixture.createForge(
				URI.create("http://forge.puppetlabs.com"), ForgeTests.getTestOutputFolder("cachefolder", true)));
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.ForgeService#createMetadata(java.lang.String)
	 * <em>Create Metadata</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.ForgeService#createMetadata(java.lang.String)
	 * @generated NOT
	 */
	public void testCreateMetadata__String() {
		assertNotNull(fixture.createMetadata("cloudsmith/testmodule"));
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.ForgeService#createRepository(java.net.URI)
	 * <em>Create Repository</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.ForgeService#createRepository(java.net.URI)
	 * @generated NOT
	 */
	public void testCreateRepository__URI() {
		assertNotNull(fixture.createRepository(URI.create("http://forge.puppetlabs.com")));
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.ForgeService#loadJSONMetadata(java.io.File)
	 * <em>Load JSON Metadata</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.ForgeService#loadJSONMetadata(java.io.File)
	 * @generated NOT
	 */
	public void testLoadJSONMetadata__File() {
		try {
			Metadata md = fixture.loadJSONMetadata(Activator.getTestData("puppetlabs-apache/metadata.json"));
			assertEquals("Unexpected module name", "puppetlabs-apache", md.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.ForgeService#loadModule(java.io.File)
	 * <em>Load Module</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.ForgeService#loadModule(java.io.File)
	 * @generated NOT
	 */
	public void testLoadModule__File() {
		try {
			Metadata md = fixture.loadModule(Activator.getTestData("puppetlabs-apache"));
			assertEquals("Unexpected module name", "puppetlabs-apache", md.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	public void testLoadModule__File2() {
		try {
			Metadata md = fixture.loadModule(Activator.getTestData("DavidSchmitt-collectd"));
			assertEquals("Unexpected module name", "DavidSchmitt-collectd", md.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	public void testLoadModule__File3() {
		try {
			Metadata md = fixture.loadModule(Activator.getTestData("bobsh-iptables"));
			assertEquals("Unexpected module name", "bobsh-iptables", md.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	public void testLoadModule__File4() {
		try {
			Metadata md = fixture.loadModule(Activator.getTestData("ghoneycutt-rsync"));
			assertEquals("Unexpected module name", "ghoneycutt-rsync", md.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	public void testLoadModule__File5() {
		try {
			Metadata md = fixture.loadModule(Activator.getTestData("lab42-common"));
			assertEquals("Unexpected module name", "lab42-common", md.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

} // ForgeServiceTest
