/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs - initial API and implementation
 */
package com.puppetlabs.geppetto.injectable.eclipse.impl;

import javax.inject.Named;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.eclipse.core.net.proxy.IProxyData;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.puppetlabs.geppetto.common.annotations.Nullable;
import com.puppetlabs.geppetto.injectable.eclipse.Activator;

public class EclipseHttpClientProvider implements Provider<HttpClient> {

	@Inject(optional = true)
	@Nullable
	@Named(CoreConnectionPNames.CONNECTION_TIMEOUT)
	private Integer connectonTimeout;

	@Inject(optional = true)
	@Nullable
	@Named(CoreConnectionPNames.SO_TIMEOUT)
	private Integer soTimeout;

	@Inject(optional = true)
	@Nullable
	private SSLSocketFactory sslSocketFactory;

	@Override
	public HttpClient get() {
		HttpParams params = new BasicHttpParams();
		if(connectonTimeout != null)
			HttpConnectionParams.setConnectionTimeout(params, connectonTimeout.intValue());
		if(soTimeout != null)
			HttpConnectionParams.setSoTimeout(params, soTimeout.intValue());

		DefaultHttpClient httpClient = new DefaultHttpClient(params);

		final SchemeRegistry schemeRegistry = httpClient.getConnectionManager().getSchemeRegistry();
		if(sslSocketFactory != null)
			schemeRegistry.register(new Scheme("https", 443, sslSocketFactory));

		httpClient.setRoutePlanner(new ProxiedRoutePlanner(schemeRegistry));
		for(IProxyData proxyData : Activator.getInstance().getProxyService().getProxyData()) {
			String user = proxyData.getUserId();
			String pwd = proxyData.getPassword();
			if(user != null || pwd != null)
				httpClient.getCredentialsProvider().setCredentials(
					new AuthScope(proxyData.getHost(), proxyData.getPort()), new UsernamePasswordCredentials(user, pwd));
		}
		return httpClient;
	}
}
