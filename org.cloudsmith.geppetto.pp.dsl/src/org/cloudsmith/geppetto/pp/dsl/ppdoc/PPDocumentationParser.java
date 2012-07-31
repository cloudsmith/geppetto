/**
 * Copyright (c) 2011, 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ppdoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.nodemodel.INode;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Provides parsing of puppet documentation.
 * Supports the following RDoc constructs
 * <ul>
 * <li>headings, =heading1, ==heading2, to =====heading5. Additional = treated same as heading5</li>
 * <li>bold, <b>*bold*</b></li>
 * <li>italic, <i>_italic_</i></li>
 * <li>fixed/code, <tt>+fixed+</tt></li>
 * <li>stop doc mode, --
 * <li>start doc mode, ++
 * <li>preformatted, indent more than <i>natural margin</i> (i.e. 2 spaces or one)</li>
 * <li>lists with hanging indents, starting with '*', '-' '<digit>.' or '<letter>.'</li>
 * <li>definitions with hanging idents - [label] or label::</li>
 * </ul>
 * <p>
 * Note that parsing is simplistic and combinations of bold/italic/fixed will be shown in the "outermost" style i.e. *_text_* is shown with only bold,
 * and not (bold+italic). (Somewhat untested, so there could be some surprising results). Also unsupported is the use of bold/italic/fixed in headers.
 * </p>
 * <p>
 * The parser produces a list of {@link DocNode} instances, where each node holds a sequence of the text, an offset (in the text document), a length,
 * and a style int. The result also contains the "comment parts" (i.e. '/' '*' '#' and whitespace) that are not considered part of the documentation
 * text. These nodes have the style HIDDEN. To get only the documentation text, simply concatenate all non HIDDEN nodes. Node text contains NL.
 * </p>
 */
public class PPDocumentationParser {

	public static class DocNode {
		int offset;

		int length;

		int style;

		String text;

		public DocNode(int offset, int length, int style, String text) {
			this.offset = offset;
			this.length = length;
			this.style = style;
			this.text = text;
		}

		public int getLength() {
			return length;
		}

		public int getOffset() {
			return offset;
		}

		public int getStyle() {
			return style;
		}

		public String getText() {
			return text;
		}
	}

	private PPGrammarAccess ga;

	/** Style for comment characters and whitespace (i.e. not part of documentation). */
	public static final int HIDDEN = 0x0;

	/** Regular documentation text */
	public static final int PLAIN = 0x1;

	/** preformatted/verbatim text */
	public static final int VERBATIM = 0x2;

	/** documentation comments between -- and ++ lines */
	public static final int COMMENT = 0x3; // a comment inside the doc

	public static final int HEADING_1 = 0x10;

	public static final int HEADING_2 = 0x20;

	public static final int HEADING_3 = 0x30;

	public static final int HEADING_4 = 0x40;

	public static final int HEADING_5 = 0x50;

	/** bold span */
	public static final int BOLD = 0x100;

	/** italic span */
	public static final int ITALIC = 0x200;

	/** span of same type as preformatted/code/fixed */
	public static final int FIXED = 0x400;

	/** Indicates a style (BOLD, ITALIC, FIXED) should be turned on */
	public static final int ON = 0x1000;

	/** Indicates a style (BOLD, ITALIC, FIXED) should be turned off */
	public static final int OFF = 0x2000;

	public static final Pattern nlPattern = Pattern.compile("\n");

	public static final Pattern leadingWs = Pattern.compile("^(\\s*\\*)?(.*)", Pattern.DOTALL);

	public static final Pattern mlCommentTail = Pattern.compile("\\s*?\\*/\\s*$", Pattern.DOTALL);

	public static final Pattern headingPattern = Pattern.compile("^\\s{0,1}(=+).*", Pattern.DOTALL);

	public static final Pattern stylePattern = Pattern.compile("(?:\\*.*?\\*)|(?:_.*?_)|(?:\\+.*?\\+)");

	public static final Pattern stopDocPattern = Pattern.compile("^\\s{0,1}\\-\\-\\s*", Pattern.DOTALL);

	public static final Pattern startDocPattern = Pattern.compile("^\\s{0,1}\\+\\+\\s*", Pattern.DOTALL);

	public static final Pattern hangingIndent = Pattern.compile("\\s*(\\*|\\-|[a-z0-9]\\.|\\[.*?\\]|.*?::)\\s*");

	public static final Pattern blankLine = Pattern.compile("\\s*", Pattern.DOTALL);

	private int naturalMargin = 1;

	private int currentIndent = naturalMargin;

	private List<Integer> indentStack = new ArrayList<Integer>();

	@Inject
	PPDocumentationParser(IGrammarAccess ga) {
		this.ga = (PPGrammarAccess) ga;
	}

	private void addStyleNodes(List<DocNode> result, int offset, String line) {
		// blank lines have no effect on indentation, and are always styled as plain
		if(blankLine.matcher(line).matches()) {
			result.add(new DocNode(offset, line.length(), PLAIN, line));
			return;
		}
		int indent = indentation(line);
		if(indent < currentIndent)
			popIndentTo(indent);

		if(indent > currentIndent) {
			// // if line starts with more than a single space (or tab), it is verbatim
			// if(line.startsWith("  ") || line.startsWith(" \t") || line.startsWith("\t")) {
			result.add(new DocNode(offset, line.length(), VERBATIM, line));
			return;
		}

		Matcher mr = hangingIndent.matcher(line);
		if(mr.lookingAt())
			pushIndent(mr.end());

		mr = headingPattern.matcher(line);
		if(mr.find()) {
			int headingLevel = mr.group(1).length();
			int style = Math.min(HEADING_1 * headingLevel, HEADING_5);
			result.add(new DocNode(offset, line.length(), style, line));
			// ignore fixed, italic and bold in headings for now

			popIndentTo(0); // pops to natural margin
			return;
		}
		mr = stylePattern.matcher(line);
		boolean match = mr.find();
		while(match) {
			StringBuffer matched = new StringBuffer();
			mr.appendReplacement(matched, ""); // before the style
			int matchLength = matched.length();
			if(matchLength > 0) {
				result.add(new DocNode(offset, matchLength, PLAIN, matched.toString()));
				offset += matchLength;
			}
			String styled = mr.group();
			int style = 0;
			if(styled.startsWith("*"))
				style = BOLD;
			else if(styled.startsWith("_"))
				style = ITALIC;
			else if(styled.startsWith("+"))
				style = FIXED;

			result.add(new DocNode(offset, styled.length(), style, styled));
			offset += styled.length();
			match = mr.find();
		}
		StringBuffer matched = new StringBuffer();
		mr.appendTail(matched);
		int matchLength = matched.length();
		if(matchLength > 0)
			result.add(new DocNode(offset, matchLength, PLAIN, matched.toString()));
	}

	/**
	 * Returns the number of space characters at the start of the string.
	 * 
	 * @param line
	 * @return
	 */
	private int indentation(String line) {
		int i = 0;
		for(; i < line.length(); i++)
			if(line.charAt(i) != ' ')
				return i;
		return i;
	}

	public List<DocNode> parse(List<INode> nodes) {
		naturalMargin = 1;
		indentStack.clear();
		pushIndent(naturalMargin);
		if(nodes.size() > 0) {
			if(nodes.size() == 1 && nodes.get(0).getGrammarElement() == ga.getML_COMMENTRule())
				return processMLComment(nodes.get(0));
			return processSLSequence(nodes);
		}
		return Collections.emptyList();
	}

	private int popIndent() {
		currentIndent = indentStack.get(indentStack.size() - 1);
		return currentIndent;
	}

	private int popIndentTo(int indent) {
		for(int i = indentStack.size() - 1; i > 0; i--) {
			if(indentStack.get(i) > indent)
				indentStack.remove(i);
			else
				break;
		}
		currentIndent = indentStack.get(indentStack.size() - 1);
		return currentIndent;
	}

	private List<DocNode> processMLComment(INode node) {
		List<DocNode> result = Lists.newArrayList();
		String allText = node.getText();
		int start = allText.indexOf("/*");

		result.add(new DocNode(node.getOffset(), start + 2, HIDDEN, allText.substring(0, start + 2)));
		int offset = node.getOffset() + start + 2;

		Matcher matcher = mlCommentTail.matcher(allText);
		matcher.find();
		int tail = matcher.start();
		final DocNode endNode = new DocNode(
			node.getOffset() + tail, allText.length() - tail, HIDDEN, allText.substring(tail, allText.length()));

		allText = allText.substring(start + 2, tail);

		int commentLevel = 0;

		String[] lines = nlPattern.split(allText, 0);
		for(int i = 0; i < lines.length; i++) {
			String line = lines[i];
			if(lines.length > 1 && i < lines.length - 1)
				line = line + "\n"; // must add the newline back (needed in the resulting nodes)
			Matcher mr = leadingWs.matcher(line);
			String leading = "";
			String remainder = line;
			if(mr.matches()) {
				leading = mr.group(1);
				if(leading != null && leading.length() > 0) {
					result.add(new DocNode(offset, leading.length(), HIDDEN, leading));
					offset += leading.length();
					remainder = mr.group(2);
				}
			}
			// Input between "--" and "++" are comments in the doc
			if(startDocPattern.matcher(remainder).matches()) {
				commentLevel = Math.max(commentLevel - 1, 0);
				result.add(new DocNode(offset, remainder.length(), COMMENT, remainder));
				offset += remainder.length();
				continue;
			}
			if(stopDocPattern.matcher(remainder).matches()) {
				commentLevel++;
				result.add(new DocNode(offset, remainder.length(), COMMENT, remainder));
				offset += remainder.length();
				continue;
			}
			if(commentLevel > 0)
				result.add(new DocNode(offset, remainder.length(), COMMENT, remainder));
			else
				addStyleNodes(result, offset, remainder);
			offset += remainder.length();

		}

		result.add(endNode);
		return result;
	}

	private List<DocNode> processSLSequence(List<INode> nodes) {
		List<DocNode> result = Lists.newArrayList();

		for(INode node : nodes) {
			String allText = node.getText();
			result.add(new DocNode(node.getOffset(), 1, HIDDEN, allText.substring(0, 1)));
			int offset = node.getOffset() + 1;
			String line = allText.substring(1);

			int commentLevel = 0;

			Matcher mr = leadingWs.matcher(line);
			String leading = "";
			String remainder = line;
			if(mr.matches()) {
				leading = mr.group(1);
				if(leading != null && leading.length() > 0) {
					result.add(new DocNode(offset, leading.length(), HIDDEN, leading));
					offset += leading.length();
					remainder = mr.group(2);
				}
			}
			// Input between "--" and "++" are comments in the doc
			if(startDocPattern.matcher(remainder).matches()) {
				commentLevel = Math.max(commentLevel - 1, 0);
				result.add(new DocNode(offset, remainder.length(), COMMENT, remainder));
				offset += remainder.length();
				continue;
			}
			if(stopDocPattern.matcher(remainder).matches()) {
				commentLevel++;
				result.add(new DocNode(offset, remainder.length(), COMMENT, remainder));
				offset += remainder.length();
				continue;
			}
			if(commentLevel > 0)
				result.add(new DocNode(offset, remainder.length(), COMMENT, remainder));
			else
				addStyleNodes(result, offset, remainder);
			offset += remainder.length();

		}

		return result;
	}

	private void pushIndent(int indent) {
		currentIndent = indent;
		indentStack.add(indent);
	}

}
