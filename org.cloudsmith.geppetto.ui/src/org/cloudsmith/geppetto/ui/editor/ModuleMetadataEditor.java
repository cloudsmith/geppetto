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
package org.cloudsmith.geppetto.ui.editor;

import java.io.IOException;

import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.part.FileEditorInput;

public class ModuleMetadataEditor extends FormEditor {

	protected Metadata metadata;

	@Override
	public void doSave(IProgressMonitor monitor) {
		commitPages(true);

		WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

			@Override
			protected void execute(IProgressMonitor progressMonitor) {
				try {
					IFile file = ((FileEditorInput) getEditorInput()).getFile();
					metadata.saveModulefile(file.getLocation().toFile());
					file.refreshLocal(0, progressMonitor);
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
			operation.run(monitor);

			editorDirtyStateChanged();
		}
		catch(Exception exception) {
			UIPlugin.INSTANCE.log(exception);
		}
	}

	@Override
	public void doSaveAs() {
		// do nothing
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);

		metadata = ForgeFactory.eINSTANCE.createMetadata();

		if(input instanceof FileEditorInput) {
			try {
				metadata.loadModuleFile(((FileEditorInput) input).getPath().toFile());

				setPartName(metadata.getFullName());
			}
			catch(IOException ioe) {
				UIPlugin.INSTANCE.log(ioe);
			}
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	protected void addPages() {

		try {
			addPage(new ModuleMetadataOverviewPage(this, "overview", UIPlugin.INSTANCE.getString("_UI_Overview_title"))); //$NON-NLS-1$ //$NON-NLS-2$
			//addPage(new ModuleMetadataSourcePage(this, "source", UIPlugin.INSTANCE.getString("_UI_Source_title"))); //$NON-NLS-1$ //$NON-NLS-2$
		}
		catch(Exception e) {
			UIPlugin.INSTANCE.log(e);
		}
	}

	Metadata getMetadata() {
		return metadata;
	}

}
