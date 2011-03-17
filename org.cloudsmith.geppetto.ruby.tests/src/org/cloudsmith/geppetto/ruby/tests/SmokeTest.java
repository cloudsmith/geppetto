package org.cloudsmith.geppetto.ruby.tests;

import java.io.File;

import junit.framework.TestCase;
import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.eclipse.core.runtime.Path;

public class SmokeTest extends TestCase{
	
	public void testHelloWorld() throws Exception {
		File aRubyFile = TestDataProvider.getTestFile(new Path("testData/ruby/helloWorld.rb"));
		RubyHelper helper = new RubyHelper();
		helper.parse(aRubyFile);
	}

}
