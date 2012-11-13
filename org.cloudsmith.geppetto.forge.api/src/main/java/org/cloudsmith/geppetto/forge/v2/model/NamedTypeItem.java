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

/**
 * Property, Parameter, or Provider for a {@link Type}.
 */
public class NamedTypeItem extends Entity {
	@Expose
	private String name;

	@Expose
	private String doc;

	/**
	 * @return Documentation for the property
	 */
	public String getDocumentation() {
		return doc;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param documentation
	 *            Documentation for the property
	 * 
	 */
	public void setDocumentation(String documentation) {
		this.doc = documentation;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
