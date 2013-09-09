/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs - initial API and implementation
 */
package com.puppetlabs.geppetto.injectable.eclipse.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.URIUtil;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;

import com.puppetlabs.geppetto.common.util.BundleAccess;

public class EclipseBundleAccess implements BundleAccess {
	private static Bundle getBundleForClass(Class<?> clazz) {
		ClassLoader bld = clazz.getClassLoader();
		if(bld instanceof BundleReference)
			return ((BundleReference) bld).getBundle();
		throw new IllegalArgumentException("Class " + clazz.getName() + " is not loaded by a BundleClassLoader");
	}

	private File getBundleResourceAsFile(Bundle bundle, String bundleRelativeResourcePath) throws IOException {
		try {
			URL resourceURL = FileLocator.find(bundle, new Path(bundleRelativeResourcePath), null);
			return resourceURL == null
					? null
					: getResourceAsFile(resourceURL);
		}
		catch(Exception e) {
			throw new IllegalStateException("Failed to convert resource URL to URI", e);
		}
	}

	@Override
	public String getDebugOption(String option) {
		return Platform.getDebugOption(option);
	}

	@Override
	public File getFileFromClassBundle(Class<?> clazz, String bundleRelativeResourcePath) throws IOException {
		return getBundleResourceAsFile(getBundleForClass(clazz), bundleRelativeResourcePath);
	}

	@Override
	public File getResourceAsFile(URL resourceURL) throws IOException {
		resourceURL = FileLocator.toFileURL(resourceURL);
		try {
			return new File(URIUtil.toURI(resourceURL));
		}
		catch(URISyntaxException e) {
			throw new IOException("Failed to convert resource URL to File URI", e);
		}
	}

	@Override
	public boolean inDebugMode() {
		return Platform.inDebugMode();
	}
}
