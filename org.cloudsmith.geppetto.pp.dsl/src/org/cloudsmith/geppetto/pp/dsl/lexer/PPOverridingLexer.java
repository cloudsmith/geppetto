/**
 * Copyright (c) 2011, 2012 Cloudsmith Inc. and other contributors, as listed below.
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
	 * This override keeps track of 'last significant token' as this is required as a predicated for other token rules.
	 * </p>
	 */
	@Override
	public Token nextToken() {
		Token t = super.nextToken();
		// System.err.println("Token: " + t.toString());
		switch(t.getType()) {
			case RULE_SL_COMMENT:
			case RULE_ML_COMMENT:
			case RULE_WS:
				break; // uninteresting tokens
			default:
				this.lastSignificantToken = t.getType();
		}
		return t;
	}
}
