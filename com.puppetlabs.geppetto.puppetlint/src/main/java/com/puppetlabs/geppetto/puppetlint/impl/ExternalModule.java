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
package com.puppetlabs.geppetto.puppetlint.impl;

import com.puppetlabs.geppetto.puppetlint.PuppetLintRunner;

import com.google.inject.AbstractModule;

/**
 * @author thhal
 * 
 */
public class ExternalModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(PuppetLintRunner.class).to(ExternalPuppetLintRunner.class);
	}
}
