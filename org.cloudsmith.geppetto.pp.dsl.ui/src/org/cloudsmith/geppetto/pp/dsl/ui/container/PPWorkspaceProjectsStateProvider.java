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
package org.cloudsmith.geppetto.pp.dsl.ui.container;

import org.eclipse.xtext.resource.containers.IAllContainersState;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 * A Provider of PPWorkspaceProjectsState
 * 
 */
public class PPWorkspaceProjectsStateProvider implements Provider<IAllContainersState> {

	@Inject
	Injector injector;

	// @Inject
	// private MembersInjector<PPWorkspaceProjectsState> membersInjector;

	@Override
	public IAllContainersState get() {
		return injector.getInstance(PPWorkspaceProjectsState.class);
		// PPWorkspaceProjectsState pp = new PPWorkspaceProjectsState();
		// membersInjector.injectMembers(pp);
		// return pp;
	}

}
