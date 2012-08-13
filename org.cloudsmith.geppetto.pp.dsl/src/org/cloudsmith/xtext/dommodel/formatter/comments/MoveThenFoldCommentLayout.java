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
package org.cloudsmith.xtext.dommodel.formatter.comments;

import org.cloudsmith.xtext.dommodel.DomModelUtils;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.AbstractLayout;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.comments.CommentProcessor.CommentFormattingOptions;
import org.cloudsmith.xtext.dommodel.formatter.comments.CommentProcessor.CommentText;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentConfiguration.CommentType;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.CharSequences;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.cloudsmith.xtext.textflow.TextFlow;

import com.google.inject.Inject;

/**
 * <p>
 * A Move-then-Fold Comment Formatting Strategy.
 * </p>
 * <p>
 * A comment that fits at the current position (after internal formatting by the {@link CommentProcessor} is appended to the given {@link ITextFlow}
 * output at its original position. If the comment does not fit, it is moved to the next line. If, on the next line, it fits at the effective indent
 * (the indent of future output) the effective indent will be used, else if the comment fits at the same indent as its original line, this position is
 * used. Finally, if the comment does not fit after having been moved, it is folded to fit and it is positioned at the effective indent.
 * </p>
 * 
 * <p>
 * Folding only takes place if there is a space to the left of the preferred max width position.
 * </p>
 * <p>
 * Comment formatting is performed in accordance with the configuration's {@link ICommentConfiguration}, and it is expected that the implementation is
 * {@code ICommentConfiguration<{@link CommentType}>}. If not, an error message is logged, and comments are output without any formatting.
 * </p>
 */
public class MoveThenFoldCommentLayout extends AbstractLayout {

	@Inject
	protected ICommentConfiguration<CommentType> commentConfiguration;

	@Override
	public boolean format(IDomNode dom, ITextFlow flow, ILayoutContext context) {
		// ignores style set
		return format(null, dom, flow, context);
	}

	@Override
	public boolean format(StyleSet styleSet, IDomNode dom, ITextFlow flow, ILayoutContext context) {
		if(!isFormattingWanted(dom, context))
			return true; // don't know why I was called really...

		// format or not?
		if(context.isWhitespacePreservation()) {
			flow.appendText(dom.getText(), true);
			return true;
		}

		CommentType commentType = commentConfiguration.classify(dom);
		ICommentFormatterAdvice advice = commentConfiguration.getFormatterAdvice(commentType);
		if(!advice.enabled() || CommentType.Unknown == commentType) {
			flow.appendText(dom.getText(), true);
			return true;
		}
		this.formatComment(styleSet, dom, flow, context, //
			commentConfiguration.getContainerInformation(commentType), advice);

		return true;
	}

	protected void formatComment(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext context) {
		if(!isFormattingWanted(node, context))
			return;

		// format or not?
		if(context.isWhitespacePreservation()) {
			output.appendText(node.getText(), true);
			return;
		}

		CommentType commentType = commentConfiguration.classify(node);
		ICommentFormatterAdvice advice = commentConfiguration.getFormatterAdvice(commentType);
		if(!advice.enabled() || CommentType.Unknown == commentType) {
			output.appendText(node.getText(), true);
			return;
		}
		this.formatComment(styleSet, node, output, context, //
			commentConfiguration.getContainerInformation(commentType), advice);
	}

	/**
	 * Formats the given comment passed in {@code node} and appends the result to the given {@code output}.
	 * 
	 * @param styleSet
	 *            the effective styles for the comment as determined by the CSS
	 * @param node
	 *            the comment node to format
	 * @param output
	 *            the measuring text flow where output should be appended
	 * @param layoutContext
	 *            the overall layout context for the formatting operation
	 * @param commentContainer
	 *            description of the container of the comment text
	 * @param advice
	 */
	protected void formatComment(StyleSet styleSet, IDomNode node, ITextFlow output, ILayoutContext layoutContext,
			ICommentContainerInformation commentContext, ICommentFormatterAdvice advice) {
		// How much space is left?
		int maxWidth = output.getPreferredMaxWidth();
		int current = output.getAppendLinePosition();
		int available = maxWidth - current;
		final String lineSeparator = layoutContext.getLineSeparatorInformation().getLineSeparator();
		final ICommentContainerInformation pos0Context = commentContext.create(0);

		// how wide will the output be if hanging at current?
		// position on line (can be -1 if there was no node model
		int pos = DomModelUtils.posOnLine(node, lineSeparator);
		// set up extraction context (use 0 if there was no INode model)
		ICommentContainerInformation in = commentContext.create(Math.max(0, pos));
		CommentProcessor cpr = new CommentProcessor();
		CommentText comment = cpr.separateCommentFromContainer(node.getText(), in, lineSeparator);

		// format in position 0 to measure it
		ICommentContainerInformation out = pos0Context;
		TextFlow formatted = cpr.formatComment(
			comment, out, new CommentFormattingOptions(advice, Integer.MAX_VALUE), layoutContext);
		int w = formatted.getWidth();
		if(w <= available) {
			// yay, it will fit as a hanging comment, reformat for this position.
			out = commentContext.create(current);
			formatted = cpr.formatComment(
				comment, out, new CommentFormattingOptions(advice, Integer.MAX_VALUE), layoutContext);
			output.appendText(formatted.getText()); // , true);
		}
		else {
			// Did not fit, move to new line if not first on line.

			int use = current;
			// if output ends with a break, then current is at the leftmost position already
			if(!(output.endsWithBreak() || output.isEmpty())) {
				// if comment fits with effective indent, use that
				// otherwise, if comment fits with same indent, use that
				// otherwise, reformat for effective indent
				//
				final int indentationSize = layoutContext.getIndentationInformation().getIndentString().length();
				int pos_sameIndent = output.getLastUsedIndentation() * indentationSize;
				int pos_effectiveIndent = output.getIndentation() * indentationSize;
				use = pos_effectiveIndent;
				if(!(use + w <= available) && pos_sameIndent + w <= available)
					use = pos_sameIndent;

				// break and manually indent first line
				output.appendText(lineSeparator); // , true);
				output.appendText(CharSequences.spaces(use)); // , true);
			}
			out = commentContext.create(use);
			// format (will wrap if required)
			// Enforce a min trailing empty line of 1 if this is a ML comment that is not on the same line,
			// but use 0 for SL comments (rule may be applied after split).
			formatted = cpr.formatComment(comment, out, new CommentFormattingOptions(
				advice, maxWidth - use, commentContext.isSLStyle()
						? 0
						: 1), layoutContext);
			output.appendText(formatted.getText()); // , true);
		}

	}
}
