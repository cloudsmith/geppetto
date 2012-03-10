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
package org.cloudsmith.xtext.dommodel.impl;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.eclipse.xtext.nodemodel.INode;

/**
 * Adapts INode to IDomNode
 * 
 */
public class NodeModelDomNodes {
	// public class NodeModelCompositeNode extends NodeModelNode {
	//
	// public NodeModelCompositeNode(ICompositeNode node, IDomNode parent) {
	// super(node, parent);
	// }
	//
	// @Override
	// public BidiIterable<IDomNode> getChildren() {
	// return new TransformingBidiIterable(getCompositeNode().getChildren(), this);
	// }
	//
	// private ICompositeNode getCompositeNode() {
	// return (ICompositeNode) getNode();
	// }
	//
	// @Override
	// public boolean hasChildren() {
	// return getCompositeNode().hasChildren();
	// }
	//
	// }
	//
	// public class NodeModelLeafNode extends NodeModelNode {
	//
	// public NodeModelLeafNode(ILeafNode node, IDomNode parent) {
	// super(node, parent);
	// }
	//
	// }
	//
	// public class NodeModelNode extends AbstractDomNode {
	// private INode node;
	//
	// public NodeModelNode(INode node, IDomNode parent) {
	// this.node = node;
	// this.setParent(parent);
	// if(node instanceof ILeafNode) {
	// ILeafNode leaf = (ILeafNode) node;
	// flip(leaf.isHidden(), NodeStatus.HIDDEN);
	// flip(tokenUtil.isCommentNode(node), NodeStatus.COMMENT);
	// flip(tokenUtil.isWhitespaceNode(node), NodeStatus.WHITESPACE);
	// flip(node.getSyntaxErrorMessage() == null, NodeStatus.CONTAINS_ERROR);
	//
	// // TODO: CONTAINS whitespace, comment, hidden, if this is a composite node
	// // search children and set status.
	// }
	// }
	//
	// @Override
	// public EObject getGrammarElement() {
	// return node.getGrammarElement();
	// }
	//
	// @Override
	// public int getLength() {
	// return node.getLength();
	// }
	//
	// @Override
	// public INode getNode() {
	// return node;
	// }
	//
	// @Override
	// public int getOffset() {
	// return node.getOffset();
	// }
	//
	// @Override
	// public EObject getSemanticElement() {
	// return node.getSemanticElement();
	// }
	//
	// @Override
	// public String getText() {
	// return node.getText();
	// }
	//
	// @Override
	// public boolean hasChildren() {
	// return node instanceof ILeafNode == false && ((ICompositeNode) node).hasChildren();
	// }
	//
	// @Override
	// public boolean isLeaf() {
	// return node instanceof ILeafNode;
	// }
	//
	// }
	//
	// private class TransformingBidiIterable implements BidiIterable<IDomNode> {
	// private class TransformingBidiIterator implements BidiIterator<IDomNode> {
	//
	// private BidiIterator<INode> delegate;
	//
	// public TransformingBidiIterator(BidiIterator<INode> delegate) {
	// this.delegate = delegate;
	// }
	//
	// @Override
	// public boolean hasNext() {
	// return delegate.hasNext();
	// }
	//
	// @Override
	// public boolean hasPrevious() {
	// return delegate.hasPrevious();
	// }
	//
	// @Override
	// public IDomNode next() {
	// return createDomNode(delegate.next(), parentNode);
	// }
	//
	// @Override
	// public IDomNode previous() {
	// return createDomNode(delegate.previous(), parentNode);
	// }
	//
	// @Override
	// public void remove() {
	// delegate.remove();
	//
	// }
	// }
	//
	// final BidiIterable<INode> delegate;
	//
	// final IDomNode parentNode;
	//
	// TransformingBidiIterable(BidiIterable<INode> delegate, IDomNode parentNode) {
	// this.delegate = delegate;
	// this.parentNode = parentNode;
	// }
	//
	// @Override
	// public BidiIterator<IDomNode> iterator() {
	// return new TransformingBidiIterator(delegate.iterator());
	// }
	//
	// @Override
	// public BidiIterable<IDomNode> reverse() {
	// return new ReversedBidiIterable<IDomNode>(this);
	// }
	// }
	//
	// protected TokenUtil tokenUtil;
	//
	// @Inject
	// public NodeModelDomNodes(TokenUtil tokenUtil) {
	// this.tokenUtil = tokenUtil;
	// }

	/**
	 * Static method that adapts an INode (being either an ICompositeNode or ILeafNode) to an IDomNode
	 * TODO: Implement this - the first implementation above creates lots of new isntances, and have
	 * issues with part of the contract.
	 * 
	 * @param node
	 * @param parent
	 * @return
	 */
	public IDomNode createDomNode(INode node, IDomNode parent) {
		throw new UnsupportedOperationException("Please implement this");
		//
		// if(node instanceof ICompositeNode)
		// return new NodeModelCompositeNode((ICompositeNode) node, parent);
		//
		// return new NodeModelLeafNode((ILeafNode) node, parent);
	}
}
