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
package com.puppetlabs.geppetto.injectable;

import com.puppetlabs.geppetto.injectable.eclipse.impl.EclipseCommonModule;

import com.google.inject.Module;

public class CommonModuleProvider {
	private static Module eclipseModule = new EclipseCommonModule();

	/**
	 * @return the commonModule
	 */
	public static synchronized Module getCommonModule() {
		return eclipseModule;
	}
}
