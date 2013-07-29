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
package org.cloudsmith.geppetto.pp.dsl.ui.pptp;

import org.cloudsmith.geppetto.common.extension.AbstractGuiceAwareExecutableExtensionFactory;
import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

/**
 * Executable Extension that configures injector for PPTP model obtained from Ruby code.
 */
public class PptpRubyExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory#getBundle()
	 */
	@Override
	protected Bundle getBundle() {
		return org.cloudsmith.geppetto.pp.dsl.ui.internal.PPDSLActivator.getInstance().getBundle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory#getInjector()
	 */
	@Override
	protected Injector getInjector() {
		return org.cloudsmith.geppetto.pp.dsl.ui.internal.PPDSLActivator.getInstance().getInjector(
			PPDSLConstants.PPTP_RUBY_LANGUAGE_NAME);
	}

}
