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
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.WidthStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.ITextFlow;

import com.google.inject.Inject;

/**
 * A Dom Model Formatter driven by rules in a {@link DomCSS}.
 * <p>
 * If there are no rules for spacing and line breaks in the style sheet produced by the given domProvider, default rules for "one space" and
 * "no line break" will be used. This makes this formatter function as a "one space formatter" in the default case.
 * </p>
 * 
 */
public class FlowLayout extends AbstractLayout implements ILayoutManager {

	private static final Spacing defaultSpacing = new Spacing(1);

	private static final LineBreaks defaultLineBreaks = new LineBreaks(0);

	@Inject
	private IFunctionFactory functions;

	@Override
	protected void formatComment(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context) {
		formatToken(styleSet, node, output, context);
	}

	@Override
	protected void formatToken(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context) {
		if(isFormattingWanted(node, context)) {
			// TEXT
			String text = styleSet.getStyleValue(TokenTextStyle.class, node, functions.textOfNode());

			if(context.isWhitespacePreservation()) {
				// alignment modifies spacing, just output text
				// TODO: "whitespacePreservation should really use the text from the node, not
				// after having passed a potential formatting function?
				output.appendText(text, true);
			}
			else {
				// ALIGNMENT
				// is left by default
				//
				Alignment alignment = styleSet.getStyleValue(AlignmentStyle.class, node);
				boolean defaultAlignment = alignment == null;
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
						output.appendText(text);
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
		}
	}

	@Override
	protected void formatWhitespace(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context) {

		// Verbatim or Formatting mode?
		// (If Verbatim and whitespace is implied, it should be formatted).
		if(context.isWhitespacePreservation() && !node.getStyleClassifiers().contains(NodeClassifier.IMPLIED)) {
			// Formatting should only be done on whitespace nodes that are implied.
			// all other whitespace nodes should be passed verbatim.
			String text = node.getText();

			if(isFormattingWanted(node, context))
				output.appendText(text);
		}
		else {
			Spacing spacing = styleSet.getStyleValue(SpacingStyle.class, node, defaultSpacing);
			LineBreaks lineBreaks = styleSet.getStyleValue(LineBreakStyle.class, node, defaultLineBreaks);
			String text = styleSet.getStyleValue(TokenTextStyle.class, node);

			if(isFormattingWanted(node, context)) {
				applySpacingAndLinebreaks(context, node, text, spacing, lineBreaks, output);
			}
		}
	}
}
