/**
 * Copyright (c) 2013 Cloudsmith Inc. and other contributors, as listed below.
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
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.cloudsmith.geppetto.common.Strings;
import org.cloudsmith.geppetto.common.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.cloudsmith.geppetto.ui.wizard.ModuleExportOperation.ExportSpec;
import org.cloudsmith.geppetto.ui.wizard.ModuleExportOperation.ResourceFileFilter;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.ide.IDEWorkbenchMessages;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.eclipse.ui.internal.wizards.datatransfer.WizardFileSystemResourceExportPage1;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ModuleExportToFileWizard extends Wizard implements IExportWizard {
	class ModuleExportToFileWizardPage extends WizardFileSystemResourceExportPage1 implements ModuleExportWizardPage {

		public ModuleExportToFileWizardPage(IStructuredSelection selection) {
			this("moduleExportToFile", selection); //$NON-NLS-1$
			setTitle(UIPlugin.INSTANCE.getString("_UI_ExportModulesToFileSystem"));
			setDescription(UIPlugin.INSTANCE.getString("_UI_ExportModulesToFileSystem_desc"));
		}

		public ModuleExportToFileWizardPage(String name, IStructuredSelection selection) {
			super(name, selection);
		}

		public boolean canFinish() {
			File dest = getDestination();
			return dest != null && ensureTargetIsValid(dest);
		}

		@Override
		protected void createOptionsGroupButtons(Group optionsGroup) {
			Font font = optionsGroup.getFont();
			createOverwriteExisting(optionsGroup, font);

			// We need these two to avoid NPE's in super class but we really
			// don't want to show them.
			Composite nonVisible = optionsGroup.getParent().getParent();
			createDirectoryStructureButton = new Button(nonVisible, SWT.CHECK);
			createSelectionOnlyButton = new Button(nonVisible, SWT.CHECK);
		}

		@Override
		public boolean finish() {
			// about to invoke the operation so save our state
			saveWidgetValues();

			if(!saveDirtyEditors())
				// User clicked on cancel when being asked to save dirty editors.
				return false;

			try {
				@SuppressWarnings("unchecked")
				List<IResource> whiteCheckedResources = getWhiteCheckedResources();
				return executeExport(new ModuleExportOperation(
					forge, getExportSpecs(whiteCheckedResources), getDestination(), this));
			}
			catch(CoreException e) {
				ErrorDialog.openError(
					getContainer().getShell(), DataTransferMessages.DataTransfer_exportProblems, null, // no special message
					e.getStatus());
				return false;
			}
		}

		public File getDestination() {
			String dest = Strings.trimToNull(getDestinationValue());
			return dest == null
					? null
					: new File(dest);
		}
	}

	interface ModuleExportWizardPage extends IWizardPage {
		boolean canFinish();

		boolean finish();
	}

	@Inject
	private Forge forge;

	@Inject
	@Named(Forge.MODULE_FILE_FILTER)
	private FileFilter defaultFilter;

	private IStructuredSelection selection;

	private ModuleExportWizardPage mainPage;

	/**
	 * Creates a wizard for exporting workspace resources to the local file system.
	 */
	public ModuleExportToFileWizard() {
		IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings section = workbenchSettings.getSection("ModuleExportToFileWizard");//$NON-NLS-1$
		if(section == null)
			section = workbenchSettings.addNewSection("ModuleExportToFileWizard");//$NON-NLS-1$

		setDialogSettings(section);
	}

	@Override
	public void addPages() {
		super.addPages();
		mainPage = createMainPage(selection);
		addPage(mainPage);
	}

	@Override
	public boolean canFinish() {
		return super.canFinish() && mainPage.canFinish();
	}

	ModuleExportWizardPage createMainPage(IStructuredSelection selection) {
		return new ModuleExportToFileWizardPage(selection);
	}

	boolean executeExport(ModuleExportOperation op) {
		try {
			getContainer().run(true, true, op);
		}
		catch(InterruptedException e) {
			return false;
		}
		catch(InvocationTargetException e) {
			Throwable exception = e.getTargetException();
			String message = exception.getMessage();
			// Some system exceptions have no message
			if(message == null)
				message = NLS.bind(IDEWorkbenchMessages.WizardDataTransfer_exceptionMessage, exception);
			MessageDialog.open(
				MessageDialog.ERROR, getShell(), IDEWorkbenchMessages.WizardExportPage_internalErrorTitle, message,
				SWT.SHEET);
			return false;
		}

		IStatus status = op.getStatus();
		if(!status.isOK()) {
			ErrorDialog.openError(getShell(), DataTransferMessages.DataTransfer_exportProblems, null, // no special message
				status);
			return false;
		}
		return true;
	}

	protected List<ExportSpec> getExportSpecs(List<IResource> resourcesToExport) throws CoreException {
		List<ExportSpec> exportSpecs = new ArrayList<ExportSpec>();
		Multimap<IProject, IResource> resourcesPerProject = ArrayListMultimap.create();

		// Collect a list of export specs where each spec represents a module root
		// directory and a FileFilter. IProject resources are considered to be
		// unfiltered module roots. Everything else represents subsets of files
		// and folders beneath the project that they reside in. We represent such
		// projects with a filter that only accepts the listed subsets.
		for(IResource currentResource : resourcesToExport) {
			if(!currentResource.isAccessible())
				continue;

			if(currentResource instanceof IProject) {
				// A project to be exported as a whole
				for(File moduleRoot : forge.findModuleRoots(currentResource.getLocation().toFile(), defaultFilter)) {
					if(isValidModule(moduleRoot, defaultFilter))
						exportSpecs.add(new ExportSpec(moduleRoot, defaultFilter));
				}
				continue;
			}
			resourcesPerProject.put(currentResource.getProject(), currentResource);
		}

		for(IProject project : resourcesPerProject.keySet()) {
			FileFilter filter = new ResourceFileFilter(resourcesPerProject.get(project), defaultFilter);
			for(File moduleRoot : forge.findModuleRoots(project.getLocation().toFile(), filter))
				if(isValidModule(moduleRoot, filter))
					exportSpecs.add(new ExportSpec(moduleRoot, filter));
			continue;
		}
		return exportSpecs;
	}

	protected Forge getForge() {
		return forge;
	}

	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		this.selection = currentSelection;
		List<?> selectedResources = IDE.computeSelectedResources(currentSelection);
		if(!selectedResources.isEmpty()) {
			this.selection = new StructuredSelection(selectedResources);
		}

		// look it up if current selection (after resource adapting) is empty
		if(selection.isEmpty() && workbench.getActiveWorkbenchWindow() != null) {
			IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
			if(page != null) {
				IEditorPart currentEditor = page.getActiveEditor();
				if(currentEditor != null) {
					Object selectedResource = currentEditor.getEditorInput().getAdapter(IResource.class);
					if(selectedResource != null) {
						selection = new StructuredSelection(selectedResource);
					}
				}
			}
		}

		setWindowTitle(DataTransferMessages.DataTransfer_export);
		setDefaultPageImageDescriptor(IDEWorkbenchPlugin.getIDEImageDescriptor("wizban/exportdir_wiz.png"));//$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}

	protected boolean isValidModule(File moduleDirectory, FileFilter filter) {
		Metadata md;
		try {
			md = forge.createFromModuleDirectory(moduleDirectory, false, filter, null, new Diagnostic());
			return md != null && md.getName() != null && md.getName().getOwner() != null &&
					md.getName().getName() != null && md.getVersion() != null;
		}
		catch(IOException e) {
		}
		return false;
	}

	@Override
	public boolean performFinish() {
		return mainPage.finish();
	}
}
