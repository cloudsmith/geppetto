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

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.cloudsmith.geppetto.forge.Dependency;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.MatchRule;
import org.cloudsmith.geppetto.forge.VersionRequirement;

/**
 * <!-- begin-user-doc --> A test case for the model object ' <em><b>Dependency</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.Dependency#matches(java.lang.String, java.lang.String) <em>Matches</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class DependencyTest extends TestCase {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(DependencyTest.class);
	}

	/**
	 * The fixture for this Dependency test case.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected Dependency fixture = null;

	/**
	 * Constructs a new Dependency test case with the given name. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public DependencyTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Dependency test case.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected Dependency getFixture() {
		return fixture;
	}

	/**
	 * Sets the fixture for this Dependency test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void setFixture(Dependency fixture) {
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
		setFixture(ForgeFactory.eINSTANCE.createDependency());
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
	 * Tests the '{@link org.cloudsmith.geppetto.forge.Dependency#matches(java.lang.String, java.lang.String) <em>Matches</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Dependency#matches(java.lang.String, java.lang.String)
	 * @generated NOT
	 */
	public void testMatches__String_String() {
		fixture.setName("a.module.name");
		VersionRequirement vr = ForgeFactory.eINSTANCE.createVersionRequirement();
		vr.setMatchRule(MatchRule.EQUIVALENT);
		vr.setVersion("2.3");
		fixture.setVersionRequirement(vr);
		assertTrue(fixture.matches("a.module.name", "2.3.2"));
		assertFalse(fixture.matches("a.module.name", "2.2.9"));
		assertFalse(fixture.matches("another.module.name", "2.3.2"));
	}

} // DependencyTest
