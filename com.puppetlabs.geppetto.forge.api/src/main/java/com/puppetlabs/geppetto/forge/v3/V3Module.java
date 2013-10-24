/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 * 
 */
package com.puppetlabs.geppetto.forge.v3;

import com.google.inject.AbstractModule;
import com.puppetlabs.geppetto.forge.v3.impl.DefaultFiles;
import com.puppetlabs.geppetto.forge.v3.impl.DefaultModules;
import com.puppetlabs.geppetto.forge.v3.impl.DefaultReleases;
import com.puppetlabs.geppetto.forge.v3.impl.DefaultUsers;

public class V3Module extends AbstractModule {
	@Override
	protected void configure() {
		bind(Modules.class).to(DefaultModules.class);
		bind(Files.class).to(DefaultFiles.class);
		bind(Releases.class).to(DefaultReleases.class);
		bind(Users.class).to(DefaultUsers.class);
	}
}
