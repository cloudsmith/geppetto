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

import java.util.List;

import org.cloudsmith.geppetto.pp.DoubleQuotedString;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.VariableTE;
import org.cloudsmith.geppetto.pp.dsl.contentassist.PPProposalsGenerator;
import org.cloudsmith.geppetto.pp.dsl.linking.PPFinder;
import org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider;
import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.edit.IModification;
import org.eclipse.xtext.ui.editor.model.edit.IModificationContext;
import org.eclipse.xtext.ui.editor.quickfix.DefaultQuickfixProvider;
import org.eclipse.xtext.ui.editor.quickfix.Fix;
import org.eclipse.xtext.ui.editor.quickfix.IssueResolutionAcceptor;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Inject;

public class PPQuickfixProvider extends DefaultQuickfixProvider {
	private static class ReplacingModification implements IModification {

		final protected int length;

		final protected int offset;

		final protected String text;

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

	private static class SurroundWithTextModification extends ReplacingModification {
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

	private final static EClass[] PARAMS_AND_VARIABLES = {
			PPPackage.Literals.DEFINITION_ARGUMENT, PPTPPackage.Literals.TYPE_ARGUMENT,
			PPPackage.Literals.VARIABLE_EXPRESSION };

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

	/**
	 * Access to naming of model elements.
	 */
	@Inject
	IQualifiedNameProvider fqnProvider;

	/**
	 * PP FQN to/from Xtext QualifiedName converter.
	 */
	@Inject
	IQualifiedNameConverter converter;

	@Inject
	private PPProposalsGenerator proposer;

	@Inject
	private ISearchPathProvider searchPathProvider;

	@Inject
	private PPFinder ppFinder;

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

	@Fix(IPPDiagnostics.ISSUE__HYPHEN_IN_NAME)
	public void hyphenInName(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final IModificationContext modificationContext = getModificationContextFactory().createModificationContext(
			issue);
		final IXtextDocument xtextDocument = modificationContext.getXtextDocument();
		xtextDocument.readOnly(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource state) throws Exception {
				String issueString = xtextDocument.get(issue.getOffset(), issue.getLength());
				String replacementString = issueString.replaceAll("-", "_");

				acceptor.accept(
					issue, "Change to '" + replacementString + "'", "Changes all '-' to '_' in the name", null, //
					new ReplacingModification(issue.getOffset(), issue.getLength(), replacementString));

				replacementString = issueString.replaceAll("-", "");
				acceptor.accept(issue, "Change to '" + replacementString + "'", "Removes all '-' from name", null, //
					new ReplacingModification(issue.getOffset(), issue.getLength(), replacementString));
			}
		});
	}

	@Fix(IPPDiagnostics.ISSUE__MISSING_COMMA)
	public void insertMissingComma(final Issue issue, IssueResolutionAcceptor acceptor) {
		acceptor.accept(
			issue, "Insert missing comma", "Insert missing comma", null, new ReplacingModification(
				issue.getOffset() + 1, 0, ","));
	}

	@Fix(IPPDiagnostics.ISSUE__INTERPOLATED_HYPHEN)
	public void interpolatedHyphen(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final IModificationContext modificationContext = getModificationContextFactory().createModificationContext(
			issue);
		final IXtextDocument xtextDocument = modificationContext.getXtextDocument();
		xtextDocument.readOnly(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource state) throws Exception {
				EObject varExpr = state.getEObject(issue.getUriToProblem().fragment());
				if(!(varExpr instanceof DoubleQuotedString))
					return; // something is wrong

				// VariableTE varTE = (VariableTE) varExpr;
				// ICompositeNode node = NodeModelUtils.getNode(varTE);

				// b) ${aaa}-bbb - i.e. the 2.6 way
				//
				String issueString = xtextDocument.get(issue.getOffset(), issue.getLength());
				boolean dollarVar = issueString.startsWith("$");
				if(dollarVar)
					issueString = issueString.substring(1);

				// --a)
				StringBuilder builder = new StringBuilder();
				builder.append("${");
				builder.append(issueString);
				builder.append("}");
				acceptor.accept(
					issue, "Change to '" + builder.toString() + "'",
					"Enclose in { } to prevent '-' from being included in variable name", null, //
					new ReplacingModification(issue.getOffset(), issue.getLength(), builder.toString()));

				// // --b)
				// builder = new StringBuilder();
				// builder.append("${");
				// int hyphenPos = issue.getOffset() - node.getOffset();
				// builder.append(node.getText().substring(1, hyphenPos));
				// builder.append("}");
				// acceptor.accept(issue, "Change to '" + builder.toString() + "'", "Change to 2.6 style", null, //
				// new ReplacingModification(node.getOffset(), hyphenPos, builder.toString()));
			}
		});

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
			"Change the name to '" + tmp + "'", null, new ReplacingModification(
				issue.getOffset(), issue.getLength(), tmp));
		tmp = getQualifiedNameConverter().toString(lowerCaseName);
		acceptor.accept(issue, "Make all segments start with lower case", //
			"Change the name to '" + tmp + "'", null, new ReplacingModification(
				issue.getOffset(), issue.getLength(), tmp));
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

	private QualifiedName nameOfScope(EObject target) {
		if(target instanceof PuppetManifest)
			return converter.toQualifiedName("::"); // global scope

		QualifiedName scopeName = fqnProvider.getFullyQualifiedName(target);

		// the target happens to be a scope
		if(scopeName != null)
			return scopeName;
		return nameOfScope(target.eContainer());

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
			acceptor.accept(issue, intString + ". Change to '" + proposal + "'", //
				"Change to (guessed value) '" + proposal + "'", null, new ReplacingModification(
					issue.getOffset(), issue.getLength(), proposal));
		}
	}

	@Fix(IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION_STRING_OK)
	public void surroundExprWithSingleQuote(final Issue issue, IssueResolutionAcceptor acceptor) {

		acceptor.accept(
			issue, "Quote expression", "Surround expression with single quotes", null,
			new SurroundWithTextModification(issue.getOffset(), issue.getLength(), "'"));
	}

	@Fix(IPPDiagnostics.ISSUE__UNSUPPORTED_EXPRESSION_STRING_OK)
	public void surroundWithInterpolation(final Issue issue, IssueResolutionAcceptor acceptor) {

		acceptor.accept(
			issue, "Interpolate expression", "Surround expression with '\"${', '}\"' ", null,
			new SurroundWithTextModification(issue.getOffset(), issue.getLength(), "\"${", "}\""));
	}

	@Fix(IPPDiagnostics.ISSUE__UNQUOTED_QUALIFIED_NAME)
	public void surroundWithSingleQuote(final Issue issue, IssueResolutionAcceptor acceptor) {

		acceptor.accept(
			issue, "Quote qualified name", "Replace qualified name with quoted name.", null,
			new SurroundWithTextModification(issue.getOffset(), issue.getLength(), "'"));
	}

	@Fix(IPPDiagnostics.ISSUE__UNKNOWN_VARIABLE)
	public void unknownVariable(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final IModificationContext modificationContext = getModificationContextFactory().createModificationContext(
			issue);

		final boolean[] unqualified = new boolean[1];
		unqualified[0] = false;

		final IXtextDocument xtextDocument = modificationContext.getXtextDocument();
		xtextDocument.readOnly(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource state) throws Exception {
				EObject varExpr = state.getEObject(issue.getUriToProblem().fragment());
				if(!(varExpr instanceof VariableExpression || varExpr instanceof VariableTE || varExpr instanceof LiteralNameOrReference))
					return; // something is wrong

				if(issue.getOffset() < 0)
					return; // something is wrong (while editing)

				String issueString = xtextDocument.get(issue.getOffset(), issue.getLength());
				boolean dollarVar = issueString.startsWith("$");
				if(dollarVar)
					issueString = issueString.substring(1);
				QualifiedName fqn = converter.toQualifiedName(issueString);
				if(fqn.getSegmentCount() > 1) {
					ppFinder.configure(varExpr);
					String[] proposals = proposer.computeProposals(issueString, //
						ppFinder.getExportedDescriptions(), //
						searchPathProvider.get(varExpr.eResource()), PARAMS_AND_VARIABLES);
					for(String s : proposals)
						acceptor.accept(issue, "Change to '$" + s + "'", "Did you mean '$" + s + "'", null, //
							new ReplacingModification(issue.getOffset() + (dollarVar
									? 1
									: 0), issueString.length(), s));
				}
				else {
					unqualified[0] = true;
				}
			}
		});
		// if it was unqualified
		if(unqualified[0])
			unqualifiedVariable(issue, acceptor);
	}

	@Fix(IPPDiagnostics.ISSUE__UNQUALIFIED_VARIABLE)
	public void unqualifiedVariable(final Issue issue, final IssueResolutionAcceptor acceptor) {

		final IModificationContext modificationContext = getModificationContextFactory().createModificationContext(
			issue);
		final IXtextDocument xtextDocument = modificationContext.getXtextDocument();
		xtextDocument.readOnly(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource state) throws Exception {
				EObject varExpr = state.getEObject(issue.getUriToProblem().fragment());
				if(!(varExpr instanceof VariableExpression || varExpr instanceof VariableTE || varExpr instanceof LiteralNameOrReference))
					return; // something is wrong

				String issueString = xtextDocument.get(issue.getOffset(), issue.getLength());
				boolean dollarVar = issueString.startsWith("$");
				if(dollarVar)
					issueString = issueString.substring(1);

				// --GLOBAL NAME
				acceptor.accept(issue, "Change to '$::" + issueString + "'", "Change to '$" + issueString +
						"' in global scope", null, new ReplacingModification(issue.getOffset() + (dollarVar
						? 1
						: 0), issueString.length(), "::" + issueString));

				// --NAME IN THIS SCOPE - AND OUTER
				// (Propose existing names in this and outer scopes)
				QualifiedName nameOfScope = nameOfScope(varExpr);
				if(nameOfScope.getSegmentCount() < 1)
					return; // it is in global scope (which was already proposed)

				ppFinder.configure(varExpr);
				for(IEObjectDescription desc : ppFinder.findVariables(varExpr, nameOfScope.append(issueString), null).getAdjusted()) {
					String nameInScopeString = converter.toString(desc.getName());
					String foundNameOfScope = converter.toString(desc.getName().skipLast(1));
					String scopeType = desc.getName().skipLast(1).equals(nameOfScope)
							? "current"
							: "inhertied";
					acceptor.accept(
						issue, "Change to '$" + nameInScopeString + "'", "Change to '$" + issueString + "' in the " +
								scopeType + " scope:\n '" + foundNameOfScope + "'", null, new ReplacingModification(
							issue.getOffset() + (dollarVar
									? 1
									: 0), issueString.length(), nameInScopeString));
				}
				// String scopeType = "current";

				for(QualifiedName qn = nameOfScope.skipLast(1); qn.getSegmentCount() > 0; qn = qn.skipLast(1)) {
					QualifiedName nameInScope = qn.append(issueString);
					String nameInScopeString = converter.toString(nameInScope);
					// TODO: Only propose if this name exists
					// configure for lookup of things
					if(ppFinder.findVariables(varExpr, nameInScopeString, null).getAdjusted().size() > 0)
						acceptor.accept(
							issue, "Change to '$" + converter.toString(nameInScope) + "'", "Change to '$" +
									issueString + "' in the outer scope:\n '" + converter.toString(qn) + "'", null,
							new ReplacingModification(issue.getOffset() + (dollarVar
									? 1
									: 0), issueString.length(), nameInScopeString));
				}
				// --VARIABLE OR PARAMETER IN A SUPERCLASS
				List<IEObjectDescription> classes = ppFinder.findHostClasses(
					varExpr, converter.toString(nameOfScope), null).getAdjusted();
				if(classes.size() > 0) {
					// ignore ambiguities, just pick the first
					// TODO: Complete PPFinder to search for variables with a search strategy exact, allscopes, allscopesStartsWith
				}
			}
		});
	}

}
