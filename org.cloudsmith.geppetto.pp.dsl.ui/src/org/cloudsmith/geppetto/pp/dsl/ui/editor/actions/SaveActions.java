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
package org.cloudsmith.geppetto.pp.dsl.ui.editor.actions;

import java.util.regex.Pattern;

import org.cloudsmith.geppetto.pp.dsl.ui.linked.ISaveActions;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;

import com.google.inject.Inject;

/**
 * Implementation of save actions
 * 
 */
public class SaveActions implements ISaveActions {
	private static Pattern trimPattern = Pattern.compile("[ \\t\\f\\x0B\\u00A0]+(\\r\\n|\\n)");

	private static Pattern funkySpacePattern = Pattern.compile("[\\f\\x0B\\u00A0]");

	@Inject
	private PPPreferencesHelper preferenceHelper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.linked.ISaveActions#perform(org.eclipse.core.resources.IResource,
	 * org.eclipse.xtext.ui.editor.model.IXtextDocument)
	 */
	@Override
	public void perform(IResource r, IXtextDocument document) {
		boolean ensureNl = preferenceHelper.getSaveActionEnsureEndsWithNewLine(r);
		boolean replaceFunkySpace = preferenceHelper.getSaveActionReplaceFunkySpaces(r);
		boolean trimLines = preferenceHelper.getSaveActionTrimLines(r);

		if(ensureNl || replaceFunkySpace || trimLines) {
			String content = document.get();
			if(ensureNl)
				if(!content.endsWith("\n"))
					content = content + "\n";
			if(trimLines)
				content = trimPattern.matcher(content).replaceAll("$1");
			// content = content.replaceAll("[ \\t\\f\\x0B\\u00A0]+(\\r\\n|\\n)", "$1");
			if(replaceFunkySpace)
				content = funkySpacePattern.matcher(content).replaceAll(" ");
			// content = content.replaceAll("[\\f\\x0B\\u00A0]", " ");
			try {
				document.replace(0, document.getLength(), content);
			}
			catch(BadLocationException e1) {
				// ignore, can't happen.
			}
		}
		// // USE THIS IF SEMANTIC CHANGES ARE NEEDED LATER
		// document.modify(new IUnitOfWork.Void<XtextResource>() {
		//
		// @Override
		// public void process(XtextResource state) throws Exception {
		// // Do any semantic changes here
		// }
		// });

	}
}
