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

import com.google.gson.Gson;
import com.google.inject.AbstractModule;

/**
 * This is the default HTTP Guice module
 */
public abstract class ForgeHttpModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ForgeClient.class).to(ForgeHttpClient.class);
		bind(ForgePreferences.class).toInstance(getForgePreferences());
		bind(Gson.class).toProvider(GsonProvider.class);
		bind(MetadataRepository.class).to(MetadataRepositoryImpl.class);
		bind(Authenticator.class).to(HttpAuthenticator.class);
	}

	protected abstract ForgePreferences getForgePreferences();
}
