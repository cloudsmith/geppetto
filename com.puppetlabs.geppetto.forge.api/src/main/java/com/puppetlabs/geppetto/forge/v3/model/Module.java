package com.puppetlabs.geppetto.forge.v3.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Module extends AbbrevModule {
	@Expose
	private Integer downloads;

	@Expose
	private Date created_at;

	@Expose
	private Date updated_at;

	@Expose
	private Release current_release;

	@Expose
	private List<AbbrevRelease> releases;

	@Expose
	private String homepage_url;

	@Expose
	private String issues_url;

	/**
	 * @return date when this module was created
	 */
	public Date getCreatedAt() {
		return created_at;
	}

	/**
	 * @return the modules latest release
	 */
	public Release getCurrentRelease() {
		return current_release;
	}

	/**
	 * @return the number of times this module has been downloaded
	 */
	public Integer getDownloads() {
		return downloads;
	}

	public String getHomepageURL() {
		return homepage_url;
	}

	public String getIssuesURL() {
		return issues_url;
	}

	/**
	 * @return all releases of the module
	 */
	public List<AbbrevRelease> getReleases() {
		return releases == null
				? Collections.<AbbrevRelease> emptyList()
				: Collections.unmodifiableList(releases);
	}

	/**
	 * @return date when this module was last updated
	 */
	public Date getUpdatedAt() {
		return updated_at;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.created_at = createdAt;
	}

	/**
	 * @param currentRelease
	 *            the currentRelease to set
	 */
	public void setCurrentRelease(Release currentRelease) {
		this.current_release = currentRelease;
	}

	/**
	 * @param downloads
	 *            the downloads to set
	 */
	public void setDownloads(Integer downloads) {
		this.downloads = downloads;
	}

	/**
	 * @param homepageURL
	 *            the homepageURL to set
	 */
	public void setHomepageURL(String homepageURL) {
		this.homepage_url = homepageURL;
	}

	/**
	 * @param issuesURL
	 *            the issuesURL to set
	 */
	public void setIssuesURL(String issuesURL) {
		this.issues_url = issuesURL;
	}

	/**
	 * @param releases
	 *            the releases to set
	 */
	public void setReleases(List<AbbrevRelease> releases) {
		this.releases = releases;
	}

	/**
	 * @param updatedAt
	 *            the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updated_at = updatedAt;
	}
}
