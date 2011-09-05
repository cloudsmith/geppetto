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
package org.cloudsmith.geppetto.ui;

import org.cloudsmith.geppetto.pp.dsl.ui.internal.PPActivator;
import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

/**
 * Extension Factory that allows UI classes to be injected.
 * 
 */
public class GeppettoExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {
	private static Injector injector;

	@Override
	protected Bundle getBundle() {
		return UIPlugin.getPlugin().getBundle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.PPExecutableExtensionFactory#getInjector()
	 */
	@Override
	protected Injector getInjector() {
		synchronized(this) {
			if(injector == null)
				injector = PPActivator.getInstance().getInjector("org.cloudsmith.geppetto.pp.dsl.PP").createChildInjector();
		}
		return injector;
	}
}
