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
package org.cloudsmith.geppetto.graph;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CancellationException;
import java.util.zip.GZIPOutputStream;

import org.cloudsmith.graph.ICancel;
import org.cloudsmith.graph.graphviz.GraphvizFormat;
import org.cloudsmith.graph.graphviz.GraphvizLayout;
import org.cloudsmith.graph.graphviz.IGraphviz;
import org.cloudsmith.graph.graphviz.IGraphviz.SVGOutputFilter;
import org.cloudsmith.graph.utils.IOutputStreamFilterFactory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A service facade for Graphviz SVG production that configures a Guice injector and performs
 * optional post processing of SVG stream.
 * 
 * The only state held by the SVGProducer is the injector. It is safe to hold on to an instance
 * of this class and use it multiple times. If instantiated via Guice it is a singleton.
 * 
 */
@Singleton
public class SVGProducer {

	// graphviz runner
	@Inject
	private IGraphviz graphviz;

	@Inject
	@SVGOutputFilter
	private IOutputStreamFilterFactory streamFilterFactory;

	/**
	 * Transforms the text in DOT language in the given dotStream to SVG and writes the resulting SVG
	 * text to the given svgStream. If compress is true, the SVG output is written as SVGZ.
	 * 
	 * When transformation is completed, the given svgStream is in a state where further writes are
	 * possible. It is the caller's responsibility to close the stream.
	 * 
	 * It is possible to inject a filter configuration into the SVG stream by binding an {@link IOutputStreamFilterFactory}
	 * annotated with
	 * 
	 * @Named(DependencyGraphModule.GRAPH_SVG_OUTPUT).
	 * 
	 * @param dotStream
	 *        stream with text in DOT language
	 * @param svgStream
	 *        stream where SVG(Z) will be written
	 * @param compress
	 *        output is SVGZ if true
	 * @param monitor
	 *        used to check for cancellation, and for work pings, will configure SubMonitor with unknown work. Does not call done
	 *        on
	 *        the passed monitor - this is the callers responsibility unless a SubMonitor was passed.
	 */
	@SuppressWarnings("resource")
	public void produceSVG(InputStream dotStream, OutputStream svgStream, boolean compress, IProgressMonitor monitor)
			throws IOException {
		// monitor
		SubMonitor m2 = SubMonitor.convert(monitor);
		// convert every 1000 checks for cancel into a worked(1)
		final ICancel cancel = new ProgressMonitorCancelIndicator(m2.newChild(IProgressMonitor.UNKNOWN), 1000);

		// configure in and out streams
		// ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		// byte[] buffer = new byte[512];
		// for(;;) {
		// int read = dotStream.read(buffer);
		// if(read == -1)
		// break;
		// tmp.write(buffer, 0, read);
		// }
		GZIPOutputStream zipStream = null;
		if(compress) {
			zipStream = new GZIPOutputStream(svgStream);
			svgStream = zipStream;
		}

		// If filtering is wanted
		svgStream = streamFilterFactory.configureFilterFor(svgStream);

		// produce SVG from DOT
		try {
			if(graphviz.writeGraphvizOutput(cancel, svgStream, GraphvizFormat.svg, null, GraphvizLayout.dot, dotStream) == null)
				throw new IOException("Graphviz SVG production failed - view logs");
		}
		catch(CancellationException e) {
			// translate to expected exception when using IProgressMonitor
			throw new OperationCanceledException();
		}

		// if the svgStream requires a finish to flush trailing data, it needs to be finished before the
		// zip stream (if used).
		if(svgStream instanceof IOutputStreamFilterFactory.IFinishable)
			((IOutputStreamFilterFactory.IFinishable) svgStream).finish();

		// Make sure everything is written as we may otherwise lose trailing uncompressed bits
		if(compress)
			zipStream.finish();

		// seems prudent to flush, but *do not* close, as the user may want to continue writing data
		// to the given stream.
		svgStream.flush();
	}
}
