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

import org.cloudsmith.xtext.dommodel.formatter.IFormattingContext;

import com.google.inject.Inject;

/**
 * A text flow that measures the appended content but that does not contain the actual text.
 * 
 */
public class MeasuredTextFlow extends AbstractTextFlow implements ITextFlow.Measuring {

	private boolean lastWasBreak;

	private boolean lastWasSpace;

	private int numberOfBreaks;

	private int currentLineWidth;

	private int maxWidth;

	private int lastLineWidth;

	private int lastUsedIndent;

	@Inject
	public MeasuredTextFlow(IFormattingContext formattingContext) {
		super(formattingContext);
		this.lastWasBreak = false;
		this.lastWasSpace = false;

		numberOfBreaks = 0;
		currentLineWidth = 0;
		maxWidth = 0;
	}

	@Override
	public ITextFlow appendBreaks(int count) {
		if(count <= 0)
			return this;
		lastWasBreak = true;
		lastWasSpace = true;
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
		lastWasSpace = true;
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
		lastWasSpace = false;
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
	protected void processTextLine(CharSequence s) {
		if(shouldLineBeWrapped(s)) {
			// wrap indent, output text, restore indent
			changeIndentation(getWrapIndentation());
			appendBreak();
			super.processTextLine(s);
			changeIndentation(-getWrapIndentation());
		}
		else
			super.processTextLine(s);

	}

	@Override
	public ITextFlow setIndentation(int count) {
		indent = Math.max(0, count * indentSize);
		return this;
	}

	/**
	 * Returns true if the text would cause text to be wider that the preferred max width, and placing
	 * it on the next line with the current indent would either make it fit or cause less overrun.
	 * Otherwise false is returned.
	 * 
	 * @param s
	 * @return true if the given characters should be placed on the next line
	 */
	protected boolean shouldLineBeWrapped(CharSequence s) {
		final int textLength = s.length();
		final int pos = endsWithBreak()
				? indent
				: getWidthOfLastLine();
		int unwrappedWidth = textLength + pos;
		if(unwrappedWidth > getPreferredMaxWidth()) {
			if(!(lastWasBreak || lastWasSpace))
				return false; // not allowed to wrap
			int wrappedWidth = textLength + indent + getWrapIndentation();
			return wrappedWidth < unwrappedWidth;
		}
		return false;
	}
}
