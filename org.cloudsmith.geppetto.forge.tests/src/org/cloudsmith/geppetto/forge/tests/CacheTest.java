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
import org.cloudsmith.geppetto.forge.Repository;

/**
 * <!-- begin-user-doc --> A test case for the model object ' <em><b>Cache</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.Cache#retrieve(java.lang.String) <em>Retrieve</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Cache#clean() <em>Clean</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CacheTest extends TestCase {

	static final String FILE_TO_TEST = "/system/releases/g/ghoneycutt/ghoneycutt-rsync-1.0.1.tar.gz";

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(CacheTest.class);
	}

	/**
	 * The fixture for this Cache test case.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected Cache fixture = null;

	/**
	 * Constructs a new Cache test case with the given name.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CacheTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Cache test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected Cache getFixture() {
		return fixture;
	}

	/**
	 * Sets the fixture for this Cache test case.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected void setFixture(Cache fixture) {
		this.fixture = fixture;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see junit.framework.TestCase#setUp()
	 * @generated NOT
	 */
	@Override
	protected void setUp() throws Exception {
		try {
			ForgeService service = ForgeFactory.eINSTANCE.createForgeService();
			Repository repository = service.createRepository(URI.create("http://forge.puppetlabs.com"));
			File cacheFolder = ForgeTests.getTestOutputFolder("cachefolder", true);
			setFixture(service.createCache(cacheFolder, repository));
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
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
	 * Tests the '{@link org.cloudsmith.geppetto.forge.Cache#clean()
	 * <em>Clean</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Cache#clean()
	 * @generated NOT
	 */
	public void testClean() {
		try {
			File file = getFixture().retrieve(FILE_TO_TEST);
			assertTrue("Retrieved file is not a file", file.isFile());
			getFixture().clean();
			assertFalse("Clean did not remove the cached files", file.isFile());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Cache#retrieve(java.lang.String)
	 * <em>Retrieve</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Cache#retrieve(java.lang.String)
	 * @generated NOT
	 */
	public void testRetrieve__String() {
		try {
			File file = getFixture().retrieve(FILE_TO_TEST);
			assertTrue("Retrieved file is not a file", file.isFile());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

} // CacheTest
