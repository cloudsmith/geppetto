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

/**
 * A tag for a module
 */
public class Tag extends TimestampedEntity {
	@Expose
	private String name;

	@Expose
	private Integer module_count;

	/**
	 * @return Count of modules for this tag
	 */
	public Integer getModuleCount() {
		return module_count;
	}

	/**
	 * @return Name of tag
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            Name of tag
	 */
	public void setName(String name) {
		this.name = name;
	}
}
