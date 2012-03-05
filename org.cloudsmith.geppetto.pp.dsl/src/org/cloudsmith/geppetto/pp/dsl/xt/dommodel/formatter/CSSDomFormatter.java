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
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.DedentStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.IndentStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.LineBreakStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.SpacingStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.TokenTextStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleSet;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.Strings;
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

	private IFunctionFactory functions;

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

			// Default spacing is one space per whitespace, no linebreak
			Select.whitespace().withStyles(//
				styles.oneSpace(), //
				styles.noLineBreak()), //

			// Except for leading whitepsace
			Select.before(Select.whitespace(), Select.node(NodeClassifier.FIRST_TOKEN)).withStyle(//
				styles.noSpace()), //
			Select.after(Select.whitespace(), Select.node(NodeClassifier.LAST_TOKEN)).withStyles(//
				styles.noSpace(), //
				styles.lineBreaks(1, 1, 2)),

			// Test rules around keyword ","
			Select.before(Select.whitespace(), Select.keyword(",")).withStyles( //
				styles.noSpace()), //
			Select.after(Select.whitespace(), Select.keyword(",")).withStyles( //
				styles.oneSpace()), //

			// Test rules inside brackets "[" and "]"
			Select.after(Select.whitespace(), Select.keyword("[")).withStyles( //
				styles.noSpace()), //
			Select.before(Select.whitespace(), Select.keyword("]")).withStyles( //
				styles.noSpace()), //

			// Test rules for java like indentation and line breaks for { }
			//
			Select.after(Select.whitespace(), Select.keyword("{")).withStyles( //
				styles.indent(), //
				styles.oneLineBreak()), //

			Select.before(Select.whitespace(), Select.keyword("}")).withStyles( //
				styles.dedent(), //
				styles.oneLineBreak()), //
			Select.after(Select.whitespace(), Select.keyword("}")).withStyles( //
				styles.oneLineBreak()) //
		);
	}

	protected void applySpacingAndLinebreaks(IFormattingContext context, String text, Spacing spacing,
			LineBreaks linebreaks, IFormStream output) {
		text = text == null
				? ""
				: text;
		final String lineSep = context.getLineSeparatorInformation().getLineSeparator();
		// if line break is wanted, it wins
		if(linebreaks.getNormal() > 0 || linebreaks.getMax() > 0) {
			// output a conforming number of line breaks
			output.lineBreaks(linebreaks.apply(Strings.countLines(text, lineSep.toCharArray())));
		}
		else {
			// remove all line breaks by replacing them with spaces
			text = text.replace(lineSep, " ");
			// output a conforming number of spaces
			output.spaces(spacing.apply(text.length()));
		}
	}

	@Override
	public ReplaceRegion format(IDomNode dom, ITextRegion regionToFormat, IFormattingContext formattingContext,
			Acceptor errors) {

		hasStarted = false;
		wsWritten = false;
		// final StringBuilder builder = new StringBuilder();
		final IFormStream output = new FormStream(formattingContext);
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

		final StyleSet styleSet = css.collectStyles(node);
		// this looks a bit odd, but protects against the pathological case where a style
		// has both indents and dedents. If both indent and dedent are 0, indentation are unchanged.
		output.changeIndentation(styleSet.getStyleValue(IndentStyle.class, node, Integer.valueOf(0)) -
				styleSet.getStyleValue(DedentStyle.class, node, Integer.valueOf(0)));

		if(DomModelUtils.isWhitespace(node)) {
			formatWhitespace(styleSet, node, regionToFormat, formattingContext, output);
			return;
		}
		if(isFormattingWanted(node, regionToFormat)) {
			String text = node.getText();
			if(text.length() == 0)
				return;
			// writeSpaceIfNecessary(node, output);
			output.text(node.getText());
		}
		hasStarted = true;
		wsWritten = false;
	}

	protected void formatWhitespace(StyleSet styleSet, IDomNode node, ITextRegion regionToFormat,
			IFormattingContext formattingContext, IFormStream output) {

		// Verbatim or Formatting mode?
		// (If Verbatim and whitespace is implied, it should be formatted).
		if(formattingContext.isWhitespacePreservation() && !node.getStyleClassifiers().contains(NodeClassifier.IMPLIED)) {
			// Formatting should only be done on whitespace nodes that are implied.
			// all other whitespace nodes should be passed verbatim.
			String text = node.getText();

			if(isFormattingWanted(node, regionToFormat))
				output.text(text);
			;
			if(text.length() > 0)
				wsWritten = true;
		}
		else {
			Spacing spacing = styleSet.getStyleValue(SpacingStyle.class, node, defaultSpacing);
			LineBreaks lineBreaks = styleSet.getStyleValue(LineBreakStyle.class, node, defaultLineBreaks);
			String text = styleSet.getStyleValue(TokenTextStyle.class, node);

			if(isFormattingWanted(node, regionToFormat)) {
				applySpacingAndLinebreaks(formattingContext, text, spacing, lineBreaks, output);
			}
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
