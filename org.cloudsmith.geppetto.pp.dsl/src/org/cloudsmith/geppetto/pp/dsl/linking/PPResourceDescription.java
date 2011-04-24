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
package org.cloudsmith.geppetto.pp.dsl.linking;

import java.util.Collection;

import org.cloudsmith.geppetto.pp.dsl.adapters.PPImportedNamesAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.PPImportedNamesAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.impl.DefaultResourceDescription;
import org.eclipse.xtext.util.IResourceScopeCache;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

/**
 * A ResourceDescription for PP that adds specially imported names via resource adapter.
 * 
 */
public class PPResourceDescription extends DefaultResourceDescription {

	public PPResourceDescription(Resource resource, IDefaultResourceDescriptionStrategy strategy,
			IResourceScopeCache cache) {
		super(resource, strategy, cache);
	}

	/**
	 * Override that adds the specially imported names to the default.
	 * 
	 * @see org.eclipse.xtext.resource.impl.DefaultResourceDescription#getImportedNames()
	 */
	@Override
	public Iterable<QualifiedName> getImportedNames() {
		Iterable<QualifiedName> superResult = super.getImportedNames();
		PPImportedNamesAdapter adapter = PPImportedNamesAdapterFactory.eINSTANCE.adapt(getResource());
		if(adapter != null) {
			Collection<QualifiedName> imported = adapter.getNames();
			if(imported.isEmpty())
				return superResult;
			return Iterables.concat(superResult, ImmutableSet.copyOf(imported));
		}
		return superResult;
	}

}
