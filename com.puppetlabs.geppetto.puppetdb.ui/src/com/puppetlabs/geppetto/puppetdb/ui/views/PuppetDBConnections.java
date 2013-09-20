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
package com.puppetlabs.geppetto.puppetdb.ui.views;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.osgi.service.prefs.BackingStoreException;

import com.puppetlabs.geppetto.puppetdb.PuppetDBConnectionPreferences;
import com.puppetlabs.geppetto.puppetdb.PuppetDBManager;
import com.puppetlabs.geppetto.puppetdb.ui.treenode.TreeNode;

class PuppetDBConnections extends TreeNode<TreeNode<?, ?>, PuppetDBConnection> {
	private final PuppetDBConnection[] children;

	private final TreeViewer viewer;

	PuppetDBConnections(TreeViewer viewer, PuppetDBManager puppetDBManager) throws BackingStoreException {
		super(null);
		this.viewer = viewer;
		List<PuppetDBConnectionPreferences> list = puppetDBManager.getPuppetDBs();
		int top = list.size();
		PuppetDBConnection[] result = new PuppetDBConnection[top];
		for(int idx = 0; idx < top; ++idx)
			result[idx] = new PuppetDBConnection(this, list.get(idx));
		children = result;
	}

	@Override
	public PuppetDBConnection[] getChildren() {
		return children;
	}

	public TreeViewer getViewer() {
		return viewer;
	}
}
