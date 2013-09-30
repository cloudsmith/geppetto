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
package com.puppetlabs.geppetto.forge.v2.model;

import com.google.gson.annotations.Expose;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.semver.Version;

/**
 * Describes a module release
 */
public class FlatRelease extends TimestampedEntity {
	@Expose
	private Version version;

	@Expose
	private Metadata metadata;

	@Expose
	private Long downloads;

	@Expose
	private String file_type;

	@Expose
	private Long file_size;

	@Expose
	private String file_md5;

	@Expose
	private String slug;

	@Expose
	private String readme;

	@Expose
	private String changelog;

	@Expose
	private String license;

	/**
	 * @return the changelog
	 */
	public String getChangelog() {
		return changelog;
	}

	/**
	 * @return Download count
	 */
	public Long getDownloads() {
		return downloads;
	}

	/**
	 * @return MD5 of file
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
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * @return JSON metadata
	 */
	public Metadata getMetadata() {
		return metadata;
	}

	/**
	 * @return the readme
	 */
	public String getReadme() {
		return readme;
	}

	/**
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}

	/**
	 * @return Version
	 */
	public Version getVersion() {
		return version;
	}

	/**
	 * @param changelog
	 *            the changelog to set
	 */
	public void setChangelog(String changelog) {
		this.changelog = changelog;
	}

	/**
	 * @param downloads
	 *            the downloads to set
	 */
	public void setDownloads(Long downloads) {
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
	 * @param readme
	 *            the readme to set
	 */
	public void setReadme(String readme) {
		this.readme = readme;
	}

	/**
	 * @param slug
	 *            the slug to set
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Version version) {
		this.version = version;
	}
}
