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

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.cloudsmith.geppetto.forge.Dependency;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.Metadata;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.containers.AbstractStorage2UriMapperClient;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Helper for Puppet Projects.
 * 
 */
public class PPWorkspaceProjectsStateHelper extends AbstractStorage2UriMapperClient {

	private final static Logger log = Logger.getLogger(PPWorkspaceProjectsStateHelper.class);

	@Inject
	private IWorkspace workspace;

	/**
	 * Returns the best matching project (or null if there is no match) among the projects in the
	 * workspace.
	 * A translation is made from "/" to "-" in the separators in dependencies. (Should be checked elsewhere).
	 * 
	 * @param d
	 * @return
	 */
	protected IProject getBestMatchingProject(Dependency d) {
		String name = d.getName();
		// Names with "/" are not allowed
		name = name.replace("/", "-");
		if(name == null || name.isEmpty())
			return null;
		String namepart = name + "-";
		BiMap<IProject, String> candidates = HashBiMap.create();
		int len = namepart.length();

		for(IProject p : getWorkspaceRoot().getProjects()) {
			String n = p.getName();
			if(n.startsWith(name + "-") && n.length() > len && isAccessibleXtextProject(p))
				candidates.put(p, p.getName().substring(len));
		}
		if(candidates.isEmpty())
			return null;

		String best = d.getVersionRequirement().findBestMatch(candidates.values());
		return candidates.inverse().get(best);
	}

	protected IWorkspaceRoot getWorkspaceRoot() {
		return workspace.getRoot();
	}

	/**
	 * Initializes visible handles from PP 'Modulefile' manifests. In case of errors, a partially
	 * parsed result is returned and error is logged.
	 * 
	 * @param handle
	 * @return
	 */
	public List<String> initVisibleHandles(String handle) {
		IProject project = getWorkspaceRoot().getProject(handle);
		if(isAccessibleXtextProject(project)) {
			IFile moduleFile = project.getFile("Modulefile");
			if(moduleFile.exists()) {
				List<String> result = Lists.newArrayList();

				// parse the "Modulefile" and get full name and version, use this as name of target entry
				try {
					Metadata metadata = ForgeFactory.eINSTANCE.createMetadata();
					metadata.loadModuleFile(moduleFile.getLocation().toFile());

					for(Dependency d : metadata.getDependencies()) {
						IProject best = getBestMatchingProject(d);
						if(best != null)
							result.add(best.getName());
						else {
							// TODO: need to inform the user about this somehow, but can't create markers here
						}
					}

				}
				catch(Exception e) {
					if(log.isDebugEnabled())
						log.debug("Could not parse Modulefile dependencies: '" + moduleFile + "'", e);
				}
				return result;
			}
		}
		return Collections.emptyList();
	}

	protected boolean isAccessibleXtextProject(IProject p) {
		return p != null && XtextProjectHelper.hasNature(p);
	}

	public void setWorkspace(IWorkspace workspace) {
		this.workspace = workspace;
	}
}
