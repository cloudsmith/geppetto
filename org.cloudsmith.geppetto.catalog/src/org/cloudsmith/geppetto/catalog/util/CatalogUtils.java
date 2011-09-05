/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.catalog.util;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.cloudsmith.geppetto.catalog.Catalog;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;

/**
 * Performs various services for catalogs.
 * 
 */
public class CatalogUtils {

	/**
	 * Produces a {@link DiffModel} for the difference between the catalogs a and b.
	 * 
	 * @param a
	 * @param b
	 * @return {@link DiffModel} describing the difference
	 * @throws OperationCanceledException
	 */
	public DiffModel catalogDelta(Catalog a, Catalog b) throws OperationCanceledException {
		MatchModel match = null;
		try {
			match = MatchService.doContentMatch(a, b, Collections.<String, Object> emptyMap());
		}
		catch(InterruptedException e) {
			throw new OperationCanceledException("Canceled by match service thread interrupt");
		}
		DiffModel diff = DiffService.doDiff(match, false);
		return diff;

	}

	public Catalog loadFromJsonFile(File f) throws IOException {
		return CatalogJsonSerializer.load(f);
	}
}
