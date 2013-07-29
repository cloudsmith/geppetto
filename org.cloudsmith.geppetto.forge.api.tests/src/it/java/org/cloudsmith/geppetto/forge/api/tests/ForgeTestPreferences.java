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
package org.cloudsmith.geppetto.forge.api.tests;

import org.cloudsmith.geppetto.forge.client.ForgeAPIPreferences;

public abstract class ForgeTestPreferences implements ForgeAPIPreferences {
	private String accessToken;

	private String scopes;

	@Override
	public String getBaseURL() {
		return "http://localhost:4567/";
	}

	@Override
	public String getOAuthAccessToken() {
		return accessToken;
	}

	@Override
	public String getOAuthClientId() {
		return ForgeAPITestBase.getPuppetForgeClientIdentity()[0];
	}

	@Override
	public String getOAuthClientSecret() {
		return ForgeAPITestBase.getPuppetForgeClientIdentity()[1];
	}

	@Override
	public String getOAuthScopes() {
		return scopes;
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
