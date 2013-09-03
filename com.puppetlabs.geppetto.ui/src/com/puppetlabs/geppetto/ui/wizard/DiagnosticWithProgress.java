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
package com.puppetlabs.geppetto.ui.wizard;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubProgressMonitor;

/**
 */
public class DiagnosticWithProgress extends Diagnostic {
	private static final long serialVersionUID = 1L;

	private final Timer tickTimer = new Timer();

	private final IProgressMonitor monitor;

	private final TimerTask tickTask = new TimerTask() {
		@Override
		public void run() {
			synchronized(monitor) {
				if(remainingOnCurrentTask > 0) {
					monitor.worked(1);
					--remainingOnCurrentTask;
				}
			}
		}
	};

	private final int ticksPerTask;

	private final Iterator<String> subNamesIter;

	private int remainingOnCurrentTask;

	public DiagnosticWithProgress(IProgressMonitor parentMonitor, int ticksInParent, List<String> subtaskNames,
			int msPerTask) {
		ticksPerTask = msPerTask / 100; // We tick 10 times per second
		monitor = new SubProgressMonitor(parentMonitor, ticksInParent);
		monitor.beginTask(null, subtaskNames.size() * ticksPerTask);
		subNamesIter = subtaskNames.iterator();
		if(subNamesIter.hasNext())
			monitor.subTask(subNamesIter.next());
		remainingOnCurrentTask = ticksPerTask;
		tickTimer.schedule(tickTask, 100, 100);
	}

	@Override
	protected void childAdded(Diagnostic diagnostic) {
		logDiagnostic(diagnostic);
	}

	public void done() {
		tickTask.cancel();
		tickTimer.cancel();
		synchronized(monitor) {
			monitor.done();
		}
	}

	private void logDiagnostic(Diagnostic diag) {
		if(diag == null)
			return;

		synchronized(monitor) {
			if(monitor.isCanceled())
				throw new OperationCanceledException();

			String msg = diag.getMessage();
			if(msg != null)
				monitor.subTask(msg);
		}
		for(Diagnostic child : diag)
			logDiagnostic(child);
	}

	public void taskDone() {
		synchronized(monitor) {
			if(remainingOnCurrentTask > 0)
				monitor.worked(remainingOnCurrentTask);
			remainingOnCurrentTask = ticksPerTask;
		}
		if(subNamesIter.hasNext())
			monitor.subTask(subNamesIter.next());
	}
}
