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

package org.cloudsmith.geppetto.pp.dsl.ui.builder;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.cloudsmith.geppetto.common.tracer.DefaultTracer;
import org.cloudsmith.geppetto.common.tracer.ITracer;
import org.cloudsmith.geppetto.forge.Dependency;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.pp.dsl.ui.PPUiConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.util.Wrapper;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;

/**
 * Builder of Modulefile that sets the dependencies on the project as dynamic project references
 * 
 */

public class PPModulefileBuilder extends IncrementalProjectBuilder {
	private final static Logger log = Logger.getLogger(PPModulefileBuilder.class);

	private static final IPath MODULEFILE_PATH = new Path("Modulefile");

	private static final String PUPPET_MODULE_PROBLEM_MARKER = "org.cloudsmith.geppetto.pp.dsl.ui.puppetModuleProblem";

	public static final String BUILDER_ID = "org.cloudsmith.geppetto.pp.dsl.ui.modulefileBuilder";

	private ITracer tracer;

	private static final QualifiedName versionKey = new QualifiedName(PPUiConstants.PLUGIN_ID, "version");

	public PPModulefileBuilder() {
		// Hm, can not inject this because it was not possible to inject this builder via the
		// executable extension factory
		tracer = new DefaultTracer(PPUiConstants.DEBUG_OPTION_MODULEFILE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int, java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IProject[] build(int kind, @SuppressWarnings("rawtypes") Map args, IProgressMonitor monitor)
			throws CoreException {
		checkCancel(monitor);

		IResourceDelta delta = getDelta(getProject());
		if(delta == null)
			fullBuild(monitor);
		else
			incrementalBuild(delta, monitor);
		return null;
	}

	private void checkCancel(IProgressMonitor monitor) throws OperationCanceledException {
		if(monitor.isCanceled())
			throw new OperationCanceledException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#clean(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		removeErrorMarkers();
		// potentially remove the dynamic project references, but this will probably trigger project reconfig
		// better to wait until the sync as they are probably still the same
	}

	protected void createMarker(IResource r, String message, Dependency d) {
		try {
			IMarker m = r.createMarker(PUPPET_MODULE_PROBLEM_MARKER);
			m.setAttribute(IMarker.MESSAGE, message);
			m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
			m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
			if(d != null)
				m.setAttribute(IMarker.LOCATION, d.getName() + d.getVersionRequirement().toString());
		}
		catch(CoreException e) {
			log.error("Could not create error marker or set its attributes for a 'Modulefile'", e);
		}
	}

	private void fullBuild(IProgressMonitor monitor) {
		removeErrorMarkers();
		syncModulefile(monitor);
	}

	/**
	 * Returns the best matching project (or null if there is no match) among the projects in the
	 * workspace.
	 * A translation is made from "/" to "-" in the separators in dependencies. (Should be checked elsewhere).
	 * 
	 * @param d
	 * @return
	 */
	protected IProject getBestMatchingProject(Dependency d, IProgressMonitor monitor) {
		// Names with "/" are not allowed
		final String name = d.getName().replace("/", "-");
		if(name == null || name.isEmpty())
			return null;

		BiMap<IProject, String> candidates = HashBiMap.create();

		final String namepart = name + "-";
		final int len = namepart.length();
		for(IProject p : getWorkspaceRoot().getProjects()) {
			checkCancel(monitor);
			String n = p.getName();

			// new style
			String version = null;
			boolean newStyleMatch = false;
			boolean oldStyleMatch = false;
			if(n.equals(name))
				newStyleMatch = true;
			else if(n.startsWith(name + "-") && n.length() > len)
				oldStyleMatch = true;

			// in both old and new style match, get the version from the persisted property
			if(newStyleMatch || oldStyleMatch) {
				if(!isAccessibleXtextProject(p))
					continue; // meaningless to add, does not have the right nature, will not be built
				try {
					version = p.getPersistentProperty(versionKey);
				}
				catch(CoreException e) {
					log.error("Error while getting version from project", e);
				}
				if(version == null)
					version = "0";
				candidates.put(p, version);
			}
			// // old style, name and version in project name
			// else if(n.startsWith(name + "-") && n.length() > len && isAccessibleXtextProject(p))
			// candidates.put(p, p.getName().substring(len));
		}
		if(candidates.isEmpty())
			return null;

		// find best version and do a lookup of project
		String best = d.getVersionRequirement().findBestMatch(candidates.values());
		if(best == null || best.length() == 0)
			return null;

		return candidates.inverse().get(best);
	}

	protected IWorkspaceRoot getWorkspaceRoot() {
		return getProject().getWorkspace().getRoot();
	}

	private void incrementalBuild(IResourceDelta delta, final IProgressMonitor monitor) {

		final Wrapper<Boolean> buildFlag = Wrapper.wrap(Boolean.FALSE);

		try {
			delta.accept(new IResourceDeltaVisitor() {

				public boolean visit(IResourceDelta delta) throws CoreException {
					checkCancel(monitor);

					if(buildFlag.get())
						return false; // already convinced we should build
					if(delta.getResource() != null && delta.getResource().isDerived())
						return false;

					// irrespective of how the Modulefile was changed, run the build (i.e. sync).
					if(isModulefileDelta(delta))
						buildFlag.set(true);

					// continue scanning the delta tree
					return true;
				}
			});
			if(buildFlag.get())
				syncModulefile(monitor);
		}
		catch(CoreException e) {
			log.error(e.getMessage(), e);
			syncModulefile(monitor);
		}
	}

	/**
	 * TODO: Should check for the Puppet Nature instead of Xtext
	 * 
	 * @param p
	 * @return
	 */
	protected boolean isAccessibleXtextProject(IProject p) {
		return p != null && XtextProjectHelper.hasNature(p);
	}

	protected boolean isModulefileDelta(IResourceDelta delta) {
		return ((delta.getResource() instanceof IFile) && MODULEFILE_PATH.equals(delta.getProjectRelativePath()));
	}

	public Metadata loadMetadata(IFile moduleFile, IProgressMonitor monitor) {
		// parse the "Modulefile" and get full name and version, use this as name of target entry
		try {
			Metadata metadata = ForgeFactory.eINSTANCE.createMetadata();
			metadata.loadModuleFile(moduleFile.getLocation().toFile());
			return metadata;
		}
		catch(Exception e) {
			createMarker(moduleFile, "Can not parse modulefile: " + e.getMessage(), null);
			if(log.isDebugEnabled())
				log.debug("Could not parse Modulefile dependencies: '" + moduleFile + "'", e);
		}
		return null;

	}

	/**
	 * Deletes all problem markers set by this builder.
	 */
	protected void removeErrorMarkers() {
		IFile m = getProject().getFile(MODULEFILE_PATH);
		try {
			m.deleteMarkers(PUPPET_MODULE_PROBLEM_MARKER, true, IResource.DEPTH_ZERO);
		}
		catch(CoreException e) {
			// nevermind, the resource may not even be there...
			// meaningless to have elaborate existence checks etc... (unless there is lots of
			// ugly logging
		}
	}

	/**
	 * Resolves the dependencies against projects in the workspace.
	 * Sets error markers when there are unresolved dependencies.
	 * 
	 * @param handle
	 * @return
	 */
	public List<IProject> resolveDependencies(Metadata metadata, IFile moduleFile, IProgressMonitor monitor) {
		List<IProject> result = Lists.newArrayList();

		// parse the "Modulefile" and get full name and version, use this as name of target entry
		try {
			// Metadata metadata = ForgeFactory.eINSTANCE.createMetadata();
			// metadata.loadModuleFile(moduleFile.getLocation().toFile());

			for(Dependency d : metadata.getDependencies()) {
				checkCancel(monitor);
				IProject best = getBestMatchingProject(d, monitor);
				if(best != null)
					result.add(best);
				else {
					createMarker(
						moduleFile,
						"Unresolved dependency :'" + d.getName() + "' version: " + d.getVersionRequirement(), d);
				}
			}
		}
		catch(Exception e) {
			if(log.isDebugEnabled())
				log.debug("Error while resolving Modulefile dependencies: '" + moduleFile + "'", e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#startupOnInitialize()
	 */
	@Override
	protected void startupOnInitialize() {
		// TODO Auto-generated method stub
		super.startupOnInitialize();
	}

	private void syncModulefile(final IProgressMonitor monitor) {
		if(tracer.isTracing())
			tracer.trace("Syncing modulefile with project");

		checkCancel(monitor);
		IProject project = getProject();
		if(isAccessibleXtextProject(project)) {
			IFile moduleFile = project.getFile("Modulefile");
			if(moduleFile.exists()) {

				// get metadata
				Metadata metadata = loadMetadata(moduleFile, monitor);
				if(metadata == null)
					return; // give up - errors have been logged.

				// sync version
				String version = metadata == null
						? "0"
						: metadata.getVersion();
				if(version == null || version.length() < 1)
					version = "0.0.0";
				try {
					IProject p = getProject();
					String storedVersion = p.getPersistentProperty(versionKey);
					if(!version.equals(storedVersion))
						p.setPersistentProperty(versionKey, version);
				}
				catch(CoreException e1) {
					log.error("Could not set version of project", e1);
				}

				List<IProject> resolutions = resolveDependencies(metadata, moduleFile, monitor);
				try {
					IProject[] dynamicReferences = project.getDescription().getDynamicReferences();
					List<IProject> current = Lists.newArrayList(dynamicReferences);
					if(current.size() == resolutions.size() && current.containsAll(resolutions))
						return; // already in sync
					// not in sync, set them
					IProjectDescription desc = project.getDescription();
					desc.setDynamicReferences(resolutions.toArray(new IProject[resolutions.size()]));
					project.setDescription(desc, monitor);

				}
				catch(CoreException e) {
					log.error("Can not sync project's dynamic dependencies", e);
				}
			}
		}
	}
}
