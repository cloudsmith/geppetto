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
package com.puppetlabs.geppetto.graph.catalog;

import java.util.Collections;

import com.puppetlabs.geppetto.catalog.CatalogFactory;
import com.puppetlabs.geppetto.catalog.CatalogResource;
import com.puppetlabs.geppetto.catalog.CatalogResourceParameter;
import com.puppetlabs.geppetto.graph.IHrefProducer;
import com.puppetlabs.geppetto.graph.SVGProducer;
import com.puppetlabs.graph.dot.DotRenderer;
import com.puppetlabs.graph.elements.Vertex;
import com.puppetlabs.graph.graphcss.GraphCSS;
import com.puppetlabs.graph.style.IStyleFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;

/**
 * Functionality common to Catalog Graph producers.
 * 
 */
public class AbstractCatalogGraphProducer implements CatalogGraphStyles {

	/**
	 * Preferably "&#8658; ", but that does not seem to work everywhere.
	 */
	protected static final String DOUBLE_RIGHT_ARROW = "=&gt; ";

	@Inject
	private IStyleFactory styles;

	@Inject
	private CatalogGraphTheme theme;

	@Inject
	private DotRenderer dotRenderer;

	@Inject
	private IHrefProducer hrefProducer;

	@Inject
	private SVGProducer svgProducer;

	@Inject
	private GraphCSS instanceRules;

	protected static Predicate<? super CatalogResourceParameter> regularParameterPredicate = new Predicate<CatalogResourceParameter>() {

		@Override
		public boolean apply(CatalogResourceParameter input) {
			String aName = input.getName();
			// skip the parameters that are really dependencies
			if("before".equals(aName) || "subscribe".equals(aName) || "require".equals(aName) || "notify".equals(aName))
				return false;
			return true;
		}
	};

	protected Vertex createVertexForMissingResource(String ref) {
		Vertex v = new Vertex("", STYLE_MissingResource);
		v.setStyles(styles.labelFormat(styles.labelStringTemplate("Error: Unknown \\n" + ref)));
		return v;
	}

	protected DotRenderer getDotRenderer() {
		return dotRenderer;
	}

	protected IHrefProducer getHrefProducer() {
		return hrefProducer;
	}

	protected GraphCSS getInstanceRules() {
		return instanceRules;
	}

	/**
	 * Adds "exported" and "virtual" as pseudo parameters if they are set to true.
	 * 
	 * @param r
	 * @return
	 */
	protected Iterable<CatalogResourceParameter> getParameterIterable(CatalogResource r) {
		Iterable<CatalogResourceParameter> result = r.getParameters();
		if(r.isExported() || r.isVirtual()) {
			if(r.isExported()) {
				CatalogResourceParameter p = CatalogFactory.eINSTANCE.createCatalogResourceParameter();
				p.setName("exported");
				p.getValue().add("true");
				result = Iterables.concat(result, Collections.singleton(p));
			}
			if(r.isVirtual()) {
				CatalogResourceParameter p = CatalogFactory.eINSTANCE.createCatalogResourceParameter();
				p.setName("virtual");
				p.getValue().add("true");
				result = Iterables.concat(result, Collections.singleton(p));
			}
		}
		return result;
	}

	protected IStyleFactory getStyles() {
		return styles;
	}

	/**
	 * Convenience method to get a SVGProducer loaded with the same injector.
	 * 
	 * @return
	 */
	public SVGProducer getSVGProducer() {
		return svgProducer;
	}

	protected CatalogGraphTheme getTheme() {
		return theme;
	}

}
