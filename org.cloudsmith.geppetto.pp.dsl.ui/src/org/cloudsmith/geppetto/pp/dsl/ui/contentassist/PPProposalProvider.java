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

import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.eval.PPStringConstantEvaluator;
import org.cloudsmith.geppetto.pp.dsl.linking.PPFinder;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IEObjectDescription;
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
	private PPFinder ppFinder;

	// @Inject
	// private IGrammarAccess grammarAccess;

	/**
	 * PP FQN to/from Xtext QualifiedName converter.
	 */
	@Inject
	IQualifiedNameConverter converter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.contentassist.AbstractPPProposalProvider#complete_AttributeOperation(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.xtext.RuleCall, org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext,
	 * org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor)
	 */
	@Override
	public void complete_AttributeOperation(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// TODO Auto-generated method stub
		// System.err.println("complete_AttributeOperation");
		super.complete_AttributeOperation(model, ruleCall, context, acceptor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.contentassist.AbstractPPProposalProvider#complete_AttributeOperations(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.xtext.RuleCall, org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext,
	 * org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor)
	 */
	@Override
	public void complete_AttributeOperations(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// TODO Auto-generated method stub
		// System.err.println("complete_AttributeOperations");
		super.complete_AttributeOperations(model, ruleCall, context, acceptor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.contentassist.AbstractPPProposalProvider#complete_ResourceBody(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.xtext.RuleCall, org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext,
	 * org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor)
	 */
	@Override
	public void complete_ResourceBody(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// TODO Auto-generated method stub
		// System.err.println("complete_ResourceBody");
		super.complete_ResourceBody(model, ruleCall, context, acceptor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.contentassist.AbstractPPProposalProvider#complete_ResourceExpression(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.xtext.RuleCall, org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext,
	 * org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor)
	 */
	@Override
	public void complete_ResourceExpression(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// System.err.println("complete_ResourceExpression");
		super.complete_ResourceExpression(model, ruleCall, context, acceptor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.contentassist.AbstractPPProposalProvider#complete_AttributeOperation(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.xtext.RuleCall, org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext,
	 * org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor)
	 */
	@Override
	public void complete_unionNameOrReference(EObject model, RuleCall ruleCall, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		super.complete_unionNameOrReference(model, ruleCall, context, acceptor);
		// System.err.println("complete_unionNameOrReference assignment to feature: " + " model: " +
		// model.eClass().getName() + " Text: " + context.getCurrentNode().getText());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.editor.contentassist.AbstractJavaBasedContentProposalProvider#completeAssignment(org.eclipse.xtext.Assignment,
	 * org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext, org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor)
	 */
	@Override
	public void completeAssignment(Assignment assignment, ContentAssistContext contentAssistContext,
			ICompletionProposalAcceptor acceptor) {
		// ParserRule parserRule = GrammarUtil.containingParserRule(assignment);
		// String methodName = "complete" + Strings.toFirstUpper(parserRule.getName()) + "_" +
		// Strings.toFirstUpper(assignment.getFeature());
		// System.err.println("completeAssigment('" + methodName + "')");
		super.completeAssignment(assignment, contentAssistContext, acceptor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.contentassist.AbstractPPProposalProvider#completeAttributeOperation_Key(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.xtext.Assignment, org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext,
	 * org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor)
	 */
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
			// The grammar is lenient with (=> value) being optional (or it is imposible to
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
				List<IEObjectDescription> descs = ppFinder.findHostClasses(resourceBody, className, null);
				if(descs.size() < 1)
					return; // can't find class, no proposals
				IEObjectDescription desc = descs.get(0); // pick first if ambiguous

				// which attribute(s) are we trying to find.
				String prefix = context.getPrefix();
				QualifiedName fqn = desc.getQualifiedName().append(prefix);

				for(IEObjectDescription d : ppFinder.findAttributesWithPrefix(resourceBody, fqn))
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
					for(IEObjectDescription d : ppFinder.findAttributesWithPrefix(resourceBody, fqn))
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.contentassist.AbstractPPProposalProvider#completeAttributeOperation_Value(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.xtext.Assignment, org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext,
	 * org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor)
	 */
	@Override
	public void completeAttributeOperation_Value(EObject model, Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		// TODO Auto-generated method stub
		// super.completeAttributeOperation_Value(model, assignment, context, acceptor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cloudsmith.geppetto.pp.dsl.ui.contentassist.AbstractPPProposalProvider#completeAttributeOperations_Attributes(org.eclipse.emf.ecore.EObject
	 * , org.eclipse.xtext.Assignment, org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext,
	 * org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor)
	 */
	@Override
	public void completeAttributeOperations_Attributes(EObject model, Assignment assignment,
			ContentAssistContext context, ICompletionProposalAcceptor acceptor) {
		// TODO Auto-generated method stub
		// System.err.println("completeAttributeOperations_Attributes");
		super.completeAttributeOperations_Attributes(model, assignment, context, acceptor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.editor.contentassist.AbstractJavaBasedContentProposalProvider#completeKeyword(org.eclipse.xtext.Keyword,
	 * org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext, org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor)
	 */
	@Override
	public void completeKeyword(Keyword keyword, ContentAssistContext contentAssistContext,
			ICompletionProposalAcceptor acceptor) {
		// System.err.println("completeKeyword('" + keyword.getValue() + "')");
		// if(keyword.getValue().equals("+>"))
		// System.err.println("Oy !!!");
		super.completeKeyword(keyword, contentAssistContext, acceptor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.cloudsmith.geppetto.pp.dsl.ui.contentassist.AbstractPPProposalProvider#completePuppetManifest_Statements(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.xtext.Assignment, org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext,
	 * org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor)
	 */
	@Override
	public void completePuppetManifest_Statements(EObject model, Assignment assignment, ContentAssistContext context,
			ICompletionProposalAcceptor acceptor) {
		super.completePuppetManifest_Statements(model, assignment, context, acceptor);

		ppFinder.configure(model.eResource());

		for(IEObjectDescription d : ppFinder.findDefinitions(model, null))
			acceptor.accept(createCompletionProposal(converter.toString(d.getQualifiedName()), context));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.editor.contentassist.AbstractJavaBasedContentProposalProvider#completeRuleCall(org.eclipse.xtext.RuleCall,
	 * org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext, org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor)
	 */
	@Override
	public void completeRuleCall(RuleCall ruleCall, ContentAssistContext contentAssistContext,
			ICompletionProposalAcceptor acceptor) {
		// AbstractRule calledRule = ruleCall.getRule();
		// String methodName = "complete_" + calledRule.getName();
		// System.err.println("completeRuleCall('" + methodName + "')");

		super.completeRuleCall(ruleCall, contentAssistContext, acceptor);
	};
}
