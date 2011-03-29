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

import org.cloudsmith.geppetto.ruby.PPFunctionInfo;
import org.cloudsmith.geppetto.ruby.RubyHelper;

import org.eclipse.core.runtime.Path;

import junit.framework.TestCase;

public class PuppetFunctionTests extends TestCase {

	public void testParseFunctionInNestedModules() throws Exception {
		File aRubyFile = TestDataProvider
				.getTestFile(new Path(
						"testData/pp-modules-ruby/module-x/lib/puppet/parser/functions/echotest.rb"));
		RubyHelper helper = new RubyHelper();
		helper.setUp();
		try {
			List<PPFunctionInfo> foundFunctions = helper.getFunctionInfo(aRubyFile);
			assertEquals("Should have found one function", 1, foundFunctions.size());
			PPFunctionInfo info = foundFunctions.get(0);
			assertEquals("Should have found echotest", "echotest", info.getFunctionName());
			assertTrue("Should have been an rValue",info.isRValue());		
		}
		finally {
			helper.tearDown();
		}
	}
	public void testParseFunctionInQualifiedNamedModule() throws Exception {
		File aRubyFile = TestDataProvider
				.getTestFile(new Path(
						"testData/pp-modules-ruby/module-x/lib/puppet/parser/functions/echotest2.rb"));
		RubyHelper helper = new RubyHelper();
		helper.setUp();
		try {
		List<PPFunctionInfo> foundFunctions = helper.getFunctionInfo(aRubyFile);
		assertEquals("Should have found one function", 1, foundFunctions.size());
		PPFunctionInfo info = foundFunctions.get(0);
		assertEquals("Should have found echotest", "echotest2", info.getFunctionName());
		assertTrue("Should have been an rValue",info.isRValue());
		
		}
		finally {
			helper.tearDown();
		}
	}
	public void testParseFunctionWithFullyQualifiedName() throws Exception {
		File aRubyFile = TestDataProvider
				.getTestFile(new Path(
						"testData/pp-modules-ruby/module-x/lib/puppet/parser/functions/echotest3.rb"));
		RubyHelper helper = new RubyHelper();
		helper.setUp();
		try {
			List<PPFunctionInfo> foundFunctions = helper.getFunctionInfo(aRubyFile);
			assertEquals("Should have found one function", 1, foundFunctions.size());
			PPFunctionInfo info = foundFunctions.get(0);
			assertEquals("Should have found echotest", "echotest3", info.getFunctionName());
		assertTrue("Should have been an rValue",info.isRValue());
		
		}
		finally {
			helper.tearDown();
		}
	}
}
