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
package com.puppetlabs.geppetto.forge.util;

import static com.puppetlabs.geppetto.diagnostic.Diagnostic.ERROR;
import static com.puppetlabs.geppetto.diagnostic.Diagnostic.WARNING;
import static com.puppetlabs.geppetto.forge.Forge.FORGE;
import static com.puppetlabs.geppetto.forge.Forge.PACKAGE;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.FileDiagnostic;
import com.puppetlabs.geppetto.forge.v2.model.Dependency;
import com.puppetlabs.geppetto.forge.v2.model.ModuleName;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;
import org.jrubyparser.SourcePosition;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.FixnumNode;
import org.jrubyparser.ast.FloatNode;
import org.jrubyparser.ast.IArgumentNode;
import org.jrubyparser.ast.ListNode;
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
		switch(node.getNodeType()) {
			case STRNODE:
				return ((StrNode) node).getValue();
			case FIXNUMNODE:
				return Long.toString(((FixnumNode) node).getValue());
			case FLOATNODE:
				return Double.toString(((FloatNode) node).getValue());
			default:
				return null;
		}
	}

	private static boolean isValidCall(String key) {
		int idx = validProperties.length;
		while(--idx >= 0)
			if(validProperties[idx].equals(key))
				return true;
		return false;
	}

	private Diagnostic diagnostics;

	private ModuleName fullName;

	private boolean nameSeen;

	private boolean versionSeen;

	protected void addDiagnostic(int severity, SourcePosition pos, String message) {
		FileDiagnostic diag = new FileDiagnostic(severity, FORGE, message, new File(pos.getFile()));
		diag.setLineNumber(pos.getEndLine() + 1);
		diagnostics.addChild(diag);
	}

	protected void addError(SourcePosition pos, String message) {
		if(diagnostics == null)
			throw new IllegalArgumentException(message);
		addDiagnostic(ERROR, pos, message);
	}

	protected void addWarning(SourcePosition pos, String message) {
		if(diagnostics != null)
			addDiagnostic(WARNING, pos, message);
	}

	protected abstract void call(CallSymbol key, SourcePosition pos, List<Argument> arguments);

	protected Dependency createDependency(String name, String versionRequirement, SourcePosition pos) {
		Dependency dep = new DependencyWithPosition(
			pos.getStartOffset(), pos.getEndOffset() - pos.getStartOffset(), pos.getStartLine(),
			new File(pos.getFile()));
		dep.setName(createModuleName(name, true, pos));
		if(versionRequirement != null)
			try {
				dep.setVersionRequirement(VersionRange.create(versionRequirement));
			}
			catch(IllegalArgumentException e) {
				addError(pos, getBadVersionRangeMessage(e));
			}
		return dep;
	}

	protected ModuleName createModuleName(String name, boolean dependency, SourcePosition pos) {
		if(name == null)
			return null;

		name = name.trim();
		if(name.length() == 0)
			return null;

		ModuleName m = null;
		try {
			m = new ModuleName(name, true);
		}
		catch(IllegalArgumentException e1) {
			try {
				m = new ModuleName(name, false);
				addWarning(pos, getBadNameMessage(e1, dependency));
			}
			catch(IllegalArgumentException e2) {
				addError(pos, getBadNameMessage(e2, dependency));
			}
		}
		return m;
	}

	protected Version createVersion(String version, SourcePosition pos) {
		try {
			return Version.create(version);
		}
		catch(IllegalArgumentException e) {
			addError(pos, e.getMessage());
			return null;
		}
	}

	protected String getBadNameMessage(IllegalArgumentException e, boolean dependency) {
		String pfx = dependency
				? "A dependency "
				: "A module ";
		return pfx + e.getMessage();
	}

	protected String getBadVersionRangeMessage(IllegalArgumentException e) {
		return e.getMessage();
	}

	public ModuleName getFullName() {
		return fullName;
	}

	private List<Node> getStringArguments(IArgumentNode callNode) throws IllegalArgumentException {
		Node argsNode = callNode.getArgs();
		if(!(argsNode instanceof ListNode))
			throw new IllegalArgumentException("IArgumentNode expected");
		ListNode args = (ListNode) argsNode;
		int top = args.size();
		ArrayList<Node> stringArgs = new ArrayList<Node>(top);
		for(int idx = 0; idx < top; ++idx) {
			Node argNode = args.get(idx);
			switch(argNode.getNodeType()) {
				case STRNODE:
				case FIXNUMNODE:
				case FLOATNODE:
				case NILNODE:
					stringArgs.add(argNode);
					break;
				default:
					addError(argNode.getPosition(), "Unexpected ruby code. Node type was: " + (argNode == null
							? "null"
							: argNode.getClass().getSimpleName()));
			}
		}
		return stringArgs;
	}

	public boolean isNameSeen() {
		return nameSeen;
	}

	public boolean isVersionSeen() {
		return versionSeen;
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
		nameSeen = false;
		versionSeen = false;
		fullName = null;
		File file = null;
		for(Node node : RubyParserUtils.findNodes(root.getBody(), new NodeType[] { NodeType.FCALLNODE })) {
			FCallNode call = (FCallNode) node;
			SourcePosition pos = call.getPosition();
			if(file == null)
				file = new File(pos.getFile());

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

		if(file != null) {
			if(!nameSeen || fullName != null && (fullName.getOwner() == null || fullName.getName() == null)) {
				diagnostics.addChild(new FileDiagnostic(
					ERROR, PACKAGE, "A full name (user-module) must be specified", file));
			}

			if(!versionSeen) {
				diagnostics.addChild(new FileDiagnostic(ERROR, PACKAGE, "A version must be specified", file));
			}
		}
	}

	public void setFullName(ModuleName fullName) {
		this.fullName = fullName;
	}

	public void setNameSeen() {
		nameSeen = true;
	}

	public void setVersionSeen() {
		versionSeen = true;
	}
}
