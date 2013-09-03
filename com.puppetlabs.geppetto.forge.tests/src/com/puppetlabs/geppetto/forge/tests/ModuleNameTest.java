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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.puppetlabs.geppetto.forge.v2.model.ModuleName;
import org.junit.Test;

public class ModuleNameTest extends AbstractForgeTest {

	private void assertFail(String name) {
		try {
			new ModuleName(name);
			fail("ModuleName should not accept: '" + name + '\'');
		}
		catch(IllegalArgumentException e) {
		}
	}

	private void assertOK(String name) {
		try {
			new ModuleName(name);
		}
		catch(IllegalArgumentException e) {
			fail("ModuleName should accept: '" + name + '\'');
		}
	}

	@Test
	public void attributes() {
		ModuleName name = new ModuleName("some-name");
		assertEquals("Full name qualifier not set correctly", "some", name.getOwner());
		assertEquals("Full name name, not set correctly", "name", name.getName());
		assertEquals("Full name separator not set correctly", '-', name.getSeparator());
	}

	@Test
	public void compareCaseInsensitive() {
		ModuleName name = new ModuleName("some-name");
		assertEquals("Qualifier not case insensitive", 0, new ModuleName("Some-name").compareTo(name));
		assertEquals("Name not case insensitive", 0, new ModuleName("some-Name").compareTo(name));
		assertEquals("Combo not case insensitive", 0, new ModuleName("SOME-NAME").compareTo(name));

		assertTrue("bad order", new ModuleName("good-name").compareTo(name) < 0);
		assertTrue("bad order", new ModuleName("xtra-name").compareTo(name) > 0);

		assertTrue("compare not case insensitive", new ModuleName("Good-Name").compareTo(name) < 0);
		assertTrue("compare not case insensitive", new ModuleName("Xtra-Name").compareTo(name) > 0);
	}

	@Test
	public void equalsCaseInsensitive() {
		ModuleName name = new ModuleName("some-name");
		assertEquals("Qualifier not case insensitive", new ModuleName("Some-name"), name);
		assertEquals("Name not case insensitive", new ModuleName("some-Name"), name);
		assertEquals("Combo not case insensitive", new ModuleName("SOME-NAME"), name);
	}

	@Test
	public void equalsSeparatorInsensitive() {
		ModuleName name = new ModuleName("some-name");
		assertEquals("ModuleName should not consider separator when comparing", new ModuleName("some/name"), name);
	}

	@Test
	public void forbiddenNames() {
		assertFail("owner-main");
		assertFail("owner-settings");
	}

	@Test
	public void nameSeparators() {
		assertOK("some-name");
		assertOK("some/name");
		assertFail("some.name");
		assertFail("some#name");
		assertFail("some$name");
		assertFail("some%name");
	}

	@Test
	public void nameWithCase() {
		assertOK("Upper-name");
		assertOK("owner-Upper");
		assertOK("some-name");
	}

	@Test
	public void nameWithDigits() {
		assertOK("1st-name");
		assertFail("owner-1st");
		assertOK("some123-name123");
	}

	@Test
	public void nameWithUnderscore() {
		assertFail("_under-name");
		assertFail("owner-_under");
		assertOK("some123-name_123");
	}

	@Test
	public void nonAsciiNames() {
		assertFail("börje-name");
		assertFail("owner-smörobröd");
	}
}
