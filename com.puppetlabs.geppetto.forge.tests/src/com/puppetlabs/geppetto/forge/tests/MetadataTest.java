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

import static com.puppetlabs.geppetto.forge.Forge.METADATA_JSON_NAME;
import static com.puppetlabs.geppetto.forge.Forge.MODULEFILE_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.util.Checksums;
import com.puppetlabs.geppetto.forge.util.ModuleUtils;
import com.puppetlabs.geppetto.forge.util.Types;
import com.puppetlabs.geppetto.forge.v2.model.Metadata;
import com.puppetlabs.geppetto.forge.v2.model.Type;

public class MetadataTest extends AbstractForgeTest {

	private static void assertChecksumsEqual(Map<String, byte[]> a, Map<String, byte[]> b) {
		if(a == b)
			return;

		if(a == null || b == null)
			fail("One of the checksum maps are not provided");

		assertEquals("Maps size differ", a.size(), b.size());

		for(Map.Entry<String, byte[]> entry : a.entrySet()) {
			byte[] bb = b.get(entry.getKey());
			assertNotNull("checksum entry not in both maps", bb);
			assertTrue("checksums differ", Arrays.equals(entry.getValue(), bb));
		}
	}

	private static Object countLines(String str) {
		if(str == null || str.length() == 0)
			return null;
		int cnt = 1;
		int idx = str.length();
		while(--idx >= 0)
			if(str.charAt(idx) == '\n')
				cnt++;
		return cnt;
	}

	private Metadata fixture = new Metadata();

	private void assertMetadataEquals(Metadata metadata) {
		assertNotNull(metadata);
		assertEquals(fixture.getAuthor(), metadata.getAuthor());
		assertEquals(fixture.getDependencies(), metadata.getDependencies());
		assertEquals(fixture.getDescription(), metadata.getDescription());
		assertEquals(fixture.getLicense(), metadata.getLicense());
		assertEquals(fixture.getName(), metadata.getName());
		assertEquals(fixture.getProjectPage(), metadata.getProjectPage());
		assertEquals(fixture.getSource(), metadata.getSource());
		assertEquals(fixture.getSummary(), metadata.getSummary());
		assertEquals(fixture.getTypes(), metadata.getTypes());
		assertEquals(fixture.getVersion(), metadata.getVersion());
		assertChecksumsEqual(fixture.getChecksums(), metadata.getChecksums());
	}

	private void performJsonSerialization(String module) {
		populateFromModule(module);
		Gson gson = getGson();
		String json1 = gson.toJson(fixture);
		Metadata mi = gson.fromJson(json1, Metadata.class);
		assertMetadataEquals(mi);
	}

	private void populateFromModule(String module) {
		try {
			fixture = getForgeUtil().createFromModuleDirectory(getTestData(module), true, null, null, new Diagnostic());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testJSonSerialization() throws Exception {
		performJsonSerialization("bobsh-iptables");
	}

	@Test
	public void testJSonSerialization2() throws Exception {
		performJsonSerialization("DavidSchmitt-collectd");
	}

	@Test
	public void testJSonSerialization3() throws Exception {
		performJsonSerialization("ghoneycutt-rsync");
	}

	@Test
	public void testJSonSerialization4() throws Exception {
		performJsonSerialization("lab42-common");
	}

	@Test
	public void testJSonSerialization5() throws Exception {
		performJsonSerialization("puppetlabs-apache");
	}

	@Test
	public void testLoadChecksums__File() {
		try {
			Map<String, byte[]> checksums = Checksums.loadChecksums(getTestData("puppetlabs-apache"), null);
			assertEquals("Incorrect number of checksums", 18, checksums.size());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testLoadTypeFiles__File() {
		try {
			List<Type> types = Types.loadTypes(getTestData("puppetlabs-apache/lib/puppet"), null);
			assertEquals("Expected 1 type", 1, types.size());
			assertEquals("Unexpected type name", "a2mod", types.get(0).getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSaveJSONMetadata__File() {
		try {
			File outputDir = getTestOutputFolder("json-ouput", true);
			populateFromModule("puppetlabs-apache");
			File jsonFile = new File(outputDir, METADATA_JSON_NAME);
			getForgeUtil().saveJSONMetadata(fixture, jsonFile);
			assertTrue("No readable metadata.json file was generated", jsonFile.canRead());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSaveModulefile__File() {
		try {
			File outputDir = getTestOutputFolder("modulefile-ouput", true);
			populateFromModule("ghoneycutt-rsync");
			File moduleFile = new File(outputDir, MODULEFILE_NAME);
			ModuleUtils.saveAsModulefile(fixture, moduleFile);
			assertTrue("No readable Modulefile file was generated", moduleFile.canRead());

			Metadata tst = new Metadata();
			ModuleUtils.parseModulefile(moduleFile, tst, new Diagnostic());
			assertEquals("Expected 2 dependencies", 2, tst.getDependencies().size());
			assertEquals("Expected 3 lines of text", 3, countLines(tst.getDescription()));
			assertEquals("Expected 4 lines of text", 4, countLines(tst.getSummary()));
			assertEquals("Expected 5 lines of text", 5, countLines(tst.getLicense()));
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}
}
