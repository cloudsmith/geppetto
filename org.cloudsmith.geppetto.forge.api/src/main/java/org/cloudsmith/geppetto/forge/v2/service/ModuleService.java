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
package org.cloudsmith.geppetto.forge.v2.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.cloudsmith.geppetto.forge.model.Constants;
import org.cloudsmith.geppetto.forge.v2.model.Module;
import org.cloudsmith.geppetto.forge.v2.model.Release;
import org.cloudsmith.geppetto.forge.v2.model.Tag;

/**
 * A CRUD service for {@link Module} objects
 */
public class ModuleService extends ForgeService {
	private static String getModulePath(String owner, String name) {
		return Constants.COMMAND_GROUP_MODULES + '/' + owner + '/' + name;
	}

	/**
	 * Create a new Module based on the given <code>module</code> template. The
	 * combination of name and owner in the template must be unique.
	 * 
	 * @param module
	 *            The template containing the data for the new module
	 * @return The created module
	 * @throws IOException
	 */
	public Module create(ModuleTemplate module) throws IOException {
		return getClient(true).postJSON(Constants.COMMAND_GROUP_MODULES, module, Module.class);
	}

	/**
	 * Delete the module identified by <code>owner</code> and <code>name</code>.
	 * 
	 * @param owner
	 *            The module owner.
	 * @param name
	 *            The name of the module.
	 * @throws IOException
	 */
	public void delete(String owner, String name) throws IOException {
		getClient(true).delete(getModulePath(owner, name));
	}

	/**
	 * @param owner
	 *            The module owner.
	 * @param name
	 *            The name of the module.
	 * @return Details of the module identified by <code>owner</code> and <code>name</code>.
	 * @throws IOException
	 */
	public Module get(String owner, String name) throws IOException {
		return getClient(false).get(getModulePath(owner, name), null, Module.class);
	}

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
	public List<Release> getReleases(String owner, String name, ListPreferences listPreferences) throws IOException {
		List<Release> releases = null;
		try {
			releases = getClient(false).get(
				getModulePath(owner, name) + "/releases", toQueryMap(listPreferences), Constants.LIST_RELEASE);
		}
		catch(HttpResponseException e) {
			if(e.getStatusCode() != HttpStatus.SC_NOT_FOUND)
				throw e;
		}
		if(releases == null)
			releases = Collections.emptyList();
		return releases;
	}

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
	public List<Tag> getTags(String owner, String name, ListPreferences listPreferences) throws IOException {
		List<Tag> tags = null;
		try {
			tags = getClient(false).get(
				getModulePath(owner, name) + "/tags", toQueryMap(listPreferences), Constants.LIST_TAG);
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
	 * Returns a list of a list of matching modules. Any module with a name that contains <code>keyword</code> is considered a match. The
	 * <code>keyword</code> can be null in which case a list of all modules
	 * will be returned.
	 * 
	 * @param keyword
	 *            A keyword to used for matching or <code>null</code> to get all modules.
	 * @param listPreferences
	 *            Pagination preferences or <code>null</code> to get all in no particular order.
	 * @return A list of matching modules.
	 * @throws IOException
	 */
	public List<Module> search(String keyword, ListPreferences listPreferences) throws IOException {
		Map<String, String> map = toQueryMap(listPreferences);
		if(keyword != null)
			map.put("keyword", keyword);
		List<Module> modules = null;
		try {
			modules = getClient(false).get(Constants.COMMAND_GROUP_MODULES, map, Constants.LIST_MODULE);
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
	public Module update(String owner, String name, ModuleTemplate module) throws IOException {
		return getClient(true).patch(getModulePath(owner, name), module, Module.class);
	}
}
