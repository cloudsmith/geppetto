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
package org.cloudsmith.xtext.dommodel.formatter.css.debug;

import java.util.Map;

import org.cloudsmith.geppetto.common.tracer.IStringProvider;
import org.cloudsmith.geppetto.common.tracer.ITracer;
import org.cloudsmith.geppetto.common.util.EclipseUtils;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;

import com.google.common.collect.MapMaker;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

/**
 * The FormattingTracer is a singleton that performs tracing if the debug option "debug/formatter" is
 * set (in .options, or via launch configuration). If not turned on, all of the formatters operations
 * have no effect.
 * 
 * The FormattingTracer also implements ITracer which delegates to a configurable tracer named {@link #DEBUG_FORMATTER}.
 * 
 */
@Singleton
public class FormattingTracer implements ITracer {

	/**
	 * Access to runtime configurable debug trace.
	 */
	@Inject
	@Named(DEBUG_FORMATTER)
	private ITracer tracer;

	/**
	 * TODO: This is obviously not generic - should reflect the name of the plugin
	 * providing the formatter.
	 */
	public static final String PLUGIN_NAME = "org.cloudsmith.geppetto.pp.dsl";

	/**
	 * Name of option in formatter providing plugin that turns on debugging/tracing
	 * of formatting.
	 */
	public static final String DEBUG_FORMATTER = PLUGIN_NAME + "/debug/formatter";

	/**
	 * Safe way of getting the debug option
	 * 
	 * @param option
	 * @return
	 */
	private static boolean getDebugOption(String option) {
		String value = EclipseUtils.getDebugOption(option);
		return (value != null && "true".equals(value))
				? true
				: false;
	}

	private boolean tracing;

	private final Map<IDomNode, StyleSet> effectiveStyleMap;

	public FormattingTracer() {
		tracing = false;
		if(EclipseUtils.inDebugMode())
			tracing = getDebugOption(DEBUG_FORMATTER);

		effectiveStyleMap = new MapMaker().weakKeys().makeMap();
	}

	public StyleSet getEffectiveStyle(IDomNode node) {
		return isTracing()
				? effectiveStyleMap.get(node)
				: null;
	}

	@Override
	public IStringProvider getStringProvider() {
		return tracer.getStringProvider();
	}

	public boolean isTracing() {
		return tracing;
	}

	public void recordEffectiveStyle(IDomNode node, StyleSet styles) {
		if(isTracing())
			effectiveStyleMap.put(node, styles);
	}

	@Override
	public void trace(String message, Object... objects) {
		tracer.trace(message, objects);
	}
}
