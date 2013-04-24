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

import java.io.IOException;
import java.util.Iterator;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.util.ModuleUtils;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.pp.dsl.ui.PPUiConstants;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.MarkerAnnotation;

public class ModuleMetadataEditor extends FormEditor implements IGotoMarker, ITextListener {

	static class DiagnosticAnnotation extends Annotation {
		private static final String INFO_TYPE = "org.eclipse.ui.workbench.texteditor.info"; //$NON-NLS-1$

		private static final String ERROR_TYPE = "org.eclipse.ui.workbench.texteditor.error"; //$NON-NLS-1$

		private static final String WARNING_TYPE = "org.eclipse.ui.workbench.texteditor.warning"; //$NON-NLS-1$

		static void add(Diagnostic diag, IAnnotationModel model, IDocument text) {
			IRegion region;
			Position position;
			try {
				int line = 0;
				if(diag.getLineNumber() > 0)
					line = diag.getLineNumber() - 1;
				region = text.getLineInformation(line);
				position = new Position(region.getOffset(), region.getLength());
			}
			catch(BadLocationException e) {
				position = new Position(0, text.getLength());
			}
			model.addAnnotation(new DiagnosticAnnotation(diag), position);
		}

		static void clear(IAnnotationModel model) {
			Iterator<?> iter = model.getAnnotationIterator();
			while(iter.hasNext()) {
				Object a = iter.next();
				if(a instanceof MarkerAnnotation) {
					try {
						// Remove this marker from the transient document model. It stems from the persisted content
						// and may since have been corrected. If not, it will be added as a DiagnosticAnnotation
						if(((MarkerAnnotation) a).getMarker().getType().equals(
							PPUiConstants.PUPPET_MODULE_PROBLEM_MARKER_TYPE))
							model.removeAnnotation((Annotation) a);
					}
					catch(CoreException e) {
					}
				}
				if(a instanceof DiagnosticAnnotation)
					model.removeAnnotation((Annotation) a);
			}
		}

		static String getAnnotationType(Diagnostic diag) {
			switch(diag.getSeverity()) {
				case Diagnostic.FATAL:
				case Diagnostic.ERROR:
					return ERROR_TYPE;
				case Diagnostic.WARNING:
					return WARNING_TYPE;
			}
			return INFO_TYPE;
		}

		DiagnosticAnnotation(Diagnostic diag) {
			super(getAnnotationType(diag), false, diag.getMessage());
		}
	}

	class ModuleTextEditor extends TextEditor {
		ModuleTextEditor() {
			setPartName(UIPlugin.INSTANCE.getString("_UI_Source_title")); //$NON-NLS-1
		}

		@Override
		protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
			SourceViewer sourceViewer = new SourceViewer(parent, ruler, styles);
			sourceViewer.addTextListener(ModuleMetadataEditor.this);
			return sourceViewer;
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

	private ModuleMetadataOverviewPage overviewPage;

	private ModuleTextEditor sourcePage;

	private final Metadata metadata = new Metadata();

	private boolean sourceInCharge;

	@Override
	protected void addPages() {
		try {
			overviewPage = new ModuleMetadataOverviewPage(
				this, "overview", UIPlugin.INSTANCE.getString("_UI_Overview_title")); //$NON-NLS-1$ //$NON-NLS-2$
			addPage(overviewPage);

			// sourcePage = new ModuleMetadataSourcePage(this, "source", UIPlugin.INSTANCE.getString("_UI_Source_title")); //$NON-NLS-1$ //$NON-NLS-2$
			// addPage(sourcePage);
			sourcePage = new ModuleTextEditor();
			setPageText(addPage(sourcePage, getEditorInput()), UIPlugin.INSTANCE.getString("_UI_Source_title"));
		}
		catch(Exception e) {
			UIPlugin.INSTANCE.log(e);
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		if(sourceInCharge) {
			sourcePage.doSave(monitor);
			sourceInCharge = false;
		}
		else {
			commitPages(true);
			WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

				@Override
				protected void execute(IProgressMonitor progressMonitor) {
					try {
						FileEditorInput input = (FileEditorInput) getEditorInput();
						IFile file = input.getFile();
						ModuleUtils.saveAsModulefile(metadata, file.getLocation().toFile());
						sourcePage.getDocumentProvider().resetDocument(input);
						file.refreshLocal(0, progressMonitor);
					}
					catch(Exception exception) {
						UIPlugin.INSTANCE.log(exception);
					}
					finally {
						progressMonitor.done();
					}
				}
			};

			try {
				operation.run(monitor);

				editorDirtyStateChanged();
			}
			catch(Exception exception) {
				UIPlugin.INSTANCE.log(exception);
			}
		}
		ModuleName name = metadata.getName();
		if(name != null)
			setPartName(name.toString());
	}

	@Override
	public void doSaveAs() {
		// do nothing
	}

	Metadata getMetadata() {
		return metadata;
	}

	@Override
	public void gotoMarker(IMarker marker) {
		((IGotoMarker) sourcePage.getAdapter(IGotoMarker.class)).gotoMarker(marker);
		setActiveEditor(sourcePage);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);

		if(input instanceof FileEditorInput) {
			IPath path;
			try {
				path = ((FileEditorInput) input).getPath();
			}
			catch(IllegalArgumentException e) {
				// This happens when an editor references a file that no longer
				// exists. Just ignore
				return;
			}
			try {
				ModuleUtils.parseModulefile(path.toFile(), metadata, new Diagnostic());
				if(metadata.getName() != null)
					setPartName(metadata.getName().toString());
			}
			catch(IOException ioe) {
				UIPlugin.INSTANCE.log(ioe);
			}
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	void overviewInCharge() {
		sourceInCharge = false;
	}

	void refreshFromOverviewPage() {
		IDocument document = sourcePage.getDocumentProvider().getDocument(getEditorInput());
		if(document != null)
			document.set(ModuleUtils.toModulefileContent(metadata));
		overviewInCharge();
	}

	@Override
	public void textChanged(TextEvent event) {
		DocumentEvent docEvent = event.getDocumentEvent();
		if(docEvent == null)
			return;

		String text = docEvent.getDocument().get();
		Diagnostic chain = new Diagnostic();
		try {
			ModuleUtils.parseModulefile("data", text, metadata, chain);
		}
		catch(Exception ex) {
		}
		sourcePage.updateDiagnosticAnnotations(chain);
		sourceInCharge = true;
		overviewPage.markStale();
	}
}
