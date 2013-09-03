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
package com.puppetlabs.geppetto.ruby.jrubyparser;

import java.util.List;

import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.ModuleNode;
import org.jrubyparser.ast.NewlineNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;

import com.google.common.collect.Lists;

/**
 * Finds a FCallNode with a given name (if given a ModuleNode to search).
 * 
 * @deprecated use {@link RubyCallFinder} instead (or improve that class).
 */
@Deprecated
public class RubyFunctionCallFinder {

	private static class FunctionVisitor extends AbstractJRubyVisitor {

		/**
		 * Returned when a visited node detect it is not meaningful to visit its
		 * children.
		 */
		public static final Object DO_NOT_VISIT_CHILDREN = new Object();

		private String name = null;

		/**
		 * Visits all nodes in graph, and if visitor returns non-null, the
		 * iteration stops and the returned non-null value is returned.
		 * 
		 * @param root
		 * @return
		 */
		private Object findFunction(Node root) {
			Object r = null;
			if (root.getNodeType() == NodeType.FCALLNODE)
				r = root.accept(this);
			if (r != DO_NOT_VISIT_CHILDREN) {
				if (r != null) {
					return r;
				}
				for (Node n : root.childNodes()) {
					r = findFunction(n);
					if (r != null)
						return r;
				}
			}
			return null;
		}

		public FCallNode findFunction(Node root, String name) {
			this.name = name;
			return (FCallNode) findFunction(root);
		}

		/**
		 * Find function calls to the given name if made from a direct child (or
		 * a newline node) in the given root.
		 * 
		 * @param root
		 * @param name
		 * @return
		 */
		public List<FCallNode> findFunctionsInNode(Node root, String name) {
			this.name = name;
			List<FCallNode> result = Lists.newArrayList();
			for (Node n : root.childNodes()) {
				if (n.getNodeType() == NodeType.NEWLINENODE)
					n = ((NewlineNode) n).getNextNode();
				if (n.getNodeType() == NodeType.FCALLNODE)
					if (name.equals(((FCallNode) n).getName()))
						result.add((FCallNode) n);
			}
			return result;
		}

		@Override
		public Object visitFCallNode(FCallNode iVisited) {
			if (name.equals(iVisited.getName()))
				return iVisited;
			return null;
		}
	}

	/**
	 * Returns a list of function nodes with the given name, or an empty list if
	 * no such function was found.
	 * 
	 * @param node
	 * @param name
	 * @return
	 */
	public List<FCallNode> findFunctions(Node node, String name) {
		return new FunctionVisitor().findFunctionsInNode(node, name);

	}

	/**
	 * Returns the first found function with the given name, or null if no such
	 * function was found.
	 * 
	 * @param root
	 * @param name
	 *            the name of the function to find
	 * @return found function node or null
	 */
	public FCallNode findFuntion(ModuleNode root, String name) {
		return new FunctionVisitor().findFunction(root, name);
	}

}
