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
import com.puppetlabs.geppetto.forge.v2.model.Tag;

/**
 * A CRUD service for {@link Tag} objects
 */
public interface TagService extends ForgeService {

	/**
	 * Create a new Tag based on the given <code>Tag</code> template. The
	 * tag name must be unique.
	 * 
	 * @param tag
	 *            The template containing the data for the new Tag
	 * @return The created Tag
	 * @throws IOException
	 */
	Tag create(Tag tag) throws IOException;

	/**
	 * Delete the Tag identified by <code>name</code>.
	 * 
	 * @param name
	 *            The name of the Tag.
	 * @throws IOException
	 */
	void delete(String name) throws IOException;

	/**
	 * @param name
	 *            The name of the Tag.
	 * @return Details about a particular Tag
	 * @throws IOException
	 */
	Tag get(String name) throws IOException;

	/**
	 * Returns a list of all known Tags.
	 * 
	 * @param listPreferences
	 *            Pagination preferences or <code>null</code> to get all in no particular order.
	 * @return A list of all Tags.
	 * @throws IOException
	 */
	List<Tag> getAll(ListPreferences listPreferences) throws IOException;

	/**
	 * @param name
	 *            The name of the Tag.
	 * @param listPreferences
	 * @return DefaultModules for a particular tag
	 * @throws IOException
	 */
	List<Module> getModules(String name, ListPreferences listPreferences) throws IOException;

	/**
	 * Updates the Tag identified by <code>name</code> with
	 * new data.
	 * 
	 * @param name
	 *            The name of the Tag.
	 * @param tag
	 *            New data for the Tag.
	 * @return The updated Tag
	 * @throws IOException
	 */
	Tag update(String name, Tag tag) throws IOException;

}
