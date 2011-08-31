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

import java.io.File;
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

	private String getUserName() {
		return System.getProperty("user.name").replace('.', '_').replace('-', '_').replace('/', '_');
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE.getImageDescriptor(UIPlugin.INSTANCE.getImage("full/wizban/NewPuppetProject.png"))); //$NON-NLS-1$
		setWindowTitle(UIPlugin.INSTANCE.getString("_UI_NewPuppetProject_title")); //$NON-NLS-1$
	}

	protected void initializeProjectContents() throws Exception {
		Forge forge = getForge();
		Metadata metadata = forge.getService().createMetadata(getUserName() + '/' + project.getName()); //$NON-NLS-1$

		if(ResourceUtil.getFile(project.getFullPath().append("manifests/init.pp")).exists()) { //$NON-NLS-1$
			File modulefile = project.getLocation().append("Modulefile").toFile(); //$NON-NLS-1$

			if(!modulefile.exists()) {
				metadata.saveModulefile(modulefile);
			}
		}
		else {
			forge.generate(project.getLocation().toFile(), metadata);
		}
	}

	protected WizardNewProjectCreationPage newProjectCreationPage(String pageName) {
		return new PuppetProjectCreationPage(pageName);
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
					MessageDialog.openError(
						getShell(), UIPlugin.INSTANCE.getString("_UI_CreateProject_title"), exception.getMessage()); //$NON-NLS-1$
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
			MessageDialog.openError(
				getShell(), UIPlugin.INSTANCE.getString("_UI_CreateProject_title"), exception.getMessage()); //$NON-NLS-1$
			return false;
		}

		if(project != null) {
			IFile modulefile = ResourceUtil.getFile(project.getFullPath().append("Modulefile")); //$NON-NLS-1$

			if(modulefile.exists()) {
				ResourceUtil.selectFile(modulefile);

				try {
					ResourceUtil.openEditor(modulefile);
				}
				catch(PartInitException partInitException) {
					MessageDialog.openError(
						getShell(), UIPlugin.INSTANCE.getString("_UI_OpenEditor_title"), partInitException.getMessage()); //$NON-NLS-1$
					return false;
				}
			}

			return true;
		}

		return false;
	}

}
