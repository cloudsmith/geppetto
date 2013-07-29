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
package org.cloudsmith.geppetto.graph.catalog;

import org.cloudsmith.geppetto.graph.FixedSVGGraphModule;
import org.cloudsmith.geppetto.graph.IHrefProducer;

/**
 * Configuration for CatalogGraph.
 * 
 */
public class CatalogGraphModule extends FixedSVGGraphModule {
	public CatalogGraphModule(Class<? extends IHrefProducer> hrefProducerClass, String urlPrefix) {
		super(hrefProducerClass, urlPrefix);
	}

	@Override
	protected void configure() {
		super.configure();
		bind(CatalogGraphProducer.class);
		bind(CatalogDeltaGraphProducer.class);
	}
}
