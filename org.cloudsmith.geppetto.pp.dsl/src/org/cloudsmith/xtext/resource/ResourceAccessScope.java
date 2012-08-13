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
import org.eclipse.emf.ecore.resource.Resource;

import com.google.common.base.Preconditions;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * <p>
 * A {@code ResourceAccessScope} acts as a provider of {@link ResourceAccess} returning an instance that has been bound to the current {@link Thread},
 * or a default "unspecific" ResourceAccess if the ResourceAccessScope has not been entered.
 * </p>
 * <p>
 * This works similar to how a Guice Scope works, but this implementation is NOT a guice scope.
 * </p>
 * <p>
 * When entering, care must be taken to exit the scope in a finally-clause or the result may be memory leaks
 * </p>
 * 
 */
@Singleton
public class ResourceAccessScope implements Provider<ResourceAccess> {

	private final ThreadLocal<ResourceAccess> context;

	private static final ResourceAccess unspecific = new ResourceAccess(null);

	public ResourceAccessScope() {
		this.context = new ThreadLocal<ResourceAccess>();
	}

	/**
	 * Enters a resource specific scope. The given {@code} resource may not be null, and the resource
	 * must have an URI set.
	 * 
	 * @param resource
	 *            the Resource to enter
	 */
	public void enter(Resource resource) {
		Preconditions.checkNotNull(resource, "resource");
		URI uri = resource.getURI();
		Preconditions.checkArgument(uri != null, "Resource.getURI() can not be null.");
		enter(uri);
	}

	/**
	 * Enters a resource specific scope.
	 * 
	 * @throws IllegalStateException
	 *             if the scope is already entered.
	 * @param uri
	 */
	public void enter(URI uri) {
		Preconditions.checkNotNull(uri, "uri");
		if(this.context.get() != null)
			throw new IllegalStateException("ResourceAccessScope already entered!");
		this.context.set(new ResourceAccess(uri));
	}

	public void exit() {
		if(this.context.get() == null)
			throw new IllegalStateException("ResourceAccessScope was not entered!");
		context.remove();
	}

	@Override
	public ResourceAccess get() {
		ResourceAccess ra = context.get();
		return ra == null
				? unspecific
				: ra;
	}

}
