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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.forge.client.ForgeClient;
import com.puppetlabs.geppetto.forge.model.Entity;
import com.puppetlabs.geppetto.forge.v3.ForgeService;
import com.puppetlabs.geppetto.forge.v3.NullProgressMonitor;
import com.puppetlabs.geppetto.forge.v3.PaginationInfo;
import com.puppetlabs.geppetto.forge.v3.ProgressMonitor;
import com.puppetlabs.geppetto.forge.v3.SortBy;
import com.puppetlabs.geppetto.forge.v3.Visitor;
import com.puppetlabs.geppetto.forge.v3.model.PaginatedResult;

public abstract class AbstractForgeService<T extends Entity, I> implements ForgeService<T, I> {
	private static <T extends Entity> PaginationInfo visitPage(PaginatedResult<T> page, Visitor<T> visitor,
			ProgressMonitor progressMonitor) throws InvocationTargetException {
		for(T entity : page.getResults()) {
			if(progressMonitor.isCanceled())
				return null;
			visitor.visit(entity, progressMonitor);
		}
		return page.getNext();

	}

	@Inject
	private ForgeClient client;

	/* (non-Javadoc)
	 * @see com.puppetlabs.geppetto.forge.v3.ForgeService#accept(com.puppetlabs.geppetto.forge.v3.AbstractForgeService.Query, com.puppetlabs.geppetto.forge.v3.AbstractForgeService.SortBy, boolean, com.puppetlabs.geppetto.forge.v3.AbstractForgeService.Visitor, com.puppetlabs.geppetto.forge.v3.AbstractForgeService.ProgressMonitor)
	 */
	@Override
	public void accept(Query<T> query, SortBy<T> sortBy, boolean includeDeleted, Visitor<T> visitor,
			ProgressMonitor progressMonitor) throws IOException, InvocationTargetException {
		if(progressMonitor == null)
			progressMonitor = new NullProgressMonitor();

		PaginatedResult<T> page = list(query, sortBy, null, includeDeleted);
		progressMonitor.beginTask(page.getTotal());

		PaginationInfo pi = visitPage(page, visitor, progressMonitor);
		while(pi != null)
			pi = visitPage(list(query, sortBy, pi, includeDeleted), visitor, progressMonitor);

		if(!progressMonitor.isCanceled())
			progressMonitor.endTask();
	}

	abstract void addIdSegment(StringBuilder bld, I id);

	/* (non-Javadoc)
	 * @see com.puppetlabs.geppetto.forge.v3.ForgeService#get(I)
	 */
	@Override
	public T get(I id) throws IOException {
		StringBuilder bld = new StringBuilder();
		bld.append(getEndpointSegment());
		bld.append('/');
		addIdSegment(bld, id);
		return client.get(bld.toString(), null, getElementResultType());
	}

	abstract Class<T> getElementResultType();

	abstract String getEndpointSegment();

	abstract Type getPaginatedResultType();

	/* (non-Javadoc)
	 * @see com.puppetlabs.geppetto.forge.v3.ForgeService#list(com.puppetlabs.geppetto.forge.v3.AbstractForgeService.Query, com.puppetlabs.geppetto.forge.v3.AbstractForgeService.SortBy, com.puppetlabs.geppetto.forge.v3.AbstractForgeService.PaginationInfo, boolean)
	 */
	@Override
	public PaginatedResult<T> list(Query<T> query, SortBy<T> sortBy, PaginationInfo pagination, boolean includeDeleted)
			throws IOException {
		Map<String, String> params;
		if(includeDeleted || query != null || sortBy != null || pagination != null) {
			params = new HashMap<String, String>();
			if(query != null)
				query.append(params);
			if(sortBy != null)
				sortBy.append(params);
			if(pagination != null)
				pagination.append(params);
			if(includeDeleted)
				params.put("show_deleted", "1");
		}
		else
			params = Collections.emptyMap();
		return client.get(getEndpointSegment(), params, getPaginatedResultType());
	}
}
