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
package com.puppetlabs.geppetto.pp.dsl.linking;

import static com.puppetlabs.geppetto.pp.adapters.ClassifierAdapter.COLLECTOR_IS_REGULAR;
import static com.puppetlabs.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_BAD;
import static com.puppetlabs.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_CLASSPARAMS;
import static com.puppetlabs.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_DEFAULT;
import static com.puppetlabs.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_OVERRIDE;
import static com.puppetlabs.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_REGULAR;

import com.puppetlabs.geppetto.pp.AtExpression;
import com.puppetlabs.geppetto.pp.CollectExpression;
import com.puppetlabs.geppetto.pp.Expression;
import com.puppetlabs.geppetto.pp.LiteralClass;
import com.puppetlabs.geppetto.pp.LiteralNameOrReference;
import com.puppetlabs.geppetto.pp.ResourceExpression;
import com.puppetlabs.geppetto.pp.VirtualNameOrReference;
import com.puppetlabs.geppetto.pp.adapters.ClassifierAdapter;
import com.puppetlabs.geppetto.pp.adapters.ClassifierAdapterFactory;
import com.puppetlabs.geppetto.pp.dsl.validation.PPPatternHelper;
import org.eclipse.emf.ecore.EObject;

import com.google.inject.Inject;

/**
 * Classifies PP model objects.
 * 
 */
public class PPClassifier {
	@Inject
	private PPPatternHelper patternHelper;

	private void _classify(CollectExpression o) {
		int resourceType = RESOURCE_IS_BAD; // unknown at this point
		final Expression resourceExpr = o.getClassReference();
		String resourceTypeName = null;
		if(resourceExpr instanceof LiteralNameOrReference) {
			resourceType = COLLECTOR_IS_REGULAR;
			resourceTypeName = ((LiteralNameOrReference) resourceExpr).getValue();
		}
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(o);
		adapter.setClassifier(resourceType);
		adapter.setResourceType(null); // unresolved
		adapter.setResourceTypeName(resourceTypeName);
	}

	/**
	 * Classifies the resource and sets the basic (non linking) parameters.
	 * 
	 * @param o
	 */
	private void _classify(ResourceExpression o) {
		// A regular resource must have a classname
		// Use of class reference is deprecated
		// classname : NAME | "class" | CLASSNAME

		int resourceType = RESOURCE_IS_BAD; // unknown at this point
		final Expression resourceExpr = o.getResourceExpr();
		String resourceTypeName = null;

		if(resourceExpr instanceof LiteralNameOrReference) {
			LiteralNameOrReference resourceTypeExpr = (LiteralNameOrReference) resourceExpr;
			resourceTypeName = resourceTypeExpr.getValue();
		}
		else if(resourceExpr instanceof LiteralClass) {
			resourceTypeName = "class";
		}
		else if(resourceExpr instanceof VirtualNameOrReference) {
			VirtualNameOrReference vn = (VirtualNameOrReference) resourceExpr;
			resourceTypeName = vn.getValue();
		}
		if(resourceTypeName != null) {
			if("class".equals(resourceTypeName))
				resourceType = RESOURCE_IS_CLASSPARAMS;
			else if(patternHelper.isCLASSREF(resourceTypeName))
				resourceType = RESOURCE_IS_DEFAULT;
			else if(patternHelper.isNAME(resourceTypeName) || patternHelper.isCLASSNAME(resourceTypeName))
				resourceType = RESOURCE_IS_REGULAR;
			// else the resource is BAD
		}
		else if(resourceExpr instanceof AtExpression) {
			resourceType = RESOURCE_IS_OVERRIDE;
			AtExpression at = (AtExpression) resourceExpr;
			Expression left = at.getLeftExpr();
			if(left instanceof LiteralNameOrReference)
				resourceTypeName = ((LiteralNameOrReference) left).getValue();
		}
		else if(resourceExpr instanceof CollectExpression) {
			resourceType = RESOURCE_IS_OVERRIDE;
			CollectExpression collect = (CollectExpression) resourceExpr;
			Expression classReference = collect.getClassReference();
			if(classReference instanceof LiteralNameOrReference)
				resourceTypeName = ((LiteralNameOrReference) classReference).getValue();
		}

		/*
		 * IMPORTANT: set the validated classifier to enable others to more quickly determine the type of
		 * resource, and its typeName (what it is a reference to).
		 */
		ClassifierAdapter adapter = ClassifierAdapterFactory.eINSTANCE.adapt(o);
		adapter.setClassifier(resourceType);
		adapter.setResourceType(null);
		adapter.setResourceTypeName(resourceTypeName);

	}

	public void classify(EObject o) {
		// currently only one type to classify, so polymorphic dispatching is overkill, but
		// follow the style
		if(o instanceof ResourceExpression)
			_classify((ResourceExpression) o);
		else if(o instanceof CollectExpression)
			_classify((CollectExpression) o);
	}

}
