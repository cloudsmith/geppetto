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
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl;

import java.util.EnumSet;
import java.util.Set;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.BidiIterable;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.EmptyBidiIterable;

/**
 * Abstract implementation of IDomNode
 * 
 */
public abstract class AbstractDomNode implements IDomNode {
	private IDomNode parentNode;

	protected EnumSet<IDomNode.NodeStatus> nodeStatus = EnumSet.noneOf(NodeStatus.class);

	public void flip(boolean flag, NodeStatus status) {
		if(flag)
			nodeStatus.add(status);
		else
			nodeStatus.remove(status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.dommodel.IDomNode#getChildren()
	 */
	@Override
	public BidiIterable<IDomNode> getChildren() {
		return EmptyBidiIterable.instance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.dommodel.IDomNode#getGrammarElement()
	 */
	@Override
	public abstract EObject getGrammarElement();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.dommodel.IDomNode#getLength()
	 */
	@Override
	public int getLength() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.dommodel.IDomNode#getNode()
	 */
	@Override
	public abstract INode getNode();

	@Override
	public Set<NodeStatus> getNodeStatus() {
		return nodeStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.dommodel.IDomNode#getOffSet()
	 */
	@Override
	public int getOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.dommodel.IDomNode#getParent()
	 */
	@Override
	public IDomNode getParent() {
		return parentNode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.dommodel.IDomNode#getSemanticElement()
	 */
	@Override
	public EObject getSemanticElement() {
		if(parentNode != null)
			return parentNode.getSemanticElement();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.dommodel.IDomNode#getText()
	 */
	@Override
	public abstract String getText();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.dommodel.IDomNode#hasChildren()
	 */
	@Override
	public abstract boolean hasChildren();

	@Override
	public abstract boolean isLeaf();

	public void setParent(IDomNode node) {
		parentNode = node;
	}

	protected void setStatusOf(NodeStatus first, NodeStatus... rest) {
		nodeStatus = EnumSet.of(first, rest);
	}
}
