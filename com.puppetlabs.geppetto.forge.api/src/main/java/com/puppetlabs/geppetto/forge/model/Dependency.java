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
package com.puppetlabs.geppetto.forge.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.puppetlabs.geppetto.forge.v2.model.Release;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;

/**
 * Describes a dependency from one module to another.
 */
public class Dependency extends Entity {
	/**
	 * We need a special serializer for Metadata because the name uses a dash as separator whereas
	 * the name in a dependency uses a slash.
	 */
	public static class JsonAdapter implements JsonSerializer<Dependency> {
		@Override
		public JsonElement serialize(Dependency src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
			JsonObject json = new JsonObject();
			if(src.getName() != null)
				json.add("name", new JsonPrimitive(src.getName().toString('/')));
			if(src.getRepository() != null)
				json.add("repository", context.serialize(src.getRepository()));
			if(src.getVersionRequirement() != null)
				json.add("version_requirement", context.serialize(src.getVersionRequirement()));
			return json;
		}
	}

	public static final JsonAdapter DEPENDENCY_ADAPTER = new JsonAdapter();

	@Expose
	private ModuleName name;

	@Expose
	private String repository;

	@Expose
	private VersionRange version_requirement;

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
	public ModuleName getName() {
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
	public VersionRange getVersionRequirement() {
		return version_requirement;
	}

	@Override
	public int hashCode() {
		int hash = safeHash(name);
		hash = hash * 31 + safeHash(version_requirement);
		return hash;
	}

	/**
	 * @param metadata
	 * @return
	 */
	public boolean matches(Metadata metadata) {
		return safeEquals(name, metadata.getName()) &&
				(version_requirement == null || version_requirement.isIncluded(metadata.getVersion()));
	}

	/**
	 * Checks if this dependency is matched by the name and version
	 * 
	 * @param name
	 *            The name to match
	 * @param version
	 *            The version to match
	 * @return The result of the match
	 */
	public boolean matches(ModuleName qname, Version version) {
		return safeEquals(name, qname) && (version_requirement == null || version_requirement.isIncluded(version));
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
				(version_requirement == null || version_requirement.isIncluded(release.getVersion()));
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(ModuleName name) {
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
	public void setVersionRequirement(VersionRange version_requirement) {
		this.version_requirement = version_requirement;
	}
}
