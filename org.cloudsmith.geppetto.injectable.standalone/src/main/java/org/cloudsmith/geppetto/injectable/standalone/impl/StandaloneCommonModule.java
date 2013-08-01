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
import org.cloudsmith.geppetto.common.util.BundleAccess;

import com.google.inject.AbstractModule;

public class StandaloneCommonModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(BundleAccess.class).to(NoBundleAccess.class);
		bind(HttpClient.class).toProvider(new StandaloneHttpClientProvider());
	}
}
