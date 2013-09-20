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

import java.text.DateFormat;

import org.eclipse.swt.graphics.Image;

import com.puppetlabs.geppetto.puppetdb.ui.treenode.LeafNode;
import com.puppetlabs.puppetdb.javaclient.model.Event;
import com.puppetlabs.puppetdb.javaclient.model.Resource;

public class ResourceEvent extends LeafNode<ResourceEvents> {

	private final Image image;

	private final Event event;

	private final Resource resource;

	private final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);

	public ResourceEvent(ResourceEvents parent, Event event, Resource resource, Image image) {
		super(parent);
		this.event = event;
		this.resource = resource;
		this.image = image;
	}

	@Override
	public String getColumnText(int colIdx) {
		String txt = null;
		switch(colIdx) {
			case 0:
				txt = event.getMessage();
				break;
			case 1:
				txt = event.getResourceType();
				break;
			case 2:
				txt = event.getResourceTitle();
				break;
			case 3:
				if(resource != null)
					txt = resource.getSourcefile();
				break;
			case 4:
				if(resource != null)
					txt = Integer.toString(resource.getSourceline());
				break;
			case 5:
				txt = dateFormat.format(event.getTimestamp());
		}
		return txt == null
				? ""
				: txt.trim();
	}

	public Event getEvent() {
		return event;
	}

	@Override
	public Image getImage(int colIdx) {
		return colIdx == 0
				? image
				: null;
	}

	public Resource getResource() {
		return resource;
	}
}
