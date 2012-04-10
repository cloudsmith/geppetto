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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.HttpMethod;
import org.cloudsmith.geppetto.forge.Repository;

/**
 * <!-- begin-user-doc --> A test case for the model object ' <em><b>Repository</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.Repository#connect(org.cloudsmith.geppetto.forge.HttpMethod, java.lang.String) <em>Connect</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class RepositoryTest extends TestCase {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RepositoryTest.class);
	}

	/**
	 * The fixture for this Repository test case.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected Repository fixture = null;

	/**
	 * Constructs a new Repository test case with the given name. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public RepositoryTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Repository test case.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected Repository getFixture() {
		return fixture;
	}

	/**
	 * Sets the fixture for this Repository test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void setFixture(Repository fixture) {
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
		ForgeService service = ForgeFactory.eINSTANCE.createForgeService();
		setFixture(service.createRepository(URI.create("http://forge.puppetlabs.com")));
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
	 */
	public void testCacheKey() {
		assertEquals(
			"Bad cache key", fixture.getCacheKey(),
			"http_forge_puppetlabs_com-75a31f1d6f1ef6eb63b4479b3512ee1508209a7f");
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Repository#connect(org.cloudsmith.geppetto.forge.HttpMethod, java.lang.String)
	 * <em>Connect</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Repository#connect(org.cloudsmith.geppetto.forge.HttpMethod, java.lang.String)
	 * @generated NOT
	 */
	public void testConnect__HttpMethod_String() {
		try {
			HttpURLConnection conn = getFixture().connect(HttpMethod.HEAD, CacheTest.FILE_TO_TEST);
			assertEquals("Unexpected content type", "application/octet-stream", conn.getContentType());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

} // RepositoryTest
