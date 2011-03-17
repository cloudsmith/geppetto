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

import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.dsl.formatting.PPFormatter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.eclipse.xtext.resource.XtextResource;

/**
 * Tests for expressions not covered by separate test classes.
 * 
 */
public class TestFailingSerialization extends AbstractPuppetTests {
	// @formatter:off
	static final String Sample_Relationship = "file {\n" + //
			"\t'file1' :\n" + //
			"} -> file {\n" + //
			"\t'file2' :\n" + //
			"} -> file {\n" + //
			"\t'file3' :\n" + //
			"}";

	static final String Sample_Assignment1 = "$x = false";

	static final String Sample_Assignment2 = "$x[a] = false";

	static final String Sample_Append = "$x += false";

	static final String Sample_Match1 = "$x =~ /[a-z]*/";

	static final String Sample_Match2 = "$x !~ /[a-z]*/";

	static final String Sample_ClassDefinition = "class testClass {\n}";

	static final String Sample_If = //
	"if $a == 1 {\n" + //
			"\ttrue\n" + //
			"}\n" + //
			"else {\n" + //
			"\tfalse\n" + //
			"}\n" + //
			"if $a == 1 {\n" + //
			"\ttrue\n" + //
			"}\n" + //
			"elsif $b < -3 {\n" + //
			"\tfalse\n" + //
			"}\n" + //
			"else {\n" + //
			"\ttrue\n" + //
			"}";

	// @formatter:on

	private String doubleQuote(String s) {
		return '"' + s + '"';
	}

	/**
	 * No matter how formatter tries to add linewrapping there is none in the formatted result.
	 * 
	 * @see PPFormatter#assignmentExpressionConfiguration(FormattingConfig c)
	 */
	public void test_Serialize_AssignmentExpression() throws Exception {
		String code = "$a = 1\n$b = 2";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce specified result", code, s);
	}

	/**
	 * No matter how formatter tries to add linewrapping there is none in the formatted result.
	 * 
	 * @see PPFormatter#functionCallConfiguration(FormattingConfig c)
	 */
	public void test_Serialize_CallAndDefine() throws Exception {
		String code = "$a = include('a')\ndefine a {\n}";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce specified result", code, s);
	}

	/**
	 * Due to issues in the formatter, this test may hit a bug that inserts whitespace
	 * between quotes and string - no workaround found - needs to be fixed in Xtext formatter.
	 * Also see {@link #test_Serialize_DoubleQuotedString_2()}
	 * 
	 * Problem caused by validation producing a warning that a single String is not a top level
	 * expression. (Not reasonable to screw up formatting though).
	 * 
	 * @see #test_Serialize_DoubleQuotedString_2() for a non failing tests.
	 * 
	 * 
	 * @throws Exception
	 */
	public void test_Serialize_DoubleQuotedString_1() throws Exception {
		String original = "before${var}/after${1+2}$$${$var}";
		String code = doubleQuote(original);
		XtextResource r = getResourceFromString(code);
		EObject result = r.getContents().get(0);
		assertTrue("Should be a PuppetManifest", result instanceof PuppetManifest);
		result = ((PuppetManifest) result).getStatements().get(0);

		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("Serialization of interpolated string should produce same result", code, s);
	}

	/**
	 * This serialization does not fail.
	 * 
	 * @see #test_Serialize_DoubleQuotedString_1() for failing variant
	 */
	public void test_Serialize_DoubleQuotedString_2() throws Exception {
		String original = "before${var}/after${1+2}$$${$var}";
		String code = "$a = " + doubleQuote(original);
		XtextResource r = getResourceFromString(code);
		EObject result = r.getContents().get(0);
		assertTrue("Should be a PuppetManifest", result instanceof PuppetManifest);

		String s = serialize(r.getContents().get(0));
		assertEquals("Serialization of interpolated string should produce same result", code, s);
	}

	/**
	 * Formatter seems to not switch back to non hidden state after import "".
	 * If changed to '' string it behaves differently.
	 * 
	 */
	public void test_Serialize_DqStringFollowedByDefine() throws Exception {
		String code = "import \"foo\"\ndefine b {\n\t$a = 1\n}";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce specified result", code, s);
	}

	/**
	 * Test that serialization without formatting does not alter the input.
	 * Broken in Xtext 2.0 - produces formatted result, should leave string alone
	 */
	public void test_Serialize_IfExpression1() throws Exception {
		String code = "if$a==1{true}else{false}if$a==1{true}elsif$b< -3{false}else{true}";
		XtextResource r = getResourceFromString(code);
		String s = serialize(r.getContents().get(0));

		// Broken in Xtext 2.0 - produces formatted result, should leave string alone
		assertEquals("serialization should produce same result as input", code, s);
	}

	/**
	 * This serialization (with formatting produces the correct result).
	 * 
	 * @see #test_Serialize_IfExpression1()
	 */
	public void test_Serialize_IfExpression2() throws Exception {
		String code = "if$a==1{true}else{false}if$a==1{true}elsif$b< -3{false}else{true}";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce specified result", Sample_If, s);
	}

	/**
	 * No matter how formatter tries to add linewrapping there is none in the formatted result.
	 * 
	 * @see PPFormatter#importExpressionConfiguration(FormattingConfig c)
	 * @see #test_Serialize_ImportExpression2() - for different failing result
	 */
	public void test_Serialize_ImportExpression1() throws Exception {
		String code = "import \"a\"\nimport \"b\"";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce specified result", code, s);
	}

	/**
	 * No matter how formatter tries to add linewrapping there is none in the formatted result.
	 * Note that result is different than in {@link #test_Serialize_ImportExpression1()} due to issue
	 * with the different use of hidden() for DQ string.
	 * 
	 * @see PPFormatter#importExpressionConfiguration(FormattingConfig c)
	 * @see #test_Serialize_ImportExpression1() - for different failing result
	 */
	public void test_Serialize_ImportExpression2() throws Exception {
		String code = "import 'a'\nimport 'b'";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce specified result", code, s);
	}

	/**
	 * Should add linewrap between the two expressions.
	 * (This rule should trigger for tests like {@link #test_Serialize_AssignmentExpression()} BTW).
	 * 
	 * @see PPFormatter#manifestConfiguration(FormattingConfig c)
	 * @throws Exception
	 */
	public void test_Serialize_ManifestStatements() throws Exception {
		String code = "include 'a'\na";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("serialization should produce specified result", code, s);
	}
}
