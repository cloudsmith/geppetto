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

import static com.puppetlabs.geppetto.forge.model.ModuleName.checkName;
import static com.puppetlabs.geppetto.forge.model.ModuleName.checkOwner;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.IMessageManager;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;
import com.puppetlabs.geppetto.ui.UIPlugin;

abstract class GuardedModulePage extends FormPage {

	protected abstract class GuardedModifyListener implements ModifyListener {
		abstract void handleEvent(ModifyEvent e);

		@Override
		public final void modifyText(ModifyEvent e) {
			if(!refresh) {
				handlingEvent = true;
				try {
					handleEvent(e);
				}
				finally {
					handlingEvent = false;
				}
			}
		}
	}

	class ModuleSectionPart extends SectionPart {

		public ModuleSectionPart(Composite parent, FormToolkit toolkit, int style) {
			super(parent, toolkit, style);
		}

		void clearMessage(Control control) {
			getManagedForm().getMessageManager().removeMessage(DEFAULT_MESSAGE_KEY, control);
		}

		void clearMessages() {
			// We get this call possibly during dispose. At the time the call arrives
			// our form is not disposed but the head of it's contained form is.
			IManagedForm mform = getManagedForm();
			if(mform != null) {
				ScrolledForm sform = mform.getForm();
				if(sform != null) {
					Form form = sform.getForm();
					if(form != null) {
						Composite head = form.getHead();
						if(head != null && !head.isDisposed()) {
							mform.getMessageManager().removeMessages();
						}
					}
				}
			}
		}

		int validateModuleName(String name, String key, Control control) {
			IMessageManager msgManager = getManagedForm().getMessageManager();
			if(name == null) {
				String msg = UIPlugin.getLocalString("_UI_Module_name_missing");
				if(control == null)
					msgManager.addMessage(key, msg, null, IMessageProvider.ERROR);
				else
					msgManager.addMessage(DEFAULT_MESSAGE_KEY, msg, null, IMessageProvider.ERROR, control);
				return IMessageProvider.ERROR;
			}

			int syntaxSeverity = IMessageProvider.NONE;
			String syntax = null;
			try {
				checkName(name, true);
			}
			catch(IllegalArgumentException e) {
				try {
					checkName(name, false);
					syntax = e.getMessage();
					syntaxSeverity = IMessageProvider.WARNING;
				}
				catch(IllegalArgumentException e2) {
					syntax = e2.getMessage();
					syntaxSeverity = IMessageProvider.ERROR;
				}
			}
			if(syntax != null)
				if(control == null)
					msgManager.addMessage(key, syntax, null, syntaxSeverity);
				else
					msgManager.addMessage(DEFAULT_MESSAGE_KEY, syntax, null, syntaxSeverity, control);
			else if(control != null)
				msgManager.removeMessage(DEFAULT_MESSAGE_KEY, control);
			return syntaxSeverity;
		}

		int validateOwnerName(String name, String key, Control control) {
			IMessageManager msgManager = getManagedForm().getMessageManager();
			if(name == null) {
				String msg = UIPlugin.getLocalString("_UI_Module_owner_missing");
				if(control == null)
					msgManager.addMessage(key, msg, null, IMessageProvider.ERROR);
				else
					msgManager.addMessage(DEFAULT_MESSAGE_KEY, msg, null, IMessageProvider.ERROR, control);
				return IMessageProvider.ERROR;
			}

			int syntaxSeverity = IMessageProvider.NONE;
			String syntax = null;
			try {
				checkOwner(name, true);
			}
			catch(IllegalArgumentException e) {
				try {
					checkOwner(name, false);
					syntax = e.getMessage();
					syntaxSeverity = IMessageProvider.WARNING;
				}
				catch(IllegalArgumentException e2) {
					syntax = e2.getMessage();
					syntaxSeverity = IMessageProvider.ERROR;
				}
			}
			if(syntax != null)
				if(control == null)
					msgManager.addMessage(key, syntax, null, syntaxSeverity);
				else
					msgManager.addMessage(DEFAULT_MESSAGE_KEY, syntax, null, syntaxSeverity, control);
			else if(control != null)
				msgManager.removeMessage(DEFAULT_MESSAGE_KEY, control);
			return syntaxSeverity;
		}

		int validateVersion(String version, Control control) {
			IMessageManager msgManager = getManagedForm().getMessageManager();
			if(version == null) {
				String msg = UIPlugin.getLocalString("_UI_Module_version_missing");
				msgManager.addMessage(DEFAULT_MESSAGE_KEY, msg, null, IMessageProvider.ERROR, control);
				return IMessageProvider.ERROR;
			}
			int result = IMessageProvider.NONE;
			try {
				Version.fromString(version);
				msgManager.removeMessage(DEFAULT_MESSAGE_KEY, control);
			}
			catch(IllegalArgumentException e) {
				msgManager.addMessage(DEFAULT_MESSAGE_KEY, e.getMessage(), null, IMessageProvider.ERROR, control);
				result = IMessageProvider.ERROR;
			}
			return result;
		}

		int validateVersionRequirement(String versionRequirement, String key) {
			IMessageManager msgManager = getManagedForm().getMessageManager();
			if(versionRequirement == null) {
				String msg = UIPlugin.getLocalString("_UI_Module_version_requirement_missing");
				msgManager.addMessage(key, msg, null, IMessageProvider.ERROR);
				return IMessageProvider.ERROR;
			}
			int result = IMessageProvider.NONE;
			try {
				VersionRange.create(versionRequirement);
			}
			catch(IllegalArgumentException e) {
				msgManager.addMessage(key, e.getMessage(), null, IMessageProvider.ERROR);
				result = IMessageProvider.ERROR;
			}
			return result;
		}
	}

	class ValidateInputListener implements VerifyListener {
		public void verifyText(VerifyEvent e) {
			if(!refresh)
				e.doit = allowModification();
		}
	}

	VerifyListener defaultVerifier = new ValidateInputListener();

	private static final String DEFAULT_MESSAGE_KEY = "default"; //$NON-NLS-1$

	private boolean handlingEvent;

	boolean refresh = false;

	GuardedModulePage(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	boolean allowModification() {
		ModuleMetadataEditor editor = getEditor();
		ModuleSourcePage sourcePage = editor.getSourcePage();
		if(!sourcePage.isEditable())
			return false;

		IDocument document = editor.getDocument();
		final boolean[] documentChanged = new boolean[1];
		IDocumentListener listener = new IDocumentListener() {
			public void documentAboutToBeChanged(DocumentEvent event) {
			}

			public void documentChanged(DocumentEvent event) {
				documentChanged[0] = true;
			}
		};
		try {
			if(document != null)
				document.addDocumentListener(listener);
			if(!sourcePage.validateEditorInputState() || documentChanged[0]) {
				return false;
			}
		}
		finally {
			if(document != null)
				document.removeDocumentListener(listener);
		}
		return true;
	}

	IFile getCurrentFile() {
		return getEditor().getFile();
	}

	@Override
	public ModuleMetadataEditor getEditor() {
		return (ModuleMetadataEditor) super.getEditor();
	}

	MetadataModel getModel() {
		return getEditor().getModel();
	}

	void markStale() {
		if(handlingEvent)
			// We got this notification because we are setting a value so just
			// disregard it.
			return;

		IManagedForm form = getManagedForm();
		if(form != null)
			for(IFormPart part : form.getParts())
				if(part instanceof SectionPart)
					((SectionPart) part).markStale();
	}

	@Override
	public void setActive(boolean flag) {
		super.setActive(flag);
		if(isActive())
			markStale();
	}

	void showDependenciesError(boolean show) {
		showGeneralError(show, "_UI_Unresolved_dependencies");
	}

	void showGeneralError(boolean show, String msgKey) {
		IMessageManager msgManager = getManagedForm().getMessageManager();
		if(show) {
			String msg = UIPlugin.getLocalString(msgKey);
			msgManager.addMessage(msgKey, msg, null, IMessageProvider.ERROR);
		}
		else
			msgManager.removeMessage(msgKey);
		msgManager.update();
	}

	void showSyntaxError(boolean show) {
		showGeneralError(show, "_UI_Syntax_Error");
	}
}
