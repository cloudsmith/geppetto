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
package org.cloudsmith.geppetto.forge.v2.service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.cloudsmith.geppetto.forge.v2.client.Constants;
import org.cloudsmith.geppetto.forge.v2.model.ConfirmationToken;
import org.cloudsmith.geppetto.forge.v2.model.Module;
import org.cloudsmith.geppetto.forge.v2.model.PasswordToken;
import org.cloudsmith.geppetto.forge.v2.model.Release;
import org.cloudsmith.geppetto.forge.v2.model.User;

/**
 * A CRUD service for {@link User} objects
 */
public class UserService extends ForgeService {
	private static String getUserPath(String name) {
		return Constants.COMMAND_GROUP_USERS + '/' + name;
	}

	/**
	 * Confirms the user using the given <code>confirmationToken</code>. This method will fail
	 * unless the OAuth token is associated with the ForgeAPI Web application and the logged in
	 * user is the owner of the given email
	 * 
	 * @param confirmationToken
	 *            The token to use when confirming
	 * @throws IOException
	 */
	public void confirmEmail(ConfirmationToken confirmationToken) throws IOException {
		getClient(true).postJSON(Constants.COMMAND_GROUP_EMAIL + "/confirm", confirmationToken, null);
	}

	/**
	 * Resets the password of the user using the given <code>passwordToken</code>. This method will fail
	 * unless the OAuth token is associated with the ForgeAPI Web application and the logged in
	 * user is the owner of the <code>passwordToken</code>
	 * 
	 * @param passwordToken
	 *            The token to use when confirming
	 * @throws IOException
	 */
	public void confirmPasswordReset(PasswordToken passwordToken, String newPassword) throws IOException {
		Map<String, String> postData = toQueryMap(passwordToken);
		postData.put("password", newPassword);
		getClient(true).postJSON(Constants.COMMAND_GROUP_PASSWORD + "/reset", postData, null);
	}

	/**
	 * Creates a new user. The given <code>name</code> must be unique since it is used to
	 * identify the user.
	 * 
	 * @param username
	 * @param email
	 * @param displayName
	 * @param password
	 * @return The created user
	 * @throws IOException
	 *             if the user could not be created
	 */
	public User create(String username, String email, String displayName, String password) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("email", email);
		params.put("display_name", displayName);
		params.put("password", password);
		return getClient(false).postJSON(Constants.COMMAND_GROUP_USERS, params, User.class);
	}

	/**
	 * Delete a user
	 * 
	 * @param name
	 *            The name of the user to delete
	 * @throws IOException
	 *             if the user could not be deleted
	 */
	public void delete(String name) throws IOException {
		getClient(true).delete(getUserPath(name));
	}

	/**
	 * 
	 * @param name
	 *            The name of the user
	 * @return Details for a particular user
	 * @throws IOException
	 */
	public User get(String name) throws IOException {
		return getClient(false).get(getUserPath(name), null, User.class);
	}

	/**
	 * Retrieves confirmation token for a user, using an email address. This method will fail
	 * unless the OAuth token is associated with the ForgeAPI Web application and the logged in
	 * user is the owner of the given email
	 * 
	 * @param email
	 *            The users email address
	 * @return The confirmation token
	 * @throws IOException
	 */
	public ConfirmationToken getConfirmationToken(String email) throws IOException {
		return getClient(true).get(
			Constants.COMMAND_GROUP_EMAIL + "/confirm", Collections.singletonMap("email", email),
			ConfirmationToken.class);
	}

	/**
	 * @param name
	 *            The name of the user
	 * @param listPreferences
	 *            Pagination preferences or <code>null</code> to get all in no particular order
	 * @return Modules for a particular user
	 * @throws IOException
	 */
	public List<Module> getModules(String name, ListPreferences listPreferences) throws IOException {
		List<Module> modules = null;
		try {
			modules = getClient(false).get(
				getUserPath(name) + "/modules", toQueryMap(listPreferences), Constants.LIST_MODULE);
		}
		catch(HttpResponseException e) {
			if(e.getStatusCode() != HttpStatus.SC_NOT_FOUND)
				throw e;
		}
		if(modules == null)
			modules = Collections.emptyList();
		return modules;
	}

	/**
	 * Retrieves password reset token for a user, using an email address. This method will fail
	 * unless the OAuth token is associated with the ForgeAPI Web application and the logged in
	 * user is the owner of the given email
	 * 
	 * @param email
	 *            The users email address
	 * @return The password reset token
	 * @throws IOException
	 */
	public PasswordToken getPasswordToken(String email) throws IOException {
		return getClient(true).get(
			Constants.COMMAND_GROUP_PASSWORD + "/reset", Collections.singletonMap("email", email), PasswordToken.class);
	}

	/**
	 * @param name
	 *            The name of the user
	 * @param listPreferences
	 *            Pagination preferences or <code>null</code> to get all in no particular order
	 * @return Releases for a particular user
	 * @throws IOException
	 */
	public List<Release> getReleases(String name, ListPreferences listPreferences) throws IOException {
		List<Release> releases = null;
		try {
			releases = getClient(false).get(
				getUserPath(name) + "/releases", toQueryMap(listPreferences), Constants.LIST_RELEASE);
		}
		catch(HttpResponseException e) {
			if(e.getStatusCode() != HttpStatus.SC_NOT_FOUND)
				throw e;
		}
		if(releases == null)
			releases = Collections.emptyList();
		return releases;
	}

	/**
	 * @param listPreferences
	 * @return All users
	 * @throws IOException
	 */
	public List<User> list(ListPreferences listPreferences) throws IOException {
		List<User> users = null;
		try {
			users = getClient(false).get(
				Constants.COMMAND_GROUP_USERS, toQueryMap(listPreferences), Constants.LIST_USER);
		}
		catch(HttpResponseException e) {
			if(e.getStatusCode() != HttpStatus.SC_NOT_FOUND)
				throw e;
		}
		if(users == null)
			users = Collections.emptyList();
		return users;
	}

	/**
	 * Updates the user identified with <code>currentName</code>.
	 * 
	 * @param currentName
	 *            The current name of the user
	 * @param newName
	 *            The new name of the user
	 * @param email
	 *            The new email for the user
	 * @param displayName
	 *            The new display name for the user
	 * @param password
	 *            The users new password
	 * @return The updated user
	 * @throws IOException
	 */
	public User update(String currentName, User user, String password) throws IOException {
		Map<String, String> params = toQueryMap(user);
		params.put("password", password);
		return getClient(true).patch(getUserPath(currentName), params, User.class);
	}
}
