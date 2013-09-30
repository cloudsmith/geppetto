package com.puppetlabs.geppetto.forge.v3.model;

import com.google.gson.annotations.Expose;
import com.puppetlabs.geppetto.forge.model.Entity;

public class Pagination extends Entity {
	@Expose
	private Integer limit;

	@Expose
	private Integer offset;

	@Expose
	private String first;

	@Expose
	private String previous;

	@Expose
	private String next;

	@Expose
	private String current;

	@Expose
	private Integer total;

	/**
	 * @return Link to the current page
	 */
	public String getCurrent() {
		return current;
	}

	/**
	 * @return Link to the first page
	 */
	public String getFirst() {
		return first;
	}

	/**
	 * @return Results per page
	 */
	public int getLimit() {
		return limit == null
				? 0
				: limit.intValue();
	}

	/**
	 * @return Link to the next page
	 */
	public String getNext() {
		return next;
	}

	/**
	 * @return Start of page
	 */
	public int getOffset() {
		return offset == null
				? 0
				: offset.intValue();
	}

	/**
	 * @return Link to the previous page
	 */
	public String getPrevious() {
		return previous;
	}

	/**
	 * @return Total number of results
	 */
	public int getTotal() {
		return total == null
				? 0
				: total.intValue();
	}

	/**
	 * @param current
	 *            the current to set
	 */
	public void setCurrent(String current) {
		this.current = current;
	}

	/**
	 * @param first
	 *            the first to set
	 */
	public void setFirst(String first) {
		this.first = first;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	/**
	 * @param next
	 *            the next to set
	 */
	public void setNext(String next) {
		this.next = next;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	/**
	 * @param previous
	 *            the previous to set
	 */
	public void setPrevious(String previous) {
		this.previous = previous;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
}
