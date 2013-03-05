package org.cloudsmith.geppetto.forge.maven.plugin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.maven.plugin.MojoFailureException;
import org.junit.Test;

public class PublishTestMojo extends AbstractForgeTestMojo {
	@Test
	public void publishOK() throws Exception {
		setTestForgeModulesRoot("test_module_c");
		Publish publish = (Publish) lookupConfiguredMojo(createMavenSession(), newMojoExecution("publish"));
		assertNotNull(publish);

		try {
			publish.execute();
		}
		catch(MojoFailureException e) {
			fail("Publishing of OK module failed: " + e.getMessage());
		}
	}

	@Test
	public void publishWithNoModuleAtForge() throws Exception {
		setTestForgeModulesRoot("test_module_d");
		Publish publish = (Publish) lookupConfiguredMojo(createMavenSession(), newMojoExecution("publish"));
		assertNotNull(publish);

		try {
			publish.execute();
			fail("Publishing succeeded although there was no module");
		}
		catch(MojoFailureException e) {
			assertTrue("Missing module not detected correctly", e.getMessage().contains("Module not found"));
		}
	}

	@Test
	public void publishWrongOwner() throws Exception {
		setTestForgeModulesRoot("test_module_wrong_owner");
		Publish publish = (Publish) lookupConfiguredMojo(createMavenSession(), newMojoExecution("publish"));
		assertNotNull(publish);

		try {
			publish.execute();
			fail("Publishing succeeded with wrong owner");
		}
		catch(MojoFailureException e) {
			assertTrue("Wrong owner not detected correctly", e.getMessage().contains("Forbidden"));
		}
	}
}
