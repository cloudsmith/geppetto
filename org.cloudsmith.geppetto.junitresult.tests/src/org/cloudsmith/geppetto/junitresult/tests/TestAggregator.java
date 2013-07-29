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
package org.cloudsmith.geppetto.junitresult.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import junit.framework.TestCase;

import org.cloudsmith.geppetto.common.os.FileUtils;
import org.cloudsmith.geppetto.junitresult.JunitResult;
import org.cloudsmith.geppetto.junitresult.Testsuite;
import org.cloudsmith.geppetto.junitresult.Testsuites;
import org.cloudsmith.geppetto.junitresult.util.JunitresultAggregator;
import org.cloudsmith.geppetto.junitresult.util.JunitresultDomSerializer;
import org.eclipse.core.runtime.Path;

/**
 * Tests the result aggregator.
 * 
 */
public class TestAggregator extends TestCase {

	private static Map<String, String> correctTimestamps = new HashMap<String, String>(33);

	static {
		correctTimestamps.put("rakeruns", "2012-07-24T22:10:04+0200");
		correctTimestamps.put("maven", "2012-07-24T19:04:44+0200");
		correctTimestamps.put("xinetd", "2012-07-24T22:10:04+0200");
		correctTimestamps.put("orientdb", "2012-07-24T20:14:34+0200");
		correctTimestamps.put("puppetdb", "2012-07-24T21:09:24+0200");
		correctTimestamps.put("jboss", "2012-07-24T18:47:52+0200");
		correctTimestamps.put("postgresql", "2012-07-24T20:33:00+0200");
		correctTimestamps.put("puppet", "2012-07-24T20:46:38+0200");
		correctTimestamps.put("apache", "2012-07-24T18:23:12+0200");
		correctTimestamps.put("libvirt", "2012-07-24T19:04:20+0200");
		correctTimestamps.put("postfix", "2012-07-24T20:25:46+0200");
		correctTimestamps.put("redis", "2012-07-24T21:15:40+0200");
		correctTimestamps.put("puppi", "2012-07-24T21:11:14+0200");
		correctTimestamps.put("munin", "2012-07-24T19:14:56+0200");
		correctTimestamps.put("haproxy", "2012-07-24T18:41:52+0200");
		correctTimestamps.put("nginx", "2012-07-24T19:28:58+0200");
		correctTimestamps.put("ntp", "2012-07-24T19:47:34+0200");
		correctTimestamps.put("tomcat", "2012-07-24T21:41:58+0200");
		correctTimestamps.put("wordpress", "2012-07-24T22:00:16+0200");
		correctTimestamps.put("mysql", "2012-07-24T19:22:02+0200");
		correctTimestamps.put("openssh", "2012-07-24T20:01:46+0200");
		correctTimestamps.put("resolver", "2012-07-24T21:19:38+0200");
		correctTimestamps.put("tftp", "2012-07-24T21:31:38+0200");
		correctTimestamps.put("puppetdashboard", "2012-07-24T21:00:40+0200");
		correctTimestamps.put("splunk", "2012-07-24T21:26:44+0200");
		correctTimestamps.put("vsftpd", "2012-07-24T21:50:32+0200");
		correctTimestamps.put("foreman", "2012-07-24T18:35:40+0200");
		correctTimestamps.put("openvpn", "2012-07-24T20:05:42+0200");
		correctTimestamps.put("php", "2012-07-24T20:18:02+0200");
		correctTimestamps.put("jenkins", "2012-07-24T18:56:08+0200");
		correctTimestamps.put("foo", "2012-07-24T18:27:26+0200");
		correctTimestamps.put("nrpe", "2012-07-24T19:38:54+0200");
		correctTimestamps.put("openntpd", "2012-07-24T19:54:46+0200");
	}

	private static boolean deleteRecursiveContent(File path, boolean top) {
		if(!path.exists())
			throw new IllegalArgumentException("Can not remove non existing path:" + path.getAbsolutePath());
		boolean ret = true;
		if(path.isDirectory()) {
			for(File f : path.listFiles()) {
				ret = ret && deleteRecursiveContent(f, false);
			}
		}
		return ret && (top || path.delete());
	}

	private static String formatDate(Date date) {
		if(date == null)
			return null;

		return String.format("%1$tY-%1$tm-%1$tdT%1$tH:%1$tM:%1$tS%1$tz", date);
	}

	public void test_aggregatorSampleTest() throws IOException, TransformerException, ParserConfigurationException {
		JunitresultAggregator aggregator = new JunitresultAggregator();
		File root = TestDataProvider.getTestFile(new Path("testData/allresults/"));
		JunitResult result = aggregator.aggregate(root, root);
		assertTrue(result instanceof Testsuites);
		Testsuites testsuite = (Testsuites) result;
		assertEquals("allresults", testsuite.getName());
		assertEquals(1, testsuite.getErrors());
		assertEquals(13, testsuite.getTests());
		assertEquals(7, testsuite.getFailures());

		JunitresultDomSerializer serializer = new JunitresultDomSerializer();
		File outputDir = TestDataProvider.getTestFile(new Path("output/"));
		File output = new File(outputDir, "sample_result.xml");
		if(!output.exists())
			output.createNewFile();
		serializer.serialize(result, new FileOutputStream(output));
	}

	public void test_aggregatorSmokeTest() throws IOException, TransformerException, ParserConfigurationException {
		JunitresultAggregator aggregator = new JunitresultAggregator();
		File root = null;

		try {
			root = TestDataProvider.getTestFile(new Path("testData/test_results"));
			root.mkdir();

			FileUtils.unzip(TestDataProvider.getTestFile(new Path("testData/test_results.zip")), root);

			JunitResult result = aggregator.aggregate(root, root);
			assertTrue(result instanceof Testsuites);
			Testsuites testsuite = (Testsuites) result;
			assertEquals("test_results", testsuite.getName());
			assertEquals(3, testsuite.getErrors());
			assertEquals(1360, testsuite.getTests());
			assertEquals(28, testsuite.getFailures());

			for(Testsuite ts : testsuite.getTestsuites()) {
				String correctTimestamp = correctTimestamps.get(ts.getName());
				if(correctTimestamp != null)
					assertEquals(formatDate(ts.getTimestamp()), correctTimestamp);
			}

			JunitresultDomSerializer serializer = new JunitresultDomSerializer();
			File outputDir = TestDataProvider.getTestFile(new Path("output/"));
			File output = new File(outputDir, "smoke_result.xml");
			if(!output.exists())
				output.createNewFile();
			serializer.serialize(result, new FileOutputStream(output));
		}
		finally {
			if(root != null)
				deleteRecursiveContent(root, true);
		}
	}
}
