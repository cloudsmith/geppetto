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
package org.cloudsmith.geppetto.pp.dsl.xt.serializer;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.IDomModelFormatter;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.IFormattingContext.FormattingContextProvider;
import org.cloudsmith.geppetto.pp.dsl.xt.serializer.acceptor.DomModelSequenceAdapter;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parsetree.reconstr.ITokenStream;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic;
import org.eclipse.xtext.serializer.impl.Serializer;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.validation.IConcreteSyntaxValidator;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class DomBasedSerializer extends Serializer {

	@Inject
	IDomModelFormatter domFormatter;

	@Inject
	FormattingContextProvider formattingContextProvider;

	@Override
	protected void serialize(EObject obj, ITokenStream tokenStream, SaveOptions options) throws IOException {
		throw new UnsupportedOperationException("Use new API");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.impl.Serializer#serialize(org.eclipse.emf.ecore.EObject, org.eclipse.xtext.resource.SaveOptions)
	 */
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

		// use the CSV as long as there are cases where is provides better messages than the serializer itself.
		if(options.isValidating()) {
			List<Diagnostic> diagnostics = new ArrayList<Diagnostic>();
			validator.validateRecursive(
				obj, new IConcreteSyntaxValidator.DiagnosticListAcceptor(diagnostics), new HashMap<Object, Object>());
			if(!diagnostics.isEmpty())
				throw new IConcreteSyntaxValidator.InvalidConcreteSyntaxException(
					"These errors need to be fixed before the model can be serialized.", diagnostics);
		}

		ISerializationDiagnostic.Acceptor errors = ISerializationDiagnostic.EXCEPTION_THROWING_ACCEPTOR;
		DomModelSequenceAdapter acceptor = new DomModelSequenceAdapter(errors);
		EObject context = getContext(obj);
		serialize(obj, context, acceptor, errors);
		ReplaceRegion r = domFormatter.format(
			acceptor.getDomModel(), null /* all text */, formattingContextProvider.get(!options.isFormatting()), errors);
		writer.append(r.getText());
	}
}
