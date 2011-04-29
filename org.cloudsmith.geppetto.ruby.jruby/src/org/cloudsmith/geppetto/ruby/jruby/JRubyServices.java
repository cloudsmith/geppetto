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
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.ruby.PPFunctionInfo;
import org.cloudsmith.geppetto.ruby.PPTypeInfo;
import org.cloudsmith.geppetto.ruby.RubySyntaxException;
import org.cloudsmith.geppetto.ruby.spi.IRubyIssue;
import org.cloudsmith.geppetto.ruby.spi.IRubyParseResult;
import org.cloudsmith.geppetto.ruby.spi.IRubyServices;
import org.jruby.CompatVersion;
import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.ast.FCallNode;
import org.jruby.ast.ModuleNode;
import org.jruby.ast.Node;
import org.jruby.lexer.yacc.InputStreamLexerSource;
import org.jruby.lexer.yacc.LexerSource;
import org.jruby.lexer.yacc.SyntaxException;
import org.jruby.parser.ParserConfiguration;
import org.jruby.parser.RubyParser;
import org.jruby.parser.RubyParserPool;
import org.jruby.parser.RubyParserResult;

import com.google.common.collect.Lists;

public class JRubyServices  implements IRubyServices{
	
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
	public static class Result implements IRubyParseResult{
		private List<IRubyIssue> issues;
		private Node AST;

		Result(RubyParserResult parserResult, List<IRubyIssue> issues) {
			this.issues = issues;
			this.AST = parserResult == null ? null : parserResult.getAST();
		}

		/**
		 * Returns a list of issues. Will return an empty list if there were no issues.
		 * @return
		 */
		@Override
		public List<IRubyIssue> getIssues() {
			return issues;
		}

		/**
		 * @return the parsed AST, or null in case of errors.
		 */
		public Node getAST() {
			return AST;
		}

		@Override
		public boolean hasErrors() {
			if(issues != null)
				for(IRubyIssue issue : issues)
					if(issue.isSyntaxError())
						return true;
			return false;
		}

		@Override
		public boolean hasIssues() {
			return issues != null && issues.size() > 0;
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
	public IRubyParseResult parse(File file) throws IOException {
		return internalParse(file);
	}
	
	protected Result internalParse(File file) throws IOException {
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
	private static final String[] functionModuleFQN = new String[] { "Puppet", "Parser", "Functions"};
	private static final String functionDefinition = "newfunction";
	@Override
	public List<PPFunctionInfo> getFunctionInfo(File file) throws IOException, RubySyntaxException {
		List<PPFunctionInfo> functions = Lists.newArrayList();
		Result result = internalParse(file);
		if(result.hasErrors())
			throw new RubySyntaxException(result.getIssues());
		RubyModuleFinder finder = new RubyModuleFinder();
		ModuleNode foundModule = finder.findModule(result.getAST(), functionModuleFQN);
		if(foundModule == null)
			return functions;
		// find the function
		FCallNode foundFunction = new RubyFunctionCallFinder().findFuntion(foundModule, functionDefinition);
		if(foundFunction == null)
			return functions;
		Object arguments = new ConstEvaluator().eval(foundFunction.getArgsNode());
		// Result should be a list with a String, and a Map
		if(!(arguments instanceof List))
			return functions;
		List<?> argList = (List<?>)arguments;
		if(argList.size() != 2)
			return functions;
		Object name = argList.get(0);
		if(! (name instanceof String))
			return functions;
		Object hash = argList.get(1);
		if(! (hash instanceof Map<?, ?>))
			return functions;
		Object type = ((Map<?,?>)hash).get("type");
		boolean rValue = "rvalue".equals(type);
		
		Object doc = ((Map<?,?>)hash).get("doc");
		String docString = doc == null ? "" : doc.toString();
		functions.add(new PPFunctionInfo((String)name, rValue, docString));
		return functions;
	}

	@Override
	public List<PPTypeInfo> getTypeInfo(File file) throws IOException, RubySyntaxException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isMockService() {
		return false;
	}

	@Override
	public List<PPTypeInfo> getTypePropertiesInfo(File file)
			throws IOException, RubySyntaxException {
		throw new UnsupportedOperationException("Implement this method");
	}

	@Override
	public PPTypeInfo getMetaTypeProperties(File file) throws IOException,
			RubySyntaxException {
		throw new UnsupportedOperationException("Implement this method");
	}

	@Override
	public IRubyParseResult parse(String path, Reader reader)
			throws IOException {
		throw new UnsupportedOperationException("Please implement me");

	}

	@Override
	public List<PPTypeInfo> getTypeInfo(String fileName, Reader reader)
			throws IOException, RubySyntaxException {
		throw new UnsupportedOperationException("Please implement me");
	}

	@Override
	public List<PPFunctionInfo> getFunctionInfo(String fileName, Reader reader) {
		throw new UnsupportedOperationException("Please implement me");
	}

	@Override
	public PPTypeInfo getMetaTypeProperties(String fileName,
			Reader reader) {
		throw new UnsupportedOperationException("Please implement me");
	}

	@Override
	public List<PPTypeInfo> getTypePropertiesInfo(String fileName, Reader reader)
			throws IOException, RubySyntaxException {
		throw new UnsupportedOperationException("Please implement me");
	}

	@Override
	public List<PPFunctionInfo> getLogFunctions(File file) throws IOException,
			RubySyntaxException {
		throw new UnsupportedOperationException("Please implement me");
	}
}