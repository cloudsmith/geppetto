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
package org.cloudsmith.geppetto.pp.dsl.ui.contentassist;

import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_CLASSPARAMS;
import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_OVERRIDE;

import java.util.List;
import java.util.ListIterator;

import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.StringExpression;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.eval.PPStringConstantEvaluator;
import org.cloudsmith.geppetto.pp.dsl.linking.PPFinder;
import org.cloudsmith.geppetto.pp.dsl.linking.PPFinder.SearchResult;
import org.cloudsmith.geppetto.pp.dsl.ui.labeling.IIconNames;
import org.cloudsmith.geppetto.pp.dsl.ui.labeling.PPDescriptionLabelProvider;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.editor.contentassist.ConfigurableCompletionProposal;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

import com.google.inject.Inject;

/**
 * see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#contentAssist on how to customize content assistant
 */
public class PPProposalProvider extends AbstractPPProposalProvider {
	@Inject
	private PPStringConstantEvaluator stringConstantEvaluator;

	@Inject
	protected PPDescriptionLabelProvider descriptionLabelProvider;

	@Inject
	private PPFinder ppFinder;

	// @Inject
	// private IGrammarAccess grammarAccess;
	@Inject
	private IImageHelper imageHelper;

	/**
	 * PP FQN to/from Xtext QualifiedName converter.
	 */
	@Inject
	IQualifiedNameConverter converter;

	@Override
	public void complete_AttributeOperation(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// TODO Auto-generated method stub
		// System.err.println("complete_AttributeOperation");
		super.complete_AttributeOperation(model, ruleCall, context, acceptor);
	}

	@Override
	public void complete_AttributeOperations(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// TODO Auto-generated method stub
		// System.err.println("complete_AttributeOperations");
		super.complete_AttributeOperations(model, ruleCall, context, acceptor);
	}

	@Override
	public void complete_ResourceBody(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// TODO Auto-generated method stub
		// System.err.println("complete_ResourceBody");
		super.complete_ResourceBody(model, ruleCall, context, acceptor);
	}

	@Override
	public void complete_ResourceExpression(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// System.err.println("complete_ResourceExpression");
		super.complete_ResourceExpression(model, ruleCall, context, acceptor);
	}

	@Override
	public void complete_unionNameOrReference(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		super.complete_unionNameOrReference(model, ruleCall, context, acceptor);
		// System.err.println("complete_unionNameOrReference assignment to feature: " + " model: " +
		// model.eClass().getName() + " Text: " + context.getCurrentNode().getText());

	}

	@Override
	public void complete_VariableExpression(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {

		// check if prefix or just completed node starts with $, if so, do not offer a "$" as a starting point
		// to get variable names. (Rationale, showing every possible variable wherever a variable can occur is not a good idea,
		// instead, the user must at least enter a $ prefix. Once entered, it is not meaningful to see it as a suggestion).
		// (The list of variables is obtained by suggesting varName for a VariableExpression.)
		//
		String prefix = context.getPrefix();
		if("".equals(prefix) && context.getLastCompleteNode() != null &&
				context.getLastCompleteNode().getText().startsWith("$"))
			prefix = "$";
		if(!prefix.startsWith("$")) {
			StyledString description = new StyledString("$");
			description.append(" - $variable", StyledString.DECORATIONS_STYLER);
			acceptor.accept(createCompletionProposal("$", description, getImage(IIconNames.BLUE_CIRCLE), context));
		}
		super.complete_VariableExpression(model, ruleCall, context, acceptor);
	}

	@Override
	public void completeAssignment(Assignment assignment, ContentAssistContext contentAssistContext,
			ICompletionProposalAcceptor acceptor) {
		// // DEBUG PRINTOUT
		// ParserRule parserRule = GrammarUtil.containingParserRule(assignment);
		// String methodName = "complete" + Strings.toFirstUpper(parserRule.getName()) + "_" +
		// Strings.toFirstUpper(assignment.getFeature());
		// System.err.println("completeAssigment('" + methodName + "')");
		// // DEBUG END
		super.completeAssignment(assignment, contentAssistContext, acceptor);
	}

	@Override
	public void completeAttributeOperation_Key(EObject model, Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {

		// System.err.println("completeAttributeOperation_Key assignment to feature: " + assignment.getFeature() +
		// " model: " + model.eClass().getName() + " Text: " + context.getCurrentNode().getText());
		// super.completeAttributeOperation_Key(model, assignment, context, acceptor);

		// Proposal for AttributeOperation depends on context (the resource body)
		ResourceBody resourceBody = null;
		if(model.eClass() == PPPackage.Literals.RESOURCE_BODY) {
			// The model is a resource body if an AttributeOperation has not yet been detected by the grammar
			resourceBody = (ResourceBody) model;
		}
		else if(model.eClass() == PPPackage.Literals.ATTRIBUTE_OPERATION) {
			// The grammar is lenient with (=> value) being optional (or it is impossible to
			// get a ResourceBody context). Special handling is required to avoid producing a
			// new list o suggestions for the op position (as the grammar thinks a property name OR and op can
			// follow).
			resourceBody = (ResourceBody) model.eContainer().eContainer();

			// If the current caret position is after the end of the key (+1), do not offer any values
			String key = ((AttributeOperation) model).getKey();
			if(key != null && key.length() > 0 //
					&& context.getOffset() > NodeModelUtils.getNode(model).getOffset() + key.length())
				return;

		}
		else
			// can not determine a context
			return;

		// INode lastCompleteNode = context.getLastCompleteNode();
		// EObject ge = lastCompleteNode.getGrammarElement();
		// if(ge instanceof RuleCall) {
		// RuleCall lastCompletedRuleCall = (RuleCall) ge;
		// System.err.println(lastCompletedRuleCall.getRule().getName());
		// }
		// if(context.getCurrentNode() instanceof HiddenLeafNode) {
		// PPGrammarAccess ppga = (PPGrammarAccess) grammarAccess;
		// if(ppga.getWSRule() == context.getCurrentNode().getGrammarElement())
		// return;
		// }
		try {
			// figure out the shape of the resource
			ResourceExpression resourceExpr = (ResourceExpression) resourceBody.eContainer();

			ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(resourceExpr);
			int resourceType = adapter.getClassifier();
			// If resource is good, and not 'class', then it must have a known reference type.
			// the resource type - also requires getting the type name from the override's expression).
			if(resourceType == RESOURCE_IS_CLASSPARAMS) {
				// resource is pp: class { classname : parameter => value }

				// Find parameters for the class
				// Find the class
				final String className = stringConstantEvaluator.doToString(resourceBody.getNameExpr());
				if(className == null)
					return; // not a static expression
				// Need the class to get its full name
				ppFinder.configure(model.eResource());
				List<IEObjectDescription> descs = ppFinder.findHostClasses(resourceBody, className, null).getAdjusted();
				if(descs.size() < 1)
					return; // can't find class, no proposals
				IEObjectDescription desc = descs.get(0); // pick first if ambiguous

				// which attribute(s) are we trying to find.
				String prefix = context.getPrefix();
				QualifiedName fqn = desc.getQualifiedName().append(prefix);

				for(IEObjectDescription d : ppFinder.findAttributesWithPrefix(resourceBody, fqn).getAdjusted())
					acceptor.accept(createCompletionProposal(d.getName().getLastSegment(), context));

			}
			else if(resourceType == RESOURCE_IS_OVERRIDE) {
				// do nothing (too complicated due to the query being able to match all sorts of things)
			}
			else {
				// Normal Resource
				ppFinder.configure(model.eResource());

				// Either a default setting Type { } or instance type { }, in both cases propose all properties and parameters
				// including meta
				IEObjectDescription desc = (IEObjectDescription) adapter.getTargetObjectDescription();
				if(desc != null) {
					// the type is known
					// which attribute(s) are we trying to find.
					String prefix = context.getPrefix();
					QualifiedName fqn = desc.getQualifiedName().append(prefix);
					for(IEObjectDescription d : ppFinder.findAttributesWithPrefix(resourceBody, fqn).getAdjusted())
						acceptor.accept(createCompletionProposal(d.getName().getLastSegment(), context));

				}

			}

		}
		catch(ClassCastException e) {
			// ignore, something is in a weird state, simply ignore proposals
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.contentassist.AbstractPPProposalProvider#completeAttributeOperation_Op(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.xtext.Assignment, org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext,
	 * org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor)
	 */
	@Override
	public void completeAttributeOperation_Op(EObject model, Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// Proposal for AttributeOperation depends on context (the resource body)
		ResourceBody resourceBody = null;
		if(model.eClass() == PPPackage.Literals.RESOURCE_BODY) {
			// The model is a resource body if an AttributeOperation has not yet been detected by the grammar
			resourceBody = (ResourceBody) model;
		}
		else if(model.eClass() == PPPackage.Literals.ATTRIBUTE_OPERATION) {
			// The grammar is lenient with (=> value) being optional (or it is imposible to
			// get a ResourceBody context). Special handling is required to avoid producing a
			// new list o suggestions for the op position (as the grammar thinks a property name OR and op can
			// follow).
			resourceBody = (ResourceBody) model.eContainer().eContainer();

		}
		else {
			// can not determine a context
			super.completeAttributeOperation_Op(model, assignment, context, acceptor);
			return;
		}
		try {
			// figure out the shape of the resource
			ResourceExpression resourceExpr = (ResourceExpression) resourceBody.eContainer();

			ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(resourceExpr);
			int resourceType = adapter.getClassifier();
			acceptor.accept(createCompletionProposal("=>", context));
			if(resourceType == RESOURCE_IS_OVERRIDE)
				acceptor.accept(createCompletionProposal("+>", context));
		}
		catch(ClassCastException e) {
			// squelsh
		}
	}

	@Override
	public void completeAttributeOperation_Value(EObject model, Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// TODO Auto-generated method stub
		// super.completeAttributeOperation_Value(model, assignment, context, acceptor);
	}

	@Override
	public void completeAttributeOperations_Attributes(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		// TODO Auto-generated method stub
		// System.err.println("completeAttributeOperations_Attributes");
		super.completeAttributeOperations_Attributes(model, assignment, context, acceptor);
	}

	@Override
	public void completeKeyword(Keyword keyword, ContentAssistContext contentAssistContext,
			ICompletionProposalAcceptor acceptor) {
		// System.err.println("completeKeyword('" + keyword.getValue() + "')");
		// if(keyword.getValue().equals("+>"))
		// System.err.println("Oy !!!");
		super.completeKeyword(keyword, contentAssistContext, acceptor);
	}

	@Override
	public void completePuppetManifest_Statements(EObject model, Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		super.completePuppetManifest_Statements(model, assignment, context, acceptor);

		ppFinder.configure(model.eResource());

		for(IEObjectDescription d : ppFinder.findDefinitions(model, null).getAdjusted()) {
			String name = converter.toString(d.getQualifiedName());
			StyledString styledDescription = new StyledString(name);
			styledDescription.append(typeSuffix(d));
			acceptor.accept(createCompletionProposal(
				name, styledDescription, descriptionLabelProvider.getImage(d), context));
		}
		// acceptor.accept(createCompletionProposal(converter.toString(d.getQualifiedName()), context));

	}

	@Override
	public void completeRuleCall(RuleCall ruleCall, ContentAssistContext contentAssistContext,
			ICompletionProposalAcceptor acceptor) {
		// // DEBUG OUTPUT
		// AbstractRule calledRule = ruleCall.getRule();
		// String methodName = "complete_" + calledRule.getName();
		// System.err.println("completeRuleCall('" + methodName + "')");
		// // DEBUG END
		super.completeRuleCall(ruleCall, contentAssistContext, acceptor);
	}

	@Override
	public void completeTextExpression_Expression(EObject model, Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		completeVarNameCommon(model, assignment, context, acceptor, false, true);
	}

	@Override
	public void completeTextExpression_VarName(EObject model, Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		completeVarNameCommon(model, assignment, context, acceptor, false, false);
	}

	@Override
	public void completeUnquotedString_Expression(EObject model, Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// TODO Auto-generated method stub
		completeVarNameCommon(model, assignment, context, acceptor, false, true);
	}

	@Override
	public void completeVariableExpression_VarName(EObject model, Assignment ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		completeVarNameCommon(model, ruleCall, context, acceptor, false, false);
	}

	private void completeVarNameCommon(EObject model, Assignment ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor, boolean bracedProposal, boolean bracedInput) {
		final INode currentNode = context.getCurrentNode();
		EObject semantic = null;
		EStructuralFeature feature = null;
		boolean disqualified = true;

		if(currentNode != null) {
			semantic = currentNode.getSemanticElement();
			if(semantic != null) {
				if(semantic instanceof StringExpression == false) {

					feature = semantic.eContainingFeature();
					// if(feature == null) {
					// System.err.println("Null feature");
					// }
					// disqualify proposals for variable in assignment lhs. (this is what creates a variable, suggestions
					// are meaningless). Note that feature may be null here (it is at least not a known lhs in assignment).
					if(feature == PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR)
						disqualified = true;
					/* TODO: add more disqualified cases here (if there are any...) */
					else
						disqualified = false;
				}
			}
		}
		if(!disqualified) {
			// pick up the default offset and length to replace by the suggestion
			// these may be modified later if there are syntax errors / ambiguities and a larger selection is to be replaced
			int replacementOffset = context.getReplaceRegion().getOffset();
			int replacementLength = context.getReplaceRegion().getLength();

			// check conditions when replaced region is not the default, and adjust so the subsequent logic only
			// uses prefix and replacement-offset/length
			//
			String prefix = context.getPrefix();
			if(bracedInput) {
				if("${".equals(prefix)) {
					prefix = "";
					replacementOffset += 2;
					replacementLength -= 2;
				}
			}
			else if("".equals(prefix) && context.getLastCompleteNode().getText().startsWith("$")) {
				prefix = context.getLastCompleteNode().getText();
				replacementOffset = context.getLastCompleteNode().getOffset();
				replacementLength = context.getLastCompleteNode().getLength();
			}
			else if(":".equals(prefix) && context.getLastCompleteNode().getText().startsWith("$")) {
				prefix = context.getLastCompleteNode().getText() + "::";
				replacementOffset = context.getLastCompleteNode().getOffset();
				replacementLength = context.getLastCompleteNode().getLength() + 1; // +1 for the prefix ':'
			}
			else if(prefix.endsWith("$")) {
				// this happens in a string when at '"aaa$|..."'
				replacementOffset += prefix.length() - 1;
				replacementLength = 1;
				prefix = "$";
			}
			if((prefix.startsWith("$") && !prefix.startsWith("${")) || bracedInput) {
				// messy
				// when after a $ a variable is not recognized until a valid sequence follows i.e (::)?<varchar>. When (::)?<varchar> has been seen
				// it is recognized as a VariableExpression
				// if inside ${ }, literal names should be proposed, but not if there is a $expr inside - e.g. ${$|, ${...$|, etc.

				// create indexed finder from the perspective of the current resource
				ppFinder.configure(model.eResource());

				// get the fqn (skip the '$') of the name to complete
				QualifiedName fqn = converter.toQualifiedName(prefix.substring(bracedInput
						? 0
						: 1));

				// turn global references '::x' into non global
				if(fqn.getSegmentCount() > 1 && fqn.getSegment(0).length() == 0)
					fqn = fqn.skipFirst(1);

				// normal converter does not add trailing empty segment, do so here to enable search in xxx::* namespace
				if(prefix.endsWith("::") && fqn.getSegmentCount() > 0)
					fqn = fqn.append("");

				// find variables using prefixed variant of find
				SearchResult r = ppFinder.findVariablesPrefixed(model, fqn, null);

				// get the name of the scope we are in to enable reduction of proposal to use locally scoped name
				QualifiedName scopeFQN = ppFinder.getNameOfScope(model);

				// Remove disqualified entries (known to be uninitialized) ( $x = $x, and define foo ($x, $y = $x) ) (surgical operation)
				removeDisqualifiedVariables(r.getAdjusted(), model);

				// Create proposals
				for(IEObjectDescription d : r.getAdjusted()) {
					// Filter out bad name(s)
					// https://github.com/cloudsmith/geppetto/issues/263
					if("*".equals(d.getQualifiedName().getLastSegment()))
						continue;

					fqn = d.getQualifiedName();
					StringBuilder b = new StringBuilder();
					// String description = null;
					Image image = null;

					// All proposals are variables, so start with $
					if(!bracedInput)
						b.append("$");
					if(bracedProposal)
						b.append("{");

					// All proposals consisting of one segment are global
					if(fqn.getSegmentCount() == 1)
						b.append("::");

					// TODO: As a consequence of fixing issue #305, this will never happen.
					// Investigate if meta parameters should be included at all as variables
					//
					// If a Parameter, and is Type::name - use only name, display "meta-parameter" (i.e. parameters "inherited" by all types).
					if(fqn.getSegmentCount() > 1 && d.getEClass() == PPTPPackage.Literals.PARAMETER &&
							"Type".equals(fqn.getFirstSegment())) {
						b.append(fqn.getSegment(1));
						// TODO: description "meta-parameter" should be provided by description label provider
						// description = "meta-parameter";
					}
					else {
						// if name is in same scope as reference, make it a local reference
						if(fqn.getSegmentCount() > 1 && fqn.skipLast(1).equals(scopeFQN))
							b.append(fqn.getLastSegment());
						else
							b.append(converter.toString(fqn));

						// description = descriptionLabelProvider.typeText(d);
						image = descriptionLabelProvider.getImage(d);

					}
					if(bracedProposal)
						b.append("}");

					// since $ is in icon, not needed in description (just makes it more difficult to read).
					StyledString styledDescription = new StyledString(b.substring(bracedInput
							? 0
							: 1));
					styledDescription.append(typeSuffix(d));
					ConfigurableCompletionProposal proposal = doCreateProposal(
						b.toString(), styledDescription, image, getPriorityHelper().getDefaultPriority(), context);
					proposal.setReplacementOffset(replacementOffset);
					proposal.setReplacementLength(replacementLength);
					acceptor.accept(proposal);
				}

			}
		}
	}

	protected Image getImage(String imageName) {
		return imageHelper.getImage(imageName);
	}

	/**
	 * Remove variables/entries that are not yet initialized. These are the values
	 * defined in the same name and type if the variable is contained in a definition argument
	 * 
	 * <p>
	 * e.g. in define selfref($selfa = $selfref::selfa, $selfb=$selfa::x) { $x=10 } none of the references to selfa, or x are disqualified.
	 * 
	 * @param descs
	 * @param o
	 * @return the number of disqualified variables removed from the list
	 */
	private int removeDisqualifiedAssignmentVariables(List<IEObjectDescription> descs, EObject o) {
		if(descs == null || descs.size() == 0)
			return 0;
		EObject p = o;
		while(p != null && p.eClass().getClassifierID() != PPPackage.ASSIGNMENT_EXPRESSION)
			p = p.eContainer();
		if(p == null)
			return 0; // not in an assignment

		// p is an AssignmentExpression at this point
		AssignmentExpression d = (AssignmentExpression) p;
		final String definitionFragment = d.eResource().getURIFragment(d);
		final String definitionURI = d.eResource().getURI().toString();

		int removedCount = 0;
		ListIterator<IEObjectDescription> litor = descs.listIterator();
		while(litor.hasNext()) {
			IEObjectDescription x = litor.next();
			URI xURI = x.getEObjectURI();
			// if in the same resource, and contain by the same EObject
			if(xURI.toString().startsWith(definitionURI) && xURI.fragment().startsWith(definitionFragment)) {
				litor.remove();
				removedCount++;
			}
		}
		return removedCount;
	}

	/**
	 * Remove variables/entries that are not yet initialized. These are the values
	 * defined in the same name and type if the variable is contained in a definition argument
	 * 
	 * <p>
	 * e.g. in define selfref($selfa = $selfref::selfa, $selfb=$selfa::x) { $x=10 } none of the references to selfa, or x are disqualified.
	 * 
	 * @param descs
	 * @param o
	 * @return the number of disqualified variables removed from the list
	 */
	private int removeDisqualifiedDefinitionVariables(List<IEObjectDescription> descs, EObject o) {
		if(descs == null || descs.size() == 0)
			return 0;
		EObject p = o;
		while(p != null && p.eClass().getClassifierID() != PPPackage.DEFINITION_ARGUMENT)
			p = p.eContainer();
		if(p == null)
			return 0; // not in a definition argument value tree

		// p is a DefinitionArgument at this point, we want it's parent being an abstract Definition
		EObject d = p.eContainer();
		if(d == null)
			return 0; // broken model
		d = d.eContainer();
		final String definitionFragment = d.eResource().getURIFragment(d);
		final String definitionURI = d.eResource().getURI().toString();

		int removedCount = 0;
		ListIterator<IEObjectDescription> litor = descs.listIterator();
		while(litor.hasNext()) {
			IEObjectDescription x = litor.next();
			URI xURI = x.getEObjectURI();
			// if in the same resource, and contain by the same EObject
			if(xURI.toString().startsWith(definitionURI) && xURI.fragment().startsWith(definitionFragment)) {
				litor.remove();
				removedCount++;
			}
		}
		return removedCount;
	}

	private int removeDisqualifiedVariables(List<IEObjectDescription> descs, EObject o) {
		int count = removeDisqualifiedDefinitionVariables(descs, o);
		count += removeDisqualifiedAssignmentVariables(descs, o);

		return count;
	}

	public StyledString typeSuffix(IEObjectDescription element) {
		EPackage epkg = element.getEClass().getEPackage();
		StyledString bld = new StyledString();
		if(epkg == PPTPPackage.eINSTANCE || epkg == PPPackage.eINSTANCE) {
			bld.append(
				" : " + descriptionLabelProvider.getClassLabel(element.getEClass()), StyledString.DECORATIONS_STYLER);
			bld.append(" - " + element.getEObjectURI().lastSegment(), StyledString.QUALIFIER_STYLER);
			return bld;
		}
		bld.append(" : ", StyledString.DECORATIONS_STYLER);
		return descriptionLabelProvider.getStyledText(element);
	}
}
