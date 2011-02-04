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

import java.util.List;

import org.cloudsmith.geppetto.forge.Dependency;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.texteditor.IDocumentProvider;

class ModuleMetadataSourcePage extends FormPage {

	protected class SourceSectionPart extends SectionPart implements ModifyListener {

		protected TextViewer textViewer;

		protected List<Dependency> dependencies = new UniqueEList<Dependency>();;

		protected Forge forge;

		protected SourceSectionPart(Composite parent, FormToolkit toolkit) {
			super(parent, toolkit, ExpandableComposite.NO_TITLE);

			Section section = getSection();

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(section);

			Composite client = toolkit.createComposite(section);

			GridLayout gridLayout = new GridLayout(1, true);
			gridLayout.marginHeight = 0;
			gridLayout.marginWidth = 0;

			client.setLayout(gridLayout);

			textViewer = new TextViewer(client, SWT.H_SCROLL | SWT.V_SCROLL);

			textViewer.getTextWidget().addModifyListener(this);

			try {
				IDocumentProvider documentProvider = new FileDocumentProvider();
				documentProvider.connect(getEditorInput());

				textViewer.setDocument(documentProvider.getDocument(getEditorInput()));
			}
			catch(Exception e) {
				UIPlugin.INSTANCE.log(e);
			}

			GridDataFactory.fillDefaults().grab(true, true).applyTo(textViewer.getTextWidget());

			section.setClient(client);
		}

		@Override
		public void modifyText(ModifyEvent me) {
			markDirty();
		}
	}

	public ModuleMetadataSourcePage(ModuleMetadataEditor editor, String id, String title) {
		super(editor, id, title);
	}

	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		super.createFormContent(managedForm);

		Composite body = managedForm.getForm().getBody();

		GridLayout gridLayout = new GridLayout(1, true);
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;

		body.setLayout(gridLayout);

		managedForm.addPart(new SourceSectionPart(body, managedForm.getToolkit()));
	}

	protected Metadata getMetadata() {
		return ((ModuleMetadataEditor) getEditor()).getMetadata();
	}

}
