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
package com.puppetlabs.geppetto.forge.v3.model;

import java.util.Date;

import com.google.gson.annotations.Expose;

public class User extends AbbrevUser {
	@Expose
	private String display_name;

	@Expose
	private Integer release_count;

	@Expose
	private Integer module_count;

	@Expose
	private Date created_at;

	@Expose
	private Date updated_at;

	/**
	 * @return When was this user account created
	 */
	public Date getCreatedAt() {
		return created_at;
	}

	/**
	 * @return the user's display name
	 */
	public String getDisplayName() {
		return display_name;
	}

	/**
	 * @return How many modules has this user published
	 */
	public Integer getModuleCount() {
		return module_count;
	}

	/**
	 * @return How many releases has this user published
	 */
	public Integer getReleaseCount() {
		return release_count;
	}

	/**
	 * @return When was this user account last updated
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
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.display_name = displayName;
	}

	/**
	 * @param moduleCount
	 *            the moduleCount to set
	 */
	public void setModuleCount(Integer moduleCount) {
		this.module_count = moduleCount;
	}

	/**
	 * @param releaseCount
	 *            the releaseCount to set
	 */
	public void setReleaseCount(Integer releaseCount) {
		this.release_count = releaseCount;
	}

	/**
	 * @param updatedAt
	 *            the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updated_at = updatedAt;
	}
}
