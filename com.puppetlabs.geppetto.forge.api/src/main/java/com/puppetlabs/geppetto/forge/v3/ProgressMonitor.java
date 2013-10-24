/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 * 
 */
package com.puppetlabs.geppetto.forge.v3;

public interface ProgressMonitor {
	/**
	 * Starts a new task with <code>totalWorkUnits</code>.
	 * 
	 * @param totalWorkUnits
	 *            The total number of work units to allocate for the task
	 */
	public void beginTask(int toDo);

	/**
	 * Marks this monitor as cancelled
	 */
	void cancel();

	/**
	 * Ends a task
	 */
	public void endTask();

	/**
	 * @return <code>true</code> if this monitor has been marked as canceled.
	 */
	boolean isCanceled();

	/**
	 * Marks work is underway and <code>workDone</code> work-units as done.
	 * 
	 * @param message
	 *            The message to show
	 * @param workDone
	 *            The number of work units that has been consumed
	 */
	public void work(String message, int workDone);
}
