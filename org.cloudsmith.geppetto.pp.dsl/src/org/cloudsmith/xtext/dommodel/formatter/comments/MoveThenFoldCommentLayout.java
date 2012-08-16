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
import org.cloudsmith.xtext.dommodel.RegionMatch;
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
		RegionMatch match = intersect(dom, context);
		if(match.isInside()) {
			if(match.isContained() && !context.isWhitespacePreservation()) {
				CommentType commentType = commentConfiguration.classify(dom);
				ICommentFormatterAdvice advice = commentConfiguration.getFormatterAdvice(commentType);
				if(!advice.enabled() || CommentType.Unknown == commentType) {
					flow.appendText(dom.getText(), true);
					return true;
				}
				this.formatComment(styleSet, dom, flow, context, //
					commentConfiguration.getContainerInformation(commentType), advice);
			}
			else
				// output the part of the text that is inside the region as verbatim text
				flow.appendText(match.apply().getFirst(), true);
		}

		return true;
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
		final int available = maxWidth - current;
		final String lineSeparator = layoutContext.getLineSeparatorInformation().getLineSeparator();
		final ICommentContainerInformation pos0Context = commentContext.create(0);

		// how wide will the output be if hanging at current?
		// position on line (can be -1 if there was no node model
		final int pos = DomModelUtils.posOnLine(node, lineSeparator);
		final int indentationSize = layoutContext.getIndentationInformation().getIndentString().length();
		// final int pos_sameIndent = output.getLastUsedIndentation() * indentationSize;
		final int pos_effectiveIndent = output.getIndentation() * indentationSize;

		// set up extraction context (use 0 if there was no INode model)
		ICommentContainerInformation in = commentContext.create(Math.max(0, pos));
		CommentProcessor cpr = new CommentProcessor();
		CommentText comment = cpr.separateCommentFromContainer(node.getText(), in, lineSeparator);

		// format in position 0 to measure it
		TextFlow formatted = cpr.formatComment(comment, pos0Context, new CommentFormattingOptions(
			advice, Integer.MAX_VALUE), layoutContext);
		int w = formatted.getWidth();
		if(w <= available) {
			// yay, it will fit as a hanging comment, reformat for this position if the position is not
			// at an even indent multiple.
			int use = current - pos_effectiveIndent;
			if(use > 0) {
				// out = commentContext.create(use);
				formatted = cpr.formatComment(comment, commentContext.create(use), new CommentFormattingOptions(
					advice, Integer.MAX_VALUE), layoutContext);
			}
			output.appendText(CharSequences.trimLeft(formatted.getText())); // , true);
		}
		else {
			// Did not fit un-wrapped

			// number of wanted empty trailing lines wanted in output
			final int trailing = commentContext.isSLStyle()
					? 0
					: 1;

			// if output ends with a break, then current is at the leftmost position already, and moving it
			// is not an option. The position is also at an indent multiple, so no need to reposition the comment.
			//
			if(output.endsWithBreak() || output.isEmpty()) {

				// re-format for effective indent
				formatted = cpr.formatComment(//
					comment, //
					pos0Context, //
					new CommentFormattingOptions(advice, maxWidth - pos_effectiveIndent, trailing), layoutContext);
			}
			else {
				// re-format for the available space (hanging at current position)
				formatted = cpr.formatComment(//
					comment, //
					commentContext.create(current - pos_effectiveIndent), //
					new CommentFormattingOptions(advice, available, trailing), layoutContext);

				// if comment formatted for hanging at current does not fit the width (because it
				// has non-breakable content, or starts at an awkward position)
				// then reformat and move the comment to the effective indent.
				if(formatted.getWidth() > maxWidth - pos_effectiveIndent) {

					// ouch, not possible to format it as hanging at current position, must move it to the next line.
					output.appendBreak();

					// re-format for the effective indent position space
					formatted = cpr.formatComment(//
						comment, //
						pos0Context, //
						new CommentFormattingOptions(advice, maxWidth - pos_effectiveIndent, trailing), layoutContext);
				}
			}
			output.appendText(CharSequences.trimLeft(formatted.getText())); // , true);
		}

	}
}
