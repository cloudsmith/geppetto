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

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.cloudsmith.geppetto.forge.Forge;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.containers.AbstractAllContainersState;
import org.eclipse.xtext.ui.containers.WorkspaceProjectsStateHelper;
import org.eclipse.xtext.util.Wrapper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A PP Workspace Projects State manager - makes use of the default implementation
 * for the Workspace (Project dependencies), as well as Puppet Manifests (Modulefile) parsing.
 * 
 */

@Singleton
public class PPWorkspaceProjectsState extends AbstractAllContainersState {
	private final static Logger log = Logger.getLogger(PPWorkspaceProjectsState.class);

	@Inject
	private WorkspaceProjectsStateHelper helper;

	@Inject
	private PPWorkspaceProjectsStateHelper manifestHelper;

	@Inject
	private Forge forge;

	protected boolean doesThisDeltaRequireClear(IResourceDelta delta) {
		return delta.getResource() instanceof IFile &&
				forge.isMetadataFile(delta.getProjectRelativePath().toPortableString());
	}

	@Override
	protected Collection<URI> doInitContainedURIs(String containerHandle) {
		// since the default holds all projects
		return helper.initContainedURIs(containerHandle);
	}

	@Override
	protected String doInitHandle(URI uri) {
		// since the default holds all projects
		return helper.initHandle(uri);
	}

	@Override
	protected List<String> doInitVisibleHandles(String handle) {
		// the manifest helper adds visibility
		return union(helper.initVisibleHandles(handle), manifestHelper.initVisibleHandles(handle));
	}

	public WorkspaceProjectsStateHelper getHelper() {
		return helper;
	}

	public PPWorkspaceProjectsStateHelper getManifestHelper() {
		return manifestHelper;
	}

	protected boolean isAccessibleXtextProject(IProject p) {
		return p != null && XtextProjectHelper.hasNature(p);
	}

	/**
	 * This method overrides the implementation in {@link AbstractAllContainersState} in order to
	 * add a hook method for the "Manifest". Reuse of logic in superclass is not good since it
	 * would potentially lead to multiple initialization for one and the same delta.
	 * See https://bugs.eclipse.org/bugs/show_bug.cgi?id=342092 - if this gets fixed, modify this
	 * implementation.
	 */
	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		if(event.getType() == IResourceChangeEvent.PRE_CLOSE || event.getType() == IResourceChangeEvent.PRE_DELETE) {
			initialize();
			return;
		}
		if(event.getDelta() != null) {
			IResourceDelta delta = event.getDelta();
			final Wrapper<Boolean> clear = Wrapper.wrap(Boolean.FALSE);
			try {
				delta.accept(new IResourceDeltaVisitor() {
					public boolean visit(IResourceDelta delta) throws CoreException {
						if(clear.get().booleanValue())
							return false;
						if(delta.getResource() != null && delta.getResource().isDerived())
							return false;
						if(delta.getKind() == IResourceDelta.ADDED || delta.getKind() == IResourceDelta.REMOVED) {
							if(delta.getResource() instanceof IStorage) {
								if(getUri((IStorage) delta.getResource()) != null) {
									clear.set(Boolean.TRUE);
									return false;
								}
							}
						}
						if(delta.getKind() == IResourceDelta.CHANGED && delta.getResource() instanceof IProject) {
							if((delta.getFlags() & IResourceDelta.DESCRIPTION) != 0) {
								clear.set(Boolean.TRUE);
								return false;
							}
						}
						// THIS IS ADDED TO ORIGINAL
						if(doesThisDeltaRequireClear(delta)) {
							clear.set(Boolean.TRUE);
							return false;
						}
						// ADDITION END
						return true;
					}
				});
				if(clear.get().booleanValue())
					initialize();
			}
			catch(CoreException e) {
				log.error(e.getMessage(), e);
				initialize();
			}
		}
	}

	public void setHelper(WorkspaceProjectsStateHelper helper) {
		this.helper = helper;
	}

	public void setManifestHelper(PPWorkspaceProjectsStateHelper manifestHelper) {
		this.manifestHelper = manifestHelper;
	}

	/**
	 * Produce the union of two lists
	 * 
	 * @param a
	 * @param b
	 * @return a U b
	 */
	private List<String> union(List<String> a, List<String> b) {
		List<String> theSmallerList = (a.size() < b.size())
				? a
				: b;
		List<String> theLargerList = (a.size() < b.size())
				? b
				: a;

		// can be optimized for empty cases and when lists are very small
		if(theSmallerList.isEmpty())
			return theLargerList;
		if(theLargerList.isEmpty())
			return theSmallerList;

		// TODO: small case - probably faster to just check if the larger list contains things from
		// the smaller list

		// Use hashsets as this is faster when there are more than a few entries
		// API doc state that it is faster to give the smaller list as the first argument to union
		return Lists.newArrayList(Sets.union(Sets.newHashSet(theSmallerList), Sets.newHashSet(theLargerList)));
	}
}
