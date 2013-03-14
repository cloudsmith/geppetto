/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.forge.maven.plugin;

import java.io.File;

import org.cloudsmith.geppetto.common.os.FileUtils;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

// @fmtOff
@SuiteClasses({
	SetupTestMojo.class,
	ValidateTestMojo.class,
	PublishTestMojo.class,
	RepublishTestMojo.class,
	ValidateTest2Mojo.class,
})
// @fmtOn
@RunWith(Suite.class)
public class ForgeIT {
	static final File TEST_POM_DIR = new File(
		new File(System.getProperty("basedir", ".")), "target/test-classes/unit/publisher");

	@BeforeClass
	public static void init() {
		FileUtils.rmR(new File(TEST_POM_DIR, "target"));
	}
}
