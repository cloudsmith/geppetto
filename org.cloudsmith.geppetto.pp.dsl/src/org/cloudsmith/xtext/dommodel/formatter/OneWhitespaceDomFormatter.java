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
package org.cloudsmith.xtext.dommodel.formatter;

import org.cloudsmith.xtext.dommodel.DomModelUtils;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContext;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;

/**
 * A Dom Model Formatter that ensures a single whitespace between tokens.
 * 
 */
public class OneWhitespaceDomFormatter implements IDomModelFormatter {

	boolean hasStarted;

	boolean wsWritten;

	@Override
	public ReplaceRegion format(IDomNode dom, ITextRegion regionToFormat, IFormattingContext formattingContext) {
		return format(dom, regionToFormat, formattingContext, ISerializationDiagnostic.EXCEPTION_THROWING_ACCEPTOR);
	}

	@Override
	public ReplaceRegion format(IDomNode dom, ITextRegion regionToFormat, IFormattingContext formattingContext,
			Acceptor errors) {

		hasStarted = false;
		wsWritten = false;
		final StringBuilder builder = new StringBuilder();
		internalFormat(dom, regionToFormat, formattingContext, builder);
		final String text = builder.toString();
		if(regionToFormat == null)
			regionToFormat = new TextRegion(0, text.length());
		return new ReplaceRegion(regionToFormat, text);
	}

	protected void formatComposite(IDomNode node, ITextRegion regionToFormat, IFormattingContext formattingContext,
			StringBuilder builder) {
		for(IDomNode n : node.getChildren())
			internalFormat(n, regionToFormat, formattingContext, builder);
	}

	protected void formatLeaf(IDomNode node, ITextRegion regionToFormat, IFormattingContext formattingContext,
			StringBuilder builder) {

		if(DomModelUtils.isWhitespace(node)) {
			if(formattingContext.isWhitespacePreservation()) {
				String text = node.getText();
				if(isFormattingWanted(node, regionToFormat))
					builder.append(text);
				if(text.length() > 0)
					wsWritten = true;

			}
			return;
		}
		if(isFormattingWanted(node, regionToFormat)) {
			writeSpaceIfNecessary(node, builder);
			String text = node.getText();
			if(text.length() == 0)
				return;
			builder.append(node.getText());
		}
		hasStarted = true;
		wsWritten = false;
	}

	protected void internalFormat(IDomNode node, ITextRegion regionToFormat, IFormattingContext formattingContext,
			StringBuilder builder) {
		if(node.isLeaf())
			formatLeaf(node, regionToFormat, formattingContext, builder);
		else
			formatComposite(node, regionToFormat, formattingContext, builder);

	}

	protected boolean isFormattingWanted(IDomNode node, ITextRegion regionToFormat) {
		if(regionToFormat == null)
			return true;
		return regionToFormat.contains(node.getOffset());
	}

	protected void writeSpaceIfNecessary(IDomNode node, StringBuilder builder) {
		if(hasStarted && !wsWritten) {
			builder.append(" ");
			wsWritten = true;
		}
	}

}
