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
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.IDomModelFormatter;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentConfiguration;
import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContextFactory;
import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContextFactory.FormattingOption;
import org.cloudsmith.xtext.formatting.ILineSeparatorInformation;
import org.cloudsmith.xtext.serializer.acceptor.DomModelSequenceAdapter;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
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
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.validation.IConcreteSyntaxValidator;

import com.google.inject.Inject;

/**
 * Extends Serializer and modifies the API (ITokenStream based formatting/output not supported).
 * Use Serializer methods that does not take ITokenStream as an argument.
 * TODO: This is a temporary solution.
 * 
 */
public class DomBasedSerializer extends Serializer {

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
		ISemanticSequencer semantic = semanticSequencerProvider.get();
		ISyntacticSequencer syntactic = syntacticSequencerProvider.get();
		IHiddenTokenSequencer hidden = hiddenTokenSequencerProvider.get();
		semantic.init((ISemanticSequenceAcceptor) syntactic, errors);
		syntactic.init(context, semanticObject, (ISyntacticSequenceAcceptor) hidden, errors);
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
		// TODO: Faster to use a non synchronizing implementation such as StringBuilderWriter
		Writer writer = new StringWriter();
		try {
			serialize(obj, writer, options);
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
		return writer.toString();
	}

	@Override
	public void serialize(EObject obj, Writer writer, SaveOptions options) throws IOException {

		// FROM SUPER VERSION
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
			acceptor.getDomModel(), null /* all text */, formattingContextFactory.create(obj, formatting(options)),
			errors);
		writer.append(r.getText());
	}

	/**
	 * Don't know if this should be here - temporary solution to get the dom model
	 * 
	 * @param obj
	 * @param preserveWhitespace
	 * @return
	 */
	public IDomNode serializeToDom(EObject obj, boolean preserveWhitespace) {
		// Uses DomModelSequencer and new formatter interface
		ISerializationDiagnostic.Acceptor errors = ISerializationDiagnostic.EXCEPTION_THROWING_ACCEPTOR;
		DomModelSequenceAdapter acceptor = new DomModelSequenceAdapter(
			hiddenTokenHelper, commentConfiguration, lineSeparatorInformation, errors);
		EObject context = getContext(obj);
		serialize(obj, context, acceptor, errors);
		return acceptor.getDomModel();
	}
}
