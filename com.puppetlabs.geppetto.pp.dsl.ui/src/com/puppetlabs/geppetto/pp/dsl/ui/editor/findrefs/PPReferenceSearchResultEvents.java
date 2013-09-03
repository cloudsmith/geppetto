/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Jan Koehnlein - Initial contribution and API
 *   Puppet Labs
 * 
 */
package com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs;

/**
 * Adaption of class with similar name from Xtext. This implementation is based on IEObjectDescription
 *
 */

import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.SearchResultEvent;
import org.eclipse.xtext.resource.IReferenceDescription;

public interface PPReferenceSearchResultEvents {

	public static class Added extends SearchResultEvent {
		private static final long serialVersionUID = 1L;

		private final IReferenceDescription referenceDescription;

		public Added(ISearchResult searchResult, IReferenceDescription referenceDescription) {
			super(searchResult);
			this.referenceDescription = referenceDescription;
		}

		public IReferenceDescription getReferenceDescription() {
			return referenceDescription;
		}
	}

	public static class Finish extends SearchResultEvent {

		private static final long serialVersionUID = 1L;

		public Finish(ISearchResult result) {
			super(result);
		}
	}

	public static class Reset extends SearchResultEvent {
		private static final long serialVersionUID = 1L;

		public Reset(PPReferenceSearchResult referenceSearchResult) {
			super(referenceSearchResult);
		}
	}

}
