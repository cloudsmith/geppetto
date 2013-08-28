package org.cloudsmith.geppetto.forge.maven.plugin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.http.client.HttpResponseException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Test;

public class PublishTestMojo extends AbstractForgeTestMojo {

	@Test
	public void publishOK() throws Exception {
		MavenSession session = packageModule("test_module_c");
		Publish publish = (Publish) lookupConfiguredMojo(session, newMojoExecution("publish"));
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
		MavenSession session = packageModule("test_module_d");
		Publish publish = (Publish) lookupConfiguredMojo(session, newMojoExecution("publish"));
		assertNotNull(publish);

		try {
			publish.execute();
		}
		catch(MojoFailureException e) {
			fail("Publishing of OK releaase failed when there was no module: " + e.getMessage());
		}
	}

	@Test
	public void publishWrongOwner() throws Exception {
		MavenSession session = packageModule("test_module_wrong_owner");
		Publish publish = (Publish) lookupConfiguredMojo(session, newMojoExecution("publish"));
		assertNotNull(publish);

		try {
			publish.execute();
			fail("Publishing succeeded with wrong owner");
		}
		catch(MojoFailureException e) {
			Throwable t = e.getCause();
			assertTrue("Exception cause is not the right class", t instanceof HttpResponseException);
			assertTrue("Wrong owner not detected correctly", t.getMessage().contains("Forbidden"));
		}
	}
}
