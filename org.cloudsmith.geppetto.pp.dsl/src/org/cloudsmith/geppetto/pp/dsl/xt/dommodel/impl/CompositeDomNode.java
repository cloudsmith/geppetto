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
import java.util.List;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.DomModelUtils;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;

import com.google.inject.internal.Lists;

/**
 * A default implementation of IDomNode that has the capacity to hold AbstractDomNode children.
 * 
 */
public class CompositeDomNode extends BaseDomNode {
	// public class ListBidiIterator extends UnmodifiableIterator<IDomNode> implements BidiIterator<IDomNode> {
	//
	// ListIterator<? extends IDomNode> listItor;
	//
	// ListBidiIterator() {
	// listItor = children.listIterator();
	// }
	//
	// @Override
	// public boolean hasNext() {
	// return listItor.hasNext();
	// }
	//
	// @Override
	// public boolean hasPrevious() {
	// return listItor.hasPrevious();
	// }
	//
	// @Override
	// public IDomNode next() {
	// return listItor.next();
	// }
	//
	// @Override
	// public IDomNode previous() {
	// return listItor.previous();
	// }
	//
	// }

	List<BaseDomNode> children;

	public CompositeDomNode() {
		children = Lists.newArrayList();
	}

	public void addChild(BaseDomNode node) {
		node.setParent(this);
		node.setIndex(children.size());
		children.add(node);
		invalidateLayout();
	}

	/**
	 * Performs the "layout" by iterating over nodes and computing the offsets and total length
	 * of all children. Basic style classification is performed to aid with selection:
	 * <ul>
	 * <li>{@link NodeClassifier#ALL_HIDDEN}</li>
	 * <li>{@link NodeClassifier#ALL_WHITESPACE}</li>
	 * <li>{@link NodeClassifier#ALL_COMMENT}</li>
	 * <li>{@link NodeClassifier#ALL_COMMENT_WHITESPACE}</li>
	 * <li>{@link NodeClassifier#CONTAINS_HIDDEN}</li>
	 * <li>{@link NodeClassifier#CONTAINS_WHITESPACE}</li>
	 * <li>{@link NodeClassifier#CONTAINS_COMMENT}</li>
	 * <li>{@link NodeClassifier#LAST_TOKEN}</li>
	 * <li>{@link NodeClassifier#FIRST_TOKEN}</li>
	 * </ul>
	 */
	@Override
	public void doLayout() {
		int hiddenCount = 0;
		int whitespaceCount = 0;
		int commentCount = 0;
		int length = 0;
		for(BaseDomNode d : children) {
			d.doLayout();
			length += d.getLength();
			if(DomModelUtils.isHidden(d))
				hiddenCount++;
			if(DomModelUtils.isWhitespace(d))
				whitespaceCount++;
			if(DomModelUtils.isComment(d))
				whitespaceCount++;
		}
		setLength(length);
		int childCount = children.size();
		setClassifiers(hiddenCount == childCount, NodeClassifier.HIDDEN);
		setClassifiers(whitespaceCount == childCount, NodeClassifier.ALL_WHITESPACE);
		setClassifiers(commentCount == childCount, NodeClassifier.ALL_COMMENT);
		setClassifiers(commentCount + whitespaceCount == childCount, NodeClassifier.ALL_COMMENT_WHITESPACE);

		setClassifiers(hiddenCount > 0, NodeClassifier.CONTAINS_HIDDEN);
		setClassifiers(whitespaceCount > 0, NodeClassifier.CONTAINS_WHITESPACE);
		setClassifiers(commentCount > 0, NodeClassifier.CONTAINS_COMMENT);

		// if this is the root
		if(getParent() == null) {
			// recalculate offsets starting with 0
			setOffset(0);
			// find and mark first and last token
			IDomNode first = DomModelUtils.firstToken(this);
			IDomNode last = DomModelUtils.lastToken(this);
			if(first != null && first instanceof AbstractDomNode)
				((AbstractDomNode) first).setClassifiers(true, NodeClassifier.FIRST_TOKEN);
			if(last != null && last instanceof AbstractDomNode)
				((AbstractDomNode) last).setClassifiers(true, NodeClassifier.LAST_TOKEN);
		}
	}

	@Override
	public List<BaseDomNode> getChildren() {
		return Collections.unmodifiableList(children);
	}

	@Override
	IDomNode getNodeAt(int index) {
		if(index < 0 || index >= children.size())
			return null;
		return children.get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl.AbstractDomNode#hasChildren()
	 */
	@Override
	public boolean hasChildren() {
		return children.size() > 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl.AbstractDomNode#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	void setOffset(int newOffset) {
		super.setOffset(newOffset);
		int o = newOffset;
		for(BaseDomNode d : children) {
			d.setOffset(o);
			o += d.getLength();
		}
	}
}
