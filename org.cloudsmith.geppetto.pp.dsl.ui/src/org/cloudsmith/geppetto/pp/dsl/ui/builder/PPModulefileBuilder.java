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

import java.io.File;
import java.io.IOException;
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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.util.Wrapper;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;

/**
 * Builder of Modulefile.
 * This builder performs the following tasks:
 * <ul>
 * <li>sets the dependencies on the project as dynamic project references</li>
 * <li>ensure all puppet projects have a dynamic reference to the target project</li>
 * <li>keeps metadata.json in sync (content and checksums)</li>
 * </ul>
 * 
 */

public class PPModulefileBuilder extends IncrementalProjectBuilder implements PPUiConstants {
	private final static Logger log = Logger.getLogger(PPModulefileBuilder.class);

	private ITracer tracer;

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

	private void createErrorMarker(IResource r, String message, Dependency d) {
		createMarker(IMarker.SEVERITY_ERROR, r, message, d);
	}

	private void createMarker(int severity, IResource r, String message, Dependency d) {
		try {
			IMarker m = r.createMarker(PUPPET_MODULE_PROBLEM_MARKER_TYPE);
			m.setAttribute(IMarker.MESSAGE, message);
			m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
			m.setAttribute(IMarker.SEVERITY, severity);
			if(d != null)
				m.setAttribute(IMarker.LOCATION, d.getName() + d.getVersionRequirement().toString());
		}
		catch(CoreException e) {
			log.error("Could not create error marker or set its attributes for a 'Modulefile'", e);
		}

	}

	private void createWarningMarker(IResource r, String message, Dependency d) {
		createMarker(IMarker.SEVERITY_WARNING, r, message, d);
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
	private IProject getBestMatchingProject(Dependency d, IProgressMonitor monitor) {
		// Names with "/" are not allowed
		final String requiredName = d.getName().replace("/", "-").toLowerCase();
		if(requiredName == null || requiredName.isEmpty()) {
			tracer.trace("Dependency with mising name found");
			return null;
		}
		tracer.trace("Resolving required name: ", requiredName);
		BiMap<IProject, String> candidates = HashBiMap.create();

		final String namepart = requiredName + "-";
		final int len = namepart.length();

		tracer.trace("Checking against all projects...");
		for(IProject p : getWorkspaceRoot().getProjects()) {
			checkCancel(monitor);
			if(!isAccessiblePuppetProject(p)) {
				tracer.trace("Project not accessible: ", p.getName());
				continue;
			}
			String projectName = p.getName().toLowerCase();

			// new style
			String version = null;
			String moduleName = null;
			try {
				moduleName = p.getPersistentProperty(PROJECT_PROPERTY_MODULENAME);
			}
			catch(CoreException e) {
				log.error("Could not read project Modulename property", e);
			}
			tracer.trace("Project: ", p.getName(), " has persisted name: ", moduleName);
			boolean matched = false;
			if(requiredName.equals(moduleName))
				matched = true;

			// // matching on project name
			// else if(projectName.equals(requiredName))
			// matched = true;
			// else if(projectName.startsWith(requiredName + "-") && projectName.length() > len)
			// matched = true;
			if(tracer.isTracing()) {
				if(!matched)
					tracer.trace("== not matched on name");
			}
			// in both old and new style match, get the version from the persisted property
			if(matched) {
				try {
					version = p.getPersistentProperty(PROJECT_PROPERTY_MODULEVERSION);
				}
				catch(CoreException e) {
					log.error("Error while getting version from project", e);
				}
				if(version == null)
					version = "0";
				if(tracer.isTracing())
					tracer.trace("Candidate with version; ", version.toString(), " added as candidate");
				candidates.put(p, version);
			}
			// // old style, name and version in project name
			// else if(n.startsWith(name + "-") && n.length() > len && isAccessibleXtextProject(p))
			// candidates.put(p, p.getName().substring(len));
		}
		if(candidates.isEmpty()) {
			if(tracer.isTracing())
				tracer.trace("No candidates found");
			return null;
		}
		if(tracer.isTracing()) {
			tracer.trace("Getting best version");
		}
		// find best version and do a lookup of project
		String best = d.getVersionRequirement().findBestMatch(candidates.values());
		if(best == null || best.length() == 0) {
			if(tracer.isTracing())
				tracer.trace("No best match found");
			return null;
		}
		if(tracer.isTracing()) {
			tracer.trace("Found best project: ", candidates.inverse().get(best).getName(), "having version:", best);
		}
		return candidates.inverse().get(best);
	}

	private IProject getProjectByName(String name) {
		return getProject().getWorkspace().getRoot().getProject(name);
	}

	private IWorkspaceRoot getWorkspaceRoot() {
		return getProject().getWorkspace().getRoot();
	}

	private void incrementalBuild(IResourceDelta delta, final IProgressMonitor monitor) {
		removeErrorMarkers();
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

					// if any file included in the checksum was changed, added, removed etc.
					if(isChecksumChange(delta))
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
	private boolean isAccessiblePuppetProject(IProject p) {
		return p != null && XtextProjectHelper.hasNature(p);
	}

	/**
	 * A change to any file except "metadata.json" is a checksum change.
	 * 
	 * @param delta
	 * @return
	 */
	private boolean isChecksumChange(IResourceDelta delta) {
		IResource resource = delta.getResource();
		if(!(resource instanceof IFile))
			return false;
		if(resource.getProjectRelativePath().toString().equals("metadata.json"))
			return false;
		return true; // all other files are included in the checksum list.
	}

	private boolean isModulefileDelta(IResourceDelta delta) {
		return ((delta.getResource() instanceof IFile) && MODULEFILE_PATH.equals(delta.getProjectRelativePath()));
	}

	private Metadata loadMetadata(IFile moduleFile, IProgressMonitor monitor) {
		// parse the "Modulefile" and get full name and version, use this as name of target entry
		try {
			Metadata metadata = ForgeFactory.eINSTANCE.createMetadata();
			metadata.loadModuleFile(moduleFile.getLocation().toFile());
			return metadata;
		}
		catch(Exception e) {
			createErrorMarker(moduleFile, "Can not parse modulefile: " + e.getMessage(), null);
			if(log.isDebugEnabled())
				log.debug("Could not parse Modulefile dependencies: '" + moduleFile + "'", e);
		}
		return null;
	}

	/**
	 * Deletes all problem markers set by this builder.
	 */
	private void removeErrorMarkers() {
		IFile m = getProject().getFile(MODULEFILE_PATH);
		try {
			m.deleteMarkers(PUPPET_MODULE_PROBLEM_MARKER_TYPE, true, IResource.DEPTH_ZERO);
		}
		catch(CoreException e) {
			// nevermind, the resource may not even be there...
			// meaningless to have elaborate existence checks etc...
		}
	}

	/**
	 * Resolves the dependencies against projects in the workspace.
	 * Sets error markers when there are unresolved dependencies.
	 * 
	 * @param handle
	 * @return
	 */
	private List<IProject> resolveDependencies(Metadata metadata, IFile moduleFile, IProgressMonitor monitor) {
		List<IProject> result = Lists.newArrayList();

		// parse the "Modulefile" and get full name and version, use this as name of target entry
		try {
			for(Dependency d : metadata.getDependencies()) {
				checkCancel(monitor);
				IProject best = getBestMatchingProject(d, monitor);
				if(best != null)
					result.add(best);
				else {
					createErrorMarker(
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

	private void syncModulefile(final IProgressMonitor monitor) {
		if(tracer.isTracing())
			tracer.trace("Syncing modulefile with project");

		checkCancel(monitor);
		IProject project = getProject();
		if(isAccessiblePuppetProject(project)) {
			IFile moduleFile = project.getFile("Modulefile");
			if(moduleFile.exists()) {

				// get metadata
				Metadata metadata = loadMetadata(moduleFile, monitor);
				if(metadata == null)
					return; // give up - errors have been logged.

				// sync version and name project data
				String version = metadata == null
						? "0"
						: metadata.getVersion();
				if(version == null || version.length() < 1)
					version = "0.0.0";
				String moduleName = metadata.getFullName().toLowerCase();
				if(moduleName != null && !project.getName().toLowerCase().contains(moduleName))
					createWarningMarker(moduleFile, "Mismatched name - project does not reflect module: '" +
							moduleName + "'", null);

				try {
					IProject p = getProject();
					String storedVersion = p.getPersistentProperty(PROJECT_PROPERTY_MODULEVERSION);
					if(!version.equals(storedVersion))
						p.setPersistentProperty(PROJECT_PROPERTY_MODULEVERSION, version);

					String storedName = p.getPersistentProperty(PROJECT_PROPERTY_MODULENAME);
					if(!moduleName.equals(storedName))
						p.setPersistentProperty(PROJECT_PROPERTY_MODULENAME, moduleName);
				}
				catch(CoreException e1) {
					log.error("Could not set version or symbolic module name of project", e1);
				}

				List<IProject> resolutions = resolveDependencies(metadata, moduleFile, monitor);
				// add the TP project
				resolutions.add(getProjectByName(PPUiConstants.PPTP_TARGET_PROJECT_NAME));

				syncProjectReferences(resolutions, monitor);

				// Sync the built .json version
				try {
					File pf = project.getLocation().toFile();
					File mf = new File(pf, "metadata.json");
					File tf = new File(pf, "lib/puppet");

					// if there are types
					if(tf.exists())
						metadata.loadTypeFiles(tf);
					metadata.loadChecksums(pf);
					metadata.saveJSONMetadata(mf);
					// must refresh the file as it was writting outside the resource framework
					IFile metadataResource = getProject().getFile("metadata.json");
					try {
						metadataResource.refreshLocal(IResource.DEPTH_ZERO, monitor);
					}
					catch(CoreException e) {
						log.error("Could not refresh 'metadata.json'", e);
					}
				}
				catch(IOException e) {
					createErrorMarker(moduleFile, "Error while writing 'metadata.json': " + e.getMessage(), null);
					log.error("Could not build 'metadata.json' for: '" + moduleFile + "'", e);
				}
			}
		}
		else {
			// puppet project without modulefile should reference the target project
			syncProjectReferences(Lists.newArrayList(getProjectByName(PPUiConstants.PPTP_TARGET_PROJECT_NAME)), monitor);
		}
	}

	private void syncProjectReferences(List<IProject> wanted, IProgressMonitor monitor) {
		try {
			final IProject project = getProject();
			final IProjectDescription description = getProject().getDescription();
			List<IProject> current = Lists.newArrayList(description.getDynamicReferences());
			if(current.size() == wanted.size() && current.containsAll(wanted))
				return; // already in sync
			// not in sync, set them
			IProjectDescription desc = getProject().getDescription();
			desc.setDynamicReferences(wanted.toArray(new IProject[wanted.size()]));
			project.setDescription(desc, monitor);
		}
		catch(CoreException e) {
			log.error("Can not sync project's dynamic dependencies", e);
		}
	}
}
