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

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import com.puppetlabs.geppetto.junitresult.JunitResult;
import com.puppetlabs.geppetto.junitresult.NegativeResult;
import com.puppetlabs.geppetto.junitresult.Testcase;
import com.puppetlabs.geppetto.junitresult.Testrun;
import com.puppetlabs.geppetto.junitresult.Testsuite;
import com.puppetlabs.geppetto.junitresult.util.JunitresultLoader;
import org.eclipse.core.runtime.Path;

/**
 * Tests the testrun form of junit result.
 * 
 */
public class TestTestrun extends TestCase {

	public void testLoad_Bougie_testrun() throws IOException {
		File f = TestDataProvider.getTestFile(new Path("testData/BougieTest_testrun.xml"));
		JunitResult result = JunitresultLoader.loadFromXML(f);

		assertTrue("should be a Testrun instance", result instanceof Testrun);
		Testrun testrun = (Testrun) result;
		assertEquals("net.cars.engine.BougieTest", testrun.getName());
		assertEquals(2, testrun.getTests());
		assertEquals(2, testrun.getStarted());
		assertEquals(0, testrun.getFailures());
		assertEquals(1, testrun.getErrors());
		assertEquals(0, testrun.getIgnored());

		assertEquals("There should be one testsuite", 1, testrun.getTestsuites().size());
		Testsuite testsuite = testrun.getTestsuites().get(0);
		assertEquals("net.cars.engine.BougieTest", testsuite.getName());
		assertEquals(0.017, testsuite.getTime());

		assertEquals("There should be two testcases", 2, testsuite.getTestcases().size());
		Testcase tc1 = testsuite.getTestcases().get(0);
		Testcase tc2 = testsuite.getTestcases().get(1);

		assertEquals("sparkDry", tc1.getName());
		assertEquals("net.cars.engine.BougieTest", tc1.getClassname());
		assertEquals(0.001, tc1.getTime());

		assertEquals("sparkHumid", tc2.getName());
		assertEquals("net.cars.engine.BougieTest", tc2.getClassname());
		assertEquals(0.005, tc2.getTime());

		assertEquals(1, tc2.getErrors().size());
		NegativeResult error = tc2.getErrors().get(0);
		assertTrue(error instanceof com.puppetlabs.geppetto.junitresult.Error);
		assertEquals("java.lang.RuntimeException: humidity level too high\n" + //
				"\tat net.cars.engine.Bougie.spark(Unknown Source)\n" + //
				"\tat net.cars.engine.BougieTest.sparkHumid(BougieTest.java:36)\n", error.getValue());

	}
}
