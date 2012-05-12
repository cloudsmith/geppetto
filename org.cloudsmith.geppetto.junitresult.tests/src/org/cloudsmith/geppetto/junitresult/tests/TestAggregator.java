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

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import junit.framework.TestCase;

import org.cloudsmith.geppetto.junitresult.JunitResult;
import org.cloudsmith.geppetto.junitresult.Testsuite;
import org.cloudsmith.geppetto.junitresult.util.JunitresultAggregator;
import org.cloudsmith.geppetto.junitresult.util.JunitresultDomSerializer;
import org.eclipse.core.runtime.Path;

/**
 * Tests the result aggregator.
 * 
 */
public class TestAggregator extends TestCase {

	public void test_aggregatorSmokeTest() throws IOException, TransformerException, ParserConfigurationException {
		JunitresultAggregator aggregator = new JunitresultAggregator();
		File root = TestDataProvider.getTestFile(new Path("testData/allresults/"));
		JunitResult result = aggregator.aggregate(root, root);
		assertTrue(result instanceof Testsuite);
		Testsuite testsuite = (Testsuite) result;
		assertEquals("allresults", testsuite.getName());
		assertEquals(0, testsuite.getErrors());
		assertEquals(11, testsuite.getTests());
		assertEquals(7, testsuite.getFailures());

		JunitresultDomSerializer serializer = new JunitresultDomSerializer();
		serializer.serialize(result, System.err);
	}
}
