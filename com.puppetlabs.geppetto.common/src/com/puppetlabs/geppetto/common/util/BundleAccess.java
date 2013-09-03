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
package com.puppetlabs.geppetto.common.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Utility class that enables some platform specific features needed by the
 * Geppetto headless parts when running in a Eclipse environment. The interface
 * must be access using the Guice injections.
 */
public interface BundleAccess {
	/**
	 * @param option
	 *            The option to retrieve
	 * @return The current setting of the Platform.getDebugOption(String) if the platform is available. If not, this
	 *         method will always return <code>false</code>
	 */
	String getDebugOption(String option);

	/**
	 * Uses the Eclipse OSGi platform to find a bundle and then find a resource in that bundle. The method will
	 * return <code>null</code> when the Eclipse Platform is unavailable
	 * 
	 * @param clazz
	 *            The class that determines what bundle to use
	 * @param bundleRelativeResourcePath
	 *            A path relative to the bundle of the class
	 * @return The File for a bundle relative resource path or <code>null</code> if the Eclipse OSGi platform
	 *         is unavailable or if the resource cannot be found.
	 * @throws IOException
	 */
	File getFileFromClassBundle(Class<?> clazz, String bundleRelativeResourcePath) throws IOException;

	/**
	 * Resolves the given URL to a file. If the Eclipse platform is available then URLs using the scheme
	 * &quot;bundleresource&quot; can be resolved. If not, only &quot;file&quot; scheme URLs will be
	 * resolved.
	 * 
	 * @param resourceURL
	 *            The URL to convert
	 * @return The given URL as a File or <code>null</code> if the URL cannot be represented as a file.
	 * @throws IOException
	 */
	File getResourceAsFile(URL resourceURL) throws IOException;

	/**
	 * @return The setting of the Platform.inDebugMode() flag if the Platform is available or <code>false</code> if not.
	 */
	boolean inDebugMode();
}
