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
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import junit.framework.TestCase;

import org.cloudsmith.geppetto.junitresult.JunitResult;
import org.cloudsmith.geppetto.junitresult.Testsuites;
import org.cloudsmith.geppetto.junitresult.util.JunitresultAggregator;
import org.cloudsmith.geppetto.junitresult.util.JunitresultDomSerializer;
import org.eclipse.core.runtime.Path;

/**
 * Tests the result aggregator.
 * 
 */
public class TestJenkinsAggregation extends TestCase {

	public void test_aggregatorSmokeTest() throws IOException, TransformerException, ParserConfigurationException {
		JunitresultAggregator aggregator = new JunitresultAggregator();
		File root = new File("/tmp/jenkins/results/"); // TestDataProvider.getTestFile(new Path("testData/allresults/"));
		JunitResult result = aggregator.aggregate(root, root);
		assertTrue(result instanceof Testsuites);
		Testsuites testsuite = (Testsuites) result;
		assertEquals("results", testsuite.getName());
		assertEquals(1, testsuite.getErrors());
		assertEquals(358, testsuite.getTests());
		assertEquals(0, testsuite.getFailures());

		JunitresultDomSerializer serializer = new JunitresultDomSerializer();
		File outputDir = TestDataProvider.getTestFile(new Path("output/"));
		File output = new File(outputDir, "jenkinsresult.xml");
		if(!output.exists())
			output.createNewFile();
		serializer.serialize(result, new FileOutputStream(output));
	}
}
