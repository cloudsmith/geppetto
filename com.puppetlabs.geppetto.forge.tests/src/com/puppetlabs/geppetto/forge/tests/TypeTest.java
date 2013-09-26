/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.forge.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import com.puppetlabs.geppetto.forge.model.Type;
import com.puppetlabs.geppetto.forge.util.Types;

import org.junit.Before;
import org.junit.Test;

public class TypeTest extends AbstractForgeTest {
	private Type fixture = null;

	@Before
	public void setUp() throws Exception {
		fixture = new Type();
	}

	@Test
	public void testLoadProvider__File() {
		try {
			Types.loadTypeFile(fixture, getTestData("DavidSchmitt-collectd/lib/puppet/type/collectd_conf.rb"));
			Types.loadProvider(fixture, getTestData("DavidSchmitt-collectd/lib/puppet/provider"));
			assertEquals("Expected 1 parameters", 1, fixture.getParameters().size());
			assertEquals("Expected 2 properties", 2, fixture.getProperties().size());
			assertEquals("Expected 1 providers", 1, fixture.getProviders().size());
			assertEquals("Unexpected provider name", "parsed", fixture.getProviders().get(0).getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testLoadProvider__File2() {
		try {
			Types.loadTypeFile(fixture, getTestData("puppetlabs-apache/lib/puppet/type/a2mod.rb"));
			Types.loadProvider(fixture, getTestData("puppetlabs-apache/lib/puppet/provider"));
			assertEquals("Expected 1 parameters", 1, fixture.getParameters().size());
			assertEquals("Expected 2 properties", 0, fixture.getProperties().size());
			assertEquals("Expected 1 providers", 1, fixture.getProviders().size());
			assertEquals("Unexpected provider name", "a2mod", fixture.getProviders().get(0).getName());
			assertEquals(
				"Unexpected provider doc", "Manage Apache 2 modules on Debian and Ubuntu",
				fixture.getProviders().get(0).getDocumentation());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testLoadTypeFile__DescWithPlus() {
		try {
			Types.loadTypeFile(fixture, getTestData("puppetlabs-stdlib/lib/puppet/type/file_line.rb"));
			assertEquals("Unexpected type name", "file_line", fixture.getName());
			assertEquals("Expected 4 parameters", 4, fixture.getParameters().size());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testLoadTypeFile__File() {
		try {
			Types.loadTypeFile(fixture, getTestData("bobsh-iptables/lib/puppet/type/iptables.rb"));
			assertEquals("Unexpected type name", "iptables", fixture.getName());
			assertEquals("Expected 22 parameters", 22, fixture.getParameters().size());
			assertEquals("Expected 0 properties", 0, fixture.getProperties().size());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testLoadTypeFile__File2() {
		try {
			Types.loadTypeFile(fixture, getTestData("lab42-common/lib/puppet/type/setparam.rb"));
			assertEquals("Unexpected type name", "setparam", fixture.getName());
			assertEquals("Expected 2 parameters", 2, fixture.getParameters().size());
			assertEquals("Expected 2 properties", 2, fixture.getProperties().size());

		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testLoadTypeFile__File3() {
		try {
			Types.loadTypeFile(fixture, getTestData("DavidSchmitt-collectd/lib/puppet/type/collectd_conf.rb"));
			assertEquals("Unexpected type name", "collectd_conf", fixture.getName());
			assertEquals("Expected 1 parameter", 1, fixture.getParameters().size());
			assertEquals("Expected 2 properties", 2, fixture.getProperties().size());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

} // TypeTest
