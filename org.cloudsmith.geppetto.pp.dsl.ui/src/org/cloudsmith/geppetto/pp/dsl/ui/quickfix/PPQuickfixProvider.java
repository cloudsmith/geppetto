/**
 * Copyright (c) 2011 Cloudsmith, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ui.quickfix;

import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.validation.Issue;

public class PPQuickfixProvider extends DefaultQuickfixProvider {

	// @Fix(MyJavaValidator.INVALID_NAME)
	// public void capitalizeName(final Issue issue, IssueResolutionAcceptor acceptor) {
	// acceptor.accept(issue, "Capitalize name", "Capitalize the name.", "upcase.png", new IModification() {
	// public void apply(IModificationContext context) throws BadLocationException {
	// IXtextDocument xtextDocument = context.getXtextDocument();
	// String firstLetter = xtextDocument.get(issue.getOffset(), 1);
	// xtextDocument.replace(issue.getOffset(), 1, firstLetter.toUpperCase());
	// }
	// });
	// }

	@Fix(IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE)
	public void makeReferenceAbsolute(final Issue issue, IssueResolutionAcceptor acceptor) {
		String data[] = issue.getData();
		if(data == null || data.length != 1 || data[0] == null || data[0].startsWith("::"))
			return;

		acceptor.accept(
			issue, "Make absolute", "Make reference absolute by prepending '::'", null, new IModification() {
				public void apply(IModificationContext context) throws BadLocationException {
					IXtextDocument xtextDocument = context.getXtextDocument();
					xtextDocument.replace(issue.getOffset(), 0, "::");
				}
			});
	}

}
