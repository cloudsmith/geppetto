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
package org.cloudsmith.geppetto.ruby.jrubyparser;

import org.jrubyparser.SourcePosition;
import org.jrubyparser.ast.BlockAcceptingNode;
import org.jrubyparser.ast.CallNode;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.IArgumentNode;
import org.jrubyparser.ast.INameNode;
import org.jrubyparser.ast.Node;

/**
 * Unifies the two types of calls (FCallNode and CallNode) into one class.
 */
public class GenericCallNode implements INameNode, IArgumentNode, BlockAcceptingNode {
	private final FCallNode fcallNode;

	private final CallNode callNode;

	public GenericCallNode(CallNode node) {
		fcallNode = null;
		callNode = node;
	}

	public GenericCallNode(FCallNode node) {
		fcallNode = node;
		callNode = null;
	}

	@Override
	public Node getArgs() {
		return (callNode == null
				? fcallNode
				: callNode).getArgs();
	}

	@Override
	public Node getIter() {
		return (callNode == null
				? fcallNode
				: callNode).getIter();
	}

	@Override
	public String getName() {
		return (callNode == null
				? fcallNode
				: callNode).getName();
	}

	public Node getNode() {
		return callNode == null
				? fcallNode
				: callNode;
	}

	public SourcePosition getPosition() {
		return (callNode == null
				? fcallNode
				: callNode).getPosition();
	}

	@Override
	public boolean hasParens() {
		return (callNode == null
				? fcallNode
				: callNode).hasParens();
	}

	public boolean isValid() {
		return fcallNode != null || callNode != null;
	}

	@Override
	public void setArgs(Node argsNode) {
		(callNode == null
				? fcallNode
				: callNode).setArgs(argsNode);
	}

	@Override
	public void setHasParens(boolean hasParens) {
		(callNode == null
				? fcallNode
				: callNode).setHasParens(hasParens);
	}

	@Override
	public void setIter(Node iterNode) {
		(callNode == null
				? fcallNode
				: callNode).setIter(iterNode);
	}

	@Override
	public void setName(String newName) {
		(callNode == null
				? fcallNode
				: callNode).setName(newName);
	}

}
