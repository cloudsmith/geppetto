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

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.IFormattingContext;

import com.google.inject.Inject;

/**
 * A text flow that measures the appended content but that does not contain the actual text.
 * 
 */
public class MeasuredTextFlow extends AbstractTextFlow implements ITextFlow.IMetrics {

	private boolean lastWasBreak;

	private int numberOfBreaks;

	private int currentLineWidth;

	private int maxWidth;

	private int lastLineWidth;

	private int lastUsedIndent;

	@Inject
	public MeasuredTextFlow(IFormattingContext formattingContext) {
		super(formattingContext);
		this.lastWasBreak = false;

		numberOfBreaks = 0;
		currentLineWidth = 0;
		maxWidth = 0;
	}

	@Override
	public ITextFlow appendBreaks(int count) {
		if(count <= 0)
			return this;
		lastWasBreak = true;
		numberOfBreaks += count;
		maxWidth = Math.max(maxWidth, currentLineWidth);
		lastLineWidth = currentLineWidth == 0
				? lastLineWidth
				: currentLineWidth;
		currentLineWidth = 0;
		return this;
	}

	@Override
	public ITextFlow appendSpaces(int count) {
		emit(count);
		return this;
	}

	/**
	 * Measures the text line.
	 * 
	 * @param s
	 */
	@Override
	protected void doTextLine(CharSequence s) {
		emit(s.length());
	}

	private void emit(int count) {
		if(lastWasBreak) {
			lastWasBreak = false;
			lastUsedIndent = indent;
			currentLineWidth += indent;
		}
		currentLineWidth += Math.max(0, count);
	}

	@Override
	public boolean endsWithBreak() {
		return lastWasBreak;
	}

	@Override
	public int getHeight() {
		if(isEmpty())
			return 0;
		if(lastWasBreak)
			return numberOfBreaks;
		return numberOfBreaks + 1;
	}

	@Override
	public int getLastUsedIndentation() {
		return lastUsedIndent / indentSize;
	}

	@Override
	public int getWidth() {
		if(lastWasBreak)
			return maxWidth;
		return Math.max(maxWidth, currentLineWidth);
	}

	@Override
	public int getWidthOfLastLine() {
		return lastWasBreak
				? lastLineWidth
				: currentLineWidth;
	}

	@Override
	public boolean isEmpty() {
		return getWidth() == 0 && numberOfBreaks == 0;
	}

	@Override
	public ITextFlow setIndentation(int count) {
		indent = Math.max(0, count * indentSize);
		return this;
	}
}
