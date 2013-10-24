/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 * 
 */
package com.puppetlabs.geppetto.forge.v1.service;

import java.io.IOException;
import java.util.List;

import com.puppetlabs.geppetto.forge.v1.model.ModuleInfo;

/**
 * A CRUD service for {@link Module} objects
 */
public interface ModuleService {

	/**
	 * Cleanly abort the currently executing request. This method does nothing if there is
	 * no executing request.
	 */
	void abortCurrentRequest();

	/**
	 * @param keyword
	 *            KeyWord to use in the search. May be <code>null</code> to get all modules.
	 * @return All DefaultModules that matches the given keyword
	 * @throws IOException
	 */
	List<ModuleInfo> search(String keyword) throws IOException;
}
