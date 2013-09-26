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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.zip.GZIPInputStream;

import org.junit.Before;
import org.junit.Test;

import com.puppetlabs.geppetto.common.os.FileUtils;
import com.puppetlabs.geppetto.common.os.StreamUtil;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.util.ModuleUtils;
import com.puppetlabs.geppetto.forge.util.TarUtils;

public class ForgeUtilTest extends AbstractForgeTest {

	private static void assertExcludesMatch(String name) {
		assertTrue(
			"The name '" + name + "' does not match default excludes pattern",
			ModuleUtils.DEFAULT_EXCLUDES_PATTERN.matcher(name).matches());
	}

	private static void assertNotExcludesMatch(String name) {
		assertFalse(
			"The name '" + name + "' matches default excludes pattern",
			ModuleUtils.DEFAULT_EXCLUDES_PATTERN.matcher(name).matches());
	}

	private Forge fixture = null;

	@Test
	public void build() {
		try {
			File installFolder = getTestOutputFolder("apache-install-here", true);
			File resultFolder = getTestOutputFolder("apache-build-result", true);
			FileUtils.cpR(getTestData("puppetlabs-apache"), installFolder, ModuleUtils.DEFAULT_FILE_FILTER, false, true);
			Metadata[] mdHandle = new Metadata[1];
			fixture.build(installFolder, resultFolder, null, mdHandle, null, new Diagnostic());
			Metadata md = mdHandle[0];
			String archiveName = md.getName().toString() + '-' + md.getVersion();
			File builtArchive = new File(resultFolder, archiveName + ".tar.gz");
			assertTrue("Build did not build any archive", builtArchive.canRead());
			File unpackFolder = getTestOutputFolder("apache-unpack-result", true);
			InputStream input = new GZIPInputStream(new FileInputStream(builtArchive));
			try {
				TarUtils.unpack(input, unpackFolder, false, null);
			}
			finally {
				StreamUtil.close(input);
			}
			assertTrue(
				"Archive doesn't contain the expected top folder", new File(unpackFolder, archiveName).isDirectory());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void changes() {
		try {
			File installFolder = getTestOutputFolder("test-changes", true);
			File resultFolder = getTestOutputFolder("test-changes-result", true);
			FileUtils.cpR(getTestData("puppetlabs-apache"), installFolder, ModuleUtils.DEFAULT_FILE_FILTER, false, true);
			fixture.build(installFolder, resultFolder, null, null, null, new Diagnostic());
			Collection<File> changes = fixture.changes(installFolder, null);
			assertTrue("Unexpected changes", changes.isEmpty());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void excludesPattern() {
		assertExcludesMatch("~");
		assertExcludesMatch("backup~");
		assertExcludesMatch("~backup~");
		assertExcludesMatch("._");
		assertExcludesMatch("%%");
		assertExcludesMatch("%percent%");
		assertExcludesMatch("._foo");
		assertExcludesMatch("CVS");
		assertExcludesMatch(".cvsignore");
		assertExcludesMatch("SCCS");
		assertExcludesMatch(".git");
		assertExcludesMatch(".gitignore");
		assertExcludesMatch(".gitmodules");
		assertExcludesMatch(".gitattributes");
		assertExcludesMatch(".hg");
		assertExcludesMatch(".hgignore");
		assertExcludesMatch(".hgsubstate");
		assertExcludesMatch(".hgtags");
		assertExcludesMatch(".bzr");
		assertExcludesMatch(".bzrignore");
		assertExcludesMatch("pkg");
		assertExcludesMatch("coverage");

		assertNotExcludesMatch(".");
		assertNotExcludesMatch("..");
		assertNotExcludesMatch(".profile");
		assertNotExcludesMatch("repo.git");
		assertNotExcludesMatch(".gitignore.save");
		assertNotExcludesMatch("~tilde");
		assertNotExcludesMatch("%percent");
		assertNotExcludesMatch("percent%");
		assertNotExcludesMatch("%%double");
		assertNotExcludesMatch("double%%");
	}

	@Test
	public void generate() {
		try {
			Metadata metadata = new Metadata();
			metadata.setName(new ModuleName("cloudsmith/testmodule"));
			File installFolder = getTestOutputFolder("testmodule-install", true);
			installFolder.delete();
			fixture.generate(installFolder, metadata);
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Before
	public void setUp() throws Exception {
		fixture = getForgeUtil();
	}

}
