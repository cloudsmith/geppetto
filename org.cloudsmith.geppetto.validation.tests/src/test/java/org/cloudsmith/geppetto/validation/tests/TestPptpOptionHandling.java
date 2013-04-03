package org.cloudsmith.geppetto.validation.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.cloudsmith.geppetto.common.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.validation.FileType;
import org.cloudsmith.geppetto.validation.ValidationOptions;
import org.cloudsmith.geppetto.validation.ValidationService;
import org.cloudsmith.geppetto.validation.runner.PPDiagnosticsRunner;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.junit.Test;

public class TestPptpOptionHandling extends AbstractValidationTest {
	/**
	 * Tests that nodex (with declares a depedency on module A, which has a
	 * transitive dependency on B) can see functions afunc and bfunc, but not
	 * cfunc.
	 */
	@Test
	public void pptpOptionHandling() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/testFor2_7_1/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);
		options.setPlatformURI(PPDiagnosticsRunner.getPuppet_2_6_4());
		vs.validate(chain, root, options, null, SubMonitor.convert(null));

		assertEquals("There should be 1 errors", 1, chain.getChildren().size());

		chain = new Diagnostic();
		options.setPlatformURI(PPDiagnosticsRunner.getPuppet_2_7_1());
		vs.validate(chain, root, options, null, SubMonitor.convert(null));
		dumpErrors(chain);
		assertEquals("There should be 0 errors", 0, chain.getChildren().size());

	}

}
