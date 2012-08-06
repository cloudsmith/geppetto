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
package org.cloudsmith.xtext.ui.resource;

import org.cloudsmith.xtext.dommodel.formatter.context.ResourceContext;
import org.cloudsmith.xtext.resource.ResourceAccess;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;
import org.eclipse.xtext.util.Pair;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * An Eclipse Platform based {@link Provider} implementation making use of the {@link ResourceContext} to provide
 * a {@link Resource} specific instance of {@code T} (or "global" if the context is not resource specific).
 * 
 */
public abstract class PlatformResourceSpecificProvider<T> implements Provider<T> {

	@Inject
	protected Provider<ResourceAccess> resourceAccessProvider;

	@Inject
	private IWorkspace workspace;

	@Inject
	private IStorage2UriMapper storage2UriMapper;

	/**
	 * A concrete implementation should lookup specific data of type {@code T} for the given IResource. If the given {@code resource} is null, the
	 * implementation should return a "global"/default {@code T}.
	 * 
	 * @param resource
	 *            the Eclipse platform resource, or null if {@link #getResourceURI()} is not a platform URI
	 * @return data of type T
	 */
	abstract protected T dataForResource(IResource resource);

	/**
	 * Finds the closest Platform {@link IResource} given an URI that is a platform {@link URI}, or an URI that
	 * an {@link IStorage2UriMapper} can map to an {@link IProject}.
	 * 
	 * @param uri
	 * @return IResource, or null, if the given uri can not be mapped to IResource.
	 */
	protected IResource findPlatformResource(URI uri) {

		if(uri != null) {
			if(uri.isPlatformResource()) {
				String pathString = uri.toPlatformString(true);
				IWorkspaceRoot root = workspace.getRoot();
				IResource r = root.findMember(pathString);

				// uri is on the form: platform:/resource/project-name/path
				// search up to project (inclusive) for an existing resource
				while(r == null && uri.segmentCount() > 1) {
					uri = uri.trimSegments(1);
					pathString = uri.toPlatformString(true);
					r = root.findMember(pathString);
				}
				return r;
			}
			for(Pair<IStorage, IProject> storage : storage2UriMapper.getStorages(uri)) {
				return storage.getSecond();
			}
		}
		// Not a resource URL, a derived implementation may do better
		return null;
	}

	@Override
	public T get() {
		// get the ResourceContext in effect (either a global context where the returned URI is
		// null or the URI of one resource.
		URI uri = resourceAccessProvider.get().getResourceURI();
		return dataForResource(findPlatformResource(uri));
	}
}
