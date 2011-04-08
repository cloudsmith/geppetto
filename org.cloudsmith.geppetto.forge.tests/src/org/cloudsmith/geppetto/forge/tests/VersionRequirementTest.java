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

import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.MatchRule;
import org.cloudsmith.geppetto.forge.VersionRequirement;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Version Requirement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.VersionRequirement#matches(java.lang.String) <em>Matches</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class VersionRequirementTest extends TestCase {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(VersionRequirementTest.class);
	}

	/**
	 * The fixture for this Version Requirement test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VersionRequirement fixture = null;

	/**
	 * Constructs a new Version Requirement test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VersionRequirementTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Version Requirement test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VersionRequirement getFixture() {
		return fixture;
	}

	/**
	 * Sets the fixture for this Version Requirement test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void setFixture(VersionRequirement fixture) {
		this.fixture = fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ForgeFactory.eINSTANCE.createVersionRequirement());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	/**
	 * Tests the '{@link org.cloudsmith.geppetto.forge.VersionRequirement#matches(java.lang.String) <em>Matches</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.VersionRequirement#matches(java.lang.String)
	 * @generated NOT
	 */
	public void testMatches__String() {
		fixture.setVersion("1.2.3.foo");
		fixture.setMatchRule(MatchRule.GREATER_OR_EQUAL);
		assertTrue(fixture.matches("1.2.3.foo"));
		assertTrue(fixture.matches("1.2.4"));
		assertTrue(fixture.matches("1.10"));
		assertTrue(fixture.matches("2alpha"));
		assertFalse(fixture.matches("1.1.1"));
		assertFalse(fixture.matches("1"));
		assertFalse(fixture.matches("1.2alpha"));

		fixture.setMatchRule(MatchRule.PERFECT);
		assertTrue(fixture.matches("1.2.3.foo"));
		assertTrue(fixture.matches("1.2.3foo"));
		assertTrue(fixture.matches("1.2.3.0.foo"));
		assertFalse(fixture.matches("1.2.3.bar"));
		assertFalse(fixture.matches("1.2.3"));

		fixture.setMatchRule(MatchRule.EQUIVALENT);
		assertTrue(fixture.matches("1.2.3.foo"));
		assertTrue(fixture.matches("1.2.3.fox"));
		assertTrue(fixture.matches("1.2.3fox"));
		assertTrue(fixture.matches("1.2.4"));
		assertFalse(fixture.matches("1.2.3.bar"));
		assertFalse(fixture.matches("1.2.2"));
		assertFalse(fixture.matches("1.3.4"));
		assertFalse(fixture.matches("2.2.3"));

		fixture.setMatchRule(MatchRule.COMPATIBLE);
		assertTrue(fixture.matches("1.2.3.foo"));
		assertTrue(fixture.matches("1.2.3.fox"));
		assertTrue(fixture.matches("1.2.4"));
		assertFalse(fixture.matches("1.2.2"));

		assertTrue(fixture.matches("1.3.4"));
		assertFalse(fixture.matches("1.1.4"));
		assertFalse(fixture.matches("2.2.3"));
	}

} // VersionRequirementTest
