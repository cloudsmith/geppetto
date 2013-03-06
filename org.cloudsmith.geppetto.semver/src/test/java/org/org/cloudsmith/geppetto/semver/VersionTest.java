package org.org.cloudsmith.geppetto.semver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Unit tests for Version.
 */
public class VersionTest {
	@Test
	public void badNegativeNumbers() {
		try {
			Version.create(1, 0, -1);
			fail("should not create version with negative numbers");
		}
		catch(IllegalArgumentException e) {
		}
	}

	@Test
	public void badPreReleaseSeparator() {
		try {
			Version.create("0.0.0.alpha");
			fail("should not permit '.' as pre-release separator");
		}
		catch(IllegalArgumentException e) {
		}
	}

	@Test
	public void badPreReleaseString() {
		try {
			Version.create("0.0.0-bad=qualifier");
			fail("should not create version illegal characters in pre-release");
		}
		catch(IllegalArgumentException e) {
		}
	}

	@Test
	public void numbersMagnitude() {
		try {
			assertTrue(Version.create("0.0.1").compareTo(Version.create("0.0.2")) < 0);
			assertTrue(Version.create("0.0.9").compareTo(Version.create("0.1.0")) < 0);
			assertTrue(Version.create("0.9.9").compareTo(Version.create("1.0.0")) < 0);
			assertTrue(Version.create("1.9.0").compareTo(Version.create("1.10.0")) < 0);
			assertTrue(Version.create("1.10.0").compareTo(Version.create("1.11.0")) < 0);
		}
		catch(IllegalArgumentException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void okStringVersions() {
		try {
			assertEquals(Version.create(0, 0, 0), Version.create("0.0.0"));
			assertEquals(Version.create(0, 0, 1), Version.create("0.0.1"));
			assertEquals(Version.create(0, 1, 0), Version.create("0.1.0"));
			assertEquals(Version.create(1, 0, 0), Version.create("1.0.0"));
			assertEquals(Version.create(0, 0, 0, "alpha"), Version.create("0.0.0-alpha"));
			assertEquals(Version.create(0, 0, 1, "alpha"), Version.create("0.0.1-alpha"));
			assertEquals(Version.create(0, 1, 0, "alpha"), Version.create("0.1.0-alpha"));
			assertEquals(Version.create(1, 0, 0, "alpha"), Version.create("1.0.0-alpha"));
		}
		catch(IllegalArgumentException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void preReleaseLessThanVersion() {
		try {
			assertTrue(Version.create("1.0.0").compareTo(Version.create("1.0.0-alpha")) > 0);
		}
		catch(IllegalArgumentException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void preReleaseMagnitude() {
		try {
			assertTrue(Version.create("1.0.0-alpha1").compareTo(Version.create("1.0.0-beta1")) < 0);
			assertTrue(Version.create("1.0.0-beta1").compareTo(Version.create("1.0.0-beta2")) < 0);
			assertTrue(Version.create("1.0.0-beta2").compareTo(Version.create("1.0.0-rc1")) < 0);
		}
		catch(IllegalArgumentException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void tooFewDigits() {
		try {
			Version.create("0.0");
			fail("should not create version with just two digits");
		}
		catch(IllegalArgumentException e) {
		}
	}
}
