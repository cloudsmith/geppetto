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

import org.cloudsmith.geppetto.pp.DoubleQuotedString;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.ExpressionTE;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.ParenthesisedExpression;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.TextExpression;
import org.cloudsmith.geppetto.pp.VariableTE;
import org.cloudsmith.geppetto.pp.VerbatimTE;
import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.XtextResource;

/**
 * Tests Literals
 * 
 */
public class TestDoubleQuotedString extends AbstractPuppetTests {

	private PrintStream savedOut;

	private String doubleQuote(String s) {
		return '"' + s + '"';
	}

	private boolean isExprClass(TextExpression te, Class<?> clazz) {
		if(!(te instanceof ExpressionTE))
			return false;
		Expression pe = ((ExpressionTE) te).getExpression();
		if(!ParenthesisedExpression.class.isAssignableFrom(pe.getClass()))
			return false;
		Expression peExpr = ((ParenthesisedExpression) pe).getExpr();
		if(peExpr == null)
			return false;
		return clazz.isAssignableFrom(peExpr.getClass());
	}

	/**
	 * Sends System.out to dev/null since there are many warnings about unknown variables (ignored unless
	 * explicitly tested for).
	 */
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
	protected boolean shouldTestSerializer(XtextResource resource) {
		// NOTE: The serializer tester seems to screw up the comparison of strings
		return false;
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		System.setOut(savedOut);
	}

	public void test_Parse_DoubleQuotedString_Dollar() throws Exception {
		String original = "before$/after";
		String code = doubleQuote(original);
		XtextResource r = getResourceFromString(code);
		EObject result = r.getContents().get(0);
		assertTrue("Should be a PuppetManifest", result instanceof PuppetManifest);
		result = ((PuppetManifest) result).getStatements().get(0);
		assertTrue("Should be a DoubleQuotedString", result instanceof DoubleQuotedString);
		DoubleQuotedString string = (DoubleQuotedString) result;

		List<TextExpression> t = string.getStringPart();
		assertEquals("List should have 1 entry", 1, t.size());
		assertTrue("List should have an instance of VerbatimTE", t.get(0) instanceof VerbatimTE);
		assertEquals("First element should be 'before'", "before$/after", ((VerbatimTE) t.get(0)).getText());
	}

	public void test_Parse_DoubleQuotedString_DollarExprVar() throws Exception {
		String original = "before${var}/after";
		String code = doubleQuote(original);
		XtextResource r = getResourceFromString(code);
		EObject result = r.getContents().get(0);
		assertTrue("Should be a PuppetManifest", result instanceof PuppetManifest);
		result = ((PuppetManifest) result).getStatements().get(0);
		assertTrue("Should be a DoubleQuotedString", result instanceof DoubleQuotedString);
		DoubleQuotedString string = (DoubleQuotedString) result;

		List<TextExpression> t = string.getStringPart();
		assertEquals("List should have 3 entries", 3, t.size());
		assertTrue("List first entry should be VerbatimTE", t.get(0) instanceof VerbatimTE);
		assertEquals("First element should be 'before'", "before", ((VerbatimTE) t.get(0)).getText());

		assertTrue(
			"Second element should be a LiteralNameOrReference", isExprClass(t.get(1), LiteralNameOrReference.class));
		assertTrue("Third element should be VertimTE", t.get(2) instanceof VerbatimTE);
		assertEquals("Third element should be '/after'", "/after", ((VerbatimTE) t.get(2)).getText());

	}

	public void test_Parse_DoubleQuotedString_DollarVar() throws Exception {
		String original = "before$var/after";
		String code = doubleQuote(original);
		XtextResource r = getResourceFromString(code);
		EObject result = r.getContents().get(0);
		assertTrue("Should be a PuppetManifest", result instanceof PuppetManifest);
		result = ((PuppetManifest) result).getStatements().get(0);
		assertTrue("Should be a DoubleQuotedString", result instanceof DoubleQuotedString);
		DoubleQuotedString string = (DoubleQuotedString) result;

		List<TextExpression> t = string.getStringPart(); // flattenTextExpression(string.getTextExpression());
		assertEquals("List should have 3 entries", 3, t.size());
		assertEquals("First element should be 'before'", "before", ((VerbatimTE) t.get(0)).getText());
		assertTrue("Second element should be VariableTE", t.get(1) instanceof VariableTE);
		assertEquals("Second element should be '$var'", "$var", ((VariableTE) t.get(1)).getVarName());
		assertEquals("Third element should be '/after'", "/after", ((VerbatimTE) t.get(2)).getText());
	}

	public void test_Parse_DoubleQuotedString_Simple() throws Exception {
		String original = "This is a simple double quoted string";
		String code = doubleQuote(original);
		XtextResource r = getResourceFromString(code);
		EObject result = r.getContents().get(0);
		assertTrue("Should be a PuppetManifest", result instanceof PuppetManifest);
		result = ((PuppetManifest) result).getStatements().get(0);

		assertTrue("Should be a DoubleQuotedString", result instanceof DoubleQuotedString);

		DoubleQuotedString string = (DoubleQuotedString) result;
		List<TextExpression> te = string.getStringPart(); // string.getTextExpression();
		assertEquals("Should be one text expression", 1, te.size());
		assertTrue("Should be a single verbatim TE", te.get(0) instanceof VerbatimTE);
		assertEquals("Should contain the original", original, ((VerbatimTE) te.get(0)).getText());
	}

	/**
	 * Due to issues in the formatter, this test may hit a bug that inserts whitespace
	 * between quotes and string - no workaround found - needs to be fixed in Xtext formatter.
	 * Also see {@link #test_Serialize_DoubleQuotedString_2()}
	 * 
	 * @throws Exception
	 */
	public void test_Serialize_DoubleQuotedString_1() throws Exception {
		String original = "before${var}/after${1+2}$$${$var}";
		String formatted = doubleQuote("before${var}/after${1 + 2}$$${$var}");
		formatted += "\n";
		String code = doubleQuote(original);
		XtextResource r = getResourceFromString(code);
		EObject result = r.getContents().get(0);
		assertTrue("Should be a PuppetManifest", result instanceof PuppetManifest);
		result = ((PuppetManifest) result).getStatements().get(0);
		assertTrue("Should be a DoubleQuotedString", result instanceof DoubleQuotedString);

		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("Serialization of interpolated string should produce same result", formatted, s);
	}

	/**
	 * Due to issues in the formatter, this test may hit a bug that inserts whitespace
	 * between quotes and string - no workaround found - needs to be fixed in Xtext formatter.
	 * The bug seems to not appear when expression is in an expression that has visible tokens
	 * other than what is parsed... (In this test, the string is assigned to a variable).
	 * Also see {@link #test_Serialize_DoubleQuotedString_1()}
	 * 
	 * @throws Exception
	 */
	public void test_Serialize_DoubleQuotedString_2() throws Exception {
		String original = "before${var}/after${1+2}$$${$var}";
		String code = "$a = " + doubleQuote(original);
		String formatted = "$a = " + doubleQuote("before${var}/after${1 + 2}$$${$var}");
		formatted += "\n";
		XtextResource r = getResourceFromString(code);
		EObject result = r.getContents().get(0);
		assertTrue("Should be a PuppetManifest", result instanceof PuppetManifest);

		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("Serialization of interpolated string should produce same result", formatted, s);
	}

	/**
	 * Formatter seems to not switch back to non hidden state interpolation.
	 * 
	 */
	public void test_Serialize_DqStringInterpolation() throws Exception {
		String code = "$a = \"a${1}b\"\nclass a {\n}\n";
		String fmt = "$a = \"a${1}b\"\n\nclass a {\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		// System.out.println(NodeModelUtils.compactDump(r.getParseResult().getRootNode(), false));
		assertEquals("serialization should produce specified result", fmt, s);
	}

	/**
	 * Without interpolation formatting does the right thing.
	 */
	public void test_Serialize_DqStringNoInterpolation() throws Exception {
		String code = "$a = \"ab\"\nclass a {\n}\n";
		String fmt = "$a = \"ab\"\n\nclass a {\n}\n";
		XtextResource r = getResourceFromString(code);
		String s = serializeFormatted(r.getContents().get(0));
		// System.out.println(NodeModelUtils.compactDump(r.getParseResult().getRootNode(), false));

		assertEquals("serialization should produce specified result", fmt, s);
	}

	public void test_serializeSimpleDqString() throws Exception {
		String code = "$x = \"a${var}\"";
		XtextResource r = getResourceFromString(code);
		EObject result = r.getContents().get(0);
		String s = serializeFormatted(result);

		assertEquals("Serialization of interpolated string should produce same result", "$x = \"a${var}\"\n", s);

	}

	public void test_Validate_DoubleQuotedString_Ok() {
		DoubleQuotedString ls = pf.createDoubleQuotedString();
		VerbatimTE te = pf.createVerbatimTE();
		ls.getStringPart().add(te);
		te.setText("I am a single quoted string with a tab \\t char");
		tester.validator().checkVerbatimTextExpression(te);
		tester.diagnose().assertOK();

		// -- control char
		te.setText("I am a single quoted string with a tab \t");
		tester.validator().checkVerbatimTextExpression(te);
		tester.diagnose().assertOK();

		// -- new line
		te.setText("I am a single quoted string with a nl \n");
		tester.validator().checkVerbatimTextExpression(te);
		tester.diagnose().assertOK();

		// -- TODO: test NBSP

		// Unicode escapes are not supported as specific escapes as any
		// escaped character is the character itself - \u1234 is simply u1234
		// Should not produce an error or warning for sq string

		// -- unicode escape \\u [hexdigit]{4,4}
		te.setText("\\u1a2b");
		tester.validator().checkVerbatimTextExpression(te);
		tester.diagnose().assertWarning(IPPDiagnostics.ISSUE__UNRECOGNIZED_ESCAPE);

		// -- hex escape \x[hexdigit]{2,3} is not supported
		te.setText("\\x1a");
		tester.validator().checkVerbatimTextExpression(te);
		tester.diagnose().assertWarning(IPPDiagnostics.ISSUE__UNRECOGNIZED_ESCAPE);

		// -- octal escape \[0-7]{3,3}
		te.setText("\\777");
		tester.validator().checkVerbatimTextExpression(te);
		tester.diagnose().assertWarning(IPPDiagnostics.ISSUE__UNRECOGNIZED_ESCAPE);

		// -- meta escape \M-[sourcecharexceptNL]
		te.setText("\\M-A");
		tester.validator().checkVerbatimTextExpression(te);
		tester.diagnose().assertWarning(IPPDiagnostics.ISSUE__UNRECOGNIZED_ESCAPE);

		// -- control escape \c[sourcecharexceptNL] or \C-[sourcecharexceptNL]
		te.setText("\\C-J");
		tester.validator().checkVerbatimTextExpression(te);
		tester.diagnose().assertWarning(IPPDiagnostics.ISSUE__UNRECOGNIZED_ESCAPE);

		te.setText("\\cJ");
		tester.validator().checkVerbatimTextExpression(te);
		tester.diagnose().assertWarning(IPPDiagnostics.ISSUE__UNRECOGNIZED_ESCAPE);

		// -- escaped backslash and quotes as well as any escaped character
		te.setText("\\\\"); // i.e. '\\'
		tester.validator().checkVerbatimTextExpression(te);
		tester.diagnose().assertOK();

		te.setText("\\\""); // i.e. '\"'
		tester.validator().checkVerbatimTextExpression(te);
		tester.diagnose().assertOK();

		te.setText("\\p"); // i.e. '\p'
		tester.validator().checkVerbatimTextExpression(te);
		tester.diagnose().assertWarning(IPPDiagnostics.ISSUE__UNRECOGNIZED_ESCAPE);
	}

}
