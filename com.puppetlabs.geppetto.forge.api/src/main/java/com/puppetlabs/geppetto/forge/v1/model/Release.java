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
package com.puppetlabs.geppetto.forge.v1.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.puppetlabs.geppetto.semver.Version;

public class Release {
	@Expose
	private Version version;

	@Expose
	private String file;

	@Expose
	private List<String[]> dependencies;

	/**
	 * @return the dependencies
	 */
	public List<String[]> getDependencies() {
		return dependencies;
	}

	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}

	public Version getVersion() {
		return version;
	}

	/**
	 * @param dependencies
	 *            the dependencies to set
	 */
	public void setDependencies(List<String[]> dependencies) {
		this.dependencies = dependencies;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Version version) {
		this.version = version;
	}
}
