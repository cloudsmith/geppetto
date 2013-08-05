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

import static com.google.inject.Guice.createInjector;
import static org.cloudsmith.geppetto.injectable.CommonModuleProvider.getCommonModule;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.cloudsmith.geppetto.common.util.BundleAccess;
import org.eclipse.core.runtime.IPath;

public class TestDataProvider {

	private static File basedir;

	/**
	 * Return the project root so that we can get testData in a way that works for both
	 * PDE and Maven test launchers
	 * 
	 * @return absolute path of the project.
	 */
	private static File getBasedir() {
		if(basedir == null) {
			String basedirProp = System.getProperty("basedir");
			if(basedirProp == null) {
				try {
					File testData = createInjector(getCommonModule()).getInstance(BundleAccess.class).getFileFromClassBundle(
						TestDataProvider.class, "testData");
					if(testData == null || !testData.isDirectory())
						fail("Unable to determine basedir");
					basedir = testData.getParentFile();
				}
				catch(IOException e) {
					fail(e.getMessage());
				}
			}
			else
				basedir = new File(basedirProp);
		}
		return basedir;
	}

	public static File getTestFile(IPath testBundleRelativePath) {
		return new File(getBasedir(), testBundleRelativePath.toOSString());
	}

	public static File getTestOutputDir() {
		File testOutputDir = new File(getBasedir(), "target/testOutput");
		testOutputDir.mkdirs();
		return testOutputDir;
	}
}
