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
package org.cloudsmith.geppetto.pp.dsl.lexer;

import java.util.Arrays;

/**
 * Not used separately, this code is copied to PPLexer.g
 * (except the methods that reference KEYWORD constants which are only in PPLexer.g)
 * 
 */
@SuppressWarnings("unused")
public class LexerSnippet {
	private boolean singleQuotedString = false;

	private boolean doubleQuotedString = false;

	protected int lastSignificantToken = 0;

	private int dqIndex = 0;

	private boolean dqStack[] = new boolean[10];

	private int braceNesting = 0;

	private void enterBrace() {
		if(!isInterpolating())
			return;
		braceNesting++;
	}

	private void exitBrace() {
		if(!isInterpolating())
			return;
		braceNesting--;
		if(braceNesting == 0)
			popDq();
	}

	private boolean isInDqString() {
		return doubleQuotedString;
	}

	private boolean isInSqString() {
		return singleQuotedString;
	}

	private boolean isInterpolating() {
		return dqIndex > 0;
	}

	private boolean isNotInString() {
		return !singleQuotedString && !doubleQuotedString;
	}

	private void popDq() {
		if(dqIndex == 0)
			doubleQuotedString = false; // bad state, but stay alive
		else
			doubleQuotedString = dqStack[--dqIndex];
	}

	private void pushDq() {
		if(dqIndex >= dqStack.length)
			dqStack = Arrays.copyOf(dqStack, dqStack.length + 10);

		dqStack[dqIndex++] = doubleQuotedString;
		doubleQuotedString = false;
	}
}
