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
package org.cloudsmith.geppetto.pp.util;

import org.cloudsmith.geppetto.pp.DoubleQuotedString;
import org.cloudsmith.geppetto.pp.TextExpression;
import org.cloudsmith.geppetto.pp.VerbatimTE;

/**
 * Tests Literals
 * 
 */
public class TextExpressionHelper {

	// public static class StringData {
	// final TextExpression te;
	//
	// final String string;
	//
	// StringData(final TextExpression firstElement, final String secondElement) {
	// te = firstElement;
	// string = secondElement;
	// }
	//
	// public String getString() {
	// return string;
	// }
	//
	// public TextExpression getTextExpression() {
	// return te;
	// }
	//
	// boolean isExprClass(Class<?> clazz) {
	// if(!(te instanceof ExpressionTE))
	// return false;
	// return clazz.isAssignableFrom(((ExpressionTE) te).getExpression().getClass());
	// }
	// }

	// private static void flatten(List<StringData> result, TextExpression te) {
	// if(te == null)
	// return;
	// if(te.getLeading() != null)
	// flatten(result, te.getLeading());
	// if(te instanceof VerbatimTE)
	// result.add(stringData(te, ((VerbatimTE) te).getText()));
	// else if(te instanceof VariableTE)
	// result.add(stringData(te, ((VariableTE) te).getVarName()));
	// else if(te instanceof ExpressionTE)
	// result.add(stringData(te, "EXPRESSION"));
	//
	// if(te.getTrailing() != null)
	// flatten(result, te.getTrailing());
	// }

	// private static List<StringData> flattenTextExpression(TextExpression te) {
	// List<StringData> result = new ArrayList<StringData>();
	// flatten(result, te);
	// return result;
	// }

	// public static List<StringData> getStringData(DoubleQuotedString dqString) {
	// return flattenTextExpression(dqString.getTextExpression());
	// }

	public static boolean hasInterpolation(DoubleQuotedString dqString) {
		for(TextExpression stringPart : dqString.getStringPart())
			if(!(stringPart instanceof VerbatimTE))
				return true;
		return false;

		// List<StringData> stringData = getStringData(dqString);
		// for(StringData sd : stringData)
		// if(!(sd.te instanceof VerbatimTE))
		// return true;
		// return false;
	}

	// private static StringData stringData(TextExpression te, String s) {
	// return new StringData(te, s);
	// }

	public String doubleQuote(String s) {
		return '"' + s + '"';
	}
}
