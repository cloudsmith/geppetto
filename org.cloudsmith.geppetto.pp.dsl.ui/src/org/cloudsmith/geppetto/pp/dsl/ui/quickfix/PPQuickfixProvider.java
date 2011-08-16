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
import org.eclipse.xtext.naming.QualifiedName;
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

	private static class ReplacingModification implements IModification {

		final private int length;

		final private int offset;

		final private String text;

		ReplacingModification(int offset, int length, String text) {
			this.length = length;
			this.offset = offset;
			this.text = text;
		}

		@Override
		public void apply(IModificationContext context) throws BadLocationException {
			IXtextDocument xtextDocument = context.getXtextDocument();
			xtextDocument.replace(offset, length, text);
		}

	}

	private static String toInitialCase(String s, boolean upper) {
		if(s.length() < 1)
			return s;
		StringBuilder builder = new StringBuilder();
		if(upper)
			builder.append(Character.toUpperCase(s.charAt(0)));
		else
			builder.append(Character.toLowerCase(s.charAt(0)));
		try {
			builder.append(s.substring(1));
		}
		catch(IndexOutOfBoundsException e) {
			// ignore
		}
		return builder.toString();
	}

	@Fix(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_TYPE_PROP)
	public void findClosestClassName(final Issue issue, IssueResolutionAcceptor acceptor) {
		proposeDataAsChangeTo(issue, acceptor);
	}

	@Fix(IPPDiagnostics.ISSUE__UNKNOWN_FUNCTION_REFERENCE_PROP)
	public void findClosestFunction(final Issue issue, IssueResolutionAcceptor acceptor) {
		proposeDataAsChangeTo(issue, acceptor);
	}

	@Fix(IPPDiagnostics.ISSUE__RESOURCE_UNKNOWN_PROPERTY_PROP)
	public void findClosestParameters(final Issue issue, IssueResolutionAcceptor acceptor) {
		proposeDataAsChangeTo(issue, acceptor);
	}

	@Fix(IPPDiagnostics.ISSUE__MISSING_COMMA)
	public void insertMissingComma(final Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(
			issue, "Insert missing comma", "Insert missing comma", null, new ReplacingModification(
				issue.getOffset() + 1, 0, ","));
	}

	@Fix(IPPDiagnostics.ISSUE__NOT_NAME_OR_REF)
	public void makeAllSegmentsSameCase(final Issue issue, IssueResolutionAcceptor acceptor) {
		String data[] = issue.getData();
		if(data == null || data.length != 1)
			return;
		QualifiedName fqn = getQualifiedNameConverter().toQualifiedName(data[0]);
		if(fqn.getSegmentCount() < 2)
			return;
		int upper = 0;
		int lower = 0;
		for(int i = 0; i < fqn.getSegmentCount(); i++) {
			String s = fqn.getSegment(i);
			if(s.length() > 0) {
				if(Character.isUpperCase(s.charAt(0)))
					upper++;
				else if(Character.isLowerCase(s.charAt(0)))
					lower++;
			}
		}
		// if all have same case, or if some where not letters
		if(upper + lower != fqn.getSegmentCount() || upper == 0 || lower == 0)
			return; // some other "unfixable" problem

		String[] segments = fqn.getSegments().toArray(new String[0]);
		for(int i = 0; i < segments.length; i++)
			segments[i] = toInitialCase(segments[i], true);
		QualifiedName upperCaseName = QualifiedName.create(segments);
		for(int i = 0; i < segments.length; i++)
			segments[i] = toInitialCase(segments[i], false);
		QualifiedName lowerCaseName = QualifiedName.create(segments);

		String tmp = getQualifiedNameConverter().toString(upperCaseName);
		acceptor.accept(issue, "Make all segments start with upper case", //
		"Change the name to '" + tmp + "'", null, new ReplacingModification(issue.getOffset(), issue.getLength(), tmp));
		tmp = getQualifiedNameConverter().toString(lowerCaseName);
		acceptor.accept(issue, "Make all segments start with lower case", //
		"Change the name to '" + tmp + "'", null, new ReplacingModification(issue.getOffset(), issue.getLength(), tmp));
	}

	@Fix(IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE)
	public void makeReferenceAbsolute(final Issue issue, IssueResolutionAcceptor acceptor) {
		String data[] = issue.getData();
		if(data == null)
			return;
		for(String proposal : data) {
			acceptor.accept(issue, "Replace with '" + proposal + "'", //
			"Change the reference to" + (proposal.startsWith("::")
					? " the absolute: \n"
					: ": \n") + proposal, null, new ReplacingModification(
				issue.getOffset(), issue.getLength(), proposal));
		}
	}

	private void proposeDataAsChangeTo(final Issue issue, IssueResolutionAcceptor acceptor) {
		String data[] = issue.getData();
		if(data == null || data.length < 1)
			return;

		// Include an ugly number to get them sorted in the correct order
		int proposalNbr = 1;
		for(String proposal : data) {
			String intString = Integer.toString(proposalNbr++);
			if(data.length > 9 && intString.length() < 2)
				intString = "0" + intString;
			acceptor.accept(
				issue, intString + ". Change to '" + proposal + "'", //
				"Change to (guessed value) '" + proposal + "'", null,
				new ReplacingModification(issue.getOffset(), issue.getLength(), proposal));
		}
	}
}
