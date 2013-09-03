/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.validation.runner;

import java.util.List;

import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.containers.StateBasedContainerManager;

import com.google.inject.Inject;

/**
 * A container manager that allows visible containers to be obtained from a container handle.
 * 
 */
public class ValidationStateBasedContainerManager extends StateBasedContainerManager {
	@Inject
	private IAllContainersState.Provider stateProvider;

	/**
	 * Returns the container handle - (normally an internal handle, but in PP validation, this handle is known to be the
	 * module path).
	 * 
	 * @param desc
	 * @param resourceIndex
	 * @return
	 */
	public String getContainerHandle(IResourceDescription desc, IResourceDescriptions resourceIndex) {
		return internalGetContainerHandle(desc, resourceIndex);
	}

	/**
	 * This method exists since prior to Xtext 2.3 it was not possible to obtain the stateProvider. When hammer has a
	 * min requirement on Xtext 2.3, the implementation of this class can be simplified by removing this method and
	 * instead calling the public getState(IResourceDescription) on the superclass, and also removing the extra injected
	 * private {@link #stateProvider} in this class.
	 * 
	 * TODO: See https://am0.cloudsmith.com/bugzilla/show_bug.cgi?id=2614 and comment above.
	 * 
	 * @param resourceDescriptions
	 * @return
	 */
	private IAllContainersState getPrivateState(IResourceDescriptions resourceDescriptions) {
		return stateProvider.get(resourceDescriptions);
	}

	/* Returns a list of IContainer visible from the given containerHandle. */
	public List<IContainer> getVisibleContainers(String containerHandle, IResourceDescriptions resourceDescriptions) {
		List<String> handles = getPrivateState(resourceDescriptions).getVisibleContainerHandles(containerHandle);
		List<IContainer> result = getVisibleContainers(handles, resourceDescriptions);
		return result;
	}
}
