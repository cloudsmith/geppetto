/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   itemis AG, initial API and implementation (Jan Koehnlein)
 *   Puppet Labs
 * 
 */
package com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.ISearchResultListener;
import org.eclipse.search.ui.SearchResultEvent;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.util.IAcceptor;

import com.google.common.collect.Lists;

/**
 * Adaption of class with similar name in xtext. This implementation deals with IEObjectDescription instead of
 * IEReferenceDescription as PP linking is not based on EReferences.
 * 
 */
public class PPReferenceSearchResult implements ISearchResult, IAcceptor<IReferenceDescription> {

	private PPReferenceQuery query;

	private List<IReferenceDescription> matchingReferences;

	private List<ISearchResultListener> listeners;

	protected PPReferenceSearchResult(PPReferenceQuery query) {
		this.query = query;
		matchingReferences = Lists.newArrayList();
		listeners = Lists.newArrayList();
	}

	public void accept(IReferenceDescription referenceDescription) {
		matchingReferences.add(referenceDescription);
		fireEvent(new PPReferenceSearchResultEvents.Added(this, referenceDescription));
	}

	public void addListener(ISearchResultListener l) {
		synchronized(listeners) {
			listeners.add(l);
		}
	}

	public void finish() {
		fireEvent(new PPReferenceSearchResultEvents.Finish(this));
	}

	void fireEvent(SearchResultEvent searchResultEvent) {
		synchronized(listeners) {
			for(ISearchResultListener listener : listeners) {
				listener.searchResultChanged(searchResultEvent);
			}
		}
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getLabel() {
		return query.getLabel();
	}

	public List<IReferenceDescription> getMatchingReferences() {
		return matchingReferences;
	}

	public ISearchQuery getQuery() {
		return query;
	}

	public String getTooltip() {
		return getLabel();
	}

	public void removeListener(ISearchResultListener l) {
		synchronized(listeners) {
			listeners.remove(l);
		}
	}

	public void reset() {
		matchingReferences.clear();
		fireEvent(new PPReferenceSearchResultEvents.Reset(this));
	}

}
