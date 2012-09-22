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
package org.cloudsmith.xtext.serializer;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cloudsmith.xtext.dommodel.DomModelUtils;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.IDomModelFormatter;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentConfiguration;
import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContextFactory;
import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContextFactory.FormattingOption;
import org.cloudsmith.xtext.resource.ResourceAccessScope;
import org.cloudsmith.xtext.serializer.acceptor.DomModelSequenceAdapter;
import org.cloudsmith.xtext.serializer.acceptor.IHiddenTokenSequencer2;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.formatting.ILineSeparatorInformation;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parsetree.reconstr.ICommentAssociater;
import org.eclipse.xtext.parsetree.reconstr.IHiddenTokenHelper;
import org.eclipse.xtext.parsetree.reconstr.ITokenStream;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.ISequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.ISyntacticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.TokenStreamSequenceAdapter;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic;
import org.eclipse.xtext.serializer.impl.Serializer;
import org.eclipse.xtext.serializer.sequencer.IHiddenTokenSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ISyntacticSequencer;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;
import org.eclipse.xtext.validation.IConcreteSyntaxValidator;

import com.google.inject.Inject;

/**
 * Extends Serializer and modifies the API (ITokenStream based formatting/output not supported).
 * Use Serializer methods that does not take ITokenStream as an argument.
 * TODO: This is a temporary solution.
 * 
 */
public class DomBasedSerializer extends Serializer {

	protected static class StringBuilderWriter extends Writer {
		private final StringBuilder builder;

		public StringBuilderWriter() {
			builder = new StringBuilder();
		}

		public StringBuilderWriter(int initialCapacity) {
			builder = new StringBuilder(initialCapacity);
		}

		public StringBuilderWriter(StringBuilder appendToBuilder) {
			builder = appendToBuilder;
		}

		@Override
		public Writer append(char value) {
			builder.append(value);
			return this;
		}

		@Override
		public Writer append(CharSequence value) {
			builder.append(value);
			return this;
		}

		@Override
		public Writer append(CharSequence value, int start, int end) {
			builder.append(value, start, end);
			return this;
		}

		@Override
		public void close() throws IOException {
			// does nothing
		}

		@Override
		public void flush() throws IOException {
			// does nothing
		}

		StringBuilder getBuilder() {
			return builder;
		}

		@Override
		public String toString() {
			return builder.toString();
		}

		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {
			builder.append(cbuf, off, len);
		}

		@Override
		public void write(String value) {
			builder.append(value);
		}
	}

	@Inject
	IDomModelFormatter domFormatter;

	@Inject
	IFormattingContextFactory formattingContextFactory;

	@Inject
	IHiddenTokenHelper hiddenTokenHelper;

	@Inject
	ILineSeparatorInformation lineSeparatorInformation;

	@Inject
	ICommentConfiguration<?> commentConfiguration;

	@Inject
	private ResourceAccessScope resourceScope;

	@Inject
	ICommentAssociater commentAssociater;

	@Inject
	CommentAssociator ppCommentAssociator;

	private FormattingOption formatting(SaveOptions options) {
		return options.isFormatting()
				? FormattingOption.Format
				: FormattingOption.PreserveWhitespace;
	}

	/**
	 * NOTE: This overridden method is required to initialize the DomModelSequences.
	 * The base implementation checks if the tokens parameter is a specific adapter, and
	 * then initializes it. This method does the same but for DomModelSequenceAdapter.
	 * 
	 */
	@Override
	protected void serialize(EObject semanticObject, EObject context, ISequenceAcceptor tokens,
			ISerializationDiagnostic.Acceptor errors) {
		serialize(semanticObject, context, tokens, errors, null);
	}

	protected void serialize(EObject semanticObject, EObject context, ISequenceAcceptor tokens,
			ISerializationDiagnostic.Acceptor errors, ICommentReconcilement commentReconciliator) {
		ISemanticSequencer semantic = semanticSequencerProvider.get();
		ISyntacticSequencer syntactic = syntacticSequencerProvider.get();
		IHiddenTokenSequencer hidden = hiddenTokenSequencerProvider.get();
		semantic.init((ISemanticSequenceAcceptor) syntactic, errors);
		syntactic.init(context, semanticObject, (ISyntacticSequenceAcceptor) hidden, errors);
		if(hidden instanceof IHiddenTokenSequencer2)
			((IHiddenTokenSequencer2) hidden).init(context, semanticObject, tokens, errors, commentReconciliator);
		else
			hidden.init(context, semanticObject, tokens, errors);

		// NOTE: This is not so nice
		if(tokens instanceof TokenStreamSequenceAdapter)
			((TokenStreamSequenceAdapter) tokens).init(context);
		else if(tokens instanceof DomModelSequenceAdapter)
			((DomModelSequenceAdapter) tokens).init(context, semanticObject);

		semantic.createSequence(context, semanticObject);
	}

	/**
	 * @throws UnsupportedOperationException
	 */
	@Override
	protected void serialize(EObject obj, ITokenStream tokenStream, SaveOptions options) throws IOException {
		throw new UnsupportedOperationException("Use new API");
	}

	@Override
	public String serialize(EObject obj, SaveOptions options) {
		return serialize(obj, options, null);
	}

	public String serialize(EObject obj, SaveOptions options, ITextRegion regionToFormat) {
		// TODO: Faster to use a non synchronizing implementation such as StringBuilderWriter from apache commons io
		Writer writer = new StringBuilderWriter();
		try {
			serialize(obj, writer, options, regionToFormat);
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
		return writer.toString();
	}

	@Override
	public void serialize(EObject obj, Writer writer, SaveOptions options) throws IOException {
		serialize(obj, writer, options, null /* all text */);
	}

	public void serialize(EObject obj, Writer writer, SaveOptions options, ITextRegion regionToFormat)
			throws IOException {

		// FROM SUPER VERSION (without the ITextRegion)
		// use the CSV as long as there are cases where is provides better messages than the serializer itself.
		if(options.isValidating()) {
			List<Diagnostic> diagnostics = new ArrayList<Diagnostic>();
			validator.validateRecursive(
				obj, new IConcreteSyntaxValidator.DiagnosticListAcceptor(diagnostics), new HashMap<Object, Object>());
			if(!diagnostics.isEmpty())
				throw new IConcreteSyntaxValidator.InvalidConcreteSyntaxException(
					"These errors need to be fixed before the model can be serialized.", diagnostics);
		}
		// END FROM SUPER VERSION

		// Uses DomModelSequencer and new formatter interface
		ISerializationDiagnostic.Acceptor errors = ISerializationDiagnostic.EXCEPTION_THROWING_ACCEPTOR;
		DomModelSequenceAdapter acceptor = new DomModelSequenceAdapter(
			hiddenTokenHelper, commentConfiguration, lineSeparatorInformation, errors);
		EObject context = getContext(obj);
		serialize(obj, context, acceptor, errors);
		ReplaceRegion r = domFormatter.format(
			acceptor.getDomModel(), regionToFormat, formattingContextFactory.create(obj, formatting(options)), errors);
		writer.append(r.getText());
	}

	@Override
	public ReplaceRegion serializeReplacement(EObject obj, SaveOptions options) {
		ICompositeNode node = NodeModelUtils.findActualNodeFor(obj);

		if(node == null) {
			throw new IllegalStateException("Cannot replace an obj that has no associated node");
		}

		ICommentReconcilement commentReconciliator = ppCommentAssociator.associateComments(node);

		boolean alreadyEnteredResourceScope = false;
		try {
			if(resourceScope.get().getResourceURI() == null) {
				alreadyEnteredResourceScope = true;
				resourceScope.enter(obj.eResource());
			}
			// Serialize everything to DOM
			IDomNode root = serializeToDom(obj.eResource().getContents().get(0), true, commentReconciliator);

			// Find the node for the modified object, we need to format the region it occupies.
			IDomNode replacementNode = DomModelUtils.findNodeForSemanticObject(root, obj);
			TextRegion regionToFormat = new TextRegion(replacementNode.getOffset(), replacementNode.getLength());

			// Override temporarily to have formatting turned on
			// GAH - this is just ridiculous internal DSL junk to set a single boolean
			options = SaveOptions.newBuilder().format().getOptions();
			ReplaceRegion r = domFormatter.format(
				root, regionToFormat,
				formattingContextFactory.create(obj.eResource().getContents().get(0), formatting(options)));
			// String text = serialize(obj.eContainer(), options, new TextRegion(node.getOffset(), node.getLength()));
			return new ReplaceRegion(node.getTotalOffset(), node.getTotalLength(), r.getText());
		}
		finally {
			if(alreadyEnteredResourceScope)
				resourceScope.exit();
		}
	}

	/**
	 * Serialize and return the resulting DOM. This is the same as calling {@link #serializeToDom(EObject, boolean, ICommentReconcilement)} with a
	 * null ICommentReconciliator.
	 * 
	 * @param obj
	 * @param preserveWhitespace
	 * @return
	 */
	public IDomNode serializeToDom(EObject obj, boolean preserveWhitespace) {
		return serializeToDom(obj, preserveWhitespace, null);
	}

	/**
	 * Serialize and return the resulting DOM.
	 * 
	 * @param obj
	 * @param preserveWhitespace
	 * @return
	 */
	public IDomNode serializeToDom(EObject obj, boolean preserveWhitespace, ICommentReconcilement commentReconciliator) {
		// Uses DomModelSequencer and new formatter interface
		ISerializationDiagnostic.Acceptor errors = ISerializationDiagnostic.EXCEPTION_THROWING_ACCEPTOR;
		DomModelSequenceAdapter acceptor = new DomModelSequenceAdapter(
			hiddenTokenHelper, commentConfiguration, lineSeparatorInformation, errors);
		EObject context = getContext(obj);
		serialize(obj, context, acceptor, errors, commentReconciliator);
		return acceptor.getDomModel();
	}
}
