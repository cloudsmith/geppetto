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

import java.util.List;

import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContext;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * An implementation of ITextFlow that records and measures the appended content.
 * 
 */
public class TextFlowWithDebugRecording extends TextFlow {

	private static class BreakCount implements TextBite {
		int count;

		boolean verbatim;

		BreakCount(int count, boolean verbatim) {
			this.count = count;
			this.verbatim = verbatim;
		}

		public void visit(StringBuilder stream) {
			stream.append("Break(").append(count).append(", ").append(verbatim).append(")\n");
		}
	}

	private static class ChangeIndentation implements TextBite {
		int change;

		ChangeIndentation(int change) {
			this.change = change;
		}

		public void visit(StringBuilder stream) {
			stream.append("ChangeIndentation(").append(change).append(")\n");
		}
	}

	private static class Indentation implements TextBite {
		int count;

		Indentation(int count) {
			this.count = count;
		}

		public void visit(StringBuilder stream) {
			stream.append("Indent(").append(count).append(")\n");
		}
	}

	private static class Log implements TextBite {
		String msg;

		Log(String s) {
			msg = s;
		}

		public void visit(StringBuilder stream) {
			stream.append(msg);
		}
	}

	private static class SpaceCount implements TextBite {
		int count;

		SpaceCount(int count) {
			this.count = count;
		}

		public void visit(StringBuilder stream) {
			stream.append("Spaces(").append(count).append(")\n");
		}

	}

	private static class Text implements TextBite {
		CharSequence text;

		boolean verbatim;

		Text(CharSequence s, boolean verbatim) {
			this.text = s;
			this.verbatim = verbatim;
		}

		public void visit(StringBuilder stream) {
			stream.append("Text('").append(text).append("', ").append(verbatim).append(")\n");
		}
	}

	private interface TextBite {
		public void visit(StringBuilder stream);
	}

	private List<TextBite> tape = Lists.newArrayList();

	@Inject
	public TextFlowWithDebugRecording(IFormattingContext formattingContext) {
		super(formattingContext);
	}

	@Override
	public ITextFlow appendBreaks(int count, boolean verbatim) {
		tape.add(new BreakCount(count, verbatim));
		return super.appendBreaks(count, verbatim);
	}

	@Override
	public ITextFlow appendSpaces(int count) {
		tape.add(new SpaceCount(count));
		return super.appendSpaces(count);
	}

	public void appendTo(StringBuilder output) {
		for(TextBite tb : tape)
			tb.visit(output);
	}

	@Override
	public ITextFlow changeIndentation(int count) {
		if(count == 0)
			return this;
		tape.add(new ChangeIndentation(count));
		return super.changeIndentation(count);
	}

	@Override
	protected void doText(CharSequence s, boolean verbatim) {
		tape.add(new Text(s, verbatim));
		super.doText(s, verbatim);
	}

	@Override
	public ITextFlow setIndentation(int count) {
		tape.add(new Indentation(count));
		return super.setIndentation(count);

	}
}
