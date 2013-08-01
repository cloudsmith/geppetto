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
package org.cloudsmith.geppetto.injectable;

import org.cloudsmith.geppetto.injectable.standalone.impl.StandaloneCommonModule;

import com.google.inject.Module;

/**
 * Provides the common {@link Module} in standalone (non Eclipse) environments
 */
public class CommonModuleProvider {
	private static Module standaloneModule = new StandaloneCommonModule();

	/**
	 * @return the commonModule
	 */
	public static synchronized Module getCommonModule() {
		return standaloneModule;
	}
}
