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
package org.cloudsmith.geppetto.pp.dsl.ui.linking;

import org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath;
import org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPathProvider;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.PPPreferenceConstants;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Inject;

/**
 * A Puppet SearchPathProvider based on preferences.
 * 
 */

public class PPUISearchPathProvider extends PPSearchPathProvider {
	@Inject
	IPreferenceStoreAccess preferenceStoreAccess;

	@Inject
	private IAllContainersState allContainers;

	@Inject
	private IWorkspace workspace;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPathProvider#get(org.eclipse.emf.ecore.resource.Resource)
	 */
	@Override
	public PPSearchPath get(Resource r) {
		String handle = allContainers.getContainerHandle(r.getURI());
		// get project
		IProject project = workspace.getRoot().getProject(handle);

		// get project specific preference and use them if they are enabled
		IPreferenceStore store = preferenceStoreAccess.getContextPreferenceStore(project);
		String pathString = store.getString(PPPreferenceConstants.PUPPET_PROJECT_PATH);
		String environment = store.getString(PPPreferenceConstants.PUPPET_ENVIRONMENT);

		// if no path at all specified, the PPSearchPath enforces a default of "*"
		PPSearchPath searchPath = PPSearchPath.fromString(pathString, null);
		// if environment is still empty
		if(environment != null)
			environment = environment.trim();
		if(environment == null || environment.length() == 0)
			environment = "production";

		// System.err.printf("Project %s uses env=%s and path=%s\n", project.getName(), environment, pathString);
		// return a resolved search path
		return searchPath.evaluate(environment);
	}
}
