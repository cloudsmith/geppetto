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

import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.eval.PPStringConstantEvaluator;
import org.cloudsmith.geppetto.pp.dsl.linking.PPFinder;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
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
		System.err.println("complete_AttributeOperation");
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
		System.err.println("complete_AttributeOperations");
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
		System.err.println("complete_ResourceBody");
		super.complete_ResourceBody(model, ruleCall, context, acceptor);
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
		System.err.println("complete_unionNameOrReference");

		ResourceBody resourceBody = null;
		if(model.eClass() == PPPackage.Literals.RESOURCE_BODY) {
			resourceBody = (ResourceBody) model;
		}
		else if(model.eClass() == PPPackage.Literals.ATTRIBUTE_OPERATION) {
			resourceBody = (ResourceBody) model.eContainer().eContainer();
		}
		else
			return;

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

			// ICompletionProposal completionProposal = createCompletionProposal("test proposal", context);
			// acceptor.accept(completionProposal);
		}
		catch(ClassCastException e) {
			// ignore, something is in a weird state, simply ignore proposals
		}
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
		// TODO Auto-generated method stub
		System.err.println("completeAttributeOperation_Key");
		super.completeAttributeOperation_Key(model, assignment, context, acceptor);
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
		System.err.println("completeAttributeOperations_Attributes");
		super.completeAttributeOperations_Attributes(model, assignment, context, acceptor);
	}
}
