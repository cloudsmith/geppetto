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

import java.util.LinkedList;
import java.util.List;

import org.jrubyparser.ast.Colon2Node;
import org.jrubyparser.ast.ConstNode;
import org.jrubyparser.ast.ModuleNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;

import com.google.common.collect.Lists;

public class RubyModuleFinder {

	private static class ConstEvaluator extends AbstractJRubyVisitor {
		private List<String> addAll(List<String> a, List<String> b) {
			a.addAll(b);
			return a;
		}

		public List<String> eval(Node node) {
			if(node == null)
				return Lists.newArrayList();
			return stringList(node.accept(this));
		}

		private List<String> splice(Object a, Object b) {
			return addAll(stringList(a), stringList(b));
		}

		@SuppressWarnings("unchecked")
		private List<String> stringList(Object x) {
			if(x instanceof List)
				return (List<String>) x; // have faith
			if(x instanceof String)
				return Lists.newArrayList((String) x);
			throw new IllegalArgumentException("Not a string or lists of strings");
		}

		@Override
		public Object visitColon2Node(Colon2Node iVisited) {
			return splice(eval(iVisited.getLeftNode()), iVisited.getName());
		}

		@Override
		public Object visitConstNode(ConstNode iVisited) {
			return iVisited.getName();
		}
	}

	private static class ModuleVisitor extends AbstractJRubyVisitor {

		/**
		 * Returned when a visited node detect it is not meaningful to visit its
		 * children.
		 */
		public static final Object DO_NOT_VISIT_CHILDREN = new Object();

		private LinkedList<Object> stack = null;

		private LinkedList<Object> nameStack = null;

		private List<String> qualifiedName = null;

		private ConstEvaluator constEvaluator = new ConstEvaluator();

		/**
		 * Visits all nodes in graph, and if visitor returns non-null, the
		 * iteration stops and the returned non-null value is returned.
		 * 
		 * @param root
		 * @return
		 */
		/*
		private Object findModule(Node root) {
			push(root);
			Object r = null;
			// ArgumentNode does not allow visitors !!! WTF.
			if(root.getNodeType() != NodeType.ARGUMENTNODE)
				r = root.accept(this);
			if(r != DO_NOT_VISIT_CHILDREN) {
				if(r != null) {
					return r;
				}
				for(Node n : root.childNodes()) {
					r = findModule(n);
					if(r != null)
						return r;
				}
			}
			pop(root);
			return null;
		}*/

		public ModuleNode findModule(Node root, String[] qualifiedName) {
			this.stack = Lists.newLinkedList();
			this.nameStack = Lists.newLinkedList();
			// NOTE: opportunity to make this better if guava a.k.a
			// google.collect 2.0 is used
			// since it has a Lists.reverse method - now this ugly construct is
			// used.
			this.qualifiedName = Lists.newArrayList(Lists.reverse(Lists.newArrayList(qualifiedName)));
			return (ModuleNode) findModule2(root);
		}

		private Object findModule2(Node root) {
			push(root);
			Object r = null;
			if(root.getNodeType() == NodeType.MODULENODE)
				r = visitModuleNode((ModuleNode) root);
			if(r != DO_NOT_VISIT_CHILDREN) {
				if(r != null) {
					return r;
				}
				for(Node n : root.childNodes()) {
					r = findModule2(n);
					if(r != null)
						return r;
				}
			}
			pop(root);
			return null;

		}

		private void pop(Node n) {
			while(stack.peek() != n) {
				Object x = stack.pop();
				if(x instanceof String)
					popName();
			}
			stack.pop();
		}

		private void popName() {
			nameStack.pop();
		}

		private void push(Node n) {
			stack.push(n);
		}

		private void pushName(String name) {
			stack.push(name);
			nameStack.push(name);
		}

		private void pushNames(List<String> names) {
			for(String name : names)
				pushName(name);
		}

		@Override
		public Object visitModuleNode(ModuleNode iVisited) {
			// Evaluate the name(s)
			pushNames(constEvaluator.eval(iVisited.getCPath()));

			// if an inner module of the wanted module is found
			// i.e. we find module a::b::c::d when we are looking for a::b::c
			//
			if(nameStack.size() > qualifiedName.size())
				return DO_NOT_VISIT_CHILDREN;

			// if it is the wanted module
			if(nameStack.size() == qualifiedName.size())
				return qualifiedName.equals(nameStack)
						? iVisited
						: DO_NOT_VISIT_CHILDREN;

			// the module's name is shorter than wanted, does it match so far?
			// i.e. we find module a::b when we are looking for a::b::c
			//
			int sizeX = qualifiedName.size();
			int sizeY = nameStack.size();
			try {
				return qualifiedName.subList(sizeX - sizeY, sizeX).equals(nameStack)
						? null
						: DO_NOT_VISIT_CHILDREN;
			}
			catch(IndexOutOfBoundsException e) {
				return DO_NOT_VISIT_CHILDREN;
			}
		}
	}

	/**
	 * Returns the first found module with the given qualified name, or null if
	 * no such module was found. The qualified name should be specified in
	 * natural order e.g. new String[] { "Puppet", "Parser", "Functions" }.
	 * 
	 * @param root
	 * @param qualifiedName
	 * @return found module or null
	 */
	public ModuleNode findModule(Node root, String[] qualifiedName) {
		return new ModuleVisitor().findModule(root, qualifiedName);
	}

}
