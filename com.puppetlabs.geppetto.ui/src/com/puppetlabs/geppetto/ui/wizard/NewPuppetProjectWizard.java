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
package com.puppetlabs.geppetto.ui.wizard;

import com.puppetlabs.geppetto.ui.UIPlugin;
import com.puppetlabs.geppetto.ui.util.ResourceUtil;
import org.eclipse.core.runtime.IProgressMonitor;
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
		return UIPlugin.getLocalString("_UI_PuppetProject_description"); //$NON-NLS-1$
	}

	@Override
	protected String getProjectCreationPageTitleKey() {
		return "_UI_PuppetProject_title";
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setDefaultPageImageDescriptor(UIPlugin.getImageDesc("full/wizban/NewPuppetProject.png")); //$NON-NLS-1$
		setWindowTitle(UIPlugin.getLocalString("_UI_NewPuppetProject_title")); //$NON-NLS-1$
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
