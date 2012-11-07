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

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.IDomNode.NodeClassifier;
import org.cloudsmith.xtext.dommodel.RegionMatch;
import org.cloudsmith.xtext.dommodel.formatter.css.Alignment;
import org.cloudsmith.xtext.dommodel.formatter.css.DomCSS;
import org.cloudsmith.xtext.dommodel.formatter.css.IFunctionFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.LineBreaks;
import org.cloudsmith.xtext.dommodel.formatter.css.Spacing;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.AlignedSeparatorIndex;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.AlignmentStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.LineBreakStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.SpacingStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.TokenTextStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.VerbatimStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.WidthStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.ITextFlow;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * <p>
 * A Dom Model Formatter driven by rules in a {@link DomCSS}.
 * </p>
 * <p>
 * If there are no rules for spacing and line breaks in the style sheet produced by the given domProvider, default rules for "one space" and
 * "no line break" will be used. This makes this formatter function as a "one space formatter" in the default case.
 * </p>
 * <p>
 * Comment formatting is performed by delegating to an instance of {@link ILayout} that is bound to the name {@value #COMMENT_LAYOUT_NAME}.
 * </p>
 */
public class FlowLayout extends AbstractLayoutManager implements ILayoutManager {

	/**
	 * The name of the wanted ILayoutManager to handle comment formatting.
	 */
	public static final String COMMENT_LAYOUT_NAME = "CommentsLayout";

	private static final Spacing defaultSpacing = new Spacing(1);

	private static final LineBreaks defaultLineBreaks = new LineBreaks(0);

	@Inject
	private IFunctionFactory functions;

	@Inject
	@Named(COMMENT_LAYOUT_NAME)
	protected ILayout commentLayout;

	@Override
	protected void formatComment(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context) {
		RegionMatch match = intersect(node, context);
		if(match.isInside()) {
			if(match.isContained() && !context.isWhitespacePreservation()) {
				Preconditions.checkState(
					commentLayout != null, "setCommentLayout(ILayout) must have been called first.");
				commentLayout.format(styleSet, node, output, context);
			}
			else
				// output the part of the text that is inside the region as verbatim text
				output.appendText(match.apply().getFirst(), true);
		}
	}

	@Override
	protected void formatToken(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context) {
		RegionMatch match = intersect(node, context);
		if(match.isInside()) {
			if(match.isContained() && !context.isWhitespacePreservation())
				formatTokenInternal(styleSet, node, output, context);
			else
				// output the part of the text that is inside the region as verbatim text
				output.appendText(match.apply().getFirst(), true);
		}

	}

	/**
	 * Formats the node without any checks for whitespace preservation or matching the contexts formatting region.
	 * 
	 * @param styleSet
	 * @param node
	 * @param output
	 * @param context
	 */
	private void formatTokenInternal(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context) {
		String text = styleSet.getStyleValue(TokenTextStyle.class, node, functions.textOfNode());

		Alignment alignment = styleSet.getStyleValue(AlignmentStyle.class, node);
		boolean verbatim = styleSet.getStyleValue(VerbatimStyle.class, node, Boolean.FALSE);
		final boolean defaultAlignment = alignment == null;
		if(defaultAlignment)
			alignment = Alignment.left;
		final int textLength = text.length();
		final Integer widthObject = styleSet.getStyleValue(WidthStyle.class, node);
		final int width = widthObject == null
				? textLength
				: widthObject.intValue();
		final int diff = width - textLength;

		switch(alignment) {
			case left:
				output.appendText(text, verbatim);
				// need to output padding separately as a 0 space gives the text flow permission to wrap which is a surprise
				// just because everything is left aligned by default.
				//
				if(diff > 0 || !defaultAlignment)
					output.appendSpaces(diff); // ok if 0 or negative = no spaces
				break;
			case right:
				// ok to output 0 space that potentially triggers wrapping
				output.appendSpaces(diff).appendText(text);
				break;
			case center:
				int leftpad = (diff) / 2;
				output.appendSpaces(leftpad).appendText(text).appendSpaces(diff - leftpad);
				break;
			case separator:
				// width must have been set as it is otherwise meaningless to try to align on
				// separator.
				if(widthObject == null) {
					output.appendText(text);
				}
				else {
					// use first non word char as default
					// right align in the given width
					int separatorIndex = styleSet.getStyleValue(
						AlignedSeparatorIndex.class, node, functions.firstNonWordChar());
					if(separatorIndex > -1)
						output.appendSpaces(width - separatorIndex).appendText(text);
					else
						output.appendSpaces(diff).appendText(text);
				}
				break;
		}
	}

	@Override
	protected void formatWhitespace(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context) {
		RegionMatch match = intersect(node, context);
		boolean wsp = context.isWhitespacePreservation();
		boolean implied = node.getStyleClassifiers().contains(NodeClassifier.IMPLIED);

		// TODO: the combination of whitespacePreservation, implied-space AND regional formatting is not
		// tested - it implies a priori knowledge of the textual representation of a serialized model without
		// any source text nodes; and will probably in practice never be requested. (A serialization pass would
		// needed to get the text to figure out where nodes are...)
		//
		if(match.isInside()) {
			if(match.isContained() && (!wsp || implied)) {
				// format if contained and formatting is wanted, or the space is implied
				Spacing spacing = styleSet.getStyleValue(SpacingStyle.class, node, defaultSpacing);
				LineBreaks lineBreaks = styleSet.getStyleValue(LineBreakStyle.class, node, defaultLineBreaks);
				String text = styleSet.getStyleValue(TokenTextStyle.class, node);
				applySpacingAndLinebreaks(context, node, text, spacing, lineBreaks, output);
			}
			else {
				output.appendText(match.apply().getFirst());
			}
		}
	}

}
