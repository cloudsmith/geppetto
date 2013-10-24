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
import com.puppetlabs.geppetto.forge.v2.model.Tag;

/**
 * A CRUD service for {@link Module} objects
 */
public interface ModuleService extends ForgeService {

	/**
	 * Create a new Module based on the given <code>module</code> template. The
	 * combination of name and owner in the template must be unique.
	 * 
	 * @param module
	 *            The template containing the data for the new module
	 * @return The created module
	 * @throws IOException
	 */
	Module create(ModuleTemplate module) throws IOException;

	/**
	 * Delete the module identified by <code>owner</code> and <code>name</code>.
	 * 
	 * @param owner
	 *            The module owner.
	 * @param name
	 *            The name of the module.
	 * @throws IOException
	 */
	void delete(String owner, String name) throws IOException;

	/**
	 * @param owner
	 *            The module owner.
	 * @param name
	 *            The name of the module.
	 * @return Details of the module identified by <code>owner</code> and <code>name</code>.
	 * @throws IOException
	 */
	Module get(String owner, String name) throws IOException;

	/**
	 * @param owner
	 *            The module owner.
	 * @param name
	 *            The name of the module.
	 * @param listPreferences
	 *            Pagination preferences or <code>null</code> to get all in no particular order.
	 * @return All releases of the module identified by <code>owner</code> and <code>name</code>.
	 * @throws IOException
	 */
	List<Release> getReleases(String owner, String name, ListPreferences listPreferences) throws IOException;

	/**
	 * @param owner
	 *            The module owner.
	 * @param name
	 *            The name of the module.
	 * @param listPreferences
	 *            Pagination preferences or <code>null</code> to get all in no particular order.
	 * @return The tags attached to the module identified by <code>owner</code> and <code>name</code>.
	 * @throws IOException
	 */
	List<Tag> getTags(String owner, String name, ListPreferences listPreferences) throws IOException;

	/**
	 * Returns a list of a list of matching modules. Any module with a name that contains <code>keyword</code> is
	 * considered a match. The <code>keyword</code> can be null in which case a list of all modules
	 * will be returned.
	 * 
	 * @param keyword
	 *            A keyword to used for matching or <code>null</code> to get all modules.
	 * @param listPreferences
	 *            Pagination preferences or <code>null</code> to get all in no particular order.
	 * @return A list of matching modules.
	 * @throws IOException
	 */
	List<Module> search(String keyword, ListPreferences listPreferences) throws IOException;

	/**
	 * Updates the module identified with <code>owner</code> and <code>name</code> with
	 * new data.
	 * 
	 * @param owner
	 *            The module owner
	 * @param name
	 *            The module name
	 * @param module
	 *            New data for the module.
	 * @return The updated module
	 * @throws IOException
	 */
	Module update(String owner, String name, ModuleTemplate module) throws IOException;

}
