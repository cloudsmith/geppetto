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

import com.google.gson.annotations.Expose;

public class User extends TimestampedEntity {
	@Expose
	private String username;

	@Expose
	private String email;

	@Expose
	private String display_name;

	@Expose
	private Integer release_count;

	@Expose
	private Integer module_count;

	/**
	 * @return Display Name
	 */
	public String getDisplayName() {
		return display_name;
	}

	/**
	 * @return Email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return Count of modules
	 */
	public Integer getModuleCount() {
		return module_count;
	}

	/**
	 * @return Count of releases
	 */
	public Integer getReleaseCount() {
		return release_count;
	}

	/**
	 * @return Username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param displayName
	 *            Display Name
	 */
	public void setDisplayName(String displayName) {
		this.display_name = displayName;
	}

	/**
	 * @param email
	 *            Email address
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param username
	 *            Username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
