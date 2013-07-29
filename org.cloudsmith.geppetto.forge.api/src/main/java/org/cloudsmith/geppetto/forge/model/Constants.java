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
package org.cloudsmith.geppetto.forge.model;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;

import org.cloudsmith.geppetto.forge.v1.model.ModuleInfo;
import org.cloudsmith.geppetto.forge.v2.model.Module;
import org.cloudsmith.geppetto.forge.v2.model.Release;
import org.cloudsmith.geppetto.forge.v2.model.Tag;
import org.cloudsmith.geppetto.forge.v2.model.User;

import com.google.gson.reflect.TypeToken;

/**
 * Constants pertaining to the ForgeAPI v2 API
 */
public interface Constants {
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
	 * Injection name for base URI
	 */
	String BASE_URI_NAME = "baseURI"; //$NON-NLS-1$

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
	 * A type representing a {@link List} of {@link Release} instances
	 */
	Type LIST_MODULE_INFO = new TypeToken<List<ModuleInfo>>() {}.getType();

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
