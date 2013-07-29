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
package org.cloudsmith.geppetto.forge.v2.model;

import java.util.Date;

import org.cloudsmith.geppetto.forge.model.Entity;

import com.google.gson.annotations.Expose;

/**
 */
public class TimestampedEntity extends Entity {
	@Expose
	private Date created_at;

	@Expose
	private Date updated_at;

	@Expose
	private Integer id;

	/**
	 * @return Date of creation
	 */
	public Date getCreatedAt() {
		return created_at;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return Date of modification
	 */
	public Date getUpdatedAt() {
		return updated_at;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
}
