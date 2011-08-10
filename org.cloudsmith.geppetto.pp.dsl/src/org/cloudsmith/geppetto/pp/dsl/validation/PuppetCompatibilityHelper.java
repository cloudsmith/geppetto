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
 * Class used to allow validation against older versions of Puppet (i.e. to produce warnings or errors
 * for constructs that older Puppet versions will not handle).
 * 
 * TODO: This should be controllable by user preferences. For the time being this class simply contains
 * the methods that are called from the validator. Different usage scenarios will probably need different ways
 * of setting up this class. An injected "ComaptibilityAdvisor" is probably a good idea, to be able to pick
 * up preferences from Eclipse preference store, or from someplace else.
 * 
 */
public class PuppetCompatibilityHelper {

	/**
	 * Puppet issue #5516
	 */
	public static boolean allowHashInSelector() {
		return true;
	}

	/**
	 * Puppet issue #6269
	 */
	public static boolean allowMoreThan2AtInSequence() {
		return true;
	}

	public static ValidationPreference circularDependencyPreference() {
		return ValidationPreference.WARNING;
	}
}
