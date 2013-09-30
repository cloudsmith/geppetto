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

import com.google.gson.reflect.TypeToken;
import com.puppetlabs.geppetto.forge.v3.model.PaginatedResult;
import com.puppetlabs.geppetto.forge.v3.model.User;

public class Users extends ForgeService<User, String> {

	/**
	 * A type representing a {@link PaginatedResult} of {@link User} instances
	 */
	public static final Type RESULT_TYPE = new TypeToken<PaginatedResult<User>>() {
	}.getType();

	@Override
	void addIdSegment(StringBuilder bld, String id) {
		bld.append(id);
	}

	@Override
	Class<User> getElementResultType() {
		return User.class;
	}

	@Override
	String getEndpointSegment() {
		return "users";
	}

	@Override
	Type getPaginatedResultType() {
		return Users.RESULT_TYPE;
	}
}
