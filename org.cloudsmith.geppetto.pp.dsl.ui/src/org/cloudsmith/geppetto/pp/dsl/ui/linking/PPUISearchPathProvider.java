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
 * @author henrik
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
		boolean pathStringEnabled = store.getBoolean(PPPreferenceConstants.PUPPET_PROJECT_PATH__ENABLED);
		boolean environmentEnabled = store.getBoolean(PPPreferenceConstants.PUPPET_ENVIRONMENT__ENABLED);
		String pathString = !pathStringEnabled
				? null
				: store.getString(PPPreferenceConstants.PUPPET_PROJECT_PATH);
		String environment = !environmentEnabled
				? null
				: store.getString(PPPreferenceConstants.PUPPET_ENVIRONMENT);

		// get global preferences if needed
		boolean checkDeaultPath = pathString == null || pathString.length() == 0;
		boolean checkDefaultEnv = environment == null || environment.length() == 0;
		if(checkDeaultPath || checkDefaultEnv) {
			store = preferenceStoreAccess.getPreferenceStore();
			if(checkDeaultPath)
				pathString = store.getString(PPPreferenceConstants.PUPPET_PROJECT_PATH);
			if(checkDefaultEnv)
				environment = store.getString(PPPreferenceConstants.PUPPET_ENVIRONMENT);
		}
		// Enforce sane default in case of faolure to get preferences
		// if nothing so far, return a global "everything" path
		if(pathString == null || pathString.length() == 0)
			pathString = "*";
		PPSearchPath searchPath = PPSearchPath.fromString(pathString, null);
		// if environment is still empty
		if(environment == null || environment.length() == 0)
			environment = "production";

		// return a resolved search path
		return searchPath.evaluate(environment);
	}
}
