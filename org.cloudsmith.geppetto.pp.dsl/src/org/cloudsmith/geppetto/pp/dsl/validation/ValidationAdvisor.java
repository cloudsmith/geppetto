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

	/**
	 * Validation Advisor for Puppet 2.6
	 * 
	 */
	public static class ValidationAdvisor_2_6 implements IValidationAdvisor {

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

		/**
		 * @returns ValidationPreference.WARNING
		 */
		@Override
		public ValidationPreference circularDependencyPreference() {
			return ValidationPreference.WARNING;
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
		 * @returns ValidationPreference.ERROR
		 */
		@Override
		public ValidationPreference unqualifiedVariables() {
			return ValidationPreference.ERROR;
		}
	}

	public static IValidationAdvisor create(ComplianceLevel level) {
		switch(level) {
			case PUPPET_2_6:
				return new ValidationAdvisor_2_6();
			case PUPPET_2_7:
				return new ValidationAdvisor_2_7();
			case PUPPET_2_8:
				return new ValidationAdvisor_2_8();
		}
		throw new IllegalArgumentException("Unsupported compliance level");
	}

}
