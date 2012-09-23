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

import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContext;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.xtext.util.Exceptions;

import com.google.inject.Inject;

/**
 * <p>
 * An implementation of {@link ITextFlow.WithText} that appends its content to an {@link Appendable} (by default an internal {@link StringBuilder}
 * instance).
 * </p>
 * 
 * <p>
 * An {@link IOException} thrown by an {@link Appendable} is rethrown as a runtime {@link WrappedException}.
 * </p>
 * 
 */
public class TextFlow extends MeasuredTextFlow implements ITextFlow.WithText {

	private Appendable builder;

	private boolean lastWasBreak;

	private int length;

	private final static String oneSpace = " ";

	public TextFlow(Appendable output, IFormattingContext formattingContext) {
		super(formattingContext);
		length = 0;
		if(output == null)
			this.builder = new StringBuilder();
		else
			this.builder = output;
		this.lastWasBreak = false;
	}

	@Inject
	public TextFlow(IFormattingContext formattingContext) {
		this(null, formattingContext);
	}

	@Override
	public ITextFlow appendBreaks(int count, boolean verbatim) {
		super.appendBreaks(count, verbatim);
		if(count <= 0)
			return this;
		internal_append(count == 1
				? getLineSeparator()
				: new CharSequences.RepeatingString(getLineSeparator(), count));
		lastWasBreak = true;
		return this;
	}

	@Override
	public ITextFlow appendSpaces(int count) {
		super.appendSpaces(count);
		if(count <= 0)
			return this;
		emit(count == 1
				? oneSpace
				: new CharSequences.Spaces(count));

		return this;
	}

	/**
	 * This implementation emits the text to the {@link Appendable} given to the constructor (or an internal buffer if
	 * unspecified). Keeps metrics up to date.
	 */
	@Override
	protected void doText(CharSequence s, boolean verbatim) {
		super.doText(s, verbatim);
		emit(s);
	}

	private void emit(CharSequence s) {
		if(s == null || s.length() == 0)
			return; // do not change state on empty output
		if(lastWasBreak) {
			lastWasBreak = false;
			internal_append(getPendingIndent() == 0
					? ""
					: new CharSequences.Fixed(indentChar, getPendingIndent()));
		}
		internal_append(s);
	}

	@Override
	public CharSequence getText() {
		CharSequence a = (builder instanceof CharSequence)
				? (CharSequence) builder
				: builder.toString();
		// include the not yet fully processed text by adding indent and current run
		CharSequence b = getCurrentRun();
		if(lastWasBreak && b.length() > 0)
			a = CharSequences.concatenate(a, new CharSequences.Fixed(indentChar, getPendingIndent()));
		return CharSequences.concatenate(a, getCurrentRun());
	}

	private void internal_append(CharSequence s) {
		// keep track of how much was appended
		length += s.length();
		try {
			builder.append(s);
		}
		catch(IOException e) {
			Exceptions.throwUncheckedException(e);
		}
	}

	@Override
	public void setIndentFirstLine(boolean flag) {
		super.setIndentFirstLine(flag);
		// if not empty, just remembers the flag
		if(!isEmpty())
			return;

		lastWasBreak = flag; // fake a break, or clear
	}

	@Override
	public int size() {
		return length;
	}

}
