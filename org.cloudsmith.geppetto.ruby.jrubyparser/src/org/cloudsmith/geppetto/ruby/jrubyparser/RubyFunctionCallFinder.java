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

import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.ModuleNode;
import org.jrubyparser.ast.Node;

public class RubyFunctionCallFinder {
	
	/**
	 * Returns the first found module with the given qualified name, or null if no such module
	 * was found. The qualified name should be specified in natural order 
	 * e.g. new String[] { "Puppet", "Parser", "Functions" }.
	 * 
	 * @param root
	 * @param qualifiedName
	 * @return found module or null
	 */
	public FCallNode findFuntion(ModuleNode root, String name) {
		return new FunctionVisitor().findFunction(root, name);
	}
	
	private static class FunctionVisitor extends AbstractJRubyVisitor {

		/**
		 * Returned when a visited node detect it is not meaningful to visit its 
		 * children.
		 */
		public static final Object DO_NOT_VISIT_CHILDREN = new Object();
		
		private String name = null;
		public FCallNode findFunction(Node root, String name) {
			this.name = name;
			return (FCallNode) findFunction(root);
		}
		
		/**
		 * Visits all nodes in graph, and if visitor returns non-null, the iteration stops
		 * and the returned non-null value is returned.
		 * @param root
		 * @return
		 */
		private Object findFunction(Node root) {
			Object r = root.accept(this);
			if(r != DO_NOT_VISIT_CHILDREN) {
				if(r != null) {
					return r;
				}
				for(Node n : root.childNodes()) {
					r = findFunction(n);
					if(r != null)
						return r;
				}
			}
			return null;
		}
		@Override
		public Object visitFCallNode(FCallNode iVisited) {
			if(name.equals(iVisited.getName()))
					return iVisited;
			return null;
		}
	}
	
}
