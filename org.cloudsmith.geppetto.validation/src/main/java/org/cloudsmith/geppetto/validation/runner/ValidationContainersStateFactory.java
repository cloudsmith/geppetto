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

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.containers.IAllContainersState;

import com.google.common.collect.Multimap;

/**
 * Computes the containers state
 * 
 */
public class ValidationContainersStateFactory {

	public IAllContainersState getContainersState(List<String> handles, Multimap<String, URI> uris,
			Multimap<String, String> restricted) {
		ValidationAllContainersState containersState = new ValidationAllContainersState();
		containersState.configure(handles, uris, restricted);
		return containersState;
	}

}
