/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.ui.action;

import java.util.ArrayList;
import java.util.List;

import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.cloudsmith.geppetto.ui.util.ResourceUtil;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionDelegate;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class ExportModulesActionDelegate extends ActionDelegate implements IObjectActionDelegate {

	protected List<IProject> projects = new ArrayList<IProject>();

	@Override
	public void dispose() {
		projects = null;
		super.dispose();
	}

	@Override
	public void run(IAction action) {
		DirectoryDialog dialog = new DirectoryDialog(
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.NONE);
		dialog.setMessage(UIPlugin.INSTANCE.getString("_UI_ExportModules_message")); //$NON-NLS-1$
		dialog.setText(UIPlugin.INSTANCE.getString("_UI_ExportModules_title")); //$NON-NLS-1$
		final String directoryPath = dialog.open();

		if(directoryPath != null) {
			WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

				@Override
				protected void execute(IProgressMonitor progressMonitor) {
					Forge forge = ForgeFactory.eINSTANCE.createForgeService().createForge(
						java.net.URI.create("http://forge.puppetlabs.com")); //$NON-NLS-1$

					for(IProject project : projects) {
						try {
							forge.build(project.getLocation().toFile(), new Path(directoryPath).toFile());
						}
						catch(Exception exception) {
							UIPlugin.INSTANCE.log(exception);
						}
					}

					progressMonitor.done();
				}
			};

			try {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().run(false, false, operation);
			}
			catch(Exception exception) {
				UIPlugin.INSTANCE.log(exception);
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		projects.clear();

		if(selection instanceof IStructuredSelection) {

			for(Object element : ((IStructuredSelection) selection).toList()) {

				if(element instanceof IProject) {
					IProject project = (IProject) element;

					if(ResourceUtil.getFile(project.getFullPath().append("Modulefile")).exists()) {
						projects.add(project);
					}
				}
			}
		}

		action.setEnabled(!projects.isEmpty());
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// ignore
	}

}
