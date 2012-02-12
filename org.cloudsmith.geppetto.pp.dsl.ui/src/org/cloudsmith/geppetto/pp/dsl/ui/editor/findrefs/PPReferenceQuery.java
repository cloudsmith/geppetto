/**
 * Copyright (c) 2010, 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   itemis AG - initial API and implementation
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ui.editor.findrefs;

import org.cloudsmith.geppetto.pp.dsl.ui.editor.findrefs.PPReferenceFinder.IPPQueryData;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;

import com.google.inject.Inject;

/**
 * Adaption of class with similar name in Xtext. Adapted to PP linking based on IEObjectDescription.
 */
public class PPReferenceQuery implements ISearchQuery {

	@Inject
	private PPReferenceFinder finder;

	@Inject
	protected EditorResourceAccess localContextProvider;

	private PPReferenceSearchResult searchResult;

	private IPPQueryData queryData;

	public PPReferenceQuery() {
	}

	public boolean canRerun() {
		return true;
	}

	public boolean canRunInBackground() {
		return true;
	}

	protected PPReferenceSearchResult createSearchResult() {
		return new PPReferenceSearchResult(this);
	}

	public String getLabel() {
		return queryData.getLabel();
	}

	public ISearchResult getSearchResult() {
		return searchResult;
	}

	public void init(IPPQueryData queryData) {
		this.queryData = queryData;
		this.searchResult = createSearchResult();
	}

	public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
		searchResult.reset();
		finder.findAllReferences(queryData, localContextProvider, searchResult, monitor);
		searchResult.finish();
		return (monitor.isCanceled())
				? Status.CANCEL_STATUS
				: Status.OK_STATUS;
	}
}
