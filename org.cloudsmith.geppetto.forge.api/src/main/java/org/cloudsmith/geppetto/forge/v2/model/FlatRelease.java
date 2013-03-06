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

import org.org.cloudsmith.geppetto.semver.Version;

import com.google.gson.annotations.Expose;

/**
 * Describes a module release
 */
public class FlatRelease extends TimestampedEntity {
	@Expose
	private Version version;

	@Expose
	private String notes;

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
	 * @return JSON metadata
	 */
	public Metadata getMetadata() {
		return metadata;
	}

	/**
	 * @return Notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @return Version
	 */
	public Version getVersion() {
		return version;
	}
}
