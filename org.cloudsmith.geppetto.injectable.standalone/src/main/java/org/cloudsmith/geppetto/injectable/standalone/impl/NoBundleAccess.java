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
package org.cloudsmith.geppetto.injectable.standalone.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.cloudsmith.geppetto.common.util.BundleAccess;

public class NoBundleAccess implements BundleAccess {
	@Override
	public String getDebugOption(String option) {
		return null;
	}

	@Override
	public File getFileFromClassBundle(Class<?> clazz, String bundleRelativeResourcePath) throws IOException {
		return null;
	}

	@Override
	public File getResourceAsFile(URL resourceURL) throws IOException {
		try {
			return new File(resourceURL.toURI());
		}
		catch(URISyntaxException e) {
			throw new IOException("Failed to convert resource URL to File URI", e);
		}
	}

	@Override
	public boolean inDebugMode() {
		return false;
	}
}
