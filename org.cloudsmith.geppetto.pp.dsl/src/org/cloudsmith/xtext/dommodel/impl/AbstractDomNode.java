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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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

	private Set<Object> styleClassifiers = Sets.newHashSet();

	// private Set<Object> nonModifiableStyleClassifiers = Collections.unmodifiableSet(styleClassifiers);

	private Object nodeId = null;

	protected IDomNode.NodeType nodeType = null;

	private StyleSet styles = new StyleSet();

	@Override
	public List<IDomNode> getChildren() {
		return Collections.emptyList();
	}

	@Override
	public abstract EObject getGrammarElement();

	@Override
	public int getLength() {
		return 0;
	}

	@Override
	public EObject getNearestSemanticObject() {
		EObject semantic = getSemanticObject();
		if(semantic != null)
			return semantic;
		if(parentNode != null)
			return parentNode.getSemanticObject();
		return null;
	}

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
	public NodeType getNodeType() {
		return nodeType;
	}

	@Override
	public int getOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IDomNode getParent() {
		return parentNode;
	}

	@Override
	public EObject getSemanticObject() {
		return null;
	}

	@Override
	public Set<Object> getStyleClassifiers() {
		return styleClassifiers;
	}

	@Override
	public StyleSet getStyles() {
		return styles;
	}

	@Override
	public abstract String getText();

	@Override
	public abstract boolean hasChildren();

	@Override
	public abstract boolean isLeaf();

	@Override
	public Iterator<IDomNode> parents() {
		return new ParentIterator(this);
	}

	public void setClassifiers(boolean flag, Object... classifiers) {
		if(flag)
			styleClassifiers.addAll(Lists.newArrayList(classifiers));
		else
			styleClassifiers.removeAll(Lists.newArrayList(classifiers));
		// nonModifiableStyleClassifiers = Collections.unmodifiableSet(styleClassifiers);
	}

	/**
	 * @param nodeId
	 *            the nodeId to set
	 */
	@Override
	public void setNodeId(Object nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodeType(NodeType nodeType) {
		this.nodeType = nodeType;
	}

	public void setParent(IDomNode node) {
		parentNode = node;
	}

}
