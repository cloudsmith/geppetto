package org.cloudsmith.geppetto.forge.maven.plugin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.maven.plugin.MojoFailureException;
import org.eclipse.xtext.util.Wrapper;
import org.junit.Test;

public class ValidateTest2Mojo extends AbstractForgeTestMojo {

	@Test
	public void moduleWithResolvedDependency() throws Exception {
		setTestForgeModulesRoot("test_module_b");
		Validate validate = (Validate) lookupConfiguredMojo(createMavenSession(), newMojoExecution("validate"));
		assertNotNull(validate);

		try {
			final Wrapper<Boolean> msgFound = new Wrapper<Boolean>(false);
			validate.setLogger(new NOPLogger() {
				@Override
				public void info(String message) {
					if(message.contains("Installing dependent module bob-test_module_c:1.0.0"))
						msgFound.set(true);
				}
			});
			validate.execute();
			assertTrue("Expected 'Installing dependent module' did not show up", msgFound.get());
		}
		catch(MojoFailureException e) {
			fail("Failed to validate module with dependency: " + e.getMessage());
		}
	}
}
