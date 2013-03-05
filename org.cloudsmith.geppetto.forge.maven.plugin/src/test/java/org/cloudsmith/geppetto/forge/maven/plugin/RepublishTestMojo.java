package org.cloudsmith.geppetto.forge.maven.plugin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.maven.plugin.MojoFailureException;
import org.eclipse.xtext.util.Wrapper;
import org.junit.Assert;
import org.junit.Test;

public class RepublishTestMojo extends AbstractForgeTestMojo {
	@SuppressWarnings("serial")
	@Test
	public void republish() throws Exception {
		setTestForgeModulesRoot("test_module_c");
		Publish publish = (Publish) lookupConfiguredMojo(createMavenSession(), newMojoExecution("publish"));
		assertNotNull(publish);

		try {
			// Publish will execute but do nothing. The result is OK.
			final Wrapper<Boolean> msgFound = new Wrapper<Boolean>(false);
			publish.setLogger(new NOPLogger() {
				@Override
				public void warn(String message) {
					if(message.contains("test_module_c:1.0.0 has already been published"))
						msgFound.set(true);
				}
			});
			publish.execute();
			Assert.assertTrue("Expected 'already been published' did not show up", msgFound.get());
		}
		catch(MojoFailureException e) {
			fail("Republishing of OK module failed: " + e.getMessage());
		}
	}
}
