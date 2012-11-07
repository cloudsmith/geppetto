/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.validation;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic;

/**
 * Helper class for validation related tasks.
 * 
 */
public class PPValidationUtils {

	/**
	 * Returns true if there are hard syntax errors in the resource.
	 * 
	 * @param r
	 * @return
	 */
	public static boolean hasSyntaxErrors(XtextResource r) {
		for(Diagnostic d : r.getErrors()) {
			if(d instanceof XtextSyntaxDiagnostic) {
				return true;
			}
		}
		return false;
	}
}
