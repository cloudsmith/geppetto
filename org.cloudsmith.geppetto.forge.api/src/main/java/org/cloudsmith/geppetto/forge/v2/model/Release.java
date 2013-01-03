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
 * Describes a module release
 */
public class Release extends FlatRelease {
	@Expose
	private Module module;

	/**
	 * @return The full name as &quot;&lt;owner&gt;/&lt;module&gt;&quot;
	 */
	public QName getFullName() {
		return module == null
				? null
				: module.getFullName();
	}

	/**
	 * @return Module that this is a release of
	 */
	public Module getModule() {
		return module;
	}
}
