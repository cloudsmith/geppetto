/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.forge.api.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.puppetlabs.geppetto.forge.model.Dependency;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;

public class DependencyTest {

	@Test
	public void testMatches__String_String() {
		Dependency dep = new Dependency();
		dep.setName(ModuleName.fromString("amodule/name"));
		dep.setVersionRequirement(VersionRange.create("2.3.x"));
		assertTrue(dep.matches(ModuleName.fromString("amodule-name"), Version.fromString("2.3.2")));
		assertFalse(dep.matches(ModuleName.fromString("amodule-name"), Version.fromString("2.2.9")));
		assertFalse(dep.matches(ModuleName.fromString("anotherModule/name"), Version.fromString("2.3.2")));
	}
}
