/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.forge.impl;

import com.google.inject.AbstractModule;
import com.puppetlabs.geppetto.forge.Cache;
import com.puppetlabs.geppetto.forge.ForgeService;

public class ForgeServiceModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(ForgeService.class).to(ForgeServiceImpl.class);
		bind(Cache.class).to(CacheImpl.class);
	}
}
