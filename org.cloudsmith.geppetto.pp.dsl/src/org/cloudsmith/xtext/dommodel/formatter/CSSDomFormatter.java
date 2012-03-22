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

import javax.swing.LayoutStyle;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.css.DomCSS;
import org.cloudsmith.xtext.formatting.ILineSeparatorInformation;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.cloudsmith.xtext.textflow.TextFlow;
import org.eclipse.xtext.formatting.IIndentationInformation;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * <p>
 * A Dom Model Formatter driven by rules in a {@link DomCSS}.
 * <p>
 * <p>
 * This formatter is given a style sheet in the form of a {@link DomCSS}, and a {@link DomNodeLayoutFeeder} that sequences the content of a node to
 * format to instances of {@link ILayoutManager} obtained by computing the {@link LayoutStyle} using the given style sheet.
 * </p>
 * <p>
 * The intent is that this implementation works for all formatting needs. A derived class could possibly override the
 * {@link #getTextFlow(IFormattingContext)} method if text flow should be collected in specific way.
 * </p>
 */
public class CSSDomFormatter implements IDomModelFormatter {

	private DomCSS css;

	private DomNodeLayoutFeeder layoutFeeder;

	@Inject
	public CSSDomFormatter(Provider<DomCSS> domProvider, DomNodeLayoutFeeder layoutFeeder) {
		this.css = domProvider.get();
		this.layoutFeeder = layoutFeeder;
	}

	@Override
	public ReplaceRegion format(IDomNode dom, final ITextRegion regionToFormat,
			final IFormattingContext formattingContext, final Acceptor errors) {

		ILayoutContext layoutContext = new ILayoutContext() {

			@Override
			public DomCSS getCSS() {
				return css;
			}

			@Override
			public Acceptor getErrorAcceptor() {
				return errors;
			}

			@Override
			public IIndentationInformation getIndentationInformation() {
				return formattingContext.getIndentationInformation();
			}

			@Override
			public ILineSeparatorInformation getLineSeparatorInformation() {
				return formattingContext.getLineSeparatorInformation();
			}

			@Override
			public int getPreferredMaxWidth() {
				return formattingContext.getPreferredMaxWidth();
			}

			@Override
			public ITextRegion getRegionToFormat() {
				return regionToFormat;
			}

			@Override
			public int getWrapIndentSize() {
				return formattingContext.getWrapIndentSize();
			}

			@Override
			public boolean isWhitespacePreservation() {
				return formattingContext.isWhitespacePreservation();
			}

		};
		final ITextFlow.WithText output = getTextFlow(formattingContext);

		layoutFeeder.sequence(dom, output, layoutContext);

		// internalFormat(dom, output, layoutContext);
		final String text = output.getText();
		return new ReplaceRegion(regionToFormat == null
				? new TextRegion(0, text.length())
				: regionToFormat, text);
	}

	protected ITextFlow.WithText getTextFlow(IFormattingContext context) {
		return new TextFlow(context);
	}
}
