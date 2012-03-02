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

import java.util.Collections;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleSet;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;

import com.google.common.collect.Sets;
import com.google.inject.internal.Lists;

/**
 * Abstract implementation of IDomNode
 * 
 */
public abstract class AbstractDomNode implements IDomNode {
	private static class ParentIterator implements Iterator<IDomNode> {
		private IDomNode current;

		ParentIterator(IDomNode startNode) {
			current = startNode;
		}

		@Override
		public boolean hasNext() {
			return current.getParent() != null;
		}

		@Override
		public IDomNode next() {
			current = current.getParent();
			return current;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Iterator is read only");
		}

	}

	protected IDomNode parentNode;

	private Set<? extends Object> styleClassifiers = Sets.newHashSet();

	private Object nodeId = null;

	protected EnumSet<IDomNode.NodeStatus> nodeStatus = EnumSet.noneOf(NodeStatus.class);

	private StyleSet styles = new StyleSet();

	public void flip(boolean flag, NodeStatus... status) {
		if(flag)
			nodeStatus.addAll(Lists.newArrayList(status));
		else
			nodeStatus.removeAll(Lists.newArrayList(status));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.dommodel.IDomNode#getChildren()
	 */
	@Override
	public List<? extends IDomNode> getChildren() {
		return Collections.emptyList();
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

	/**
	 * @return the nodeId
	 */
	@Override
	public Object getNodeId() {
		return nodeId;
	}

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
	 * @see org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode#getStyleClassifiers()
	 */
	@Override
	public Set<? extends Object> getStyleClassifiers() {
		return styleClassifiers;
	}

	@Override
	public StyleSet getStyles() {
		return styles;
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

	@Override
	public Iterator<IDomNode> parents() {
		return new ParentIterator(this);
	}

	/**
	 * @param nodeId
	 *            the nodeId to set
	 */
	@Override
	public void setNodeId(Object nodeId) {
		this.nodeId = nodeId;
	}

	public void setParent(IDomNode node) {
		parentNode = node;
	}

	protected void setStatusOf(NodeStatus first, NodeStatus... rest) {
		nodeStatus = EnumSet.of(first, rest);
	}
}
