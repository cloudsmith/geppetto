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
		public ValidationPreference booleansInStringForm() {
			return problemsAdvisor.booleansInStringForm();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cloudsmith.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor#caseDefaultShouldAppearLast()
		 */
		@Override
		public ValidationPreference caseDefaultShouldAppearLast() {
			return problemsAdvisor.caseDefaultShouldAppearLast();
		}

		@Override
		public ValidationPreference circularDependencyPreference() {
			return problemsAdvisor.circularDependencyPreference();
		}

		@Override
		public ValidationPreference interpolatedNonBraceEnclosedHyphens() {
			return problemsAdvisor.interpolatedNonBraceEnclosedHyphens();
		}

		@Override
		public ValidationPreference missingDefaultInSelector() {
			return problemsAdvisor.missingDefaultInSelector();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cloudsmith.geppetto.pp.dsl.validation.IStylisticProblemsAdvisor#selectorDefaultShouldAppearLast()
		 */
		@Override
		public ValidationPreference selectorDefaultShouldAppearLast() {
			return problemsAdvisor.selectorDefaultShouldAppearLast();
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
		 * @returns true
		 */
		@Override
		public boolean allowHashInSelector() {
			return true;
		}

		/**
		 * @returns true
		 */
		@Override
		public boolean allowMoreThan2AtInSequence() {
			return true;
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
	 * Validation Advisor for Puppet 2.8
	 * 
	 */
	public static class ValidationAdvisor_2_8 extends ValidationAdvisor_2_7 implements IValidationAdvisor {

		/**
		 * @param problemsAdvisor
		 */
		protected ValidationAdvisor_2_8(IPotentialProblemsAdvisor problemsAdvisor) {
			super(problemsAdvisor);
		}

		/**
		 * @returns ValidationPreference.IGNORE
		 */
		@Override
		public ValidationPreference definitionArgumentListEndComma() {
			return ValidationPreference.IGNORE;
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
			case PUPPET_2_8:
				return new ValidationAdvisor_2_8(problemsAdvisor);
		}
		throw new IllegalArgumentException("Unsupported compliance level");
	}

}
