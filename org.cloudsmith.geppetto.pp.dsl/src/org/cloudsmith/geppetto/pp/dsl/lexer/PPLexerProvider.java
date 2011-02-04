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

import org.cloudsmith.geppetto.pp.dsl.parser.antlr.lexer.InternalPPLexer;

public class PPLexerProvider extends org.eclipse.xtext.parser.antlr.LexerProvider<InternalPPLexer> {

	public PPLexerProvider(Class<InternalPPLexer> clazz) {
		super(InternalPPLexer.class);
	}

}
