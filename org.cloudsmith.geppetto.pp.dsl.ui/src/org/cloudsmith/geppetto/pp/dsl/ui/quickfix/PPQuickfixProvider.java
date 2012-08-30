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
import org.cloudsmith.geppetto.pp.dsl.ui.labeling.PPDescriptionLabelProvider;
import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.cloudsmith.xtext.dommodel.formatter.comments.CommentProcessor;
import org.cloudsmith.xtext.dommodel.formatter.comments.CommentProcessor.CommentFormattingOptions;
import org.cloudsmith.xtext.dommodel.formatter.comments.CommentProcessor.CommentText;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentConfiguration;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentConfiguration.CommentType;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentContainerInformation;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentContainerInformation.HashSLCommentContainer;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentContainerInformation.JavaLikeMLCommentContainer;
import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContextFactory;
import org.cloudsmith.xtext.dommodel.formatter.context.IFormattingContextFactory.FormattingOption;
import org.cloudsmith.xtext.resource.ResourceAccessScope;
import org.cloudsmith.xtext.textflow.CharSequences;
import org.cloudsmith.xtext.textflow.TextFlow;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
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
import com.google.inject.Provider;

public class PPQuickfixProvider extends DefaultQuickfixProvider {
	@Inject
	protected PPDescriptionLabelProvider descriptionLabelProvider;

	private final static EClass[] PARAMS_AND_VARIABLES = { //
	//
			PPPackage.Literals.DEFINITION_ARGUMENT, //
			// PPTPPackage.Literals.TYPE_ARGUMENT, //
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

	@Inject
	IFormattingContextFactory formattingContextFactory;

	@Inject
	protected Provider<ICommentConfiguration<CommentType>> commentConfigurationProvider;

	@Inject
	private ResourceAccessScope resourceScope;

	@Inject
	private Provider<RelationshipExpressionFixer> relationshipExpressionFixer;

	@Fix(IPPDiagnostics.ISSUE_UNWANTED_ML_COMMENT)
	public void changeMLCommentToSLComment(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final IModificationContext modificationContext = getModificationContextFactory().createModificationContext(
			issue);
		final IXtextDocument xtextDocument = modificationContext.getXtextDocument();
		xtextDocument.readOnly(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource state) throws Exception {
				resourceScope.enter(state);
				try {

					String issueString = xtextDocument.get(issue.getOffset(), issue.getLength());
					final boolean endsWithBreak = issue.getData() != null && issue.getData().length == 1 &&
							"true".equals(issue.getData()[0]);
					CommentProcessor commentProcessor = new CommentProcessor();
					JavaLikeMLCommentContainer mlContainer = new ICommentContainerInformation.JavaLikeMLCommentContainer();
					HashSLCommentContainer hashContainer = new ICommentContainerInformation.HashSLCommentContainer();
					int offsetOfNode = issue.getOffset();

					int posOnLine = offsetOfNode -
							Math.max(0, 1 + CharSequences.lastIndexOf(
								xtextDocument.get(0, xtextDocument.getLength()), "\n", offsetOfNode - 1));

					CommentText commentText = commentProcessor.separateCommentFromContainer(
						issueString, mlContainer.create(posOnLine), "\n");
					TextFlow result = commentProcessor.formatComment(
						commentText, hashContainer.create(posOnLine), new CommentFormattingOptions(
							commentConfigurationProvider.get().getFormatterAdvice(CommentType.SingleLine),
							Integer.MAX_VALUE, 0, 1), formattingContextFactory.create(state, FormattingOption.Format));

					if(!endsWithBreak)
						result.appendBreak();
					String replacement = CharSequences.trimLeft(result.getText()).toString();

					acceptor.accept(
						issue, "Change to # style comment",
						"Changes comment to # style (any trailing logic on last line is moved to separate line", null,
						new ReplacingModification(issue.getOffset(), issue.getLength(), replacement));
				}
				finally {
					resourceScope.exit();
				}
			}
		});
	}

	@Fix(IPPDiagnostics.ISSUE__UNBRACED_INTERPOLATION)
	public void changeToBracedInterpolation(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final IModificationContext modificationContext = getModificationContextFactory().createModificationContext(
			issue);
		final IXtextDocument xtextDocument = modificationContext.getXtextDocument();
		xtextDocument.readOnly(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource state) throws Exception {

				String issueString = xtextDocument.get(issue.getOffset(), issue.getLength());

				acceptor.accept(issue, "Surround interpolated variable with ${ }", //
					"Changes '" + issueString + "' to '${" + issueString.substring(1) + "}'", null, //
					new SurroundWithTextModification(issue.getOffset() + 1, issueString.length() - 1, "{", "}"));
			}
		});
	}

	@Fix(IPPDiagnostics.ISSUE__NOT_INITIAL_LOWERCASE)
	public void changeToInitialLowerCase(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final IModificationContext modificationContext = getModificationContextFactory().createModificationContext(
			issue);
		final IXtextDocument xtextDocument = modificationContext.getXtextDocument();
		xtextDocument.readOnly(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource state) throws Exception {
				String issueString = xtextDocument.get(issue.getOffset(), issue.getLength());
				int pos = issueString.startsWith("$")
						? 1
						: 0;
				if(issueString.length() > pos) {
					char c = issueString.charAt(pos);
					if(Character.isLetter(c)) {
						StringBuilder builder = new StringBuilder();
						builder.append("Change '").append(c).append("' to '").append(Character.toLowerCase(c)).append(
							"'.");
						if(Character.isLetter(issueString.charAt(pos)))
							acceptor.accept(
								issue,
								"Change first character to lower case",
								builder.toString(),
								null,
								new ReplacingModification(
									issue.getOffset() + pos, 1, Character.toString(Character.toLowerCase(c))));
					}
					else {
						if(c == '_') {
							int count = 0;
							for(int i = pos; i < issueString.length() && issueString.charAt(i) == '_'; i++)
								count++;

							acceptor.accept(
								issue, "Remove the leading underscore", "Removes all leading underscores.", null,
								new ReplacingModification(issue.getOffset() + pos, count, ""));
						}
						else if(Character.isDigit(c)) {
							int count = 0;
							for(int i = pos; i < issueString.length() && Character.isDigit(issueString.charAt(i)); i++)
								count++;
							acceptor.accept(
								issue, "Remove the leading digits", "Removes all leading digits", null,
								new ReplacingModification(issue.getOffset() + pos, count, ""));

						}
						// ? insert 'a' ? (stupid, but perhaps better than nothing)
						acceptor.accept(
							issue, "Insert an 'a' before first character.",
							"Inserts the lower case letter 'a' before the first character", null,
							new ReplacingModification(issue.getOffset() + pos, 0, "a"));

					}
				}
			}
		});
	}

	@Fix(IPPDiagnostics.ISSUE__DQ_STRING_NOT_REQUIRED)
	public void changeToSQString(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final IModificationContext modificationContext = getModificationContextFactory().createModificationContext(
			issue);
		final IXtextDocument xtextDocument = modificationContext.getXtextDocument();
		xtextDocument.readOnly(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource state) throws Exception {

				String issueString = xtextDocument.get(issue.getOffset(), issue.getLength());
				StringBuilder replacement = new StringBuilder();
				replacement.append("'");
				replacement.append(escapeChar(issueString.substring(1, issueString.length() - 1), '\''));
				replacement.append("'");

				acceptor.accept(issue, "Replace with single quoted string", "Changes \" to '", null, //
					new ReplacingModification(issue.getOffset(), issueString.length(), replacement.toString()));
			}
		});
	}

	@Fix(IPPDiagnostics.ISSUE__DQ_STRING_NOT_REQUIRED_VAR)
	public void changeToVariable(final Issue issue, final IssueResolutionAcceptor acceptor) {
		final IModificationContext modificationContext = getModificationContextFactory().createModificationContext(
			issue);
		final IXtextDocument xtextDocument = modificationContext.getXtextDocument();
		xtextDocument.readOnly(new IUnitOfWork.Void<XtextResource>() {
			@Override
			public void process(XtextResource state) throws Exception {

				String issueString = xtextDocument.get(issue.getOffset(), issue.getLength());

				acceptor.accept(issue, "Replace with variable", "Replace string with " + issue.getData()[0], null, //
					new ReplacingModification(issue.getOffset(), issueString.length(), issue.getData()[0]));
			}
		});
	}

	private String escapeChar(String s, char x) {
		StringBuilder result = new StringBuilder();
		boolean nextIsEscaped = false;
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == '\\') {
				nextIsEscaped = !nextIsEscaped;
			}
			else if(c == x) {
				if(!nextIsEscaped)
					result.append('\\');
				nextIsEscaped = false;
			}
			else {
				nextIsEscaped = false;
			}
			result.append(c);
		}
		return result.toString();
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

	@Fix(IPPDiagnostics.ISSUE_RIGHT_TO_LEFT_RELATIONSHIP)
	public void fixRightToLeftRelationsip(final Issue issue, final IssueResolutionAcceptor acceptor) {
		if(issue.getLength() > 2 || issue.getData() == null || issue.getData().length != 2 ||
				"false".equals(issue.getData()[1]))
			return; // can't fix it

		relationshipExpressionFixer.get().fixRightToLeftRelationsip(issue, acceptor);

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

	@Fix(IPPDiagnostics.ISSUE__STRING_BOOLEAN)
	public void makeStringBoolean(final Issue issue, final IssueResolutionAcceptor acceptor) {
		String booleanText = issue.getData()[0];
		acceptor.accept(
			issue, "Change to boolean " + booleanText,
			"A string is always true in boolean sense.\nChange to a real boolean value.", null, //
			new ReplacingModification(issue.getOffset(), issue.getLength(), booleanText));
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
					issue.getOffset(), issue.getLength(), proposal, true));
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

		acceptor.accept(issue, "Quote name", "Replace name with quoted name.", null, new SurroundWithTextModification(
			issue.getOffset(), issue.getLength(), "'"));
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

	@Fix(IPPDiagnostics.ISSUE__UNQUOTED_INTERPOLATION)
	public void unquotedInterpolation(final Issue issue, final IssueResolutionAcceptor acceptor) {

		// "${x}
		acceptor.accept(
			issue, "Surround with double quotes", "Places the unquoted interpolation in a string", null,
			new SurroundWithTextModification(issue.getOffset(), issue.getLength(), "\"", "\""));

		// $x
		acceptor.accept(
			issue, "Change to regular variable reference", "Removes the '{' and '}'", null, new IModification() {

				@Override
				public void apply(IModificationContext context) throws Exception {
					IXtextDocument doc = context.getXtextDocument();
					doc.replace(
						issue.getOffset(), issue.getLength(),
						"$" + doc.get(issue.getOffset() + 2, issue.getLength() - 3));
				}
			});

		// $x ? { undef => '', default => $x }
		acceptor.accept(
			issue, "Change to selector that makes undef empty string", "$x ? {undef => '', default => $x }", null,
			new IModification() {

				@Override
				public void apply(IModificationContext context) throws Exception {
					IXtextDocument doc = context.getXtextDocument();
					String varName = "$" + doc.get(issue.getOffset() + 2, issue.getLength() - 3);
					StringBuilder builder = new StringBuilder();
					builder.append(varName);
					builder.append(" ? {");
					builder.append(" undef => '', default => ");
					builder.append(varName);
					builder.append("}");
					doc.replace(issue.getOffset(), issue.getLength(), builder.toString());
				}
			});
	}
}
