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

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.forge.api.it.ForgeAPITestBase;
import com.puppetlabs.geppetto.forge.api.it.ForgeAPITestBase.TestModule;
import com.puppetlabs.geppetto.forge.api.tests.GuiceJUnitRunner;
import com.puppetlabs.geppetto.forge.api.tests.GuiceJUnitRunner.TestModules;
import com.puppetlabs.geppetto.forge.model.VersionedName;
import com.puppetlabs.geppetto.forge.v3.Files;

@RunWith(GuiceJUnitRunner.class)
@TestModules(TestModule.class)
public class FilesTests extends ForgeAPITestBase {
	@Inject
	private Files service;

	@Test
	public void testGetFile() throws IOException {
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		service.download(new VersionedName("puppetlabs-java/0.3.0"), ba);
		assertTrue("No bytes where downloaded", ba.size() > 0);
	}
}
