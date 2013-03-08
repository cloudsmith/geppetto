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

import java.io.IOException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.inject.Inject;

public class HttpAuthenticator implements Authenticator {

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

	private final Gson gson;

	private final ForgeAPIPreferences forgePreferences;

	@Inject
	public HttpAuthenticator(Gson gson, ForgeAPIPreferences forgePreferences) {
		this.gson = gson;
		this.forgePreferences = forgePreferences;
	}

	@Override
	public AuthResponse authenticate(String user, String password) throws IOException {
		HttpPost request = new HttpPost(forgePreferences.getOAuthURL());

		MultipartEntity entity = new MultipartEntity();
		entity.addPart("grant_type", new StringBody("password"));
		entity.addPart("client_id", new StringBody(forgePreferences.getOAuthClientId()));
		entity.addPart("client_secret", new StringBody(forgePreferences.getOAuthClientSecret()));
		entity.addPart("username", new StringBody(user));
		entity.addPart("password", new StringBody(password));
		request.setEntity(entity);

		HttpClient httpClient = ForgeHttpClient.createHttpClient();
		return httpClient.execute(request, new JSonResponseHandler<OAuthResponse>(gson, OAuthResponse.class));
	}
}
