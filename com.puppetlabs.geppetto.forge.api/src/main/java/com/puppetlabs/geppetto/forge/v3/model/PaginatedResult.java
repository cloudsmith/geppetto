package com.puppetlabs.geppetto.forge.v3.model;

import java.util.Collections;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.puppetlabs.geppetto.forge.model.Entity;
import com.puppetlabs.geppetto.forge.v3.PaginationInfo;

public class PaginatedResult<T extends Entity> extends Entity {
	@Expose
	private Pagination pagination;

	@Expose
	private List<T> results;

	/**
	 * Returns the pagination info needed to get the next page
	 * 
	 * @return Pagination info to get the next page or <code>null</code> if at the last page
	 */
	public PaginationInfo getNext() {
		int limit = pagination.getLimit();
		int last = pagination.getOffset() + limit;
		int left = pagination.getTotal() - last;
		if(left <= 0)
			return null;
		if(limit > left)
			limit = left;

		return new PaginationInfo(last, limit);
	}

	/**
	 * @return the pagination
	 */
	public Pagination getPagination() {
		return pagination;
	}

	/**
	 * Returns the pagination info needed to get the previous page
	 * 
	 * @return Pagination info to get the previous page or <code>null</code> if at the first page
	 */
	public PaginationInfo getPrevious() {
		int offset = pagination.getOffset();
		if(offset <= 0)
			return null;
		int limit = pagination.getLimit();
		if(limit > offset)
			limit = offset;
		offset -= limit;
		return new PaginationInfo(offset - limit, limit);
	}

	/**
	 * @return list of corresponding resource type for this page
	 */
	public List<T> getResults() {
		return results == null
				? Collections.<T> emptyList()
				: Collections.unmodifiableList(results);
	}

	/**
	 * @return
	 */
	public int getTotal() {
		return pagination.getTotal();
	}

	/**
	 * @param pagination
	 *            the pagination to set
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(List<T> results) {
		this.results = results;
	}
}
