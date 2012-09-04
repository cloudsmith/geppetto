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
package org.cloudsmith.geppetto.ruby.tests;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;

import org.cloudsmith.geppetto.ruby.PPTypeInfo;
import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.eclipse.core.runtime.Path;

public class PuppetTypeTests extends TestCase {

	public void testParseFunctionInNestedModules() throws Exception {
		File aRubyFile = TestDataProvider.getTestFile(new Path(
			"testData/pp-modules-ruby/module-x/lib/puppet/type/thing.rb"));
		RubyHelper helper = new RubyHelper();
		helper.setUp();
		try {
			List<PPTypeInfo> foundTypes = helper.getTypeInfo(aRubyFile);
			assertEquals("Should have found one function", 1, foundTypes.size());
			PPTypeInfo info = foundTypes.get(0);
			assertEquals("Should have found 'thing'", "thing", info.getTypeName());
			assertEquals("Should have found one parameter", 2, info.getParameters().size());
			assertEquals("Should have found two properties", 2, info.getProperties().size());

			PPTypeInfo.Entry nameEntry = info.getParameters().get("name");
			assertNotNull("Should have found a parameter called 'name'", nameEntry);
			assertEquals(
				"Should have found a description of 'name'", "<p>Description of name</p>", nameEntry.getDocumentation());

			// TODO: check "ensure"
			PPTypeInfo.Entry weightEntry = info.getProperties().get("weight");
			assertNotNull("Should have found a property called 'weight'", weightEntry);
			assertEquals(
				"Should have found a description of 'weight'", "<p>Description of weight</p>",
				weightEntry.getDocumentation());

			PPTypeInfo.Entry emptyEntry = info.getProperties().get("empty");
			assertNotNull("Should have found a property called 'weight'", emptyEntry);
			assertEquals("Should have found a missing description of 'empty'", "", emptyEntry.getDocumentation());

		}
		finally {
			helper.tearDown();
		}
	}

}
