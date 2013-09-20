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
package com.puppetlabs.geppetto.puppetdb.ui.treenode;

import org.eclipse.jface.viewers.TreeViewer;

/**
 * Represents a <code>TreeNode</code> that has no children.
 * 
 * @param <P>
 *            The type of the parent node
 */
public abstract class LeafNode<P extends TreeNode<?, ?>> extends TreeNode<P, TreeNode<?, ?>> {
	protected LeafNode(P parent) {
		super(parent);
	}

	@Override
	protected final TreeNode<?, ?>[] getChildren() {
		return null;
	}

	/**
	 * Returns the column text.
	 */
	@Override
	public String getColumnTooltip(int colIdx) {
		return getColumnText(colIdx);
	}

	/**
	 * Will set the viewer child count of the given <code>viewer</code> to zero for this instance
	 */
	@Override
	public final void updateChildCount(TreeViewer viewer, int currentChildCount) {
		if(currentChildCount != 0)
			viewer.setChildCount(this, 0);
	}

	/**
	 * Does nothing since this node has no children
	 */
	@Override
	public final void updateElement(TreeViewer viewer, int index) {
	}

	@Override
	public void updateHasChildren(TreeViewer viewer) {
		viewer.setHasChildren(this, false);
	}
}
