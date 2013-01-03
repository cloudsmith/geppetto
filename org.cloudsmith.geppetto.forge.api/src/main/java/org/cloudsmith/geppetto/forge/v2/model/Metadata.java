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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;

/**
 * Module meta-data
 */
public class Metadata extends Entity {
	@Expose
	private QName name;

	@Expose
	private String version;

	@Expose
	private String summary;

	@Expose
	private String author;

	@Expose
	private String description;

	@Expose
	private List<Dependency> dependencies;

	@Expose
	private List<Type> types;

	@Expose
	private Map<String, String> checksums;

	@Expose
	private String source;

	@Expose
	private String project_page;

	@Expose
	private String license;

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the checksums
	 */
	public Map<String, String> getChecksums() {
		return checksums == null
				? Collections.<String, String> emptyMap()
				: checksums;
	}

	/**
	 * @return the dependencies
	 */
	public List<Dependency> getDependencies() {
		return dependencies == null
				? Collections.<Dependency> emptyList()
				: dependencies;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * @return the name
	 */
	public QName getName() {
		return name;
	}

	/**
	 * @return the project_page
	 */
	public String getProject_page() {
		return project_page;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @return the types
	 */
	public List<Type> getTypes() {
		return types;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @param checksums
	 *            the checksums to set
	 */
	public void setChecksums(Map<String, String> checksums) {
		this.checksums = checksums;
	}

	/**
	 * @param dependencies
	 *            the dependencies to set
	 */
	public void setDependencies(List<Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param license
	 *            the license to set
	 */
	public void setLicense(String license) {
		this.license = license;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(QName name) {
		this.name = name;
	}

	/**
	 * @param project_page
	 *            the project_page to set
	 */
	public void setProject_page(String project_page) {
		this.project_page = project_page;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @param types
	 *            the types to set
	 */
	public void setTypes(List<Type> types) {
		this.types = types;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
}
