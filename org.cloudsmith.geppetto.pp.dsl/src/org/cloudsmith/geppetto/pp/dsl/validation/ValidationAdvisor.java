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

import org.cloudsmith.geppetto.pp.dsl.validation.IValidationAdvisor.ComplianceLevel;

public class ValidationAdvisor {

	public static class BaseValidationAdvisor implements IPotentialProblemsAdvisor, IStylisticProblemsAdvisor {

		private IPotentialProblemsAdvisor problemsAdvisor;

		protected BaseValidationAdvisor(IPotentialProblemsAdvisor problemsAdvisor) {
			this.problemsAdvisor = problemsAdvisor;
		}

		@Override
		public ValidationPreference assignmentToVarNamedString() {
			return problemsAdvisor.assignmentToVarNamedString();
		}

		@Override
		public ValidationPreference booleansInStringForm() {
			return problemsAdvisor.booleansInStringForm();
		}

		@Override
		public ValidationPreference caseDefaultShouldAppearLast() {
			return problemsAdvisor.caseDefaultShouldAppearLast();
		}

		@Override
		public ValidationPreference circularDependencyPreference() {
			return problemsAdvisor.circularDependencyPreference();
		}

		@Override
		public ValidationPreference dqStringNotRequired() {
			return problemsAdvisor.dqStringNotRequired();
		}

		@Override
		public ValidationPreference dqStringNotRequiredVariable() {
			return problemsAdvisor.dqStringNotRequiredVariable();
		}

		@Override
		public ValidationPreference ensureShouldAppearFirstInResource() {
			return problemsAdvisor.ensureShouldAppearFirstInResource();
		}

		@Override
		public ValidationPreference interpolatedNonBraceEnclosedHyphens() {
			return problemsAdvisor.interpolatedNonBraceEnclosedHyphens();
		}

		@Override
		public ValidationPreference missingDefaultInSelector() {
			return problemsAdvisor.missingDefaultInSelector();
		}

		@Override
		public ValidationPreference mlComments() {
			return problemsAdvisor.mlComments();
		}

		@Override
		public ValidationPreference rightToLeftRelationships() {
			return problemsAdvisor.rightToLeftRelationships();
		}

		@Override
		public ValidationPreference selectorDefaultShouldAppearLast() {
			return problemsAdvisor.selectorDefaultShouldAppearLast();
		}

		@Override
		public ValidationPreference unbracedInterpolation() {
			return problemsAdvisor.unbracedInterpolation();
		}

		@Override
		public ValidationPreference unquotedResourceTitles() {
			return problemsAdvisor.unquotedResourceTitles();
		}

	}

	/**
	 * Validation Advisor for Puppet 2.6
	 * 
	 */
	public static class ValidationAdvisor_2_6 extends BaseValidationAdvisor implements IValidationAdvisor {
		/**
		 * @param problemsAdvisor
		 */
		protected ValidationAdvisor_2_6(IPotentialProblemsAdvisor problemsAdvisor) {
			super(problemsAdvisor);
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowExtendedDependencyTypes() {
			return false;
		}

		/**
		 * @returns true
		 */
		@Override
		public boolean allowHashInSelector() {
			return true;
		}

		/**
		 * @returns false
		 */
		@Override
		public boolean allowInheritanceFromParameterizedClass() {
			return false;
		}

		/**
		 * @returns true
		 */
		@Override
		public boolean allowMoreThan2AtInSequence() {
			return true;
		}

		@Override
		public boolean allowUnless() {
			return false;
		}

		@Override
		public boolean allowUnquotedQualifiedResourceNames() {
			return false;
		}

		/**
		 * @returns ValidationPreference.ERROR
		 */
		@Override
		public ValidationPreference definitionArgumentListEndComma() {
			return ValidationPreference.ERROR;
		}

		@Override
		public ValidationPreference definitionParamterMissingDollar() {
			return ValidationPreference.WARNING;
		}

		/**
		 * @returns ValidationPreference.WARNING
		 */
		@Override
		public ValidationPreference hyphensInNames() {
			return ValidationPreference.WARNING;
		}

		/**
		 * @returns ValidationPreference.ERROR
		 */
		@Override
		public ValidationPreference periodInCase() {
			return ValidationPreference.ERROR;
		}

		/**
		 * @returns ValidationPreference.IGNORE
		 */
		@Override
		public ValidationPreference unqualifiedVariables() {
			return ValidationPreference.IGNORE;
		}
	}

	/**
	 * Validation Advisor for Puppet 2.7
	 * 
	 */
	public static class ValidationAdvisor_2_7 extends ValidationAdvisor_2_6 implements IValidationAdvisor {

		/**
		 * @param problemsAdvisor
		 */
		protected ValidationAdvisor_2_7(IPotentialProblemsAdvisor problemsAdvisor) {
			super(problemsAdvisor);
		}

		@Override
		public boolean allowUnquotedQualifiedResourceNames() {
			return true;
		}

		/**
		 * @returns ValidationPreference.WARNING
		 */
		@Override
		public ValidationPreference definitionArgumentListEndComma() {
			return ValidationPreference.WARNING;
		}

		/**
		 * @returns ValidationPreference.IGNORE
		 */
		@Override
		public ValidationPreference periodInCase() {
			return ValidationPreference.IGNORE;
		}

		/**
		 * @returns ValidationPreference.WARNING
		 */
		@Override
		public ValidationPreference unqualifiedVariables() {
			return ValidationPreference.WARNING;
		}

	}

	/**
	 * Validation Advisor for Puppet 3.0
	 * 
	 */
	public static class ValidationAdvisor_3_0 extends ValidationAdvisor_2_7 implements IValidationAdvisor {
		/**
		 * @param problemsAdvisor
		 */
		protected ValidationAdvisor_3_0(IPotentialProblemsAdvisor problemsAdvisor) {
			super(problemsAdvisor);
		}

		/**
		 * @returns true
		 */
		@Override
		public boolean allowExtendedDependencyTypes() {
			return true;
		}

		/**
		 * @returns true, but unsure if really supported
		 */
		@Override
		public boolean allowInheritanceFromParameterizedClass() {
			return true;
		}

		@Override
		public boolean allowUnless() {
			return true;
		}

		/**
		 * @returns ValidationPreference.IGNORE
		 */
		@Override
		public ValidationPreference definitionArgumentListEndComma() {
			return ValidationPreference.IGNORE;
		}

		@Override
		public ValidationPreference definitionParamterMissingDollar() {
			return ValidationPreference.ERROR;
		}

		/**
		 * @returns ValidationPreference.ERROR
		 */
		@Override
		public ValidationPreference hyphensInNames() {
			return ValidationPreference.ERROR;
		}

		/**
		 * @returns ValidationPreference.ERROR
		 */
		@Override
		public ValidationPreference unqualifiedVariables() {
			return ValidationPreference.ERROR;
		}

	}

	public static IValidationAdvisor create(ComplianceLevel level, IPotentialProblemsAdvisor problemsAdvisor) {
		switch(level) {
			case PUPPET_2_6:
				return new ValidationAdvisor_2_6(problemsAdvisor);
			case PUPPET_2_7:
				return new ValidationAdvisor_2_7(problemsAdvisor);
			case PUPPET_3_0:
				return new ValidationAdvisor_3_0(problemsAdvisor);
		}
		throw new IllegalArgumentException("Unsupported compliance level");
	}

}
