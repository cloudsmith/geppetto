package org.cloudsmith.geppetto.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test suite defining a green build.
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$

		suite.addTest(org.cloudsmith.geppetto.forge.tests.ForgeTests.suite());
		// suite.addTest(org.cloudsmith.geppetto.ruby.tests.AllTests.suite());
		suite.addTest(org.cloudsmith.geppetto.pp.dsl.tests.AllTests.suite());

		//$JUnit-END$
		return suite;
	}

}
