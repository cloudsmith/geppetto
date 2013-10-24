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
package com.puppetlabs.geppetto.forge.v2.api.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.puppetlabs.geppetto.forge.api.it.ForgeAPITestBase;
import com.puppetlabs.geppetto.forge.v2.model.Release;
import com.puppetlabs.geppetto.forge.v2.service.ReleaseService;

import org.junit.Test;

/**
 */
public class ReleaseTests extends ForgeAPITestBase {
	@Test
	public void testDownloadRelease() throws IOException {
		ReleaseService service = getTestUserForge().createReleaseService();
		ByteArrayOutputStream content = new ByteArrayOutputStream();
		service.download(TEST_USER, TEST_MODULE, TEST_RELEASE_VERSION, content);
		assertEquals(
			"Wrong release content size", content.size(), TestDataProvider.getTestData(TEST_GZIPPED_RELEASE).length);
	}

	/* Disabled since it's not really used and puts a hard strain on the server
	@Test
	public void testListReleases() throws IOException {
		DefaultReleaseService service = getTestUserForge().createReleaseService();
		List<Release> releases = service.list(null);
		assertNotNull("Null Release list", releases);
		assertFalse("Empty Release list", releases.isEmpty());
	}

	@Test
	public void testListReleasesSorted() throws IOException {
		DefaultReleaseService service = getTestUserForge().createReleaseService();
		ListPreferences listPrefs = new ListPreferences();
		listPrefs.setLimit(4);
		listPrefs.setOffset(2);
		listPrefs.setSortBy("name");
		listPrefs.setSortOrder("descending");
		List<Release> DefaultReleases = service.list(listPrefs);
		assertNotNull("Null Release list", DefaultReleases);
		assertFalse("Empty Release list", DefaultReleases.isEmpty());
	}
	*/

	@Test
	public void testReleaseDetail() throws IOException {
		ReleaseService service = getTestUserForge().createReleaseService();
		Release release = service.get(TEST_USER, TEST_MODULE, TEST_RELEASE_VERSION);
		assertNotNull("Null release", release);
	}
}
