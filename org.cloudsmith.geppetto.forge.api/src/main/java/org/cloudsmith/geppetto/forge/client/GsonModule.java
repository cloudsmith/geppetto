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
package org.cloudsmith.geppetto.forge.client;


import com.google.gson.Gson;
import com.google.inject.AbstractModule;

/**
 */
public class GsonModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(Gson.class).toProvider(GsonProvider.class);
	}
}
