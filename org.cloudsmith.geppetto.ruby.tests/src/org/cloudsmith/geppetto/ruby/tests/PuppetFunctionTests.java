package org.cloudsmith.geppetto.ruby.tests;

import java.io.File;
import java.util.List;

import org.cloudsmith.geppetto.ruby.PPFunctionInfo;
import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.cloudsmith.geppetto.ruby.spi.IRubyParseResult;

import org.eclipse.core.runtime.Path;

import junit.framework.TestCase;

public class PuppetFunctionTests extends TestCase {

	public void testParseFunction() throws Exception {
		File aRubyFile = TestDataProvider
				.getTestFile(new Path(
						"testData/pp-modules-ruby/module-x/lib/puppet/parser/functions/echotest.rb"));
		RubyHelper helper = new RubyHelper();
		helper.setUp();
		try {
		IRubyParseResult r = helper.parse(aRubyFile);
		List<PPFunctionInfo> foundFunctions = helper.getFunctionInfo(aRubyFile);
//		Node root = r.getAST();
//		ModuleVisitor moduleVisitor = new ModuleVisitor();
//		moduleVisitor.all(root);
//		System.err.println("Found root: " + root.toString());
		System.err.println("Found functions: "+ foundFunctions);
		}
		finally {
			helper.tearDown();
		}
	}
	
}
