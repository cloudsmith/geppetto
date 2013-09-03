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
package com.puppetlabs.geppetto.forge.api.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.http.client.HttpResponseException;
import com.puppetlabs.geppetto.forge.v2.service.ReleaseService;
import org.junit.Test;

/**
 * @author thhal
 * 
 */
public class ReleaseTestDelete extends ForgeAPITestBase {
	@Test
	public void testDeleteRelease() throws IOException {
		ReleaseService service = getTestUserForge().createReleaseService();
		service.delete(TEST_USER, TEST_MODULE, TEST_RELEASE_VERSION);
		try {
			service.get(TEST_USER, TEST_MODULE, TEST_RELEASE_VERSION);
			fail("Expected 404");
		}
		catch(HttpResponseException e) {
			assertEquals("Wrong response code", 404, e.getStatusCode());
		}
	}
}
