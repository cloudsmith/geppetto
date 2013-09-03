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
package com.puppetlabs.geppetto.pp.dsl.ui.container;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.forge.v2.model.Dependency;
import com.puppetlabs.geppetto.forge.v2.model.Metadata;
import com.puppetlabs.geppetto.forge.v2.model.ModuleName;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;
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

	@Inject
	private Forge forge;

	/**
	 * Returns the best matching project (or null if there is no match) among the projects in the
	 * workspace.
	 * A translation is made from "/" to "-" in the separators in dependencies. (Should be checked elsewhere).
	 * 
	 * @param d
	 * @return
	 */
	protected IProject getBestMatchingProject(Dependency d) {
		ModuleName name = d.getName();
		if(name == null)
			return null;
		// Names with "/" are not allowed
		name = name.withSeparator('-');
		String namepart = name + "-";
		BiMap<IProject, Version> candidates = HashBiMap.create();
		int len = namepart.length();

		for(IProject p : getWorkspaceRoot().getProjects()) {
			String n = p.getName();
			if(n.startsWith(namepart) && n.length() > len && isAccessibleXtextProject(p)) {
				try {
					candidates.put(p, Version.create(p.getName().substring(len)));
				}
				catch(IllegalArgumentException e) {
					// Project name does not end with a valid version. Just skip it
				}
			}
		}
		if(candidates.isEmpty())
			return null;

		VersionRange vr = d.getVersionRequirement();
		if(vr == null)
			vr = VersionRange.ALL_INCLUSIVE;
		Version best = vr.findBestMatch(candidates.values());
		return candidates.inverse().get(best);
	}

	public List<String> getVisibleProjectNames(IProject project) {
		if(isAccessibleXtextProject(project)) {
			File moduleDir = project.getLocation().toFile();
			List<String> result = Lists.newArrayList();

			// parse the "Modulefile/metadata.json" and get full name and version, use this as name of target entry
			// TODO: Improve this to report diagnostics
			try {
				Diagnostic diag = new Diagnostic();
				Metadata metadata = forge.createFromModuleDirectory(moduleDir, false, null, null, diag);
				if(metadata != null) {
					for(Dependency d : metadata.getDependencies()) {
						IProject best = getBestMatchingProject(d);
						if(best != null)
							result.add(best.getName());
						else {
							// TODO: need to inform the user about this somehow, but can't create markers here
						}
					}
				}
			}
			catch(Exception e) {
				if(log.isDebugEnabled())
					log.debug("Could not parse any metadata from project: '" + project.getName() + "'", e);
			}
			return result;
		}
		return Collections.emptyList();
	}

	protected IWorkspaceRoot getWorkspaceRoot() {
		return workspace.getRoot();
	}

	/**
	 * Initializes visible handles from PP 'Modulefile/metadata.json' manifests. In case of errors, a partially
	 * parsed result is returned and error is logged.
	 * 
	 * @param handle
	 * @return
	 */
	public List<String> initVisibleHandles(String handle) {
		IProject project = getWorkspaceRoot().getProject(handle);
		return getVisibleProjectNames(project);
	}

	protected boolean isAccessibleXtextProject(IProject p) {
		return p != null && XtextProjectHelper.hasNature(p);
	}

	public void setWorkspace(IWorkspace workspace) {
		this.workspace = workspace;
	}
}
