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

import org.junit.Test;

import com.puppetlabs.geppetto.forge.model.ModuleName;

public class ModuleNameTest extends AbstractForgeTest {

	private void assertFail(String name) {
		try {
			ModuleName.fromString(name);
			fail("ModuleName should not accept: '" + name + '\'');
		}
		catch(IllegalArgumentException e) {
		}
	}

	private void assertOK(String name) {
		try {
			ModuleName.fromString(name);
		}
		catch(IllegalArgumentException e) {
			fail("ModuleName should accept: '" + name + '\'');
		}
	}

	@Test
	public void attributes() {
		ModuleName name = ModuleName.fromString("some-name");
		assertEquals("Full name qualifier not set correctly", "some", name.getOwner());
		assertEquals("Full name name, not set correctly", "name", name.getName());
	}

	@Test
	public void compareCaseInsensitive() {
		ModuleName name = ModuleName.fromString("some-name");
		assertEquals("Qualifier not case insensitive", 0, ModuleName.fromString("Some-name").compareTo(name));
		assertEquals("Name not case insensitive", 0, ModuleName.fromString("some-Name").compareTo(name));
		assertEquals("Combo not case insensitive", 0, ModuleName.fromString("SOME-NAME").compareTo(name));

		assertTrue("bad order", ModuleName.fromString("good-name").compareTo(name) < 0);
		assertTrue("bad order", ModuleName.fromString("xtra-name").compareTo(name) > 0);

		assertTrue("compare not case insensitive", ModuleName.fromString("Good-Name").compareTo(name) < 0);
		assertTrue("compare not case insensitive", ModuleName.fromString("Xtra-Name").compareTo(name) > 0);
	}

	@Test
	public void equalsCaseInsensitive() {
		ModuleName name = ModuleName.fromString("some-name");
		assertEquals("Qualifier not case insensitive", ModuleName.fromString("Some-name"), name);
		assertEquals("Name not case insensitive", ModuleName.fromString("some-Name"), name);
		assertEquals("Combo not case insensitive", ModuleName.fromString("SOME-NAME"), name);
	}

	@Test
	public void equalsSeparatorInsensitive() {
		ModuleName name = ModuleName.fromString("some-name");
		assertEquals("ModuleName should not consider separator when comparing", ModuleName.fromString("some/name"), name);
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
