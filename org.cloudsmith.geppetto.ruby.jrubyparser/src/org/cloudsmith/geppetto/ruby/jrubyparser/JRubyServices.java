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
import org.cloudsmith.geppetto.ruby.spi.IRubyServicesFactory;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.jrubyparser.CompatVersion;
import org.jrubyparser.ast.ClassNode;
import org.jrubyparser.ast.InstAsgnNode;
import org.jrubyparser.ast.NewlineNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;
import org.jrubyparser.lexer.LexerSource;
import org.jrubyparser.lexer.SyntaxException;
import org.jrubyparser.parser.ParserConfiguration;
import org.jrubyparser.parser.Ruby18Parser;
import org.jrubyparser.parser.Ruby19Parser;
import org.jrubyparser.parser.RubyParser;

import com.google.common.collect.Lists;

public class JRubyServices implements IRubyServices {

	/**
	 * Holds the JRuby parser result (the AST and any reported issues/errors).
	 * 
	 */
	public static class Result implements IRubyParseResult {
		private List<IRubyIssue> issues;

		private Node AST;

		Result(Node rootNode, List<IRubyIssue> issues) {
			this.issues = issues;
			this.AST = rootNode;
		}

		/**
		 * @return the parsed AST, or null in case of errors.
		 */
		public Node getAST() {
			return AST;
		}

		/**
		 * Returns a list of issues. Will return an empty list if there were no
		 * issues.
		 * 
		 * @return
		 */
		@Override
		public List<IRubyIssue> getIssues() {
			return issues;
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

	public static IRubyServicesFactory FACTORY = new IRubyServicesFactory() {
		public IRubyServices create() {
			return new JRubyServices();
		}
	};

	/**
	 * The number of the first line in a source file.
	 */
	private final int startLine = 1;

	/**
	 * Compatibility of Ruby language version.
	 */
	private final CompatVersion rubyVersion = CompatVersion.RUBY1_9;

	private ParserConfiguration parserConfiguration;

	// private Ruby rubyRuntime;

	// private static final String[] functionModuleFQN = new String[] {
	// "Puppet", "Parser", "Functions"};
	private static final String functionDefinition = "newfunction";

	private static final String[] newFunctionFQN = new String[] { "Puppet", "Parser", "Functions", functionDefinition };

	private static final String[] NAGIOS_BASE_PATH = new String[] { "puppet", "external", "nagios", "base.rb" };

	@Override
	public List<PPFunctionInfo> getFunctionInfo(File file) throws IOException, RubySyntaxException {
		Result result = internalParse(file);
		return getFunctionInfo(result);
	}

	protected List<PPFunctionInfo> getFunctionInfo(Result result) throws IOException, RubySyntaxException {
		if(result.hasErrors())
			throw new RubySyntaxException(result.getIssues());
		List<PPFunctionInfo> functions = Lists.newArrayList();
		RubyCallFinder callFinder = new RubyCallFinder();
		GenericCallNode found = callFinder.findCall(result.getAST(), newFunctionFQN);
		if(found == null)
			return functions;
		Object arguments = new ConstEvaluator().eval(found.getArgs());
		// Result should be a list with a String, and a Map
		if(!(arguments instanceof List))
			return functions;
		List<?> argList = (List<?>) arguments;

		if(argList.size() < 1)
			return functions;
		Object name = argList.get(0);
		if(!(name instanceof String))
			return functions;

		// Functions can lack rtype and documentation. In that case they just have name
		if(argList.size() == 1) {
			functions.add(new PPFunctionInfo((String) name, false, ""));
			return functions;
		}

		Object hash = argList.get(1);
		if(!(hash instanceof Map<?, ?>))
			return functions;
		Object type = ((Map<?, ?>) hash).get("type");
		boolean rValue = "rvalue".equals(type);
		Object doc = ((Map<?, ?>) hash).get("doc");
		String docString = doc == null
				? ""
				: doc.toString();

		functions.add(new PPFunctionInfo((String) name, rValue, docString));
		return functions;
	}

	@Override
	public List<PPFunctionInfo> getFunctionInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		Result result = internalParse(fileName, reader);
		return getFunctionInfo(result);
	}

	@Override
	public List<PPFunctionInfo> getLogFunctions(File file) throws IOException, RubySyntaxException {
		List<PPFunctionInfo> functions = Lists.newArrayList();
		Result result = internalParse(file);
		Node root = result.getAST();
		ClassNode logClass = new RubyClassFinder().findClass(root, "Puppet", "Util", "Log");
		if(logClass == null)
			return functions;

		for(Node n : logClass.getBody().childNodes()) {
			if(n.getNodeType() == NodeType.NEWLINENODE)
				n = ((NewlineNode) n).getNextNode();
			// if (n.getNodeType() == NodeType.CLASSNODE) {
			// ClassNode cn = (ClassNode) n;
			// // TODO: Check that we have Puppet::Util::Log class
			// Object name = new ConstEvaluator().eval(cn.getCPath());
			// if (!Lists.newArrayList("Puppet", "Util", "Log").equals(name)) {
			// return functions; // wrong ruby file passed.
			// }
			// for (Node n2 : cn.getBodyNode().childNodes()) {
			// if (n.getNodeType() == NodeType.NEWLINENODE)
			// n = ((NewlineNode) n).getNextNode();
			if(n.getNodeType() == NodeType.INSTASGNNODE) {
				InstAsgnNode instAsgn = (InstAsgnNode) n;
				if("@levels".equals(instAsgn.getName())) {
					Object value = new ConstEvaluator().eval(instAsgn.getValue());
					if(!(value instanceof List<?>))
						return functions;
					for(Object o : (List<?>) value) {
						functions.add(new PPFunctionInfo((String) o, false, "Log a message on the server at level " +
								o + "."));
					}

				}
			}

		}

		return functions;

	}

	@Override
	public PPTypeInfo getMetaTypeProperties(File file) throws IOException, RubySyntaxException {
		Result result = internalParse(file);
		return getMetaTypeProperties(result);
	}

	protected PPTypeInfo getMetaTypeProperties(Result result) throws IOException, RubySyntaxException {
		if(result.hasErrors())
			throw new RubySyntaxException(result.getIssues());
		PPTypeFinder typeFinder = new PPTypeFinder();
		PPTypeInfo typeInfo = typeFinder.findMetaTypeInfo(result.getAST());
		return typeInfo;
	}

	@Override
	public PPTypeInfo getMetaTypeProperties(String fileName, Reader reader) throws IOException, RubySyntaxException {
		return getMetaTypeProperties(internalParse(fileName, reader));

	}

	@Override
	public Map<String, String> getRakefileTaskDescriptions(File file) throws IOException {
		return getRakefileTaskDescriptions(internalParse(file));
	}

	/**
	 * @param result
	 *            - the parsed result (without syntax errors)
	 * @return
	 */
	private Map<String, String> getRakefileTaskDescriptions(Result result) {
		RubyRakefileTaskFinder taskFinder = new RubyRakefileTaskFinder();
		Map<String, String> info = taskFinder.findTasks(result.getAST());

		return info;
	}

	@Override
	public List<PPTypeInfo> getTypeInfo(File file) throws IOException, RubySyntaxException {
		final Result result = internalParse(file);
		return getTypeInfo(result, isNagiosLoad(file));
	}

	protected List<PPTypeInfo> getTypeInfo(Result result, boolean nagiosLoad) throws IOException, RubySyntaxException {
		if(result.hasErrors())
			throw new RubySyntaxException(result.getIssues());
		PPTypeFinder typeFinder = new PPTypeFinder();

		if(nagiosLoad)
			return typeFinder.findNagiosTypeInfo(result.getAST());

		List<PPTypeInfo> types = Lists.newArrayList();
		PPTypeInfo typeInfo = typeFinder.findTypeInfo(result.getAST());
		if(typeInfo != null)
			types.add(typeInfo);
		return types;
	}

	@Override
	public List<PPTypeInfo> getTypeInfo(String fileName, Reader reader) throws IOException, RubySyntaxException {
		Result result = internalParse(fileName, reader);
		return getTypeInfo(result, isNagiosLoad(fileName));
	}

	@Override
	public List<PPTypeInfo> getTypePropertiesInfo(File file) throws IOException, RubySyntaxException {
		return getTypePropertiesInfo(internalParse(file));
	}

	public List<PPTypeInfo> getTypePropertiesInfo(Result result) throws IOException, RubySyntaxException {
		List<PPTypeInfo> types = Lists.newArrayList();
		if(result.hasErrors())
			throw new RubySyntaxException(result.getIssues());
		PPTypeFinder typeFinder = new PPTypeFinder();
		List<PPTypeInfo> typeInfo = typeFinder.findTypePropertyInfo(result.getAST());
		if(typeInfo != null)
			types.addAll(typeInfo);
		return types;
	}

	@Override
	public List<PPTypeInfo> getTypePropertiesInfo(String fileName, Reader reader) throws IOException,
			RubySyntaxException {
		return getTypePropertiesInfo(internalParse(fileName, reader));
	}

	/**
	 * Implementation that exposes the Result impl class. Don't want callers of
	 * the JRubyService to see this.
	 * 
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

	protected Result internalParse(String path, Reader reader) throws IOException {
		if(!(reader instanceof BufferedReader))
			reader = new BufferedReader(reader);
		try {
			return internalParse(path, reader, parserConfiguration);
		}
		finally {
			StreamUtil.close(reader);
		}

	}

	/**
	 * Where the parsing "magic" takes place. This impl is used instead of a
	 * similar in the Parser util class since that impl uses a Null warning
	 * collector.
	 * 
	 * @param file
	 * @param content
	 * @param configuration
	 * @return
	 * @throws IOException
	 */
	protected Result internalParse(String file, Reader content, ParserConfiguration configuration) throws IOException {
		RubyParser parser;
		if(configuration.getVersion() == CompatVersion.RUBY1_8) {
			parser = new Ruby18Parser();
		}
		else {
			parser = new Ruby19Parser();
		}
		RubyParserWarningsCollector warnings = new RubyParserWarningsCollector();
		parser.setWarnings(warnings);

		LexerSource lexerSource = LexerSource.getSource(file, content, configuration);

		Node parserResult = null;
		try {
			parserResult = parser.parse(configuration, lexerSource).getAST();

		}
		catch(SyntaxException e) {
			warnings.syntaxError(e);
		}
		return new Result(parserResult, warnings.getIssues());

	}

	@Override
	public boolean isMockService() {
		return false;
	}

	private boolean isNagiosLoad(File file) {
		return isNagiosLoad(file.getAbsolutePath());
	}

	private boolean isNagiosLoad(String filePath) {
		final int nlength = NAGIOS_BASE_PATH.length;
		final IPath path = Path.fromOSString(filePath);
		final int length = path.segmentCount();
		boolean nagiosLoad = true; // until proven wrong
		for(int ix = 0; ix > -4 && nagiosLoad; ix--) {
			nagiosLoad = NAGIOS_BASE_PATH[nlength - 1 + ix].equals(path.segment(length - 1 + ix));
		}
		return nagiosLoad;
	}

	/**
	 * IOExceptions thrown FileNotFound, and while reading
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@Override
	public IRubyParseResult parse(File file) throws IOException {
		return internalParse(file);
	}

	@Override
	public IRubyParseResult parse(String path, Reader reader) throws IOException {
		return internalParse(path, reader);
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
}
