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
 * Helps with TextExpressions in interpolated Strings.
 * 
 */
public class TextExpressionHelper {

	public static boolean hasInterpolation(DoubleQuotedString dqString) {
		for(TextExpression stringPart : dqString.getStringPart())
			if(!(stringPart instanceof VerbatimTE))
				return true;
		return false;

	}

	public String doubleQuote(String s) {
		return '"' + s + '"';
	}
}
