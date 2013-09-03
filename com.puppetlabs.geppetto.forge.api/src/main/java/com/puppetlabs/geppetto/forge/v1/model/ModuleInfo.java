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
package com.puppetlabs.geppetto.forge.v1.model;

import java.util.Collections;
import java.util.List;

import com.puppetlabs.geppetto.forge.model.Entity;
import com.puppetlabs.geppetto.forge.v2.model.ModuleName;
import com.puppetlabs.geppetto.semver.Version;

import com.google.gson.annotations.Expose;

public class ModuleInfo extends Entity {
	@Expose
	private String author;

	@Expose
	private ModuleName full_name;

	@Expose
	private String name;

	@Expose
	private String desc;

	@Expose
	private String project_url;

	@Expose
	private Version version;

	@Expose
	private List<String> tag_list;

	@Expose
	private List<Release> releases;

	/**
	 * @param moduleName
	 * @param version
	 */
	public ModuleInfo(ModuleName moduleName, Version version) {
		this.full_name = moduleName;
		this.name = moduleName == null
				? null
				: moduleName.getName();
		this.version = version;
	}

	public String getAuthor() {
		return author;
	}

	public String getDesc() {
		return desc;
	}

	public ModuleName getFullName() {
		return full_name;
	}

	public String getName() {
		return name;
	}

	public String getProjectUrl() {
		return project_url;
	}

	public List<Release> getReleases() {
		return releases == null
				? Collections.<Release> emptyList()
				: releases;
	}

	public List<String> getTagList() {
		return tag_list == null
				? Collections.<String> emptyList()
				: tag_list;
	}

	public Version getVersion() {
		return version;
	}
}
