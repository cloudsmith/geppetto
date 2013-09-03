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
package com.puppetlabs.geppetto.pp.dsl.ui.editor.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppetlabs.geppetto.pp.dsl.ui.linked.ISaveActions;
import com.puppetlabs.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import com.puppetlabs.geppetto.pp.dsl.validation.PPValidationUtils;
import com.puppetlabs.xtext.dommodel.IDomNode;
import com.puppetlabs.xtext.dommodel.formatter.IDomModelFormatter;
import com.puppetlabs.xtext.dommodel.formatter.context.IFormattingContextFactory;
import com.puppetlabs.xtext.dommodel.formatter.context.IFormattingContextFactory.FormattingOption;
import com.puppetlabs.xtext.resource.ResourceAccessScope;
import com.puppetlabs.xtext.serializer.DomBasedSerializer;
import com.puppetlabs.xtext.ui.editor.formatting.DummyReadOnly;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.reconciler.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;

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

	@Inject
	private ResourceAccessScope resourceScope;

	@Inject
	private Provider<IDomModelFormatter> formatterProvider;

	@Inject
	private Provider<DomBasedSerializer> serializerProvider;

	@Inject
	private Provider<IFormattingContextFactory> formattingContextProvider;

	protected IDomModelFormatter getFormatter() {
		// get via injector, as formatter is resource dependent
		// return injector.getInstance(IDomModelFormatter.class);
		return formatterProvider.get();
	}

	protected IFormattingContextFactory getFormattingContextFactory() {
		// get via injector, as formatting context may be resource dependent
		// return injector.getInstance(IFormattingContextFactory.class);
		return formattingContextProvider.get();
	}

	protected DomBasedSerializer getSerializer() {
		// get via injector, as formatting context as serialization uses the formatter
		// which is resource dependent
		// return injector.getInstance(DomBasedSerializer.class);
		return serializerProvider.get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.puppetlabs.geppetto.pp.dsl.ui.linked.ISaveActions#perform(org.eclipse.core.resources.IResource,
	 * org.eclipse.xtext.ui.editor.model.IXtextDocument)
	 */
	@Override
	public void perform(IResource r, final IXtextDocument document) {
		final boolean ensureNl = preferenceHelper.getSaveActionEnsureEndsWithNewLine(r);
		final boolean replaceFunkySpace = preferenceHelper.getSaveActionReplaceFunkySpaces(r);
		final boolean trimLines = preferenceHelper.getSaveActionTrimLines(r);
		final boolean fullFormat = preferenceHelper.getSaveActionFormat(r);

		if(ensureNl || replaceFunkySpace || trimLines || fullFormat) {
			// Xtext issue, a dummy read only is needed before all modify operations.
			document.readOnly(DummyReadOnly.Instance);

			document.modify(new IUnitOfWork<ReplaceRegion, XtextResource>() {

				@Override
				public ReplaceRegion exec(XtextResource state) throws Exception {
					// No action if there are hard syntax errors
					if(!PPValidationUtils.hasSyntaxErrors(state) && process(state))
						return new ReplaceRegion(0, document.getLength(), document.get());
					return null;
					// return new ReplaceRegion(0, 0, ""); // nothing changed
				}

				public boolean process(XtextResource state) throws Exception {
					// Do any semantic changes here
					boolean changed = false;
					String content = document.get();
					if(ensureNl)
						if(!content.endsWith("\n")) {
							content = content + "\n";
							try {
								document.replace(content.length() - 1, 0, "\n");
								content = document.get();
								changed = true;
							}
							catch(BadLocationException e) {
								// ignore
							}
						}
					if(trimLines) {
						Matcher matcher = trimPattern.matcher(content);
						boolean mustRefetch = false;

						int lengthAdjustment = 0;
						while(matcher.find()) {
							int offset = matcher.start();
							int length = matcher.end() - offset;
							try {
								String replacement = matcher.group(1);
								document.replace(offset - lengthAdjustment, length, replacement);
								lengthAdjustment += (length - replacement.length());
								mustRefetch = true;
								changed = true;
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
								changed = true;
							}
							catch(BadLocationException e) {
								// ignore
							}
						}
					}
					if(fullFormat) {
						// Most of this, and the required methods is a copy of ContentFormatterFactory - which
						// runs this in a separate UnitOfWork. TODO: Can be combined.
						//
						content = document.get();
						ISerializationDiagnostic.Acceptor errors = ISerializationDiagnostic.EXCEPTION_THROWING_ACCEPTOR;
						try {
							resourceScope.enter(state);
							// EObject context = getContext(state.getContents().get(0));
							IDomNode root = getSerializer().serializeToDom(state.getContents().get(0), false);
							org.eclipse.xtext.util.ReplaceRegion r = getFormatter().format(
								root, new TextRegion(0, document.getLength()), //
								getFormattingContextFactory().create(state, FormattingOption.Format), errors);
							if(!content.equals(r.getText())) {
								document.replace(0, document.getLength(), r.getText());
								changed = true;
							}
						}
						finally {
							resourceScope.exit();
						}

					}
					return changed; // no change
				}
			});
		}

	}
}
