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

import org.cloudsmith.geppetto.forge.v2.MetadataRepository;
import org.cloudsmith.geppetto.forge.v2.repository.MetadataRepositoryImpl;

/**
 * This is the default HTTP Guice module
 */
public class ForgeHttpModule extends GsonModule {

	private final ForgeAPIPreferences preferences;

	public ForgeHttpModule(ForgeAPIPreferences preferences) {
		this.preferences = preferences;
	}

	@Override
	protected void configure() {
		super.configure();
		bind(ForgeClient.class).to(ForgeHttpClient.class);
		bind(ForgeAPIPreferences.class).toInstance(preferences);
		bind(MetadataRepository.class).to(MetadataRepositoryImpl.class);
		bind(Authenticator.class).to(HttpAuthenticator.class);
	}

	protected ForgeAPIPreferences getPreferences() {
		return preferences;
	}
}
