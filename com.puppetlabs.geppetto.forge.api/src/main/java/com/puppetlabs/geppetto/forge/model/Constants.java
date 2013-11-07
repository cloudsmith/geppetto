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
package com.puppetlabs.geppetto.forge.model;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.puppetlabs.geppetto.forge.v2.model.Module;
import com.puppetlabs.geppetto.forge.v2.model.Release;
import com.puppetlabs.geppetto.forge.v2.model.Tag;
import com.puppetlabs.geppetto.forge.v2.model.User;

/**
 * Constants pertaining to the ForgeAPI v2 API
 */
public interface Constants {
	/**
	 * The default base URL
	 */
	String FORGE_SERVICE_BASE_URL = "http://forgeapi.puppetlabs.com";

	/**
	 * Binding name for base URI
	 */
	String BASE_URL_NAME = "forge.service.baseURI"; //$NON-NLS-1$

	/**
	 * Binding name for OAuth clientId
	 */
	String OAUTH_CLIENT_ID = "forge.oauth.client.id";

	/**
	 * Binding name for OAuth clientSecret
	 */
	String OAUTH_CLIENT_SECRET = "forge.oauth.client.secret";

	/**
	 * Binding name for URL used by the Forge v1 API
	 */
	String API_V1_URL_NAME = "forge.api.v1.url";

	/**
	 * Binding name for URL used by the Forge v2 API
	 */
	String API_V2_URL_NAME = "forge.api.v2.url";

	/**
	 * Binding name for URL used by the Forge v3 API
	 */
	String API_V3_URL_NAME = "forge.api.v3.url";

	/**
	 * Binding name for URL used by the Forge OAuth API
	 */
	String API_OAUTH_URL_NAME = "forge.api.oauth.url";

	/**
	 * URI path segment used for commands specific to files
	 */
	String COMMAND_GROUP_FILES = "files";

	/**
	 * URI path segment used for commands specific to user emails
	 */
	String COMMAND_GROUP_EMAIL = "email"; //$NON-NLS-1$

	/**
	 * URI path segment used for commands specific to user passwords
	 */
	String COMMAND_GROUP_PASSWORD = "password"; //$NON-NLS-1$

	/**
	 * URI path segment used for commands specific to users
	 */
	String COMMAND_GROUP_USERS = "users"; //$NON-NLS-1$

	/**
	 * URI path segment used for commands specific to releases
	 */
	String COMMAND_GROUP_RELEASES = "releases"; //$NON-NLS-1$

	/**
	 * URI path segment used for commands specific to modules
	 */
	String COMMAND_GROUP_MODULES = "modules"; //$NON-NLS-1$

	/**
	 * URI path segment used for commands specific to tags
	 */
	String COMMAND_GROUP_TAGS = "tags"; //$NON-NLS-1$

	/**
	 * The content type of posts and responses.
	 */
	String CONTENT_TYPE_JSON = "application/json"; //$NON-NLS-1$

	/**
	 * Injection name for credentials
	 */
	String CREDENTIALS_NAME = "credentials"; //$NON-NLS-1$

	/**
	 * The string used when presenting us to the server
	 */
	String USER_AGENT = "Geppetto/1.0.0"; //$NON-NLS-1$

	/**
	 * The encoding used by the API
	 */
	Charset UTF_8 = Charset.forName("UTF-8"); //$NON-NLS-1$

	// @fmtOff
	/**
	 * A type representing a {@link List} of {@link Module} instances
	 */
	Type LIST_MODULE = new TypeToken<List<Module>>() {}.getType();

	/**
	 * A type representing a {@link List} of {@link Release} instances
	 */
	Type LIST_RELEASE = new TypeToken<List<Release>>() {}.getType();

	/**
	 * A type representing a {@link List} of {@link Tag} instances
	 */
	Type LIST_TAG = new TypeToken<List<Tag>>() {}.getType();

	/**
	 * A type representing a {@link List} of {@link User} instances
	 */
	Type LIST_USER = new TypeToken<List<User>>() {}.getType();
	// @fmtOff
}
