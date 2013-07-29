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
package org.cloudsmith.geppetto.common.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.util.Map;

/**
 * Utility class that enables some platform specific features needed by the
 * Geppetto headless parts when running in a Eclipse environment
 */
public class EclipseUtils {
	private static Method BUNDLE_REFERENCE__GET_BUNDLE = null;

	private static Method FILE_LOCATOR__FIND = null;

	private static Method FILE_LOCATOR__TO_FILE_URL = null;

	private static Method URI_UTIL__TO_URI = null;

	private static Method PLATFORM__GET_DEBUG_OPTION = null;

	private static Method PLATFORM__IN_DEBUG_MODE = null;

	private static Constructor<?> PATH_CTOR = null;

	static {
		try {
			Class<?> BUNDLE_CLASS = Class.forName("org.osgi.framework.Bundle");
			Class<?> BUNDLE_REFERENCE_CLASS = Class.forName("org.osgi.framework.BundleReference");
			BUNDLE_REFERENCE__GET_BUNDLE = BUNDLE_REFERENCE_CLASS.getMethod("getBundle");

			Class<?> PATH_CLASS = Class.forName("org.eclipse.core.runtime.Path");
			PATH_CTOR = PATH_CLASS.getConstructor(String.class);

			Class<?> IPATH_CLASS = Class.forName("org.eclipse.core.runtime.IPath");
			Class<?> FILE_LOCATOR_CLASS = Class.forName("org.eclipse.core.runtime.FileLocator");
			FILE_LOCATOR__FIND = FILE_LOCATOR_CLASS.getMethod("find", BUNDLE_CLASS, IPATH_CLASS, Map.class);
			FILE_LOCATOR__TO_FILE_URL = FILE_LOCATOR_CLASS.getMethod("toFileURL", URL.class);

			Class<?> URI_UTIL_CLASS = Class.forName("org.eclipse.core.runtime.URIUtil");
			URI_UTIL__TO_URI = URI_UTIL_CLASS.getMethod("toURI", URL.class);

			Class<?> PLATFORM_CLASS = Class.forName("org.eclipse.core.runtime.Platform");
			PLATFORM__GET_DEBUG_OPTION = PLATFORM_CLASS.getMethod("getDebugOption", String.class);
			PLATFORM__IN_DEBUG_MODE = PLATFORM_CLASS.getMethod("inDebugMode");
		}
		catch(Exception e) {
		}
	}

	private static Object getBundleForClass(Class<?> clazz) {
		try {
			return BUNDLE_REFERENCE__GET_BUNDLE.invoke(clazz.getClassLoader());
		}
		catch(Exception e) {
			throw new IllegalStateException("Failed to get reference to the containing bundle", e);
		}
	}

	private static File getBundleResourceAsFile(Object bundle, String bundleRelativeResourcePath) throws IOException {
		try {
			URL resourceURL = (URL) FILE_LOCATOR__FIND.invoke(
				null, bundle, PATH_CTOR.newInstance(bundleRelativeResourcePath), null);
			return resourceURL == null
					? null
					: getResourceAsFile(resourceURL);
		}
		catch(Exception e) {
			throw new IllegalStateException("Failed to convert resource URL to URI", e);
		}
	}

	/**
	 * @param option
	 *            The option to retrieve
	 * @return The current setting of the Platform.getDebugOption(String) if the platform is available. If not, this method will always return
	 *         <code>false</code>
	 */
	public static String getDebugOption(String option) {
		if(PLATFORM__GET_DEBUG_OPTION != null) {
			try {
				return (String) PLATFORM__GET_DEBUG_OPTION.invoke(null, option);
			}
			catch(Exception e) {
			}
		}
		return null;
	}

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
	public static File getFileFromClassBundle(Class<?> clazz, String bundleRelativeResourcePath) throws IOException {
		if(URI_UTIL__TO_URI != null)
			// OSGi is available so use the Bundle approach
			return getBundleResourceAsFile(getBundleForClass(clazz), bundleRelativeResourcePath);

		return null;
	}

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
	public static File getResourceAsFile(URL resourceURL) throws IOException {
		try {
			if(URI_UTIL__TO_URI != null) {
				// File locator etc. is available so we can resolve bundleresource scheme.
				resourceURL = (URL) FILE_LOCATOR__TO_FILE_URL.invoke(null, resourceURL);
				return new File((URI) URI_UTIL__TO_URI.invoke(null, resourceURL));
			}
			return new File(resourceURL.toURI());
		}
		catch(Exception e) {
			throw new IllegalStateException("Failed to convert resource URL to File URI", e);
		}
	}

	/**
	 * @return The setting of the Platform.inDebugMode() flag if the Platform is available or <code>false</code> if not.
	 */
	public static boolean inDebugMode() {
		if(PLATFORM__IN_DEBUG_MODE != null) {
			try {
				Boolean flag = (Boolean) PLATFORM__IN_DEBUG_MODE.invoke(null);
				return flag != null && flag.booleanValue();
			}
			catch(Exception e) {
			}
		}
		return false;
	}
}
