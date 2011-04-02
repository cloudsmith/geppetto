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

import junit.framework.TestCase;

import org.cloudsmith.geppetto.pp.dsl.pptp.IPPTP;
import org.cloudsmith.geppetto.pp.dsl.pptp.PPTPManager;
import org.cloudsmith.geppetto.pp.pptp.Type;

import com.google.common.collect.Iterators;

/**
 * Low level testing of PPTPManager
 * 
 */
public class PPTPManagerTests extends TestCase {
	private IPPTP PPTP = new PPTPManager();

	public void testDefaultTargetFunctions() {
		assertEquals("Should have found 21 functions", 21, Iterators.size(PPTP.functions()));
	}

	public void testDefaultTargetTypes() {
		assertEquals("Should have found 32 types", 32, Iterators.size(PPTP.types()));
	}

	public void testFileBucketType() {
		Type filebucket = PPTP.findType("filebucket");
		assertNotNull("Should have found type 'filebucket", filebucket);
		assertEquals("Should have 4 parameters", 4, filebucket.getParameters().size());
		assertEquals("Should find parameter 'name'", "name", PPTP.findParameter(filebucket, "name").getName());
		assertEquals("Should find parameter 'server'", "server", PPTP.findParameter(filebucket, "server").getName());
		assertEquals("Should find parameter 'port'", "port", PPTP.findParameter(filebucket, "port").getName());
		assertEquals("Should find parameter 'path'", "path", PPTP.findParameter(filebucket, "path").getName());
		assertEquals("Should have no properties", 0, filebucket.getProperties().size());
	}

	public void testFindFunction() {
		assertEquals("There should be a function called 'fail'", "fail", PPTP.findFunction("fail").getName());
	}

	public void testFindType() {
		assertEquals("There should be a type called 'mount'", "mount", PPTP.findType("mount").getName());
		assertEquals("There should be a type found searching for 'Mount'", "mount", PPTP.findType("Mount").getName());
		assertNull("There is no type called 'idiot'", PPTP.findType("idiot"));
	}

	public void testFunctionStartsWith() {
		assertEquals(
			"There should be 3 functions starting with 't'", 3, Iterators.size(PPTP.functionsStartingWith("t")));
		assertEquals(
			"There should be 2 functions starting with 'ta'", 2, Iterators.size(PPTP.functionsStartingWith("ta")));
		assertEquals(
			"There should be 1 function starting with 'tagg'", 1, Iterators.size(PPTP.functionsStartingWith("tagg")));
		assertEquals(
			"There should be 0 functions starting with 'tagx'", 0, Iterators.size(PPTP.functionsStartingWith("tagx")));

	}

	public void testTypeStartsWith() {
		assertEquals("There should be 5 types starting with 'm'", 5, Iterators.size(PPTP.typesStartingWith("m")));
		assertEquals("There should be 3 types starting with 'ma'", 3, Iterators.size(PPTP.typesStartingWith("ma")));
		assertEquals("There should be 2 types starting with 'mail'", 2, Iterators.size(PPTP.typesStartingWith("mail")));
		assertEquals("There should be 1 type starting with 'maila'", 1, Iterators.size(PPTP.typesStartingWith("maila")));
		assertEquals(
			"There should be 0 types starting with 'mailax'", 0, Iterators.size(PPTP.typesStartingWith("mailax")));
	}
}
