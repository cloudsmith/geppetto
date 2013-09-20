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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILazyTreePathContentProvider;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * A lazy content provider that delegates all actions to the element that is passed
 * as the first argument. The element is assumed to always be an instance
 * of {@link TreeNode}.
 */
public class TreeNodeContentProvider implements ILazyTreePathContentProvider {
	private final TreeViewer viewer;

	/**
	 * Creates a new instance for the given <code>viewer</code>.
	 * 
	 * @param viewer
	 *            The viewer that will receive updates from the new instance
	 */
	public TreeNodeContentProvider(TreeViewer viewer) {
		this.viewer = viewer;
	}

	private void addParents(TreeNode<?, ?> node, List<TreeNode<?, ?>> parents) {
		TreeNode<?, ?> parent = node.getParent();
		if(parent != null) {
			addParents(parent, parents);
			parents.add(parent);
		}
	}

	/**
	 * This is a no-op.
	 */
	@Override
	public void dispose() {
	}

	private TreeNode<?, ?> getNode(TreePath treePath) {
		Object node = treePath.getSegmentCount() > 0
				? treePath.getLastSegment()
				: viewer.getInput();
		return (TreeNode<?, ?>) node;
	}

	/**
	 * Delegates to {@link TreeNode#getParent() element.getParent()}.
	 */
	@Override
	public TreePath[] getParents(Object element) {
		List<TreeNode<?, ?>> parents = new ArrayList<TreeNode<?, ?>>();
		addParents((TreeNode<?, ?>) element, parents);
		return new TreePath[] { new TreePath(parents.toArray(new Object[parents.size()])) };
	}

	/**
	 * This is a no-op.
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	/**
	 * Delegates to {@link TreeNode#updateChildCount(TreeViewer, int) element.updateChildCount(TreeViewer, int)}.
	 */
	@Override
	public void updateChildCount(TreePath treePath, int currentChildCount) {
		getNode(treePath).updateChildCount(viewer, currentChildCount);
	}

	/**
	 * Delegates to {@link TreeNode#updateElement(TreeViewer, int) element.updateElement(TreeViewer, int)}.
	 */
	@Override
	public void updateElement(TreePath parentPath, int index) {
		getNode(parentPath).updateElement(viewer, index);
	}

	@Override
	public void updateHasChildren(TreePath treePath) {
		getNode(treePath).updateHasChildren(viewer);
	}
}
