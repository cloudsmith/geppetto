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
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * The PPImportedNamesAdapter is used to record information about:
 * <ul>
 * <li>"imported" (x-referenced) names that have been searched in order to find a resolution.</li>
 * <li>a set of resolutions (i.e. IEObjectDescriptions) that have been used to resolve an x-reference</li>
 * <li>a set of unresolved names</li>
 * </ul>
 * 
 * @TODO: consider changing the name of this class as it handls more that just "imported names".
 */
public class PPImportedNamesAdapter extends AdapterImpl {

	public static class Location {
		private int line;

		private int offset;

		private int length;

		Location(int line, int offset, int length) {
			this.line = line;
			this.offset = offset;
			this.length = length;
		}

		/**
		 * @return the length
		 */
		public int getLength() {
			return length;
		}

		/**
		 * @return the line
		 */
		public int getLine() {
			return line;
		}

		/**
		 * @return the offset
		 */
		public int getOffset() {
			return offset;
		}
	}

	private final static List<QualifiedName> EMPTY = Collections.emptyList();

	private final static Set<IEObjectDescription> EMPTY_DESCSET = Collections.emptySet();

	private final static Set<QualifiedName> EMPTY_NAMESET = Collections.emptySet();

	private final static Map<QualifiedName, List<Location>> EMPTY_UNRESOLVED = Collections.emptyMap();

	Map<QualifiedName, List<Location>> unresolvedNames;

	List<QualifiedName> importedNames;

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
	 * Adds a single name to the set of unresolved names.
	 * 
	 * @param name
	 */
	public void addUnresolved(QualifiedName name, int line, int offset, int length) {
		synchronized(this) {
			if(unresolvedNames == null)
				unresolvedNames = Maps.newHashMap(); // Sets.newHashSet();
			if(unresolvedNames.get(name) == null)
				unresolvedNames.put(name, Lists.<Location> newArrayList());
		}
		unresolvedNames.get(name).add(new Location(line, offset, length));
	}

	// /**
	// * Adds a collection of names to the set of unresolved names.
	// *
	// * @param unresolved
	// */
	// private void addxUnresolved(Collection<QualifiedName> unresolved) {
	// synchronized(this) {
	// if(unresolvedNames == null)
	// unresolvedNames = Maps.newHashMap(); // Sets.newHashSet();
	// }
	// unresolvedNames.addAll(unresolved);
	// }

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
	 * Returns all unresolved information in an unmodifiable map. The keys are the qualified name
	 * of an unresolved element, and there is one or more Locations describing where the qualified name
	 * is found in the resource.
	 * 
	 * @return
	 */
	public Map<QualifiedName, List<Location>> getUnresolved() {
		return unresolvedNames == null
				? EMPTY_UNRESOLVED
				: Collections.unmodifiableMap(unresolvedNames);
	}

	/**
	 * @return the unresolved names or an empty Collection if there are no unresolved x-references.
	 */
	public Collection<QualifiedName> getUnresolvedNames() {
		return Collections.unmodifiableSet(unresolvedNames != null
				? unresolvedNames.keySet()
				: EMPTY_NAMESET);
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type == PPImportedNamesAdapter.class;
	}
}
