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

import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;

public class PPOverridingLexer extends PPLexer {

	public PPOverridingLexer() {
		;
	}

	public PPOverridingLexer(CharStream input) {
		super(input);
	}

	/**
	 * Special processing for keywords 'and' 'or' 'in' that recognizes these keywords only if they are
	 * not followed by a non word character (letter or digit or _).
	 * 
	 */
	@Override
	public void mTokens() throws RecognitionException {
		super.mTokens();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.parser.antlr.Lexer#nextToken()
	 */
	@Override
	public Token nextToken() {
		// TODO Auto-generated method stub
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

	// public void xmTokens() throws RecognitionException {
	// int alt = -1;
	//
	// switch(input.LA(1)) {
	// case 'a':
	// if(input.LA(2) != 'n')
	// alt = ALT_LOWER_A;
	// else if(input.LA(3) != 'd')
	// alt = ALT_LOWER_A;
	// else {
	// int la = input.LA(4);
	// if(Character.isLetterOrDigit(la) || la == '_')
	// alt = ALT_LOWER_A;
	// else
	// alt = ALT_AND;
	// }
	// break;
	// case 'o':
	// if(input.LA(2) != 'r')
	// alt = ALT_LOWER_O;
	// else {
	// int la = input.LA(3);
	// if(Character.isLetterOrDigit(la) || la == '_')
	// alt = ALT_LOWER_O;
	// else
	// alt = ALT_OR;
	// }
	// break;
	// case 'i':
	// if(input.LA(2) != 'n')
	// alt = ALT_LOWER_I;
	// else {
	// int la = input.LA(3);
	// if(Character.isLetterOrDigit(la) || la == '_')
	// alt = ALT_LOWER_I;
	// else
	// alt = ALT_IN;
	// }
	// break;
	// }
	// switch(alt) {
	// case ALT_LOWER_I:
	// // mRULE_LOWER_I();
	// break;
	// case ALT_LOWER_O:
	// // mRULE_LOWER_O();
	// break;
	// case ALT_LOWER_A:
	// // mRULE_LOWER_A();
	// break;
	// case ALT_AND:
	// mKEYWORD_3();
	// break;
	// case ALT_OR:
	// mKEYWORD_2();
	// break;
	// case ALT_IN:
	// mKEYWORD_1();
	// break;
	// default:
	// super.mTokens();
	// }
	// }
}
