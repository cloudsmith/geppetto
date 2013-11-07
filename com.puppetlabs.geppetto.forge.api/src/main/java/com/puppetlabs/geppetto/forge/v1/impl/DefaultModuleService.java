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
package com.puppetlabs.geppetto.forge.v1.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.puppetlabs.geppetto.forge.client.ForgeClient;
import com.puppetlabs.geppetto.forge.model.Constants;
import com.puppetlabs.geppetto.forge.v1.model.ModuleInfo;
import com.puppetlabs.geppetto.forge.v1.service.ModuleService;
import com.puppetlabs.geppetto.forge.v2.model.Release;

public class DefaultModuleService implements ModuleService {
	@Inject
	private ForgeClient forgeClient;

	/**
	 * A type representing a {@link List} of {@link Release} instances
	 */
	Type LIST_MODULE_INFO = new TypeToken<List<ModuleInfo>>() {
	}.getType();

	@Override
	public void abortCurrentRequest() {
		forgeClient.abortCurrentRequest();
	}

	@Override
	public List<ModuleInfo> search(String keyword) throws IOException {
		List<ModuleInfo> modules = null;
		try {
			modules = forgeClient.getV1(Constants.COMMAND_GROUP_MODULES, keyword == null
					? null
					: Collections.singletonMap("q", keyword), LIST_MODULE_INFO);
		}
		catch(HttpResponseException e) {
			if(e.getStatusCode() != HttpStatus.SC_NOT_FOUND)
				throw e;
		}
		if(modules == null)
			modules = Collections.emptyList();
		return modules;
	}
}
