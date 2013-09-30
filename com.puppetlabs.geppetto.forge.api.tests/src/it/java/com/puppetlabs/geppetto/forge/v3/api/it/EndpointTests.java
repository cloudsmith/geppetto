/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.forge.v3.api.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;

import com.puppetlabs.geppetto.forge.api.it.ForgeAPITestBase;
import com.puppetlabs.geppetto.forge.api.it.ForgeAPITestBase.TestModule;
import com.puppetlabs.geppetto.forge.api.tests.GuiceJUnitRunner;
import com.puppetlabs.geppetto.forge.api.tests.GuiceJUnitRunner.TestModules;
import com.puppetlabs.geppetto.forge.model.Entity;
import com.puppetlabs.geppetto.forge.v3.ForgeService;
import com.puppetlabs.geppetto.forge.v3.ForgeService.PaginationInfo;
import com.puppetlabs.geppetto.forge.v3.ForgeService.Compare;
import com.puppetlabs.geppetto.forge.v3.model.PaginatedResult;

@RunWith(GuiceJUnitRunner.class)
@TestModules(TestModule.class)
public abstract class EndpointTests<T extends Entity, I> extends ForgeAPITestBase {
	protected abstract ForgeService<T, I> getService();

	protected void testGet(I id) throws IOException {
		T instance = getService().get(id);
		assertNotNull("Null instance", instance);
	}

	protected void testList(Compare<T> query) throws IOException {
		PaginatedResult<T> result = getService().list(query, null, null, false);
		assertNotNull("Null result", result);
		PaginationInfo nxt = result.getNext();
		assertNotNull("Null pagination for next page", nxt);
		List<T> entries = result.getResults();
		assertNotNull("Null result list", entries);
		assertFalse("Empty list", entries.isEmpty());

		int total = result.getTotal();
		assertTrue("Too few modules", total > 60);
		List<T> allEntries = new ArrayList<T>(total);
		allEntries.addAll(entries);
		while(nxt != null) {
			result = getService().list(query, null, nxt, false);
			allEntries.addAll(result.getResults());
			nxt = result.getNext();
		}
		assertEquals("Not all entries read", total, allEntries.size());
	}
}
