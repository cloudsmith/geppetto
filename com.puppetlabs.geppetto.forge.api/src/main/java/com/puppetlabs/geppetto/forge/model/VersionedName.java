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
package com.puppetlabs.geppetto.forge.model;

import java.io.Serializable;

import com.puppetlabs.geppetto.semver.Version;

/**
 * A Module name with a version.
 */
public class VersionedName implements Serializable, Comparable<VersionedName> {
	private static final long serialVersionUID = 1L;

	/**
	 * Find the first dash or a slash that is followed by a digit. That position
	 * must denote the separator between the name and the version since the version
	 * must start with a digit and the name must start with a letter.
	 * 
	 * @param slug
	 *            The string to parse
	 * @return The position of the separator or -1 if it cannot be found
	 */
	public static int getVersionSeparatorPosition(String slug) {
		if(slug == null)
			return -1;

		int top = slug.length();
		for(int idx = 0; idx < top; ++idx) {
			switch(slug.charAt(idx)) {
				case '/':
				case '-':
					if(idx + 1 < top && Character.isDigit(slug.charAt(idx + 1)))
						return idx;
			}
		}
		return -1;
	}

	private final ModuleName moduleName;

	private final Version version;

	/**
	 * @param moduleName
	 * @param version
	 * 
	 * @return The created name
	 * @throws IllegalArgumentException
	 */
	public VersionedName(ModuleName moduleName, Version version) throws IllegalArgumentException {
		this.moduleName = moduleName;
		this.version = version;
	}

	/**
	 * Create a versioned name based on a slug. The slug consists of three parts that are separated
	 * by either '/' or '-'. The method {@link #getVersionSeparatorPosition(String)} is used when
	 * determining the position of the version separator.
	 * 
	 * @param slug
	 *            The string to create the owner/name/version from
	 * @return The created name
	 */
	public VersionedName(String slug) {
		int sep = getVersionSeparatorPosition(slug);
		if(sep <= 0)
			throw new IllegalArgumentException(
				"Must be a full module name (owner [-/] name) and a version separated by a dash or slash");

		moduleName = ModuleName.fromString(slug.substring(0, sep));
		version = Version.fromString(slug.substring(sep + 1));
	}

	/**
	 * @param moduleName
	 * @param version
	 * @return The created name
	 * @throws IllegalArgumentException
	 */
	public VersionedName(String moduleName, String version) throws IllegalArgumentException {
		this(ModuleName.fromString(moduleName), Version.fromString(version));
	}

	/**
	 * @param owner
	 * @param name
	 * @param version
	 * @return The created name
	 * @throws IllegalArgumentException
	 */
	public VersionedName(String owner, String name, String version) throws IllegalArgumentException {
		this(ModuleName.create(owner, name, false), Version.create(version));
	}

	/**
	 * @param owner
	 * @param name
	 * @param version
	 * @return The created name
	 * @throws IllegalArgumentException
	 */
	public VersionedName(String owner, String name, Version version) throws IllegalArgumentException {
		this(ModuleName.create(owner, name, false), version);
	}

	@Override
	public int compareTo(VersionedName o) {
		int cmp = getModuleName().compareTo(o.getModuleName());
		if(cmp == 0)
			cmp = getVersion().compareTo(o.getVersion());
		return cmp;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof VersionedName))
			return false;
		VersionedName vn = (VersionedName) o;
		return getModuleName().equals(vn.getModuleName()) && getVersion().equals(vn.getVersion());
	}

	public ModuleName getModuleName() {
		return moduleName;
	}

	public Version getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		return getModuleName().hashCode() * 31 + getVersion().hashCode();
	}

	/**
	 * Returns a string representation of this versioned name using the default
	 * version separator character '/'.
	 */
	@Override
	public String toString() {
		StringBuilder bld = new StringBuilder();
		toString(bld, '/');
		return bld.toString();
	}

	/**
	 * Append a string representation of this versioned name using the given version separator character
	 * onto the given builder.
	 * 
	 * @param bld
	 *            The builder that will receive the string representation
	 * @versionSep the separator that will be inserted between the module name and the version
	 */
	public void toString(StringBuilder bld, char versionSep) {
		getModuleName().toString(bld);
		bld.append(versionSep);
		getVersion().toString(bld);
	}
}
