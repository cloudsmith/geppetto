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

import java.io.IOException;
import java.util.List;

import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContext;

import com.google.inject.Inject;

/**
 * An abstract implementation of an ITextFlow.
 * 
 */
public abstract class AbstractTextFlow implements ITextFlow {

	protected int indent;

	protected final String lineSeparator;

	protected final int indentSize;

	protected final char indentChar;

	protected int wrapIndentSize;

	protected int preferredMaxWidth;

	/**
	 * Copy constructor
	 * 
	 * @param original
	 */
	protected AbstractTextFlow(AbstractTextFlow original) {
		this.indent = original.indent;
		this.lineSeparator = original.lineSeparator;
		this.indentChar = original.indentChar;
		this.indentSize = original.indentSize;
		this.wrapIndentSize = original.wrapIndentSize;
		this.preferredMaxWidth = original.preferredMaxWidth;
	}

	@Inject
	protected AbstractTextFlow(IFormattingContext formattingContext) {
		this.preferredMaxWidth = formattingContext.getPreferredMaxWidth();
		this.wrapIndentSize = formattingContext.getWrapIndentSize();
		lineSeparator = formattingContext.getLineSeparatorInformation().getLineSeparator();
		String indentationString = formattingContext.getIndentationInformation().getIndentString();

		indentSize = indentationString.length();
		indentChar = indentSize == 0
				? ' '
				: indentationString.charAt(0);
		if(indentSize > 0) {
			for(int i = 0; i < indentSize; i++)
				if(indentationString.charAt(i) != indentChar)
					throw new IllegalStateException("Indentation string must consist of the same character");
		}

	}

	@Override
	public Appendable append(char c) throws IOException {
		return appendText(String.valueOf(c));
	}

	@Override
	public Appendable append(CharSequence csq) throws IOException {
		return appendText(csq);
	}

	@Override
	public Appendable append(CharSequence csq, int start, int end) throws IOException {
		return appendText(csq.subSequence(start, end));
	}

	@Override
	public ITextFlow appendBreak() {
		return appendBreaks(1);
	}

	@Override
	public ITextFlow appendBreaks(int count) {
		appendBreaks(count, false);
		return this;
	}

	@Override
	public abstract ITextFlow appendBreaks(int count, boolean verbatim);

	@Override
	public ITextFlow appendSpace() {
		return appendSpaces(1);
	}

	@Override
	public abstract ITextFlow appendSpaces(int count);

	/**
	 * Breaks lines into separate lines and calls a sequence of {@link #doText(String)} and {@link #oneBreak(int)}.
	 * A derived class should not override this method, and instead override {@link #doText(String)} to perform the emit of
	 * the actual text, or {@link #processTextSequence(CharSequence)} to do processing before emit (wrapping, etc).
	 */
	@Override
	public ITextFlow appendText(CharSequence s) {
		return appendText(s, false);
	}

	@Override
	public ITextFlow appendText(CharSequence s, boolean verbatim) {
		processEmbeddedLinebreaks(s, verbatim);
		return this;
	}

	@Override
	public ITextFlow changeIndentation(int count) {

		if(count == 0)
			return this;
		indent += count * indentSize;
		indent = Math.max(0, indent);
		return this;
	}

	protected abstract void doText(CharSequence s, boolean verbatim);

	@Override
	public abstract ITextFlow ensureBreaks(int count);

	@Override
	public int getIndentation() {
		return indent / indentSize;
	}

	@Override
	public int getIndentSize() {
		return indentSize;
	}

	protected String getLineSeparator() {
		return lineSeparator;
	}

	@Override
	public int getPreferredMaxWidth() {
		return preferredMaxWidth;
	}

	@Override
	public int getWrapIndentation() {
		return wrapIndentSize;
	}

	/**
	 * Process (and output) given sequence for embedded line breaks (must be counted and subject to indentation).
	 * Results in a series of calls to {@link #processTextSequence(CharSequence)} and (if there are
	 * any embedded breaks) {@link #appendBreaks(1)}.
	 * 
	 * @param s
	 */
	protected void processEmbeddedLinebreaks(CharSequence s, boolean verbatim) {
		List<CharSequence> lines = CharSequences.split(s, lineSeparator);
		int sz = lines.size();
		for(int i = 0; i < sz - 1; i++) {
			processTextSequence(lines.get(i), verbatim);
			appendBreaks(1, verbatim);
		}
		// last line (may be terminated with line separator)
		if(sz > 0) {
			CharSequence line = lines.get(sz - 1);
			processTextSequence(line, verbatim);
			if(CharSequences.endsWith(line, lineSeparator))
				appendBreaks(1, verbatim);
		}

	}

	/**
	 * This default implementation does nothing but call {@link #doText(CharSequence)} - a derived implementation
	 * may buffer, perform auto line wrapping etc.
	 * 
	 * @param s
	 */
	protected void processTextSequence(CharSequence s, boolean verbatim) {
		doText(s, verbatim);
	}

	@Override
	public ITextFlow setIndentation(int count) {
		indent = Math.max(0, count * indentSize);
		return this;

	}

	@Override
	public void setPreferredMaxWidth(int preferredMaxWidth) {
		this.preferredMaxWidth = preferredMaxWidth;
	};

	public void setWrapIndentation(int count) {
		wrapIndentSize = count;
	}
}
