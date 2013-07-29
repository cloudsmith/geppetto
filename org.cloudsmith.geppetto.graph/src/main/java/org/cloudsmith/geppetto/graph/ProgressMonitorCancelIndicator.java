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
package org.cloudsmith.geppetto.graph;

import java.util.concurrent.CancellationException;

import org.cloudsmith.graph.ICancel;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * An ICancel adapter for Eclipse IProgressMonitor, that in addition to checking for
 * cancellation, calls worked(1) on the monitor per n cancellation checks.
 * 
 */
public class ProgressMonitorCancelIndicator implements ICancel {

	private final IProgressMonitor monitor;

	private int checksPerPing;

	private int pingCounter;

	public ProgressMonitorCancelIndicator(IProgressMonitor monitor, int checksPerPing) {
		this.monitor = monitor;
		this.checksPerPing = checksPerPing;
		pingCounter = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.graph.ICancel#assertContinue()
	 */
	@Override
	public void assertContinue() throws CancellationException {
		if(isCanceled())
			throw new CancellationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.graph.ICancel#isCanceled()
	 */
	@Override
	public boolean isCanceled() {
		if(++pingCounter > checksPerPing)
			ping();
		return monitor.isCanceled();
	}

	private void ping() {
		pingCounter = 0;
		monitor.worked(1);
	}

}
