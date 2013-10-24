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
package com.puppetlabs.geppetto.forge.v2.service;

import java.io.IOException;
import java.util.List;

import com.puppetlabs.geppetto.forge.v2.model.Module;
import com.puppetlabs.geppetto.forge.v2.model.Release;
import com.puppetlabs.geppetto.forge.v2.model.User;

/**
 * A CRUD service for {@link User} objects
 */
public interface UserService {

	/**
	 * 
	 * @param name
	 *            The name of the user
	 * @return Details for a particular user
	 * @throws IOException
	 */
	User get(String name) throws IOException;

	/**
	 * @param name
	 *            The name of the user
	 * @param listPreferences
	 *            Pagination preferences or <code>null</code> to get all in no particular order
	 * @return DefaultModules for a particular user
	 * @throws IOException
	 */
	List<Module> getModules(String name, ListPreferences listPreferences) throws IOException;

	/**
	 * @param name
	 *            The name of the user
	 * @param listPreferences
	 *            Pagination preferences or <code>null</code> to get all in no particular order
	 * @return DefaultReleases for a particular user
	 * @throws IOException
	 */
	List<Release> getReleases(String name, ListPreferences listPreferences) throws IOException;

	/**
	 * @param listPreferences
	 * @return All users
	 * @throws IOException
	 */
	List<User> list(ListPreferences listPreferences) throws IOException;

}
