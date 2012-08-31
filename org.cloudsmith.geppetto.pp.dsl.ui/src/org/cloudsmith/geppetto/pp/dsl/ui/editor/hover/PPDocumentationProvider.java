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
import java.util.List;

import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.dsl.adapters.CrossReferenceAdapter;
import org.cloudsmith.geppetto.pp.pptp.IDocumented;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.util.PolymorphicDispatcher;

/**
 * @author henrik
 * 
 */
public class PPDocumentationProvider implements IEObjectDocumentationProvider {

	private PolymorphicDispatcher<String> documentationDispatcher = new PolymorphicDispatcher<String>(
		"_document", 1, 2, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<String> get()) {
		@Override
		protected String handleNoSuchMethod(Object... params) {
			return null;
		}
	};

	protected String _document(EStructuralFeature feature, LiteralNameOrReference o) {

		switch(feature.getFeatureID()) {
			case PPPackage.RESOURCE_EXPRESSION__RESOURCE_EXPR:
				return getCrossReferenceDocumentation(o);
		}
		return null;
	}

	protected String _document(IDocumented o) {
		return document(o.getDocumentation());
	}

	protected String _document(Object o) {
		return null;
	}

	protected String _document(String o) {
		// turn puppet internal doc format (from Ruby) into HTML
		StringBuilder builder = new StringBuilder();
		builder.append("<pre>");
		builder.append(o);
		builder.append("</pre>");
		return builder.toString();
	}

	protected String _document(VariableExpression o) {
		return getCrossReferenceDocumentation(o);
	}

	public String document(Object o) {
		if(o == null)
			return null;
		String result = documentationDispatcher.invoke(o);
		return result == null && o instanceof EObject
				? documentationDispatcher.invoke(((EObject) o).eContainingFeature(), o)
				: result;

	}

	private String getCrossReferenceDocumentation(EObject o) {
		List<IEObjectDescription> xrefs = CrossReferenceAdapter.get(o);
		if(xrefs.size() > 1) {
			return "Ambiguous reference";
		}
		if(xrefs.size() == 0) {
			return "Unresolved";
		}
		IEObjectDescription ref = xrefs.get(0);

		EObject x = ref.getEObjectOrProxy();
		if(x.eIsProxy())
			x = EcoreUtil.resolve(x, o);
		return document(x);

	}

	/**
	 * @see org.eclipse.xtext.documentation.IEObjectDocumentationProvider#getDocumentation(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public String getDocumentation(EObject o) {
		return document(o);
	}

}
