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
package com.puppetlabs.geppetto.pp.dsl.ui.contentassist;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.apache.log4j.Logger;
import com.puppetlabs.geppetto.pp.dsl.lexer.PPOverridingLexer;

/**
 * This lexer wraps the external lexer used by the main parser.
 * This is required as the content assist lexer is injected with a provider that requires it to be
 * an instance of the "contentassist.antlr.internal.Lexer" class - hence it is impossible to use the
 * PPOverridingLexer directly.
 * 
 */
public class PPContentAssistLexer extends org.eclipse.xtext.ui.editor.contentassist.antlr.internal.Lexer {

	private static final Logger logger = Logger.getLogger(PPContentAssistLexer.class);

	private PPOverridingLexer delegate;

	public PPContentAssistLexer() {
		super();
		delegate = new PPOverridingLexer();
	}

	public PPContentAssistLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}

	public PPContentAssistLexer(CharStream input, RecognizerSharedState state) {
		super(input, state);
		delegate = new PPOverridingLexer(input, state);
	}

	@Override
	public void emitErrorMessage(String msg) {
		// don't call super, since it would do a plain vanilla
		// System.err.println(msg);
		if(logger.isTraceEnabled())
			logger.trace(msg);
	}

	@Override
	public String getGrammarFileName() {
		return "../com.puppetlabs.geppetto.pp.dsl.ui/src-gen/com/puppetlabs/geppetto/pp/dsl/ui/contentassist/antlr/lexer/InternalPPLexer.g";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.antlr.runtime.Lexer#mTokens()
	 */
	@Override
	public void mTokens() throws RecognitionException {
		delegate.mTokens();

	}

	/**
	 * @see org.antlr.runtime.Lexer#nextToken()
	 */
	@Override
	public Token nextToken() {
		return delegate.nextToken();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.antlr.runtime.Lexer#setCharStream(org.antlr.runtime.CharStream)
	 */
	@Override
	public void setCharStream(CharStream input) {
		super.setCharStream(input);
		delegate.setCharStream(input);
	}
}
