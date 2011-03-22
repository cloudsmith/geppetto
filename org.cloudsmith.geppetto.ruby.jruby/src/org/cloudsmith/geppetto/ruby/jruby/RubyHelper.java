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
package org.cloudsmith.geppetto.ruby.jruby;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.cloudsmith.geppetto.ruby.jruby.RubyParserWarningsCollector.RubyIssue;
import org.jruby.CompatVersion;
import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.ast.Node;
import org.jruby.lexer.yacc.InputStreamLexerSource;
import org.jruby.lexer.yacc.LexerSource;
import org.jruby.lexer.yacc.SyntaxException;
import org.jruby.parser.ParserConfiguration;
import org.jruby.parser.RubyParser;
import org.jruby.parser.RubyParserPool;
import org.jruby.parser.RubyParserResult;

public class RubyHelper {
	
	/** 
	 * The number of the first line in a source file.
	 */
	private final int startLine = 1;

	/**
	 * No idea what this is. Setting to true did not seem to add any information to errors.
	 */
	private final boolean extraPositionInfo = false;
	
	/**
	 * No idea what this is. Setting to false still reports errors with some source and a caret indicating
	 * position.
	 */
	private final boolean inlineSource = false;
	
	/**
	 * Compatibility of Ruby language version.
	 */
	private final CompatVersion rubyVersion = CompatVersion.RUBY1_9;

	/**
	 * Holds the JRuby parser result (the AST and any reported issues/errors).
	 *
	 */
	public static class Result {
		private List<RubyIssue> issues;
		private Node AST;

		Result(RubyParserResult parserResult, List<RubyIssue> issues) {
			this.issues = issues;
			this.AST = parserResult == null ? null : parserResult.getAST();
		}

		/**
		 * Returns a list of issues. Will return an empty list if there were no issues.
		 * @return
		 */
		public List<RubyIssue> getIssues() {
			return issues;
		}

		/**
		 * @return the parsed AST, or null in case of errors.
		 */
		public Node getAST() {
			return AST;
		}

	}

	private ParserConfiguration parserConfiguration;
	private Ruby rubyRuntime;

	/**
	 * IOExceptions thrown FileNotFound, and while reading
	 * @param file
	 * @return 
	 * @throws IOException
	 */
	public Result parse(File file) throws IOException {
		if(rubyRuntime == null)
			setUp();
		
		// Set up input 
		final List<String> lexerCapture = null; // do not want to record the read text lines
		FileInputStream input = new FileInputStream(file);
		LexerSource source = new InputStreamLexerSource(file.getPath(), 
				input, lexerCapture, startLine, extraPositionInfo);

		// Get a parser
		RubyParser parser = RubyParserPool.getInstance().borrowParser(rubyVersion);

		// Create a warnings collector to give to the parser
		RubyParserWarningsCollector warnings = new RubyParserWarningsCollector(rubyRuntime);
		parser.setWarnings(warnings);
		RubyParserResult parserResult = null;
		try {
			parserResult = parser.parse(parserConfiguration, source);
		} catch (SyntaxException e) {
			warnings.syntaxError(e);
		} finally {
			RubyParserPool.getInstance().returnParser(parser);
		}
		return new Result(parserResult, warnings.getIssues());
	}
	
	/**
	 * Configure a Ruby Runtime, it is needed for the warnings processor even if we are not
	 * going to be running any of the scripts.
	 */
	public void setUp() {
		RubyInstanceConfig config = new RubyInstanceConfig();
		config.setCompatVersion(rubyVersion);
		rubyRuntime = Ruby.newInstance(config);
		parserConfiguration = new ParserConfiguration(rubyRuntime,startLine,extraPositionInfo,inlineSource,rubyVersion);
	}
	
	public void tearDown() {
		rubyRuntime = null;
		parserConfiguration = null;
	}
}