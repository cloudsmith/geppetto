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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
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
	public void equalsSeparatorInsensitive() {
		ModuleName name = new ModuleName("some-name");
		assertEquals("ModuleName should not consider separator when comparing", new ModuleName("some/name"), name);
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
		assertFail("Upper-name");
		assertFail("owner-Upper");
		assertOK("some-name");
	}

	@Test
	public void nameWithDigits() {
		assertFail("1st-name");
		assertFail("owner-1st");
		assertOK("some123-name123");
	}

	@Test
	public void nameWithUnderscore() {
		assertFail("_under-name");
		assertFail("owner-_under");
		assertOK("some_123-name_123");
	}

	@Test
	public void nonAsciiNames() {
		assertFail("börje-name");
		assertFail("owner-smörobröd");
	}
}
