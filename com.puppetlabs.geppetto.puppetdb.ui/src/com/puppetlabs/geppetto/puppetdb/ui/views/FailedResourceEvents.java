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
package com.puppetlabs.geppetto.puppetdb.ui.views;

import static com.puppetlabs.puppetdb.javaclient.model.Event.STATUS;
import static com.puppetlabs.puppetdb.javaclient.query.Query.eq;

import org.eclipse.swt.graphics.Image;

import com.puppetlabs.geppetto.puppetdb.ui.UIPlugin;
import com.puppetlabs.puppetdb.javaclient.model.Event;
import com.puppetlabs.puppetdb.javaclient.model.Event.Status;
import com.puppetlabs.puppetdb.javaclient.query.Expression;

/**
 * The parent node for all resource events thas has been marked as {@link Status#failure failure}.
 */
public class FailedResourceEvents extends ResourceEvents {

	private static final String FAILURES_TEXT = UIPlugin.getLocalString("_UI_Failures");

	private static final Image FAILURE_LIST_IMAGE = UIPlugin.createdImage("event_failure_list.png");

	private static final Image FAILURE_IMAGE = UIPlugin.createdImage("event_failure.png");

	/**
	 * Creates a new node with the given <code>parent</code>.
	 * 
	 * @param parent
	 *            The parent of this node
	 */
	public FailedResourceEvents(PuppetDBConnection parent) {
		super(parent);
	}

	@Override
	public String getColumnText(int colIdx) {
		return colIdx == 0
				? FAILURES_TEXT
				: "";
	}

	@Override
	protected Expression<Event> getEventQuery() {
		return eq(STATUS, Status.failure);
	}

	@Override
	public Image getImage(int colIdx) {
		return colIdx == 0
				? FAILURE_LIST_IMAGE
				: null;
	}

	@Override
	protected Image getResourceEventImage() {
		return FAILURE_IMAGE;
	}
}
