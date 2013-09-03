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

import com.puppetlabs.geppetto.forge.model.Entity;

import com.google.gson.annotations.Expose;

/**
 * Represents a short list of collection references, whereby each item is
 * represented with a minimal amount of information and a link to find out
 * more info.
 */
public class AnnotatedLink extends Entity {
	@Expose
	private String slug;

	@Expose
	private String key;

	@Expose
	private SelfLink _links;

	/**
	 * @return the entity_key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the links
	 */
	public HalLink getLink() {
		return _links == null
				? null
				: _links.getSelf();
	}

	/**
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}
}
