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
package org.cloudsmith.geppetto.pp.dsl.tests;

import java.io.IOException;
import java.net.URL;

import org.cloudsmith.geppetto.common.util.EclipseUtils;
import org.cloudsmith.geppetto.pp.dsl.target.PptpResourceUtil;
import org.eclipse.emf.common.util.URI;
import org.junit.Test;

public class TestPptpResourceAsFile extends AbstractPuppetTests {
	@Test
	public void test_PptpResourceAsFile() {
		try {
			URI uri = PptpResourceUtil.getFacter_1_6();
			assertNotNull("Facter pptp URI is null", uri);
			assertNotNull("Facter pptp file is null", EclipseUtils.getResourceAsFile(new URL(uri.toString())));

			uri = PptpResourceUtil.getPuppet_2_7_19();
			assertNotNull("Puppet pptp URI is null", uri);
			assertNotNull("Puppet pptp file is null", EclipseUtils.getResourceAsFile(new URL(uri.toString())));
		}
		catch(IOException e) {
			fail("Unable to obtain File that appoints facter pptp: " + e);
		}
	}
}
