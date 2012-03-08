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

import com.google.inject.Inject;
import com.google.inject.internal.Lists;

public class RecordingMeasuringTextStream extends MeasuringTextStream implements IRecordingStream {

	private static class BreakCount implements TextBite {
		int count;

		BreakCount(int count) {
			this.count = count;
		}

		public void visit(IFormStream stream) {
			stream.breaks(count);
		}
	}

	private static class ChangeIndentation implements TextBite {
		int change;

		ChangeIndentation(int change) {
			this.change = change;
		}

		public void visit(IFormStream stream) {
			stream.changeIndentation(change);
		}
	}

	private static class SpaceCount implements TextBite {
		int count;

		SpaceCount(int count) {
			this.count = count;
		}

		public void visit(IFormStream stream) {
			stream.spaces(count);
		}
	}

	private static class Text implements TextBite {
		String text;

		Text(String s) {
			this.text = s;
		}

		public void visit(IFormStream stream) {
			stream.text(text);
		}
	}

	private interface TextBite {
		public void visit(IFormStream stream);
	}

	private List<TextBite> tape = Lists.newArrayList();

	@Inject
	public RecordingMeasuringTextStream(IFormattingContext formattingContext) {
		super(formattingContext);
	}

	@Override
	public void appendTo(IFormStream output) {
		for(TextBite tb : tape)
			tb.visit(output);
	}

	@Override
	public void breaks(int count) {
		super.breaks(count);
		tape.add(new BreakCount(count));
	}

	@Override
	public void changeIndentation(int count) {
		super.changeIndentation(count);
		if(count == 0)
			return;
		indent += count * indentSize;
		indent = Math.max(0, indent);
		tape.add(new ChangeIndentation(count));
	}

	@Override
	protected void doTextLine(String s) {
		super.doTextLine(s);
		tape.add(new Text(s));

	}

	@Override
	public void setIndentation(int count) {
		indent = Math.max(0, count * indentSize);

	}

	@Override
	public void spaces(int count) {
		super.spaces(count);
		if(count <= 0)
			return;
		tape.add(new SpaceCount(count));
	}
}
