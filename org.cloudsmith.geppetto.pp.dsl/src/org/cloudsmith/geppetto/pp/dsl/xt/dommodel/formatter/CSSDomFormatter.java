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

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.DomModelUtils;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode.NodeClassifier;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.DomCSS;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.IFunctionFactory;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.LineBreaks;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.Spacing;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.DedentStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.IndentStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.LineBreakStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.SpacingStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.TokenTextStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleSet;
import org.cloudsmith.geppetto.pp.dsl.xt.formatter.ITextFlow;
import org.cloudsmith.geppetto.pp.dsl.xt.formatter.TextFlow;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.util.TextRegion;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * A Dom Model Formatter driven by rules in a {@link DomCSS}.
 * <p>
 * If there are no rules for spacing and line breaks in the style sheet produced by the given domProvider, default rules for "one space" and
 * "no line break" will be used. This makes this formatter function as a "one space formatter" in the default case.
 * </p>
 * 
 */
public class CSSDomFormatter implements IDomModelFormatter {
	// IMPORTANT: Must be a unique instance of empty string - do not change to = ""
	public static final String IMPLIED_EMPTY_WHITESPACE = new String("");

	private static final Spacing defaultSpacing = new Spacing(1);

	private static final LineBreaks defaultLineBreaks = new LineBreaks(0);

	private DomCSS css;

	private final static Integer DEFAULT_0 = Integer.valueOf(0);

	@Inject
	IFunctionFactory functions;

	@Inject
	public CSSDomFormatter(Provider<DomCSS> domProvider) {
		css = domProvider.get();
	}

	/**
	 * Outputs the result of applying the given {@link Spacing} and {@link LineBreaks} specifications to the given
	 * text to the given output {@link ITextFlow}.
	 * <p>
	 * Called when it has been decided that a whitespace should be processed (it is included in the region to format).
	 * </p>
	 * <p>
	 * If the given {@link LineBreaks} has a <i>normal</i> {@link LineBreaks#getNormal()} or <i>max</i> {@link LineBreaks#getMax()} greater than 0 the
	 * line break specification wins, and no spaces are produced.
	 * </p>
	 * <p>
	 * A missing quantity will produce the <i>normal</i> quantity, a quantity less than <i>min</i> will produce a <i>min</i> quantity, and a quantity
	 * greater than <i>max</i> will produce a <i>max</i> quantity.
	 * </p>
	 * 
	 * @param context
	 *            - provides line separator information
	 * @param text
	 *            - the text applied to spacing and line break specifications
	 * @param spacing
	 *            - the spacing specification
	 * @param linebreaks
	 *            - the line break specification
	 * @param output
	 *            - where output is produced
	 */
	protected void applySpacingAndLinebreaks(IFormattingContext context, String text, Spacing spacing,
			LineBreaks linebreaks, ITextFlow output) {
		text = text == null
				? ""
				: text;
		final String lineSep = context.getLineSeparatorInformation().getLineSeparator();
		// if line break is wanted, it wins
		if(linebreaks.getNormal() > 0 || linebreaks.getMax() > 0) {
			// output a conforming number of line breaks
			output.appendBreaks(linebreaks.apply(Strings.countLines(text, lineSep.toCharArray())));
		}
		else {
			// remove all line breaks by replacing them with spaces
			text = text.replace(lineSep, " ");
			// output a conforming number of spaces
			output.appendSpaces(spacing.apply(text.length()));
		}
	}

	@Override
	public ReplaceRegion format(IDomNode dom, ITextRegion regionToFormat, IFormattingContext formattingContext,
			Acceptor errors) {

		final ITextFlow.WithText output = new TextFlow(formattingContext);
		internalFormat(dom, regionToFormat, formattingContext, output);
		final String text = output.getText();
		if(regionToFormat == null)
			regionToFormat = new TextRegion(0, text.length());
		return new ReplaceRegion(regionToFormat, text);
	}

	protected void formatComposite(IDomNode node, ITextRegion regionToFormat, IFormattingContext formattingContext,
			ITextFlow output) {
		for(IDomNode n : node.getChildren())
			internalFormat(n, regionToFormat, formattingContext, output);
	}

	protected void formatLeaf(IDomNode node, ITextRegion regionToFormat, IFormattingContext formattingContext,
			ITextFlow output) {

		final StyleSet styleSet = css.collectStyles(node);

		// Process indentation for all types of leafs.
		// This looks a bit odd, but protects against the pathological case where a style
		// has both indents and dedents. If both indent and dedent are 0, indentation is unchanged.
		output.changeIndentation(styleSet.getStyleValue(IndentStyle.class, node, DEFAULT_0) -
				styleSet.getStyleValue(DedentStyle.class, node, DEFAULT_0));

		if(DomModelUtils.isWhitespace(node)) {
			formatWhitespace(styleSet, node, regionToFormat, formattingContext, output);
			return;
		}
		if(isFormattingWanted(node, regionToFormat)) {
			styleSet.getStyleValue(TokenTextStyle.class, node, functions.textOfNode());
			String text = node.getText();
			if(text.length() > 0)
				output.appendText(text);
		}
	}

	protected void formatWhitespace(StyleSet styleSet, IDomNode node, ITextRegion regionToFormat,
			IFormattingContext formattingContext, ITextFlow output) {

		// Verbatim or Formatting mode?
		// (If Verbatim and whitespace is implied, it should be formatted).
		if(formattingContext.isWhitespacePreservation() && !node.getStyleClassifiers().contains(NodeClassifier.IMPLIED)) {
			// Formatting should only be done on whitespace nodes that are implied.
			// all other whitespace nodes should be passed verbatim.
			String text = node.getText();

			if(isFormattingWanted(node, regionToFormat))
				output.appendText(text);
		}
		else {
			Spacing spacing = styleSet.getStyleValue(SpacingStyle.class, node, defaultSpacing);
			LineBreaks lineBreaks = styleSet.getStyleValue(LineBreakStyle.class, node, defaultLineBreaks);
			String text = styleSet.getStyleValue(TokenTextStyle.class, node);

			if(isFormattingWanted(node, regionToFormat)) {
				applySpacingAndLinebreaks(formattingContext, text, spacing, lineBreaks, output);
			}
		}
	}

	protected void internalFormat(IDomNode node, ITextRegion regionToFormat, IFormattingContext formattingContext,
			ITextFlow output) {
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
}
