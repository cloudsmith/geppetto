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

import org.apache.http.client.HttpClient;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.puppetlabs.geppetto.common.util.BundleAccess;

public class EclipseCommonModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(BundleAccess.class).to(EclipseBundleAccess.class).in(Scopes.SINGLETON);
		bind(HttpClient.class).toProvider(EclipseHttpClientProvider.class);
	}
}
