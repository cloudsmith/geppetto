package com.puppetlabs.geppetto.puppetlint.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import com.puppetlabs.geppetto.puppetlint.PuppetLintRunner;
import com.puppetlabs.geppetto.puppetlint.PuppetLintRunner.Issue;
import static com.puppetlabs.geppetto.puppetlint.PuppetLintRunner.Option.*;
import com.puppetlabs.geppetto.puppetlint.PuppetLintService;
import org.eclipse.core.runtime.Path;
import org.junit.Before;
import org.junit.Test;

public class TestPuppetLint {
	private PuppetLintRunner runner;
	
	@Before
	public void before() {
		runner = PuppetLintService.getInstance().getPuppetLintRunner();
	}

	@Test
	public void testPuppetLintVersion() {
		try {
			String version = runner.getVersion();
			assertNotNull("Verson returned null", version);
			assertFalse("Version is empty", version.isEmpty());
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testPuppetLintFolderRun() {
		try {
			File folder = Activator.getBundleResourceAsFile(Path.fromPortableString("testData"));
			List<Issue> issues = runner.run(folder);
			assertNotNull("An null issue collection was returned", issues);
			assertFalse("No issues were generated", issues.isEmpty());
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	private static boolean hasProblem(String checkName, List<Issue> issues) {
		for(Issue issue : issues)
			if(issue.getCheckName().equals(checkName))
				return true;
		return false;
	}

	@Test
	public void testPuppetLintFileRun() {
		try {
			File file = Activator.getBundleResourceAsFile(Path.fromPortableString("testData/modules/passenger/manifests/init.pp"));
			List<Issue> issues = runner.run(file);
			assertNotNull("An null issue collection was returned", issues);
			assertTrue("80 chars problem not found", hasProblem("80chars", issues));

			issues = runner.run(file, NoEightyCharsCheck);
			assertNotNull("An null issue collection was returned", issues);
			assertFalse("80 chars problem found", hasProblem("80chars", issues));
		} catch(Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
