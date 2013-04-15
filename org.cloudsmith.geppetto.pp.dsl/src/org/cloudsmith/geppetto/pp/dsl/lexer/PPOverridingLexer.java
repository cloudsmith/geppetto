/**
 * Copyright (c) 2011, 2013 Cloudsmith Inc. and other contributors, as listed below.
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

import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;

/**
 * The PPOverridingLexer is used to adapt an external lexer "PPLexer.g" to the parser generated from
 * pp.xtext. To maintain sanity, the PPLexer.g is written in terms of keywords named KW_<name> where
 * the parser is using KEYWORD_<number> and where numbers change when new terminals/keywords are added
 * (with a manual tricky synchronization task as a result).
 * As long as the terminals/kewords in PPLexer.g are the same as those in pp.xtext it is sufficient
 * to simply transpose them into range. All KW_ tokens will be after the (common) set of RULE_ tokens.
 * The lexer starts with token 4 (<4 are special token numbers), and thus the first token
 * is KW_INHERITS, which from time to time is given some KEYWORD_nn name (it was KEYWORD_66 for a long time),
 * which is then mapped to the token number 4.
 * 
 * When new keywords are introduced in the grammar, a check is required to ensure that that
 * the set of tokens is the same, and if "inherits" is the first (have number 4). If a terminal/keyword
 * is missing (or one added) this simple transposition will fail miserably, so it is important to check.
 * 
 */
public class PPOverridingLexer extends PPLexer {

	public PPOverridingLexer() {
		;
	}

	public PPOverridingLexer(CharStream input) {
		super(input);
	}

	public PPOverridingLexer(CharStream input, RecognizerSharedState state) {
		super(input, state);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void mTokens() throws RecognitionException {
		// Does nothing - but is useful when debugging (set breakpoint here, the super impl is used in more
		// than one lexer subclass.
		//
		super.mTokens();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * This override keeps track of 'last significant token' as this is required as a predicate for other token rules. The remembered token is
	 * remembered in the token range used by the external lexer (i.e. unmapped from KW_ range to KEYWORD_ range).
	 * </p>
	 */
	@Override
	public Token nextToken() {
		Token t = super.nextToken();
		// This translates all KW_ to the KEYWORD range, i.e. KW_INHERTIS (84) KEYWORD_66 (inherits) = 4
		int origType = t.getType();
		if(origType >= KW_INHERITS)
			t.setType(origType - KW_INHERITS + 4);

		// System.err.println("Token: " + t.toString());
		switch(origType) {
			case RULE_SL_COMMENT:
			case RULE_ML_COMMENT:
			case RULE_WS:
				break; // uninteresting tokens
			default:
				this.lastSignificantToken = origType;
		}
		return t;
	}
}
