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

import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.cloudsmith.geppetto.ruby.jrubyparser.JRubyServices;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All Puppet Tests.
 */
@SuiteClasses({
// @fmtOff
	TestRubyDocProcessor.class,
	TestRubyDocProcessor2.class,
	PptpResourceTests.class,
	SmokeTest.class,
	PuppetFunctionTests.class,
	PuppetTypeTests.class,
	PuppetTPTests.class
// @fmtOn
})
@RunWith(Suite.class)
public class AllTests {
	@BeforeClass
	public static void initRubyService() {
		RubyHelper.setRubyServicesFactory(JRubyServices.FACTORY);
	}
}
