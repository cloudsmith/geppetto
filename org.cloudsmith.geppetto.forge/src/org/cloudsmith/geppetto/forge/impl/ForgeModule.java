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
package org.cloudsmith.geppetto.forge.impl;

import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.ERB;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.ForgePreferences;
import org.cloudsmith.geppetto.forge.v2.client.ForgeHttpModule;

public class ForgeModule extends ForgeHttpModule {
	public ForgeModule(ForgePreferences forgePreferences) {
		super(forgePreferences);
	}

	@Override
	protected void configure() {
		super.configure();
		bind(Forge.class).to(ForgeImpl.class);
		bind(Cache.class).to(CacheImpl.class);
		bind(ERB.class).to(ERBImpl.class);
		bind(ForgePreferences.class).toInstance((ForgePreferences) getPreferences());
	}
}
