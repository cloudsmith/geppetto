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
package com.puppetlabs.geppetto.ui.editor;

import static com.puppetlabs.geppetto.common.Strings.trimToNull;
import static com.puppetlabs.geppetto.forge.Forge.MODULEFILE_NAME;
import static org.eclipse.xtext.util.Strings.emptyIfNull;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.puppetlabs.geppetto.forge.v2.model.ModuleName;
import com.puppetlabs.geppetto.ui.UIPlugin;

class ModuleOverviewPage extends GuardedModulePage {

	protected class DetailsSectionPart extends SectionPart {

		protected Text sourceText;

		protected Text projectPageText;

		protected Text summaryText;

		protected Text descriptionText;

		protected DetailsSectionPart(Composite parent, FormToolkit toolkit) {
			super(parent, toolkit, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);

			Section section = getSection();

			section.setText(UIPlugin.getLocalString("_UI_Details_title")); //$NON-NLS-1$
			section.setDescription(UIPlugin.getLocalString("_UI_Details_description")); //$NON-NLS-1$

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).span(2, 1).applyTo(section);

			Composite client = toolkit.createComposite(section);
			client.setLayout(new GridLayout(2, false));

			toolkit.createLabel(client, UIPlugin.getLocalString("_UI_Source_label")); //$NON-NLS-1$

			sourceText = toolkit.createText(client, null);
			sourceText.addVerifyListener(defaultVerifier);
			sourceText.addModifyListener(new GuardedModifyListener() {
				@Override
				public void handleEvent(ModifyEvent e) {
					getModel().setSource(sourceText.getText());
				}
			});

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(sourceText);

			toolkit.createLabel(client, UIPlugin.getLocalString("_UI_ProjectPage_label")); //$NON-NLS-1$

			projectPageText = toolkit.createText(client, null);
			projectPageText.addVerifyListener(defaultVerifier);
			projectPageText.addModifyListener(new GuardedModifyListener() {
				@Override
				public void handleEvent(ModifyEvent e) {
					getModel().setProjectPage(projectPageText.getText());
				}
			});

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(projectPageText);

			toolkit.createLabel(client, UIPlugin.getLocalString("_UI_Summary_label")); //$NON-NLS-1$

			summaryText = toolkit.createText(client, null);
			summaryText.addVerifyListener(defaultVerifier);
			summaryText.addModifyListener(new GuardedModifyListener() {
				@Override
				public void handleEvent(ModifyEvent e) {
					getModel().setSummary(summaryText.getText());
				}
			});

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(summaryText);

			toolkit.createLabel(client, UIPlugin.getLocalString("_UI_Description_label")); //$NON-NLS-1$

			descriptionText = toolkit.createText(client, null, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
			descriptionText.addVerifyListener(defaultVerifier);
			descriptionText.addModifyListener(new GuardedModifyListener() {
				@Override
				public void handleEvent(ModifyEvent e) {
					getModel().setDescription(descriptionText.getText());
				}
			});

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(descriptionText);
			toolkit.paintBordersFor(client);
			section.setClient(client);
		}

		@Override
		public void refresh() {
			if(sourceText.isDisposed())
				return;
			refresh = true;
			try {
				MetadataModel metadata = getModel();
				sourceText.setText(metadata.getSource());
				projectPageText.setText(metadata.getProjectPage());
				summaryText.setText(metadata.getSummary());
				descriptionText.setText(metadata.getDescription());
				super.refresh();
			}
			finally {
				refresh = false;
			}
		}
	}

	protected class GeneralInformationSectionPart extends ModuleSectionPart {

		private Text userText;

		private Text nameText;

		private Text versionText;

		private Text authorText;

		private Text licenseText;

		private ModifyListener nameAndUserListener = new GuardedModifyListener() {

			@Override
			public void handleEvent(ModifyEvent me) {
				String user = trimToNull(userText.getText());
				String name = trimToNull(nameText.getText());
				validateOwnerName(user, null, userText);
				validateModuleName(name, null, nameText);

				MetadataModel metadata = getModel();
				if(user == null && name == null)
					metadata.setModuleName("");
				else {
					if(user == null)
						metadata.setModuleName(name);
					else if(name == null)
						metadata.setModuleName(user);
					else
						metadata.setModuleName(user + '-' + name);
				}
			}
		};

		protected GeneralInformationSectionPart(Composite parent, FormToolkit toolkit) {
			super(parent, toolkit, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);

			Section section = getSection();

			section.setText(UIPlugin.getLocalString("_UI_GeneralInformation_title")); //$NON-NLS-1$
			section.setDescription(UIPlugin.getLocalString("_UI_GeneralInformation_description")); //$NON-NLS-1$

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(section);
			GridDataFactory textGDFactory = GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).indent(
				4, 0);

			Composite client = toolkit.createComposite(section);
			client.setLayout(new GridLayout(4, false));

			toolkit.createLabel(client, UIPlugin.getLocalString("_UI_Name_label")); //$NON-NLS-1$

			userText = toolkit.createText(client, null);
			userText.addVerifyListener(ownerCharsVerifier);
			userText.addModifyListener(nameAndUserListener);
			textGDFactory.applyTo(userText);

			toolkit.createLabel(client, "-"); //$NON-NLS-1$

			nameText = toolkit.createText(client, null);
			nameText.addVerifyListener(nameCharsVerifier);
			nameText.addModifyListener(nameAndUserListener);

			textGDFactory.applyTo(nameText);

			toolkit.createLabel(client, UIPlugin.getLocalString("_UI_Version_label")); //$NON-NLS-1$

			versionText = toolkit.createText(client, null);
			versionText.addVerifyListener(versionCharsVerifier);
			versionText.addModifyListener(new GuardedModifyListener() {
				@Override
				public void handleEvent(ModifyEvent e) {
					String version = versionText.getText();
					validateVersion(version, versionText);
					getModel().setVersion(version);
				}
			});

			// Rest is on a line of their own so they need to span 3 columns
			textGDFactory = textGDFactory.span(3, 1);
			textGDFactory.applyTo(versionText);

			toolkit.createLabel(client, UIPlugin.getLocalString("_UI_Author_label")); //$NON-NLS-1$

			authorText = toolkit.createText(client, null);
			authorText.addVerifyListener(defaultVerifier);
			authorText.addModifyListener(new GuardedModifyListener() {
				@Override
				public void handleEvent(ModifyEvent e) {
					getModel().setAuthor(authorText.getText());
				}
			});
			textGDFactory.applyTo(authorText);

			toolkit.createLabel(client, UIPlugin.getLocalString("_UI_License_label")); //$NON-NLS-1$

			licenseText = toolkit.createText(client, null);
			licenseText.addVerifyListener(defaultVerifier);
			licenseText.addModifyListener(new GuardedModifyListener() {
				@Override
				public void handleEvent(ModifyEvent e) {
					getModel().setLicense(licenseText.getText());
				}
			});
			textGDFactory.applyTo(licenseText);
			toolkit.paintBordersFor(client);
			section.setClient(client);
		}

		@Override
		public void refresh() {
			if(userText.isDisposed())
				return;
			refresh = true;
			try {
				MetadataModel model = getModel();
				String[] qname = ModuleName.splitName(emptyIfNull(model.getModuleName()));
				String owner = qname[0];
				String name = qname[1];
				userText.setText(emptyIfNull(owner));
				nameText.setText(emptyIfNull(name));
				String version = model.getVersion();
				versionText.setText(emptyIfNull(version));
				if(model.isSyntaxError()) {
					clearMessage(userText);
					clearMessage(nameText);
					clearMessage(versionText);
					showSyntaxError(true);
				}
				else {
					showSyntaxError(false);
					showDependenciesError(model.hasDependencyErrors());
					validateOwnerName(owner, null, userText);
					validateModuleName(name, null, nameText);
					validateVersion(trimToNull(version), versionText);
				}

				authorText.setText(model.getAuthor());
				licenseText.setText(model.getLicense());
			}
			finally {
				refresh = false;
			}
			super.refresh();
		}
	}

	static String nameWithDashSeparator(String name) {
		if(name == null)
			return "";

		int sepIdx = name.indexOf('-');
		if(sepIdx < 0) {
			sepIdx = name.indexOf('/');
			if(sepIdx >= 0)
				return name.substring(0, sepIdx) + '-' + name.substring(sepIdx + 1);
		}
		return name;
	}

	private VerifyListener nameCharsVerifier = new ValidateInputListener() {
		@Override
		public void verifyText(VerifyEvent ev) {
			super.verifyText(ev);
			if(ev.doit) {
				// Verify that the typed character is valid as a 'name' in the 'owner/name' combination
				char c = ev.character;
				ev.doit = c < 0x20 || c == '_' || c >= '0' && c <= '9' || c >= 'a' && c <= 'z';
			}
		}
	};

	private VerifyListener ownerCharsVerifier = new ValidateInputListener() {
		@Override
		public void verifyText(VerifyEvent ev) {
			super.verifyText(ev);
			if(ev.doit) {
				// Verify that the typed character is valid as an 'owner' in the 'owner/name' combination
				char c = ev.character;
				ev.doit = c < 0x20 || c >= '0' && c <= '9' || c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z';
			}
		}
	};

	private VerifyListener versionCharsVerifier = new ValidateInputListener() {
		@Override
		public void verifyText(VerifyEvent ev) {
			super.verifyText(ev);
			if(ev.doit) {
				char c = ev.character;
				// @fmtOff
				ev.doit =
					   c < 0x20
					|| c == '_'
					|| c == '-'
					|| c == '.'
					|| c >= '0' && c <= '9'
					|| c >= 'a' && c <= 'z'
					|| c >= 'A' && c <= 'Z';
				// @fmtOn
			}
		}
	};

	public ModuleOverviewPage(ModuleMetadataEditor editor, String id, String title) {
		super(editor, id, title);
	}

	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		super.createFormContent(managedForm);
		String formTitle;
		IFile file = getEditor().getFile();
		if(MODULEFILE_NAME.equals(file.getName()))
			formTitle = "_UI_Modulefile_Overview_title";
		else if(file.isDerived())
			formTitle = "_UI_Derived_Metadata_Overview_title";
		else
			formTitle = "_UI_Metadata_Overview_title";

		managedForm.getForm().setText(UIPlugin.getLocalString(formTitle));
		Composite body = managedForm.getForm().getBody();
		body.setLayout(new GridLayout(1, true));
		FormToolkit toolkit = managedForm.getToolkit();
		managedForm.addPart(new GeneralInformationSectionPart(body, toolkit));
		managedForm.addPart(new DetailsSectionPart(body, toolkit));
		body.addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event event) {
				managedForm.getMessageManager().update();
			}
		});
	}
}
