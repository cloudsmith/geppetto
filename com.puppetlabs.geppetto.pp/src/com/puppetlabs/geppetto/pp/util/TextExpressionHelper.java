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
package org.cloudsmith.geppetto.pp.util;

import java.util.ArrayList;
import java.util.List;

import org.cloudsmith.geppetto.pp.DoubleQuotedString;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.ExpressionTE;
import org.cloudsmith.geppetto.pp.LiteralName;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.TextExpression;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.VariableTE;
import org.cloudsmith.geppetto.pp.VerbatimTE;
import org.eclipse.emf.common.util.EList;

/**
 * Helps with TextExpressions in interpolated Strings.
 * 
 */
public class TextExpressionHelper {

	public static String getNonInterpolated(DoubleQuotedString dqString) {
		if(hasInterpolation(dqString))
			return null;
		// if there is no interpolation, there is by definition only one StringData
		// ignore the fact that a static string could be interpolated since this is an
		// esoteric case.
		// TODO: Handle this esoteric case "abc${"xyz"}"
		EList<TextExpression> parts = dqString.getStringPart();
		if(parts.size() < 1)
			return "";
		if(parts.size() == 1) {
			return ((VerbatimTE) parts.get(0)).getText();
		}
		// impossible can not have more than one verbatim part and not be interpolated
		throw new IllegalArgumentException("The given dqString lied about not being interpolated");
	}

	public static boolean hasInterpolation(DoubleQuotedString dqString) {
		for(TextExpression stringPart : dqString.getStringPart())
			if(!(stringPart instanceof VerbatimTE))
				return true;
		return false;

	}

	/**
	 * Returns a list of trivially interpolated variables; i.e. ${name}, $name, or ${$name}but not variables embedded in more
	 * complex expression e.g. ${$a + $b}.
	 * 
	 * @param dqString
	 * @return
	 */
	public static List<String> interpolatedVariables(DoubleQuotedString dqString) {
		List<String> result = new ArrayList<String>();
		for(TextExpression te : dqString.getStringPart()) {
			if(te instanceof VariableTE) {
				result.add(((VariableTE) te).getVarName());
			}
			else if(te instanceof ExpressionTE) {
				Expression e = ((ExpressionTE) te).getExpression();
				if(e instanceof LiteralNameOrReference)
					result.add(((LiteralNameOrReference) e).getValue());
				else if(e instanceof LiteralName)
					result.add(((LiteralName) e).getValue());
				else if(e instanceof VariableExpression)
					result.add(((VariableExpression) e).getVarName());
			}
		}
		return result;
	}

	public static int interpolationCount(DoubleQuotedString dqString) {
		int count = 0;
		for(TextExpression te : dqString.getStringPart()) {
			if(te instanceof VerbatimTE)
				continue;
			count++;
		}
		return count;

	}

	public static int segmentCount(DoubleQuotedString dqString) {
		return dqString.getStringPart().size();
	}

	public String doubleQuote(String s) {
		return '"' + s + '"';
	}

}
