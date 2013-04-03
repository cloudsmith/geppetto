package org.cloudsmith.geppetto.validation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.cloudsmith.geppetto.validation.runner.PuppetCatalogCompilerRunner;
import org.cloudsmith.geppetto.validation.runner.PuppetCatalogCompilerRunner.CatalogDiagnostic;
import org.eclipse.core.runtime.SubMonitor;
import org.junit.Test;

public class TestCatalogCompilerRunner {

	/**
	 * Note, requires the mock-output.sh file under /testData to produce the
	 * output
	 * 
	 * @throws IOException
	 */
	@Test
	public void catalogCompilerRunner() throws IOException {
		File script = TestDataProvider.getTestFile("testData/mock-output.sh");
		PuppetCatalogCompilerRunner compiler = new PuppetCatalogCompilerRunner(script.getAbsolutePath());
		int result = compiler.compileCatalog(
			new File("foo"), null, "bar", new File("baz"), SubMonitor.convert(null, 10000));
		assertEquals("Should have 0 exit status", 0, result);
		List<CatalogDiagnostic> diagnostics = compiler.getDiagnostics();
		assertEquals("Should have found n diagnostics", 3, diagnostics.size());
		int i = 1;
		boolean parseErrorSeen = false;
		for(CatalogDiagnostic d : diagnostics) {
			String s = d.getFile().getName();
			assertEquals("File should end with .pp", ".pp", s.substring(s.length() > 3
					? s.length() - 3
					: 0));
			assertEquals("Should have 10*diag# as line", 10 * i, d.getLineNumber());
			if(d.getMessage().startsWith("Could not parse")) {
				assertEquals(
					"Should have parse diagnostic code",
					PuppetCatalogCompilerRunner.CatalogDiagnostic.CODE_PARSE_ERROR, d.getCode());
				parseErrorSeen = true;
			}
			else
				assertEquals(
					"Should have unknown diagnostic code",
					PuppetCatalogCompilerRunner.CatalogDiagnostic.CODE_UNSPECIFIC, d.getCode());
			i++;
		}
		assertTrue("should have seen parse error", parseErrorSeen);
	}
}
