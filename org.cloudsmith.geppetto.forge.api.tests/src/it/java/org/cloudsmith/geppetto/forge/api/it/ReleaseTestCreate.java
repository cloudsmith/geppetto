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
package org.cloudsmith.geppetto.forge.api.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.cloudsmith.geppetto.forge.v2.model.Release;
import org.cloudsmith.geppetto.forge.v2.service.ReleaseService;
import org.junit.Test;

/**
 * @author thhal
 * 
 */
public class ReleaseTestCreate extends ForgeAPITestBase {
	@Test
	public void testCreate() throws IOException {
		ReleaseService service = getTestUserForge().createReleaseService();
		byte[] releaseFile = TestDataProvider.getTestData(TEST_GZIPPED_RELEASE);
		Release newRelease = service.create(
			TEST_USER, TEST_MODULE, "Some notes about this release", new ByteArrayInputStream(releaseFile),
			releaseFile.length);
		assertNotNull("Null Release", newRelease);
		assertEquals("Incorrect release version", newRelease.getVersion(), TEST_RELEASE_VERSION);
	}
}
