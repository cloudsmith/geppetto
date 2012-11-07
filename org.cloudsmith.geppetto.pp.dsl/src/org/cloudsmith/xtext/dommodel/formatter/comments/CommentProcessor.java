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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContext;
import org.cloudsmith.xtext.dommodel.formatter.css.Alignment;
import org.cloudsmith.xtext.textflow.CharSequences;
import org.cloudsmith.xtext.textflow.TextFlow;

import com.google.common.collect.Lists;

/**
 * <p>
 * This comment processor is used to parse and format comments spanning multiple lines. It is a low-level class that can be used to implement higher
 * order comment formatting strategies.
 * </p>
 * <p>
 * Comments may be a sequence of single line comments, or a multi line comment. When processing a sequence of single line comments, everything in the
 * passed region to format must be comment text or whitespace - input in this example:
 * 
 * <pre>
 *  # comment
 *  a = 10 # comment
 *  b = 20 # comment
 * </pre>
 * 
 * will result in something like:
 * 
 * <pre>
 *  # comment
 *  # a = 10 # comment
 *  # b = 20 # comment
 *  # comment
 * </pre>
 * <p>
 * <b>Usage:</b> An instance of the class is created with an ICommentContext that describes how the comment should be parsed. This context includes
 * the relative starting position of the comment sequence. One of the classes implementing this interface may be used as a convenience. As an example,
 * given the text:
 * </p>
 * 
 * <pre>
 * {@code
 * a = 10 /*
 *     * comment starts here
 *     * and continues here,
 *  * but is not aligned ok
 *    and some lines do not start with the repeating character
 *  ∗/
 * }
 * </pre>
 * 
 * Is processed by providing the starting line offset 7. When formatting with the same context, the result is:
 * 
 * <pre>
 * {@code
 * a = 10 /*
 *         * comment starts here
 *         * and continues here
 *         * but is not aligned ok
 *         * and some lines do not start with the repeating character
 *         ∗/ 
 * }
 * </pre>
 * 
 * To relocate the comment, or output it in a different style, use a second {@link ICommentContainerInformation} in the format call.
 * The same input example as before can be output to look like this:
 * 
 * <pre>
 * {@code
 * a = 10 #
 *        # comment starts here
 *        # and continues here
 *        # but is not aligned ok
 *        # and some lines do not start with the repeating character
 *        # 
 * }
 * </pre>
 * <p>
 * The comment processor has special processing of comment lines that consists of the same repeated character. All such lines are formatted without a
 * left margin, and such lines longer than 5 characters are truncated instead of folded/wrapped when not fitting within the max constraints.
 * </p>
 * <p>
 * It is possible to constrain trailing empty lines min/max.
 * </p>
 * <p>
 * Note that the result will contain leading whitespace up to the position passed as the starting position. When appending the text to an existing
 * flow already at this position, the method {@link CharSequences#trimLeft(CharSequence)} can be used to adjust the text to this position.
 * </p>
 * TODO: Needs to know about the ICommentFormatterAdvice !
 */
public class CommentProcessor {

	public static class CommentFormattingOptions {
		private int maxWidth;

		private int minEmptyTrailing;

		private int maxEmptyTrailing;

		private boolean retainInline;

		private ICommentFormatterAdvice advice;

		public CommentFormattingOptions(ICommentFormatterAdvice advice, int maxWidth) {
			this(advice, maxWidth, 1, 1, true);
		}

		public CommentFormattingOptions(ICommentFormatterAdvice advice, int maxWidth, int trailing) {
			this(advice, maxWidth, trailing, trailing, true);
		}

		public CommentFormattingOptions(ICommentFormatterAdvice advice, int maxWidth, int minEmptyTrailing,
				int maxEmptyTrailing) {
			this(advice, maxWidth, minEmptyTrailing, maxEmptyTrailing, true);
		}

		public CommentFormattingOptions(ICommentFormatterAdvice advice, int maxWidth, int minEmptyTrailing,
				int maxEmptyTrailing, boolean retainInline) {
			this.maxWidth = maxWidth;
			this.minEmptyTrailing = minEmptyTrailing;
			this.maxEmptyTrailing = maxEmptyTrailing;
			this.retainInline = retainInline;
			this.advice = advice;
		}

		/**
		 * @return the maxEmptyTrailing
		 */
		public int getMaxEmptyTrailing() {
			return maxEmptyTrailing;
		}

		/**
		 * @return the maxWidth of the formatted comment (including markers and margin)
		 */
		public int getMaxWidth() {
			return maxWidth;
		}

		/**
		 * @return the minEmptyTrailing
		 */
		public int getMinEmptyTrailing() {
			return minEmptyTrailing;
		}

		/**
		 * @return true if an inline comment should be retained on a single line
		 */
		public boolean isRetainInline() {
			return retainInline;

		}

	}

	public static class CommentText {
		private CharSequence trailingContainerText;

		private List<CharSequence> lines;

		public CommentText(List<CharSequence> lines, CharSequence trailingContainerText) {
			this.lines = lines;
			this.trailingContainerText = trailingContainerText;
		}

		public List<CharSequence> getLines() {
			return lines;
		}
	}

	public CommentProcessor() {
	}

	protected TextFlow emit(CommentText commentText, ICommentContainerInformation out,
			CommentFormattingOptions options, IFormattingContext formattingContext) {
		final ICommentFormatterAdvice advice = options.advice;
		final String lineSeparator = formattingContext.getLineSeparatorInformation().getLineSeparator();
		final String endToken = out.getEndToken();
		List<CharSequence> lines = commentText.lines;
		TextFlow flow = new TextFlow(formattingContext);
		// StringBuilder builder = new StringBuilder();

		int indentSize = out.getLeftPosition();
		int leftMarginSize = out.getLeftMargin();
		if(Alignment.right == out.getMarkerColumnAlignment())
			indentSize += out.getMarkerColumnWidth() - out.getRepeatingToken().length();
		CharSequence indent = new CharSequences.Spaces(indentSize);
		CharSequence leftMargin = new CharSequences.Spaces(leftMarginSize);

		ensureTrailingLines(lines, options);
		try {
			// always process first line even if it is also the last
			int limit = Math.max(1, lines.size() - 1);
			boolean singleLine = lines.size() == 1;
			for(int i = 0; i < limit; i++) {
				// comment container
				if(i == 0)
					flow.append(CharSequences.spaces(out.getLeftPosition())).append(out.getStartToken());
				else
					flow.append(indent).append(out.getRepeatingToken());

				CharSequence s = lines.get(i);
				if(s.length() > 0) {
					boolean hasBannerLength = s.length() > 4;
					boolean alignSpecialLeft = advice.getAlignSpecialLinesLeft();

					// Homogeneous lines should not have a leftMargin e.g. '#---' '********' unless advice says so
					// anything starting with letter or digit, or that is not homogeneous has a leftMargin
					if(Character.isLetterOrDigit(s.charAt(0)) //
							||
							!(CharSequences.isHomogeneous(s) && (hasBannerLength || alignSpecialLeft)))
						flow.append(leftMargin);
					flow.append(s);

					// // Homogeneous lines should not have a leftMargin e.g. '#---' '********' unless advice says so
					// // anything starting with letter or digit, or that is not homogeneous has a leftMargin
					// if(Character.isLetterOrDigit(s.charAt(0)) || !advice.getAlignSpecialLinesLeft() ||
					// !(CharSequences.isHomogeneous(s)))
					// flow.append(leftMargin);
					// flow.append(s);
				}
				if(!singleLine)
					flow.append(lineSeparator);
			}
			// process last line
			if(singleLine) {
				// last line is the same as the first
				if(endToken.length() > 0)
					flow.append(" "); // space before end token (if one will be output)
			}
			else {
				CharSequence s = lines.get(limit);
				flow.append(indent);
				if(s.length() > 0 || out.isSLStyle()) {
					flow.append(out.getRepeatingToken());
					if(s.length() > 0) {
						if(Character.isLetterOrDigit(s.charAt(0)) || !CharSequences.isHomogeneous(s))
							flow.append(leftMargin);
						flow.append(s);
						if(!out.isSLStyle())
							flow.append(" "); // a ML comment may be followed by something
					}
				}
			}
			if(endToken.length() > 0)
				flow.append(out.getEndToken());
			// finally append trailing stuff
			if(commentText.trailingContainerText.length() > 0)
				flow.append(commentText.trailingContainerText);
		}
		catch(IOException e) {
			// can't happen here, since the TextFlow uses a StringBuilder
			// TODO: Actually - this is wrong, the API is open
		}
		return flow;
	}

	/**
	 * Surgically modify given list to conform to min/max trailing empty lines.
	 * 
	 * @param lines
	 */
	private void ensureTrailingLines(List<CharSequence> lines, CommentFormattingOptions options) {
		if(lines.size() == 1 && options.isRetainInline())
			return; // do nothing for inline/same-line comments

		int nbrEmpty = numberOfTrailingEmptyLines(lines);
		int minEmptyTrailing = options.getMinEmptyTrailing();
		int maxEmptyTrailing = options.getMaxEmptyTrailing();
		// ensure min number of empty lines
		while(nbrEmpty < minEmptyTrailing) {
			lines.add("");
			nbrEmpty++;
		}
		// ensure max
		while(nbrEmpty > maxEmptyTrailing) {
			lines.remove(lines.size() - 1);
			nbrEmpty--;
		}

	}

	public List<CharSequence> foldLine(CharSequence s, int width, ICommentFormatterAdvice advice) {
		ArrayList<CharSequence> result = Lists.newArrayList();
		int originalIndentation = CharSequences.indexOfNonWhitespace(s, 0);
		if(originalIndentation == -1) {
			result.add(""); // protect against all whitespace (should not happen).
		}
		else if(width <= 0 || s.length() < width) {
			result.add(s);
		}
		else {
			CharSequence template = protectedRegionsTemplate(s, advice);

			while(s != null && s.length() > width) {
				// chop of first part
				// int end = CharSequences.lastIndexOfWhitespace(s, width);
				int end = CharSequences.lastIndexOfWhitespace(template, width);
				if(end < 0) {
					// could not make first part of string comply with width, make it as short as possible
					// end = CharSequences.indexOfWhitespace(s, width);
					end = CharSequences.indexOfWhitespace(template, width);
				}
				if(end == -1 || end == s.length() - 1) {
					// have to accept all of it
					result.add(s);
					s = null;
				}
				else {
					CharSequence t = s.subSequence(0, end);
					t = CharSequences.trim(t, 0, end);
					result.add(t);

					// adjust s and template
					s = s.subSequence(end, s.length());
					template = template.subSequence(end, template.length());

					// if just spaces, or empty stuff, do not make it into a line
					if(CharSequences.isEmpty(s)) {
						s = null;
					}
					else {
						s = CharSequences.trim(s, end, 0);
						template = CharSequences.trim(template, end, 0);
						// indent the hacked off part to same position as original
						// (note that spaces function returns empty sequence if count < 0)
						s = CharSequences.concatenate(CharSequences.spaces(originalIndentation), s);
						template = CharSequences.concatenate(CharSequences.spaces(originalIndentation), template);
					}
				}
			}
			// add the conforming trailing part (if there was one)
			if(s != null)
				result.add(s);
		}
		return result;
	}

	public void foldLines(List<CharSequence> lines, ICommentFormatterAdvice advice, int width) {
		for(int i = 0; i < lines.size(); i++) {
			CharSequence s = lines.get(i);
			if(s.length() > width) {
				// shorten if banner, else fold
				if(isBanner(s)) {
					switch(advice.getBannerAdvice()) {
						case Truncate:
							lines.set(i, s.subSequence(0, width + 1));
							break;
						case Fold:
							lines.set(i, s.subSequence(0, width + 1));
							lines.add(i + 1, s.subSequence(width, s.length()));
							break;
						case NoWrap:
							// keep it
							break;
					}
				}
				else {
					List<CharSequence> folded = foldLine(s, width, advice);
					lines.set(i, folded.get(0));
					lines.addAll(i + 1, folded.subList(1, folded.size()));
					i += folded.size() - 1; // skip lines that are already folded
				}
			}
		}
	}

	public TextFlow formatComment(CharSequence s, ICommentContainerInformation in, ICommentContainerInformation out,
			CommentFormattingOptions options, IFormattingContext context) {
		String lineSeparator = context.getLineSeparatorInformation().getLineSeparator();
		return formatComment(separateCommentFromContainer(s, out, lineSeparator), out, options, context);
	}

	public TextFlow formatComment(CommentText commentText, ICommentContainerInformation out,
			CommentFormattingOptions options, IFormattingContext context) {
		foldLines(
			commentText.lines, options.advice, options.getMaxWidth() - out.getMarkerColumnWidth() - out.getLeftMargin());
		TextFlow result = emit(commentText, out, options, context);
		return result;

	}

	/**
	 * Returns true if the line has length 5 or longer and {@link #isHomogeneous(CharSequence)} and the sequence
	 * of characters is not a letter or digit. This is intended
	 * to answer true for lines that can be truncated instead of wrapped when they exceed the width.
	 * It also enables extending such lines to the max allowed width.
	 * 
	 * The number 5 is selected since certain comment processors (RDoc is one) use '---' and '+++' and similar instructions
	 * to indicate processing instructions - and such should never be extended.
	 * 
	 * @param s
	 * @return
	 */
	protected boolean isBanner(CharSequence s) {
		return s.length() > 4 && !Character.isLetterOrDigit(s.charAt(0)) && CharSequences.isHomogeneous(s);
	}

	protected int numberOfTrailingEmptyLines(List<CharSequence> lines) {
		int count = 0;
		for(int i = lines.size() - 1; i >= 0; i--) {
			if(lines.get(i).length() == 0)
				count++;
			else
				break;
		}
		return count;
	}

	private CharSequence protectedRegionsTemplate(CharSequence s, ICommentFormatterAdvice advice) {
		if(!advice.isDoubleDollarVerbatim())
			return s;
		boolean inProtectedArea = false;
		StringBuilder builder = new StringBuilder(s.length());
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(inProtectedArea && c != '$')
				builder.append("x");
			else {
				if(c == '$') {
					if(inProtectedArea)
						inProtectedArea = false;
					else {
						if(CharSequences.indexOf(s, "$", i + 1) > 0)
							inProtectedArea = true;
					}
				}
				builder.append(c);
			}
		}
		return builder;
	}

	/**
	 * Separates the comment text from its surrounding container. The result is a sequence of trimmed text lines.
	 * The text does not contain any of the comment start/repeat/end tokens, and the text is relative to
	 * the comment's natural margin.
	 * 
	 * @return {@link CommentText} with trimmed lines and any trailing text after endToken
	 */
	public CommentText separateCommentFromContainer(CharSequence s, ICommentContainerInformation in,
			String lineSeparator) {
		// separate the comment between start-end (if any) from any trailing stuff
		final String endToken = in.getEndToken();
		List<CharSequence> lines = null;
		CharSequence trailingText = "";
		if(in.isSLStyle()) {
			lines = CharSequences.split(s, lineSeparator, false);
			trailingText = lineSeparator;
		}
		else if(endToken.length() > 0) {
			// ML with end token, and optional trailing text.
			List<CharSequence> bodyAndTrailing = CharSequences.split(s, endToken);
			s = bodyAndTrailing.get(0);
			trailingText = bodyAndTrailing.get(1);
			lines = CharSequences.split(s, lineSeparator, true);
		}
		else {
			// ML without end token included in text, no trailing
			lines = CharSequences.split(s, lineSeparator, false);
		}

		// lineSeparators are removed at this point

		// parse and trim first line (removes startToken as well)
		lines.set(0, trim(lines.get(0), in.getStartToken(), in));

		for(int i = 1; i < lines.size(); i++)
			lines.set(i, trim(lines.get(i), in.getRepeatingToken(), in));
		return new CommentText(lines, trailingText);
	}

	/**
	 * Special trim left that is aware of a) the hanging indent position b) the position of the repeating sequence.
	 * Will trim to hanging, or to the position after the repeating if everything left of repeating is space.
	 * <p>
	 * <b>Example</b><br/>
	 * Given hanging indent is 2, and '_' denotes space, all of these are trimmed to <code>"a "</code>
	 * <ul>
	 * <li><code>"_*a "</code></li>
	 * <li><code>"__a "</code></li>
	 * <li><code>"_a "</code></li>
	 * <li><code>"a "</code></li>
	 * <li><code>"_______*a " -> "a "</code></li>
	 * </ul>
	 * </p>
	 * 
	 * @param s
	 * @return
	 */
	protected CharSequence trim(CharSequence s, String expectedToken, ICommentContainerInformation in) {
		if(s.length() < 1)
			return s;
		int start = 0;
		int hangsOn = in.getLeftPosition() + in.getMarkerColumnWidth();

		// find first non ws char
		int limit = Math.min(hangsOn, s.length());
		for(; start < limit; start++) {
			if(!Character.isWhitespace(s.charAt(start)))
				break;
		}
		// String repeating = in.getRepeatingToken();
		int expectedTokenLength = expectedToken.length();
		int repeatIdx = CharSequences.indexOf(s, expectedToken, start);
		HAS_REPEAT: if(repeatIdx != -1) {
			for(int i = start; i < repeatIdx; i++)
				if(!Character.isWhitespace(s.charAt(i)))
					break HAS_REPEAT;
			// start is first char after repeating
			// e.g. 4 in "    *_"
			start = repeatIdx + expectedTokenLength;
		}
		limit = s.length();
		return CharSequences.trim(s.subSequence(start, limit), in.getLeftMargin(), limit);
	}
}
