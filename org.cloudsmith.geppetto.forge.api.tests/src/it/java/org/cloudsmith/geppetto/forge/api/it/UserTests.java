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
package org.cloudsmith.geppetto.forge.api.it;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.cloudsmith.geppetto.forge.v2.model.User;
import org.cloudsmith.geppetto.forge.v2.service.ListPreferences;
import org.cloudsmith.geppetto.forge.v2.service.UserService;
import org.junit.Test;

/**
 * @author thhal
 * 
 */
public class UserTests extends ForgeAPITestBase {

	@Test
	public void testListUsers() throws IOException {
		UserService service = getTestUserForge().createUserService();
		List<User> Users = service.list(null);
		assertNotNull("Null User list", Users);
		assertFalse("Empty User list", Users.isEmpty());
	}

	@Test
	public void testListUsersSorted() throws IOException {
		UserService service = getTestUserForge().createUserService();
		ListPreferences listPrefs = new ListPreferences();
		listPrefs.setLimit(4);
		listPrefs.setOffset(1);
		listPrefs.setSortBy("username");
		listPrefs.setSortOrder("descending");
		List<User> Users = service.list(listPrefs);
		assertNotNull("Null User list", Users);
		assertFalse("Empty User list", Users.isEmpty());
	}

	@Test
	public void testUserDetail() throws IOException {
		UserService service = getTestUserForge().createUserService();
		User user = service.get(TEST_USER);
		assertNotNull("Null user", user);
	}
}
