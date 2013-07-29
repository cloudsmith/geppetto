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
package org.cloudsmith.geppetto.forge.v2.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.cloudsmith.geppetto.forge.model.Constants;
import org.cloudsmith.geppetto.forge.v2.model.Module;
import org.cloudsmith.geppetto.forge.v2.model.Tag;

/**
 * A CRUD service for {@link Tag} objects
 */
public class TagService extends ForgeService {
	private static String getTagPath(String name) {
		return Constants.COMMAND_GROUP_TAGS + '/' + name;
	}

	/**
	 * Create a new Tag based on the given <code>Tag</code> template. The
	 * tag name must be unique.
	 * 
	 * @param tag
	 *            The template containing the data for the new Tag
	 * @return The created Tag
	 * @throws IOException
	 */
	public Tag create(Tag tag) throws IOException {
		return getClient(true).postJSON(Constants.COMMAND_GROUP_TAGS, tag, Tag.class);

	}

	/**
	 * Delete the Tag identified by <code>name</code>.
	 * 
	 * @param name
	 *            The name of the Tag.
	 * @throws IOException
	 */
	public void delete(String name) throws IOException {
		getClient(true).delete(getTagPath(name));
	}

	/**
	 * @param name
	 *            The name of the Tag.
	 * @return Details about a particular Tag
	 * @throws IOException
	 */
	public Tag get(String name) throws IOException {
		return getClient(false).get(getTagPath(name), null, Tag.class);
	}

	/**
	 * Returns a list of all known Tags.
	 * 
	 * @param listPreferences
	 *            Pagination preferences or <code>null</code> to get all in no particular order.
	 * @return A list of all Tags.
	 * @throws IOException
	 */
	public List<Tag> getAll(ListPreferences listPreferences) throws IOException {
		List<Tag> tags = null;
		try {
			tags = getClient(false).get(Constants.COMMAND_GROUP_TAGS, toQueryMap(listPreferences), Constants.LIST_TAG);
		}
		catch(HttpResponseException e) {
			if(e.getStatusCode() != HttpStatus.SC_NOT_FOUND)
				throw e;
		}
		if(tags == null)
			tags = Collections.emptyList();
		return tags;
	}

	/**
	 * @param name
	 *            The name of the Tag.
	 * @param listPreferences
	 * @return Modules for a particular tag
	 * @throws IOException
	 */
	public List<Module> getModules(String name, ListPreferences listPreferences) throws IOException {
		List<Module> modules = null;
		try {
			modules = getClient(false).get(
				getTagPath(name) + "/modules", toQueryMap(listPreferences), Constants.LIST_MODULE);
		}
		catch(HttpResponseException e) {
			if(e.getStatusCode() != HttpStatus.SC_NOT_FOUND)
				throw e;
		}
		if(modules == null)
			modules = Collections.emptyList();
		return modules;
	}

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
	public Tag update(String name, Tag tag) throws IOException {
		return getClient(true).patch(getTagPath(name), tag, Tag.class);
	}
}
