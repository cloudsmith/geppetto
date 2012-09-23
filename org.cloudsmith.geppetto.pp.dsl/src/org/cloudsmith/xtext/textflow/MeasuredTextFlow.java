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

import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContext;

import com.google.inject.Inject;

/**
 * A text flow that measures the appended content but that does not contain the actual text.
 * 
 */
public class MeasuredTextFlow extends AbstractTextFlow {

	private int lastWasBreak;

	private boolean lastWasSpace;

	private int numberOfBreaks;

	private int currentLineWidth;

	private int maxWidth;

	private int lastLineWidth;

	private int lastUsedIndent;

	private CharSequence currentRun;

	private int pendingIndent;

	private boolean indentFirstLine;

	private int indentAtRunStart;

	@Inject
	public MeasuredTextFlow(IFormattingContext formattingContext) {
		super(formattingContext);
		this.lastWasBreak = 0; // false;
		this.lastWasSpace = false;

		numberOfBreaks = 0;
		currentLineWidth = 0;
		maxWidth = 0;
		pendingIndent = 0;
		indentAtRunStart = 0;
	}

	/**
	 * Copy constructor
	 * 
	 * @param original
	 */
	public MeasuredTextFlow(MeasuredTextFlow original) {
		super(original);
		this.lastWasBreak = original.lastWasBreak;
		this.lastWasSpace = original.lastWasSpace;
		this.numberOfBreaks = original.numberOfBreaks;
		this.currentLineWidth = original.currentLineWidth;
		this.lastUsedIndent = original.lastUsedIndent;
		this.currentRun = original.currentRun;
		this.pendingIndent = original.pendingIndent;
		this.indentFirstLine = original.indentFirstLine;
		this.indentAtRunStart = original.indentAtRunStart;
	}

	@Override
	public ITextFlow appendBreaks(int count, boolean verbatim) {
		if(currentRun != null) {
			doText(currentRun, verbatim);
			currentRun = null;
			indentAtRunStart = this.getIndentation();
		}
		lastWasSpace = true;
		if(count <= 0)
			return this;
		lastWasBreak += count; // = true;
		numberOfBreaks += count;
		maxWidth = Math.max(maxWidth, currentLineWidth);
		lastLineWidth = currentLineWidth == 0
				? lastLineWidth
				: currentLineWidth;
		currentLineWidth = 0;

		// verbatim break simply means, no indentation
		pendingIndent = verbatim
				? 0
				: indent;
		return this;
	}

	@Override
	public ITextFlow appendSpaces(int count) {
		if(currentRun != null) {
			CharSequence run = currentRun;
			currentRun = null;
			doText(run, true);
			indentAtRunStart = this.getIndentation();
		}
		emit(count);
		lastWasSpace = true;
		return this;
	}

	/**
	 * Must be called from a derived method since this method performs measuring.
	 * 
	 * @param s
	 *            the text that will be emitted.
	 */
	@Override
	protected void doText(CharSequence s, boolean verbatim) {
		emit(s.length());
		if(s.length() > 0)
			lastWasSpace = false;
	}

	private void emit(int count) {
		if(count == 0)
			return; // do not change state
		if(lastWasBreak > 0) {
			lastWasBreak = 0; // false;
			lastUsedIndent = pendingIndent; // was indent, does not work when buffering
			currentLineWidth += pendingIndent; // was indent, does not work when buffering
		}
		currentLineWidth += Math.max(0, count);
	}

	@Override
	public boolean endsWithBreak() {
		return lastWasBreak > 0 && currentRun == null;
	}

	@Override
	public ITextFlow ensureBreaks(int count) {
		if(!endsWithBreak())
			return appendBreaks(count, false);

		int missingBreaks = Math.max(0, count - lastWasBreak);
		if(missingBreaks == 0)
			pendingIndent = indent;
		else
			return appendBreaks(missingBreaks, false);

		return this;
	}

	@Override
	public int getAppendLinePosition() {
		if(lastWasBreak > 0)
			return pendingIndent + (currentRun == null
					? 0
					: currentRun.length());
		return currentLineWidth + getRunWidth();
	}

	protected CharSequence getCurrentRun() {
		return currentRun == null
				? ""
				: currentRun;
	}

	@Override
	public int getEndBreakCount() {
		// if there is pending text, then the lastWasBreak is pending and not at the end at all
		// report as 0
		return currentRun != null
				? 0
				: lastWasBreak;
	}

	@Override
	public int getHeight() {
		if(isEmpty())
			return 0;
		if(lastWasBreak > 0)
			return numberOfBreaks;
		return numberOfBreaks + 1;
	}

	@Override
	public int getLastUsedIndentation() {
		return lastUsedIndent / indentSize;
	}

	protected int getPendingIndent() {
		return pendingIndent;
	}

	private int getRunWidth() {
		return currentRun == null
				? 0
				: currentRun.length();
	}

	@Override
	public int getWidth() {
		// break only, or break + unprocessed
		if(lastWasBreak > 0)
			return Math.max(maxWidth, currentRun == null
					? 0
					: currentRun.length() + pendingIndent);

		// something else than break processed, but there can be unprocessed
		return Math.max(maxWidth, currentLineWidth + getRunWidth());
	}

	@Override
	public int getWidthOfLastLine() {
		if(lastWasBreak > 0)
			return currentRun == null
					? lastLineWidth
					: pendingIndent + currentRun.length();
		return currentLineWidth + getRunWidth();
	}

	@Override
	public boolean isEmpty() {
		return getWidth() == 0 && numberOfBreaks == 0;
	}

	@Override
	public boolean isIndentFirstLine() {
		return indentFirstLine;
	}

	/**
	 * This implementation buffers non-breakable sequences and performs auto line wrapping if <code>verbatim</code> is <code>false</code>.
	 * When output is <i>verbatim</i> pending output is flushed, and new output is immediately processed, and no
	 * automatic line wrapping will take place.
	 */
	@Override
	protected void processTextSequence(CharSequence s, boolean verbatim) {
		if(s == null || s.length() == 0)
			return; // no text, do nothing
		if(verbatim) {
			if(currentRun != null) {
				doText(currentRun, false); // if there was a current run, it is not verbatim
				currentRun = null;
				indentAtRunStart = this.getIndentation();
			}
			doText(s, true);
			return;
		}
		currentRun = CharSequences.concatenate(currentRun, s); // handles null

		if(shouldLineBeWrapped(currentRun)) {
			// wrap indent, output text, restore indent
			CharSequence processRun = currentRun;
			currentRun = null;
			int tmpIndentation = getIndentation();
			setIndentation(indentAtRunStart);
			// changeIndentation(getWrapIndentation());
			appendBreak();
			setIndentation(tmpIndentation);
			super.processTextSequence(processRun, verbatim);
			// changeIndentation(-getWrapIndentation());
		}
		// do nothing, just keep the currentRun
	}

	@Override
	public ITextFlow setIndentation(int count) {
		indent = Math.max(0, count * indentSize);
		return this;
	}

	@Override
	public void setIndentFirstLine(boolean flag) {
		indentFirstLine = flag;
		// if not empty, just remembers the flag
		if(!isEmpty())
			return;

		if(flag)
			pendingIndent = indent;
		lastWasBreak = 1; // fake a break
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
		final int pos = lastWasBreak > 0
				? pendingIndent // indent for preceding break
				: currentLineWidth;
		int unwrappedWidth = textLength + pos;
		if(unwrappedWidth > getPreferredMaxWidth()) {
			if(!(lastWasBreak > 0 || lastWasSpace))
				return false; // not allowed to wrap
			// note: use indent here, it is the indent for next break made
			int wrappedWidth = textLength + (indent + getWrapIndentation()) * indentSize;
			return wrappedWidth < unwrappedWidth;
		}
		return false;
	}
}
