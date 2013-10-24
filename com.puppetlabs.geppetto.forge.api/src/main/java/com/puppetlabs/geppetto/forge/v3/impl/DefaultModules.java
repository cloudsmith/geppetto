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
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.v3.Modules;
import com.puppetlabs.geppetto.forge.v3.model.Module;
import com.puppetlabs.geppetto.forge.v3.model.PaginatedResult;

public class DefaultModules extends AbstractForgeService<Module, ModuleName> implements Modules {

	/**
	 * A type representing a {@link PaginatedResult} of {@link Module} instances
	 */
	public static final Type RESULT_TYPE = new TypeToken<PaginatedResult<Module>>() {
	}.getType();

	@Override
	void addIdSegment(StringBuilder bld, ModuleName id) {
		id.withSeparator('-').toString(bld);
	}

	@Override
	Class<Module> getElementResultType() {
		return Module.class;
	}

	@Override
	String getEndpointSegment() {
		return "modules";
	}

	@Override
	Type getPaginatedResultType() {
		return RESULT_TYPE;
	}

}
