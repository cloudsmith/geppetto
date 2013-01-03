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
package org.cloudsmith.geppetto.forge.v2.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.Expose;

/**
 */
public class Module extends TimestampedEntity {
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
	private User owner;

	@Expose
	private List<String> tagarray = Collections.emptyList();

	@Expose
	private FlatRelease current_release;

	@Expose
	private List<AnnotatedLink> releases;

	/**
	 * @return Commit Feed URL
	 */
	public String getCommitFeedURL() {
		return commit_feed_url;
	}

	/**
	 * @return Current release for module
	 */
	public FlatRelease getCurrentRelease() {
		return current_release;
	}

	/**
	 * @return Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return The full name as &quot;&lt;owner&gt;/&lt;module&gt;&quot;
	 */
	public QName getFullName() {
		if(owner != null && name != null) {
			String ownerName = owner.getUsername();
			if(ownerName != null)
				return new QName(ownerName, name);
		}
		return null;
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
	public User getOwner() {
		return owner;
	}

	/**
	 * @return the releases
	 */
	public List<AnnotatedLink> getReleases() {
		return releases;
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
		return new ArrayList<String>(tagarray);
	}

	/**
	 * @param commitFeedURL
	 *            Commit Feed URL
	 */
	public void setCommitFeedURL(String commitFeedURL) {
		this.commit_feed_url = commitFeedURL;
	}

	/**
	 * @param currentRelease
	 *            Current release for module
	 */
	public void setCurrentRelease(FlatRelease currentRelease) {
		this.current_release = currentRelease;
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
	public void setOwner(User owner) {
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
		this.tagarray = tags == null
				? Collections.<String> emptyList()
				: tags;
	}
}
