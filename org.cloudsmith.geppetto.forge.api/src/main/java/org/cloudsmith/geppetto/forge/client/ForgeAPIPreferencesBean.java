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
package org.cloudsmith.geppetto.forge.client;

import java.io.Serializable;

/**
 * A standard bean implementation that contains all preferences and has setters for them.
 */
public class ForgeAPIPreferencesBean implements ForgeAPIPreferences, Serializable {
	private static final long serialVersionUID = 1L;

	private static String appendURLSegment(String url, String segment) {
		if(url == null)
			throw new IllegalArgumentException("ForgeAPI preference for base URL has not been set");

		// We assume that 'segment' always starts with a slash
		StringBuilder bld = new StringBuilder(url);
		if(bld.charAt(bld.length() - 1) == '/')
			bld.setLength(bld.length() - 1);
		bld.append(segment);
		return bld.toString();
	}

	private String baseURL;

	private String login;

	private String oauthAccessToken;

	private String oauthClientId;

	private String oauthClientSecret;

	private String oauthScopes;

	private String password;

	public ForgeAPIPreferencesBean() {
	}

	public ForgeAPIPreferencesBean(ForgeAPIPreferences copy) {
		baseURL = copy.getBaseURL();
		login = copy.getLogin();
		oauthAccessToken = copy.getOAuthAccessToken();
		oauthClientId = copy.getOAuthClientId();
		oauthClientSecret = copy.getOAuthClientSecret();
		oauthScopes = copy.getOAuthScopes();
		password = copy.getPassword();
	}

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
		return appendURLSegment(baseURL, "/oauth/token");
	}

	@Override
	public String getPassword() {
		return password;
	}

	public String getURL_v1() {
		return appendURLSegment(baseURL, "/v1/");
	}

	public String getURL_v2() {
		return appendURLSegment(baseURL, "/v2/");
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

	public void setPassword(String password) {
		this.password = password;
	}
}
