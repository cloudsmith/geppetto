package org.cloudsmith.geppetto.forge.maven.plugin;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.maven.plugin.MojoFailureException;
import org.junit.Test;

public class ValidateTestMojo extends AbstractForgeTestMojo {

	@Test
	public void moduleWithComplexRuby() throws Exception {
		setTestForgeModulesRoot("test_module_complex_ruby");
		Validate validate = (Validate) lookupConfiguredMojo(createMavenSession(), newMojoExecution("validate"));
		assertNotNull(validate);

		try {
			validate.execute();
			fail("Complex ruby was not detected");
		}
		catch(MojoFailureException e) {
			assertTrue("Complex ruby was not detected", e.getMessage().contains("version must be specified"));
		}
	}

	@Test
	public void moduleWithInterpolatedComplexRuby() throws Exception {
		setTestForgeModulesRoot("test_module_interpolated_complex_ruby");
		Validate validate = (Validate) lookupConfiguredMojo(createMavenSession(), newMojoExecution("validate"));
		assertNotNull(validate);

		try {
			validate.execute();
			fail("Interpolated complex ruby was not detected");
		}
		catch(MojoFailureException e) {
			assertTrue(
				"Interpolated complex ruby was not detected", e.getMessage().contains("version must be specified"));
		}
	}

	@Test
	public void moduleWithInvalidMetadata() throws Exception {
		setTestForgeModulesRoot("test_metadata_invalid");
		Validate validate = (Validate) lookupConfiguredMojo(createMavenSession(), newMojoExecution("validate"));
		assertNotNull(validate);

		try {
			validate.execute();
			fail("Invalid metadata was not detected");
		}
		catch(MojoFailureException e) {
			assertTrue("Invalid metadata was not detected", e.getMessage().contains("MalformedJsonException"));
		}
	}

	@Test
	public void moduleWithNoMetadataNorModulefile() throws Exception {
		setTestForgeModulesRoot("test_no_metadata_nor_modulefile");
		Validate validate = (Validate) lookupConfiguredMojo(createMavenSession(), newMojoExecution("validate"));
		assertNotNull(validate);

		try {
			validate.execute();
			fail("Module found although both metadata.json and Modulefile is missing");
		}
		catch(MojoFailureException e) {
			assertTrue(
				"Module found although both metadata.json and Modulefile is missing",
				e.getMessage().contains("No modules found"));
		}
	}

	@Test
	public void moduleWithNoModulefile() throws Exception {
		setTestForgeModulesRoot("test_metadata_no_modulefile");
		Validate validate = (Validate) lookupConfiguredMojo(createMavenSession(), newMojoExecution("validate"));
		assertNotNull(validate);

		try {
			validate.execute();
		}
		catch(MojoFailureException e) {
			fail("Validation failed when Modulefile is missing: " + e.getMessage());
		}
	}

	@Test
	public void moduleWithoutMetadataName() throws Exception {
		setTestForgeModulesRoot("test_metadata_no_name");
		Validate validate = (Validate) lookupConfiguredMojo(createMavenSession(), newMojoExecution("validate"));
		assertNotNull(validate);

		try {
			validate.execute();
			fail("Missing module name in metadata.json was not detected");
		}
		catch(MojoFailureException e) {
			assertTrue(
				"Missing module name in metadata.json was not detected",
				e.getMessage().contains("name (user-module) must be specified"));
		}
	}

	@Test
	public void moduleWithoutMetadataVersion() throws Exception {
		setTestForgeModulesRoot("test_metadata_no_version");
		Validate validate = (Validate) lookupConfiguredMojo(createMavenSession(), newMojoExecution("validate"));
		assertNotNull(validate);

		try {
			validate.execute();
			fail("Missing module version in metadata.json was not detected");
		}
		catch(MojoFailureException e) {
			assertTrue(
				"Missing module version in metadata.json was not detected",
				e.getMessage().contains("version must be specified"));
		}
	}

	@Test
	public void moduleWithoutName() throws Exception {
		setTestForgeModulesRoot("test_module_no_name");
		Validate validate = (Validate) lookupConfiguredMojo(createMavenSession(), newMojoExecution("validate"));
		assertNotNull(validate);

		try {
			validate.execute();
			fail("Missing module name was not detected");
		}
		catch(MojoFailureException e) {
			assertTrue(
				"Missing module name was not detected", e.getMessage().contains("name (user-module) must be specified"));
		}
	}

	@Test
	public void moduleWithoutVersion() throws Exception {
		setTestForgeModulesRoot("test_module_no_version");
		Validate validate = (Validate) lookupConfiguredMojo(createMavenSession(), newMojoExecution("validate"));
		assertNotNull(validate);

		try {
			validate.execute();
			fail("Missing module version was not detected");
		}
		catch(MojoFailureException e) {
			assertTrue("Missing module version was not detected", e.getMessage().contains("version must be specified"));
		}
	}

	@Test
	public void unresolvedDependency() throws Exception {
		setTestForgeModulesRoot("test_module_b");
		Validate validate = (Validate) lookupConfiguredMojo(createMavenSession(), newMojoExecution("validate"));
		assertNotNull(validate);

		try {
			validate.execute();
			fail("Unresolved dependency was not detected");
		}
		catch(MojoFailureException e) {
			assertTrue("Unresolved dependency was not detected", e.getMessage().contains("Unresolved"));
		}
	}

	@Test
	public void validateOK() throws Exception {
		setTestForgeModulesRoot("test_module_c");
		Validate validate = (Validate) lookupConfiguredMojo(createMavenSession(), newMojoExecution("validate"));
		assertNotNull(validate);

		try {
			validate.execute();
		}
		catch(MojoFailureException e) {
			fail("Validation of OK module failed: " + e.getMessage());
		}
	}
}
