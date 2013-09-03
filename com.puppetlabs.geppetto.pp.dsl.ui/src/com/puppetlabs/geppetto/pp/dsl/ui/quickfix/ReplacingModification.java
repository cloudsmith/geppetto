/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package org.cloudsmith.geppetto.pp.dsl.ui.quickfix;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;

class ReplacingModification implements IModification {

	final protected int length;

	final protected int offset;

	final protected String text;

	final protected boolean handleQuotes;

	ReplacingModification(int offset, int length, String text) {
		this(offset, length, text, false);
	}

	ReplacingModification(int offset, int length, String text, boolean handleQuotes) {
		this.length = length;
		this.offset = offset;
		this.text = text;
		this.handleQuotes = handleQuotes;
	}

	@Override
	public void apply(IModificationContext context) throws BadLocationException {
		IXtextDocument xtextDocument = context.getXtextDocument();
		int o = offset;
		int l = length;
		if(handleQuotes) {
			String s = xtextDocument.get(offset, length);
			char c = s.charAt(0);
			if(s.charAt(s.length() - 1) == c && (c == '\'' || c == '"')) {
				o++;
				l -= 2;
			}
		}
		xtextDocument.replace(o, l, text);
	}

}
