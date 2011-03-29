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
package org.cloudsmith.geppetto.ruby.jrubyparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.ruby.PPFunctionInfo;
import org.cloudsmith.geppetto.ruby.PPTypeInfo;
import org.cloudsmith.geppetto.ruby.RubySyntaxException;
import org.cloudsmith.geppetto.ruby.spi.IRubyIssue;
import org.cloudsmith.geppetto.ruby.spi.IRubyParseResult;
import org.cloudsmith.geppetto.ruby.spi.IRubyServices;
import org.jrubyparser.CompatVersion;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.ModuleNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.lexer.LexerSource;
import org.jrubyparser.lexer.SyntaxException;
import org.jrubyparser.parser.ParserConfiguration;
import org.jrubyparser.parser.Ruby18Parser;
import org.jrubyparser.parser.Ruby19Parser;
import org.jrubyparser.parser.RubyParser;

import com.google.common.collect.Lists;

public class JRubyServices  implements IRubyServices{
	
	/** 
	 * The number of the first line in a source file.
	 */
	private final int startLine = 1;

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

		Result(Node rootNode, List<IRubyIssue> issues) {
			this.issues = issues;
			this.AST = rootNode;
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
//	private Ruby rubyRuntime;

	/**
	 * IOExceptions thrown FileNotFound, and while reading
	 * @param file
	 * @return 
	 * @throws IOException
	 */
	@Override
	public IRubyParseResult parse(File file) throws IOException {
		return internalParse(file);
	}
	/** Implementation that exposes the Result impl class. Don't want callers of the JRubyService to 
	 * see this.
	 * @param file
	 * @return
	 */
	protected Result internalParse(File file) throws IOException {
		if(!file.exists())
			throw new FileNotFoundException(file.getPath());
		final Reader reader = new BufferedReader(new FileReader(file));
		try {
			return internalParse(file.getAbsolutePath(), reader, parserConfiguration);
		}
		finally {
			StreamUtil.close(reader);
		}		
	}
	/**
	 * Where the parsing "magic" takes place. This impl is used instead of a similar in the Parser util class
	 * since that impl uses a Null warning collector.
	 * @param file
	 * @param content
	 * @param configuration
	 * @return
	 * @throws IOException
	 */
	protected Result internalParse(String file, Reader content, ParserConfiguration configuration) throws IOException {
        RubyParser parser;
        if (configuration.getVersion() == CompatVersion.RUBY1_8) {
            parser = new Ruby18Parser();
        } else {
            parser = new Ruby19Parser();
        }
		RubyParserWarningsCollector warnings = new RubyParserWarningsCollector();
        parser.setWarnings(warnings);
        
        LexerSource lexerSource = LexerSource.getSource(file, content, configuration);

        Node parserResult = null;
		try {
			parserResult = parser.parse(configuration, lexerSource).getAST();
			
		} catch (SyntaxException e) {
			warnings.syntaxError(e);
		}
		return new Result(parserResult, warnings.getIssues());

	}
	
	/**
	 * Configure the ruby environment... (very little is needed in this impl).
	 */
	public void setUp() {
		parserConfiguration = new ParserConfiguration(startLine, rubyVersion);
	}
	
	public void tearDown() {
		parserConfiguration = null;
	}
	
	private static final String[] functionModuleFQN = new String[] { "Puppet", "Parser", "Functions"};
	private static final String functionDefinition = "newfunction";
	private static final String[] newFunctionFQN = new String[] { "Puppet", "Parser", "Functions", "newfunction"};
	
	@Override
	public List<PPFunctionInfo> getFunctionInfo(File file) throws IOException, RubySyntaxException {
		List<PPFunctionInfo> functions = Lists.newArrayList();
		Result result = internalParse(file);
		if(result.hasErrors())
			throw new RubySyntaxException();
		RubyCallFinder callFinder = new RubyCallFinder();
		GenericCallNode found = callFinder.findCall(result.getAST(), newFunctionFQN);
		if(found == null)
			return functions;
		Object arguments = new ConstEvaluator().eval(found.getArgsNode());
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
	
//	//@Override
//	private List<PPFunctionInfo> getFunctionInfoOLD(File file) throws IOException, RubySyntaxException {
//		List<PPFunctionInfo> functions = Lists.newArrayList();
//		Result result = internalParse(file);
//		if(result.hasErrors())
//			throw new RubySyntaxException();
//		RubyModuleFinder finder = new RubyModuleFinder();
//		ModuleNode foundModule = finder.findModule(result.getAST(), functionModuleFQN);
//		if(foundModule == null)
//			return functions;
//		// find the function
//		FCallNode foundFunction = new RubyFunctionCallFinder().findFuntion(foundModule, functionDefinition);
//		if(foundFunction == null)
//			return functions;
//		Object arguments = new ConstEvaluator().eval(foundFunction.getArgsNode());
//		// Result should be a list with a String, and a Map
//		if(!(arguments instanceof List))
//			return functions;
//		List<?> argList = (List<?>)arguments;
//		if(argList.size() != 2)
//			return functions;
//		Object name = argList.get(0);
//		if(! (name instanceof String))
//			return functions;
//		Object hash = argList.get(1);
//		if(! (hash instanceof Map<?, ?>))
//			return functions;
//		Object type = ((Map<?,?>)hash).get("type");
//		boolean rValue = "rvalue".equals(type);
//		Object doc = ((Map<?,?>)hash).get("doc");
//		String docString = doc == null ? "" : doc.toString();
//
//		functions.add(new PPFunctionInfo((String)name, rValue, docString));
//		return functions;
//	}

	@Override
	public List<PPTypeInfo> getTypeInfo(File file) throws IOException, RubySyntaxException {
		List<PPTypeInfo> types = Lists.newArrayList();
		Result result = internalParse(file);
		if(result.hasErrors())
			throw new RubySyntaxException();
		PPTypeFinder typeFinder = new PPTypeFinder();
		PPTypeInfo typeInfo = typeFinder.findTypeInfo(result.getAST());
		if(typeInfo != null)
			types.add(typeInfo);
		return types;
	}
	
	@Override
	public List<PPTypeInfo> getTypePropertiesInfo(File file) throws IOException, RubySyntaxException {
		List<PPTypeInfo> types = Lists.newArrayList();
		Result result = internalParse(file);
		if(result.hasErrors())
			throw new RubySyntaxException();
		PPTypeFinder typeFinder = new PPTypeFinder();
		PPTypeInfo typeInfo = typeFinder.findTypePropertyInfo(result.getAST());
		if(typeInfo != null)
			types.add(typeInfo);
		return types;
	}

	@Override
	public boolean isMockService() {
		return false;
	}
}