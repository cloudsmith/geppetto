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
package org.cloudsmith.geppetto.graph.dependency;

import org.cloudsmith.geppetto.graph.DependencyGraphProducer;
import org.cloudsmith.geppetto.graph.FixedSVGGraphModule;
import org.cloudsmith.geppetto.graph.IHrefProducer;

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
