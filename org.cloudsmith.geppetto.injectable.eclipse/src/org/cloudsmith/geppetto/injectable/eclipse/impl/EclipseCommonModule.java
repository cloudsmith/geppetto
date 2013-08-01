package org.cloudsmith.geppetto.injectable.eclipse.impl;

import org.apache.http.client.HttpClient;
import org.cloudsmith.geppetto.common.util.BundleAccess;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class EclipseCommonModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(BundleAccess.class).to(EclipseBundleAccess.class).in(Scopes.SINGLETON);
		bind(HttpClient.class).toProvider(EclipseHttpClientProvider.class);
	}

}
