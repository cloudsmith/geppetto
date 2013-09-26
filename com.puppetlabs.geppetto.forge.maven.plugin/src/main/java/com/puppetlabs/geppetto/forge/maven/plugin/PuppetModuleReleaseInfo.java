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
package com.puppetlabs.geppetto.forge.maven.plugin;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.puppetlabs.geppetto.forge.model.Entity;
import com.puppetlabs.geppetto.forge.model.Metadata;

/**
 * Stores information about a release that cannot be contained in a POM
 */
public class PuppetModuleReleaseInfo extends Entity {
	@Expose
	private String changelog;

	@Expose
	private String license;

	@Expose
	private Metadata metadata;

	@Expose
	private String readme;

	@Expose
	private List<String> tags;

	public String getChangelog() {
		return changelog;
	}

	public String getLicense() {
		return license;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public String getReadme() {
		return readme;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setChangelog(String changelog) {
		this.changelog = changelog;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public void setReadme(String readme) {
		this.readme = readme;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
