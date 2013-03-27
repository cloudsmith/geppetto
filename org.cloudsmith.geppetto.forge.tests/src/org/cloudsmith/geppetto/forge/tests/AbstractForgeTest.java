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

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.cloudsmith.geppetto.common.util.EclipseUtils;
import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.impl.ForgeModule;
import org.cloudsmith.geppetto.forge.impl.ForgePreferencesBean;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class AbstractForgeTest {
	private static String TEST_FORGE_URI = "http://forge-staging-api.puppetlabs.com/v2/";

	private static Injector injector;

	private static File basedir;

	public static void delete(File fileOrDir) throws IOException {
		File[] children = fileOrDir.listFiles();
		if(children != null)
			for(File child : children)
				delete(child);
		if(!fileOrDir.delete() && fileOrDir.exists())
			throw new IOException("Unable to delete " + fileOrDir);
	}

	private static File getBasedir() {
		if(basedir == null) {
			String basedirProp = System.getProperty("basedir");
			if(basedirProp == null) {
				try {
					File testData = EclipseUtils.getFileFromClassBundle(AbstractForgeTest.class, "testData");
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

	public static Cache getCache() {
		return getInjector().getInstance(Cache.class);
	}

	public static Forge getForge() {
		return getInjector().getInstance(Forge.class);
	}

	public static Gson getGson() {
		return getInjector().getInstance(Gson.class);
	}

	private synchronized static Injector getInjector() {
		if(injector == null) {
			ForgePreferencesBean forgePreferences = new ForgePreferencesBean();
			forgePreferences.setBaseURL(TEST_FORGE_URI);
			try {
				forgePreferences.setCacheLocation(getTestOutputFolder("cachefolder", true).getAbsolutePath());
				injector = Guice.createInjector(new ForgeModule(forgePreferences));
			}
			catch(Exception e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		}
		return injector;
	}

	public static File getTestData(String path) throws IOException {
		return new File(new File(getBasedir(), "testData"), path);
	}

	public static File getTestOutputFolder(String name, boolean purge) throws IOException {
		File testFolder = new File(new File(new File(getBasedir(), "target"), "testOutput"), name);
		testFolder.mkdirs();
		if(purge) {
			// Ensure that the folder is empty
			for(File file : testFolder.listFiles())
				delete(file);
		}
		return testFolder;
	}
}
