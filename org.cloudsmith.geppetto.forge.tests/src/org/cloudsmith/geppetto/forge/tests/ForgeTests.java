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

import junit.framework.TestSuite;

import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.datalocation.Location;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@SuiteClasses({
	// @fmtOff
	ForgeTest.class,
	ForgeServiceTest.class,
	CacheTest.class,
	RepositoryTest.class,
	MetadataTest.class,
	DependencyTest.class,
	ModuleInfoTest.class,
	TypeTest.class,
	VersionRequirementTest.class
	// @fmtOn

})
@RunWith(Suite.class)
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
}
