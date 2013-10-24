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
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import com.puppetlabs.geppetto.forge.model.Constants;
import com.puppetlabs.geppetto.forge.v2.model.Module;
import com.puppetlabs.geppetto.forge.v2.model.Release;
import com.puppetlabs.geppetto.forge.v2.model.Tag;
import com.puppetlabs.geppetto.forge.v2.service.ListPreferences;
import com.puppetlabs.geppetto.forge.v2.service.ModuleService;
import com.puppetlabs.geppetto.forge.v2.service.ModuleTemplate;

public class DefaultModuleService extends AbstractForgeService implements ModuleService {
	private static String getModulePath(String owner, String name) {
		return Constants.COMMAND_GROUP_MODULES + '/' + owner + '/' + name;
	}

	/* (non-Javadoc)
	 * @see com.puppetlabs.geppetto.forge.v2.service.ModuleService#create(com.puppetlabs.geppetto.forge.v2.service.ModuleTemplate)
	 */
	@Override
	public Module create(ModuleTemplate module) throws IOException {
		return getClient(true).postJSON(Constants.COMMAND_GROUP_MODULES, module, Module.class);
	}

	/* (non-Javadoc)
	 * @see com.puppetlabs.geppetto.forge.v2.service.ModuleService#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String owner, String name) throws IOException {
		getClient(true).delete(getModulePath(owner, name));
	}

	/* (non-Javadoc)
	 * @see com.puppetlabs.geppetto.forge.v2.service.ModuleService#get(java.lang.String, java.lang.String)
	 */
	@Override
	public Module get(String owner, String name) throws IOException {
		return getClient(false).getV2(getModulePath(owner, name), null, Module.class);
	}

	/* (non-Javadoc)
	 * @see com.puppetlabs.geppetto.forge.v2.service.ModuleService#getReleases(java.lang.String, java.lang.String, com.puppetlabs.geppetto.forge.v2.service.ListPreferences)
	 */
	@Override
	public List<Release> getReleases(String owner, String name, ListPreferences listPreferences) throws IOException {
		List<Release> releases = null;
		try {
			releases = getClient(false).getV2(
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

	/* (non-Javadoc)
	 * @see com.puppetlabs.geppetto.forge.v2.service.ModuleService#getTags(java.lang.String, java.lang.String, com.puppetlabs.geppetto.forge.v2.service.ListPreferences)
	 */
	@Override
	public List<Tag> getTags(String owner, String name, ListPreferences listPreferences) throws IOException {
		List<Tag> tags = null;
		try {
			tags = getClient(false).getV2(
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

	/* (non-Javadoc)
	 * @see com.puppetlabs.geppetto.forge.v2.service.ModuleService#search(java.lang.String, com.puppetlabs.geppetto.forge.v2.service.ListPreferences)
	 */
	@Override
	public List<Module> search(String keyword, ListPreferences listPreferences) throws IOException {
		Map<String, String> map = toQueryMap(listPreferences);
		if(keyword != null)
			map.put("keyword", keyword);
		List<Module> modules = null;
		try {
			modules = getClient(false).getV2(Constants.COMMAND_GROUP_MODULES, map, Constants.LIST_MODULE);
		}
		catch(HttpResponseException e) {
			if(e.getStatusCode() != HttpStatus.SC_NOT_FOUND)
				throw e;
		}
		if(modules == null)
			modules = Collections.emptyList();
		return modules;
	}

	/* (non-Javadoc)
	 * @see com.puppetlabs.geppetto.forge.v2.service.ModuleService#update(java.lang.String, java.lang.String, com.puppetlabs.geppetto.forge.v2.service.ModuleTemplate)
	 */
	@Override
	public Module update(String owner, String name, ModuleTemplate module) throws IOException {
		return getClient(true).patch(getModulePath(owner, name), module, Module.class);
	}
}
