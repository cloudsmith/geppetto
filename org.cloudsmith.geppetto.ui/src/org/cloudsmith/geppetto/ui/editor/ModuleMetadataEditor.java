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

import java.util.Iterator;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.pp.dsl.ui.PPUiConstants;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.IElementStateListener;
import org.eclipse.ui.texteditor.MarkerAnnotation;

import com.google.inject.Inject;

public class ModuleMetadataEditor extends FormEditor implements IGotoMarker {

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
				else if(a instanceof DiagnosticAnnotation)
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

	class ElementListener implements IElementStateListener {
		public void elementContentAboutToBeReplaced(Object element) {
		}

		public void elementContentReplaced(Object element) {
		}

		public void elementDeleted(Object element) {
			if(element != null && element.equals(getEditorInput()))
				close(false);
		}

		public void elementDirtyStateChanged(Object element, boolean isDirty) {
		}

		public void elementMoved(Object originalElement, Object movedElement) {
			if(originalElement != null && originalElement.equals(getEditorInput())) {
				close(true);
			}
		}
	}

	@Inject
	private Forge forge;

	private ModuleOverviewPage overviewPage;

	private ModuleDependenciesPage dependenciesPage;

	private ModuleSourcePage sourcePage;

	private boolean stale = false;

	private final MetadataModel model = new MetadataModel();

	@Override
	protected void addPages() {
		try {
			overviewPage = new ModuleOverviewPage(this, "overview", UIPlugin.INSTANCE.getString("_UI_Overview_title")); //$NON-NLS-1$ //$NON-NLS-2$
			addPage(overviewPage);

			dependenciesPage = new ModuleDependenciesPage(
				this, "dependencies", UIPlugin.INSTANCE.getString("_UI_Dependencies_title")); //$NON-NLS-1$ //$NON-NLS-2$
			addPage(dependenciesPage);

			sourcePage = new ModuleSourcePage(this);
			setPageText(addPage(sourcePage, getEditorInput()), UIPlugin.INSTANCE.getString("_UI_Source_title"));
			sourcePage.getDocumentProvider().addElementStateListener(new ElementListener());
			refreshModel();

			String name = getModuleName();
			if(name != null)
				setPartName(name);
		}
		catch(Exception e) {
			UIPlugin.INSTANCE.log(e);
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		sourcePage.doSave(monitor);
		String name = getModuleName();
		if(name != null)
			setPartName(name);
	}

	@Override
	public void doSaveAs() {
		// do nothing
	}

	IDocument getDocument() {
		if(sourcePage != null) {
			IDocumentProvider dp = sourcePage.getDocumentProvider();
			if(dp != null)
				return dp.getDocument(getEditorInput());
		}
		return null;
	}

	IFile getFile() {
		IEditorInput input = getEditorInput();
		return input == null
				? null
				: (IFile) input.getAdapter(IFile.class);
	}

	Forge getForge() {
		return forge;
	}

	MetadataModel getModel() {
		if(stale)
			refreshModel();
		return model;
	}

	String getModuleName() {
		return getModel().getModuleName();
	}

	ModuleOverviewPage getOverviewPage() {
		return overviewPage;
	}

	IPath getPath() {
		IFile currentFile = getFile();
		return currentFile == null
				? null
				: currentFile.getLocation();
	}

	ModuleSourcePage getSourcePage() {
		return sourcePage;
	}

	@Override
	public void gotoMarker(IMarker marker) {
		int line = marker.getAttribute(IMarker.LINE_NUMBER, 0);
		if(line > 0) {
			((IGotoMarker) sourcePage.getAdapter(IGotoMarker.class)).gotoMarker(marker);
			setActiveEditor(sourcePage);
		}
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	void markStale() {
		stale = true;
		overviewPage.markStale();
		dependenciesPage.markStale();
	}

	private void refreshModel() {
		model.setDocument(getDocument(), getPath(), new Diagnostic());
		stale = false;
	}
}
