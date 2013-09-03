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

import javax.inject.Named;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.puppetlabs.geppetto.forge.model.Constants;

/**
 */
public class OAuthModule extends AbstractModule implements Authenticator {
	public static class OAuthResponse implements AuthResponse {
		@Expose
		private String scope;

		@Expose
		private String access_token;

		public String getScopes() {
			return scope;
		}

		public String getToken() {
			return access_token;
		}
	}

	private final String clientId;

	private final String clientSecret;

	private final String username;

	private final String password;

	@Inject
	@Named(Constants.API_OAUTH_URL_NAME)
	private String oauthURL;

	@Inject
	private Gson gson;

	public OAuthModule(String clientId, String clientSecret, String username, String password) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.username = username;
		this.password = password;
	}

	@Override
	public AuthResponse authenticate(HttpClient httpClient) throws IOException {
		HttpPost request = new HttpPost(oauthURL);

		MultipartEntity entity = new MultipartEntity();
		entity.addPart("grant_type", new StringBody("password"));
		entity.addPart("client_id", new StringBody(clientId));
		entity.addPart("client_secret", new StringBody(clientSecret));
		entity.addPart("username", new StringBody(username));
		entity.addPart("password", new StringBody(password));
		request.setEntity(entity);

		return httpClient.execute(request, new JSonResponseHandler<OAuthResponse>(gson, OAuthResponse.class));
	}

	@Override
	protected void configure() {
		bind(Authenticator.class).toInstance(this);
	}

}
