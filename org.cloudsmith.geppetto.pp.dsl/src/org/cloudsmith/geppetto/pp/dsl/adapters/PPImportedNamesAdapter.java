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
package org.cloudsmith.geppetto.pp.dsl.adapters;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.common.collect.Lists;

/**
 * The PPImportedNamesAdapter associates a List of QualifiedNames (that are imported/wanted) in a resource.
 */
public class PPImportedNamesAdapter extends AdapterImpl {

	private final static List<QualifiedName> EMPTY = Collections.emptyList();

	List<QualifiedName> importedNames;

	public void add(QualifiedName name) {
		synchronized(this) {
			if(importedNames == null)
				importedNames = Lists.newArrayList();
		}
		importedNames.add(name);
	}

	public void clear() {
		importedNames = null;
	}

	/**
	 * @return the imported QualifiedNames or an empty list if there are no imported names.
	 */
	public List<QualifiedName> getNames() {
		return Collections.unmodifiableList(importedNames != null
				? importedNames
				: EMPTY);
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == PPImportedNamesAdapter.class;
	}
}
