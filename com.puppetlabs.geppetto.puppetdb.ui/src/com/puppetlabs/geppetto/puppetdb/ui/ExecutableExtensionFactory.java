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
package com.puppetlabs.geppetto.puppetdb.ui;

import org.osgi.framework.Bundle;

import com.google.inject.Injector;
import com.puppetlabs.geppetto.injectable.eclipse.AbstractGuiceAwareExecutableExtensionFactory;
import com.puppetlabs.geppetto.puppetdb.PuppetDBManagerFactory;

/**
 * Extension Factory that allows UI classes to be injected.
 */
public class ExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {
	private Injector injector;

	@Override
	protected Bundle getBundle() {
		return UIPlugin.getInstance().getContext().getBundle();
	}

	@Override
	protected Injector getInjector() {
		synchronized(this) {
			if(injector == null)
				injector = PuppetDBManagerFactory.newInjector();
			return injector;
		}
	}
}
