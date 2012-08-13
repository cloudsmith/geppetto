/**
 * Copyright (c) 2011, 2012 Cloudsmith Inc. and other contributors, as listed below.
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cloudsmith.geppetto.pp.dsl.ui.linked.ISaveActions;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;

/**
 * Implementation of save actions
 * 
 */
public class SaveActions implements ISaveActions {

	// case '\u00A0': // NBSP
	// case '\u1680': // OGHAM SPACE MARK");
	// case '\u2000': // EN QUAD");
	// case '\u2001': // EM QUAD");
	// case '\u2002': // EN SPACE");
	// case '\u2003': // EM SPACE");
	// case '\u2004': // THREE-PER-EM SPACE");
	// case '\u2005': // FOUR-PER-EM SPACE");
	// case '\u2006': // SIX-PER-EM SPACE");
	// case '\u2007': // FIGURE SPACE");
	// case '\u2008': // PUNCTUATION SPACE");
	// case '\u2009': // THIN SPACE");
	// case '\u200A': // HAIR SPACE");
	// case '\u200B': // ZERO WIDTH SPACE");
	// case '\u202F': // NARROW NO-BREAK SPACE");
	// case '\u3000': // IDEOGRAPHIC SPACE");

	public static String funkySpaces = "\\u00A0\\u1680\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200A\\u200B\\u202F\\u3000";

	private static Pattern trimPattern = Pattern.compile("[ \\t\\f\\x0B" + funkySpaces + "]+(\\r\\n|\\n)");

	private static Pattern funkySpacePattern = Pattern.compile("[\\f\\x0B" + funkySpaces + "]");

	@Inject
	private PPPreferencesHelper preferenceHelper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.linked.ISaveActions#perform(org.eclipse.core.resources.IResource,
	 * org.eclipse.xtext.ui.editor.model.IXtextDocument)
	 */
	@Override
	public void perform(IResource r, final IXtextDocument document) {
		final boolean ensureNl = preferenceHelper.getSaveActionEnsureEndsWithNewLine(r);
		final boolean replaceFunkySpace = preferenceHelper.getSaveActionReplaceFunkySpaces(r);
		final boolean trimLines = preferenceHelper.getSaveActionTrimLines(r);

		if(ensureNl || replaceFunkySpace || trimLines) {
			document.modify(new IUnitOfWork.Void<XtextResource>() {

				@Override
				public void process(XtextResource state) throws Exception {
					// Do any semantic changes here

					String content = document.get();
					if(ensureNl)
						if(!content.endsWith("\n")) {
							content = content + "\n";
							try {
								document.replace(content.length() - 1, 0, "\n");
								content = document.get();
							}
							catch(BadLocationException e) {
								// ignore
							}
						}
					if(trimLines) {
						Matcher matcher = trimPattern.matcher(content);
						boolean mustRefetch = false;
						;
						int lengthAdjustment = 0;
						while(matcher.find()) {
							int offset = matcher.start();
							int length = matcher.end() - offset;
							try {
								String replacement = matcher.group(1);
								document.replace(offset - lengthAdjustment, length, replacement);
								lengthAdjustment += (length - replacement.length());
								mustRefetch = true;
							}
							catch(BadLocationException e) {
								// ignore
							}
						}
						if(mustRefetch)
							content = document.get();
					}
					if(replaceFunkySpace) {
						Matcher matcher = funkySpacePattern.matcher(content);
						int lengthAdjustment = 0;
						while(matcher.find()) {
							int offset = matcher.start();
							int length = matcher.end() - offset;
							try {
								document.replace(offset - lengthAdjustment, length, " ");
								lengthAdjustment += length - 1;
							}
							catch(BadLocationException e) {
								// ignore
							}
						}
					}
				}
			});

		}

	}
}
