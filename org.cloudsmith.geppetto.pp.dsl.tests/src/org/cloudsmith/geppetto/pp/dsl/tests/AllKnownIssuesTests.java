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
package org.cloudsmith.geppetto.pp.dsl.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All Known Issues (failing) Puppet Tests.
 * 
 */
public class AllKnownIssuesTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllKnownIssuesTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTestSuite(TestLinking.class);
		suite.addTestSuite(TestFailingFormatting.class);
		suite.addTestSuite(TestFailingSerialization.class);
		// $JUnit-END$
		return suite;
	}

}
