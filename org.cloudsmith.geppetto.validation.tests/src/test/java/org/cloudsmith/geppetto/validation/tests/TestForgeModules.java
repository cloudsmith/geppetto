package org.cloudsmith.geppetto.validation.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.cloudsmith.geppetto.common.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.validation.FileType;
import org.cloudsmith.geppetto.validation.ValidationOptions;
import org.cloudsmith.geppetto.validation.ValidationService;
import org.eclipse.core.runtime.SubMonitor;
import org.junit.Test;

public class TestForgeModules extends AbstractValidationTest {
	@Test
	public void validateForge() throws Exception {
		File root = new File("/Users/henrik/gitrepos/forge-modules");
		if(!root.isDirectory())
			return;

		// TestDataProvider.getTestFile(new Path(
		// "testData/test-modules/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);
		vs.validate(chain, root, options, null, SubMonitor.convert(null));
		assertEquals("There should be 100 errors", 100, chain.getChildren().size());
	}
}
