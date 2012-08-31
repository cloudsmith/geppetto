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
package org.cloudsmith.geppetto.ruby;

import org.cloudsmith.geppetto.common.CharSequences;

/**
 * @author henrik
 * 
 */
public class RubyDocProcessor {

	private enum State {
		start, para, pre;
	};

	static public String asHTML(String s, int marginSize) {
		if(s == null || s.length() < 1)
			return s;
		if(marginSize < 0) {
			// indentation of first line is unknown, and it is impossible to figure out if the next line is
			// indented to "verbatim position" or not. give up:
			StringBuilder builder = new StringBuilder();
			builder.append("<pre>");
			builder.append(s);
			builder.append("</pre>");
			return builder.toString();
		}
		// the natural indent is 2 chars right of the given position:
		// @doc = "Starts here and
		// continues here
		// and here."
		//
		final int naturalMargin = marginSize;
		String[] lines = s.split("\\n");

		// trim left margin
		// first line is problematic, since initial whitespace is inconsistently used in the source
		// If it starts with whitespace, assume it is at the natural margin.

		for(int i = 0; i < lines.length; i++)
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
				}
				else {
					flowState = State.para;
					builder.append("<p>");
				}
				builder.append(line).append("\n");
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
					builder.append(line).append("\n");
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
					builder.append(line).append("\n");
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
