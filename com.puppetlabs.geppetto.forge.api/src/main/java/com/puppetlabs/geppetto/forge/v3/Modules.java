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
package com.puppetlabs.geppetto.forge.v3;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.reflect.TypeToken;
import com.puppetlabs.geppetto.forge.client.GsonModule.DateJsonAdapter;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.v3.model.Module;
import com.puppetlabs.geppetto.forge.v3.model.PaginatedResult;

public class Modules extends ForgeService<Module, ModuleName> {

	public static class ChangedSince extends Compare<Module> {
		public ChangedSince(Date changedSince) {
			super("changed_since", DateJsonAdapter.dateToString(changedSince));
		}
	}

	public static class OwnedBy extends Compare<Module> {
		public OwnedBy(String owner) {
			super("owner", owner);
		}
	}

	public static class WithTag extends Compare<Module> {
		public WithTag(String tag) {
			super("tag", tag);
		}
	}

	public static class WithText extends Compare<Module> {
		public WithText(String keyword) {
			super("query", keyword);
		}
	}

	public static final SortBy<Module> RANK = new SortBy<Module>("rank");

	public static final SortBy<Module> DOWNLOAD_COUNT = new SortBy<Module>("download_count");

	public static final SortBy<Module> LAST_RELEASED = new SortBy<Module>("last_released");

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
		return Modules.RESULT_TYPE;
	}

}
