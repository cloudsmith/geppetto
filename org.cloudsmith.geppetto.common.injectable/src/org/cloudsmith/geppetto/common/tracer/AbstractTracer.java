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

import org.eclipse.core.runtime.Platform;

import com.google.inject.Inject;

/**
 * Provides the base functionality for a tracer - configuring it based on the option.
 * A derived class typically only implements the trace method.
 * 
 */
public abstract class AbstractTracer implements ITracer {

	public static class DefaultStringProvider implements IStringProvider {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cloudsmith.geppetto.common.tracer.IStringProvider#doToString(java.lang.Object)
		 */
		@Override
		public String doToString(Object o) {
			return String.valueOf(o);
		}
	}

	/**
	 * Safe way of getting the debug option
	 * 
	 * @param option
	 * @return
	 */
	private static boolean getDebugOption(String option) {
		String value = Platform.getDebugOption(option);
		return (value != null && "true".equals(value))
				? true
				: false;
	}

	protected boolean tracing;

	protected String option;

	@Inject
	private IStringProvider stringProvider;

	protected AbstractTracer(String option) {
		tracing = false;
		this.option = option;
		if(Platform.inDebugMode())
			tracing = getDebugOption(option);
	}

	public String getOption() {
		return option;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.common.tracer.ITracer#getStringProvider()
	 */
	@Override
	public IStringProvider getStringProvider() {
		return stringProvider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.common.tracer.ITracer#isTracing()
	 */
	@Override
	public boolean isTracing() {
		return tracing;
	}

	public void setStringProvider(IStringProvider provider) {
		stringProvider = provider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.common.tracer.ITracer#trace(java.lang.String, java.lang.Object[])
	 */
	@Override
	public abstract void trace(String message, Object... objects);

}
