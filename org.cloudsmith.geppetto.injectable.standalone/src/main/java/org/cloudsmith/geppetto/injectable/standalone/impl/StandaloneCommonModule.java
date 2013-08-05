/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.injectable.standalone.impl;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.cloudsmith.geppetto.common.util.BundleAccess;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.ProvisionException;

public class StandaloneCommonModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(BundleAccess.class).to(NoBundleAccess.class);
		bind(HttpClient.class).toProvider(new StandaloneHttpClientProvider());
	}

	@Provides
	public SSLSocketFactory provideSSLSocketFactory() {
		try {
			return new SSLSocketFactory(new TrustSelfSignedStrategy());
		}
		catch(RuntimeException e) {
			throw e;
		}
		catch(Exception e) {
			throw new ProvisionException("Unable to create SSLSocketFactory", e);
		}
	}
}
