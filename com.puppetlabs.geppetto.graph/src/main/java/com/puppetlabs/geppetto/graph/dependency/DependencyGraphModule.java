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
package com.puppetlabs.geppetto.graph.dependency;

import com.puppetlabs.geppetto.graph.DependencyGraphProducer;
import com.puppetlabs.geppetto.graph.FixedSVGGraphModule;
import com.puppetlabs.geppetto.graph.IHrefProducer;

/**
 * Configuration for DependencyGraph.
 * 
 */
public class DependencyGraphModule extends FixedSVGGraphModule {
	public DependencyGraphModule(Class<? extends IHrefProducer> hrefProducerClass, String urlPrefix) {
		super(hrefProducerClass, urlPrefix);
	}

	protected void bindDependencyGraphProducer() {
		bind(DependencyGraphProducer.class).to(DependencyDataCalculator.class);
	}

	@Override
	protected void configure() {
		super.configure();
		bindDependencyGraphProducer();
	}
}
