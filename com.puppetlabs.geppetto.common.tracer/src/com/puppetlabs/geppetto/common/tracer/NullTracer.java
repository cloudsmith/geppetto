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
package com.puppetlabs.geppetto.common.tracer;

public class NullTracer implements ITracer {
	public static final NullTracer INSTANCE = new NullTracer();

	private static final IStringProvider NULL_PROVIDER = new IStringProvider() {
		@Override
		public String doToString(Object o) {
			return "";
		}
	};

	private NullTracer() {
	}

	@Override
	public IStringProvider getStringProvider() {
		return NULL_PROVIDER;
	}

	@Override
	public boolean isTracing() {
		return false;
	}

	@Override
	public void trace(String message, Object... objects) {
	}
}
