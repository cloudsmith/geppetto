/*******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM - Initial API and implementation
 *******************************************************************************/
package org.eclipse.core.runtime;

/**
 * An extension to the IProgressMonitor interface for monitors that want to
 * support feedback when an activity is blocked due to concurrent activity in
 * another thread.
 * <p>
 * When a monitor that supports this extension is passed to an operation, the
 * operation should call <code>setBlocked</code> whenever it knows that it
 * must wait for a lock that is currently held by another thread. The operation
 * should continue to check for and respond to cancelation requests while
 * blocked. When the operation is no longer blocked, it must call <code>clearBlocked</code>
 * to clear the blocked state.
 * <p>
 * This interface can be used without OSGi running.
 * </p><p>
 * Clients may implement this interface.
 * </p>
 * @see IProgressMonitor
 * @since 3.0
 */
public interface IProgressMonitorWithBlocking extends IProgressMonitor {
	/**
	 * Indicates that this operation is blocked by some background activity. If
	 * a running operation ever calls <code>setBlocked</code>, it must
	 * eventually call <code>clearBlocked</code> before the operation
	 * completes.
	 * <p>
	 * If the caller is blocked by a currently executing job, this method will return
	 * an <code>IJobStatus</code> indicating the job that is currently blocking
	 * the caller. If this blocking job is not known, this method will return a plain
	 * informational <code>IStatus</code> object.
	 * </p>
	 * 
	 * @param reason an optional status object whose message describes the
	 * reason why this operation is blocked, or <code>null</code> if this
	 * information is not available.
	 * @see #clearBlocked()
	 */
	public void setBlocked(IStatus reason);

	/**
	 * Clears the blocked state of the running operation. If a running
	 * operation ever calls <code>setBlocked</code>, it must eventually call
	 * <code>clearBlocked</code> before the operation completes.
	 * 
	 * @see #setBlocked(IStatus)
	 */
	public void clearBlocked();

}
