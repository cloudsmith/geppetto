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
package org.cloudsmith.xtext.ui.resource;

import org.cloudsmith.xtext.resource.AbstractResourceSpecificDataProvuder;
import org.cloudsmith.xtext.resource.IResourceSpecificDataProvider;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;

/**
 * An Eclipse Platform based implementation of {@link IResourceSpecificDataProvider}.
 * 
 */
public abstract class PlatformAwareResourceSpecificDataProvider<T> extends AbstractResourceSpecificDataProvuder<T> {

	/**
	 * A derived implementation should lookup specific data of type T for the given IResource. The implementation
	 * may return some default value, but can also return null (thus causing the given {@code defaultValue} passed
	 * in a call to {@link #get(URI, Object)} to be returned.
	 * 
	 * @param resource
	 *            the Eclipse platform resource
	 * @return data of type T or null
	 */
	abstract protected T dataForResource(IResource resource);

	protected IResource findPlatformResource(URI uri) {
		if(uri.isPlatformResource()) {
			String pathString = uri.toPlatformString(true);
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
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
		// Not a resource URL, a derived implementation may do better
		return null;
	}

	@Override
	abstract public T get();

	@Override
	public T get(URI uri, T defaultValue) {
		IResource resource = findPlatformResource(uri);
		if(resource == null)
			return defaultValue;

		T data = dataForResource(resource);
		return data == null
				? defaultValue
				: data;
	}

}
