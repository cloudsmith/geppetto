package org.cloudsmith.geppetto.validation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.cloudsmith.geppetto.validation.ValidationService;
import org.cloudsmith.geppetto.validation.runner.BuildResult;
import org.cloudsmith.geppetto.validation.runner.RakefileInfo;
import org.cloudsmith.geppetto.validation.runner.RakefileInfo.Rakefile;
import org.cloudsmith.geppetto.validation.runner.RakefileInfo.Raketask;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.junit.Test;

import com.google.common.collect.Lists;

public class TestRakefileScanning extends AbstractValidationTest {

	private void assertTask(Raketask task, String name, String description) {
		assertEquals("Expected taskname", name, task.getName());
		assertEquals("Expected description", description, task.getDescription());
	}

	@Test
	public void configuration() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/rakefiledata/simple/"));
		assertTrue("CONFIGURATION ERROR: Testdata directory must exist", root.exists());
		assertTrue(
			"CONFIGURATION ERROR: Testdata directory must be a directory: check test config!", root.isDirectory());
		File theRakefile = new File(root, "a/Rakefile");
		assertTrue("CONFIGURATION ERROR: Testdata a/Rakefile must exist: check test config!", theRakefile.exists());

		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		BuildResult result = vs.validate(chain, root, null, null, SubMonitor.convert(null));
		assertTrue(
			"CONFIGURATION ERROR:: Configuration should include ruby services!!", result.isRubyServicesAvailable());
	}

	@Test
	public void configurationOfFiles() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/rakefiledata/simple/"));
		assertTrue("CONFIGURATION ERROR: Testdata directory must exist", root.exists());
		assertTrue(
			"CONFIGURATION ERROR: Testdata directory must be a directory: check test config!", root.isDirectory());
		File theRakefile = new File(root, "a/Rakefile");
		assertTrue("CONFIGURATION ERROR: Testdata a/Rakefile must exist: check test config!", theRakefile.exists());
	}

	@Test
	public void oneRakefile4Tasks() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/rakefiledata/simple/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		BuildResult result = vs.validate(chain, root, null, null, SubMonitor.convert(null));
		DiagnosticsAsserter asserter = new DiagnosticsAsserter(chain);
		asserter.assertAll(asserter.issue(IPPDiagnostics.ISSUE__STRING_BOOLEAN).optional().greedy());

		RakefileInfo rakefileInfo = result.getRakefileInfo();
		assertEquals("Should have found a rakefile", 1, rakefileInfo.getRakefiles().size());
		Rakefile rakefile = rakefileInfo.getRakefiles().get(0);
		assertEquals("Should have a relative path of a/Rakefile", "a/Rakefile", rakefile.getPath().toString());
		assertEquals("Should have found 4 tasks", 4, rakefile.getTasks().size());
		assertTask(rakefile.getTasks().get(0), "outer:myspace:mytask", "this is my task");
		assertTask(rakefile.getTasks().get(1), "outer:cucumberTask", "a cucumber task");
		assertTask(rakefile.getTasks().get(2), "outer:rspecTask", "a rspec task");
		assertTask(rakefile.getTasks().get(3), "default", "the default task");

	}

	@Test
	public void oneRakefileWithCode() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/rakefiledata/withcode/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		BuildResult result = vs.validate(chain, root, null, null, SubMonitor.convert(null));
		DiagnosticsAsserter asserter = new DiagnosticsAsserter(chain);
		asserter.assertAll(asserter.issue(IPPDiagnostics.ISSUE__STRING_BOOLEAN).optional().greedy());

		RakefileInfo rakefileInfo = result.getRakefileInfo();
		assertEquals("Should have found a rakefile", 1, rakefileInfo.getRakefiles().size());
		Rakefile rakefile = rakefileInfo.getRakefiles().get(0);
		assertEquals(
			"Should have a relative path of smoketest/Rakefile", "smoketest/Rakefile", rakefile.getPath().toString());
		assertEquals("Should have found 4 tasks", 5, rakefile.getTasks().size());
		assertTask(rakefile.getTasks().get(0), "test0", "");
		assertTask(rakefile.getTasks().get(1), "test1", "");
		assertTask(rakefile.getTasks().get(2), "test2", "");
		assertTask(rakefile.getTasks().get(3), "runall", "");
		assertTask(rakefile.getTasks().get(4), "default", "");

	}

	@Test
	public void rakefileFromJenkinsModule() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/rakefiledata/fromJenkinsModule/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		BuildResult result = vs.validate(chain, root, null, null, SubMonitor.convert(null));
		DiagnosticsAsserter asserter = new DiagnosticsAsserter(chain);
		asserter.assertAll(asserter.issue(IPPDiagnostics.ISSUE__STRING_BOOLEAN).optional().greedy());

		RakefileInfo rakefileInfo = result.getRakefileInfo();
		assertEquals("Should have found one rakefile", 1, rakefileInfo.getRakefiles().size());
		Rakefile rakefile = rakefileInfo.getRakefiles().get(0);
		assertEquals("Should have a relative path of Rakefile", "Rakefile", rakefile.getPath().toString());
		assertEquals("Should have found 8 tasks", 8, rakefile.getTasks().size());
		assertTask(rakefile.getTasks().get(0), "default", "");
		assertTask(rakefile.getTasks().get(1), "spec", "Run all module spec tests (Requires rspec-puppet gem)");
		assertTask(rakefile.getTasks().get(2), "spec_task", "");
		assertTask(rakefile.getTasks().get(3), "build", "Build package");
		assertTask(rakefile.getTasks().get(4), "test:integration", "Run the full integration test suite (slow!)");
		assertTask(
			rakefile.getTasks().get(5), "test:check",
			"Make sure some of the rspec-puppet directories/files are in place");
		assertTask(rakefile.getTasks().get(6), "test:cucumber", "");
		assertTask(rakefile.getTasks().get(7), "test:spec", "");

	}

	@Test
	public void twoRakefilesWith4Tasks() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/rakefiledata/twice/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		BuildResult result = vs.validate(chain, root, null, null, SubMonitor.convert(null));
		DiagnosticsAsserter asserter = new DiagnosticsAsserter(chain);
		asserter.assertAll(asserter.issue(IPPDiagnostics.ISSUE__STRING_BOOLEAN).optional().greedy());

		RakefileInfo rakefileInfo = result.getRakefileInfo();
		assertEquals("Should have found two rakefiles", 2, rakefileInfo.getRakefiles().size());
		Rakefile rakefile = rakefileInfo.getRakefiles().get(0);
		List<String> rakefilePaths = Lists.newArrayList();
		rakefilePaths.add(rakefile.getPath().toString());

		assertEquals("Should have found 4 tasks", 4, rakefile.getTasks().size());
		assertTask(rakefile.getTasks().get(0), "outer:myspace:mytask", "this is my task");
		assertTask(rakefile.getTasks().get(1), "outer:cucumberTask", "a cucumber task");
		assertTask(rakefile.getTasks().get(2), "outer:rspecTask", "a rspec task");
		assertTask(rakefile.getTasks().get(3), "default", "the default task");

		rakefile = rakefileInfo.getRakefiles().get(1);
		rakefilePaths.add(rakefile.getPath().toString());
		assertTrue("Should contain a/Rakefile", rakefilePaths.contains("a/Rakefile"));
		assertTrue("Should contain b/Rakefile", rakefilePaths.contains("b/alternative.rake"));

		assertEquals("Should have found 4 tasks", 4, rakefile.getTasks().size());
		assertTask(rakefile.getTasks().get(0), "outer:myspace:mytask", "this is my task");
		assertTask(rakefile.getTasks().get(1), "outer:cucumberTask", "a cucumber task");
		assertTask(rakefile.getTasks().get(2), "outer:rspecTask", "a rspec task");
		assertTask(rakefile.getTasks().get(3), "default", "the default task");

	}
}
