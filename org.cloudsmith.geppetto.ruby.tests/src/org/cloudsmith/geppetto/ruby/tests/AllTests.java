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

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All Puppet Tests.
 * 
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTestSuite(PptpResourceTests.class);
		suite.addTestSuite(SmokeTest.class);
		suite.addTestSuite(PuppetFunctionTests.class);
		suite.addTestSuite(PuppetTypeTests.class);
		suite.addTestSuite(PuppetTPTests.class);
		// $JUnit-END$
		return suite;
	}

}
