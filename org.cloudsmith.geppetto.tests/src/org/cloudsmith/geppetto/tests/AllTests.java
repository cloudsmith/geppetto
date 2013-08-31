package org.cloudsmith.geppetto.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite defining a green build.
 */
@SuiteClasses({
	// @fmtOff
	org.cloudsmith.geppetto.semver.tests.AllTests.class,
	org.cloudsmith.geppetto.forge.api.tests.AllTests.class,
	org.cloudsmith.geppetto.forge.api.it.ForgeIT.class,
	org.cloudsmith.geppetto.forge.tests.ForgeTests.class,
	org.cloudsmith.geppetto.ruby.tests.AllTests.class,
	org.cloudsmith.geppetto.pp.dsl.tests.AllTests.class,
	org.cloudsmith.geppetto.validation.tests.AllTests.class,
	org.cloudsmith.geppetto.graph.tests.AllTests.class
	// @fmtOn
})
@RunWith(Suite.class)
public class AllTests {
}
