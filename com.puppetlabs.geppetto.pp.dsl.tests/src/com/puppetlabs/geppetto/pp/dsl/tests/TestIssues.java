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
package org.cloudsmith.geppetto.pp.dsl.tests;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.junit4.validation.AssertableDiagnostics;
import org.eclipse.xtext.resource.XtextResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

/**
 * Tests specific to reported issues.
 * 
 */
public class TestIssues extends AbstractPuppetTests {

	private PrintStream savedOut;

	/**
	 * Sends System.out to dev/null since there are many warnings about unknown variables (ignored unless
	 * explicitly tested for).
	 */
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

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		System.setOut(savedOut);
	}

	@Test
	public void test_inheritFromParameterizedClass_issue381() throws Exception {
		String code = "class base($basevar) {} class derived inherits base {}";
		Resource r = loadAndLinkSingleResource(code);

		tester.validate(r.getContents().get(0)).assertOK();
		resourceErrorDiagnostics(r).assertDiagnostic(IPPDiagnostics.ISSUE__INHERITANCE_WITH_PARAMETERS);
		resourceWarningDiagnostics(r).assertOK();
	}

	/**
	 * [11] Geppetto does not yet know about parameterized classes
	 * https://github.com/cloudsmith/geppetto/issues#issue/11
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_Issue_11() throws Exception {
		String code = "class xxx($p) {} class { 'xxx':\n" + //
				"p => '666',\n" + //
				"}\n";
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
	}

	/**
	 * [4] Parser does not understand realize on multiple lines
	 * https://github.com/cloudsmith/geppetto/issues#issue/4
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_Issue_4() throws Exception {
		String code = "$confdir = 'x' \n" + //
				"realize (\n" + //
				"File[\"$confdir/ping/amazon.cfg\"],\n" + //
				"File[\"$confdir/ping/amazon.cfg\"],\n" + //
				"File[\"$confdir/ping/amazon.cfg\"],\n" + //
				")";
		XtextResource r = getResourceFromString(code);
		tester.validate(r.getContents().get(0)).assertOK();
	}

	/**
	 * [206] single char class name and inheritance results in things not being found
	 * An unqualified reference to an inherited variable should produce no errors or warnings.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_Issue206() throws Exception {
		String code = "class a {\n" + //
				"$x = 10\n" + //
				"class bb inherits a {\n" + //
				"$ref = $x\n" + //
				"}\n" + //
				"}\n"; //
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	@Test
	public void test_Issue399() throws Exception {
		String code = "exec { 'something': unless => false }";
		// URI targetURI = URI.createPlatformPluginURI("/org.cloudsmith.geppetto.pp.dsl/targets/puppet-3.0.0.pptp", true);
		Resource r = loadAndLinkSingleResource(code, true);
		tester.validate(r.getContents().get(0)).assertOK();
		resourceWarningDiagnostics(r).assertOK();
		resourceErrorDiagnostics(r).assertOK();
	}

	@Test
	public void test_Issue400() throws Exception {
		ImmutableList<String> source = ImmutableList.of("notify { [a, b, c]:", //
			"}", //
			"$var = Notify[a]", //
			"$var -> case 'x' {", "  'x' : {", //
			"    notify { d:", //
			"    }", //
			"  }", //
			"} ~> 'x' ? {", //
			"  'y'     => Notify[b],", //
			"  default => Notify[c]", //
			"}\n");
		String code = Joiner.on("\n").join(source).toString();
		Resource r = loadAndLinkSingleResource(code);
		AssertableDiagnostics asserter = tester.validate(r.getContents().get(0));
		asserter.assertAny(AssertableDiagnostics.errorCode(IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION));

	}

	@Test
	public void test_Issue403() throws Exception {
		String code = "class foo(a) { }";
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertWarning(IPPDiagnostics.ISSUE__NOT_VARNAME);
	}

	@Test
	public void test_Issue405() throws Exception {
		String code = "$x = '' $y = ${x}";
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertError(IPPDiagnostics.ISSUE__UNQUOTED_INTERPOLATION);
	}

	@Test
	public void test_Issue407_falsePositive() throws Exception {
		String code = "class foo { }";
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertOK();
	}

	@Test
	public void test_Issue407_main() throws Exception {
		String code = "class main { }";
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertError(IPPDiagnostics.ISSUE__RESERVED_NAME);
	}

	@Test
	public void test_Issue407_settings() throws Exception {
		String code = "class settings { }";
		Resource r = loadAndLinkSingleResource(code);
		tester.validate(r.getContents().get(0)).assertError(IPPDiagnostics.ISSUE__RESERVED_NAME);
	}

	@Test
	public void test_Issue435_paddingDqString() throws Exception {
		String code = "$a = true ? {\n" + //
				"\"something\" => 'dba',\n" + //
				"default => ''\n" + //
				"}\n";
		ImmutableList<String> formatted = ImmutableList.of("$a = true ? {", //
			"  \"something\" => 'dba',",//
			"  default     => ''", //
			"}\n");

		String fmt = Joiner.on("\n").join(formatted).toString();

		Resource r = loadAndLinkSingleResource(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals(fmt, s);

	}
}
