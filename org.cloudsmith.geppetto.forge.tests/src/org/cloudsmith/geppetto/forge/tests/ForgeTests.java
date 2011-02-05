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
package org.cloudsmith.geppetto.forge.tests;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;

/**
 * <!-- begin-user-doc --> A test suite for the '<em><b>forge</b></em>' package.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class ForgeTests extends TestSuite {

	public static void delete(File fileOrDir) throws IOException {
		File[] children = fileOrDir.listFiles();
		if(children != null)
			for(File child : children)
				delete(child);
		if(!fileOrDir.delete() && fileOrDir.exists())
			throw new IOException("Unable to delete " + fileOrDir);
	}

	public static File getTestOutputFolder(String name, boolean purge) throws IOException {
		Location instanceLocation = Platform.getInstanceLocation();
		URL url = instanceLocation != null
				? instanceLocation.getURL()
				: null;
		File testFolder;
		if(instanceLocation == null || !instanceLocation.isSet() || url == null) {
			String tempDir = System.getProperty("java.io.tmpdir");
			testFolder = new File(tempDir, name);
		}
		else {
			testFolder = new File(Activator.toFile(url), name);
		}
		testFolder.mkdirs();
		if(purge) {
			// Ensure that the folder is empty
			for(File file : testFolder.listFiles())
				delete(file);
		}
		return testFolder;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new ForgeTests("forge Tests");
		suite.addTestSuite(ForgeTest.class);
		suite.addTestSuite(ForgeServiceTest.class);
		suite.addTestSuite(CacheTest.class);
		suite.addTestSuite(RepositoryTest.class);
		suite.addTestSuite(MetadataTest.class);
		suite.addTestSuite(TypeTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ForgeTests(String name) {
		super(name);
	}

} // ForgeTests
