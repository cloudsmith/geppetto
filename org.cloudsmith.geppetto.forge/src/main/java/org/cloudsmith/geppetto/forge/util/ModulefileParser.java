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

	private static String getString(Node node) {
		return node instanceof NilNode
				? null
				: ((StrNode) node).getValue();
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

	protected abstract void call(String key, SourcePosition pos, String value, SourcePosition vp);

	protected abstract void call(String key, SourcePosition pos, String value1, SourcePosition vp1, String value2,
			SourcePosition vp2);

	protected abstract void call(String key, SourcePosition pos, String value1, SourcePosition vp1, String value2,
			SourcePosition vp2, String value3, SourcePosition vp3);

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
		int idx = validProperties.length;
		while(--idx >= 0)
			if(validProperties[idx].equals(key)) {
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
				break;
			}
		addError(pos, bld.toString());
	}

	protected void parseRubyAST(RootNode root, Diagnostic diagnostics) {
		this.diagnostics = diagnostics;
		for(Node node : RubyParserUtils.findNodes(root.getBody(), new NodeType[] { NodeType.FCALLNODE })) {
			FCallNode call = (FCallNode) node;
			SourcePosition pos = call.getPosition();
			String key = call.getName();
			List<Node> args = getStringArguments(call);
			int nargs = args.size();
			if(nargs > 0 && nargs <= 3) {
				Node n1 = args.get(0);
				SourcePosition p1 = n1.getPosition();
				if(nargs > 1) {
					Node n2 = args.get(1);
					SourcePosition p2 = n2.getPosition();
					if(nargs > 2) {
						Node n3 = args.get(2);
						SourcePosition p3 = n3.getPosition();
						call(key, pos, getString(n1), p1, getString(n2), p2, getString(n3), p3);
					}
					else {
						call(key, pos, getString(n1), p1, getString(n2), p2);
					}
				}
				else {
					call(key, pos, getString(n1), p1);
				}
			}
			else {
				noResponse(key, pos, args.size());
			}
		}
	}
}
