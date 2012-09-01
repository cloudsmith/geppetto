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
package org.cloudsmith.geppetto.pp.dsl.ui.editor.hover;

import java.util.Collections;

import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;
import org.eclipse.xtext.util.PolymorphicDispatcher;

/**
 * @author henrik
 * 
 */
public class PPHoverProvider extends DefaultEObjectHoverProvider {

	private PolymorphicDispatcher<Boolean> hoverDispatcher = new PolymorphicDispatcher<Boolean>(
		"_hover", 1, 1, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<Boolean> get()) {
		@Override
		protected Boolean handleNoSuchMethod(Object... params) {
			return false;
		}
	};

	protected Boolean _hover(AttributeOperation o) {
		return true;
	}

	protected Boolean _hover(LiteralNameOrReference o) {
		EStructuralFeature feature = o.eContainingFeature();
		if(feature == PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR)
			return true;
		// System.err.println("feature " + feature.getName());
		return false;
	}

	protected Boolean _hover(VariableExpression o) {
		return true;
	}

	/**
	 * @see org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider#getFirstLine(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected String getFirstLine(EObject o) {
		return "PP:" + super.getFirstLine(o);
	}

	/**
	 * @see org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider#hasHover(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected boolean hasHover(EObject o) {
		// System.out.println("Hover query for: " + o.getClass()); // For debugging, finding what to react to
		return hoverDispatcher.invoke(o) || super.hasHover(o);
	}
}
