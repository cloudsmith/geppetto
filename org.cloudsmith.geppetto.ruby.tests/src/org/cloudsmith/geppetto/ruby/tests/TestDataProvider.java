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
package org.cloudsmith.geppetto.ruby.tests;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.URIUtil;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;

public class TestDataProvider {

	/**
	 * Get a {@link Bundle} containing the specified class.
	 * 
	 * @param clazz
	 *            a class for which the containing bundle is to be determined
	 * @return the bundle containing the class
	 */
	private static Bundle getBundleForClass(Class<?> clazz) {
		try {
			return ((BundleReference) clazz.getClassLoader()).getBundle();
		}
		catch(ClassCastException e) {
			throw new IllegalStateException("Failed to get reference to the containing bundle", e);
		}
	}

	/**
	 * Get a resource found in the specified bundle as a File. Extracting it
	 * into the filesystem if necessary.
	 * 
	 * @param bundle
	 *            the bundle containing the resource
	 * @param bundleRelativeResourcePath
	 *            bundle relative path of the resource
	 * @return a {@link File} incarnation of the resource
	 * @throws IOException
	 */
	private static File getBundleResourceAsFile(Bundle bundle, IPath bundleRelativeResourcePath) throws IOException {
		URL resourceURL = FileLocator.find(bundle, bundleRelativeResourcePath, null);
		if(resourceURL == null)
			return null;

		resourceURL = FileLocator.toFileURL(resourceURL);

		try {
			return new File(URIUtil.toURI(resourceURL));
		}
		catch(URISyntaxException e) {
			throw new IllegalStateException("Failed to convert resource URL to URI", e);
		}
	}

	public static File getTestFile(IPath testBundleRelativePath) throws IOException {
		return getBundleResourceAsFile(getBundleForClass(TestDataProvider.class), testBundleRelativePath);
	}

}
