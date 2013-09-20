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
package com.puppetlabs.geppetto.puppetdb;

import static com.puppetlabs.geppetto.injectable.CommonModuleProvider.getCommonModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import com.puppetlabs.geppetto.puppetdb.impl.DefaultModule;

public class PuppetDBManagerFactory {
	/**
	 * Returns a Module that contains all default bindings
	 * 
	 * @return The default bindings module
	 */
	public static Module getDefaultBindings() {
		// We override the default module with the common module to get the correct
		// HttpClient injection
		return Modules.override(new DefaultModule()).with(getCommonModule());
	}

	public static Injector newInjector(Module... overrides) {
		Module module = getDefaultBindings();
		if(overrides.length > 0)
			module = Modules.override(module).with(overrides);
		return Guice.createInjector(module);
	}
}
