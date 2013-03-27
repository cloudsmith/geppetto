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

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.cloudsmith.geppetto.forge.v2.client.Constants;
import org.cloudsmith.geppetto.forge.v2.model.Module;
import org.cloudsmith.geppetto.forge.v2.model.Release;
import org.cloudsmith.geppetto.forge.v2.model.User;

/**
 * A CRUD service for {@link User} objects
 */
public class UserService extends ForgeService {
	private static String getUserPath(String name) {
		return Constants.COMMAND_GROUP_USERS + '/' + name;
	}

	/**
	 * 
	 * @param name
	 *            The name of the user
	 * @return Details for a particular user
	 * @throws IOException
	 */
	public User get(String name) throws IOException {
		return getClient(false).get(getUserPath(name), null, User.class);
	}

	/**
	 * @param name
	 *            The name of the user
	 * @param listPreferences
	 *            Pagination preferences or <code>null</code> to get all in no particular order
	 * @return Modules for a particular user
	 * @throws IOException
	 */
	public List<Module> getModules(String name, ListPreferences listPreferences) throws IOException {
		List<Module> modules = null;
		try {
			modules = getClient(false).get(
				getUserPath(name) + "/modules", toQueryMap(listPreferences), Constants.LIST_MODULE);
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
	 * @param name
	 *            The name of the user
	 * @param listPreferences
	 *            Pagination preferences or <code>null</code> to get all in no particular order
	 * @return Releases for a particular user
	 * @throws IOException
	 */
	public List<Release> getReleases(String name, ListPreferences listPreferences) throws IOException {
		List<Release> releases = null;
		try {
			releases = getClient(false).get(
				getUserPath(name) + "/releases", toQueryMap(listPreferences), Constants.LIST_RELEASE);
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
	 * @param listPreferences
	 * @return All users
	 * @throws IOException
	 */
	public List<User> list(ListPreferences listPreferences) throws IOException {
		List<User> users = null;
		try {
			users = getClient(false).get(
				Constants.COMMAND_GROUP_USERS, toQueryMap(listPreferences), Constants.LIST_USER);
		}
		catch(HttpResponseException e) {
			if(e.getStatusCode() != HttpStatus.SC_NOT_FOUND)
				throw e;
		}
		if(users == null)
			users = Collections.emptyList();
		return users;
	}
}
