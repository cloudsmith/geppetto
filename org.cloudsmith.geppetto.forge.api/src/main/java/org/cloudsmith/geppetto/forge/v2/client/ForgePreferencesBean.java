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

import java.io.Serializable;

public class ForgePreferencesBean implements ForgePreferences, Serializable {
	private static final long serialVersionUID = -4834246942497662777L;

	private String baseURL;

	private String login;

	private String oauthAccessToken;

	private String oauthClientId;

	private String oauthClientSecret;

	private String oauthURL;

	private String oauthScopes;

	private String password;

	public String getBaseURL() {
		return baseURL;
	}

	public String getForgePassword() {
		return password;
	}

	public String getLogin() {
		return login;
	}

	public String getOAuthAccessToken() {
		return oauthAccessToken;
	}

	public String getOAuthClientId() {
		return oauthClientId;
	}

	public String getOAuthClientSecret() {
		return oauthClientSecret;
	}

	public String getOAuthScopes() {
		return oauthScopes;
	}

	@Override
	public String getOAuthURL() {
		return oauthURL;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setOAuthAccessToken(String oauthAccessToken) {
		this.oauthAccessToken = oauthAccessToken;
	}

	public void setOAuthClientId(String oauthClientId) {
		this.oauthClientId = oauthClientId;
	}

	public void setOAuthClientSecret(String oauthClientSecret) {
		this.oauthClientSecret = oauthClientSecret;
	}

	public void setOAuthScopes(String oauthScopes) {
		this.oauthScopes = oauthScopes;
	}

	public void setOAuthURL(String oauthURL) {
		this.oauthURL = oauthURL;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
