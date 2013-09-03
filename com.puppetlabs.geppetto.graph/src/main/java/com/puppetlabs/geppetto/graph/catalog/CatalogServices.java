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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.puppetlabs.geppetto.catalog.Catalog;
import com.puppetlabs.geppetto.catalog.util.CatalogJsonSerializer;
import com.puppetlabs.geppetto.graph.EmptyStringHrefProducer;
import com.puppetlabs.geppetto.graph.GraphHrefType;
import com.puppetlabs.geppetto.graph.IHrefProducer;
import com.puppetlabs.geppetto.graph.ProgressMonitorCancelIndicator;
import com.puppetlabs.graph.ICancel;
import com.puppetlabs.graph.utils.ByteArrayOutputStream2;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Catalog Services provides services that:
 * <ul>
 * <li>Produce a SVG graph of a Puppet Catalog</li>
 * <li>Produce a SVG graph of the difference of two Puppet Catalogs</li>
 * </ul>
 * 
 */
public class CatalogServices {

	private final Injector injector;

	public CatalogServices() {
		this(EmptyStringHrefProducer.class, "");
	}

	public CatalogServices(Class<? extends IHrefProducer> hrefProducerClass, String prefix) {
		injector = Guice.createInjector(new CatalogGraphModule(hrefProducerClass, prefix));
	}

	public CatalogServices(GraphHrefType graphHrefType, String prefix) {
		this(graphHrefType == null
				? GraphHrefType.JS.getHrefProducerClass()
				: graphHrefType.getHrefProducerClass(), prefix);
	}

	@Deprecated
	public void produceDOTDeltaGraph(String catalogName, InputStream oldCatalogStream, InputStream newCatalogStream,
			OutputStream dotStream, IProgressMonitor monitor) throws IOException {
		produceDOTDeltaGraph(catalogName, oldCatalogStream, null, newCatalogStream, null, dotStream, monitor);
	}

	/**
	 * Produces a graph depicting the old and new and the delta between them. Output is in SVG format.
	 * 
	 * @param catalogName
	 *        The name to generate as title in the graph. May be <code>null</code>
	 * @param oldCatalogSream
	 * @param newCatalogStream
	 * @param svgStream
	 * @param monitor
	 * @param root
	 *        - the root for files listed as source files in the catalog
	 */
	public void produceDOTDeltaGraph(String catalogName, InputStream oldCatalogStream, IPath oldRoot,
			InputStream newCatalogStream, IPath newRoot, OutputStream dotStream, IProgressMonitor monitor)
			throws IOException {
		final SubMonitor ticker = SubMonitor.convert(monitor, 1000);
		CatalogDeltaGraphProducer graphProducer = injector.getInstance(CatalogDeltaGraphProducer.class);
		Catalog oldCatalog = CatalogJsonSerializer.load(oldCatalogStream);
		Catalog newCatalog = CatalogJsonSerializer.load(newCatalogStream);

		ICancel cancel = new ProgressMonitorCancelIndicator(ticker.newChild(IProgressMonitor.UNKNOWN), 1000);
		graphProducer.produceGraph(cancel, catalogName, oldCatalog, oldRoot, newCatalog, newRoot, dotStream);
	}

	@Deprecated
	public void produceDOTGraph(String catalogName, InputStream catalogStream, OutputStream dotStream,
			IProgressMonitor monitor) throws IOException {
		produceDOTGraph(catalogName, catalogStream, dotStream, monitor, null);
	}

	public void produceDOTGraph(String catalogName, InputStream catalogStream, OutputStream dotStream,
			IProgressMonitor monitor, IPath root) throws IOException {
		final SubMonitor ticker = SubMonitor.convert(monitor, 1000);
		CatalogGraphProducer graphProducer = injector.getInstance(CatalogGraphProducer.class);
		ICancel cancel = new ProgressMonitorCancelIndicator(ticker.newChild(IProgressMonitor.UNKNOWN), 1000);
		Catalog catalog = CatalogJsonSerializer.load(catalogStream);

		graphProducer.produceGraph(cancel, catalog, catalogName, dotStream, root);
	}

	@Deprecated
	public void produceSVGDeltaGraph(String catalogName, InputStream oldCatalogStream, InputStream newCatalogStream,
			OutputStream svgStream, IProgressMonitor monitor) throws IOException {
		produceSVGDeltaGraph(catalogName, oldCatalogStream, null, newCatalogStream, null, svgStream, monitor);
	}

	/**
	 * Produces a graph depicting the old and new and the delta between them. Output is in SVG format.
	 * 
	 * @param catalogName
	 *        The name to generate as title in the graph. May be <code>null</code>
	 * @param oldCatalogSream
	 * @param newCatalogStream
	 * @param svgStream
	 * @param monitor
	 * @param root
	 *        - the root for files listed as source files in the catalog
	 */
	public void produceSVGDeltaGraph(String catalogName, InputStream oldCatalogStream, IPath oldRoot,
			InputStream newCatalogStream, IPath newRoot, OutputStream svgStream, IProgressMonitor monitor)
			throws IOException {
		final SubMonitor ticker = SubMonitor.convert(monitor, 2000);
		CatalogDeltaGraphProducer graphProducer = injector.getInstance(CatalogDeltaGraphProducer.class);
		Catalog oldCatalog = CatalogJsonSerializer.load(oldCatalogStream);
		Catalog newCatalog = CatalogJsonSerializer.load(newCatalogStream);

		ICancel cancel = new ProgressMonitorCancelIndicator(ticker.newChild(IProgressMonitor.UNKNOWN), 1000);

		ByteArrayOutputStream2 out = new ByteArrayOutputStream2();

		graphProducer.produceGraph(cancel, catalogName, oldCatalog, oldRoot, newCatalog, newRoot, out);
		graphProducer.getSVGProducer().produceSVG(out.toInputStream(false), svgStream, false, //
		ticker.newChild(IProgressMonitor.UNKNOWN));
	}

	@Deprecated
	public void produceSVGGraph(String catalogName, InputStream catalogStream, OutputStream svgStream,
			IProgressMonitor monitor) throws IOException {
		produceSVGGraph(catalogName, catalogStream, svgStream, monitor, null);
	}

	public void produceSVGGraph(String catalogName, InputStream catalogStream, OutputStream svgStream,
			IProgressMonitor monitor, IPath root) throws IOException {
		final SubMonitor ticker = SubMonitor.convert(monitor, 2000);
		CatalogGraphProducer graphProducer = injector.getInstance(CatalogGraphProducer.class);
		ICancel cancel = new ProgressMonitorCancelIndicator(ticker.newChild(IProgressMonitor.UNKNOWN), 1000);
		Catalog catalog = CatalogJsonSerializer.load(catalogStream);

		ByteArrayOutputStream2 out = new ByteArrayOutputStream2();
		graphProducer.produceGraph(cancel, catalog, catalogName, out, root);
		graphProducer.getSVGProducer().produceSVG(out.toInputStream(false), svgStream, false, //
		ticker.newChild(IProgressMonitor.UNKNOWN));
	}
}
