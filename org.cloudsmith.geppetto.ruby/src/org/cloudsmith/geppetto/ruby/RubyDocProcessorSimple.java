/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package org.cloudsmith.geppetto.ruby;

import org.cloudsmith.geppetto.common.CharSequences;

/**
 * Processes documentation text found in ruby code. The main problem is determining the "natural margin" as it is ambiguous.
 * 
 * 
 */
public class RubyDocProcessorSimple {

	private enum State {
		start, para, pre;
	};

	/**
	 * Substitutes '*text*' with '&lt;b>text&lt;/b>', and '`text`' with '&lt;tt>text&lt;/tt>', and
	 * appends the text and a new line.
	 * 
	 * @param s
	 * @param builder
	 */
	static private void appendNonVerbatimLine(String s, StringBuilder builder) {
		s = s.replaceAll("\\*\\*([^\\*]+)\\*\\*", "<strong>$1</strong>");
		s = s.replaceAll("\\*([^\\*]+)\\*", "<b>$1</b>");
		s = s.replaceAll("`([^`]+)`", "<tt>$1</tt>");

		builder.append(s).append("\n");
	}

	static public String asHTML(String s) {
		if(s == null || s.length() < 1)
			return s;

		int minPos = Integer.MAX_VALUE;
		String[] lines = s.split("\\n");
		for(int i = 1; i < lines.length; i++) {
			int idx = CharSequences.indexOfNonWhitespace(lines[i], 0);
			if(idx >= 0)
				minPos = Math.min(minPos, idx);
		}

		// trim left margin
		// first line is problematic, since initial whitespace is inconsistently used in the source
		// If it starts with whitespace, assume it is at the natural margin.

		final int naturalMargin = minPos;
		// always trim the first line - hope it is never a verbatim (how can it be detected? - indented from what?)
		if(lines.length > 0)
			lines[0] = CharSequences.trim(lines[0]).toString();
		for(int i = 1; i < lines.length; i++)
			lines[i] = CharSequences.trim(lines[i], naturalMargin, lines[i].length()).toString();

		State flowState = State.start;
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < lines.length; i++) {
			String line = lines[i];
			int firstCharPos = CharSequences.indexOfNonWhitespace(line, 0);
			boolean verbatimLine = firstCharPos > 0;
			boolean emptyLine = firstCharPos < 0;
			if(flowState == State.start) {
				if(emptyLine)
					continue; // skip empty leading lines
				if(verbatimLine) {
					flowState = State.pre;
					builder.append("<pre>");
					builder.append(line).append("\n");
				}
				else {
					flowState = State.para;
					builder.append("<p>");
					appendNonVerbatimLine(line, builder);
				}
			}
			else if(flowState == State.para) {
				if(verbatimLine) {
					builder.append("</p>\n<pre>");
					flowState = State.pre;
					builder.append(line).append("\n");
				}
				else if(emptyLine) {
					// start new para
					builder.append("</p>\n<p>");
				}
				else {
					appendNonVerbatimLine(line, builder);
				}
			}
			else if(flowState == State.pre) {
				if(verbatimLine) {
					builder.append(line).append("\n");
				}
				else if(emptyLine) {
					builder.append("\n");
				}
				else {
					// break pre
					flowState = State.para;
					builder.append("</pre>\n<p>");
					appendNonVerbatimLine(line, builder);
				}
			}
		}
		switch(flowState) {
			case pre:
				builder.append("</pre>");
				break;
			case para:
				builder.append("</p>");
				break;
			case start:
				// no content
				break;
		}
		return builder.toString();
	}
}
