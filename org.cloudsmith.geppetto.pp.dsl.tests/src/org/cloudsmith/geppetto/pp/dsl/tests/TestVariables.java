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
import org.cloudsmith.geppetto.pp.dsl.validation.PPPatternHelper;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.junit.validation.AssertableDiagnostics;
import org.eclipse.xtext.resource.XtextResource;

import com.google.common.collect.Lists;

/**
 * Test validation/linking of variables.
 * 
 */
public class TestVariables extends AbstractPuppetTests implements AbstractPuppetTests.SerializationTestControl {

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

	@Override
	public boolean shouldTestSerializer(XtextResource resource) {
		// The serializer validator screws up when optional content is always inserted by serializer
		return false;
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

	public void test_assignmentToDecVarNotAllowed() throws Exception {
		// not allowed
		String code = "$0 = 10"; //
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertError(IPPDiagnostics.ISSUE__ASSIGNMENT_DECIMAL_VAR);

		// allowed, not decimal
		code = "$01 = 10"; //
		r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertOK();
	}

	public void test_decimalDollarVariables_notOk() throws Exception {
		// if
		String code = "if 'abc' == 'abc' {\n" + //
				"notice(\"$1\")" + //
				"}\n"; //
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__UNKNOWN_REGEXP);
		resourceErrorDiagnostics(r).assertOK();

		// case
		code = "case 'abc' {\n" + //
				"abc:" + "{ notice(\"$1\") }" + //
				"}\n"; //
		r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__UNKNOWN_REGEXP);
		resourceErrorDiagnostics(r).assertOK();

		// selector
		code = "$a = 'abc' ? {\n" + //
				"'abc' =>" + "$1\n" + //
				"}\n"; //
		r = getResourceFromString(code);
		AssertableDiagnostics asserter = tester.validate(r.getContents().get(0));
		asserter.assertAll(AssertableDiagnostics.warningCode(IPPDiagnostics.ISSUE__MISSING_DEFAULT));
		resourceWarningDiagnostics(r).assertAll(
			AssertableResourceDiagnostics.diagnostic(IPPDiagnostics.ISSUE__UNKNOWN_REGEXP));
		resourceErrorDiagnostics(r).assertOK();
	}

	public void test_decimalDollarVariables_ok() throws Exception {
		// if
		String code = "if 'abc' =~ /a(b)c/ {\n" + //
				"notice(\"$1\")" + //
				"}\n"; //
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();

		// case
		code = "case 'abc' {\n" + //
				"/a(b)c/:" + "{ notice(\"$1\") }" + //
				"}\n"; //
		r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();

		// selector
		code = "$a = 'abc' ? {\n" + //
				"/a(b)c/ =>" + "$1\n" + //
				"}\n"; //
		r = getResourceFromString(code);
		AssertableDiagnostics asserter = tester.validate(r.getContents().get(0));
		asserter.assertAll(AssertableDiagnostics.warningCode(IPPDiagnostics.ISSUE__MISSING_DEFAULT));
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	public void test_decimalVariables_notOk() throws Exception {
		// if
		String code = "if 'abc' == 'abc' {\n" + //
				"notice(\"${1}\")" + //
				"}\n"; //
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__UNKNOWN_REGEXP);
		resourceErrorDiagnostics(r).assertOK();

		// case
		code = "case 'abc' {\n" + //
				"abc:" + "{ notice(\"${1}\") }" + //
				"}\n"; //
		r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__UNKNOWN_REGEXP);
		resourceErrorDiagnostics(r).assertOK();

		// selector
		code = "$a = 'abc' ? {\n" + //
				"'abc' =>" + "\"${1}\"\n" + //
				"}\n"; //
		r = getResourceFromString(code);
		AssertableDiagnostics asserter = tester.validate(r.getContents().get(0));
		asserter.assertAll(AssertableDiagnostics.warningCode(IPPDiagnostics.ISSUE__MISSING_DEFAULT));
		resourceWarningDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__UNKNOWN_REGEXP);
		resourceErrorDiagnostics(r).assertOK();
	}

	public void test_decimalVariables_ok() throws Exception {
		// if
		String code = "if 'abc' =~ /a(b)c/ {\n" + //
				"notice(\"${1}\")" + //
				"}\n"; //
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();

		// case
		code = "case 'abc' {\n" + //
				"/a(b)c/:" + "{ notice(\"${1}\") }" + //
				"}\n"; //
		r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();

		// selector
		code = "$a = 'abc' ? {\n" + //
				"/a(b)c/ =>" + "\"${1}\"\n" + //
				"}\n"; //
		r = getResourceFromString(code);
		AssertableDiagnostics asserter = tester.validate(r.getContents().get(0));
		asserter.assertAll(AssertableDiagnostics.warningCode(IPPDiagnostics.ISSUE__MISSING_DEFAULT));
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	public void test_hyphenNotOk() throws Exception {
		String code = "$a-b = 10";
		XtextResource r = getResourceFromString(code);

		AssertableDiagnostics asserter = tester.validate(r.getContents().get(0));
		asserter.assertAny(
			AssertableDiagnostics.errorCode(IPPDiagnostics.ISSUE__NOT_ASSIGNABLE),
			AssertableDiagnostics.errorCode(IPPDiagnostics.ISSUE__NOT_NUMERIC));
		resourceWarningDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__UNKNOWN_VARIABLE);
		resourceErrorDiagnostics(r).assertOK();
	}

	public void test_linking2RegexpVar() throws Exception {
		String code = "if 'abc' =~ /a(b)c/ { notice(\"$1\") } "; //
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
	}

	public void test_numericVariableDetection() throws Exception {
		PPPatternHelper patternHelper = new PPPatternHelper();
		for(int i = 0; i < 21; i++) {
			assertTrue("Should accept $" + i, patternHelper.isDECIMALVAR("$" + i));
			assertTrue("Should accept " + i, patternHelper.isDECIMALVAR("" + i));
		}
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
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__INHERITANCE_WITH_PARAMETERS);
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
		Resource r = loadAndLinkSingleResource(code);

		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__INHERITANCE_WITH_PARAMETERS);
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
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__UNKNOWN_VARIABLE);
		resourceErrorDiagnostics(r).assertOK();

	}

}
