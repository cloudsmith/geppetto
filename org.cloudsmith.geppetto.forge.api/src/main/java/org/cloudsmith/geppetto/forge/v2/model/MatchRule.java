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
package org.cloudsmith.geppetto.forge.v2.model;


/**
 * Rule use by {@link VersionRequirement}
 */
public enum MatchRule {
	/**
	 * Version must match exactly the specified version.
	 */
	PERFECT("=="),

	/**
	 * Version must be at least at the version specified, or at a higher service level (major and minor version levels must equal the specified
	 * version).
	 */
	EQUIVALENT("="),

	/**
	 * Version must be at least at the version specified, or at a higher service level or minor level (major version level must equal the specified
	 * version).
	 */
	COMPATIBLE("~"),

	/**
	 * Version must be less than the version specified.
	 */
	LESS("<"),

	/**
	 * Version must be at less or equal to the version specified.
	 */
	LESS_OR_EQUAL("<="),

	/**
	 * Version must be greater than the version specified.
	 */
	GREATER(">"),

	/**
	 * Version must be greater or equal to the version specified.
	 */
	GREATER_OR_EQUAL(">=");

	private final String literal;

	private MatchRule(String literal) {
		this.literal = literal;
	}

	@Override
	public String toString() {
		return literal;
	}
}
