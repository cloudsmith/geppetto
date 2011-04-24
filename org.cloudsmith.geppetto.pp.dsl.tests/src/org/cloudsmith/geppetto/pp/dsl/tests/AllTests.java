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
 * All Puppet Tests.
 * 
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTestSuite(TestWsAndComments.class);
		suite.addTestSuite(TestPuppetResourceExpr.class);
		suite.addTestSuite(TestLiterals.class);
		suite.addTestSuite(TestExpressions.class);
		suite.addTestSuite(TestCollectExpression.class);
		suite.addTestSuite(TestSelectorExpression.class);
		suite.addTestSuite(TestDoubleQuotedString.class);
		suite.addTestSuite(TestIssues.class);
		// suite.addTestSuite(PPTPManagerTests.class);
		// $JUnit-END$
		return suite;
	}

}
