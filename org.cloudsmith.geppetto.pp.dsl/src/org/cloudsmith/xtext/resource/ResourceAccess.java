/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.xtext.resource;

import org.eclipse.emf.common.util.URI;

/**
 * A ResourceContext contains the instances created in a Guice scope as well as a reference
 * to a Resource URI.
 * 
 */
public final class ResourceAccess {

	private final URI resourceURI;

	public ResourceAccess(URI uri) {
		// null is ok
		this.resourceURI = uri;
	}

	/**
	 * Returns null for a "global", non resource specific context.
	 * 
	 * @return
	 */
	public URI getResourceURI() {
		return resourceURI;
	}

	/**
	 * @return true if this resource access has a reference to a resource
	 */
	public boolean isResourceSpecific() {
		return resourceURI != null;
	}

}
