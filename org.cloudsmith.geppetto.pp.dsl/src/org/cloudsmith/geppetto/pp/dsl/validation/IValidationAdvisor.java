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
 * An advisor to validation. Different implementations of this class capture the validation rules specific
 * to a language version.
 * 
 */
public interface IValidationAdvisor {
	public enum ComplianceLevel {
		PUPPET_2_6, PUPPET_2_7, PUPPET_2_8
	}

	/**
	 * Should Hash be allowed in a selector.
	 * Puppet issue #5516
	 */
	public boolean allowHashInSelector();

	/**
	 * Should more than 2 at (i.e. []) operators be allowed in sequence e.g. $a[x][y][z]
	 * Puppet issue #6269
	 */
	public boolean allowMoreThan2AtInSequence();

	/**
	 * Prior to 2.7 it was not possible to use unquoted qualified resource names.
	 * 
	 * @return
	 */
	public boolean allowUnquotedQualifiedResourceNames();

	/**
	 * How should circular module dependencies be reported (ignore, warning, error).
	 * 
	 * @return
	 */
	public ValidationPreference circularDependencyPreference();

	/**
	 * How should unqualified variable references be reported (ignore, warning, error).
	 * 
	 * @return
	 */
	public ValidationPreference unqualifiedVariables();

}
