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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.forge.client.ForgeClient;
import com.puppetlabs.geppetto.forge.model.Entity;
import com.puppetlabs.geppetto.forge.v3.model.PaginatedResult;

public abstract class ForgeService<T extends Entity, I> {
	public static class And<T extends Entity> extends ArrayList<Query<T>> implements Query<T> {
		private static final long serialVersionUID = 1L;

		public And(Collection<Query<T>> queries) {
			super(queries);
		}

		public And(Query<T> a, Query<T> b) {
			add(a);
			add(b);
		}

		public And(Query<T> a, Query<T> b, Query<T> c) {
			add(a);
			add(b);
			add(c);
		}

		public void append(Map<String, String> collector) {
			for(Query<T> query : this)
				query.append(collector);
		}
	}

	public static class Compare<T extends Entity> implements Query<T> {
		private final String what;

		private final String value;

		/**
		 * @param what
		 *            What to query
		 * @param value
		 *            The query value
		 */
		Compare(String what, String value) {
			this.what = what;
			this.value = value;
		}

		@Override
		public void append(Map<String, String> collector) {
			collector.put(what, value);
		}
	}

	public static class NullProgressMonitor implements ProgressMonitor {
		private boolean canceled = false;

		@Override
		public void beginTask(int toDo) {
		}

		@Override
		public void cancel() {
			canceled = true;
		}

		@Override
		public void endTask() {
		}

		@Override
		public boolean isCanceled() {
			return canceled;
		}

		@Override
		public void work(String message, int workDone) {
		}
	}

	public static class PaginationInfo implements Parameters {
		private final int offset;

		private final int limit;

		/**
		 * @param offset
		 *            where the page starts in the total list of matching results
		 * @param limit
		 *            how many results are returned per page (maximum is limited to 20, and the default is 20)
		 */
		public PaginationInfo(int offset, int limit) {
			this.offset = offset;
			this.limit = limit;
		}

		@Override
		public void append(Map<String, String> collector) {
			collector.put("offset", Integer.toString(offset));
			collector.put("limit", Integer.toString(limit));
		}
	}

	public interface Parameters {
		void append(Map<String, String> collector);
	}

	public interface ProgressMonitor {
		/**
		 * Starts a new task with <code>totalWorkUnits</code>.
		 * 
		 * @param totalWorkUnits
		 *            The total number of work units to allocate for the task
		 */
		public void beginTask(int toDo);

		/**
		 * Marks this monitor as cancelled
		 */
		void cancel();

		/**
		 * Ends a task
		 */
		public void endTask();

		/**
		 * @return <code>true</code> if this monitor has been marked as canceled.
		 */
		boolean isCanceled();

		/**
		 * Marks work is underway and <code>workDone</code> work-units as done.
		 * 
		 * @param message
		 *            The message to show
		 * @param workDone
		 *            The number of work units that has been consumed
		 */
		public void work(String message, int workDone);
	}

	public interface Query<T extends Entity> extends Parameters {
	}

	public static class SortBy<T extends Entity> implements Parameters {
		private String columnName;

		public SortBy(String columnName) {
			this.columnName = columnName;
		}

		@Override
		public void append(Map<String, String> collector) {
			collector.put("sort_by", columnName);
		}
	}

	/**
	 * Visitor used by the {@link ForgeService#accept(Compare, SortBy, boolean, Visitor)} method.
	 * 
	 * @param <T>
	 */
	public interface Visitor<T extends Entity> {
		/**
		 * Visit the given entity.
		 * 
		 * @param entity
		 *            The entity to visit
		 * @throws IOException
		 */
		void visit(T entity, ProgressMonitor progressMonitor) throws InvocationTargetException;
	}

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

	/**
	 * <p>
	 * Let the given <code>visitor</code> visit all entities that matches the given <code>query</code> in the order
	 * given by <code>orderBy</code>. If a <code>progressMonitor</code> is provided, then it is initialized to the total
	 * number of entries and it's expected that the visitor consumes one work unit on each visit. The visitor may also
	 * use the monitor to cancel the iteration.
	 * </p>
	 * <p>
	 * If the <code>progressMonitor</code> parameter is <code>null</code> then an instance of
	 * {@link NullProgressMonitor} will be created. Hence, the visitor can rely on that the monitor will never be
	 * <code>null</code> and that it can always be used for cancellation
	 * </p>
	 * 
	 * @param query
	 *            The query or <code>null</code> for all elements.
	 * @param sortBy
	 *            The sort order or <code>null</code> for default order.
	 * @param includeDeleted
	 *            Set to <code>true</code> to include deleted entries.
	 * @param visitor
	 *            The visitor that will visit the matching entries.
	 * @param progressMonitor
	 *            Monitor that can be used for canceling the iteration and to report progress
	 * @throws IOException
	 */
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

	/**
	 * Retrieve the entity that corresponds to the given <code>id</code>.
	 * 
	 * @param id
	 *            The id of the wanted entity
	 * @return The entity that matches the <code>id</code> or <code>null</code> if no such entry could be found.
	 * 
	 * @throws IOException
	 */
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

	/**
	 * Returns a paginated result that corresponds to the given <code>query</code>, <code>orderBy</code>, and
	 * <code>paginationInfo</code>. New pagination info for the next and previous page can be obtained from
	 * the returned result.
	 * 
	 * @param query
	 *            The query or <code>null</code> for all elements.
	 * @param sortBy
	 *            The sort order or <code>null</code> for default order.
	 * @param pagination
	 *            The pagination info from a previous search or <code>null</code> for the first one.
	 * @param includeDeleted
	 *            Set to <code>true</code> to include deleted entries.
	 * @return
	 * @throws IOException
	 */
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
