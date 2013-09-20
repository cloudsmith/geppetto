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
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

/**
 * Represents a node in a TreeViewer that uses {@link SWT#VIRTUAL} style,
 * a {@link TreeNodeContentProvider}, and a {@link TreeNodeLabelProvider}.
 * 
 * @param <P>
 *            The type of the parent node
 * @param <C>
 *            The type of the children nodes
 */
public abstract class TreeNode<P extends TreeNode<?, ?>, C extends TreeNode<?, ?>> {
	private final P parent;

	protected TreeNode(P parent) {
		this.parent = parent;
	}

	/**
	 * Returns the children of this node
	 * 
	 * @return the children
	 */
	protected abstract C[] getChildren();

	/**
	 * Returns the text for the column at the given <code>colIdx</code>.
	 * 
	 * @param colIdx
	 *            The index of the column.
	 * @return The text for the column at <code>colIdx</code> or an empty string if not applicable
	 */
	public String getColumnText(int colIdx) {
		return "";
	}

	/**
	 * Returns the tooltip for the column at the given <code>colIdx</code>.
	 * 
	 * @param colIdx
	 *            The index of the column.
	 * @return The tooltip for the column at <code>colIdx</code> or <code>null</code> if not applicable
	 */
	public String getColumnTooltip(int colIdx) {
		return null;
	}

	/**
	 * @param colIdx
	 *            The index of the column.
	 * @return The image for the column at <code>colIdx</code> or <code>null</code> if not applicable
	 */
	public Image getImage(int colIdx) {
		return null;
	}

	/**
	 * Returns the parent node.
	 * 
	 * @return The parent node or <code>null</code> if this is the top node
	 */
	public final P getParent() {
		return parent;
	}

	/**
	 * Updates the child count for this node in the given <code>viewer</code> using the method {@link TreeViewer#setChildCount(Object, int)}
	 * unless is equal to the <code>currentChildCount</code>.
	 * 
	 * @param viewer
	 *            The viewer that will receive the call
	 * @param currentChildCount
	 *            The viewers current child count for this node
	 */
	public void updateChildCount(TreeViewer viewer, int currentChildCount) {
		C[] children = getChildren();
		if(children.length != currentChildCount)
			viewer.setChildCount(this, children.length);
	}

	/**
	 * <p>
	 * Replaces the child at the given <code>index</code> in the <code>viewer</code> with the child that this instance has at the given
	 * <code>index</code> using the method {@link TreeViewer#replace(Object, int, Object)} and also calls
	 * {@link #updateChildCount(TreeViewer, int)} on that child.
	 * </p>
	 * 
	 * @param viewer
	 *            The viewer that will receive the call
	 * @param index
	 *            The index of the child node
	 */
	public void updateElement(TreeViewer viewer, int index) {
		C[] children = getChildren();
		if(index < children.length) {
			C child = children[index];
			viewer.replace(this, index, child);
			child.updateHasChildren(viewer);
		}
	}

	/**
	 * Updates the <code>viewer</code> with information if this node has children or not
	 * 
	 * @param viewer
	 *            The viewer that will receive the call
	 */
	public void updateHasChildren(TreeViewer viewer) {
		viewer.setChildCount(this, getChildren().length);
	}
}
