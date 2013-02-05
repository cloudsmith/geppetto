/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.forge.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.MatchRule;
import org.cloudsmith.geppetto.forge.VersionRequirement;
import org.junit.Before;
import org.junit.Test;

public class VersionRequirementTest {
	private VersionRequirement fixture = null;

	@Before
	public void setUp() throws Exception {
		fixture = ForgeFactory.eINSTANCE.createVersionRequirement();
	}

	@Test
	public void testFindBestMatch__Collection() {
		fixture.setVersion("1.2.3.foo");
		fixture.setMatchRule(MatchRule.EQUIVALENT);
		String best = fixture.findBestMatch(Arrays.asList("1.2", "1.1", "1.3", "1.2.3", "1.2.4", "1.2.4alpha"));
		assertEquals("1.2.4", best);
	}

	@Test
	public void testMatches__String() {
		fixture.setVersion("1.2.3.foo");
		fixture.setMatchRule(MatchRule.GREATER);
		assertFalse(fixture.matches("1.2.3.foo"));
		assertFalse(fixture.matches("1.2.3-foo"));
		assertFalse(fixture.matches("1.2.3-fon"));
		assertTrue(fixture.matches("1.2.3-fox"));
		assertTrue(fixture.matches("1-2.4"));
		assertTrue(fixture.matches("1.10"));
		assertTrue(fixture.matches("2alpha"));

		fixture.setMatchRule(MatchRule.GREATER_OR_EQUAL);
		assertTrue(fixture.matches("1.2.3.foo"));
		assertTrue(fixture.matches("1.2.3-foo"));
		assertFalse(fixture.matches("1.2.3-fon"));
		assertTrue(fixture.matches("1.2.3-fox"));
		assertTrue(fixture.matches("1-2.4"));
		assertTrue(fixture.matches("1.10"));
		assertTrue(fixture.matches("2alpha"));
		assertFalse(fixture.matches("1.1.1"));
		assertFalse(fixture.matches("1"));
		assertFalse(fixture.matches("1.2alpha"));

		fixture.setMatchRule(MatchRule.PERFECT);
		assertTrue(fixture.matches("1.2.3.foo"));
		assertTrue(fixture.matches("1.2.3foo"));
		assertTrue(fixture.matches("1.2.3.0.foo"));
		assertFalse(fixture.matches("1.2.3.bar"));
		assertFalse(fixture.matches("1.2.3"));

		fixture.setMatchRule(MatchRule.EQUIVALENT);
		assertTrue(fixture.matches("1.2.3.foo"));
		assertTrue(fixture.matches("1.2.3.fox"));
		assertTrue(fixture.matches("1.2.3fox"));
		assertTrue(fixture.matches("1.2.4"));
		assertFalse(fixture.matches("1.2.3.bar"));
		assertFalse(fixture.matches("1.2.2"));
		assertFalse(fixture.matches("1.3.4"));
		assertFalse(fixture.matches("2.2.3"));

		fixture.setMatchRule(MatchRule.COMPATIBLE);
		assertTrue(fixture.matches("1.2.3.foo"));
		assertTrue(fixture.matches("1.2.3.fox"));
		assertTrue(fixture.matches("1.2.4"));
		assertFalse(fixture.matches("1.2.2"));

		assertTrue(fixture.matches("1.3.4"));
		assertFalse(fixture.matches("1.1.4"));
		assertFalse(fixture.matches("2.2.3"));

		fixture.setMatchRule(MatchRule.LESS_OR_EQUAL);
		assertTrue(fixture.matches("1.2.3.foo"));
		assertFalse(fixture.matches("1.2.3.fox"));
		assertTrue(fixture.matches("1.2.3.fon"));
		assertFalse(fixture.matches("1.2.4"));
		assertTrue(fixture.matches("1.2.2"));

		assertFalse(fixture.matches("1.3.4"));
		assertTrue(fixture.matches("1.1.4"));
		assertFalse(fixture.matches("2.2.3"));
		assertTrue(fixture.matches("0.2.3"));

		fixture.setMatchRule(MatchRule.LESS);
		assertFalse(fixture.matches("1.2.3.foo"));
		assertFalse(fixture.matches("1.2.3.fox"));
		assertTrue(fixture.matches("1.2.3.fon"));
		assertFalse(fixture.matches("1.2.4"));
		assertTrue(fixture.matches("1.2.2"));

		assertFalse(fixture.matches("1.3.4"));
		assertTrue(fixture.matches("1.1.4"));
		assertFalse(fixture.matches("2.2.3"));
		assertTrue(fixture.matches("0.2.3"));
	}
}
