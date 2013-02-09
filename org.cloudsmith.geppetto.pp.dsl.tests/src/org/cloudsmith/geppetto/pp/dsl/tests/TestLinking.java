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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test validation/linking of variables.
 * 
 */
public class TestLinking extends AbstractPuppetTests {

	private PrintStream savedOut;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		savedOut = System.out;
		OutputStream sink = new OutputStream() {

			@Override
			public void write(int arg0) throws IOException {
				// do nothing
			}

		};
		System.setOut(new PrintStream(sink));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.junit.AbstractXtextTests#tearDown()
	 */
	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		System.setOut(savedOut);
	}

	/**
	 * A reference to a variable created with += should produce no errors or warnings
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_appendShouldCreateVariable() throws Exception {
		String code1 = "$arr2 = ['a']\n" + //
				"class foo {\n" + //
				"$arr2 += ['b', 'c']\n" + //
				"}\n";

		String code2 = "$arr = $foo::arr2\n"; //

		// XtextResource r = getResourceFromString(code);
		List<Resource> resources = loadAndLinkResources(code1, code2);
		Resource r = resources.get(0);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();

		r = resources.get(1);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	/**
	 * An unqualified reference to an inherited variable should produce no errors or warnings.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_link_A_Inherits_B_andVar() throws Exception {
		String code1 = "class a {\n" + //
				"$x = 10\n" + //
				"}\n";

		String code2 = "class b inherits a {\n" + //
				"$ref = $x\n" + //
				"}\n"; //
		;
		// XtextResource r = getResourceFromString(code);
		List<Resource> resources = loadAndLinkResources(code1, code2);
		Resource r = resources.get(0);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();

		r = resources.get(1);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

}
