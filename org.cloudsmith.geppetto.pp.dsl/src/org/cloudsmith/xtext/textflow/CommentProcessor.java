/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
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

import java.util.List;

/**
 * @author henrik
 * 
 */
public class CommentProcessor {

	private String startToken;

	private String repeating;

	private String endToken;

	private int hangsOn;

	private CharSequence trailingText;

	private int maxEmptyTrailing;

	private int minEmptyTrailing;

	public CommentProcessor(String startToken, String repeating, String endToken) {
		this.startToken = startToken;
		this.repeating = repeating;
		this.endToken = endToken;
		this.trailingText = "";
		this.maxEmptyTrailing = 1;
		this.minEmptyTrailing = 1;
	}

	protected CharSequence emit(List<CharSequence> lines, int on, String lineSeparator) {
		StringBuilder builder = new StringBuilder();
		CharSequence indent = new CharSequences.Spaces(on - repeating.length());
		ensureTrailingLines(lines);

		int limit = lines.size() - 1;
		for(int i = 0; i < limit; i++) {
			CharSequence s = lines.get(i);
			if(i == 0) {
				builder.append(startToken).append(s).append(lineSeparator);
			}
			else {
				builder.append(indent).append(repeating).append(s).append(lineSeparator);
			}
		}
		// process last line
		builder.append(indent).append(lines.get(limit)).append(endToken);
		// finally append trailing stuff
		builder.append(trailingText);
		return builder;
	}

	/**
	 * Surgically modify given list to conform to min/max trailing empty lines.
	 * 
	 * @param lines
	 */
	private void ensureTrailingLines(List<CharSequence> lines) {
		int nbrEmpty = numberOfTrailingEmptyLines(lines);
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

	public CharSequence processComment(CharSequence s, int posOnLine, int maxWidth, String lineSeparator) {

		// hanging indent guessed to be first pos after startToken
		hangsOn = posOnLine + startToken.length();

		// Split lines (lineSeparators removed)
		List<CharSequence> lines = CharSequences.split(s, lineSeparator);

		// remove the startToken from the first line
		CharSequence firstLine = lines.get(0);
		lines.set(0, firstLine.subSequence(startToken.length(), firstLine.length()));

		// remove the endToken from the last line
		// but remember any trailing stuff after the end token
		if(endToken != null && endToken.length() > 0) {
			int lastLineNbr = lines.size() - 1;
			CharSequence lastLine = lines.get(lastLineNbr);
			int endsAt = CharSequences.lastIndexOf(lastLine, endToken, lastLine.length() - 1);
			if(endsAt + endToken.length() < lastLine.length())
				trailingText = lastLine.subSequence(endsAt + endToken.length(), lastLine.length());
			lines.set(lastLineNbr, lastLine.subSequence(0, endsAt));
		}
		// Note, first line is already handled
		for(int i = 1; i < lines.size(); i++)
			lines.set(i, trimLeft(lines.get(i)));

		CharSequence result = emit(lines, hangsOn, lineSeparator);
		return result;
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
	protected CharSequence trimLeft(CharSequence s) {
		if(s.length() < 1)
			return s;
		int start = 0;
		// find first non ws char
		int limit = Math.min(hangsOn, s.length());
		for(; start < limit; start++) {
			if(!Character.isWhitespace(s.charAt(start)))
				break;
		}
		int repeatIdx = CharSequences.indexOf(s, repeating, start);
		HAS_REPEAT: if(repeatIdx != -1) {
			for(int i = start; i < repeatIdx; i++)
				if(!Character.isWhitespace(s.charAt(i)))
					break HAS_REPEAT;
			// start is first char after repeating
			// e.g. 4 in "    *_"
			start = repeatIdx + repeating.length();
		}
		// // Nope, don't do it - this is authors responsability, the comment may contain space sensitive markup that is then
		// // destroyed.
		// // assume that lines should start with a single separating space that should be
		// // skipped. This fixes a ragged left edge.
		// if(Character.isWhitespace(s.charAt(start)))
		// start++;
		int end = s.length();
		for(int i = s.length() - 1; i >= start; i--)
			if(Character.isWhitespace(s.charAt(i)))
				end--;
			else
				break;
		return s.subSequence(start, end);
	}
}
