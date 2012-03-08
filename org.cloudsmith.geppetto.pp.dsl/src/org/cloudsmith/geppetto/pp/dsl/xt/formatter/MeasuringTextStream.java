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
package org.cloudsmith.geppetto.pp.dsl.xt.formatter;

import java.util.List;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.IFormattingContext;
import org.eclipse.xtext.util.Strings;

import com.google.inject.Inject;

/**
 * A text stream that measures the content placed in it but that does not contain the actual text.
 * 
 */
public class MeasuringTextStream implements IFormStream, ITextMetrics {

	int indentSize = 2;

	int indent = 0;

	private boolean lastWasBreak;

	private int numberOfLines;

	private int currentLineWidth;

	private int maxWidth;

	private String lineSeparator;

	private int lastLineWidth;

	@Inject
	public MeasuringTextStream(IFormattingContext formattingContext) {
		this.indent = 0;
		this.lastWasBreak = false;

		String indentationString = formattingContext.getIndentationInformation().getIndentString();

		indentSize = indentationString.length();
		if(indentSize > 0) {
			char c = indentationString.charAt(0);
			for(int i = 0; i < indentSize; i++)
				if(indentationString.charAt(i) != c)
					throw new IllegalStateException("Indentation string must consist of the same character");
		}

		numberOfLines = 0;
		currentLineWidth = 0;
		maxWidth = 0;
		lineSeparator = formattingContext.getLineSeparatorInformation().getLineSeparator();
	}

	@Override
	public void breaks(int count) {
		if(count <= 0)
			return;
		lastWasBreak = true;
		numberOfLines += count;
		maxWidth = Math.max(maxWidth, currentLineWidth);
		lastLineWidth = currentLineWidth == 0
				? lastLineWidth
				: currentLineWidth;
		currentLineWidth = 0;
	}

	@Override
	public void changeIndentation(int count) {
		if(count == 0)
			return;
		indent += count * indentSize;
		indent = Math.max(0, indent);
	}

	/**
	 * Measures the text line.
	 * 
	 * @param s
	 */
	protected void doTextLine(String s) {
		currentLineWidth += s.length();
	}

	private void emit(int count) {
		if(lastWasBreak) {
			lastWasBreak = false;
			currentLineWidth += indent;
		}
		currentLineWidth += Math.max(0, count);
	}

	protected void emit(String s) {
		if(lastWasBreak) {
			lastWasBreak = false;
			currentLineWidth += indent;
		}
		currentLineWidth += s.length();
	}

	@Override
	public boolean endsWithBreak() {
		return lastWasBreak;
	}

	@Override
	public int getHeight() {
		return numberOfLines;
	}

	@Override
	public int getIndentation() {
		return indent / indentSize;
	}

	@Override
	public int getWidth() {
		if(lastWasBreak)
			return maxWidth;
		return Math.max(maxWidth, currentLineWidth);
	}

	@Override
	public int getWidthOfLastLine() {
		return lastLineWidth;
	}

	@Override
	public boolean isEmpty() {
		return getWidth() == 0;
	}

	@Override
	public void oneSpace() {
		spaces(1);
	}

	protected void processTextLines(String s) {
		List<String> lines = Strings.split(s, lineSeparator);
		int sz = lines.size();
		for(int i = 0; i < sz - 1; i++) {
			doTextLine(lines.get(i));
			breaks(1);
		}
		// last line (may be terminated with lineseparator)
		if(sz > 0) {
			String line = lines.get(sz - 1);
			doTextLine(line);
			if(line.endsWith(lineSeparator))
				breaks(1);
		}

	}

	@Override
	public void setIndentation(int count) {
		indent = Math.max(0, count * indentSize);
	}

	@Override
	public void spaces(int count) {
		emit(count);
	}

	/**
	 * Breaks lines into separate lines and calls a sequence of {@link #doTextLine(String)} and {@link #oneBreak(int)}.
	 * A derived class should not override this method, and instead override {@link #doTextLine(String)}.
	 */
	@Override
	public void text(String s) {
		processTextLines(s);
	}
}
