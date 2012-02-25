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
package org.cloudsmith.geppetto.pp.dsl.ppformatting;

import java.io.IOException;
import java.io.Writer;

class FormStream implements IFormStream {
	public static class WriterFormStream extends FormStream {
		private Writer out;

		public WriterFormStream(Writer out) {
			this.out = out;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cloudsmith.geppetto.pp.dsl.formatting.FormStream#flush()
		 */
		@Override
		public void flush() throws IOException {
			out.write(getText());
		}
	}

	int indentSize = 2;

	int indent = 0;

	private StringBuilder builder;

	private boolean lastWasBreak;

	private final String spaceBuffer;

	private final String oneSpace;

	public FormStream() {
		this.indent = 0;
		this.builder = new StringBuilder();
		this.lastWasBreak = false;
		StringBuilder spaces = new StringBuilder(256);
		spaces.append("        ");
		while(spaces.length() < 256)
			spaces.append(spaces);
		spaceBuffer = spaces.toString();
		oneSpace = spaceStr(1);
	}

	/**
	 * break line, do not output indent until some other output takes place
	 * as it may be preceded by a dedent.
	 */
	@Override
	public void breakLine() {
		builder.append("\n");
		lastWasBreak = true;
	}

	@Override
	public void dedent() {
		indent = Math.max(0, indent - indentSize);
	}

	private void emit(String s) {
		if(lastWasBreak) {
			lastWasBreak = false;
			builder.append(spaceStr(indent));
		}
		builder.append(s);
	}

	@Override
	public void flush() throws IOException {

	}

	@Override
	public String getText() {
		return builder.toString();
	}

	@Override
	public void indent() {
		indent += indentSize;
	}

	@Override
	public void noSpace() {

	}

	/**
	 * one space, SL comment, or space MLComment space ( 1 MLCMNT + 2 )
	 */
	@Override
	public void oneSpace() {
		emit(oneSpace);
	}

	@Override
	public int size() {
		return builder.length();
	}

	/**
	 * count space, or SL comment, or max(ML.length, count)
	 * 
	 * @param count
	 */
	@Override
	public void space(int count) {
		if(count < 0)
			return;
		while(count > 256) {
			emit(spaceStr(256));
			count -= 256;
		}
		if(count > 0)
			emit(spaceStr(count));
	}

	private String spaceStr(int size) {
		return spaceBuffer.substring(0, size);
	}

	@Override
	public void text(String s) {
		emit(s);
	}
}
