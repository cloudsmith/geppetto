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
package org.cloudsmith.geppetto.pp.dsl.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.cloudsmith.geppetto.common.util.BundleAccess;
import org.cloudsmith.geppetto.pp.dsl.target.PptpResourceUtil;
import org.cloudsmith.geppetto.pp.dsl.target.PuppetTarget;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;

public class TestPptpResourceAsFile extends AbstractPuppetTests {

	void readXMLFromJarURL(URL url) throws IOException {
		URLConnection urlConnection = url.openConnection();
		assertTrue("URL is not a JarURL", urlConnection instanceof JarURLConnection);
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		try {
			String firstLine = reader.readLine();
			assertEquals("Unexpected first line", "<?xml version=\"1.0\"", firstLine.substring(0, 19));
		}
		finally {
			reader.close();
		}
	}

	@Test
	public void test_PptpResourceAsFile() {
		try {
			URI uri = PptpResourceUtil.getFacter_1_6();
			assertNotNull("Facter pptp URI is null", uri);
			URL url = new URL(uri.toString());
			if("jar".equals(uri.scheme()))
				readXMLFromJarURL(url);
			else
				assertNotNull("Facter pptp file is null", get(BundleAccess.class).getResourceAsFile(url));

			uri = PuppetTarget.PUPPET27.getPlatformURI();
			assertNotNull("Puppet pptp URI is null", uri);
			url = new URL(uri.toString());
			if("jar".equals(uri.scheme()))
				readXMLFromJarURL(url);
			else
				assertNotNull("Puppet pptp file is null", get(BundleAccess.class).getResourceAsFile(url));
		}
		catch(IOException e) {
			fail("Unable to obtain File that appoints facter pptp: " + e);
		}
	}
}
