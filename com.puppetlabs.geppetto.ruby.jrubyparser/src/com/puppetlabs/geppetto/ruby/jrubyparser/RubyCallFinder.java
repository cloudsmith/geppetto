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

import org.jrubyparser.ast.CallNode;
import org.jrubyparser.ast.ClassNode;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.ModuleNode;
import org.jrubyparser.ast.NewlineNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;

import com.google.common.collect.Lists;

/**
 * Finds calls in a parsed ruby AST. An instance of this class can be reused,
 * but it is not threadsafe.
 * 
 * Calls are found using a FQN - i.e. a sequence of module/receiver names, where
 * the last name segment is the name of the function. The search will find
 * function calls irrespective of call type (e.g. a FCallNode (where receiver is
 * implied), or CallNode where receiver is explicit. All FQN names are appended
 * to the current scope - i.e. if a call is made to X::Y::foo() in module A, it
 * will be found by a search of A::X::Y::foo().
 * 
 * TODO: global references are not handled - i.e. if a call to ::X::Y::foo() is
 * made inside module A, it will be recognized (in error) as A::X::Y::foo() -
 * also see {@link ConstEvaluator}.
 */
public class RubyCallFinder {

	/**
	 * Keeps track of where processing is in the node model.
	 */
	private LinkedList<Object> stack = null;

	/**
	 * Keeps track of the scope name.
	 */
	private LinkedList<Object> nameStack = null;

	/**
	 * The wanted FQN
	 */
	private List<String> qualifiedName = null;

	/**
	 * An evaluator of constant ruby expressions
	 */
	private ConstEvaluator constEvaluator = new ConstEvaluator();

	public GenericCallNode findCall(Node root, String... qualifiedName) {
		if(qualifiedName.length < 1)
			throw new IllegalArgumentException("qualifiedName can not be empty");

		this.stack = Lists.newLinkedList();
		this.nameStack = Lists.newLinkedList();

		// OLD CODE, NOW FIXED
		// opportunity to make this better if guava a.k.a google.collect
		// 2.0 is used
		// since it has a Lists.reverse method - now this ugly construct is
		// used.
		// OLD CODE
		// this.qualifiedName = Lists.newArrayList(Iterables.reverse(Lists
		// .newArrayList(qualifiedName)));
		this.qualifiedName = Lists.reverse(Lists.newArrayList(qualifiedName));

		List<GenericCallNode> result = findCallInternal(root, true);
		return result == null || result.size() != 1
				? null
				: result.get(0);
	}

	private List<GenericCallNode> findCallInternal(Node root, boolean findFirst) {
		push(root);
		List<GenericCallNode> result = null;

		SEARCH: {
			switch(root.getNodeType()) {
				case MODULENODE:
					ModuleNode module = (ModuleNode) root;
					// Evaluate the name(s)
					pushNames(constEvaluator.stringList(constEvaluator.eval(module.getCPath())));
					if(!inCompatibleScope())
						break SEARCH;
					break; // search children

				case CLASSNODE:
					ClassNode classNode = (ClassNode) root;
					pushNames(constEvaluator.stringList(constEvaluator.eval(classNode.getCPath())));
					if(!inCompatibleScope())
						break SEARCH;
					break; // search children

				case CALLNODE:
					CallNode callNode = (CallNode) root;
					if(!callNode.getName().equals(qualifiedName.get(0)))
						break SEARCH;
					pushNames(constEvaluator.stringList(constEvaluator.eval(callNode.getReceiver())));
					if(inWantedScope())
						return Lists.newArrayList(new GenericCallNode(callNode));
					pop(root); // clear the pushed names
					push(root); // push it again
					break; // continue search inside the function

				case FCALLNODE:
					FCallNode fcallNode = (FCallNode) root;
					if(!fcallNode.getName().equals(qualifiedName.get(0)))
						break SEARCH;
					if(inWantedScope())
						return Lists.newArrayList(new GenericCallNode(fcallNode));
					break; // continue search inside the function
				default:
					break;
			}

			for(Node n : root.childNodes()) {
				if(n.getNodeType() == NodeType.NEWLINENODE)
					n = ((NewlineNode) n).getNextNode();
				List<GenericCallNode> r = findCallInternal(n, findFirst);
				if(r != null) {
					if(result == null)
						result = r;
					else
						result.addAll(r);
					// only collect one
					if(findFirst)
						return result;
				}
			}
		} // SEARCH

		pop(root);
		// return a found result or null
		return result == null || result.size() == 0
				? null
				: result;
	}

	public List<GenericCallNode> findCalls(Node root, String... qualifiedName) {
		if(qualifiedName.length < 1)
			throw new IllegalArgumentException("qualifiedName can not be empty");

		this.stack = Lists.newLinkedList();
		this.nameStack = Lists.newLinkedList();

		// NOTE: opportunity to make this better if guava a.k.a google.collect
		// 2.0 is used
		// since it has a Lists.reverse method - now this ugly construct is
		// used.
		this.qualifiedName = Lists.reverse(Lists.newArrayList(qualifiedName));

		// TODO: make this return more than one
		return findCallInternal(root, false);
	}

	/**
	 * If in the exact scope, or if scope is an outer scope of the wanted scope.
	 * 
	 * @return true if wanted or outer scope of wanted
	 */
	private boolean inCompatibleScope() {
		// if an inner module of the wanted module is found
		// i.e. we find module a::b::c::d(::_)* when we are looking for
		// a::b::c::FUNC
		//
		if(nameStack.size() >= qualifiedName.size())
			return false; // will not be found in this module - it is out of
							// scope

		// the module's name is shorter than wanted, does it match so far?
		// i.e. we find module a::b when we are looking for a::b::c::FUNC
		//
		int sizeX = qualifiedName.size();
		int sizeY = nameStack.size();
		try {
			return qualifiedName.subList(sizeX - sizeY, sizeX).equals(nameStack)
					? true
					: false;
		}
		catch(IndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * Returns true if the current scope is the wanted scope.
	 * 
	 * @return
	 */
	private boolean inWantedScope() {
		// we are in wanted scope if all segments (except the function name)
		// match.
		try {
			return qualifiedName.subList(1, qualifiedName.size()).equals(nameStack)
					? true
					: false;
		}
		catch(IndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * Pops the stack until we are the previous scope.
	 * 
	 * @param n
	 */
	private void pop(Node n) {
		while(stack.peek() != n) {
			Object x = stack.pop();
			if(x instanceof String)
				popName();
		}
		stack.pop();
	}

	/**
	 * Pops a name of the namestack - should not be called without also managing
	 * the scope stack.
	 */
	private void popName() {
		nameStack.pop();
	}

	/**
	 * Push node so we know where we are in scope.
	 * 
	 * @param n
	 */
	private void push(Node n) {
		stack.push(n);
	}

	/**
	 * Pushes a name onto the name stack (and the scope stack, as we need to
	 * discard the names when leaving a named scope).
	 * 
	 * @param name
	 */
	private void pushName(String name) {
		stack.push(name);
		nameStack.push(name);
	}

	/**
	 * Convenience for pushing 0-n names in a list. Same as call {@link #pushName(String)} for each.
	 * 
	 * @param names
	 */
	private void pushNames(List<String> names) {
		for(String name : names)
			pushName(name);
	}
}
