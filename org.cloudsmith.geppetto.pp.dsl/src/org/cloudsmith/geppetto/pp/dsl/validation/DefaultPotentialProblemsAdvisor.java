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
 * A default implementation of IPotentialProblemsAdvisor that returns Warnings for all potential problems.
 */
public class DefaultPotentialProblemsAdvisor implements IPotentialProblemsAdvisor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor#circularDependencyPreference()
	 */
	@Override
	public ValidationPreference circularDependencyPreference() {
		return ValidationPreference.WARNING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor#interpolatedNonBraceEnclosedHyphens()
	 */
	@Override
	public ValidationPreference interpolatedNonBraceEnclosedHyphens() {
		return ValidationPreference.WARNING;
	}

}
