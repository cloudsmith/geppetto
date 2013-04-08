package org.cloudsmith.geppetto.semver.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * All Puppet Tests.
 */
@SuiteClasses({
// @fmtOff
	VersionTest.class,
	VersionRangeTest.class
// @fmtOn
})
@RunWith(Suite.class)
public class AllTests {
}
