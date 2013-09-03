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
import java.util.Map;

import org.jrubyparser.ast.ArrayNode;
import org.jrubyparser.ast.CallNode;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.HashNode;
import org.jrubyparser.ast.ListNode;
import org.jrubyparser.ast.NewlineNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Finds Rakefile task creations in a parsed ruby AST. An instance of this class can be reused,
 * but it is not threadsafe.
 * 
 * TODO: text from CallFinder - change this
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
public class RubyRakefileTaskFinder {

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
	// private List<String> qualifiedName = null;

	private List<String> cucumberTask = Lists.newArrayList("Cucumber", "Rake", "Task");

	private List<String> rspecTask = Lists.newArrayList("RSpec", "Core", "RakeTask");

	/**
	 * An evaluator of constant ruby expressions
	 */
	private ConstEvaluator constEvaluator = new ConstEvaluator();

	// public List<GenericCallNode> findCalls(Node root, String... qualifiedName) {
	// if(qualifiedName.length < 1)
	// throw new IllegalArgumentException("qualifiedName can not be empty");
	//
	// this.stack = Lists.newLinkedList();
	// this.nameStack = Lists.newLinkedList();
	//
	// this.qualifiedName = Lists.reverse(Lists.newArrayList(qualifiedName));
	//
	// // TODO: make this return more than one
	// return findTaskInternal(root, false);
	// }
	private String lastDesc;

	public Map<String, String> findTasks(Node root) {
		// use a linked map to get entries in the order they are added
		Map<String, String> resultMap = Maps.newLinkedHashMap();

		this.stack = Lists.newLinkedList();
		this.nameStack = Lists.newLinkedList();

		// this.qualifiedName = Lists.reverse(Lists.newArrayList(qualifiedName));
		if(root != null)
			findTasksInternal(root, resultMap);
		return resultMap;
	}

	private void findTasksInternal(Node root, Map<String, String> resultMap) {
		SEARCH: {
			switch(root.getNodeType()) {
				case MODULENODE:
					lastDesc = ""; // not valid now

					break SEARCH; // don't know what to do when user declares a module

				case CLASSNODE:
					lastDesc = ""; // not valid now

					// don't know what to do when user declares a class, any calls to
					// create tasks could be inside a loop etc. Just impossible to figure out
					break SEARCH;

				case CALLNODE:
					// don't know yet what the calls look like - if they are calls to "do" with
					// the interesting part on the left
					// or what...
					//
					// CallNode callNode = (CallNode) root;
					if(!processCallNode((CallNode) root, resultMap))
						break SEARCH;
					break;

				// if(!callNode.getName().equals(qualifiedName.get(0)))
				// break SEARCH;
				// pushNames(constEvaluator.stringList(constEvaluator.eval(callNode.getReceiverNode())));
				// if(inWantedScope())
				// return Lists.newArrayList(new GenericCallNode(callNode));
				// pop(root); // clear the pushed names
				// push(root); // push it again
				// break; // continue search inside the function

				case FCALLNODE:
					// Don't know what the interesting calls look like
					if(!processFCallNode((FCallNode) root, resultMap))
						break SEARCH;
					break;
				// FCallNode fcallNode = (FCallNode) root;
				// if(!fcallNode.getName().equals(qualifiedName.get(0)))
				// break SEARCH;
				// if(inWantedScope())
				// return Lists.newArrayList(new GenericCallNode(fcallNode));
				// break; // continue search inside the function
				default:
					break;
			}

			for(Node n : root.childNodes()) {
				if(n.getNodeType() == NodeType.NEWLINENODE)
					n = ((NewlineNode) n).getNextNode();
				findTasksInternal(n, resultMap);
			}
		} // SEARCH

	}

	/**
	 * If the argsNode is an Array with a Hash, then return the node for the first key (the name).
	 * This construct is found for input task :foo => 'dependson', or :foo => ['depdendson', 'andonthis']
	 * 
	 * @param argsNode
	 * @return
	 */
	private Node getTaskNameNodeFromArgNode(Node argsNode) {
		if(argsNode instanceof ArrayNode) {
			ArrayNode arrayNode = (ArrayNode) argsNode;
			if(arrayNode.size() > 0) {
				Node a = arrayNode.get(0);
				if(a instanceof HashNode) {
					HashNode argsHash = (HashNode) a;
					ListNode listNode = argsHash.getListNode();
					if(listNode.size() > 0)
						argsNode = listNode.get(0);
				}
			}
		}
		return argsNode;
	}

	/**
	 * If in the exact scope, or if scope is an outer scope of the wanted scope.
	 * 
	 * @return true if wanted or outer scope of wanted
	 */
	/*
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
	}*/

	/**
	 * Returns true if the current scope is the wanted scope.
	 * 
	 * @return
	 */
	/*
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
	}*/

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
	 * @param root
	 * @return
	 */
	private boolean processCallNode(CallNode root, Map<String, String> resultMap) {
		String mName = root.getName();
		try {
			if(mName.equals("new")) {
				List<String> receiver = constEvaluator.stringList(constEvaluator.eval(root.getReceiver()));
				boolean isRspec = receiver.equals(rspecTask);
				boolean isCucumber = !isRspec && receiver.equals(cucumberTask);
				if(isRspec || isCucumber) {
					// recognized as a task
					Node argsNode = getTaskNameNodeFromArgNode(root.getArgs());
					List<String> nameList = constEvaluator.stringList(constEvaluator.eval(argsNode));
					if(nameList.size() < 1) {
						if(isRspec)
							nameList = Lists.newArrayList("spec");
						else
							nameList = Lists.newArrayList("cucumber");
					}
					String taskName = Joiner.on(":").join(Iterables.concat(Lists.reverse(nameStack), nameList));
					resultMap.put(taskName, lastDesc);
					// System.err.println("Added task: " + taskName + " with description: " + lastDesc);
					lastDesc = ""; // consumed
				}
			}
		}
		catch(RuntimeException e) {
			// Failed to handle some constant evaluation - not sure what, should not fail, could be
			// caused by faulty ruby code (syntax errors etc.) causing a strange model.
			// Should be handled elsewhere.
			return false;
		}
		return false;
	}

	/**
	 * @param root
	 * @return
	 */
	private boolean processFCallNode(FCallNode root, Map<String, String> resultMap) {
		final String fName = root.getName();
		if(fName.equals("namespace")) {
			// System.err.println("Found NAMESPACE (Fcall)");
			// push the name on the nameStack
			// this is the argument to the namespace function
			push(root);
			pushNames(constEvaluator.stringList(constEvaluator.eval(root.getArgs())));

			for(Node n : root.childNodes()) {
				if(n.getNodeType() == NodeType.NEWLINENODE)
					n = ((NewlineNode) n).getNextNode();
				findTasksInternal(n, resultMap);
			}
			pop(root);
		}
		else if(fName.equals("task")) {
			// System.err.println("Found TASK (Fcall)");
			// argument is the name of the task
			// prepend with name parts from namespaces
			// pick up lastDesc
			Node argsNode = getTaskNameNodeFromArgNode(root.getArgs());
			String taskName = Joiner.on(":").join(
				Iterables.concat(Lists.reverse(nameStack), constEvaluator.stringList(constEvaluator.eval(argsNode))));

			resultMap.put(taskName, lastDesc);
			// System.err.println("Added task: " + taskName + " with description: " + lastDesc);
			lastDesc = ""; // consumed
		}
		else if(fName.equals("desc")) {
			// argument is the description
			lastDesc = Joiner.on("").join(constEvaluator.stringList(constEvaluator.eval(root.getArgs())));
		}
		else {
			lastDesc = ""; // consume, not valid any more
		}
		return false;
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
