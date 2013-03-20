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

import org.cloudsmith.geppetto.common.Strings;
import org.cloudsmith.geppetto.forge.util.Checksums;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jface.dialogs.IDialogSettings;
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

import com.google.inject.Inject;

public class ModuleExportToForgeWizard extends ModuleExportToFileWizard {
	class ModuleExportToForgeWizardPage extends WizardExportResourcesPage implements ModuleExportWizardPage {
		private Text loginField;

		private Text passwordField;

		private Button saveInSecureStoreButton;

		private String initialModuleOwner;

		public ModuleExportToForgeWizardPage(IStructuredSelection selection) {
			this("moduleExportToForge", selection); //$NON-NLS-1$
			setTitle(UIPlugin.INSTANCE.getString("_UI_ExportModulesToForge"));
			setDescription(UIPlugin.INSTANCE.getString("_UI_ExportModulesToForge_desc"));
		}

		public ModuleExportToForgeWizardPage(String name, IStructuredSelection selection) {
			super(name, selection);
		}

		@Override
		public boolean canFinish() {
			setErrorMessage("Foo you");
			return false;
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

			// new project label
			Label loginLabel = new Label(destinationGroup, SWT.NONE);
			loginLabel.setText(plugin.getString("_UI_Login_label"));
			loginLabel.setFont(font);

			// new project name entry field
			loginField = new Text(destinationGroup, SWT.BORDER);
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.widthHint = SIZING_TEXT_FIELD_WIDTH;
			loginField.setLayoutData(data);
			loginField.setFont(font);

			// new project label
			Label passwordLabel = new Label(destinationGroup, SWT.NONE);
			passwordLabel.setText(plugin.getString("_UI_Password_label"));
			passwordLabel.setFont(font);

			// new project name entry field
			passwordField = new Text(destinationGroup, SWT.BORDER | SWT.PASSWORD);
			data = new GridData(GridData.FILL_HORIZONTAL);
			data.widthHint = SIZING_TEXT_FIELD_WIDTH;
			passwordField.setLayoutData(data);
			passwordField.setFont(font);

			// Set the initial value first before listener
			// to avoid handling an event during the creation.
			if(initialModuleOwner != null)
				loginField.setText(initialModuleOwner);

			loginField.addListener(SWT.Modify, this);

			saveInSecureStoreButton = new Button(destinationGroup, SWT.PUSH);
			saveInSecureStoreButton.setText(plugin.getString("_UI_Save_In_Secure_Storage_label"));
			saveInSecureStoreButton.addListener(SWT.Selection, this);
			saveInSecureStoreButton.setFont(font);
			setButtonLayoutData(saveInSecureStoreButton);
		}

		@Override
		public boolean finish() {
			return false;
		}

		public void handleEvent(Event e) {
			Widget source = e.widget;
			if(source == saveInSecureStoreButton) {
				String login = Strings.trimToNull(loginField.getText());
				if(login != null) {
					String password = Strings.emptyToNull(passwordField.getText());
					if(password != null)
						saveSecurePassword(login, password);
				}
			}
		}

		@Override
		protected void internalSaveWidgetValues() {
			super.internalSaveWidgetValues();
			IDialogSettings settings = getDialogSettings();
			if(settings == null)
				return;
			String login = Strings.trimToNull(loginField.getText());
			settings.put(STORE_LOGIN, login);
			if(login != null) {
				String password = Strings.emptyToNull(passwordField.getText());
				if(password != null)
					saveSecurePassword(login, password);
			}

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
	}

	@Inject
	private PPPreferencesHelper preferenceHelper;

	private static final String STORE_LOGIN = "ModuleExportToForgeWizardPage.STORE_LOGIN"; //$NON-NLS-1$

	@Override
	ModuleExportWizardPage createMainPage(IStructuredSelection selection) {
		return new ModuleExportToForgeWizardPage(selection);
	}

	private String loadSecurePassword(String login) {
		ISecurePreferences preferences = SecurePreferencesFactory.getDefault();
		if(preferences == null)
			return null;

		// To access the secure storage, we need the preference instance
		String host = Strings.trimToNull(preferenceHelper.getForgeURI());
		if(host == null)
			return null;

		// Construct the secure preferences node key
		String nodeKey = "/Puppetforge Credentials/" + login + '/' + Checksums.createSHA1(host); //$NON-NLS-1$
		ISecurePreferences node = preferences.node(nodeKey);
		try {
			return node == null
					? null
					: node.get("password", null);
		}
		catch(StorageException e) {
			return null;
		}
	}

	private void saveSecurePassword(String login, String password) {
		ISecurePreferences preferences = SecurePreferencesFactory.getDefault();
		if(preferences == null)
			return;

		// To access the secure storage, we need the preference instance
		String host = Strings.trimToNull(preferenceHelper.getForgeURI());
		if(host == null)
			return;

		// Construct the secure preferences node key
		String nodeKey = "/Puppetforge Credentials/" + login + '/' + Checksums.createSHA1(host); //$NON-NLS-1$
		ISecurePreferences node = preferences.node(nodeKey);
		if(node != null) {
			try {
				node.put("password", password, true); //$NON-NLS-1$
			}
			catch(StorageException ex) { /* ignored on purpose */
			}
		}
	}

}
