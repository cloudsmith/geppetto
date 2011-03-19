package org.cloudsmith.geppetto.ruby.tests;

import java.io.File;

import junit.framework.TestCase;
import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.cloudsmith.geppetto.ruby.RubyHelper.Result;
import org.cloudsmith.geppetto.ruby.RubyParserWarningsCollector.RubyIssue;
import org.eclipse.core.runtime.Path;

public class SmokeTest extends TestCase{
	
	public void testHelloWorld() throws Exception {
		File aRubyFile = TestDataProvider.getTestFile(new Path("testData/ruby/helloWorld.rb"));
		RubyHelper helper = new RubyHelper();
		helper.parse(aRubyFile);
	}
	public void testHelloBrokenWorld() throws Exception {
		File aRubyFile = TestDataProvider.getTestFile(new Path("testData/ruby/helloBrokenWorld.rb"));
		RubyHelper helper = new RubyHelper();
		Result r = helper.parse(aRubyFile);
		assertEquals("Expect one error",1, r.getIssues().size());
		RubyIssue theIssue = r.getIssues().get(0);
		assertTrue("Expect one syntax error", theIssue.isSyntaxError());
		assertEquals("source line starts with 1", 1, theIssue.getLine());
		assertEquals("the file path is reported", aRubyFile.getPath(), theIssue.getFileName());
		assertTrue("the error message is the expected", theIssue.getMessage().startsWith("syntax error, unexpected tLPAREN_ARG"));
	}
	public void testHelloBrokenWorld2() throws Exception {
		File aRubyFile = TestDataProvider.getTestFile(new Path("testData/ruby/helloBrokenWorld2.rb"));
		RubyHelper helper = new RubyHelper();
		Result r = helper.parse(aRubyFile);
		assertEquals("Expect one error",1, r.getIssues().size());
		RubyIssue theIssue = r.getIssues().get(0);
		assertTrue("Expect one syntax error", theIssue.isSyntaxError());
		assertEquals("source line is 2", 2, theIssue.getLine());
		assertEquals("the file path is reported", aRubyFile.getPath(), theIssue.getFileName());
		assertTrue("the error message is the expected", theIssue.getMessage().startsWith("syntax error, unexpected tLPAREN_ARG"));
	}

}
