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
package org.cloudsmith.geppetto.ui.wizard;

import java.util.Collections;

import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.cloudsmith.geppetto.ui.util.ResourceUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public class NewPuppetProjectWizard extends Wizard implements INewWizard {

	protected class PuppetProjectCreationPage extends WizardNewProjectCreationPage {

		protected PuppetProjectCreationPage(String pageName) {
			super(pageName);
		}

		@Override
		protected boolean validatePage() {

			if(super.validatePage()) {
				IPath locationPath = getLocationPath();
				projectLocation = Platform.getLocation().equals(locationPath)
						? null
						: locationPath;
				projectContainer = getProjectHandle().getFullPath();
				return true;
			}

			return false;
		}

	}

	protected IPath projectLocation;

	protected IPath projectContainer;

	protected IProject project;

	protected Forge forge;

	@Override
	public void addPages() {
		WizardNewProjectCreationPage newProjectCreationPage = newProjectCreationPage("NewProjectCreationPage"); //$NON-NLS-1$

		newProjectCreationPage.setTitle(getProjectCreationPageTitle());
		newProjectCreationPage.setDescription(getProjectCreationPageDescription());

		addPage(newProjectCreationPage);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE.getImageDescriptor(UIPlugin.INSTANCE.getImage("full/wizban/NewPuppetProject.png"))); //$NON-NLS-1$
		setWindowTitle(UIPlugin.INSTANCE.getString("_UI_NewPuppetProject_title")); //$NON-NLS-1$
	}

	@Override
	public boolean performFinish() {
		WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

			@Override
			protected void execute(IProgressMonitor progressMonitor) {
				try {
					project = ResourceUtil.createProject(
						new Path(projectContainer.toString()), projectLocation == null
								? null
								: URI.createFileURI(projectLocation.toOSString()), Collections.<IProject> emptyList(),
						progressMonitor);

					initializeProjectContents();

					project.refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(progressMonitor, 1));
				}
				catch(Exception exception) {
					UIPlugin.INSTANCE.log(exception);
				}
				finally {
					progressMonitor.done();
				}
			}
		};

		try {
			getContainer().run(false, false, operation);
		}
		catch(Exception exception) {
			UIPlugin.INSTANCE.log(exception);
			return false;
		}

		if(project != null) {
			IFile manifestFile = ResourceUtil.getFile(project.getFullPath().append("manifests/init.pp")); //$NON-NLS-1$

			if(manifestFile.exists()) {
				ResourceUtil.selectFile(manifestFile);

				try {
					ResourceUtil.openEditor(manifestFile);
				}
				catch(PartInitException partInitException) {
					MessageDialog.openError(
						getShell(), UIPlugin.INSTANCE.getString("_UI_OpenEditor_title"), partInitException.getMessage()); //$NON-NLS-1$
					return false;
				}
			}
		}

		return true;
	}

	protected Forge getForge() {

		if(forge == null) {
			forge = ForgeFactory.eINSTANCE.createForgeService().createForge(
				java.net.URI.create("http://forge.puppetlabs.com")); //$NON-NLS-1$
		}

		return forge;
	}

	protected String getProjectCreationPageDescription() {
		return UIPlugin.INSTANCE.getString("_UI_PuppetProject_description"); //$NON-NLS-1$
	}

	protected String getProjectCreationPageTitle() {
		return UIPlugin.INSTANCE.getString("_UI_PuppetProject_title"); //$NON-NLS-1$
	}

	protected void initializeProjectContents() throws Exception {
		Forge forge = getForge();
		Metadata metadata = forge.getService().createMetadata(System.getProperty("user.name") + '/' + project.getName()); //$NON-NLS-1$
		forge.generate(project.getLocation().toFile(), metadata);
	}

	protected WizardNewProjectCreationPage newProjectCreationPage(String pageName) {
		return new PuppetProjectCreationPage(pageName);
	}

}
