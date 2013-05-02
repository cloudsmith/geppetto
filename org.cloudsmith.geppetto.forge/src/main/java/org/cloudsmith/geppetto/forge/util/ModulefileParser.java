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
package org.cloudsmith.geppetto.forge.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.diagnostic.FileDiagnostic;
import org.cloudsmith.geppetto.forge.Forge;
import org.jrubyparser.SourcePosition;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.IArgumentNode;
import org.jrubyparser.ast.ListNode;
import org.jrubyparser.ast.NilNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;
import org.jrubyparser.ast.RootNode;
import org.jrubyparser.ast.StrNode;

/**
 * An Modulefile parser that produces calls with one, two, or three string
 * arguments.
 */
public abstract class ModulefileParser {
	private static final String[] validProperties = new String[] {
			"name", "author", "description", "license", "project_page", "source", "summary", "version", "dependency" };

	private static Argument createArgument(Node n) {
		SourcePosition p = n.getPosition();
		String v = getString(n);
		return new Argument(p.getStartOffset(), p.getEndOffset() - p.getStartOffset(), v);
	}

	private static String getString(Node node) {
		return node instanceof NilNode
				? null
				: ((StrNode) node).getValue();
	}

	private static boolean isValidCall(String key) {
		int idx = validProperties.length;
		while(--idx >= 0)
			if(validProperties[idx].equals(key))
				return true;
		return false;
	}

	private Diagnostic diagnostics;

	protected void addDiagnostic(int severity, SourcePosition pos, String message) {
		FileDiagnostic diag = new FileDiagnostic(severity, Forge.FORGE, message, new File(pos.getFile()));
		diag.setLineNumber(pos.getEndLine() + 1);
		diagnostics.addChild(diag);
	}

	protected void addError(SourcePosition pos, String message) {
		if(diagnostics == null)
			throw new IllegalArgumentException(message);
		addDiagnostic(Diagnostic.ERROR, pos, message);
	}

	protected void addWarning(SourcePosition pos, String message) {
		if(diagnostics != null)
			addDiagnostic(Diagnostic.WARNING, pos, message);
	}

	protected abstract void call(CallSymbol key, SourcePosition pos, List<Argument> arguments);

	private List<Node> getStringArguments(IArgumentNode callNode) throws IllegalArgumentException {
		Node argsNode = callNode.getArgs();
		if(!(argsNode instanceof ListNode))
			throw new IllegalArgumentException("IArgumentNode expected");
		ListNode args = (ListNode) argsNode;
		int top = args.size();
		ArrayList<Node> stringArgs = new ArrayList<Node>(top);
		for(int idx = 0; idx < top; ++idx) {
			Node argNode = args.get(idx);
			if(argNode instanceof StrNode || argNode instanceof NilNode)
				stringArgs.add(argNode);
			else
				addError(argNode.getPosition(), "Unexpected ruby code. Node type was: " + (argNode == null
						? "null"
						: argNode.getClass().getSimpleName()));
		}
		return stringArgs;
	}

	protected void noResponse(String key, SourcePosition pos, int nargs) {
		StringBuilder bld = new StringBuilder();
		bld.append('\'');
		bld.append(key);
		bld.append("' is not a metadata property");
		if(isValidCall(key)) {
			// This is a valid property so the number of arguments
			// must be wrong.
			bld.append(" that takes ");
			if(nargs > 0)
				bld.append(nargs);
			else
				bld.append("no");
			bld.append(" argument");
			if(nargs == 0 || nargs > 1)
				bld.append('s');
		}
		addError(pos, bld.toString());
	}

	public void parseRubyAST(RootNode root, Diagnostic diagnostics) {
		this.diagnostics = diagnostics;
		for(Node node : RubyParserUtils.findNodes(root.getBody(), new NodeType[] { NodeType.FCALLNODE })) {
			FCallNode call = (FCallNode) node;
			SourcePosition pos = call.getPosition();
			String key = call.getName();
			List<Node> args = getStringArguments(call);
			int nargs = args.size();
			if(nargs > 3 || !isValidCall(key)) {
				noResponse(key, pos, nargs);
				continue;
			}

			CallSymbol callSymbol;
			try {
				callSymbol = CallSymbol.valueOf(key);
			}
			catch(IllegalArgumentException e) {
				noResponse(key, pos, nargs);
				continue;
			}

			List<Argument> arguments;
			if(nargs == 0)
				arguments = Collections.emptyList();
			else {
				if(nargs == 1)
					arguments = Collections.singletonList(createArgument(args.get(0)));
				else {
					arguments = new ArrayList<Argument>(nargs);
					for(int idx = 0; idx < nargs; ++idx)
						arguments.add(createArgument(args.get(idx)));
				}
			}
			call(callSymbol, pos, arguments);
		}
	}
}
