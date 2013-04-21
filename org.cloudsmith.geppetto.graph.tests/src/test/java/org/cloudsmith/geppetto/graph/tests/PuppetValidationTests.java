package org.cloudsmith.geppetto.graph.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All Puppet Validation Tests.
 * 
 */
// @fmtOff
@SuiteClasses({
	TestCatalogGraph.class,
	TestDependencyGraph.class, // has Xtest methods that can be turned on if repo is present
})
// @fmtOn
@RunWith(Suite.class)
public class PuppetValidationTests {
}
