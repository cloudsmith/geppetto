/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.linking;

import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.COLLECTOR_IS_REGULAR;
import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_BAD;
import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_CLASSPARAMS;
import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_DEFAULT;
import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_OVERRIDE;
import static org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter.RESOURCE_IS_REGULAR;

import org.cloudsmith.geppetto.pp.AtExpression;
import org.cloudsmith.geppetto.pp.CollectExpression;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.LiteralClass;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.VirtualNameOrReference;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.validation.PPPatternHelper;
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
