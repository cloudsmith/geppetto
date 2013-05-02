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

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.cloudsmith.geppetto.ui.editor.ModuleMetadataEditor.DiagnosticAnnotation;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;

class ModuleSourcePage extends TextEditor {
	private final ModuleMetadataEditor editor;

	ModuleSourcePage(ModuleMetadataEditor editor) {
		this.editor = editor;
		setPartName(UIPlugin.INSTANCE.getString("_UI_Source_title")); //$NON-NLS-1
	}

	@Override
	protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
		SourceViewer sourceViewer = new SourceViewer(parent, ruler, styles);
		sourceViewer.addTextListener(new ITextListener() {
			@Override
			public void textChanged(TextEvent event) {
				DocumentEvent docEvent = event.getDocumentEvent();
				if(docEvent == null)
					return;

				Diagnostic chain = new Diagnostic();
				editor.getModel().validate(editor.getPath(), docEvent.getDocument(), chain);
				updateDiagnosticAnnotations(chain);
				if(isActive())
					editor.markStale();
			}
		});
		return sourceViewer;
	}

	boolean isActive() {
		return equals(editor.getActiveEditor());
	}

	void updateDiagnosticAnnotations(Diagnostic chain) {
		ISourceViewer viewer = getSourceViewer();
		IAnnotationModel model = viewer.getAnnotationModel();
		IDocument document = viewer.getDocument();
		DiagnosticAnnotation.clear(model);
		for(Diagnostic diag : chain)
			if(diag.getSeverity() >= Diagnostic.WARNING)
				DiagnosticAnnotation.add(diag, model, document);
	}
}
