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


import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@SuiteClasses({
// @fmtOff
	ModuleNameTest.class,
	ForgeUtilTest.class,
	ForgeServiceTest.class,
	MetadataTest.class,
	ModuleUtilsTest.class,
	TypeTest.class,
	// @fmtOn
})
@RunWith(Suite.class)
public class ForgeTests extends TestSuite {
}
