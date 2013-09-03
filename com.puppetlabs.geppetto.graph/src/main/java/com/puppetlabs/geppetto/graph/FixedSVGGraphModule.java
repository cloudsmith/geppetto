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
package com.puppetlabs.geppetto.graph;

import com.puppetlabs.graph.DefaultGraphModule;
import com.puppetlabs.graph.dot.DotRenderer;
import com.puppetlabs.graph.graphviz.IGraphviz;
import com.puppetlabs.graph.graphviz.SVGFixerOutputStream;
import com.puppetlabs.graph.graphviz.SVGFixerOutputStreamFilterFactory;
import com.puppetlabs.graph.utils.IOutputStreamFilterFactory;

import com.google.inject.Singleton;
import com.google.inject.name.Names;

/**
 * Configuration for Graph Producers that produce DOT output suitable for SVG post processing.
 * Makes use of a magic empty string that is filtered out when producing SVG.
 * Note that this configuration is (more or less) useless when using the generated DOT to provide anything but SVG
 * output as empty strings will contain the magic text which may be rendered to a non post-processable format
 * (e.g. removing text rendered into a JPEG).
 * 
 */
public class FixedSVGGraphModule extends DefaultGraphModule {
	private final Class<? extends IHrefProducer> hrefProducerClass;

	private final String urlPrefix;

	public FixedSVGGraphModule(Class<? extends IHrefProducer> hrefProducerClass, String urlPrefix) {
		this.hrefProducerClass = hrefProducerClass;
		this.urlPrefix = urlPrefix;
	}

	@Override
	protected void bindEmptyStringConstant() {
		bindConstant().annotatedWith(DotRenderer.EmptyString.class).to(SVGFixerOutputStream.EMPTY_STRING_BUG);
	}

	protected void bindIHrefProducer() {
		if(urlPrefix != null)
			bind(String.class).annotatedWith(Names.named(AbstractHrefProducer.URL_PREFIX_NAME)).toInstance(urlPrefix);
		bind(IHrefProducer.class).to(hrefProducerClass);
	}

	@Override
	protected void bindSVGOutputFilterProvider() {
		bind(IOutputStreamFilterFactory.class).annotatedWith(IGraphviz.SVGOutputFilter.class).to(
			SVGFixerOutputStreamFilterFactory.class).in(Singleton.class);
	}

	@Override
	protected void configure() {
		super.configure();
		bindIHrefProducer();
	}
}
