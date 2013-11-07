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

import org.eclipse.jface.viewers.TreeViewer;
import org.osgi.service.prefs.BackingStoreException;

import com.puppetlabs.geppetto.puppetdb.ui.treenode.TreeNode;
import com.puppetlabs.puppetdb.javaclient.PuppetDBClient;

public abstract class PuppetDBQuery<C extends TreeNode<?, ?>> extends TreeNode<PuppetDBConnection, C> {
	public PuppetDBQuery(PuppetDBConnection parent) {
		super(parent);
	}

	public PuppetDBClient getClient() throws BackingStoreException {
		return getParent().getClient();
	}

	public TreeViewer getViewer() {
		return getParent().getViewer();
	}

	@Override
	public void updateHasChildren(TreeViewer viewer) {
		viewer.setHasChildren(this, true);
	}
}
