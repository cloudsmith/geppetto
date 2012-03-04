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
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter;

import org.cloudsmith.geppetto.pp.dsl.ppformatting.FormStream;
import org.cloudsmith.geppetto.pp.dsl.ppformatting.IFormStream;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.DomModelUtils;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode.NodeClassifier;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.DomCSS;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.IFunctionFactory;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.IStyleFactory;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.LineBreaks;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.Select;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.Spacing;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.LineBreakStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.SpacingStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.TokenTextStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleSet;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;

import com.google.inject.Inject;

/**
 * A Dom Model Formatter that ensures a single whitespace between tokens.
 * 
 */
public class CSSDomFormatter implements IDomModelFormatter {
	private static final Spacing defaultSpacing = new Spacing(1);

	private static final LineBreaks defaultLineBreaks = new LineBreaks(0);

	boolean hasStarted;

	boolean wsWritten;

	IStyleFactory styles;

	IFunctionFactory functions;

	private DomCSS css;

	@Inject
	public CSSDomFormatter(IStyleFactory styles, IFunctionFactory functions) {
		this.styles = styles;
		this.functions = functions;

		// Starting with a static CSS
		css = new DomCSS();

		css.addRules( //
			// Default nodes print their text
			Select.any().withStyle(//
				styles.tokenText(functions.textOfNode())), //

			// Default spacing is one space per whitespace
			Select.whitespace().withStyle(//
				styles.oneSpace()), //

			// Except for leading whitepsace
			Select.before(Select.whitespace(), Select.node(NodeClassifier.FIRST_TOKEN)).withStyle(//
				styles.noSpace()), //
			Select.after(Select.whitespace(), Select.node(NodeClassifier.LAST_TOKEN)).withStyle(//
				styles.noSpace()),

			// Test rules around keyword ","
			Select.before(Select.whitespace(), Select.keyword(",")).withStyles(//
				styles.noSpace()), //
			Select.after(Select.whitespace(), Select.keyword(",")).withStyles(//
				styles.oneSpace()), //

			// Test rules inside brackets "[" and "]"
			Select.after(Select.whitespace(), Select.keyword("[")).withStyles(//
				styles.noSpace()), //
			Select.before(Select.whitespace(), Select.keyword("]")).withStyles(//
				styles.noSpace()) //
		);
	}

	protected int calculateSpaces(String text, Spacing spacing) {
		int length = text == null
				? 0
				: text.length();
		// TODO: DEBUG OUTPUT REMOVAL
		System.err.println("Space(" + length + ") with spacing(" + spacing.getMin() + ", " + spacing.getNormal() +
				", " + spacing.getMax() + ")");
		if(length == 0)
			return spacing.getNormal();
		if(length < spacing.getMin())
			return spacing.getMin();
		if(length > spacing.getMax())
			return spacing.getMax();
		return length;
	}

	@Override
	public ReplaceRegion format(IDomNode dom, ITextRegion regionToFormat, IFormattingContext formattingContext,
			Acceptor errors) {

		hasStarted = false;
		wsWritten = false;
		// final StringBuilder builder = new StringBuilder();
		final IFormStream output = new FormStream();
		internalFormat(dom, regionToFormat, formattingContext, output);
		final String text = output.getText();
		if(regionToFormat == null)
			regionToFormat = new TextRegion(0, text.length());
		return new ReplaceRegion(regionToFormat, text);
	}

	protected void formatComposite(IDomNode node, ITextRegion regionToFormat, IFormattingContext formattingContext,
			IFormStream output) {
		for(IDomNode n : node.getChildren())
			internalFormat(n, regionToFormat, formattingContext, output);
	}

	protected void formatLeaf(IDomNode node, ITextRegion regionToFormat, IFormattingContext formattingContext,
			IFormStream output) {

		if(DomModelUtils.isWhitespace(node)) {
			formatWhitespace(node, regionToFormat, formattingContext, output);
			return;
		}
		if(isFormattingWanted(node, regionToFormat)) {
			String text = node.getText();
			if(text.length() == 0)
				return;
			writeSpaceIfNecessary(node, output);
			output.text(node.getText());
		}
		hasStarted = true;
		wsWritten = false;
	}

	protected void formatWhitespace(IDomNode node, ITextRegion regionToFormat, IFormattingContext formattingContext,
			IFormStream output) {
		if(formattingContext.isWhitespacePreservation()) {
			String text = node.getText();

			if(isFormattingWanted(node, regionToFormat))
				output.text(text);
			if(text.length() > 0)
				wsWritten = true;
		}
		else {
			StyleSet styleSet = css.collectStyles(node);

			Spacing spacing = styleSet.getStyleValue(SpacingStyle.class, node, defaultSpacing);
			LineBreaks lineBreaks = styleSet.getStyleValue(LineBreakStyle.class, node);
			String text = styleSet.getStyleValue(TokenTextStyle.class, node);

			// TODO: deal with line break
			int count = calculateSpaces(text, spacing);
			if(isFormattingWanted(node, regionToFormat))
				output.space(count);
			// if(count > 0)
			// mark ws as written even if 0, since this is exactly what the rules dictated
			wsWritten = true;
		}
	}

	protected void internalFormat(IDomNode node, ITextRegion regionToFormat, IFormattingContext formattingContext,
			IFormStream output) {
		if(node.isLeaf())
			formatLeaf(node, regionToFormat, formattingContext, output);
		else
			formatComposite(node, regionToFormat, formattingContext, output);

	}

	protected boolean isFormattingWanted(IDomNode node, ITextRegion regionToFormat) {
		if(regionToFormat == null)
			return true;
		return regionToFormat.contains(node.getOffset());
	}

	protected void writeSpaceIfNecessary(IDomNode node, IFormStream output) {
		if(hasStarted && !wsWritten) {
			output.oneSpace();
			wsWritten = true;
		}
	}
}
