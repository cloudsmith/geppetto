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

import java.util.Map;

public class PaginationInfo implements Parameters {
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