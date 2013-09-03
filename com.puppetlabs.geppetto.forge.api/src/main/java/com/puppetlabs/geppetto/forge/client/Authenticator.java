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
package com.puppetlabs.geppetto.forge.client;

import java.io.IOException;

import org.apache.http.client.HttpClient;

/**
 * The authenticator is responsible for authenticating the user.
 * In response, it expects a string with valid scopes and a token that can be used for the
 * reminder of the session.
 */
public interface Authenticator {
	/**
	 * An interface that captures the authentication response.
	 */
	interface AuthResponse {
		String getScopes();

		String getToken();
	}

	/**
	 * Authenticate the user.
	 * 
	 * @param httpClient
	 *            The Client that executes the HTTP authentication request
	 * @return An authentication response
	 * @throws IOException
	 *             when the authentication did not succeed
	 */
	AuthResponse authenticate(HttpClient httpClient) throws IOException;
}
