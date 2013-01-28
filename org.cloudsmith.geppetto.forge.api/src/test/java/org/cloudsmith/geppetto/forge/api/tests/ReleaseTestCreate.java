/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.forge.api.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
		File releaseFile = Activator.getTestData(ForgeTests.TEST_GZIPPED_RELEASE);
		InputStream in = new FileInputStream(releaseFile);
		try {
			Release newRelease = service.create(
				ForgeTests.TEST_USER, ForgeTests.TEST_MODULE, "Some notes about this release", in, releaseFile.length());
			assertNotNull("Null Release", newRelease);
			assertEquals("Incorrect release version", newRelease.getVersion(), ForgeTests.TEST_RELEASE_VERSION);
		}
		finally {
			in.close();
		}
	}
}
