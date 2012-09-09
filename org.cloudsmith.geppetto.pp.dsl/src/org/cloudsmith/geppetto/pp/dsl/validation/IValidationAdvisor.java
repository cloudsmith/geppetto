/**
 * Copyright (c) 2011, 2012 Cloudsmith Inc. and other contributors, as listed below.
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
 * An advisor to validation. Different implementations of this class capture the validation rules specific
 * to a language version.
 * 
 */
public interface IValidationAdvisor extends IPotentialProblemsAdvisor {
	public enum ComplianceLevel {
		PUPPET_2_6, PUPPET_2_7, PUPPET_3_0
	}

	/**
	 * If 3.0 extended dependency types should be allowed
	 * (resource | resourceref | collection | variable | quoted text | selector | case statement | hasharrayaccesses)
	 * See geppetto Issue #400.
	 */
	public boolean allowExtendedDependencyTypes();

	/**
	 * Should Hash be allowed in a selector.
	 * Puppet issue #5516
	 */
	public boolean allowHashInSelector();

	/**
	 * Before 3.0 and hiera support, a class can not inherit from a parameterized class.
	 */
	public boolean allowInheritanceFromParameterizedClass();

	/**
	 * Should more than 2 at (i.e. []) operators be allowed in sequence e.g. $a[x][y][z]
	 * Puppet issue #6269
	 */
	public boolean allowMoreThan2AtInSequence();

	/**
	 * The "unless" statement was added in Puppet 3.0.
	 * 
	 * @return
	 */
	public boolean allowUnless();

	/**
	 * Prior to 2.7 it was not possible to use unquoted qualified resource names.
	 * 
	 * @return
	 */
	public boolean allowUnquotedQualifiedResourceNames();

	/**
	 * Prior to version 2.7.8, an optional end comma in a definition argument list causes parse exception.
	 * 
	 * @return
	 */
	public ValidationPreference definitionArgumentListEndComma();

	/**
	 * Prior to 3.0, a missing $ in a definition parameter name declaration was deprecated.
	 * In 3.0 it is an error.
	 */
	public ValidationPreference definitionParamterMissingDollar();

	/**
	 * Hyphens in names are deprecated
	 * Puppet issue #10146
	 * And will be errors in later releases.
	 */
	public ValidationPreference hyphensInNames();

	/**
	 * Prior to 2.7 (?) it was not possible to have case labels with a ".".
	 * 
	 * @return
	 */
	public ValidationPreference periodInCase();

	/**
	 * How should relationships goign right to left be reported.
	 */
	public ValidationPreference rightToLeftRelationships();

	/**
	 * How should unqualified variable references be reported (ignore, warning, error).
	 */
	public ValidationPreference unqualifiedVariables();

}
