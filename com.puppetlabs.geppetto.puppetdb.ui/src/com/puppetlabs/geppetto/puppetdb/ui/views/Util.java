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

import java.util.concurrent.Callable;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Shell;

import com.puppetlabs.geppetto.puppetdb.PuppetDBManager;
import com.puppetlabs.geppetto.puppetdb.ui.UIPlugin;

public class Util {
	public static boolean alterPreferences(Shell shell, PuppetDBManager manager, Callable<Void> alterations) {
		try {
			alterations.call();
			manager.flush();
			return true;
		}
		catch(Exception e) {
			ErrorDialog.openError(
				shell,
				UIPlugin.getLocalString("_UI_UnableToStorePreferences_title"),
				null,
				new Status(
					IStatus.ERROR, UIPlugin.getInstance().getContext().getBundle().getSymbolicName(),
					UIPlugin.getLocalString("_UI_UnableToStorePreferences_message"), e));
			return false;
		}
	}

	public static PuppetDBConnections getPuppetDBConnections(TreeViewer viewer, PuppetDBManager manager) {
		try {
			return new PuppetDBConnections(viewer, manager);
		}
		catch(Exception e) {
			ErrorDialog.openError(
				viewer.getTree().getShell(),
				UIPlugin.getLocalString("_UI_UnableToObtainConnections_title"),
				null,
				new Status(
					IStatus.ERROR, UIPlugin.getInstance().getContext().getBundle().getSymbolicName(),
					UIPlugin.getLocalString("_UI_UnableToObtainConnections_message"), e));
			return null;
		}
	}
}
