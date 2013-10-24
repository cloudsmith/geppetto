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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.puppetlabs.geppetto.forge.model.Entity;
import com.puppetlabs.geppetto.forge.v3.model.PaginatedResult;

/**
 * Basic service for v3 endpoints. Entries can be retrived individually, as paginated results, or using an entry
 * visitor.
 * 
 * @param <T>
 * @param <I>
 */
public interface ForgeService<T extends Entity, I> {
	public static class And<T extends Entity> extends ArrayList<Query<T>> implements Query<T> {
		private static final long serialVersionUID = 1L;

		public And() {
		}

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

	public interface Query<T extends Entity> extends Parameters {
	}

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
	void accept(Query<T> query, SortBy<T> sortBy, boolean includeDeleted, Visitor<T> visitor,
			ProgressMonitor progressMonitor) throws IOException, InvocationTargetException;

	/**
	 * Retrieve the entity that corresponds to the given <code>id</code>.
	 * 
	 * @param id
	 *            The id of the wanted entity
	 * @return The entity that matches the <code>id</code> or <code>null</code> if no such entry could be found.
	 * 
	 * @throws IOException
	 */
	T get(I id) throws IOException;

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
	PaginatedResult<T> list(Query<T> query, SortBy<T> sortBy, PaginationInfo pagination, boolean includeDeleted)
			throws IOException;

}
