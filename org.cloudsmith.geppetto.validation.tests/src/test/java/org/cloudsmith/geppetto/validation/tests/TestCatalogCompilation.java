package org.cloudsmith.geppetto.validation.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.cloudsmith.geppetto.validation.runner.PuppetCatalogCompilerRunner;
import org.cloudsmith.geppetto.validation.runner.PuppetCatalogCompilerRunner.CatalogDiagnostic;
import org.eclipse.core.runtime.SubMonitor;
import org.junit.Test;

public class TestCatalogCompilation {

	/**
	 * Note, requires puppet (and hence ruby) installed on the machine executing
	 * the test. in addition to that it requires the inputs to the catalog
	 * compiler under /testData to produce the output.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testCatalogCompilation() throws IOException {
		File manifest = TestDataProvider.getTestFile("testData/test-manifest.pp");
		File modulePath = TestDataProvider.getTestFile("testData/test-modules/");
		File factsFile = TestDataProvider.getTestFile("testData/test-node-facts.yaml");

		PuppetCatalogCompilerRunner compiler = new PuppetCatalogCompilerRunner();
		int result = compiler.compileCatalog(
			manifest, modulePath, "test-node", factsFile, SubMonitor.convert(null, 10000));
		assertEquals("Should have 0 exit status", 0, result);
		List<CatalogDiagnostic> diagnostics = compiler.getDiagnostics();
		assertEquals("Should have found n diagnostics", 0, diagnostics.size());
	}

}
