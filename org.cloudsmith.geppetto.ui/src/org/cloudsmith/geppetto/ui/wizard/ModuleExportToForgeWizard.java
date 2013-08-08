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

import static org.cloudsmith.geppetto.forge.ForgeService.getForgeModule;
import static org.cloudsmith.geppetto.injectable.CommonModuleProvider.getCommonModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.cloudsmith.geppetto.common.Strings;
import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.impl.ForgePreferencesBean;
import org.cloudsmith.geppetto.forge.util.Checksums;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.cloudsmith.geppetto.ui.wizard.ModuleExportOperation.ExportSpec;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.dialogs.WizardExportResourcesPage;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ModuleExportToForgeWizard extends ModuleExportToFileWizard {
	class ModuleExportToForgeWizardPage extends WizardExportResourcesPage implements ModuleExportWizardPage {
		private Text loginField;

		private Text passwordField;

		private Button saveInSecureStoreButton;

		private boolean validationChange;

		private Button dryRunButton;

		public ModuleExportToForgeWizardPage(IStructuredSelection selection) {
			this("moduleExportToForge", selection); //$NON-NLS-1$
			setTitle(UIPlugin.INSTANCE.getString("_UI_ExportModulesToForge"));
			setDescription(UIPlugin.INSTANCE.getString("_UI_ExportModulesToForge_desc"));
		}

		public ModuleExportToForgeWizardPage(String name, IStructuredSelection selection) {
			super(name, selection);
		}

		@Override
		protected void createDestinationGroup(Composite parent) {
			UIPlugin plugin = UIPlugin.INSTANCE;
			Group destinationGroup = new Group(parent, SWT.NONE);
			GridLayout layout = new GridLayout();
			destinationGroup.setLayout(layout);
			destinationGroup.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));
			destinationGroup.setText(plugin.getString("_UI_Forge_Credentials_label"));
			destinationGroup.setFont(parent.getFont());

			Font font = destinationGroup.getFont();

			Label loginLabel = new Label(destinationGroup, SWT.NONE);
			loginLabel.setText(plugin.getString("_UI_Login_label"));
			loginLabel.setFont(font);

			loginField = new Text(destinationGroup, SWT.BORDER | SWT.READ_ONLY);
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.widthHint = SIZING_TEXT_FIELD_WIDTH;
			loginField.setLayoutData(data);
			loginField.setFont(font);

			Label passwordLabel = new Label(destinationGroup, SWT.NONE);
			passwordLabel.setText(plugin.getString("_UI_Password_label"));
			passwordLabel.setFont(font);

			passwordField = new Text(destinationGroup, SWT.BORDER | SWT.PASSWORD);
			data = new GridData(GridData.FILL_HORIZONTAL);
			data.widthHint = SIZING_TEXT_FIELD_WIDTH;
			passwordField.setLayoutData(data);
			passwordField.setFont(font);
			passwordField.addListener(SWT.Modify, this);
		}

		@Override
		protected void createOptionsGroupButtons(Group optionsGroup) {
			UIPlugin plugin = UIPlugin.INSTANCE;
			Font font = optionsGroup.getFont();
			saveInSecureStoreButton = new Button(optionsGroup, SWT.CHECK);
			saveInSecureStoreButton.setText(plugin.getString("_UI_SaveInSecureStorage_label"));
			saveInSecureStoreButton.addListener(SWT.Selection, this);
			saveInSecureStoreButton.setFont(font);

			dryRunButton = new Button(optionsGroup, SWT.CHECK);
			dryRunButton.setText(plugin.getString("_UI_DryRun_label"));
			dryRunButton.setFont(font);
			dryRunButton.setSelection(false);
		}

		@Override
		public boolean finish() {
			// about to invoke the operation so save our state
			saveWidgetValues();

			if(!saveDirtyEditors())
				// User clicked on cancel when being asked to save dirty editors.
				return false;

			ForgePreferencesBean forgePrefs = new ForgePreferencesBean();
			forgePrefs.setBaseURL(preferenceHelper.getForgeURI());
			forgePrefs.setLogin(loginField.getText());
			forgePrefs.setPassword(passwordField.getText());
			forgePrefs.setOAuthClientId(FORGE_CLIENT_ID);
			forgePrefs.setOAuthClientSecret(FORGE_CLIENT_SECRET);
			forgePrefs.setOAuthAccessToken(null);
			Injector injector = Guice.createInjector(getForgeModule(forgePrefs, getCommonModule()));
			Forge forge = injector.getInstance(Forge.class);
			try {
				@SuppressWarnings("unchecked")
				List<IResource> whiteCheckedResources = getWhiteCheckedResources();
				File tmpDir = new File(System.getProperty("java.io.tmpdir"));
				File destinationDir = File.createTempFile("forge-", ".tarballs", tmpDir);
				destinationDir.delete();
				destinationDir.mkdir();
				ModuleExportToForgeOperation exportOp = new ModuleExportToForgeOperation(
					forge, getExportSpecs(whiteCheckedResources), destinationDir, dryRunButton.getSelection());
				boolean result = executeExport(exportOp);
				Diagnostic diag = exportOp.getDiagnostic();
				if(diag.getSeverity() == Diagnostic.ERROR) {
					Exception e = diag.getException();
					ErrorDialog.openError(
						getContainer().getShell(), DataTransferMessages.DataTransfer_exportProblems,
						null, // no special message
						new Status(
							IStatus.ERROR, UIPlugin.getPlugin().getBundle().getSymbolicName(), 0, diag.toString(), e));
				}
				else
					MessageDialog.openInformation(
						getContainer().getShell(), DataTransferMessages.DataTransfer_information, diag.toString());
				return result;
			}
			catch(CoreException e) {
				ErrorDialog.openError(
					getContainer().getShell(), DataTransferMessages.DataTransfer_exportProblems, null, // no special message
					e.getStatus());
			}
			catch(Exception e) {
				ErrorDialog.openError(
					getContainer().getShell(), DataTransferMessages.DataTransfer_exportProblems, null, new Status(
						IStatus.ERROR, UIPlugin.getPlugin().getBundle().getSymbolicName(), 0, e.getMessage(), e));
			}
			return false;
		}

		public void handleEvent(Event e) {
			if(validationChange)
				// Don't act on events that stems from login/password settings made
				// by the source group validation
				return;

			Widget source = e.widget;
			if(source == saveInSecureStoreButton && saveInSecureStoreButton.getSelection()) {
				String login = Strings.trimToNull(loginField.getText());
				if(login != null) {
					String password = Strings.emptyToNull(passwordField.getText());
					if(password != null)
						saveSecurePassword(login, password);
				}
			}
			updatePageCompletion();
		}

		@Override
		protected void internalSaveWidgetValues() {
			super.internalSaveWidgetValues();
			IDialogSettings settings = getDialogSettings();
			String login = Strings.trimToNull(loginField.getText());
			if(settings != null)
				settings.put(STORE_LOGIN, login);
			if(saveInSecureStoreButton.getSelection())
				saveSecurePassword(login, Strings.emptyToNull(passwordField.getText()));
		}

		@Override
		protected void restoreWidgetValues() {
			IDialogSettings settings = getDialogSettings();
			if(settings == null)
				return;

			String password = null;
			String login = settings.get(STORE_LOGIN);
			if(login == null)
				login = "";
			else
				password = loadSecurePassword(login);

			if(password == null)
				password = "";

			loginField.setText(login);
			passwordField.setText(password);
		}

		@Override
		public boolean validateDestinationGroup() {
			if("".equals(passwordField.getText())) {
				setErrorMessage(UIPlugin.INSTANCE.getString("_UI_EnterPassword"));
				return false;
			}
			return super.validateDestinationGroup();
		}

		@Override
		public boolean validateSourceGroup() {
			if(!super.validateSourceGroup())
				return false;

			try {
				@SuppressWarnings("unchecked")
				List<IResource> whiteCheckedResources = getWhiteCheckedResources();
				UIPlugin plugin = UIPlugin.INSTANCE;
				String owner = null;
				Diagnostic diag = new Diagnostic();
				for(ExportSpec spec : getExportSpecs(whiteCheckedResources)) {
					try {
						Metadata md = getForge().createFromModuleDirectory(
							spec.getModuleRoot(), false, spec.getFileFilter(), null, diag);
						if(md != null) {
							ModuleName name = md.getName();
							if(owner == null)
								owner = name.getOwner();
							else if(!owner.equals(name.getOwner())) {
								setErrorMessage(plugin.getString("_UI_MultipleModuleOwners"));
								return false;
							}
						}
					}
					catch(IOException e) {
					}
				}

				if(owner == null) {
					setErrorMessage(plugin.getString("_UI_NoModulesSelected"));
					return false;
				}
				if(!owner.equals(loginField.getText())) {
					// Owner changed
					validationChange = true;
					try {
						loginField.setText(owner);
						String password = null;
						if(saveInSecureStoreButton.getSelection())
							password = loadSecurePassword(owner);
						if(password == null)
							password = "";
						passwordField.setText(password);
					}
					finally {
						validationChange = false;
					}
				}
				return true;
			}
			catch(CoreException e) {
				setErrorMessage(e.getMessage());
				return false;
			}
		}
	}

	private static final String FORGE_CLIENT_ID = "cac18b1f07f13a244c47644548b29cbbe58048f3aaccdeefa7c0306467afda44";

	private static final String FORGE_CLIENT_SECRET = "2227c9a7392382f58b5e4d084b705827cb574673ff7d2a5905ef21685fd48e40";

	@Inject
	private PPPreferencesHelper preferenceHelper;

	private static final String STORE_LOGIN = "ModuleExportToForgeWizardPage.STORE_LOGIN"; //$NON-NLS-1$

	@Override
	ModuleExportWizardPage createMainPage(IStructuredSelection selection) {
		return new ModuleExportToForgeWizardPage(selection);
	}

	private ISecurePreferences getPasswordNode(String login) {
		if(login == null)
			return null;

		ISecurePreferences preferences = SecurePreferencesFactory.getDefault();
		if(preferences == null)
			return null;

		String host = Strings.trimToNull(preferenceHelper.getForgeURI());
		if(host == null)
			return null;

		StringBuilder bld = new StringBuilder();
		bld.append("/Puppetforge Credentials/"); //$NON-NLS-1$
		bld.append(login);
		bld.append('/');
		Checksums.appendSHA1(bld, host);
		return preferences.node(bld.toString());
	}

	private String loadSecurePassword(String login) {
		ISecurePreferences node = getPasswordNode(login);
		if(node != null)
			try {
				return node.get("password", null);
			}
			catch(StorageException e) {
			}
		return null;
	}

	private void saveSecurePassword(String login, String password) {
		ISecurePreferences node = getPasswordNode(login);
		if(node != null) {
			try {
				node.put("password", password, true); //$NON-NLS-1$
			}
			catch(StorageException ex) { /* ignored on purpose */
			}
		}
	}

}
