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
package com.puppetlabs.geppetto.forge.v3.api.it;

import java.io.IOException;

import org.junit.Test;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.forge.model.VersionedName;
import com.puppetlabs.geppetto.forge.v3.Releases;
import com.puppetlabs.geppetto.forge.v3.model.Release;

public class ReleasesTests extends EndpointTests<Release, VersionedName> {
	@Inject
	private Releases service;

	@Override
	protected Releases getService() {
		return service;
	}

	@Test
	public void testGetRelease() throws IOException {
		testGet(new VersionedName("puppetlabs-java/0.3.0"));
	}

	@Test
	public void testListReleases() throws IOException {
		testList(new Releases.OwnedBy("puppetlabs"));
	}
}
