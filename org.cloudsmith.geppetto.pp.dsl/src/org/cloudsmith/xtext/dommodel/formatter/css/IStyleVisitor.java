/**
 * Copyright (c) 2006-2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.xtext.dommodel.formatter.css;

import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager;

/**
 * A visitor for IStyle
 * 
 */
public interface IStyleVisitor {

	public static class Default implements IStyleVisitor {

		@Override
		public void align(Alignment x) {
		}

		@Override
		public void dedent(int count) {
		}

		@Override
		public void indent(int count) {
		}

		@Override
		public void layout(ILayoutManager value) {
		}

		@Override
		public void lineBreaks(LineBreaks lineBreakInfo) {
		}

		@Override
		public void spacing(Spacing value) {
		}

		@Override
		public void styleName(String name) {
		}

		@Override
		public void tokenText(String text) {
		}

		@Override
		public void verbatim(Boolean value) {
			// TODO Auto-generated method stub

		}

		@Override
		public void width(Integer value) {
		}

	}

	public void align(Alignment x);

	public void dedent(int count);

	public void indent(int count);

	public void layout(ILayoutManager value);

	public void lineBreaks(LineBreaks lineBreakInfo);

	public void spacing(Spacing value);

	public void styleName(String name);

	public void tokenText(String text);

	public void verbatim(Boolean value);

	public void width(Integer value);

}
