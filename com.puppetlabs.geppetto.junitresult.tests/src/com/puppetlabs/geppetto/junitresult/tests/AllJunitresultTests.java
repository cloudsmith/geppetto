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

package com.puppetlabs.geppetto.junitresult.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllJunitresultTests {
	public static Test suite() {
		TestSuite suite = new TestSuite(AllJunitresultTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTestSuite(TestTestrun.class);
		suite.addTestSuite(TestTessuite.class);
		suite.addTestSuite(TestAggregator.class);
		// requires manual setup
		// suite.addTestSuite(TestJenkinsAggregation.class);
		// $JUnit-END$
		return suite;
	}

}
