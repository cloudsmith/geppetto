/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 * 
 */
package com.puppetlabs.geppetto.forge.v3.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.puppetlabs.geppetto.forge.model.Metadata;

/**
 * A release is a specific version of a module
 */
public class Release extends AbbrevRelease {
	@Expose
	private AbbrevModule module;

	@Expose
	private Metadata metadata;

	@Expose
	private List<String> tags;

	@Expose
	private Long file_size;

	@Expose
	private String file_type;

	@Expose
	private String file_md5;

	@Expose
	private Integer downloads;

	@Expose
	private String readme;

	@Expose
	private String changelog;

	@Expose
	private String license;

	@Expose
	private Date created_at;

	@Expose
	private Date updated_at;

	@Expose
	private Date deleted_at;

	/**
	 * @return the changelog file extracted from the module
	 */
	public String getChangelog() {
		return changelog;
	}

	/**
	 * @return the date this release was created
	 */
	public Date getCreatedAt() {
		return created_at;
	}

	/**
	 * @return the date this release was deleted or <code>null</code>
	 */
	public Date getDeletedAt() {
		return deleted_at;
	}

	/**
	 * @return Download count
	 */
	public Integer getDownloads() {
		return downloads;
	}

	/**
	 * @return MD5 of the file
	 */
	public String getFileMD5() {
		return file_md5;
	}

	/**
	 * @return File size
	 */
	public Long getFileSize() {
		return file_size;
	}

	/**
	 * @return File type
	 */
	public String getFileType() {
		return file_type;
	}

	/**
	 * @return the license file extracted from the module
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * @return the metadata for this release
	 */
	public Metadata getMetadata() {
		return metadata;
	}

	/**
	 * @return the abbrieviated representation of this releases module
	 */
	public AbbrevModule getModule() {
		return module;
	}

	/**
	 * @return the readme file extracted from the module
	 */
	public String getReadme() {
		return readme;
	}

	/**
	 * @return the tags on this release
	 */
	public List<String> getTags() {
		return tags == null
				? Collections.<String> emptyList()
				: Collections.unmodifiableList(tags);
	}

	/**
	 * @return the datae this release was last changed
	 */
	public Date getUpdatedAt() {
		return updated_at;
	}

	/**
	 * @param changelog
	 *            the changelog to set
	 */
	public void setChangelog(String changelog) {
		this.changelog = changelog;
	}

	/**
	 * @param createdAt
	 *            the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.created_at = createdAt;
	}

	/**
	 * @param deletedAt
	 *            the deletedAt to set
	 */
	public void setDeletedAt(Date deletedAt) {
		this.deleted_at = deletedAt;
	}

	/**
	 * @param downloads
	 *            the downloads to set
	 */
	public void setDownloads(Integer downloads) {
		this.downloads = downloads;
	}

	/**
	 * @param fileMD5
	 *            the fileMD5 to set
	 */
	public void setFileMD5(String fileMD5) {
		this.file_md5 = fileMD5;
	}

	/**
	 * @param fileSize
	 *            the fileSize to set
	 */
	public void setFileSize(Long fileSize) {
		this.file_size = fileSize;
	}

	/**
	 * @param fileType
	 *            the fileType to set
	 */
	public void setFileType(String fileType) {
		this.file_type = fileType;
	}

	/**
	 * @param license
	 *            the license to set
	 */
	public void setLicense(String license) {
		this.license = license;
	}

	/**
	 * @param metadata
	 *            the metadata to set
	 */
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	/**
	 * @param module
	 *            the module to set
	 */
	public void setModule(AbbrevModule module) {
		this.module = module;
	}

	/**
	 * @param readme
	 *            the readme to set
	 */
	public void setReadme(String readme) {
		this.readme = readme;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * @param updatedAt
	 *            the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updated_at = updatedAt;
	}
}
