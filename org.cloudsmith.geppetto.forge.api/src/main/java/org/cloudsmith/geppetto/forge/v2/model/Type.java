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

import java.util.List;

import com.google.gson.annotations.Expose;

/**
 */
public class Type extends NamedTypeItem {
	@Expose
	private List<NamedTypeItem> properties;

	@Expose
	private List<NamedTypeItem> parameters;

	@Expose
	private List<NamedTypeItem> providers;

	/**
	 * @return the parameters
	 */
	public List<NamedTypeItem> getParameters() {
		return parameters;
	}

	/**
	 * @return the properties
	 */
	public List<NamedTypeItem> getProperties() {
		return properties;
	}

	/**
	 * @return the providers
	 */
	public List<NamedTypeItem> getProviders() {
		return providers;
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setParameters(List<NamedTypeItem> parameters) {
		this.parameters = parameters;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(List<NamedTypeItem> properties) {
		this.properties = properties;
	}

	/**
	 * @param providers
	 *            the providers to set
	 */
	public void setProviders(List<NamedTypeItem> providers) {
		this.providers = providers;
	}
}
