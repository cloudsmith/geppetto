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
package org.cloudsmith.geppetto.pp.dsl.validation;

/**
 * An interface for potential problems preferences.
 * 
 */
public interface IPotentialProblemsAdvisor {

	/**
	 * How should circular module dependencies be reported (ignore, warning, error).
	 * 
	 * @return
	 */
	public ValidationPreference circularDependencyPreference();

	/**
	 * How to validate hyphens in non brace enclosed interpolations. In < 2.7 interpolation stops at a hyphen, but
	 * not in 2.7. Thus when using 2.6 code in 2.7 or vice versa, the result is different.
	 * 
	 * @return
	 */
	public ValidationPreference interpolatedNonBraceEnclosedHyphens();
}
