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
package org.cloudsmith.geppetto.forge.v2.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.semver.Version;
import org.cloudsmith.geppetto.semver.VersionRange;
import org.junit.Test;

public class DependencyTest {

	@Test
	public void testMatches__String_String() {
		Dependency dep = new Dependency();
		dep.setName(new ModuleName("a.module/name"));
		dep.setVersionRequirement(VersionRange.create("2.3.x"));
		assertTrue(dep.matches(new ModuleName("a.module-name"), Version.create("2.3.2")));
		assertFalse(dep.matches(new ModuleName("a.module-name"), Version.create("2.2.9")));
		assertFalse(dep.matches(new ModuleName("another.module/name"), Version.create("2.3.2")));
	}
}
