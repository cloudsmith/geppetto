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
package com.puppetlabs.geppetto.forge.v3.impl;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.puppetlabs.geppetto.forge.model.VersionedName;
import com.puppetlabs.geppetto.forge.v3.Releases;
import com.puppetlabs.geppetto.forge.v3.model.PaginatedResult;
import com.puppetlabs.geppetto.forge.v3.model.Release;

public class DefaultReleases extends AbstractForgeService<Release, VersionedName> implements Releases {

	/**
	 * A type representing a {@link PaginatedResult} of {@link Release} instances
	 */
	public static final Type RESULT_TYPE = new TypeToken<PaginatedResult<Release>>() {
	}.getType();

	@Override
	void addIdSegment(StringBuilder bld, VersionedName id) {
		id.getModuleName().withSeparator('-').toString(bld);
		bld.append('-');
		id.getVersion().toString(bld);
	}

	@Override
	Class<Release> getElementResultType() {
		return Release.class;
	}

	@Override
	String getEndpointSegment() {
		return "releases";
	}

	@Override
	Type getPaginatedResultType() {
		return RESULT_TYPE;
	}

}
