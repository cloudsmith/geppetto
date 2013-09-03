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
package com.puppetlabs.geppetto.pp.dsl.ui.quickfix;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;

class SurroundWithTextModification extends ReplacingModification {
	private final String suffix;

	/**
	 * @param offset
	 * @param length
	 * @param text
	 *            text used before and after the replaced text
	 */
	SurroundWithTextModification(int offset, int length, String text) {
		super(offset, length, text);
		suffix = text;
	}

	/**
	 * Surrounds text with prefix, suffix
	 * 
	 * @param offset
	 *            start of section to surround
	 * @param length
	 *            length of section to surround
	 * @param prefix
	 *            text before the section
	 * @param suffix
	 *            text after the section
	 */
	SurroundWithTextModification(int offset, int length, String prefix, String suffix) {
		super(offset, length, prefix);
		this.suffix = suffix;
	}

	@Override
	public void apply(IModificationContext context) throws BadLocationException {
		IXtextDocument xtextDocument = context.getXtextDocument();
		String tmp = text + xtextDocument.get(offset, length) + suffix;
		xtextDocument.replace(offset, length, tmp);
	}

}
