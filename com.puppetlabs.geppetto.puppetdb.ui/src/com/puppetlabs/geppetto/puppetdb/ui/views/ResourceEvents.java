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

import static com.puppetlabs.puppetdb.javaclient.model.Resource.CERTNAME;
import static com.puppetlabs.puppetdb.javaclient.model.Resource.TITLE;
import static com.puppetlabs.puppetdb.javaclient.model.Resource.TYPE;
import static com.puppetlabs.puppetdb.javaclient.query.Query.and;
import static com.puppetlabs.puppetdb.javaclient.query.Query.eq;
import static com.puppetlabs.puppetdb.javaclient.query.Query.or;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.puppetlabs.geppetto.puppetdb.ui.UIPlugin;
import com.puppetlabs.puppetdb.javaclient.model.Event;
import com.puppetlabs.puppetdb.javaclient.model.Node;
import com.puppetlabs.puppetdb.javaclient.model.Report;
import com.puppetlabs.puppetdb.javaclient.model.Resource;
import com.puppetlabs.puppetdb.javaclient.query.Expression;

public abstract class ResourceEvents extends PuppetDBQuery<ResourceEvent> implements Runnable {
	private static final ResourceEvent[] empty = new ResourceEvent[0];

	private ResourceEvent[] children;

	public ResourceEvents(PuppetDBConnection dbAccess) {
		super(dbAccess);
	}

	private Map<String, Resource> buildResourceMap(List<Event> events) {
		try {
			List<Resource> resourceList = getClient().getResources(getResourceQuery(events));
			int top = resourceList.size();
			if(top == 0)
				return Collections.emptyMap();

			Map<String, Resource> resourceMap = new HashMap<String, Resource>(top);
			for(int idx = 0; idx < top; ++idx) {
				Resource resource = resourceList.get(idx);
				resourceMap.put(getResourceKey(resource.getCertname(), resource.getTitle(), resource.getType()), resource);
			}
			return resourceMap;
		}
		catch(IOException e) {
			UIPlugin.logException("Unable to query PuppetDB for resources", e);
			return Collections.emptyMap();
		}
	}

	@Override
	public synchronized ResourceEvent[] getChildren() {
		if(children == null) {
			children = empty;
			Display.getDefault().asyncExec(this);
		}
		return children;
	}

	protected abstract Expression<Event> getEventQuery();

	protected abstract Image getResourceEventImage();

	private String getResourceKey(String certname, String resourceTitle, String resourceType) {
		return certname + '$' + resourceTitle + '$' + resourceType;
	}

	private Expression<Resource> getResourceQuery(Event event) {
		return and(eq(CERTNAME, event.getCertname()), eq(TITLE, event.getResourceTitle()), eq(TYPE, event.getResourceType()));
	}

	private Expression<Resource> getResourceQuery(List<Event> events) {
		int top = events.size();
		if(top == 0)
			return null;
		if(top == 1)
			return getResourceQuery(events.get(0));

		List<Expression<Resource>> queries = new ArrayList<Expression<Resource>>(top);
		for(Event event : events)
			queries.add(getResourceQuery(event));
		return or(queries);
	}

	@Override
	public void run() {
		ResourceEvent[] tuples = empty;
		try {
			List<Expression<Event>> reportHashes = new ArrayList<Expression<Event>>();
			for(Node node : getClient().getActiveNodes(null))
				for(Report report : getClient().getReports(eq(Report.CERTNAME, node.getName())))
					if(report.getEndTime().equals(node.getReportTimestamp()))
						reportHashes.add(eq(Event.REPORT, report.getHash()));

			int hashCount = reportHashes.size();
			if(hashCount > 0) {
				Expression<Event> reportQuery = hashCount == 1
						? reportHashes.get(0)
						: or(reportHashes);
				List<Event> events = getClient().getEvents(and(reportQuery, getEventQuery()));
				int top = events.size();
				if(top > 0) {
					Map<String, Resource> resources = buildResourceMap(events);
					tuples = new ResourceEvent[top];
					for(int idx = 0; idx < top; ++idx) {
						Event event = events.get(idx);
						Resource resource = resources.get(getResourceKey(
							event.getCertname(), event.getResourceTitle(), event.getResourceType()));
						tuples[idx] = new ResourceEvent(this, event, resource, getResourceEventImage());
					}
				}
			}
			synchronized(this) {
				children = tuples;
				TreeViewer tree = getViewer();
				if(!tree.getTree().isDisposed())
					tree.refresh(this);
			}
		}
		catch(Exception e) {
			tuples = empty;
			getParent().showException(e);
		}
	}
}
