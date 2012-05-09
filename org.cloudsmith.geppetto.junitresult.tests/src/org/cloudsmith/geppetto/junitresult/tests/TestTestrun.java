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
package org.cloudsmith.geppetto.junitresult.tests;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.cloudsmith.geppetto.junitresult.JunitResult;
import org.cloudsmith.geppetto.junitresult.NegativeResult;
import org.cloudsmith.geppetto.junitresult.Testcase;
import org.cloudsmith.geppetto.junitresult.Testrun;
import org.cloudsmith.geppetto.junitresult.Testsuite;
import org.cloudsmith.geppetto.junitresult.util.JunitresultLoader;
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
		assertEquals("0.017", testsuite.getTime());

		assertEquals("There should be two testcases", 2, testsuite.getTestcases().size());
		Testcase tc1 = testsuite.getTestcases().get(0);
		Testcase tc2 = testsuite.getTestcases().get(1);

		assertEquals("sparkDry", tc1.getName());
		assertEquals("net.cars.engine.BougieTest", tc1.getClassname());
		assertEquals("0.001", tc1.getTime());

		assertEquals("sparkHumid", tc2.getName());
		assertEquals("net.cars.engine.BougieTest", tc2.getClassname());
		assertEquals("0.005", tc2.getTime());

		NegativeResult error = tc2.getNegativeResult();
		assertTrue(error instanceof org.cloudsmith.geppetto.junitresult.Error);
		assertEquals("java.lang.RuntimeException: humidity level too high\n" + //
				"\tat net.cars.engine.Bougie.spark(Unknown Source)\n" + //
				"\tat net.cars.engine.BougieTest.sparkHumid(BougieTest.java:36)\n", error.getValue());

	}
}
