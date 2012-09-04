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
package org.cloudsmith.geppetto.ruby;

import java.util.Collections;
import java.util.List;

import org.cloudsmith.geppetto.common.CharSequences;
import org.cloudsmith.geppetto.ruby.RubyDocProcessor.RubyDocLexer.HeadingToken;
import org.cloudsmith.geppetto.ruby.RubyDocProcessor.RubyDocLexer.Token;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * A better Ruby Doc parser
 * 
 */
public class RubyDocProcessor {
	public static class RubyDocLexer {
		public class HeadingToken extends Token {
			int level;

			HeadingToken(int lineIdx) {
				String s = lines[lineIdx];

				for(int i = 0; i < s.length(); i++)
					if(s.charAt(i) != '=') {
						text = s.substring(i);
						level = i;
						break;
					}
				level = Math.min(5, level);
			}

			public int getLevel() {
				return level;
			}
		}

		public class ListEndToken extends Token {
		}

		public class ListItemEndToken extends Token {
		}

		public class ListItemStartToken extends Token {
		}

		public class ListStartToken extends Token {
			ListStartToken(char startChar) {
				text = Character.toString(startChar);
			}
		}

		public class ParagraphEndToken extends Token {
		}

		public class ParagraphStartToken extends Token {
		}

		public class SpanToken extends Token {
			StringBuilder builder = new StringBuilder();

			SpanToken(int startLine, int lastLine) {
				if(startLine >= lastLine)
					throw new IllegalArgumentException("empty span");
				List<String> section = Lists.newArrayListWithExpectedSize(lastLine - startLine);
				for(int i = startLine; i < lastLine; i++)
					section.add(lines[i].substring(naturalMargin));
				text = Joiner.on(" ").join(section).toString();
				text = text.replaceAll("\\*\\*([^\\*]+)\\*\\*", "<strong>$1</strong>");
				text = text.replaceAll("\\*([^\\*]+)\\*", "<b>$1</b>");
				text = text.replaceAll("_([^_]+)_", "<i>$1</i>");
				text = text.replaceAll("`([^`]+)`", "<tt>$1</tt>");
				text = text.replaceAll("\\+([^\\+]+)\\+", "<tt>$1</tt>");

			}

		}

		public abstract class Token {
			protected String text = "";

			String getText() {
				return text;
			}

			/**
			 * Debugging type output.
			 */
			@Override
			public String toString() {
				StringBuilder builder = new StringBuilder();
				builder.append("(");
				builder.append(getClass().getSimpleName());
				if(getText().length() > 0)
					builder.append("'").append(getText()).append("'");
				builder.append(")");
				return builder.toString();
			}
		}

		public class VerbatimToken extends Token {

			VerbatimToken(int startLine, int endLine) {
				// keep lines verbatim, but remove their naturalMargin, or it will again be
				// indented when converted to HTML using a possible nested/indented <pre>
				StringBuilder builder = new StringBuilder();
				for(int i = startLine; i < endLine; i++)
					builder.append(CharSequences.trim(lines[i], naturalMargin, lines[i].length())).append("\n");
				text = builder.toString();
			}
		}

		private String[] lines;

		List<Integer> marginStack = Lists.newLinkedList();

		List<Token> tokens = Lists.newArrayList();

		int naturalMargin = 0;

		RubyDocLexer(String[] lines) {
			this.lines = lines;
			marginStack.add(0, naturalMargin); // start at 0
			tokenize();
		}

		private int eatComment(int start) {
			for(int i = start; i < lines.length; i++)
				if(isCommentEnd(i))
					return i;
			return lines.length - 1;
		}

		private int emitHeading(int start) {
			tokens.add(new HeadingToken(start));
			return start;
		}

		private int emitList(int start) {
			int nonWsPos = CharSequences.indexOfNonWhitespace(lines[start], naturalMargin);
			pushMargin();
			naturalMargin = CharSequences.indexOfNonWhitespace(lines[start], nonWsPos + 1);
			tokens.add(new ListStartToken(lines[start].charAt(nonWsPos)));
			int i = start;
			for(; i < lines.length; i++) {
				if(isCommentStart(i))
					i = eatComment(i);
				else if(isListContinue(i)) {
					// true for first list item, as well as subsequent items
					i = emitListItem(i);
				}
				else {
					break;
				}
			}
			tokens.add(new ListEndToken());
			popMargin();

			return i - 1;
		}

		private int emitListItem(int start) {
			tokens.add(new ListItemStartToken());
			int i = start;
			for(; i < lines.length; i++) {
				if(isCommentStart(i))
					i = eatComment(i);
				else if(i > start && isLeftOfMargin(i))
					break;
				else {
					i = emitPara(i);
				}
			}
			tokens.add(new ListItemEndToken());
			return i - 1;
		}

		private int emitPara(final int start) {
			tokens.add(new ParagraphStartToken());
			int i = start;
			int spanStart = start;
			for(; i < lines.length; i++) {
				if(isBlankLine(i)) {
					if(i > spanStart)
						tokens.add(new SpanToken(spanStart, i));
					spanStart = i + 1;
					break;
				}
				else if(isCommentStart(i)) {
					if(i > spanStart)
						tokens.add(new SpanToken(spanStart, i));
					i = eatComment(i);
					spanStart = i + 1;
				}
				else if(isVerbatim(i)) {
					if(i > spanStart)
						tokens.add(new SpanToken(spanStart, i));
					i = emitVerbatim(i);
					spanStart = i + 1;
					// continue
				}
				else if(i > start && isLeftOfMargin(i)) {
					if(i > spanStart)
						tokens.add(new SpanToken(spanStart, i));
					spanStart = i + 1;
					break;
				}
				else if(isHeading(i)) {
					if(i > spanStart)
						tokens.add(new SpanToken(spanStart, i));
					spanStart = i + 1;
					break;
				}
				else if(isListStart(i)) {
					if(i > spanStart)
						tokens.add(new SpanToken(spanStart, i));
					i = emitList(i);
					spanStart = i + 1;
				}
			}
			if(i > spanStart)
				tokens.add(new SpanToken(spanStart, i));
			tokens.add(new ParagraphEndToken());
			return i - 1;
		}

		private int emitParaOrList(int start) {
			if(isListStart(start))
				return emitList(start);
			else if(isHeading(start))
				return emitHeading(start);
			else
				return emitPara(start);
		}

		private int emitVerbatim(int start) {
			pushMargin();
			naturalMargin = naturalMargin + 2;
			// This makes it impossible to have a first line with more indent that natural + 2
			// natrualMargin = CharSequences.indexOfNonWhitespace(lines[start], naturalMargin);
			int lastVerbatimLine = start;
			for(int i = start; i < lines.length; i++)
				if(!isBlankLine(i) && isLeftOfMargin(i))
					break;
				else
					lastVerbatimLine++;
			tokens.add(new VerbatimToken(start, lastVerbatimLine));
			popMargin();
			return lastVerbatimLine - 1;
		}

		public List<Token> getTokens() {
			return Collections.unmodifiableList(tokens);
		}

		private boolean isBlankLine(int i) {
			return CharSequences.indexOfNonWhitespace(lines[i], 0) < 0;
		}

		private boolean isCommentEnd(int i) {
			return lines[i].startsWith("++");
		}

		private boolean isCommentStart(int i) {
			return lines[i].startsWith("--");
		}

		private boolean isHeading(int i) {
			return lines[i].startsWith("=");
		}

		private boolean isLeftOfMargin(int i) {
			return CharSequences.indexOfNonWhitespace(lines[i], 0) < naturalMargin;
		}

		private boolean isListContinue(int i) {
			int margin = marginStack.get(1);
			int nonWsPos = CharSequences.indexOfNonWhitespace(lines[i], margin);
			if(nonWsPos < 0)
				return false;
			return (isListStartChar(lines[i].charAt(nonWsPos)) && lines[i].length() >= nonWsPos + 1 && lines[i].charAt(nonWsPos + 1) == ' ');
		}

		/**
		 * Returns true, if the first char after natural margin is a list start char, and is followed by
		 * a whitespace. (This to not get a false positive on *bold*). Check starts at natural margin
		 * to enable * * sublist starts here
		 * 
		 * @param i
		 * @return
		 */
		private boolean isListStart(int i) {
			int nonWsPos = CharSequences.indexOfNonWhitespace(lines[i], naturalMargin);
			if(nonWsPos < 0)
				return false;
			return (isListStartChar(lines[i].charAt(nonWsPos)) && lines[i].length() >= nonWsPos + 1 && lines[i].charAt(nonWsPos + 1) == ' ');
		}

		private boolean isListStartChar(char c) {
			switch(c) {
				case '*':
				case '-':
					return true;
			}
			return false;
		}

		private boolean isVerbatim(int i) {
			return CharSequences.indexOfNonWhitespace(lines[i], 0) >= naturalMargin + 2;
		}

		private void popMargin() {
			naturalMargin = marginStack.remove(0);
		}

		private void pushMargin() {
			marginStack.add(0, naturalMargin);
		}

		private void tokenize() {
			for(int i = 0; i < lines.length; i++) {
				if(isBlankLine(i))
					continue; // skip leading lines
				if(isCommentStart(i))
					i = eatComment(i); // skip comment
				else if(isVerbatim(i))
					i = emitVerbatim(i);
				else
					i = emitParaOrList(i);
			}
		}
	}

	private PolymorphicDispatcher<String> htmlDispatcher = new PolymorphicDispatcher<String>(
		"_html", 1, 2, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<String> get()) {
		@Override
		protected String handleNoSuchMethod(Object... params) {
			return "<pre>INTERNAL ERROR, missing _html method for token: " + params[0].getClass().getName() + "</pre>";
		}
	};

	protected String _html(HeadingToken o) {
		StringBuilder builder = new StringBuilder();
		builder.append("<h").append(o.getLevel()).append(">");
		builder.append(o.getText());
		builder.append("</h").append(o.getLevel()).append(">");
		return builder.toString();
	}

	/**
	 * Join with ""
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	protected String _html(Object o1, Object o2) {
		return "";
	}

	protected String _html(RubyDocLexer.ListEndToken o) {
		return "</ul>";
	}

	protected String _html(RubyDocLexer.ListItemEndToken o) {
		return "</li>";
	}

	protected String _html(RubyDocLexer.ListItemStartToken o) {
		return "<li>";
	}

	protected String _html(RubyDocLexer.ListStartToken o) {
		return "<ul>";
	}

	protected String _html(RubyDocLexer.ParagraphEndToken o) {
		return "</p>";
	}

	protected String _html(RubyDocLexer.ParagraphStartToken o) {
		return "<p>";

	}

	protected String _html(RubyDocLexer.SpanToken o) {
		// this is really PCDATA - span is a container since spans can be nested
		return o.getText();
	}

	/**
	 * Join with " "
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	protected String _html(RubyDocLexer.SpanToken o1, RubyDocLexer.SpanToken o2) {
		return " ";
	}

	protected String _html(RubyDocLexer.VerbatimToken o) {
		StringBuilder builder = new StringBuilder();
		builder.append("<pre>");
		builder.append(o.getText());
		builder.append("</pre>");
		return builder.toString();
	}

	public String asHTML(String s) {
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

		// lines where pos 0 is the natural margin
		return asHTML(lines);
	}

	public String asHTML(String[] lines) {
		RubyDocLexer lexer = new RubyDocLexer(lines);
		StringBuilder builder = new StringBuilder();

		Object prevToken = ""; // represents start of input
		for(Token t : lexer.getTokens()) {
			builder.append(htmlDispatcher.invoke(prevToken, t)); // join on
			builder.append(htmlDispatcher.invoke(t));
			prevToken = t;
		}
		return builder.toString();
	}
}
