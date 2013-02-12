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
package org.cloudsmith.geppetto.validation.runner;

import org.eclipse.xtext.parser.IParseResult;

/**
 * SyntaxErrorException thrown with more information about syntax error(s)
 * detected when doing low level parsing of puppet source.
 * 
 * To investigate the actual error, get the parse result, and drill down into
 * the nodes returned from {@link IParseResult#getSyntaxErrors()}.
 * 
 */
public class PPSyntaxErrorException extends Exception {

	private static final long serialVersionUID = 5291479164795826991L;

	private IParseResult parseResult;

	public PPSyntaxErrorException(IParseResult result) {
		super("PP Syntax Error");
		this.parseResult = result;
	}

	public IParseResult getParseResult() {
		return this.parseResult;
	}
}
