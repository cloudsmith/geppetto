/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.pp.dsl.validation;

import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * Helps with reserved names.
 * 
 */
public class PPReservedNameHelper {

	public static final List<String> reservedClassNames = ImmutableList.of("main", "settings");

	public static boolean isReservedClassName(String s) {
		return reservedClassNames.contains(s);
	}
}
