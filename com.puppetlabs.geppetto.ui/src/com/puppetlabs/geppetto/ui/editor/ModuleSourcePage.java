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

import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.ui.UIPlugin;
import com.puppetlabs.geppetto.ui.editor.ModuleMetadataEditor.DiagnosticAnnotation;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelListener;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IElementStateListener;

class ModuleSourcePage extends TextEditor {
	private final ModuleMetadataEditor editor;

	private boolean clearingAnnotations = false;

	private final IElementStateListener stateListener = new IElementStateListener() {
		public void elementContentAboutToBeReplaced(Object element) {
		}

		public void elementContentReplaced(Object element) {
		}

		public void elementDeleted(Object element) {
			if(element != null && element.equals(getEditorInput()))
				editor.close(false);
		}

		public void elementDirtyStateChanged(Object element, boolean isDirty) {
		}

		public void elementMoved(Object originalElement, Object movedElement) {
			if(originalElement != null && originalElement.equals(getEditorInput())) {
				editor.close(true);
			}
		}
	};

	private boolean internalTextChange = false;

	ModuleSourcePage(ModuleMetadataEditor editor) {
		this.editor = editor;
		setPartName(UIPlugin.getLocalString("_UI_Source_title")); //$NON-NLS-1
	}

	@Override
	protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
		SourceViewer sourceViewer = new SourceViewer(parent, ruler, styles);
		sourceViewer.addTextListener(new ITextListener() {
			@Override
			public void textChanged(TextEvent event) {
				if(!internalTextChange) {
					DocumentEvent docEvent = event.getDocumentEvent();
					if(docEvent != null)
						validate();
				}
			}
		});
		return sourceViewer;
	}

	@Override
	public void dispose() {
		IDocumentProvider dp = getDocumentProvider();
		if(dp != null)
			dp.removeElementStateListener(stateListener);
		super.dispose();
	}

	void endInternalTextChange() {
		internalTextChange = false;
	}

	void initialize() {
		getDocumentProvider().addElementStateListener(stateListener);
		getSourceViewer().getAnnotationModel().addAnnotationModelListener(new IAnnotationModelListener() {
			@Override
			public void modelChanged(IAnnotationModel model) {
				if(!clearingAnnotations) {
					clearingAnnotations = true;
					try {
						DiagnosticAnnotation.clearBuilderAnnotations(model);
					}
					finally {
						clearingAnnotations = false;
					}
				}
			}
		});
		validate();
	}

	boolean isActive() {
		return equals(editor.getActiveEditor());
	}

	void startInternalTextChange() {
		internalTextChange = true;
	}

	void updateDiagnosticAnnotations(Diagnostic chain) {
		ISourceViewer viewer = getSourceViewer();
		if(viewer == null)
			return;
		IAnnotationModel model = viewer.getAnnotationModel();
		if(model == null)
			return;
		IDocument document = viewer.getDocument();
		DiagnosticAnnotation.clearDiagnosticAnnotations(model);
		for(Diagnostic diag : chain)
			if(diag.getSeverity() >= Diagnostic.WARNING)
				DiagnosticAnnotation.add(diag, model, document);
	}

	void validate() {
		Diagnostic chain = new Diagnostic();
		editor.getModel().setDocument(editor.getDocument(), editor.getPath(), chain);
		updateDiagnosticAnnotations(chain);
		if(isActive())
			editor.markStale();
	}
}
