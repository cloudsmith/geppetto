/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.xtext.serializer;

import java.util.List;

import org.eclipse.xtext.nodemodel.INode;

/**
 * Interface used to enable reconciliation of comments after a semantic modification.
 */
public interface ICommentReconcilement {

	/**
	 * Given three nodes produce the set of comment nodes that should be inserted between
	 * the nodes {@code last} and {@code current}. The {@code preceding} node is the semantic node immediately preceding
	 * the {@code last} node.
	 * <p>
	 * If first called with {@code A, B, C} the next call should be for {@code B, C, D} etc.
	 * </p>
	 */
	List<INode> commentNodesFor(INode preceding, INode last, INode current);

	/**
	 * Return a string with the whitespace content that should appear between the two given nodes. The prevCommentNode is null
	 * for the first node of a sequence.
	 * 
	 * @param prevCommentNode
	 * @param node
	 * @return
	 */
	String getWhitespaceBetween(INode prevCommentNode, INode node);

	/**
	 * Return true, if the given (comment) node is a node that will be reconciled (and thus not included in its
	 * "non reconciled" position unless this reconcilement says so by returning it in {@link #commentNodesFor(INode, INode, INode)}).
	 * 
	 * @param node
	 *            - the node to check if it is reconciled or not
	 * @return - true if reconciled
	 */
	boolean isReconciledCommentNode(INode node);
}
