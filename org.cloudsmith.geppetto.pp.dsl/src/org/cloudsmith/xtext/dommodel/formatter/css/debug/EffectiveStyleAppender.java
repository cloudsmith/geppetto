/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.xtext.dommodel.formatter.css.debug;

import java.io.IOException;

import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager;
import org.cloudsmith.xtext.dommodel.formatter.css.Alignment;
import org.cloudsmith.xtext.dommodel.formatter.css.IStyleVisitor;
import org.cloudsmith.xtext.dommodel.formatter.css.LineBreaks;
import org.cloudsmith.xtext.dommodel.formatter.css.Spacing;

/**
 * @author henrik
 * 
 */
public class EffectiveStyleAppender implements IStyleVisitor {

	public static String encodedString(String input) {
		if(input == null)
			return null;
		input = input.replace("\n", "\\n");
		input = input.replace("\t", "\\t");
		input = input.replace("\r", "\\r");
		return input;

	}

	private Appendable out;

	public EffectiveStyleAppender(Appendable out) {
		this.out = out;
	}

	@Override
	public void align(Alignment x) {
		append("Alignment(");
		append(x.toString());
		append(")");
	}

	private EffectiveStyleAppender append(String s) {
		try {
			out.append(s);
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	@Override
	public void dedent(int count) {
		append("Dedent(");
		append(Integer.toString(count));
		append(")");
	}

	@Override
	public void indent(int count) {
		append("Indent(");
		append(Integer.toString(count));
		append(")");
	}

	@Override
	public void layout(ILayoutManager value) {
		append("Layout(");
		append(value.getClass().getSimpleName());
		append(")");
	}

	@Override
	public void lineBreaks(LineBreaks lineBreakInfo) {
		append(lineBreakInfo.toString());
	}

	@Override
	public void spacing(Spacing value) {
		append(value.toString());
	}

	@Override
	public void styleName(String name) {
		append("Name(");
		append(name);
		append(")");
	}

	@Override
	public void tokenText(String text) {
		// skip when text is null (composites)
		if(text == null)
			return;
		append("Text(\"");
		append(encodedString(text));
		append("\")");

	}

	@Override
	public void verbatim(Boolean value) {
		append("Verbatim(");
		append(value.toString());
		append(")");
	}

	@Override
	public void width(Integer value) {
		append("Width(");
		append(value.toString());
		append(")");
	}

}
