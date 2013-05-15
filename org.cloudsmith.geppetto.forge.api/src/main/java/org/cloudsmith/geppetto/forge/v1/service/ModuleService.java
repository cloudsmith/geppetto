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
package org.cloudsmith.geppetto.forge.v1.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.cloudsmith.geppetto.forge.client.ForgeClient;
import org.cloudsmith.geppetto.forge.model.Constants;
import org.cloudsmith.geppetto.forge.v1.model.ModuleInfo;
import org.cloudsmith.geppetto.forge.v2.model.Module;

import com.google.inject.Inject;

/**
 * A CRUD service for {@link Module} objects
 */
public class ModuleService {
	@Inject
	private ForgeClient forgeClient;

	/**
	 * Cleanly abort the currently executing request. This method does nothing if there is
	 * no executing request.
	 */
	public void abortCurrentRequest() {
		forgeClient.abortCurrentRequest();
	}

	/**
	 * @param keyword
	 *            KeyWord to use in the search. May be <code>null</code> to get all modules.
	 * @return All Modules that matches the given keyword
	 * @throws IOException
	 */
	public List<ModuleInfo> search(String keyword) throws IOException {
		List<ModuleInfo> modules = null;
		try {
			modules = forgeClient.getV1(Constants.COMMAND_GROUP_MODULES, keyword == null
					? null
					: Collections.singletonMap("q", keyword), Constants.LIST_MODULE_INFO);
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
