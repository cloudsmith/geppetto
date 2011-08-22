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

import java.io.IOException;
import java.util.Collections;

import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.forge.ModuleInfo;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.cloudsmith.geppetto.ui.dialog.ModuleListSelectionDialog;
import org.cloudsmith.geppetto.ui.util.ResourceUtil;
import org.cloudsmith.geppetto.ui.util.StringUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public class NewPuppetProjectFromForgeWizard extends NewPuppetProjectWizard {

	protected class PuppetProjectFromForgeCreationPage extends NewPuppetProjectWizard.PuppetProjectCreationPage {

		protected Object[] moduleChoices = null;

		protected Text moduleField = null;

		protected PuppetProjectFromForgeCreationPage(String pageName) {
			super(pageName);
		}

		@Override
		public void createControl(Composite parent) {
			parent = new Composite(parent, SWT.NONE);

			GridLayout gridLayout = new GridLayout();
			gridLayout.marginHeight = 0;
			gridLayout.marginWidth = 0;

			parent.setLayout(gridLayout);

			GridDataFactory.fillDefaults().applyTo(parent);

			Composite composite = new Composite(parent, SWT.NONE);

			gridLayout = new GridLayout(3, false);
			gridLayout.marginHeight = 10;
			gridLayout.marginWidth = 10;

			composite.setLayout(gridLayout);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(composite);

			createModuleGroup(composite);

			super.createControl(parent);

			setControl(parent);
		}

		protected void createModuleGroup(Composite parent) {
			Label moduleLabel = new Label(parent, SWT.NONE);
			moduleLabel.setText(UIPlugin.INSTANCE.getString("_UI_Module_label")); //$NON-NLS-1$

			moduleField = new Text(parent, SWT.BORDER | SWT.READ_ONLY);
			moduleField.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseDoubleClick(MouseEvent me) {
					promptForModuleSelection();
				}
			});

			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(moduleField);

			Button resultTypeButton = new Button(parent, SWT.PUSH);
			resultTypeButton.setText(UIPlugin.INSTANCE.getString("_UI_Select_label")); //$NON-NLS-1$
			resultTypeButton.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent se) {
					promptForModuleSelection();
				}
			});
		}

		protected Object[] getModuleChoices() {

			if(moduleChoices == null) {
				EList<Object> choices = new UniqueEList.FastCompare<Object>();

				try {
					choices.addAll(getForge().search(null));
				}
				catch(IOException ioe) {
					ioe.printStackTrace();
				}

				moduleChoices = choices.toArray();
			}

			return moduleChoices;
		}

		protected void promptForModuleSelection() {
			ModuleListSelectionDialog dialog = new ModuleListSelectionDialog(getShell());

			dialog.setMultipleSelection(false);
			dialog.setElements(getModuleChoices());

			if(module != null) {
				dialog.setInitialElementSelections(Collections.singletonList(module));
			}

			if(dialog.open() == Window.OK) {
				setModule((ModuleInfo) dialog.getFirstResult());
			}
		}

		protected void setModule(ModuleInfo selectedModule) {
			module = selectedModule;

			moduleField.setText(StringUtil.getModuleText(module));

			validatePage();
		}

		@Override
		public void setVisible(boolean visible) {
			super.setVisible(visible);

			if(visible) {
				moduleField.setFocus();
			}
		}

		@Override
		protected boolean validatePage() {

			if(module == null) {
				setErrorMessage(null);
				setMessage(UIPlugin.INSTANCE.getString("_UI_ModuleCannotBeEmpty_message")); //$NON-NLS-1$
				return false;
			}

			if(super.validatePage()) {
				String preferredProjectName = module.getName();

				if(!preferredProjectName.equals(getProjectName())) {
					setErrorMessage(null);
					setMessage(UIPlugin.INSTANCE.getString(
						"_UI_ProjectNameShouldMatchModule_message", new Object[] { preferredProjectName }), WARNING); //$NON-NLS-1$
				}

				return true;
			}

			return false;
		}

	}

	protected ModuleInfo module;

	@Override
	protected String getProjectCreationPageDescription() {
		return UIPlugin.INSTANCE.getString("_UI_PuppetProjectFromForge_description"); //$NON-NLS-1$
	}

	@Override
	protected String getProjectCreationPageTitle() {
		return UIPlugin.INSTANCE.getString("_UI_PuppetProjectFromForge_title"); //$NON-NLS-1$
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setDefaultPageImageDescriptor(ExtendedImageRegistry.INSTANCE.getImageDescriptor(UIPlugin.INSTANCE.getImage("full/wizban/NewPuppetProject.png"))); //$NON-NLS-1$
		setWindowTitle(UIPlugin.INSTANCE.getString("_UI_NewPuppetProjectFromForge_title")); //$NON-NLS-1$
	}

	@Override
	protected void initializeProjectContents() throws Exception {

		if(module != null) {
			Metadata metadata = getForge().install(module.getFullName(), project.getLocation().toFile(), true, true);

			IFile moduleFile = ResourceUtil.getFile(project.getFullPath().append("Modulefile")); //$NON-NLS-1$

			if(!moduleFile.exists()) {

				if(metadata.getName() == null) {
					metadata.setName(module.getName());
				}

				if(metadata.getUser() == null) {
					metadata.setUser(StringUtil.getUser(module));
				}

				metadata.saveModulefile(moduleFile.getLocation().toFile());
			}
		}
	}

	@Override
	protected WizardNewProjectCreationPage newProjectCreationPage(String pageName) {
		return new PuppetProjectFromForgeCreationPage(pageName);
	}

}
