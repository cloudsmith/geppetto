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
package org.cloudsmith.geppetto.forge.api.tests;

import org.cloudsmith.geppetto.forge.v2.client.ForgePreferences;

public abstract class ForgeTestPreferences implements ForgePreferences {
	private String accessToken;

	private String scopes;

	@Override
	public String getBaseURL() {
		return "http://localhost:4567/v2/";
	}

	@Override
	public String getOAuthAccessToken() {
		return accessToken;
	}

	@Override
	public String getOAuthClientId() {
		return ForgeTests.getPuppetForgeClientIdentity()[0];
	}

	@Override
	public String getOAuthClientSecret() {
		return ForgeTests.getPuppetForgeClientIdentity()[1];
	}

	@Override
	public String getOAuthScopes() {
		return scopes;
	}

	@Override
	public String getOAuthURL() {
		return "http://localhost:4567/oauth/token";
	}

	@Override
	public void setOAuthAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public void setOAuthScopes(String scopes) {
		this.scopes = scopes;
	}
}
