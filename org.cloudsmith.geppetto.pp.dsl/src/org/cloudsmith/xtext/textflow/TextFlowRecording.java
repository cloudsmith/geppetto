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
public class TextFlowRecording extends MeasuredTextFlow implements ITextFlow.Recording {

	private static class BreakCount implements TextBite {
		int count;

		BreakCount(int count) {
			this.count = count;
		}

		public void visit(ITextFlow stream) {
			stream.appendBreaks(count);
		}
	}

	private static class ChangeIndentation implements TextBite {
		int change;

		ChangeIndentation(int change) {
			this.change = change;
		}

		public void visit(ITextFlow stream) {
			stream.changeIndentation(change);
		}
	}

	private static class Indentation implements TextBite {
		int count;

		Indentation(int count) {
			this.count = count;
		}

		public void visit(ITextFlow stream) {
			stream.setIndentation(count);
		}
	}

	private static class SpaceCount implements TextBite {
		int count;

		SpaceCount(int count) {
			this.count = count;
		}

		public void visit(ITextFlow stream) {
			stream.appendSpaces(count);
		}
	}

	private static class Text implements TextBite {
		CharSequence text;

		Text(CharSequence s) {
			this.text = s;
		}

		public void visit(ITextFlow stream) {
			stream.appendText(text);
		}
	}

	private interface TextBite {
		public void visit(ITextFlow stream);
	}

	private List<TextBite> tape = Lists.newArrayList();

	@Inject
	public TextFlowRecording(IFormattingContext formattingContext) {
		super(formattingContext);
	}

	@Override
	public ITextFlow appendBreaks(int count, boolean verbatim) {
		tape.add(new BreakCount(count));
		return super.appendBreaks(count, verbatim);
	}

	@Override
	public ITextFlow appendSpaces(int count) {
		tape.add(new SpaceCount(count));
		return super.appendSpaces(count);
	}

	@Override
	public void appendTo(ITextFlow output) {
		for(TextBite tb : tape)
			tb.visit(output);
	}

	@Override
	public ITextFlow changeIndentation(int count) {
		tape.add(new ChangeIndentation(count));
		return super.changeIndentation(count);
	}

	@Override
	protected void doText(CharSequence s, boolean verbatim) {
		super.doText(s, verbatim);
		tape.add(new Text(s));

	}

	@Override
	public ITextFlow setIndentation(int count) {
		tape.add(new Indentation(count));
		return super.setIndentation(count);

	}
}
