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

import org.cloudsmith.geppetto.pp.dsl.validation.PPValidationUtils;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.IDomModelFormatter;
import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContextFactory;
import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContextFactory.FormattingOption;
import org.cloudsmith.xtext.resource.ResourceAccessScope;
import org.cloudsmith.xtext.serializer.DomBasedSerializer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.IFormattingStrategy;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.widgets.Display;
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
import com.google.inject.Provider;

public class ContentFormatterFactory implements IContentFormatterFactory {

	public class ContentFormatter implements IContentFormatter {
		public void format(IDocument document, IRegion region) {
			IXtextDocument doc = (IXtextDocument) document;
			doc.modify(new FormattingUnitOfWork(doc, region));
		}

		public IFormattingStrategy getFormattingStrategy(String contentType) {
			return null;
		}
	}

	public class FormattingUnitOfWork implements IUnitOfWork<ReplaceRegion, XtextResource> {

		protected final IRegion region;

		protected final IXtextDocument doc;

		public FormattingUnitOfWork(IXtextDocument doc, IRegion region) {
			super();
			this.region = region;
			this.doc = doc;
			// Xtext issue, a dummy read only is needed before all modify operations.
			doc.readOnly(DummyReadOnly.Instance);
		}

		public ReplaceRegion exec(XtextResource state) throws Exception {

			// Do not format if there are syntax errors
			if(PPValidationUtils.hasSyntaxErrors(state)) {
				Display.getCurrent().beep();
				return null;
			}

			// TODO: Q: viewer related parameters passed to formatting context?
			// TODO: A: the document is aware of the current Preference store - this could be used instead of the
			// roundabout way with the resoureScope.
			// TODO: get the dom root
			ISerializationDiagnostic.Acceptor errors = ISerializationDiagnostic.EXCEPTION_THROWING_ACCEPTOR;
			try {
				resourceScope.enter(state);
				// EObject context = getContext(state.getContents().get(0));
				IDomNode root = getSerializer().serializeToDom(state.getContents().get(0), false);
				org.eclipse.xtext.util.ReplaceRegion r = getFormatter().format(
					root, new TextRegion(region.getOffset(), region.getLength()), //
					getFormattingContextFactory().create(state, FormattingOption.Format), errors);
				ReplaceRegion replaceRegion = new ReplaceRegion(r.getOffset(), r.getLength(), r.getText());
				try {
					if(replaceRegion != null) {

						String current = null;
						try {
							current = doc.get(replaceRegion.getOffset(), replaceRegion.getLength());
						}
						catch(BadLocationException e) {
							// ignore, current is null
						}
						// Optimize - if replacement is equal to current
						if(current == null || !current.equals(replaceRegion.getText()))
							doc.replace(replaceRegion.getOffset(), replaceRegion.getLength(), r.getText());
					}
				}
				catch(BadLocationException e) {
					throw new RuntimeException(e);
				}
				return replaceRegion;

			}
			finally {
				resourceScope.exit();
			}
		}

		protected EObject getContext(EObject semanticObject) {
			Iterator<EObject> contexts = contextFinder.findContextsByContentsAndContainer(semanticObject, null).iterator();
			if(!contexts.hasNext())
				throw new RuntimeException("No Context for " + EmfFormatter.objPath(semanticObject) + " could be found");
			return contexts.next();
		}

	}

	@Inject
	private Provider<IDomModelFormatter> formatterProvider;

	@Inject
	private Provider<IFormattingContextFactory> formattingContextProvider;

	@Inject
	private ResourceAccessScope resourceScope;

	@Inject
	protected IContextFinder contextFinder;

	@Inject
	private Provider<DomBasedSerializer> serializerProvider;

	@Inject
	IHiddenTokenHelper hiddenTokenHelper;

	public IContentFormatter createConfiguredFormatter(SourceViewerConfiguration configuration,
			ISourceViewer sourceViewer) {
		// TODO: pick up important information about viewer and pass on
		// configuration.getTabWidth(sourceViewer); // ?? Always returns 4 ?!?

		return new ContentFormatter();
	}

	protected IDomModelFormatter getFormatter() {
		// get via injector, as formatter is resource dependent
		// return injector.getInstance(IDomModelFormatter.class);
		return formatterProvider.get();
	}

	protected IFormattingContextFactory getFormattingContextFactory() {
		// get via injector, as formatting context may be resource dependent
		// return injector.getInstance(IFormattingContextFactory.class);
		return formattingContextProvider.get();
	}

	protected DomBasedSerializer getSerializer() {
		// get via injector, as formatting context as serialization uses the formatter
		// which is resource dependent
		// return injector.getInstance(DomBasedSerializer.class);
		return serializerProvider.get();
	}

}
