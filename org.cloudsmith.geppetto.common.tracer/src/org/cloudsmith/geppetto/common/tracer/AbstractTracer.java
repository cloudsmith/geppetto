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
package org.cloudsmith.geppetto.common.tracer;

import org.cloudsmith.geppetto.common.util.BundleAccess;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Provides the base functionality for a tracer - configuring it based on the option.
 * A derived class typically only implements the trace method.
 */
public abstract class AbstractTracer implements ITracer {

	@Singleton
	public static class DefaultStringProvider implements IStringProvider {
		@Override
		public String doToString(Object o) {
			return String.valueOf(o);
		}
	}

	protected final String option;

	private IStringProvider stringProvider;

	private boolean tracing;

	protected AbstractTracer(String option) {
		this.option = option;
	}

	public String getOption() {
		return option;
	}

	@Override
	public IStringProvider getStringProvider() {
		return stringProvider;
	}

	/**
	 * Method used for injection. Should normally not be called explicitly
	 * 
	 * @param provider
	 * @param bundleAccess
	 */
	@Inject
	public void inject(IStringProvider provider, BundleAccess bundleAccess) {
		stringProvider = provider;
		tracing = bundleAccess.inDebugMode() && Boolean.parseBoolean(bundleAccess.getDebugOption(option));
	}

	@Override
	public boolean isTracing() {
		return tracing;
	}

	@Override
	public abstract void trace(String message, Object... objects);

}
