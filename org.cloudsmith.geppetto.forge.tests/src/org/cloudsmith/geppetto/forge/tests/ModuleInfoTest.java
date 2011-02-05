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

import java.net.URI;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ModuleInfo;
import org.cloudsmith.geppetto.forge.impl.ModuleInfoImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * <!-- begin-user-doc --> A test case for the model object ' <em><b>Module Info</b></em>'. <!-- end-user-doc -->
 * 
 * @generated
 */
public class ModuleInfoTest extends TestCase {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ModuleInfoTest.class);
	}

	/**
	 * The fixture for this Module Info test case.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected ModuleInfo fixture = null;

	/**
	 * Constructs a new Module Info test case with the given name. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ModuleInfoTest(String name) {
		super(name);
	}

	public void testJSonSerialization() throws Exception {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		fixture.setFullName("A full name");
		fixture.setName("aName");
		fixture.setProjectURL(URI.create("http://www.example.com"));
		fixture.setVersion("0.0.1");
		String json = gson.toJson(fixture);
		assertEquals(
			"{\"full_name\":\"A full name\",\"name\":\"aName\",\"project_url\":\"http://www.example.com\",\"version\":\"0.0.1\"}",
			json);
		ModuleInfo mi = gson.fromJson(json, ModuleInfoImpl.class);
		assertTrue(EcoreUtil.equals(fixture, mi));
	}

	/**
	 * Returns the fixture for this Module Info test case.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ModuleInfo getFixture() {
		return fixture;
	}

	/**
	 * Sets the fixture for this Module Info test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void setFixture(ModuleInfo fixture) {
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
		setFixture(ForgeFactory.eINSTANCE.createModuleInfo());
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
} // ModuleInfoTest
