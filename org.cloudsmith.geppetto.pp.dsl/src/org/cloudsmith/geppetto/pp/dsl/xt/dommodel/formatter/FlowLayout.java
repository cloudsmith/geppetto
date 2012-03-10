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

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode.NodeClassifier;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.DomCSS;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.IFunctionFactory;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.LineBreaks;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.Spacing;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.LineBreakStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.SpacingStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleFactory.TokenTextStyle;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.StyleSet;
import org.cloudsmith.geppetto.pp.dsl.xt.textflow.ITextFlow;

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
			styleSet.getStyleValue(TokenTextStyle.class, node, functions.textOfNode());
			String text = node.getText();
			if(text.length() > 0)
				output.appendText(text);
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
				applySpacingAndLinebreaks(context, text, spacing, lineBreaks, output);
			}
		}
	}
}
