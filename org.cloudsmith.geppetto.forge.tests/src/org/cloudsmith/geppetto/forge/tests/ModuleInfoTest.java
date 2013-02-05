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

import java.net.URI;

import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ModuleInfo;
import org.cloudsmith.geppetto.forge.impl.ModuleInfoImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ModuleInfoTest {

	private ModuleInfo fixture = null;

	@Before
	public void setUp() throws Exception {
		fixture = ForgeFactory.eINSTANCE.createModuleInfo();
	}

	@Test
	public void testJSonSerialization() throws Exception {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		fixture.setFullName("A full name");
		fixture.setName("aName");
		fixture.setProjectURL(URI.create("http://www.example.com"));
		fixture.setVersion("0.0.1");
		String json = gson.toJson(fixture);
		assertEquals(
			"{\"full_name\":\"A full name\",\"name\":\"aName\",\"project_url\":\"http://www.example.com\",\"version\":\"0.0.1\"}",
			json);
		ModuleInfo mi = gson.fromJson(json, ModuleInfoImpl.class);
		assertTrue(EcoreUtil.equals(fixture, mi));
	}
}
