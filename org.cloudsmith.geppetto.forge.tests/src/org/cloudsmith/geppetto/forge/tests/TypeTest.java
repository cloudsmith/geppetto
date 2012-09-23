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

import junit.textui.TestRunner;

import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.Type;

/**
 * <!-- begin-user-doc --> A test case for the model object ' <em><b>Type</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.Type#loadTypeFile(java.io.File) <em>Load Type File</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Type#loadProvider(java.io.File) <em>Load Provider</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TypeTest extends DocumentedTest {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(TypeTest.class);
	}

	/**
	 * Constructs a new Type test case with the given name.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TypeTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Type test case.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected Type getFixture() {
		return (Type) fixture;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ForgeFactory.eINSTANCE.createType());
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
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Type#loadProvider(java.io.File)
	 * <em>Load Provider</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Type#loadProvider(java.io.File)
	 * @generated NOT
	 */
	public void testLoadProvider__File() {
		try {
			getFixture().loadTypeFile(Activator.getTestData("DavidSchmitt-collectd/lib/puppet/type/collectd_conf.rb"));
			getFixture().loadProvider(Activator.getTestData("DavidSchmitt-collectd/lib/puppet/provider"));
			assertEquals("Expected 1 parameters", 1, getFixture().getParameters().size());
			assertEquals("Expected 2 properties", 2, getFixture().getProperties().size());
			assertEquals("Expected 1 providers", 1, getFixture().getProviders().size());
			assertEquals("Unexpected provider name", "parsed", getFixture().getProviders().get(0).getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	public void testLoadProvider__File2() {
		try {
			getFixture().loadTypeFile(Activator.getTestData("puppetlabs-apache/lib/puppet/type/a2mod.rb"));
			getFixture().loadProvider(Activator.getTestData("puppetlabs-apache/lib/puppet/provider"));
			assertEquals("Expected 1 parameters", 1, getFixture().getParameters().size());
			assertEquals("Expected 2 properties", 0, getFixture().getProperties().size());
			assertEquals("Expected 1 providers", 1, getFixture().getProviders().size());
			assertEquals("Unexpected provider name", "a2mod", getFixture().getProviders().get(0).getName());
			assertEquals(
				"Unexpected provider doc", "Manage Apache 2 modules on Debian and Ubuntu",
				getFixture().getProviders().get(0).getDoc());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	public void testLoadTypeFile__DescWithPlus() {
		try {
			getFixture().loadTypeFile(Activator.getTestData("puppetlabs-stdlib/lib/puppet/type/file_line.rb"));
			assertEquals("Unexpected type name", "file_line", getFixture().getName());
			assertEquals("Expected 4 parameters", 4, getFixture().getParameters().size());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Type#loadTypeFile(java.io.File)
	 * <em>Load Type File</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Type#loadTypeFile(java.io.File)
	 * @generated NOT
	 */
	public void testLoadTypeFile__File() {
		try {
			getFixture().loadTypeFile(Activator.getTestData("bobsh-iptables/lib/puppet/type/iptables.rb"));
			assertEquals("Unexpected type name", "iptables", getFixture().getName());
			assertEquals("Expected 22 parameters", 22, getFixture().getParameters().size());
			assertEquals("Expected 0 properties", 0, getFixture().getProperties().size());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	public void testLoadTypeFile__File2() {
		try {
			getFixture().loadTypeFile(Activator.getTestData("lab42-common/lib/puppet/type/setparam.rb"));
			assertEquals("Unexpected type name", "setparam", getFixture().getName());
			assertEquals("Expected 2 parameters", 2, getFixture().getParameters().size());
			assertEquals("Expected 2 properties", 2, getFixture().getProperties().size());

		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	public void testLoadTypeFile__File3() {
		try {
			getFixture().loadTypeFile(Activator.getTestData("DavidSchmitt-collectd/lib/puppet/type/collectd_conf.rb"));
			assertEquals("Unexpected type name", "collectd_conf", getFixture().getName());
			assertEquals("Expected 1 parameter", 1, getFixture().getParameters().size());
			assertEquals("Expected 2 properties", 2, getFixture().getProperties().size());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

} // TypeTest
