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

public class Dependency extends Entity {
	private static boolean safeEquals(Object a, Object b) {
		if(a == b)
			return true;
		if(a == null || b == null)
			return false;
		return a.equals(b);
	}

	@Expose
	private QName name;

	@Expose
	private String repository;

	@Expose
	private VersionRequirement version_requirement;

	@Override
	public boolean equals(Object other) {
		if(this == other)
			return true;
		if(!(other instanceof Dependency))
			return false;
		Dependency d = (Dependency) other;
		return safeEquals(name, d.name) && safeEquals(version_requirement, d.version_requirement);
	}

	/**
	 * @return the name
	 */
	public QName getName() {
		return name;
	}

	/**
	 * @return the repository
	 */
	public String getRepository() {
		return repository;
	}

	/**
	 * @return the version requirement
	 */
	public VersionRequirement getVersionRequirement() {
		return version_requirement;
	}

	@Override
	public int hashCode() {
		int code = 1;
		if(name != null)
			code = name.hashCode();
		else
			code = 773;
		if(version_requirement != null)
			code = code * 31 + version_requirement.hashCode();
		return code;
	}

	/**
	 * @param metadata
	 * @return
	 */
	public boolean matches(Metadata metadata) {
		return safeEquals(name, metadata.getName()) &&
				(version_requirement == null || version_requirement.matches(metadata.getVersion()));
	}

	/**
	 * Checks if this dependency is matched by the given release.
	 * 
	 * @param release
	 *            The release to match
	 * @return The result of the match
	 */
	public boolean matches(Release release) {
		return safeEquals(name, release.getFullName()) &&
				(version_requirement == null || version_requirement.matches(release.getVersion()));
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(QName name) {
		this.name = name;
	}

	/**
	 * @param repository
	 *            the repository to set
	 */
	public void setRepository(String repository) {
		this.repository = repository;
	}

	/**
	 * @param version_requirement
	 *            the version requirement to set
	 */
	public void setVersionRequirement(VersionRequirement version_requirement) {
		this.version_requirement = version_requirement;
	}
}
