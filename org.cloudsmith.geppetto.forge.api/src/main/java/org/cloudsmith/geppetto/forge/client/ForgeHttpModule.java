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

import static org.cloudsmith.geppetto.forge.model.Constants.API_OAUTH_URL_NAME;
import static org.cloudsmith.geppetto.forge.model.Constants.API_V1_URL_NAME;
import static org.cloudsmith.geppetto.forge.model.Constants.API_V2_URL_NAME;

import org.cloudsmith.geppetto.forge.v2.MetadataRepository;
import org.cloudsmith.geppetto.forge.v2.repository.MetadataRepositoryImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

/**
 * This is the default HTTP Guice module
 */
public abstract class ForgeHttpModule extends AbstractModule {

	public static String appendURLSegment(String url, String segment) {
		if(url == null)
			throw new IllegalArgumentException("ForgeAPI preference for base URL has not been set");

		// We assume that 'segment' always starts with a slash
		StringBuilder bld = new StringBuilder(url);
		if(bld.charAt(bld.length() - 1) == '/')
			bld.setLength(bld.length() - 1);
		bld.append(segment);
		return bld.toString();
	}

	@Override
	protected void configure() {
		bind(ForgeClient.class).to(ForgeHttpClient.class);
		bind(MetadataRepository.class).to(MetadataRepositoryImpl.class);
	}

	protected abstract String getBaseURL();

	@Provides
	@Named(API_OAUTH_URL_NAME)
	public String provideOAuthURL() {
		return appendURLSegment(getBaseURL(), "/oauth/token");
	}

	@Provides
	@Named(API_V1_URL_NAME)
	public String provideV1URL() {
		return appendURLSegment(getBaseURL(), "/v1/");
	}

	@Provides
	@Named(API_V2_URL_NAME)
	public String provideV2URL() {
		return appendURLSegment(getBaseURL(), "/v2/");
	}
}
