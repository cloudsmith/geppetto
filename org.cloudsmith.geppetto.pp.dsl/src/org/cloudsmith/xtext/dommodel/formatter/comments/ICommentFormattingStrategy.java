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
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import org.cloudsmith.xtext.dommodel.formatter.comments.CommentProcessor.CommentFormattingOptions;
import org.cloudsmith.xtext.dommodel.formatter.comments.CommentProcessor.CommentText;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.CharSequences;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.cloudsmith.xtext.textflow.TextFlow;

/**
 * Interface for comment formatting.
 * 
 */
public interface ICommentFormattingStrategy {
	/**
	 * <p>
	 * A Move-then-Fold Comment Formatting Strategy.
	 * </p>
	 * <p>
	 * A comment that fit at the current position (after internal formatting by the {@link CommentProcessor} is appended to the given output
	 * {@link ITextFlow} at its original position. If the comment does not fit, it is moved to the next line. If, on the next line, it fits at the
	 * effective indent (the indent of future output) the effective indent will be used, else if the comment fits at the same indent as its original
	 * line, this position is used. Finally, if the comment does not fit after having been moved, it is folded to fit and it is positioned at the
	 * effective indent.
	 * </p>
	 * 
	 * <p>
	 * Folding only takes place if there is a space to the left of the preferred max width position.
	 * </p>
	 * 
	 */
	public static class MoveThenFold implements ICommentFormattingStrategy {
		@Override
		public void format(StyleSet styleSet, IDomNode node, ITextFlow.Measuring output, ILayoutContext layoutContext,
				ICommentContext commentContext) {
			// How much space is left?
			int maxWidth = output.getPreferredMaxWidth();
			int current = output.getAppendLinePosition();
			int available = maxWidth - current;
			final String lineSeparator = layoutContext.getLineSeparatorInformation().getLineSeparator();
			final ICommentContext pos0Context = commentContext.getContextForPosition(0);

			// how wide will the output be if hanging at current?
			// position on line (can be -1 if there was no node model
			int pos = DomModelUtils.posOnLine(node, lineSeparator);
			// set up extraction context (use 0 if there was no INode model)
			ICommentContext in = commentContext.getContextForPosition(Math.max(0, pos));
			CommentProcessor cpr = new CommentProcessor();
			CommentText comment = cpr.separateCommentFromContainer(node.getText(), in, lineSeparator);

			// format in position 0 to measure it
			ICommentContext out = pos0Context;
			TextFlow formatted = cpr.formatComment(
				comment, out, new CommentFormattingOptions(Integer.MAX_VALUE), layoutContext);
			int w = formatted.getWidth();
			if(w <= available) {
				// yay, it will fit as a hanging comment, reformat for this position.
				out = commentContext.getContextForPosition(current);
				formatted = cpr.formatComment(
					comment, out, new CommentFormattingOptions(Integer.MAX_VALUE), layoutContext);
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
				out = commentContext.getContextForPosition(use);
				// format (will wrap if required)
				// Enforce a min trailing empty line of 1 if this is a ML comment that is not on the same line,
				// but use 0 for SL comments (rule may be applied after split).
				formatted = cpr.formatComment(
					comment, out, new CommentFormattingOptions(maxWidth - use, commentContext.isSLStyle()
							? 0
							: 1), layoutContext);
				output.appendText(formatted.getText()); // , true);
			}

		}
	}

	public void format(StyleSet styleSet, IDomNode node, ITextFlow.Measuring output, ILayoutContext layoutContext,
			ICommentContext commentContext);
}
