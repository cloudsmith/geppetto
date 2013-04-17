package org.cloudsmith.geppetto.validation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.validation.FileType;
import org.cloudsmith.geppetto.validation.IValidationConstants;
import org.cloudsmith.geppetto.validation.ValidationOptions;
import org.cloudsmith.geppetto.validation.ValidationService;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences.ClassDescription;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences.Export;
import org.cloudsmith.geppetto.validation.runner.BuildResult;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

public class TestNodeHandling extends AbstractValidationTest {

	/**
	 * Tests that nodex (with declares a dependency on module A, which has a
	 * transitive dependency on B) can see functions afunc and bfunc, but not
	 * cfunc.
	 */
	@Test
	public void validateDuplicateRoleDifferentEnv() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/testRoles3/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);
		options.setSearchPath("modules/*:roles/production/*");
		BuildResult buildResult = vs.validate(chain, root, options, null, SubMonitor.convert(null));

		// Without constraint that only things on path are validated - there should be two redefinition errors
		//
		List<Diagnostic> children = chain.getChildren();
		assertEquals("There should be no diagnostics", 0, children.size());
		AllModuleReferences exports = buildResult.getAllModuleReferences();
		// dumpExports(exports);

		Iterable<Export> visibleExports = exports.getVisibleExports(new File("roles/production/X"));
		Export exporteda = exports.findExportedClass("aclass", visibleExports);
		assertNotNull("Should have found exported 'aclass'", exporteda);
		Export exportedb = exports.findExportedClass("bclass", visibleExports);
		assertNotNull("Should have found exported 'bclass'", exportedb);
		Export exportedc = exports.findExportedClass("cclass", visibleExports);
		assertNull("Should not have found exported 'cclass'", exportedc);

		Iterable<String> paramsForA = exports.getParameterNames(exporteda, visibleExports);
		assertTrue("Should contain 'aparam'", Iterables.contains(paramsForA, "aparam"));
		assertEquals("Should have one parameter", 1, Iterables.size(paramsForA));

		// Test new API as well
		List<AllModuleReferences.ClassDescription> classes = exports.getClassDescriptions(visibleExports);
		Map<String, ClassDescription> classMap = Maps.newHashMap();
		for(ClassDescription cd : classes)
			classMap.put(cd.getExportedClass().getName(), cd);
		assertTrue("Should contain 'aclass'", classMap.containsKey("aclass"));
		assertTrue("Should contain 'bclass'", classMap.containsKey("bclass"));
		assertFalse("Should not contain 'cclass'", classMap.containsKey("cclass"));

		assertEquals(
			"a class should have a param", "aparam",
			classMap.get("aclass").getExportedParameters().get("aparam").getLastNameSegment());

		assertEquals("should have one parameter", 1, classMap.get("aclass").getExportedParameters().size());

		assertEquals("Should have found aclass($aparam) with default value '10'", "10", //
			classMap.get("aclass").getExportedParameters().get("aparam").getDefaultValueText());

	}

	/**
	 * Tests that role X (with declares a dependency on module A, which has a
	 * transitive dependency on B) can see functions afunc and bfunc, but not
	 * cfunc.
	 * 
	 * Use a path that reveals two versions of the role X.
	 */
	@Test
	public void validateDuplicateRoleDuplicateRole() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/testRoles3/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);
		// options.setSearchPath("modules/*:roles/production/*");
		vs.validate(chain, root, options, null, SubMonitor.convert(null));

		// Without constraint that only things on path are validated - there should be two redefinition errors
		//
		List<Diagnostic> children = chain.getChildren();
		assertEquals("There should be two diagnostics", 2, children.size());
		for(Diagnostic d : children)
			assertEquals(IValidationConstants.ISSUE__MODULEFILE_REDEFINITION, d.getIssue());
	}

	/**
	 * Tests that nodex (with declares a depedency on module A, which has a
	 * transitive dependency on B) can see functions afunc and bfunc, but not
	 * cfunc.
	 */
	@Test
	public void validateNodes() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/testRoles/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);
		BuildResult buildResult = vs.validate(chain, root, options, null, SubMonitor.convert(null));
		AllModuleReferences exports = buildResult.getAllModuleReferences();

		Iterable<Export> visibleExports = exports.getVisibleExports(new File("roles/X"));
		Export exporteda = exports.findExportedClass("aclass", visibleExports);
		assertNotNull("Should have found exported 'aclass'", exporteda);
		Export exportedb = exports.findExportedClass("b::bclass", visibleExports);
		assertNotNull("Should have found exported 'bclass'", exportedb);
		Export exportedx = exports.findExportedClass("xclass", visibleExports);
		assertNotNull("Should have found exported 'xclass'", exportedx);
		Export exportedc = exports.findExportedClass("cclass", visibleExports);
		assertNull("Should not have found exported 'cclass'", exportedc);

		Iterable<String> paramsForA = exports.getParameterNames(exporteda, visibleExports);
		assertTrue("Should contain 'aparam'", Iterables.contains(paramsForA, "aparam"));
		assertEquals("Should have one parameter", 1, Iterables.size(paramsForA));

		DiagnosticsAsserter asserter = new DiagnosticsAsserter(chain);
		asserter.assertErrors(
			asserter.messageFragment("Unknown function: 'cfunc'"), asserter.messageFragment("Unknown class: 'cclass'"));
	}

	/**
	 * Tests that nodex (with declares a dependency on module A, which has a
	 * transitive dependency on B) can see functions afunc and bfunc, but not
	 * cfunc.
	 */
	@Test
	public void validateNodes2() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/testRoles2/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);
		BuildResult buildResult = vs.validate(chain, root, options, null, SubMonitor.convert(null));
		AllModuleReferences exports = buildResult.getAllModuleReferences();
		// dumpExports(exports);

		Iterable<Export> visibleExports = exports.getVisibleExports(new File("roles/X"));
		Export exporteda = exports.findExportedClass("aclass", visibleExports);
		assertNotNull("Should have found exported 'aclass'", exporteda);
		Export exportedb = exports.findExportedClass("bclass", visibleExports);
		assertNotNull("Should have found exported 'bclass'", exportedb);
		Export exportedc = exports.findExportedClass("cclass", visibleExports);
		assertNull("Should not have found exported 'cclass'", exportedc);

		Iterable<String> paramsForA = exports.getParameterNames(exporteda, visibleExports);
		assertTrue("Should contain 'aparam'", Iterables.contains(paramsForA, "aparam"));
		assertEquals("Should have one parameter", 1, Iterables.size(paramsForA));

		// Test new API as well
		List<AllModuleReferences.ClassDescription> classes = exports.getClassDescriptions(visibleExports);
		Map<String, ClassDescription> classMap = Maps.newHashMap();
		for(ClassDescription cd : classes)
			classMap.put(cd.getExportedClass().getName(), cd);
		assertTrue("Should contain 'aclass'", classMap.containsKey("aclass"));
		assertTrue("Should contain 'bclass'", classMap.containsKey("bclass"));
		assertFalse("Should not contain 'cclass'", classMap.containsKey("cclass"));

		assertEquals(
			"a class should have a param", "aparam",
			classMap.get("aclass").getExportedParameters().get("aparam").getLastNameSegment());

		assertEquals("should have one parameter", 1, classMap.get("aclass").getExportedParameters().size());

		assertEquals("Should have found aclass($aparam) with default value '10'", "10", //
			classMap.get("aclass").getExportedParameters().get("aparam").getDefaultValueText());

	}

}
