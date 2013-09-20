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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import com.puppetlabs.geppetto.puppetdb.PuppetDBConnectionPreferences;
import com.puppetlabs.geppetto.puppetdb.ui.UIPlugin;
import com.puppetlabs.geppetto.puppetdb.ui.treenode.TreeNode;
import com.puppetlabs.puppetdb.javaclient.PuppetDBClient;

/**
 * A tree node that represents a connection to a PuppetDB instance
 */
public class PuppetDBConnection extends TreeNode<PuppetDBConnections, PuppetDBQuery<?>> {
	private static final Image DB_IMAGE = UIPlugin.createdImage("database.png");

	private final PuppetDBConnectionPreferences dbAccess;

	private final PuppetDBQuery<?>[] children = new PuppetDBQuery[] { new FailedResourceEvents(this), new OKResourceEvents(this) };

	private PuppetDBClient client;

	PuppetDBConnection(PuppetDBConnections parent, PuppetDBConnectionPreferences dbAccess) {
		super(parent);
		this.dbAccess = dbAccess;
	}

	@Override
	public PuppetDBQuery<?>[] getChildren() {
		return children;
	}

	/**
	 * Returns the client that represent the PuppetDB instance
	 * 
	 * @return The client
	 */
	public synchronized PuppetDBClient getClient() {
		if(client == null)
			client = dbAccess.getClient();
		return client;
	}

	@Override
	public String getColumnText(int colIdx) {
		return colIdx == 0
				? dbAccess.getIdentifier()
				: "";
	}

	@Override
	public Image getImage(int colIdx) {
		return colIdx == 0
				? DB_IMAGE
				: null;
	}

	private boolean dialogUp = false;

	public void showException(Exception e) {
		synchronized(this) {
			if(dialogUp)
				return;
			dialogUp = true;
		}

		ErrorDialog.openError(
			getViewer().getTree().getShell(),
			UIPlugin.getLocalString("_UI_UnableToConnect_title"),
			null,
			new Status(IStatus.ERROR, UIPlugin.getInstance().getContext().getBundle().getSymbolicName(), UIPlugin.getLocalString(
				"_UI_UnableToConnect_message", dbAccess.getIdentifier()), e));

		synchronized(this) {
			dialogUp = false;
		}
	}

	/**
	 * Returns the preferences used when connecting to the PuppetDB instance
	 * 
	 * @return The preferences
	 */
	public PuppetDBConnectionPreferences getPreferences() {
		return dbAccess;
	}

	public TreeViewer getViewer() {
		return getParent().getViewer();
	}
}
