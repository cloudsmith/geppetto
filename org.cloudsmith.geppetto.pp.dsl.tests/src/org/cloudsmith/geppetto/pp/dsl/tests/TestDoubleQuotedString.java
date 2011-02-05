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

import java.util.ArrayList;
import java.util.List;

import org.cloudsmith.geppetto.pp.DoubleQuotedString;
import org.cloudsmith.geppetto.pp.ExpressionTE;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.TextExpression;
import org.cloudsmith.geppetto.pp.VariableTE;
import org.cloudsmith.geppetto.pp.VerbatimTE;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.resource.XtextResource;

/**
 * Tests Literals
 * 
 */
public class TestDoubleQuotedString extends AbstractPuppetTests {

	private static class TEPair {
		final TextExpression te;

		final String string;

		TEPair(final TextExpression firstElement, final String secondElement) {
			te = firstElement;
			string = secondElement;
		}

		boolean isExprClass(Class<?> clazz) {
			if(!(te instanceof ExpressionTE))
				return false;
			return clazz.isAssignableFrom(((ExpressionTE) te).getExpression().getClass());
		}
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

		List<TEPair> t = flattenTextExpression(string.getTextExpression());
		assertEquals("List should have 3 entries", 3, t.size());
		assertEquals("First element should be 'before'", "before", t.get(0).string);
		assertEquals("Second element should be a $", "$", t.get(1).string);
		assertEquals("Third element should be '/after'", "/after", t.get(2).string);
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

		List<TEPair> t = flattenTextExpression(string.getTextExpression());
		assertEquals("List should have 3 entries", 3, t.size());
		assertEquals("First element should be 'before'", "before", t.get(0).string);
		assertTrue(
			"Second element should be a LiteralNameOrReference", t.get(1).isExprClass(LiteralNameOrReference.class));
		assertEquals("Third element should be '/after'", "/after", t.get(2).string);

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

		List<TEPair> t = flattenTextExpression(string.getTextExpression());
		assertEquals("List should have 3 entries", 3, t.size());
		assertEquals("First element should be 'before'", "before", t.get(0).string);
		assertEquals("Second element should be '$var'", "$var", t.get(1).string);
		assertEquals("Third element should be '/after'", "/after", t.get(2).string);
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
		TextExpression te = string.getTextExpression();
		assertTrue("Should be a single verbatim TE", te instanceof VerbatimTE);
		assertEquals("Should contain the original", original, ((VerbatimTE) te).getText());
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
		String code = doubleQuote(original);
		XtextResource r = getResourceFromString(code);
		EObject result = r.getContents().get(0);
		assertTrue("Should be a PuppetManifest", result instanceof PuppetManifest);
		result = ((PuppetManifest) result).getStatements().get(0);
		assertTrue("Should be a DoubleQuotedString", result instanceof DoubleQuotedString);

		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("Serialization of interpolated string should produce same result", code, s);
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
		XtextResource r = getResourceFromString(code);
		EObject result = r.getContents().get(0);
		assertTrue("Should be a PuppetManifest", result instanceof PuppetManifest);

		String s = serializeFormatted(r.getContents().get(0));
		assertEquals("Serialization of interpolated string should produce same result", code, s);
	}

	private String doubleQuote(String s) {
		return '"' + s + '"';
	}

	private void flatten(List<TEPair> result, TextExpression te) {
		if(te == null)
			return;
		if(te.getLeading() != null)
			flatten(result, te.getLeading());
		if(te instanceof VerbatimTE)
			result.add(tePair(te, ((VerbatimTE) te).getText()));
		else if(te instanceof VariableTE)
			result.add(tePair(te, ((VariableTE) te).getVarName()));
		else if(te instanceof ExpressionTE)
			result.add(tePair(te, "EXPRESSION"));

		if(te.getTrailing() != null)
			flatten(result, te.getTrailing());
	}

	private List<TEPair> flattenTextExpression(TextExpression te) {
		List<TEPair> result = new ArrayList<TEPair>();
		flatten(result, te);
		return result;
	}

	private TEPair tePair(TextExpression te, String s) {
		return new TEPair(te, s);
	}
}
