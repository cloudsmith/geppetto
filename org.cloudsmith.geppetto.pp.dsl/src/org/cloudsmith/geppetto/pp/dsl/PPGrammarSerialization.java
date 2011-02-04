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
package org.cloudsmith.geppetto.pp.dsl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.parsetree.reconstr.impl.DefaultTransientValueService;

public class PPGrammarSerialization extends DefaultTransientValueService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.parsetree.reconstr.ITransientValueService#isCheckElementsIndividually(org.eclipse.emf.ecore.EObject,
	 * org.eclipse.emf.ecore.EStructuralFeature)
	 */
	@Override
	public boolean isCheckElementsIndividually(EObject owner, EStructuralFeature feature) {
		return true;
	}

}
