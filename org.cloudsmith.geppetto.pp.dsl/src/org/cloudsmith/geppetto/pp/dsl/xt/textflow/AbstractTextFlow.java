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
package org.cloudsmith.geppetto.pp.dsl.xt.textflow;

import java.io.IOException;
import java.util.List;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.IFormattingContext;

import com.google.inject.Inject;
import com.google.inject.internal.Lists;

/**
 * An abstract implementation of an ITextFlow.
 * 
 */
public abstract class AbstractTextFlow implements ITextFlow {
	protected static boolean endsWith(CharSequence value, String end) {
		if(value instanceof String)
			return ((String) value).endsWith(end);
		int sz = value.length();
		int szEnd = end.length();
		if(value.length() < end.length())
			return false;
		return value.subSequence(sz - szEnd - 1, sz).equals(end);
	}

	protected static int indexOf(CharSequence value, String delimiter, int from) {
		if(value instanceof String)
			return ((String) value).indexOf(delimiter, from);
		else if(value instanceof StringBuilder)
			return ((StringBuilder) value).indexOf(delimiter, from);
		return value.toString().indexOf(delimiter, from);

	}

	/**
	 * @param value
	 * @param delimiter
	 * @return
	 */
	protected static List<CharSequence> split(CharSequence value, String delimiter) {
		List<CharSequence> result = Lists.newArrayList();
		int lastIndex = 0;
		int index = indexOf(value, delimiter, lastIndex);
		while(index != -1) {
			result.add(value.subSequence(lastIndex, index));
			lastIndex = index + delimiter.length();
			index = indexOf(value, delimiter, lastIndex);
		}
		result.add(value.subSequence(lastIndex, value.length()));
		return result;
	}

	protected int indent;

	protected final String lineSeparator;

	protected final int indentSize;

	protected final char indentChar;

	@Inject
	protected AbstractTextFlow(IFormattingContext formattingContext) {
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
	public abstract ITextFlow appendBreaks(int count);

	@Override
	public ITextFlow appendSpace() {
		return appendSpaces(1);
	}

	@Override
	public abstract ITextFlow appendSpaces(int count);

	/**
	 * Breaks lines into separate lines and calls a sequence of {@link #doTextLine(String)} and {@link #oneBreak(int)}.
	 * A derived class should not override this method, and instead override {@link #doTextLine(String)}.
	 */
	@Override
	public ITextFlow appendText(CharSequence s) {
		processTextLines(s);
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

	protected abstract void doTextLine(CharSequence s);

	@Override
	public int getIndentation() {
		return indent / indentSize;
	}

	protected String getLineSeparator() {
		return lineSeparator;
	}

	protected void processTextLines(CharSequence s) {
		List<CharSequence> lines = split(s, lineSeparator);
		int sz = lines.size();
		for(int i = 0; i < sz - 1; i++) {
			doTextLine(lines.get(i));
			appendBreaks(1);
		}
		// last line (may be terminated with line separator)
		if(sz > 0) {
			CharSequence line = lines.get(sz - 1);
			doTextLine(line);
			if(endsWith(line, lineSeparator))
				appendBreaks(1);
		}

	}

	@Override
	public ITextFlow setIndentation(int count) {
		indent = Math.max(0, count * indentSize);
		return this;

	}

}
