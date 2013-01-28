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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.cloudsmith.geppetto.forge.v2.model.Release;
import org.cloudsmith.geppetto.forge.v2.service.ListPreferences;
import org.cloudsmith.geppetto.forge.v2.service.ReleaseService;
import org.junit.Test;

/**
 */
public class ReleaseTests extends ForgeAPITestBase {
	@Test
	public void testDownloadRelease() throws IOException {
		ReleaseService service = getTestUserForge().createReleaseService();
		ByteArrayOutputStream content = new ByteArrayOutputStream();
		service.download(ForgeIT.TEST_USER, ForgeIT.TEST_MODULE, ForgeIT.TEST_RELEASE_VERSION, content);
		assertEquals(
			"Wrong release content size", content.size(),
			Activator.getTestData(ForgeIT.TEST_GZIPPED_RELEASE).length());
	}

	@Test
	public void testListReleases() throws IOException {
		ReleaseService service = getTestUserForge().createReleaseService();
		List<Release> releases = service.list(null);
		assertNotNull("Null Release list", releases);
		assertFalse("Empty Release list", releases.isEmpty());
	}

	@Test
	public void testListReleasesSorted() throws IOException {
		ReleaseService service = getTestUserForge().createReleaseService();
		ListPreferences listPrefs = new ListPreferences();
		listPrefs.setLimit(4);
		listPrefs.setOffset(2);
		listPrefs.setSortBy("name");
		listPrefs.setSortOrder("descending");
		List<Release> Releases = service.list(listPrefs);
		assertNotNull("Null Release list", Releases);
		assertFalse("Empty Release list", Releases.isEmpty());
	}

	@Test
	public void testReleaseDetail() throws IOException {
		ReleaseService service = getTestUserForge().createReleaseService();
		Release release = service.get(ForgeIT.TEST_USER, ForgeIT.TEST_MODULE, ForgeIT.TEST_RELEASE_VERSION);
		assertNotNull("Null release", release);
	}
}
