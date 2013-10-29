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

import org.junit.Test;

import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.util.ModuleUtils;

public class ModuleUtilsTest extends AbstractForgeTest {

	@Test
	public void loadModule() {
		try {
			Metadata md = getForgeUtil().createFromModuleDirectory(
				getTestData("puppetlabs-apache"), false, null, null, new Diagnostic());
			assertEquals("Unexpected module name", ModuleName.fromString("puppetlabs-apache"), md.getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void loadModule2() {
		try {
			Metadata md = getForgeUtil().createFromModuleDirectory(
				getTestData("DavidSchmitt-collectd"), false, null, null, new Diagnostic());
			assertEquals("Unexpected module name", ModuleName.fromString("davidschmitt-collectd"), md.getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void loadModule3() {
		try {
			Metadata md = getForgeUtil().createFromModuleDirectory(
				getTestData("bobsh-iptables"), false, null, null, new Diagnostic());
			assertEquals("Unexpected module name", ModuleName.fromString("bobsh/iptables"), md.getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void loadModule4() {
		try {
			Metadata md = getForgeUtil().createFromModuleDirectory(
				getTestData("ghoneycutt-rsync"), false, null, null, new Diagnostic());
			assertEquals("Unexpected module name", ModuleName.fromString("ghoneycutt-RSync"), md.getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void loadModule5() {
		try {
			Metadata md = getForgeUtil().createFromModuleDirectory(
				getTestData("lab42-common"), false, null, null, new Diagnostic());
			assertEquals("Unexpected module name", ModuleName.fromString("lab42-common"), md.getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void parseModuleFile__File() {
		try {
			Metadata md = new Metadata();
			ModuleUtils.parseModulefile(getTestData("puppetlabs-apache/Modulefile"), md, new Diagnostic());
			assertEquals("Unexpected module name", ModuleName.fromString("puppetlabs-apache"), md.getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

}
