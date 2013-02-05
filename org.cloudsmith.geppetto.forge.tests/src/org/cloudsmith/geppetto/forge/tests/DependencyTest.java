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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.cloudsmith.geppetto.forge.Dependency;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.MatchRule;
import org.cloudsmith.geppetto.forge.VersionRequirement;
import org.junit.Before;
import org.junit.Test;

public class DependencyTest {

	private Dependency fixture = null;

	@Before
	public void setUp() throws Exception {
		fixture = ForgeFactory.eINSTANCE.createDependency();
	}

	@Test
	public void testMatches__String_String() {
		fixture.setName("a.module.name");
		VersionRequirement vr = ForgeFactory.eINSTANCE.createVersionRequirement();
		vr.setMatchRule(MatchRule.EQUIVALENT);
		vr.setVersion("2.3");
		fixture.setVersionRequirement(vr);
		assertTrue(fixture.matches("a.module.name", "2.3.2"));
		assertFalse(fixture.matches("a.module.name", "2.2.9"));
		assertFalse(fixture.matches("another.module.name", "2.3.2"));
	}
}
