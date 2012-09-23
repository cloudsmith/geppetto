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

package org.cloudsmith.geppetto.catalog.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All Puppet Tests.
 * 
 */
public class AllCatalogTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllCatalogTests.class.getName());
		// $JUnit-BEGIN$
		suite.addTestSuite(TestJsonLoad.class);
		suite.addTestSuite(TestCatalogRspec.class);
		// $JUnit-END$
		return suite;
	}

}
