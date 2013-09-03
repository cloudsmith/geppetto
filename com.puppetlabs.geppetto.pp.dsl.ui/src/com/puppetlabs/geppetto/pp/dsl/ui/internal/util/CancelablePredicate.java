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
package com.puppetlabs.geppetto.pp.dsl.ui.internal.util;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;

import com.google.common.base.Predicate;

/**
 * A Predicate that checks a monitor for cancellation and reports one unit worked per comparison.
 * 
 * @param <T>
 * 
 */
public abstract class CancelablePredicate<T> implements Predicate<T> {
	private final IProgressMonitor monitor;

	public CancelablePredicate(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.common.base.Predicate#apply(java.lang.Object)
	 */
	@Override
	final public boolean apply(T input) {
		checkMonitor();
		boolean result = monitoredApply(input);
		monitor.worked(1);
		return result;
	}

	protected void checkMonitor() throws OperationCanceledException {
		if(monitor != null && monitor.isCanceled())
			throw new OperationCanceledException();
	}

	/**
	 * Should be implemented to do what {@link Predicate#apply(Object)} normally does. Is called from {@link #apply(Object)}.
	 * 
	 * @param input
	 * @return
	 */
	public abstract boolean monitoredApply(T input);
}
