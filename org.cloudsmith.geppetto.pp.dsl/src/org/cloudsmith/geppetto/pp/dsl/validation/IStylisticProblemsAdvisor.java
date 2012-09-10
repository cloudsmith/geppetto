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
 * An interface for stylistic problems preferences.
 * 
 */
public interface IStylisticProblemsAdvisor {

	/**
	 * How an (optional) default that is not placed last should be validated for a case expression.
	 * 
	 * @return
	 */
	public ValidationPreference caseDefaultShouldAppearLast();

	/**
	 * How the 'ensure' property should be validated if not placed first among a resource's properties.
	 */
	public ValidationPreference ensureShouldAppearFirstInResource();

	/**
	 * How to 'validate' the presence of ML comments.
	 */
	public ValidationPreference mlComments();

	/**
	 * How to validate right to left relationships ( e.g. a <- b and a <~ b)
	 */
	ValidationPreference rightToLeftRelationships();

	/**
	 * How an (almost required) default that is not placed last should be validated for a selector expression.
	 * 
	 * @return
	 */
	public ValidationPreference selectorDefaultShouldAppearLast();

}
