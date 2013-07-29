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
package org.cloudsmith.geppetto.pp.dsl.ui.editor.findrefs;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.util.PolymorphicDispatcher;

/**
 * Attempts to sort found references based on resource name, and on location of the source in the file
 * so order appears natural for user (i.e. as close to from top to bottom in file it is possible to get without access to actual
 * offsets in text).
 * 
 */
public class ReferenceSearchViewSorter extends ViewerSorter {

	private PolymorphicDispatcher<Integer> comparator = PolymorphicDispatcher.createForSingleTarget(
		"_compare", 2, 2, this);

	protected Integer _compare(IReferenceDescription o0, IReferenceDescription o1) {
		return comparator.invoke(o0.getSourceEObjectUri(), o1.getSourceEObjectUri());
	}

	protected Integer _compare(IResourceDescription rd0, IResourceDescription rd1) {
		return _compare(rd0.getURI(), rd1.getURI());
	}

	protected Integer _compare(Object o0, Object o1) {
		return null;
	}

	protected Integer _compare(ReferenceSearchViewTreeNode o0, ReferenceSearchViewTreeNode o1) {
		return comparator.invoke(o0.getDescription(), o1.getDescription());
	}

	protected Integer _compare(URI o0, URI o1) {
		String[] segments0 = o0.segments();
		String[] segments1 = o1.segments();
		for(int i = 0; i < Math.min(segments0.length, segments1.length); ++i) {
			int compareToIgnoreCase = segments0[i].compareToIgnoreCase(segments1[i]);
			if(compareToIgnoreCase != 0)
				return compareToIgnoreCase;
		}
		int diff = segments0.length - segments1.length;
		if(diff != 0)
			return diff;

		String f0 = o0.fragment();
		String f1 = o1.fragment();
		if(f0 == null && f1 == null)
			return diff;
		f0 = f0 == null
				? ""
				: f0;
		f1 = f1 == null
				? ""
				: f1;
		return f0.compareTo(f1);

	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		Integer result = comparator.invoke(e1, e2);
		return result == null
				? super.compare(viewer, e1, e2)
				: result;
	}
}
