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

import java.util.List;

import org.cloudsmith.geppetto.forge.v2.model.Module;

import com.google.gson.annotations.Expose;

/**
 * An Module template to use when creating new modules.
 */
public class ModuleTemplate {
	@Expose
	private String owner;

	@Expose
	private String name;

	@Expose
	private String description;

	@Expose
	private String home_page_url;

	@Expose
	private String source_url;

	@Expose
	private String issues_url;

	@Expose
	private String commit_feed_url;

	@Expose
	private List<String> tags;

	public ModuleTemplate fromModule(Module module) {
		ModuleTemplate template = new ModuleTemplate();
		template.setOwner(module.getOwner().getUsername());
		template.setName(module.getName());
		template.setDescription(module.getDescription());
		template.setHomePageURL(module.getHomePageURL());
		template.setSourceURL(module.getSourceURL());
		template.setIssuesURL(module.getIssuesURL());
		template.setCommitFeedURL(module.getCommitFeedURL());
		template.setTags(module.getTags());
		return template;
	}

	/**
	 * @return Commit Feed URL
	 */
	public String getCommitFeedURL() {
		return commit_feed_url;
	}

	/**
	 * @return Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return Home Page URL
	 */
	public String getHomePageURL() {
		return home_page_url;
	}

	/**
	 * @return Issues URL
	 */
	public String getIssuesURL() {
		return issues_url;
	}

	/**
	 * @return Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @return Source URL
	 */
	public String getSourceURL() {
		return source_url;
	}

	/**
	 * @return List of tag names
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * @param commitFeedURL
	 *            Commit Feed URL
	 */
	public void setCommitFeedURL(String commitFeedURL) {
		this.commit_feed_url = commitFeedURL;
	}

	/**
	 * @param description
	 *            Description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param homePageURL
	 *            Home Page URL
	 */
	public void setHomePageURL(String homePageURL) {
		this.home_page_url = homePageURL;
	}

	/**
	 * @param issuesURL
	 *            Issues URL
	 */
	public void setIssuesURL(String issuesURL) {
		this.issues_url = issuesURL;
	}

	/**
	 * @param name
	 *            Name of module
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param owner
	 *            Name of owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @param sourceURL
	 *            Source URL
	 */
	public void setSourceURL(String sourceURL) {
		this.source_url = sourceURL;
	}

	/**
	 * @param tags
	 *            Tag names
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
