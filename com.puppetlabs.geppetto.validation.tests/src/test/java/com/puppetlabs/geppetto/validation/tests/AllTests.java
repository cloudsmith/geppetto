package com.puppetlabs.geppetto.validation.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All Puppet Validation Tests.
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({
	// @fmtOff
	TestCatalogCompilerRunner.class,
	// TestCatalogCompilation.class, Needs some love. It's broken in more recent versions of puppet.
	TestCatalogCompilerRunner.class,
	TestValidatorService.class,
	TestValidatorServiceApi2.class,
	TestNodeHandling.class,
	TestPptpOptionHandling.class,
	// TestStackhammerDemo.class, requires stackhammer-demo repo to be present
	TestParsing.class,
	TestRakefileScanning.class,
	TestForgeModules.class
	// @fmtOn
})
public class AllTests {
}
