/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.xtext.ui.editor.formatting;

import java.util.Iterator;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.IDomModelFormatter;
import org.cloudsmith.xtext.dommodel.formatter.IFormattingContext.FormattingContextProvider;
import org.cloudsmith.xtext.serializer.DomBasedSerializer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.IFormattingStrategy;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.xtext.parsetree.reconstr.IHiddenTokenHelper;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic;
import org.eclipse.xtext.serializer.sequencer.IContextFinder;
import org.eclipse.xtext.ui.editor.formatting.IContentFormatterFactory;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.reconciler.ReplaceRegion;
import org.eclipse.xtext.util.EmfFormatter;
import org.eclipse.xtext.util.TextRegion;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;

public class ContentFormatterFactory implements IContentFormatterFactory {

	public class ContentFormatter implements IContentFormatter {
		public void format(IDocument document, IRegion region) {
			IXtextDocument doc = (IXtextDocument) document;
			ReplaceRegion r = doc.readOnly(new FormattingUnitOfWork(region));
			try {
				if(r != null) {

					String current = null;
					try {
						current = doc.get(r.getOffset(), r.getLength());
					}
					catch(BadLocationException e) {
						// ignore, current is null
					}
					// Optimize - if replacement is equal to current
					if(current == null || !current.equals(r.getText()))
						doc.replace(r.getOffset(), r.getLength(), r.getText());
				}
			}
			catch(BadLocationException e) {
				throw new RuntimeException(e);
			}
		}

		public IFormattingStrategy getFormattingStrategy(String contentType) {
			return null;
		}
	}

	public class FormattingUnitOfWork implements IUnitOfWork<ReplaceRegion, XtextResource> {

		protected final IRegion region;

		public FormattingUnitOfWork(IRegion region) {
			super();
			this.region = region;
		}

		public ReplaceRegion exec(XtextResource state) throws Exception {
			// TODO: viewer related parameters passed to formatting context?
			// TODO: get the dom root
			ISerializationDiagnostic.Acceptor errors = ISerializationDiagnostic.EXCEPTION_THROWING_ACCEPTOR;
			// EObject context = getContext(state.getContents().get(0));
			IDomNode root = serializer.serializeToDom(state.getContents().get(0), false);
			org.eclipse.xtext.util.ReplaceRegion r = formatter.format(
				root, new TextRegion(region.getOffset(), region.getLength()), formattingContextProvider.get(), errors);
			return new ReplaceRegion(r.getOffset(), r.getLength(), r.getText());
		}

		protected EObject getContext(EObject semanticObject) {
			Iterator<EObject> contexts = contextFinder.findContextsByContentsAndContainer(semanticObject, null).iterator();
			if(!contexts.hasNext())
				throw new RuntimeException("No Context for " + EmfFormatter.objPath(semanticObject) + " could be found");
			return contexts.next();
		}

	}

	@Inject
	protected IDomModelFormatter formatter;

	@Inject
	FormattingContextProvider formattingContextProvider;

	@Inject
	protected IContextFinder contextFinder;

	@Inject
	IHiddenTokenHelper hiddenTokenHelper;

	@Inject
	DomBasedSerializer serializer;

	public IContentFormatter createConfiguredFormatter(SourceViewerConfiguration configuration,
			ISourceViewer sourceViewer) {
		// TODO: pick up important information about viewer and pass on
		// configuration.getTabWidth(sourceViewer); // ?? Always returns 4 ?!?

		return new ContentFormatter();
	}

}
