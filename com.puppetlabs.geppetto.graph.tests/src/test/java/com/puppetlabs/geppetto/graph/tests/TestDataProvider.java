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
package org.cloudsmith.geppetto.graph.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.cloudsmith.geppetto.common.util.BundleAccess;
import org.cloudsmith.geppetto.injectable.CommonModuleProvider;
import org.eclipse.core.runtime.IPath;

import com.google.inject.Guice;

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
					File testData = Guice.createInjector(CommonModuleProvider.getCommonModule()).getInstance(
						BundleAccess.class).getFileFromClassBundle(TestDataProvider.class, "testData");
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

	public static File getTestFile(String testBundleRelativePath) {
		return new File(getBasedir(), testBundleRelativePath);
	}

	public static File getTestOutputDir() {
		File testOutputDir = new File(getBasedir(), "target/testOutput");
		testOutputDir.mkdirs();
		return testOutputDir;
	}
}
