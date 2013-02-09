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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.cloudsmith.geppetto.ruby.spi.IRubyIssue;
import org.cloudsmith.geppetto.ruby.spi.IRubyParseResult;
import org.eclipse.core.runtime.Path;
import org.junit.Test;

public class SmokeTest {

	@Test
	public void testConfiguration() {
		RubyHelper rubyHelper = new RubyHelper();
		rubyHelper.setUp();
		assertTrue("Should have a real ruby service configured", rubyHelper.isRubyServicesAvailable());
	}

	@Test
	public void testHelloBrokenWorld() throws Exception {
		File aRubyFile = TestDataProvider.getTestFile(new Path("testData/ruby/helloBrokenWorld.rb"));
		RubyHelper helper = new RubyHelper();
		helper.setUp();
		try {
			IRubyParseResult r = helper.parse(aRubyFile);
			assertEquals("Expect one error", 1, r.getIssues().size());
			IRubyIssue theIssue = r.getIssues().get(0);
			assertTrue("Expect one syntax error", theIssue.isSyntaxError());
			assertEquals("source line starts with 1", 1, theIssue.getLine());
			assertEquals("the file path is reported", aRubyFile.getPath(), theIssue.getFileName());
			assertTrue("the error message is the expected", theIssue.getMessage().contains("unexpected tLPAREN_ARG"));
			// assertTrue("the error message is the expected", theIssue.getMessage().startsWith("syntax error, unexpected tLPAREN_ARG"));
		}
		finally {
			helper.tearDown();
		}
	}

	@Test
	public void testHelloBrokenWorld2() throws Exception {
		File aRubyFile = TestDataProvider.getTestFile(new Path("testData/ruby/helloBrokenWorld2.rb"));
		RubyHelper helper = new RubyHelper();
		helper.setUp();
		try {
			IRubyParseResult r = helper.parse(aRubyFile);
			assertEquals("Expect one error", 1, r.getIssues().size());
			IRubyIssue theIssue = r.getIssues().get(0);
			assertTrue("Expect one syntax error", theIssue.isSyntaxError());
			assertEquals("source line is 2", 2, theIssue.getLine());
			assertEquals("the file path is reported", aRubyFile.getPath(), theIssue.getFileName());
			assertTrue("the error message is the expected", theIssue.getMessage().contains("unexpected tLPAREN_ARG"));
		}
		finally {
			helper.tearDown();
		}
	}

	@Test
	public void testHelloWorld() throws Exception {
		File aRubyFile = TestDataProvider.getTestFile(new Path("testData/ruby/helloWorld.rb"));
		RubyHelper helper = new RubyHelper();
		helper.setUp();
		helper.parse(aRubyFile);
		helper.tearDown();
	}

}
