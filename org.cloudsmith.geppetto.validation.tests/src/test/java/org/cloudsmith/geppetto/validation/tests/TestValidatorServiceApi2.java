package org.cloudsmith.geppetto.validation.tests;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.diagnostic.FileDiagnostic;
import org.cloudsmith.geppetto.pp.dsl.validation.IPPDiagnostics;
import org.cloudsmith.geppetto.validation.FileType;
import org.cloudsmith.geppetto.validation.IValidationConstants;
import org.cloudsmith.geppetto.validation.ValidationOptions;
import org.cloudsmith.geppetto.validation.ValidationService;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class TestValidatorServiceApi2 extends AbstractValidationTest {

	private void assertNotEquals(String message, Object expected, Object actual) {
		assertThat(message, expected, not(equalTo(actual)));
	}

	@Test
	public void relativeAmbiguityErrorReport() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/ambiguity/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(false);
		options.setCheckModuleSemantics(false);
		options.setCheckReferences(true);
		options.setFileType(FileType.MODULE_ROOT);

		vs.validate(chain, root, options, null, SubMonitor.convert(null));
		assertContainsErrorCode(chain, IPPDiagnostics.ISSUE__RESOURCE_AMBIGUOUS_REFERENCE);
		assertTrue(
			"Message text should contain a relative reference",
			chain.getChildren().get(0).getMessage().startsWith("Ambiguous reference to: 'fluff' found in: 1 resource [") &&
					chain.getChildren().get(0).getMessage().contains("manifests/ambigA.pp"));

	}

	@Test
	public void validateAString_NotOk() throws Exception {
		String code = "$a = ";
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validate(chain, code, SubMonitor.convert(null));
		assertTrue("There should be errors", chain.getChildren().size() != 0);
	}

	@Test
	public void validateAString_ok() throws Exception {
		String code = "$a = 'a::b'";
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validate(chain, code, SubMonitor.convert(null));
		assertTrue("There should be no errors", chain.getChildren().size() == 0);
	}

	@Test
	public void validateManifest_notok() throws Exception {
		File manifest = TestDataProvider.getTestFile(new Path("testData/manifests/not_ok_manifest.pp"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validate(chain, manifest, null, null, SubMonitor.convert(null));
		assertTrue("There should be errors", chain.getChildren().size() != 0);
	}

	@Test
	public void validateManifest_ok() throws Exception {
		File manifest = TestDataProvider.getTestFile(new Path("testData/manifests/ok_manifest.pp"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validate(chain, manifest, null, null, SubMonitor.convert(null));
		assertTrue("There should be no errors", chain.getChildren().size() == 0);
	}

	@Test
	public void validateModule_notok() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/broken/broken-module/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validate(chain, root, null, null, SubMonitor.convert(null));
		DiagnosticsAsserter asserter = new DiagnosticsAsserter(chain);
		asserter.assertErrors(asserter.messageFragment("unexpected tIDENTIFIER"));
		// optionally accept Unknown variables, and hyphen in name, but no other warnings
		asserter.assertWarnings(
			asserter.issue(IPPDiagnostics.ISSUE__UNKNOWN_VARIABLE).optional().greedy(),
			asserter.issue(IPPDiagnostics.ISSUE__HYPHEN_IN_NAME).optional().greedy());

		assertEquals("There should be two diagnostic entries", 2, chain.getChildren().size());
	}

	@Test
	public void validateModule_ok() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/test-modules/test-module/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validate(chain, root, null, null, SubMonitor.convert(null));

		DiagnosticsAsserter asserter = new DiagnosticsAsserter(chain);
		// no errors
		asserter.assertErrors();
		// optionally accept Unknown variables, and hyphen in name, but no other warnings
		asserter.assertWarnings(
			asserter.issue(IPPDiagnostics.ISSUE__UNKNOWN_VARIABLE).optional().greedy(),
			asserter.issue(IPPDiagnostics.ISSUE__HYPHEN_IN_NAME).optional().greedy());

		assertEquals("There should be one diagnostic entriy", 1, chain.getChildren().size());

	}

	@Test
	public void validateModuleWithSpaces_notok() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/broken withSpaces/module"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validate(chain, root, null, null, SubMonitor.convert(null));
		assertNotEquals("There should be errors", 0, chain.getChildren().size());
		for(Diagnostic d : chain)
			if(d instanceof FileDiagnostic) {
				File f = ((FileDiagnostic) d).getFile();
				assertEquals(
					"Reported files should start with 'manifests/'", "manifests/not ok manifest.pp", f.getPath());
			}
	}

	@Test
	public void validateRepository_notok() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/forgeModules/lab42-activemq-0.1.2-withErrors/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(false);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(false);
		options.setFileType(FileType.MODULE_ROOT);

		vs.validate(chain, root, options, null, SubMonitor.convert(null));
		assertNotEquals("There should be  errors", 0, chain.getChildren().size());
		Set<String> fileNames = Sets.newHashSet();
		for(Diagnostic d : chain) {
			if("This is not a boolean".equals(d.getMessage()))
				continue; // skip this (UGLY)
			if(d instanceof FileDiagnostic) {
				File f = ((FileDiagnostic) d).getFile();
				fileNames.add(f.getPath());
			}
		}
		for(String s : fileNames) {
			File f = new File(s);
			assertTrue(
				"Only files with errors (starts with 'x-', or 'Modulefile') should be reported. Was:" + f.getName(),
				f.getName().startsWith("x-") || f.getName().equals("Modulefile"));
		}
		assertEquals("Number of files with errors", 7, fileNames.size());
		List<FileDiagnostic> modulefileDiag = Lists.newArrayList();
		for(Diagnostic d : chain)
			if(d instanceof FileDiagnostic && ((FileDiagnostic) d).getFile().getName().equals("Modulefile"))
				modulefileDiag.add((FileDiagnostic) d);

		assertEquals("There should have been two dependency error", 2, modulefileDiag.size());
		for(FileDiagnostic diag : modulefileDiag)
			assertEquals(
				"Should have been reported as unsatisifed depedency",
				IValidationConstants.ISSUE__MODULEFILE_UNSATISFIED_DEPENDENCY, diag.getIssue());
	}

	@Test
	public void validateRepository_ok() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/forgeModules/lab42-activemq-0.1.2/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validate(chain, root, null, null, SubMonitor.convert(null));
		DiagnosticsAsserter asserter = new DiagnosticsAsserter(chain);
		asserter.assertAll(asserter.issue(IPPDiagnostics.ISSUE__STRING_BOOLEAN).optional().greedy());
	}

	@Test
	public void validateRepositoryDependencies() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/dependencyCheckData/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(false);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(false);
		options.setFileType(FileType.PUPPET_ROOT);

		vs.validate(chain, root, options, null, SubMonitor.convert(null));
		assertNotEquals("There should be  errors", 0, chain.getChildren().size());
		Set<String> fileNames = Sets.newHashSet();
		for(Diagnostic d : chain)
			if(d instanceof FileDiagnostic)
				fileNames.add(((FileDiagnostic) d).getFile().getPath());

		for(String s : fileNames) {
			File f = new File(s);
			assertTrue(
				"Only files with errors (starts with 'x-', or 'Modulefile') should be reported. Was:" + f.getName(),
				f.getName().startsWith("x-") || f.getName().equals("Modulefile"));
		}
		assertEquals("Number of files with errors", 1, fileNames.size());
		List<FileDiagnostic> modulefileDiag = Lists.newArrayList();
		for(Diagnostic d : chain) {
			if(d instanceof FileDiagnostic && ((FileDiagnostic) d).getFile().getName().equals("Modulefile"))
				modulefileDiag.add((FileDiagnostic) d);
		}
		assertEquals("There should have been one dependency error", 1, modulefileDiag.size());
		for(FileDiagnostic diag : modulefileDiag)
			assertEquals(
				"Should have been reported as unsatisifed depedency",
				IValidationConstants.ISSUE__MODULEFILE_UNSATISFIED_DEPENDENCY, diag.getIssue());
	}

	@Test
	public void validateRepositoryDependenciesWithExclude() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/dependencyCheckData/"));
		ValidationService vs = getValidationService();
		ValidationOptions options = getValidationOptions();
		Diagnostic chain = new Diagnostic();
		options.setCheckLayout(false);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(false);
		options.setFileType(FileType.PUPPET_ROOT);

		vs.validate(chain, root, options, new File[] { new File(root, "moduleB/") }, SubMonitor.convert(null));
		assertEquals("There should be no errors", 0, chain.getChildren().size());

	}

	@Test
	public void validateSeveralRepositories_ok() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/test-modules/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		vs.validate(chain, root, null, null, SubMonitor.convert(null));

		int hyphenWarning = 0;
		for(Diagnostic e : chain)
			if(IPPDiagnostics.ISSUE__INTERPOLATED_HYPHEN.equals(e.getIssue()) ||
					IPPDiagnostics.ISSUE__HYPHEN_IN_NAME.equals(e.getIssue()))
				hyphenWarning++;
		assertEquals("There should be two errors", 2, chain.getChildren().size() - hyphenWarning);
	}

	@Test
	public void variationsOfValidateCall() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/ghbindcases/asmodule/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(false);
		options.setCheckModuleSemantics(false);
		options.setCheckReferences(false);
		options.setFileType(FileType.MODULE_ROOT);

		vs.validate(chain, root, options, null, SubMonitor.convert(null));
		assertContainsErrorCode(chain, IPPDiagnostics.ISSUE__MISSING_COMMA);

		// Same but using a repository layout
		root = TestDataProvider.getTestFile(new Path("testData/ghbindcases/asrepo/"));
		vs = getValidationService();
		chain = new Diagnostic();
		options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);

		vs.validate(chain, root, options, null, SubMonitor.convert(null));
		assertContainsErrorCode(chain, IPPDiagnostics.ISSUE__MISSING_COMMA);

		// Use API1 call to do the same as repository layout validation above
		vs = getValidationService();
		chain = new Diagnostic();
		options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);

		vs.validateRepository(chain, root, SubMonitor.convert(null));
		assertContainsErrorCode(chain, IPPDiagnostics.ISSUE__MISSING_COMMA);

		// just the manifest
		root = TestDataProvider.getTestFile(new Path(
			"testData/ghbindcases/asmodule/ghoneycutt-bind-1.0.0/manifests/master.pp"));

		vs = getValidationService();
		chain = new Diagnostic();
		options = getValidationOptions();
		options.setCheckLayout(false);
		options.setCheckModuleSemantics(false);
		options.setCheckReferences(false);
		options.setFileType(FileType.SINGLE_SOURCE_FILE);

		vs.validate(chain, root, options, null, SubMonitor.convert(null));
		assertContainsErrorCode(chain, IPPDiagnostics.ISSUE__MISSING_COMMA);

		// Validate single file in context of repo
		root = TestDataProvider.getTestFile(new Path("testData/ghbindcases/asrepo/"));
		vs = getValidationService();
		chain = new Diagnostic();
		options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);

		vs.validate(chain, root, options, new File[] { new File(
			root, "modules/ghoneycutt-bind-1.0.0/manifests/master.pp") }, SubMonitor.convert(null));
		assertContainsErrorCode(chain, IPPDiagnostics.ISSUE__MISSING_COMMA);
		DiagnosticsAsserter asserter = new DiagnosticsAsserter(chain);
		asserter.assertErrors(//
			asserter.messageFragment("Unknown class: 'generic'"), //
			asserter.messageFragment("Unknown class: 'pam'"), //
			asserter.messageFragment("Unknown class: 'ssh'"), //
			asserter.messageFragment("Unknown class: 'svn'"), //
			asserter.messageFragment("Unknown resource type: 'pam::accesslogin'"), //
			asserter.messageFragment("Unknown resource type: 'svn::checkout'"), //
			asserter.messageFragment("Missing comma."));
		asserter.assertWarnings(asserter.issue(IPPDiagnostics.ISSUE__UNKNOWN_VARIABLE).optional().greedy());
	}
}
