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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.cloudsmith.geppetto.forge.util.ModuleUtils;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.Module;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.semver.VersionRange;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.cloudsmith.geppetto.ui.dialog.ModuleListSelectionDialog;
import org.cloudsmith.geppetto.ui.editor.ModuleInfo;
import org.cloudsmith.geppetto.ui.util.ResourceUtil;
import org.cloudsmith.geppetto.ui.util.StringUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.dialogs.MessageDialog;
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

public class NewPuppetProjectFromForgeWizard extends NewPuppetModuleProjectWizard {
	protected class PuppetProjectFromForgeCreationPage extends NewPuppetModuleProjectWizard.PuppetProjectCreationPage {

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
					// TODO: Show error dialog
					List<Module> modules = getForge().search(null);
					List<ModuleInfo> moduleInfos = new ArrayList<ModuleInfo>(modules.size());
					for(Module module : modules)
						moduleInfos.add(new ModuleInfo(module.getFullName(), null));
					choices.addAll(moduleInfos);
				}
				catch(IOException ioe) {
					StringBuilder builder = new StringBuilder();
					builder.append("IOException: " + ioe.getClass().getName());
					builder.append("\n");
					builder.append(ioe.getMessage());
					builder.append("\n\n(See the log view for technical details).");
					MessageDialog.openError(getShell(), "Error while communicating with the ForgeAPI.", //
						builder.toString()); //
					log.error("Error while communicating with the ForgeAPI", ioe);
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
				String preferredProjectName = module.getName().withSeparator('-').toString();

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

	private static final Logger log = Logger.getLogger(NewPuppetProjectFromForgeWizard.class);

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
			VersionRange vr = module.getVersion() == null
					? null
					: VersionRange.exact(module.getVersion());
			Metadata metadata = getForge().install(module.getName(), vr, project.getLocation().toFile(), true, true);

			IFile moduleFile = ResourceUtil.getFile(project.getFullPath().append("Modulefile")); //$NON-NLS-1$

			if(!moduleFile.exists()) {
				ModuleName mdName = metadata.getName();
				if(mdName == null)
					metadata.setName(module.getName());
				ModuleUtils.saveAsModulefile(metadata, moduleFile.getLocation().toFile());
			}
		}
	}

	@Override
	protected WizardNewProjectCreationPage newProjectCreationPage(String pageName) {
		return new PuppetProjectFromForgeCreationPage(pageName);
	}

}
