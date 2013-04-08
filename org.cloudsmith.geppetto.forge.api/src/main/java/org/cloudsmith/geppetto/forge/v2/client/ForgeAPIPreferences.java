/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.forge.v2.client;

/**
 * Preferences used by the ForgeAPI client
 */
public interface ForgeAPIPreferences {
	/**
	 * @return The base URL for all API calls
	 */
	String getBaseURL();

	/**
	 * @return The login to use when obtaining the OAuth access_token. This will only be used when no access_token has been provided.
	 */
	String getLogin();

	/**
	 * @return The OAuth access_token or <code>null</code> if it's not yet known. Returning <code>null</code> will initiate a login sequence.
	 */
	String getOAuthAccessToken();

	/**
	 * @return The OAuth client_id
	 */
	String getOAuthClientId();

	/**
	 * @return The OAuth client_secret
	 */
	String getOAuthClientSecret();

	/**
	 * @return A space separated list of OAuth scopes
	 */
	String getOAuthScopes();

	/**
	 * @return The URL used when obtaining the OAuth access_token for
	 *         a combination of client_id, client_secret, username, password, and scope.
	 */
	String getOAuthURL();

	/**
	 * @return The password to use when obtaining the OAuth access_token. This will only be used when no access_token has been provided.
	 */
	String getPassword();

	/**
	 * Set the OAuth AccessToken
	 */
	void setOAuthAccessToken(String token);

	/**
	 * Set the OAuth Scopes
	 * 
	 * @param scopes
	 *            A space separated list of scopes
	 */
	void setOAuthScopes(String scopes);
}
