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
package org.cloudsmith.geppetto.pp.dsl.ppformatting;

import java.io.IOException;
import java.io.Writer;

import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContext;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.cloudsmith.xtext.textflow.TextFlow;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.util.ReplaceRegion;

import com.google.inject.Inject;

/**
 * An {@link ISerializer} using a handwritten formatter for PP.
 * TODO: This was experimental, and will be superseded by the new DomFormatter.
 * 
 */
public class PPSerializer implements ISerializer {

	@Inject
	PPExpressionFormatter expressionFormatter;

	@Inject
	IFormattingContext formattingContext;

	private String getString(CharSequence s) {
		if(s instanceof String)
			return (String) s;
		if(s instanceof StringBuilder)
			return ((StringBuilder) s).toString();
		return new StringBuilder(s).toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.ISerializer#serialize(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public String serialize(EObject obj) {
		return serialize(obj, SaveOptions.defaultOptions());
	}

	protected void serialize(EObject obj, ITextFlow.WithText stream, SaveOptions options) throws IOException {
		expressionFormatter.format(obj, stream);
		// if(stream instanceof TextFlow)
		// ((TextFlow) stream).flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.ISerializer#serialize(org.eclipse.emf.ecore.EObject, org.eclipse.xtext.resource.SaveOptions)
	 */
	@Override
	public String serialize(EObject obj, SaveOptions options) {
		ITextFlow.WithText tokenStringBuffer = new TextFlow(formattingContext);
		try {
			serialize(obj, tokenStringBuffer, options);
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
		return getString(tokenStringBuffer.getText());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.ISerializer#serialize(org.eclipse.emf.ecore.EObject, java.io.Writer, org.eclipse.xtext.resource.SaveOptions)
	 */
	@Override
	public void serialize(EObject obj, Writer writer, SaveOptions options) throws IOException {
		serialize(obj, new TextFlow(writer, formattingContext), options);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.serializer.ISerializer#serializeReplacement(org.eclipse.emf.ecore.EObject, org.eclipse.xtext.resource.SaveOptions)
	 */
	@Override
	public ReplaceRegion serializeReplacement(EObject obj, SaveOptions options) {
		ICompositeNode node = NodeModelUtils.findActualNodeFor(obj);
		String text = serialize(obj);
		return new ReplaceRegion(node.getTotalOffset(), node.getTotalLength(), text);
	}

}
