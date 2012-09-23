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
 * A default implementation of IPotentialProblemsAdvisor that returns Warnings for all potential problems, and
 * Ignore for all stylistic problems
 */
public class DefaultPotentialProblemsAdvisor implements IPotentialProblemsAdvisor, IStylisticProblemsAdvisor {

	@Override
	public ValidationPreference assignmentToVarNamedString() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference booleansInStringForm() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference caseDefaultShouldAppearLast() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference circularDependencyPreference() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference dqStringNotRequired() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference dqStringNotRequiredVariable() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference ensureShouldAppearFirstInResource() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference interpolatedNonBraceEnclosedHyphens() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference missingDefaultInSelector() {
		return ValidationPreference.WARNING;
	}

	@Override
	public ValidationPreference mlComments() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference rightToLeftRelationships() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference selectorDefaultShouldAppearLast() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference unbracedInterpolation() {
		return ValidationPreference.IGNORE;
	}

	@Override
	public ValidationPreference unquotedResourceTitles() {
		return ValidationPreference.IGNORE;
	}

}
