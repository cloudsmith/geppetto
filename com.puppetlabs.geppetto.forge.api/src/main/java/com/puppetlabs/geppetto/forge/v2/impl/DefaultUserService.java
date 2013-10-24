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
package com.puppetlabs.geppetto.forge.v2.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import com.puppetlabs.geppetto.forge.model.Constants;
import com.puppetlabs.geppetto.forge.v2.model.Module;
import com.puppetlabs.geppetto.forge.v2.model.Release;
import com.puppetlabs.geppetto.forge.v2.model.User;
import com.puppetlabs.geppetto.forge.v2.service.ListPreferences;
import com.puppetlabs.geppetto.forge.v2.service.UserService;

public class DefaultUserService extends AbstractForgeService implements UserService {
	private static String getUserPath(String name) {
		return Constants.COMMAND_GROUP_USERS + '/' + name;
	}

	@Override
	public User get(String name) throws IOException {
		return getClient(false).getV2(getUserPath(name), null, User.class);
	}

	@Override
	public List<Module> getModules(String name, ListPreferences listPreferences) throws IOException {
		List<Module> modules = null;
		try {
			modules = getClient(false).getV2(
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

	@Override
	public List<Release> getReleases(String name, ListPreferences listPreferences) throws IOException {
		List<Release> releases = null;
		try {
			releases = getClient(false).getV2(
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

	@Override
	public List<User> list(ListPreferences listPreferences) throws IOException {
		List<User> users = null;
		try {
			users = getClient(false).getV2(
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
