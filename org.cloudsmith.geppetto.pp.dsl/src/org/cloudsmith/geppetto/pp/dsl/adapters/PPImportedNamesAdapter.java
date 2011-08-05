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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * The PPImportedNamesAdapter is used to record information about:
 * <ul>
 * <li>"imported" (x-referenced) names that have been searched in order to find a resolution.</li>
 * <li>a set of resolutions (i.e. IEObjectDescriptions) that have been used to resolve an x-reference</li>
 * <li>a set of unresolved named</li>
 * </ul>
 * 
 * @TODO: consider changing the name of this class as it handls more that just "imported names".
 */
public class PPImportedNamesAdapter extends AdapterImpl {

	private final static List<QualifiedName> EMPTY = Collections.emptyList();

	private final static Set<IEObjectDescription> EMPTY_DESCSET = Collections.emptySet();

	private final static Set<QualifiedName> EMPTY_NAMESET = Collections.emptySet();

	List<QualifiedName> importedNames;

	Set<QualifiedName> unresolvedNames;

	Set<IEObjectDescription> resolvedDescriptions;

	Set<IEObjectDescription> ambigousDescriptions;

	/**
	 * Adds the given name to the set of searched names (names that influence the resolution).
	 * 
	 * @param name
	 */
	public void add(QualifiedName name) {
		synchronized(this) {
			if(importedNames == null)
				importedNames = Lists.newArrayList();
		}
		importedNames.add(name);
	}

	/**
	 * Adds a collection of ambiguous references to the set of x-references found to be ambiguous.
	 * 
	 * @param ambiguities
	 */
	public void addAmbiguous(Collection<IEObjectDescription> ambiguities) {
		synchronized(this) {
			if(ambigousDescriptions == null)
				ambigousDescriptions = Sets.newHashSet();
		}
		ambigousDescriptions.addAll(ambiguities);
	}

	/**
	 * Adds a single ambiguity to the set of x-references found to be ambiguous.
	 * 
	 * @param ambiguities
	 */
	public void addAmbiguous(IEObjectDescription ambiguity) {
		addAmbiguous(Sets.newHashSet(ambiguity));
	}

	/**
	 * Adds a collection of resolutions to the set of resolved x-references.
	 * 
	 * @param resolved
	 */
	public void addResolved(Collection<IEObjectDescription> resolved) {
		synchronized(this) {
			if(resolvedDescriptions == null)
				resolvedDescriptions = Sets.newHashSet();
		}
		resolvedDescriptions.addAll(resolved);
	}

	/**
	 * Adds a single resolution to the set of resolved x-references.
	 * 
	 * @param desc
	 */
	public void addResolved(IEObjectDescription desc) {
		addResolved(Sets.newHashSet(desc));
	}

	/**
	 * Adds a collection of names to the set of unresolved names.
	 * 
	 * @param unresolved
	 */
	public void addUnresolved(Collection<QualifiedName> unresolved) {
		synchronized(this) {
			if(unresolvedNames == null)
				unresolvedNames = Sets.newHashSet();
		}
		unresolvedNames.addAll(unresolved);
	}

	/**
	 * Adds a single name to the set of unresolved names.
	 * 
	 * @param name
	 */
	public void addUnresolved(QualifiedName name) {
		addUnresolved(Sets.newHashSet(name));
	}

	public void clear() {
		importedNames = null;
		unresolvedNames = null;
		resolvedDescriptions = null;
	}

	/**
	 * @return the ambiguous IEObjectDescriptions or an empty Collection if there are no ambiguous x-references.
	 */
	public Collection<IEObjectDescription> getAmbiguousDescriptions() {
		return Collections.unmodifiableSet(ambigousDescriptions != null
				? ambigousDescriptions
				: EMPTY_DESCSET);
	}

	/**
	 * @return the imported QualifiedNames or an empty list if there are no imported names.
	 */
	public List<QualifiedName> getNames() {
		return Collections.unmodifiableList(importedNames != null
				? importedNames
				: EMPTY);
	}

	/**
	 * @return the resolved IEObjectDescriptions or an empty Collection if there are no resolved x-references.
	 */
	public Collection<IEObjectDescription> getResolvedDescriptions() {
		return Collections.unmodifiableSet(resolvedDescriptions != null
				? resolvedDescriptions
				: EMPTY_DESCSET);
	}

	/**
	 * @return the unresolved names or an empty Collection if there are no unresolved x-references.
	 */
	public Collection<QualifiedName> getUnresolvedNames() {
		return Collections.unmodifiableSet(unresolvedNames != null
				? unresolvedNames
				: EMPTY_NAMESET);
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == PPImportedNamesAdapter.class;
	}
}
