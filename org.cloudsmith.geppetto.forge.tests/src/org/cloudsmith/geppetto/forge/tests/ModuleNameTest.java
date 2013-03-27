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
import static org.junit.Assert.assertTrue;

import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.junit.Test;

public class ModuleNameTest extends AbstractForgeTest {

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
		assertEquals("Not separator insensitive", new ModuleName("some/name"), name);
	}
}
