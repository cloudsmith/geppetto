package com.puppetlabs.geppetto.forge.v3.model;

import java.net.URI;

import com.google.gson.annotations.Expose;
import com.puppetlabs.geppetto.forge.model.Entity;

public class AbbrevUser extends Entity {
	@Expose
	private URI uri;

	@Expose
	private String username;

	@Expose
	private String gravatar_id;

	/**
	 * @return the gravatar_id of this user
	 */
	public String getGravatarId() {
		return gravatar_id;
	}

	/**
	 * @return the uri at which this users specifics can be found
	 */
	public URI getUri() {
		return uri;
	}

	/**
	 * @return the username of this user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param gravatarId
	 *            the gravatarId to set
	 */
	public void setGravatarId(String gravatarId) {
		this.gravatar_id = gravatarId;
	}

	/**
	 * @param uri
	 *            the uri to set
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
