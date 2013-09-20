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

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * A column label provider that delegates all actions to the element that is passed
 * as the argument. The element is assumed to always be an instance
 * of {@link TreeNode}.
 */
public class TreeNodeLabelProvider extends ColumnLabelProvider {
	private final int colIdx;

	/**
	 * Creates a new instance that represents the column at the given <code>colIdx</code>.
	 * 
	 * @param colIdx
	 *            The index of the column represented by this provider
	 */
	public TreeNodeLabelProvider(int colIdx) {
		this.colIdx = colIdx;
	}

	/**
	 * Delegates to {@link TreeNode#getImage(int) element.getImage(colIdx)}.
	 */
	@Override
	public Image getImage(Object element) {
		return ((TreeNode<?, ?>) element).getImage(colIdx);
	}

	/**
	 * Delegates to {@link TreeNode#getColumnText(int) element.getColumnText(colIdx)}.
	 */
	@Override
	public String getText(Object element) {
		return ((TreeNode<?, ?>) element).getColumnText(colIdx);
	}

	/**
	 * Delegates to {@link TreeNode#getColumnTooltip(int) element.getColumnTooltip(colIdx)}.
	 */
	@Override
	public String getToolTipText(Object element) {
		return ((TreeNode<?, ?>) element).getColumnTooltip(colIdx);
	}
}
