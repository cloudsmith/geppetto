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

import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.XtextResource;

import com.google.common.collect.Lists;

/**
 * Test validation/linking of variables.
 * 
 */
public class TestVariables extends AbstractPuppetTests {

	private PrintStream savedOut;

	@Override
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
	protected void tearDown() throws Exception {
		super.tearDown();
		System.setOut(savedOut);
	}

	/**
	 * An qualified reference to a global variable should produce no warning/error
	 * (the existence of all global variables can not be computed for a given runtime).
	 * 
	 * @throws Exception
	 */
	public void test_variable_CGx_Gy() throws Exception {
		URI uri = makeManifestURI(1);
		initializeResourceSet(Lists.newArrayList(uri));
		String code = "$y = 10\n" + //
				"$ref = $::x\n" //
		;
		Resource r = loadResource(code, uri);
		resolveCrossReferences(r);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	/**
	 * An qualified reference to a global variable should produce no errors or warnings.
	 * 
	 * @throws Exception
	 */
	public void test_variable_class_CGx_Gy() throws Exception {
		String code = "class a {\n" + //
				"$ref = $::x\n" + //
				"}\n"; //
		;
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	/**
	 * Reference to existing global variable should work, no errors or warnings.
	 * 
	 * @throws Exception
	 */
	public void test_variable_Gx_Gx() throws Exception {
		String code = "$x = 10\n" + //
				"$ref = $x\n" //
		;
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	/**
	 * An unqualified reference to a global variable should produce warning/error.
	 * (In 2.7 (the default), it should be a warning).
	 * 
	 * @throws Exception
	 */
	public void test_variable_Gx_Gy() throws Exception {
		String code = "$y = 10\n" + //
				"$ref = $x\n" //
		;
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__UNKNOWN_VARIABLE);
		resourceErrorDiagnostics(r).assertOK();
	}

	/**
	 * An unqualified reference to an inherited class parameter variable should produce no errors or warnings.
	 * 
	 * @throws Exception
	 */
	public void test_variable_Lx_IIPx() throws Exception {
		String code = "class aa($x=10) {\n" + //
				"class bb inherits aa {\n" + //
				"class cc inherits bb {\n" + //
				"$ref = $x\n" + //
				"}\n" + //
				"}\n" + //
				"}\n"; //
		;
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	/**
	 * An unqualified reference to an inherited variable should produce no errors or warnings.
	 * 
	 * @throws Exception
	 */
	public void test_variable_Lx_IIVx() throws Exception {
		String code = "class aa {\n" + //
				"$x = 10\n" + //
				"class bb inherits aa {\n" + //
				"class cc inherits bb {\n" + //
				"$ref = $x\n" + //
				"}\n" + //
				"}\n" + //
				"}\n"; //
		;
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	/**
	 * An unqualified reference to an inherited class parameter variable should produce no errors or warnings.
	 * 
	 * @throws Exception
	 */
	public void test_variable_Lx_IPx() throws Exception {
		String code = "class aa($x=10) {\n" + //
				"class bb inherits aa {\n" + //
				"$ref = $x\n" + //
				"}\n" + //
				"}\n"; //
		;
		XtextResource r = getResourceFromString(code);

		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	/**
	 * An unqualified reference to an inherited variable should produce no errors or warnings.
	 * 
	 * @throws Exception
	 */
	public void test_variable_Lx_IVx() throws Exception {
		String code = "class aa {\n" + //
				"$x = 10\n" + //
				"class bb inherits aa {\n" + //
				"$ref = $x\n" + //
				"}\n" + //
				"}\n"; //
		;
		// XtextResource r = getResourceFromString(code);
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	/**
	 * An unqualified reference to a local variable should produce no errors or warnings.
	 * 
	 * @throws Exception
	 */
	public void test_variable_Lx_Lx() throws Exception {
		String code = "class a {\n" + //
				"$x = 10\n" + //
				"$ref = $x\n" + //
				"}\n"; //
		;
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	/**
	 * An unqualified reference to a variable in outer scope should produce warning/error.
	 * (Warning in 2.7 (default)).
	 * 
	 * @throws Exception
	 */
	public void test_variable_Lx_OVx() throws Exception {
		String code = "class a {\n" + //
				"$x = 10\n" + //
				"class b {\n" + //
				"$ref = $x\n" + //
				"}\n" + //
				"}\n"; //
		;
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__UNKNOWN_VARIABLE);
		resourceErrorDiagnostics(r).assertOK();

	}
}
