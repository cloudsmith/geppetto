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

import junit.framework.TestCase;

import org.cloudsmith.geppetto.common.stats.IntegerCluster;

/**
 * Tests formatting utility classes.
 * 
 */
public class TestFormatterUtils extends TestCase {

	public void test_Cluster() {
		IntegerCluster cluster = new IntegerCluster(20);
		int[] values = new int[] { 13, 18, 13, 95, };
		int[] expectedClusterMax = new int[] { 18, 18, 18, 95, };
		for(int i = 0; i < values.length; i++)
			cluster.add(values[i]);

		assertEquals("Expected number of clusters", 2, cluster.getClusterCount());
		for(int i = 0; i < values.length; i++)
			assertEquals(
				"Expecte max of cluster is not correct for: " + i, expectedClusterMax[i], cluster.clusterMax(values[i]));

	}

	public void test_Cluster0() {
		IntegerCluster cluster = new IntegerCluster(20);
		int[] values = new int[] { 1, 2, 3, 4, 5, };
		int[] expectedClusterMax = new int[] { 5, 5, 5, 5, 5 };
		for(int i = 0; i < values.length; i++)
			cluster.add(values[i]);

		assertEquals("Expected number of clusters", 1, cluster.getClusterCount());
		for(int i = 0; i < values.length; i++)
			assertEquals(
				"Expecte max of cluster is not correct for: " + i, expectedClusterMax[i], cluster.clusterMax(values[i]));

	}

	public void test_Cluster2() {
		IntegerCluster cluster = new IntegerCluster(20);
		int[] values = new int[] { 1, 2, 3, 4, 5, 15, 27, 28, 50, 51, 52 };
		int[] expectedClusterMax = new int[] { 5, 5, 5, 5, 5, 28, 28, 28, 52, 52, 52 };
		for(int i = 0; i < values.length; i++)
			cluster.add(values[i]);

		assertEquals("Expected number of clusters", 3, cluster.getClusterCount());
		for(int i = 0; i < values.length; i++)
			assertEquals(
				"Expecte max of cluster is not correct for: " + i, expectedClusterMax[i], cluster.clusterMax(values[i]));

	}

	public void test_Cluster3() {
		IntegerCluster cluster = new IntegerCluster(20);
		int[] values = new int[] { 1, 2, 3, 4, 5, 14, 27, 28, 50, 51, 52 };
		int[] expectedClusterMax = new int[] { 14, 14, 14, 14, 14, 14, 28, 28, 52, 52, 52 };
		for(int i = 0; i < values.length; i++)
			cluster.add(values[i]);

		assertEquals("Expected number of clusters", 3, cluster.getClusterCount());
		for(int i = 0; i < values.length; i++)
			assertEquals(
				"Expecte max of cluster is not correct for: " + i, expectedClusterMax[i], cluster.clusterMax(values[i]));

	}
}
