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
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.v3.Modules;
import com.puppetlabs.geppetto.forge.v3.model.Module;

public class ModulesTests extends EndpointTests<Module, ModuleName> {
	@Inject
	private Modules service;

	@Override
	protected Modules getService() {
		return service;
	}

	@Test
	public void testGetModule() throws IOException {
		testGet(ModuleName.fromString("puppetlabs-java"));
	}

	@Test
	public void testListModules() throws IOException {
		testList(new Modules.OwnedBy("puppetlabs"));
	}
}
