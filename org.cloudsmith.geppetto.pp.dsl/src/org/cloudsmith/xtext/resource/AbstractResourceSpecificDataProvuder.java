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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * An abstract provider of resource specific data of type T. An derived class only needs to
 * implement {@link #get()} to return default-default values, and the method {@link #get(URI, Object)},
 * but may implement the other methods.
 * 
 */
public abstract class AbstractResourceSpecificDataProvuder<T> implements IResourceSpecificDataProvider<T> {

	@Override
	abstract public T get();

	@Override
	public T get(EObject semantic) {
		return get(semantic, get());
	}

	@Override
	public T get(EObject semantic, T defaultValue) {
		if(semantic == null || semantic.eResource() == null)
			throw new IllegalArgumentException("semantic can not be null, and must be in a Resource");
		return get(semantic.eResource().getURI(), defaultValue);
	}

	@Override
	public T get(Resource resource) {
		return get(resource.getURI(), get());
	}

	@Override
	public T get(Resource resource, T defaultValue) {
		return get(resource.getURI(), defaultValue);
	}

	@Override
	public T get(URI uri) {
		return get(uri, get());
	}

	@Override
	abstract public T get(URI uri, T defaultValue);

}
