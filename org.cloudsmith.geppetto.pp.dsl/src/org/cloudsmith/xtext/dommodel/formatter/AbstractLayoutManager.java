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
import org.cloudsmith.xtext.dommodel.IDomNode.NodeClassifier;
import org.cloudsmith.xtext.dommodel.IDomNode.NodeType;
import org.cloudsmith.xtext.dommodel.formatter.css.LineBreaks;
import org.cloudsmith.xtext.dommodel.formatter.css.Spacing;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.DedentStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.IndentStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleFactory.LayoutManagerStyle;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.CharSequences;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.eclipse.xtext.util.Strings;

/**
 * Abstract implementation of ILayoutManaager
 * 
 */
public abstract class AbstractLayoutManager extends AbstractLayout implements ILayoutManager {

	private final static Integer DEFAULT_0 = Integer.valueOf(0);

	/**
	 * Reverses any indentation set before the composite.
	 */
	@Override
	public void afterComposite(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context) {
		// Process indentation for all types of composites.
		// This looks a bit odd, but protects against the pathological case where a style
		// has both indents and dedents. If both indent and dedent are 0, indentation is unchanged.
		output.changeIndentation(styleSet.getStyleValue(DedentStyle.class, node, DEFAULT_0) -
				styleSet.getStyleValue(IndentStyle.class, node, DEFAULT_0));
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
	protected void applySpacingAndLinebreaks(ILayoutContext context, IDomNode node, String text, Spacing spacing,
			LineBreaks linebreaks, ITextFlow output) {
		text = text == null
				? ""
				: text;
		final String lineSep = context.getLineSeparatorInformation().getLineSeparator();
		final int existingLinebreaks = Strings.countLines(text, lineSep.toCharArray());
		// if line break is wanted, it wins
		if(linebreaks.getNormal() > 0 || (linebreaks.getMax() > 0 && existingLinebreaks > 0)) {
			// output a conforming number of line breaks
			// possibly accept a comment as a linebreak if this whitespace did not already contain
			// a break
			int emittedLinebreaks = 0;
			if(existingLinebreaks == 0 && linebreaks.isCommentEndingWithBreakAcceptable()) {
				IDomNode n = DomModelUtils.nextLeaf(node);
				if(n != null && n.getNodeType() == NodeType.COMMENT &&
						n.getStyleClassifiers().contains(NodeClassifier.LINESEPARATOR_TERMINATED)) {
					// comment breaks the line
					output.appendSpaces(1); // separate the comment with a space

					// format the comment with layout manager given by rules (or this layout by default)
					final StyleSet styleSet = context.getCSS().collectStyles(n);
					tracer.recordEffectiveStyle(node, styleSet);

					final ILayoutManager layout = styleSet.getStyleValue(LayoutManagerStyle.class, n, this);
					layout.format(styleSet, n, output, context);
					// do not process this comment again
					context.markConsumed(n);

					// comment counts as one linebreak
					emittedLinebreaks++;
				}
			}
			if(linebreaks.isExistingAcceptable())
				output.ensureBreaks(linebreaks.apply(existingLinebreaks));
			else
				output.appendBreaks(linebreaks.apply(existingLinebreaks) - emittedLinebreaks);
		}
		else {
			// remove all line breaks by replacing them with spaces
			text = text.replace(lineSep, " ");
			// output a conforming number of spaces, and make it nonBreakableSpace if required.
			if(spacing.isBreakable())
				output.appendSpaces(spacing.apply(text.length()));
			else
				output.appendText(CharSequences.spaces(spacing.apply(text.length())));
		}
	}

	/**
	 * This implementation applies indentation set in a composite.
	 */
	@Override
	public void beforeComposite(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context) {
		// Process indentation for all types of composites.
		// This looks a bit odd, but protects against the pathological case where a style
		// has both indents and dedents. If both indent and dedent are 0, indentation is unchanged.
		output.changeIndentation(styleSet.getStyleValue(IndentStyle.class, node, DEFAULT_0) -
				styleSet.getStyleValue(DedentStyle.class, node, DEFAULT_0));
	}

	@Override
	public boolean format(IDomNode node, ITextFlow flow, ILayoutContext context) {
		StyleSet styleSet = context.getCSS().collectStyles(node);
		tracer.recordEffectiveStyle(node, styleSet);

		return format(styleSet, node, flow, context);
	}

	@Override
	public boolean format(StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		// if already consumed by previous formatting - skip it and report as formatted
		if(context.isConsumed(node))
			return true;
		if(!node.isLeaf())
			return formatComposite(styleSet, node, flow, context);
		formatLeaf(styleSet, node, flow, context);
		return true; // it was a leaf, there is nothing to prune
	}

	/**
	 * Derived class should implement this method to format a leaf containing a comment.
	 * 
	 * @param styleSet
	 * @param node
	 * @param output
	 * @param context
	 */
	protected abstract void formatComment(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context);

	/**
	 * <p>
	 * This implementation does nothing, and returns <code>false</code>.
	 * </p>
	 * <p>
	 * A derived class may want to decorate children of the composite node and return <code>false</code>, or process and provide all output for
	 * children and return <code>true</code>
	 * </p>
	 * 
	 * @param styleSet
	 * @param node
	 * @param flow
	 * @param context
	 * @return false - children should be visited
	 */
	protected boolean formatComposite(StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		return false; // Did not process children
	}

	/**
	 * Processes indentation styling, and calls one of the specific methods:
	 * <ul>
	 * <li>{@link #formatToken(StyleSet, IDomNode, ITextFlow, ILayoutContext)}</li>
	 * <li>{@link #formatComment(StyleSet, IDomNode, ITextFlow, ILayoutContext)}</li>
	 * <li>{@link #formatWhitespace(StyleSet, IDomNode, ITextFlow, ILayoutContext)}</li>
	 * </ul>
	 * These should be implemented by a subclass.
	 * 
	 * @param styleSet
	 * @param node
	 * @param output
	 * @param context
	 */
	protected void formatLeaf(final StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context) {

		// Process indentation for all types of leafs.
		// This looks a bit odd, but protects against the pathological case where a style
		// has both indents and dedents. If both indent and dedent are 0, indentation is unchanged.
		output.changeIndentation(styleSet.getStyleValue(IndentStyle.class, node, DEFAULT_0) -
				styleSet.getStyleValue(DedentStyle.class, node, DEFAULT_0));

		switch(node.getNodeType()) {
			case WHITESPACE:
				formatWhitespace(styleSet, node, output, context);
				break;
			case COMMENT:
				formatComment(styleSet, node, output, context);
				break;
			default:
				formatToken(styleSet, node, output, context);
		}
	}

	/**
	 * Derived class should implement this method and format a token (a leaf that is not whitespace, and not comment).
	 * 
	 * @param styleSet
	 * @param node
	 * @param output
	 * @param context
	 */
	protected abstract void formatToken(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context);

	/**
	 * Derived class should implement this method and format whitespace.
	 * 
	 * @param styleSet
	 * @param node
	 * @param output
	 * @param context
	 */
	protected abstract void formatWhitespace(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context);

}
