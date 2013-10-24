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

import com.puppetlabs.geppetto.forge.model.Entity;

public class SortBy<T extends Entity> implements Parameters {
	private String columnName;

	public SortBy(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public void append(Map<String, String> collector) {
		collector.put("sort_by", columnName);
	}
}