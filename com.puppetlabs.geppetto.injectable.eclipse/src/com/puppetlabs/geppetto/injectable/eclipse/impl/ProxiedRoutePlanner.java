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

import java.net.InetAddress;
import java.net.URI;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.protocol.HttpContext;
import org.eclipse.core.net.proxy.IProxyData;

import com.puppetlabs.geppetto.injectable.eclipse.Activator;

public class ProxiedRoutePlanner implements HttpRoutePlanner {
	private final SchemeRegistry schemeRegistry;

	public ProxiedRoutePlanner(SchemeRegistry schemeRegistry) {
		this.schemeRegistry = schemeRegistry;
	}

	@Override
	public HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {
		if(request == null) {
			throw new IllegalStateException("Request must not be null.");
		}

		// If we have a forced route, we can do without a target.
		HttpRoute route = ConnRouteParams.getForcedRoute(request.getParams());
		if(route != null)
			return route;

		// If we get here, there is no forced route.
		// So we need a target to compute a route.

		if(target == null) {
			throw new IllegalStateException("Target host must not be null.");
		}

		final InetAddress local = ConnRouteParams.getLocalAddress(request.getParams());
		final HttpHost proxy = ConnRouteParams.getDefaultProxy(request.getParams());

		final Scheme schm;
		try {
			schm = schemeRegistry.getScheme(target.getSchemeName());
		}
		catch(IllegalStateException ex) {
			throw new HttpException(ex.getMessage());
		}
		// as it is typically used for TLS/SSL, we assume that
		// a layered scheme implies a secure connection
		final boolean secure = schm.isLayered();

		if(proxy != null)
			return new HttpRoute(target, local, proxy, secure);

		IProxyData[] select = Activator.getInstance().getProxyService().select(URI.create(target.toURI()));
		for(IProxyData proxyData : select)
			if(proxyData.getType().equals(IProxyData.HTTP_PROXY_TYPE)) {
				HttpHost proxyHost = new HttpHost(proxyData.getHost(), proxyData.getPort());
				return new HttpRoute(target, null, proxyHost, secure);
			}

		return new HttpRoute(target, local, secure);
	}
}
