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
 * An interface for obtaining typed resource specific data (e.g. preferences), that can be implemented
 * by static, stand alone, or Eclipse project aware providers.
 * 
 */
public interface IResourceSpecificDataProvider<T> {

	/**
	 * Produces default data of type T.
	 * 
	 * @return {@code T} default value
	 */
	public T get();

	/**
	 * <p>
	 * Shorthand for calling {@link #get(URI)} with {@code semantic.eResource().getURI()}
	 * </p>
	 * 
	 * @throws IllegalArgumentException
	 *             if the semantic object is not in a resource.
	 */
	public T get(EObject semantic);

	/**
	 * <p>
	 * Shorthand for calling {@link #get(URI, Object))} with {@code semantic.eResource().getURI()} as the first argument.
	 * </p>
	 * 
	 * @throws IllegalArgumentException
	 *             if the semantic object is not in a resource.
	 */
	public T get(EObject semantic, T defaultValue);

	/**
	 * <p>
	 * Shorthand for calling {@link #get(Resource)} with {@code eResource().getURI()}
	 * </p>
	 */
	public T get(Resource resource);

	/**
	 * <p>
	 * Shorthand for calling {@link #get(URI, Object))} with {@code eResource().getURI()} as the first argument.
	 * </p>
	 * 
	 * @throws IllegalArgumentException
	 *             if the semantic object is not in a resource.
	 */
	public T get(Resource resource, T defaultValue);

	/**
	 * <p>
	 * Produces data specific to the given resource denoted by {@link URI}. This resource may, or may not exist.
	 * </p>
	 * <p>
	 * If no resource specific data is found the default-default data is returned, i.e. the same as calling {@code get(uri, get())}
	 * </p>
	 * 
	 * @param uri
	 *            reference to a resource - may or may not exist
	 * @return type T resource specific data, or the result of calling {@link #get()} if no specific data exists.
	 */
	public T get(URI uri);

	/**
	 * <p>
	 * Produces data specific to the given resource denoted by {@link URI}. This resource may, or may not exist.
	 * </p>
	 * <p>
	 * If no resource specific data is found the given {@code defaultValue} is returned.
	 * </p>
	 * 
	 * @param uri
	 *            reference to the resource - may or may not exist
	 * @param defaultValue
	 *            returned if no specific data exists
	 * @return type T resource specific data, or the {@code defaultValue} if no specific data exists.
	 */
	public T get(URI uri, T defaultValue);

}
