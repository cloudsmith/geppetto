package com.puppetlabs.geppetto.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite defining a green build.
 */
@SuiteClasses({
	// @fmtOff
	com.puppetlabs.geppetto.semver.tests.AllTests.class,
	com.puppetlabs.geppetto.forge.api.tests.AllTests.class,
	com.puppetlabs.geppetto.forge.api.it.ForgeIT.class,
	com.puppetlabs.geppetto.forge.tests.ForgeTests.class,
	com.puppetlabs.geppetto.ruby.tests.AllTests.class,
	com.puppetlabs.geppetto.pp.dsl.tests.AllTests.class,
	com.puppetlabs.geppetto.validation.tests.AllTests.class,
	com.puppetlabs.geppetto.graph.tests.AllTests.class
	// @fmtOn
})
@RunWith(Suite.class)
public class AllTests {
}
