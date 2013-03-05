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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.cloudsmith.geppetto.common.os.FileUtils;
import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.IncompleteException;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.forge.ModuleInfo;
import org.cloudsmith.geppetto.forge.ReleaseInfo;
import org.cloudsmith.geppetto.forge.impl.MetadataImpl;
import org.cloudsmith.geppetto.forge.util.TarUtils;
import org.junit.Before;
import org.junit.Test;

public class ForgeTest {

	private static void assertExcludesMatch(String name) {
		assertTrue(
			"The name '" + name + "' does not match default excludes pattern",
			MetadataImpl.DEFAULT_EXCLUDES_PATTERN.matcher(name).matches());
	}

	private static void assertNotExcludesMatch(String name) {
		assertFalse(
			"The name '" + name + "' matches default excludes pattern",
			MetadataImpl.DEFAULT_EXCLUDES_PATTERN.matcher(name).matches());
	}

	private Forge fixture = null;

	@Before
	public void setUp() throws Exception {
		ForgeService forgeService = ForgeFactory.eINSTANCE.createForgeService();
		fixture = forgeService.createForge(
			URI.create("http://forge.puppetlabs.com"), ForgeTests.getTestOutputFolder("cache", false));
	}

	@Test
	public void testBuild__File_File() {
		try {
			File installFolder = ForgeTests.getTestOutputFolder("apache-install-here", true);
			File resultFolder = ForgeTests.getTestOutputFolder("apache-build-result", true);
			FileUtils.cpR(
				Activator.getTestData("puppetlabs-apache"), installFolder, FileUtils.DEFAULT_EXCLUDES, false, true);
			Metadata md = fixture.build(installFolder, resultFolder, null);
			String archiveName = md.getFullName() + '-' + md.getVersion();
			File builtArchive = new File(resultFolder, archiveName + ".tar.gz");
			assertTrue("Build did not build any archive", builtArchive.canRead());
			File unpackFolder = ForgeTests.getTestOutputFolder("apache-unpack-result", true);
			InputStream input = new GZIPInputStream(new FileInputStream(builtArchive));
			try {
				TarUtils.unpack(input, unpackFolder, false);
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
		catch(IncompleteException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testChanges__File() {
		try {
			File installFolder = ForgeTests.getTestOutputFolder("test-changes", true);
			File resultFolder = ForgeTests.getTestOutputFolder("test-changes-result", true);
			FileUtils.cpR(
				Activator.getTestData("puppetlabs-apache"), installFolder, FileUtils.DEFAULT_EXCLUDES, false, true);
			fixture.build(installFolder, resultFolder, null);
			List<File> changes = fixture.changes(installFolder, null);
			assertTrue("Unexpected changes", changes.isEmpty());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
		catch(IncompleteException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testExcludesPattern() {
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
	public void testGenerate__File_Metadata() {
		try {
			ForgeService service = ForgeFactory.eINSTANCE.createForgeService();
			Metadata metadata = service.createMetadata("cloudsmith/testmodule");
			File installFolder = ForgeTests.getTestOutputFolder("testmodule-install", true);
			installFolder.delete();
			fixture.generate(installFolder, metadata);
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGetRelease__String() {
		try {
			ReleaseInfo info = fixture.getRelease("puppetlabs-apache");
			assertTrue("Unexpeced file", info.getFile().startsWith("/system/releases/p/puppetlabs/puppetlabs-apache-"));
			assertNotNull(info.getVersion());
		}
		catch(FileNotFoundException e) {
			fail("No release found matching 'puppetlabs-apache'");
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testInstall__String_File_boolean_boolean() {
		try {
			File installFolder = ForgeTests.getTestOutputFolder("stdlib-install", true);
			fixture.install("puppetlabs/stdlib", installFolder, false, true);
			File found = new File(installFolder, "stdlib");
			assertTrue("Installation did not produce the expected result", found.isDirectory());
		}
		catch(IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testSearch__String() {
		try {
			List<ModuleInfo> hits = fixture.search("rsync");
			assertFalse("No modules found matching 'rsync'", hits.isEmpty());
			for(ModuleInfo mi : hits)
				System.out.println(mi);
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

}
