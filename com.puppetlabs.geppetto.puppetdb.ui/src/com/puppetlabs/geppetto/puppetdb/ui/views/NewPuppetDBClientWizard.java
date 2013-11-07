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
package com.puppetlabs.geppetto.puppetdb.ui.views;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.osgi.service.prefs.BackingStoreException;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.puppetdb.PuppetDBConnectionPreferences;
import com.puppetlabs.geppetto.puppetdb.PuppetDBManager;
import com.puppetlabs.geppetto.puppetdb.ui.UIPlugin;

public class NewPuppetDBClientWizard extends Wizard implements INewWizard {

	/**
	 * Validates that the entered characters are valid according to <a href="http://tools.ietf.org/html/rfc1123">RFC 1123</a>
	 */
	protected static class HostnameVerifyListener implements VerifyListener {
		@Override
		public void verifyText(VerifyEvent e) {
			// @fmtOff
			e.doit = 
				   e.character == 0
				|| e.keyCode == SWT.BS
				|| e.keyCode == SWT.DEL
				|| e.character == '-'
				|| e.character == '.'
				|| e.character >= '0' && e.character <= '9'
				|| e.character >= 'A' && e.character <= 'Z'
				|| e.character >= 'a' && e.character <= 'z';
			// @fmtOn
		}
	}

	protected static class IntVerifyListener implements VerifyListener {
		@Override
		public void verifyText(VerifyEvent e) {
			e.doit = e.character == 0 || e.keyCode == SWT.BS || e.keyCode == SWT.DEL || e.character >= '0' && e.character <= '9';
		}
	}

	protected class NewPuppetDBClientWizardPage extends WizardPage {

		class BasicModifyListener extends ValidatingModifyListener<String> {
			BasicModifyListener(String value, String requiredMsgKey) {
				super(value, requiredMsgKey);
			}

			@Override
			String convert(String value) {
				return value;
			}
		}

		abstract class FileModifyAndSelectionListener extends ValidatingModifyListener<File> implements SelectionListener {
			FileModifyAndSelectionListener(File file, String requiredMsgKey) {
				super(file, requiredMsgKey);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		}

		class ValidateFileModifyListener extends FileModifyAndSelectionListener {
			private boolean badFile = false;

			ValidateFileModifyListener(File file, String requiredMsgKey) {
				super(file, requiredMsgKey);
			}

			@Override
			void clear() {
				super.clear();
				badFile = false;
			}

			@Override
			File convert(String value) {
				File file = new File(value);
				badFile = !(file.isFile() && file.canRead());
				return file;
			}

			@Override
			String getMessage() {
				return badFile
						? UIPlugin.getLocalString("_UI_InvalidFileName", getValue().getAbsolutePath())
						: super.getMessage();
			}

			@Override
			boolean isValid() {
				return !badFile && super.isValid();
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
				File file = getValue();
				if(file != null) {
					String parent = null;
					if(file.isFile()) {
						parent = file.getParent();
						dialog.setFileName(file.getName());
					}
					else {
						while(file != null) {
							if(file.isDirectory()) {
								parent = file.getAbsolutePath();
								break;
							}
							file = file.getParentFile();
						}
					}
					if(parent != null)
						dialog.setFilterPath(parent);
					dialog.setFilterExtensions(new String[] { "*.pem" });
				}
				String txt = dialog.open();
				getTextWidget().setText(txt);
			}
		}

		class ValidateSSLDirModifyListener extends FileModifyAndSelectionListener {
			ValidateSSLDirModifyListener(File value) {
				super(value, null);
			}

			@Override
			File convert(String text) {
				return new File(text);
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				File file = getValue();
				if(file != null) {
					String parent = null;
					while(file != null) {
						if(file.isDirectory()) {
							parent = file.getAbsolutePath();
							break;
						}
						file = file.getParentFile();
					}
					if(parent != null)
						dialog.setFilterPath(parent);
				}
				String txt = dialog.open();
				getTextWidget().setText(txt);
			}
		}

		abstract class ValidatingModifyListener<T> implements ModifyListener {
			private final String requiredMsgKey;

			private Text text;

			private T value;

			ValidatingModifyListener(T initialValue, String requiredMsgKey) {
				this.requiredMsgKey = requiredMsgKey;
				this.value = initialValue;
			}

			void clear() {
				value = null;
			}

			abstract T convert(String value);

			void dispose() {
				text.removeModifyListener(this);
				text = null;
			}

			String getMessage() {
				return UIPlugin.getLocalString(requiredMsgKey);
			}

			Text getTextWidget() {
				return text;
			}

			T getValue() {
				return value;
			}

			boolean isValid() {
				return requiredMsgKey == null || value != null;
			}

			@Override
			public void modifyText(ModifyEvent e) {
				String txt = ((Text) e.widget).getText().trim();
				if(txt.length() > 0)
					value = convert(txt);
				else
					clear();
				validatePage();
			}

			void setTextWidget(Text text) {
				this.text = text;
			}

			boolean validate() {
				if(!isValid()) {
					setErrorMessage(getMessage());
					return false;
				}
				return true;
			}
		}

		private BasicModifyListener dns = new BasicModifyListener(null, "_UI_DNS_must_be_set");

		private BasicModifyListener port = new BasicModifyListener(null, "_UI_Port_must_be_set");

		private ValidateSSLDirModifyListener sslDir = new ValidateSSLDirModifyListener(null);

		private ValidateFileModifyListener caCert = new ValidateFileModifyListener(null, null);

		private ValidateFileModifyListener hostCert = new ValidateFileModifyListener(null, null);

		private ValidateFileModifyListener hostPrivateKey = new ValidateFileModifyListener(null, null);

		protected NewPuppetDBClientWizardPage() {
			super("Add new PuppetDB Connection");
		}

		@Override
		public void createControl(Composite parent) {
			FormToolkit toolkit = new FormToolkit(parent.getDisplay());

			Form form = toolkit.createForm(parent);
			Composite composite = form.getBody();
			composite.setLayout(new GridLayout(1, true));
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(composite);

			createServerGroup(composite, toolkit);
			createCredentialsGroup(composite, toolkit);
			setControl(composite);
		}

		protected void createCredentialsGroup(Composite parent, FormToolkit toolkit) {
			Group group = new Group(parent, toolkit.getOrientation());
			toolkit.adapt(group);
			group.setText(UIPlugin.getLocalString("_UI_CredentialsGroup_title"));
			group.setLayout(new GridLayout(3, false));
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(group);

			createLabeledFileSelectionText("_UI_SSL_Directory_label", toolkit, group, sslDir);
			createLabeledFileSelectionText("_UI_CaCert_label", toolkit, group, caCert);
			createLabeledFileSelectionText("_UI_HostCert_label", toolkit, group, hostCert);
			createLabeledFileSelectionText("_UI_HostPrivateKey_label", toolkit, group, hostPrivateKey);
			toolkit.paintBordersFor(group);
		}

		private Text createLabeledFileSelectionText(String labelKey, FormToolkit toolkit, Composite parent,
				FileModifyAndSelectionListener listener) {
			Text text = createLabeledText(labelKey, toolkit, parent, listener);
			Button button = new Button(parent, SWT.NONE);
			button.setText(UIPlugin.getLocalString("_UI_Browse_label"));
			button.addSelectionListener(listener);
			return text;
		}

		private Text createLabeledText(String labelKey, FormToolkit toolkit, Composite parent, ValidatingModifyListener<?> listener) {
			toolkit.createLabel(parent, UIPlugin.getLocalString(labelKey));
			Text text = toolkit.createText(parent, null);
			text.addModifyListener(listener);
			listener.setTextWidget(text);
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(text);
			return text;
		}

		protected void createServerGroup(Composite parent, FormToolkit toolkit) {
			Group group = new Group(parent, toolkit.getOrientation());
			toolkit.adapt(group);
			group.setText(UIPlugin.getLocalString("_UI_ServerGroup_title"));
			group.setLayout(new GridLayout(4, false));
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(group);

			Text dnsField = createLabeledText("_UI_Hostname_label", toolkit, group, dns);
			dnsField.addVerifyListener(new HostnameVerifyListener());
			Text portField = createLabeledText("_UI_Port_label", toolkit, group, port);
			portField.addVerifyListener(new IntVerifyListener());
			portField.setText("8080");
			toolkit.paintBordersFor(group);
		}

		@Override
		public void dispose() {
			caCert.dispose();
			dns.dispose();
			port.dispose();
			sslDir.dispose();
			hostCert.dispose();
			hostPrivateKey.dispose();
			super.dispose();
		}

		public boolean performFinish() {
			return Util.alterPreferences(getShell(), puppetDBManager, new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					PuppetDBConnectionPreferences access = puppetDBManager.add(dns.getValue(), Integer.parseInt(port.getValue()));
					access.setCaCert(readAsciiFile(caCert.getValue()));
					access.setHostCert(readAsciiFile(hostCert.getValue()));
					access.setHostPrivateKey(readAsciiFile(hostPrivateKey.getValue()));
					puppetDBManager.flush();
					return null;
				}
			});
		}

		String readAsciiFile(File file) throws IOException {
			if(file == null)
				return null;
			ByteArrayOutputStream bld = new ByteArrayOutputStream();
			FileInputStream input = new FileInputStream(file);
			try {
				byte[] buffer = new byte[4096];
				int rdLen;
				while((rdLen = input.read(buffer)) > 0)
					bld.write(buffer, 0, rdLen);
				return new String(bld.toByteArray(), "ASCII");
			}
			finally {
				input.close();
			}
		}

		void setSSLFiles() {
			File dir = sslDir.getValue();
			if(dir == null || !dir.isDirectory())
				return;

			String hostname = dns.getValue();
			if(caCert.getValue() == null) {
				File caCertFile = new File(new File(dir, "ca"), "ca_crt.pem");
				if(caCertFile.isFile() && caCertFile.canRead())
					caCert.getTextWidget().setText(caCertFile.getAbsolutePath());
			}
			if(hostname != null) {
				String hostPem = hostname + ".pem";
				if(hostCert.getValue() == null) {
					File hostCertFile = new File(new File(dir, "certs"), hostPem);
					if(hostCertFile.isFile() && hostCertFile.canRead())
						hostCert.getTextWidget().setText(hostCertFile.getAbsolutePath());
				}
				if(hostPrivateKey.getValue() == null) {
					File hostPrivateKeyFile = new File(new File(dir, "private_keys"), hostPem);
					if(hostPrivateKeyFile.isFile() && hostPrivateKeyFile.canRead())
						hostPrivateKey.getTextWidget().setText(hostPrivateKeyFile.getAbsolutePath());
				}
			}
		}

		void validatePage() {
			setErrorMessage(null);
			setSSLFiles();
			// @fmtOff
			setPageComplete(
				   dns.validate()
				&& port.validate()
				&& validateUnique()
				&& caCert.validate()
				&& hostCert.validate()
				&& hostPrivateKey.validate());
			// @fmtOn
		}

		boolean validateUnique() {
			try {
				if(puppetDBManager.get(dns.getValue(), Integer.parseInt(port.getValue())) != null) {
					setErrorMessage(UIPlugin.getLocalString("_UI_PuppetDB_Already_Exists"));
					return false;
				}
			}
			catch(BackingStoreException e) {
				setErrorMessage(e.getMessage());
				return false;
			}
			return true;
		}
	}

	@Inject
	private PuppetDBManager puppetDBManager;

	private NewPuppetDBClientWizardPage mainPage;

	public NewPuppetDBClientWizard() {
		setWindowTitle(UIPlugin.getLocalString("_UI_New_PuppetDB_Connection"));
	}

	/**
	 * Constructor used when started from {@link PuppetResourceEventsView}
	 * 
	 * @param puppetDBManager
	 */
	NewPuppetDBClientWizard(PuppetDBManager puppetDBManager) {
		this();
		this.puppetDBManager = puppetDBManager;
	}

	@Override
	public void addPages() {
		mainPage = new NewPuppetDBClientWizardPage();
		addPage(mainPage);
	}

	PuppetDBManager getPuppetDBManager() {
		return puppetDBManager;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	@Override
	public boolean performFinish() {
		return mainPage.performFinish();
	}
}
