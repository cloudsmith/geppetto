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

import org.jrubyparser.ast.BlockAcceptingNode;
import org.jrubyparser.ast.CallNode;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.IArgumentNode;
import org.jrubyparser.ast.INameNode;
import org.jrubyparser.ast.Node;

/**
 * Unifies the two types of calls (FCallNode and CallNode) into one class.
 */
public class GenericCallNode implements INameNode, IArgumentNode, BlockAcceptingNode{
	private final FCallNode fcallNode;
	private final CallNode callNode;
	
	public GenericCallNode(FCallNode node) {
		fcallNode = node;
		callNode = null;
	}
	public GenericCallNode(CallNode node) {
		fcallNode = null;
		callNode = node;
	}
	public boolean isValid() {
		return fcallNode != null || callNode != null;
	}
	@Override
	public Node getIterNode() {
		return (callNode == null ? fcallNode : callNode).getIterNode();
	}

	@Override
	public Node setIterNode(Node iterNode) {
		return (callNode == null ? fcallNode : callNode).setIterNode(iterNode);
	}

	@Override
	public Node getArgsNode() {
		return (callNode == null ? fcallNode : callNode).getArgsNode();
	}

	@Override
	public Node setArgsNode(Node argsNode) {
		return (callNode == null ? fcallNode : callNode).setArgsNode(argsNode);
	}
	@Override
	public String getName() {
		return (callNode == null ? fcallNode : callNode).getName();
	}
	
}