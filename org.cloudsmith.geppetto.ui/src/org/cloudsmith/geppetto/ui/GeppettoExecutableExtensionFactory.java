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
package org.cloudsmith.geppetto.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

/**
 * Extension Factory that allows UI classes to be injected.
 * 
 */
public class GeppettoExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {
	@Override
	protected Bundle getBundle() {
		return UIPlugin.getInstance().getContext().getBundle();
	}

	@Override
	protected Injector getInjector() {
		return UIPlugin.getInstance().getInjector();
	}
}
