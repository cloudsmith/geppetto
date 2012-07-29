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
package org.cloudsmith.xtext.textflow;

import java.util.ArrayList;
import java.util.List;

import org.cloudsmith.xtext.dommodel.formatter.css.Alignment;

import com.google.common.collect.Lists;

/**
 * This comment processor is used to parse and format comments spanning multiple lines.
 * Comments may be a sequence of single line comments, or a multi line comment.
 * When processing a sequence of single line comments, everything in the passed region to format
 * must be comment text or whitespace - input in this example:
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
 * To relocate the comment, or output it in a different style, use a second {@link ICommentContext} in the format call.
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
 */
public class CommentProcessor {

	public static class CommentFormattingOptions {
		private int maxWidth;

		private int minEmptyTrailing;

		private int maxEmptyTrailing;

		public CommentFormattingOptions(int maxWidth) {
			this(maxWidth, 1, 1);
		}

		public CommentFormattingOptions(int maxWidth, int minEmptyTrailing, int maxEmptyTrailing) {
			this.maxWidth = maxWidth;
			this.minEmptyTrailing = minEmptyTrailing;
			this.maxEmptyTrailing = maxEmptyTrailing;
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

	}

	private CharSequence trailingText;

	private ICommentContext in;

	public CommentProcessor(ICommentContext in) {
		this.in = in;
		this.trailingText = "";
	}

	protected CharSequence emit(List<CharSequence> lines, ICommentContext out, CommentFormattingOptions options,
			String lineSeparator) {
		StringBuilder builder = new StringBuilder();
		int indentSize = out.getLeftPosition();
		int leftMarginSize = out.getLeftMargin();
		if(Alignment.right == out.getMarkerColumnAlignment())
			indentSize += out.getMarkerColumnWidth() - out.getRepeatingToken().length();
		CharSequence indent = new CharSequences.Spaces(indentSize);
		CharSequence leftMargin = new CharSequences.Spaces(leftMarginSize);

		ensureTrailingLines(lines, options);

		int limit = lines.size() - 1;
		for(int i = 0; i < limit; i++) {
			CharSequence s = lines.get(i);
			if(i == 0)
				builder.append(out.getStartToken());
			else
				builder.append(indent).append(out.getRepeatingToken());

			if(s.length() > 0) {
				// Homogeneous lines should not have a leftMargin e.g. '#---' '********'
				if(!CharSequences.isHomogeneous(s))
					builder.append(leftMargin);
				builder.append(s);
			}
			builder.append(lineSeparator);
		}

		// process last line
		CharSequence s = lines.get(limit);
		builder.append(indent);
		if(s.length() > 0) {
			builder.append(out.getRepeatingToken());
			if(!CharSequences.isHomogeneous(s))
				builder.append(leftMargin);
			builder.append(s);
		}
		builder.append(out.getEndToken());
		// finally append trailing stuff
		builder.append(trailingText);
		return builder;
	}

	/**
	 * Surgically modify given list to conform to min/max trailing empty lines.
	 * 
	 * @param lines
	 */
	private void ensureTrailingLines(List<CharSequence> lines, CommentFormattingOptions options) {
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

	public List<CharSequence> foldLine(CharSequence s, int width) {
		ArrayList<CharSequence> result = Lists.newArrayList();
		int originalIndentation = CharSequences.indexOfNonWhitespace(s, 0);
		if(originalIndentation == -1) {
			result.add(""); // protect against all whitespace (should not happen).
		}
		else if(width <= 0 || s.length() < width) {
			result.add(s);
		}
		else {
			while(s != null && s.length() > width) {
				// chop of first part
				int end = CharSequences.lastIndexOfWhitespace(s, width);
				if(end < 0) {
					// could not make first part of string comply with width, make it as short as possible
					end = CharSequences.indexOfWhitespace(s, width);
				}
				if(end == -1 || end == s.length() - 1) {
					// have to accept all of it
					result.add(s);
					s = null;
				}
				else {
					CharSequence t = s.subSequence(0, end);
					CharSequences.trim(t, 0, end);
					result.add(t);

					s = s.subSequence(end, s.length());
					// if just spaces, or empty stuff, do not make it into a line
					if(CharSequences.isEmpty(s)) {
						s = null;
					}
					else {
						s = CharSequences.trim(s, end, 0);
						// indent the hacked off part to same position as original
						// (note that spaces function returns empty sequence if count < 0)
						s = CharSequences.concatenate(CharSequences.spaces(originalIndentation), s);
					}
				}
			}
			// add the conforming trailing part (if there was one)
			if(s != null)
				result.add(s);
		}
		return result;
	}

	public void foldLines(List<CharSequence> lines, int width) {
		int limit = lines.size();
		for(int i = 0; i < limit; i++) {
			CharSequence s = lines.get(i);
			if(s.length() > width) {
				// shorten if banner, else fold
				if(isBanner(s)) {
					lines.set(i, s.subSequence(0, width + 1));
				}
				else {
					List<CharSequence> folded = foldLine(s, width);
					lines.set(i, folded.get(0));
					lines.addAll(i + 1, folded.subList(1, folded.size()));
				}
			}
		}
	}

	public CharSequence formatComment(CharSequence s, CommentFormattingOptions options, String lineSeparator) {
		return formatComment(s, in, options, lineSeparator);
	}

	public CharSequence formatComment(CharSequence s, ICommentContext out, CommentFormattingOptions options,
			String lineSeparator) {

		// Split lines (lineSeparators removed)
		List<CharSequence> lines = CharSequences.split(s, lineSeparator);

		// parse and trim first line
		lines.set(0, trim(lines.get(0), in.getStartToken(), in));

		// remove the endToken from the last line
		// but remember any trailing stuff after the end token
		String endToken = in.getEndToken();
		if(!in.isSLStyle() && in.getEndToken().length() > 0) {
			int lastLineNbr = lines.size() - 1;
			CharSequence lastLine = lines.get(lastLineNbr);
			int lastLineLength = lastLine.length();
			int endsAt = CharSequences.lastIndexOf(lastLine, endToken, lastLineLength - 1);
			if(endsAt + endToken.length() < lastLineLength)
				trailingText = lastLine.subSequence(endsAt + endToken.length(), lastLineLength);
			lines.set(lastLineNbr, lastLine.subSequence(0, endsAt));
		}
		// Note, first line is already handled, and last line already has its marker removed, so any content
		// before the end token, as in "* some text */" is covered by the repeatRule, and text like
		// "    some text */" is covered by the computed beginning of the text before the end marker.
		//
		for(int i = 1; i < lines.size(); i++)
			lines.set(i, trim(lines.get(i), in.getRepeatingToken(), in));

		foldLines(lines, options.getMaxWidth() - out.getMarkerColumnWidth() - out.getLeftMargin());
		CharSequence result = emit(lines, out, options, lineSeparator);
		return result;
	}

	/**
	 * Returns true if the line has length 5 or longer and {@link #isHomogeneous(CharSequence)}. This is intended
	 * to answer true for lines that can be shortened instead of wrapped when they exceed the width.
	 * It also enables extending such lines to the max allowed width.
	 * 
	 * The number 5 is selected since certain comment processors (RDoc is one) use '---' and '+++' and similar instructions
	 * to indicate processing instructions - and such should never be extended.
	 * 
	 * @param s
	 * @return
	 */
	protected boolean isBanner(CharSequence s) {
		return s.length() > 4 && CharSequences.isHomogeneous(s);
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
	protected CharSequence trim(CharSequence s, String expectedToken, ICommentContext in) {
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
