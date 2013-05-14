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

import org.cloudsmith.geppetto.ui.UIPlugin;
import org.cloudsmith.geppetto.ui.util.ResourceUtil;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

/**
 * A New Wizard for a free-form puppet project.
 * Does not create any content except the project.
 * 
 */
public class NewPuppetProjectWizard extends NewPuppetModuleProjectWizard {

	@Override
	protected String getProjectCreationPageDescription() {
		return UIPlugin.INSTANCE.getString("_UI_PuppetProject_description"); //$NON-NLS-1$
	}

	@Override
	protected String getProjectCreationPageTitleKey() {
		return "_UI_PuppetProject_title";
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE.getImageDescriptor(UIPlugin.INSTANCE.getImage("full/wizban/NewPuppetProject.png"))); //$NON-NLS-1$
		setWindowTitle(UIPlugin.INSTANCE.getString("_UI_NewPuppetProject_title")); //$NON-NLS-1$
	}

	@Override
	protected void initializeProjectContents(IProgressMonitor monitor) throws Exception {
		// no content generated
	}

	@Override
	public boolean performFinish() {
		boolean result = super.performFinish();
		if(!result)
			return result;
		ResourceUtil.selectFile(project);
		return true;

	}
}
