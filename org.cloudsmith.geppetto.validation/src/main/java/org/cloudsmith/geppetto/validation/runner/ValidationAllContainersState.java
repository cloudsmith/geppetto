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
package org.cloudsmith.geppetto.validation.runner;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.containers.ResourceSetBasedAllContainersState;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

/**
 * This specialization of the default {@link ResourceSetBasedAllContainersState} adds the ability to use more than a single container.
 * 
 */
public class ValidationAllContainersState extends ResourceSetBasedAllContainersState {

	private ArrayListMultimap<String, String> restricted;

	/**
	 * This method is only here to throw exception when using the regular API.
	 * DO NOT USE - use {@link #configure(List, Multimap, Multimap)} instead.
	 */
	@Override
	@Deprecated
	public void configure(List<String> containers, Multimap<String, URI> container2Uris) {
		throw new UnsupportedOperationException("Call configure with 3 arguments instead");
	}

	public void configure(List<String> containers, Multimap<String, URI> container2Uris,
			Multimap<String, String> restrictedVisibility) {
		super.configure(containers, container2Uris);
		this.restricted = ArrayListMultimap.create(restrictedVisibility);
	}

	/**
	 * Returns the map of restricted container handles, a get(String handle)
	 * returns the set of visible container handles for the given handle. The
	 * handle should be a File path to a module directory, a path to the root
	 * (non modular content) of a repository, or the special string "_pptp".
	 * 
	 * @return
	 */
	public Multimap<String, String> getRestricted() {
		return Multimaps.unmodifiableMultimap(restricted);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.xtext.resource.containers.ResourceSetBasedAllContainersState
	 * #getVisibleContainerHandles(java.lang.String)
	 */
	@Override
	public List<String> getVisibleContainerHandles(String handle) {
		if(restricted.containsKey(handle))
			return Collections.unmodifiableList(restricted.get(handle));

		// super method returns all containers
		return super.getVisibleContainerHandles(handle);
	}
}
