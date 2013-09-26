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

package com.puppetlabs.geppetto.pp.dsl.ui.builder;

import static com.puppetlabs.geppetto.forge.Forge.METADATA_JSON_NAME;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.puppetlabs.geppetto.common.tracer.DefaultTracer;
import com.puppetlabs.geppetto.common.tracer.ITracer;
import com.puppetlabs.geppetto.common.tracer.NullTracer;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.FileDiagnostic;
import com.puppetlabs.geppetto.forge.FilePosition;
import com.puppetlabs.geppetto.forge.Forge;
import com.puppetlabs.geppetto.forge.model.Dependency;
import com.puppetlabs.geppetto.forge.model.Metadata;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.pp.dsl.ui.PPUiConstants;
import com.puppetlabs.geppetto.pp.dsl.ui.internal.PPDSLActivator;
import com.puppetlabs.geppetto.pp.dsl.validation.IValidationAdvisor;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.util.Wrapper;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 * Builder of Modulefile/metadata.json.
 * This builder performs the following tasks:
 * <ul>
 * <li>sets the dependencies on the project as dynamic project references</li>
 * <li>ensure all puppet projects have a dynamic reference to the target project</li>
 * <li>keeps derived metadata.json in sync with Modulefile if applicable (content and checksums)</li>
 * </ul>
 * 
 */

public class PPModuleMetadataBuilder extends IncrementalProjectBuilder implements PPUiConstants {
	private final static Logger log = Logger.getLogger(PPModuleMetadataBuilder.class);

	/**
	 * Returns the best matching project (or null if there is no match) among the projects in the
	 * workspace
	 * 
	 * @param name
	 *            The name of the module to match
	 * @param versionRequirement
	 *            The version requirement. Can be <code>null</code>.
	 * @return The matching project or <code>null</code>.
	 */
	public static IProject getBestMatchingProject(ModuleName requiredName, VersionRange versionRequirement) {
		return getBestMatchingProject(requiredName, versionRequirement, NullTracer.INSTANCE, new NullProgressMonitor());
	}

	private static IProject getBestMatchingProject(ModuleName name, VersionRange vr, ITracer tracer,
			IProgressMonitor monitor) {
		// Names with "/" are not allowed
		if(name == null) {
			if(tracer.isTracing())
				tracer.trace("Dependency with missing name found");
			return null;
		}

		name = name.withSeparator('-');
		if(tracer.isTracing())
			tracer.trace("Resolving required name: ", name);
		BiMap<IProject, Version> candidates = HashBiMap.create();

		if(tracer.isTracing())
			tracer.trace("Checking against all projects...");
		for(IProject p : getWorkspaceRoot().getProjects()) {
			if(!isAccessiblePuppetProject(p)) {
				if(tracer.isTracing())
					tracer.trace("Project not accessible: ", p.getName());
				continue;
			}

			Version version = null;
			ModuleName moduleName = null;
			try {
				String mn = p.getPersistentProperty(PROJECT_PROPERTY_MODULENAME);
				moduleName = mn == null
						? null
						: new ModuleName(mn);
			}
			catch(CoreException e) {
				log.error("Could not read project Modulename property", e);
			}
			if(tracer.isTracing())
				tracer.trace("Project: ", p.getName(), " has persisted name: ", moduleName);
			boolean matched = false;
			if(name.equals(moduleName))
				matched = true;

			if(tracer.isTracing()) {
				if(!matched)
					tracer.trace("== not matched on name");
			}
			// get the version from the persisted property
			if(matched) {
				try {
					version = Version.create(p.getPersistentProperty(PROJECT_PROPERTY_MODULEVERSION));
				}
				catch(Exception e) {
					log.error("Error while getting version from project", e);
				}
				if(version == null)
					version = Version.MIN;
				if(tracer.isTracing())
					tracer.trace("Candidate with version; ", version.toString(), " added as candidate");
				candidates.put(p, version);
			}
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
		if(vr == null)
			vr = VersionRange.ALL_INCLUSIVE;
		Version best = vr.findBestMatch(candidates.values());
		if(best == null) {
			if(tracer.isTracing())
				tracer.trace("No best match found");
			return null;
		}
		if(tracer.isTracing()) {
			tracer.trace("Found best project: ", candidates.inverse().get(best).getName(), "having version:", best);
		}
		return candidates.inverse().get(best);
	}

	private static int getMarkerSeverity(Diagnostic diagnostic) {
		int markerSeverity;
		switch(diagnostic.getSeverity()) {
			case Diagnostic.FATAL:
			case Diagnostic.ERROR:
				markerSeverity = IMarker.SEVERITY_ERROR;
				break;
			case Diagnostic.WARNING:
				markerSeverity = IMarker.SEVERITY_WARNING;
				break;
			default:
				markerSeverity = IMarker.SEVERITY_INFO;
		}
		return markerSeverity;
	}

	private static IWorkspaceRoot getWorkspaceRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	/**
	 * TODO: Should check for the Puppet Nature instead of Xtext
	 * 
	 * @param p
	 * @return
	 */
	private static boolean isAccessiblePuppetProject(IProject p) {
		return p != null && XtextProjectHelper.hasNature(p);
	}

	private final Provider<IValidationAdvisor> validationAdvisorProvider;

	private Forge forge;

	private ITracer tracer;

	private IValidationAdvisor validationAdvisor;

	public PPModuleMetadataBuilder() {
		// Hm, can not inject this because it was not possible to inject this builder via the
		// executable extension factory
		Injector injector = ((PPDSLActivator) PPDSLActivator.getInstance()).getPPInjector();
		tracer = new DefaultTracer(PPUiConstants.DEBUG_OPTION_MODULEFILE);
		validationAdvisorProvider = injector.getProvider(IValidationAdvisor.class);
		forge = injector.getInstance(Forge.class);
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
		if(monitor.isCanceled()) {
			forgetLastBuiltState();
			throw new OperationCanceledException();
		}
	}

	private void checkCircularDependencies(final IProgressMonitor monitor) {
		IProject p = getProject();
		List<IProject> visited = Lists.newArrayList();
		List<IProject> circular = Lists.newArrayList();
		try {
			visit(p, visited, circular);
		}
		catch(CoreException e) {
			log.error("Can not check for circular dependencies", e);
		}
		isCircular: if(!circular.isEmpty()) {
			// a direct dependency A -> A is tolerated for the hidden PPTP project
			if(circular.get(0).equals(p) && circular.size() == 1 &&
					PPUiConstants.PPTP_TARGET_PROJECT_NAME.equals(p.getName()))
				break isCircular;

			StringBuffer buf = new StringBuffer("Circular dependency: [");
			for(IProject circ : circular) {
				buf.append(circ.getName());
				buf.append("->");
			}
			buf.append("]");
			int circularSeverity = -1;
			switch(getValidationAdvisor().circularDependencyPreference()) {
				case ERROR:
					circularSeverity = IMarker.SEVERITY_ERROR;
					break;
				case WARNING:
					circularSeverity = IMarker.SEVERITY_WARNING;
					break;
				case IGNORE: // just don't do it...
					break;
			}

			if(circularSeverity != -1)
				createMarker(circularSeverity, p, buf.toString(), null);
		}

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
			if(d != null) {
				VersionRange vr = d.getVersionRequirement();
				m.setAttribute(IMarker.LOCATION, d.getName() + (vr == null
						? ""
						: vr.toString()));
				if(d instanceof FilePosition)
					m.setAttribute(IMarker.LINE_NUMBER, ((FilePosition) d).getLine() + 1);
			}
			else
				m.setAttribute(IMarker.LOCATION, r.getName());
		}
		catch(CoreException e) {
			log.error("Could not create error marker or set its attributes", e);
		}

	}

	private void createResourceMarkers(IResource r, Diagnostic diagnostic) {
		for(Diagnostic child : diagnostic)
			createResourceMarkers(r, child);

		String msg = diagnostic.getMessage();
		if(msg == null)
			return;

		try {
			IMarker m = r.createMarker(PUPPET_MODULE_PROBLEM_MARKER_TYPE);
			m.setAttribute(IMarker.MESSAGE, msg);
			m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
			m.setAttribute(IMarker.SEVERITY, getMarkerSeverity(diagnostic));
			m.setAttribute(IMarker.LOCATION, r.getName());
			if(diagnostic instanceof FileDiagnostic)
				m.setAttribute(IMarker.LINE_NUMBER, ((FileDiagnostic) diagnostic).getLineNumber());
		}
		catch(CoreException e) {
			log.error("Could not create error marker or set its attributes", e);
		}
	}

	private void createWarningMarker(IResource r, String message, Dependency d) {
		createMarker(IMarker.SEVERITY_WARNING, r, message, d);
	}

	private void fullBuild(IProgressMonitor monitor) {
		removeErrorMarkers();
		syncModuleMetadata(monitor);
	}

	private IProject getProjectByName(String name) {
		return getProject().getWorkspace().getRoot().getProject(name);
	}

	private synchronized IValidationAdvisor getValidationAdvisor() {
		if(validationAdvisor == null)
			validationAdvisor = validationAdvisorProvider.get();
		return validationAdvisor;
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

					// irrespective of how the Modulefile/metadata.json was changed, run the build (i.e. sync).
					if(isModuleMetadataDelta(delta))
						buildFlag.set(true);

					// if any file included in the checksum was changed, added, removed etc.
					if(isChecksumChange(delta))
						buildFlag.set(true);

					// continue scanning the delta tree
					return true;
				}
			});
			if(buildFlag.get())
				syncModuleMetadata(monitor);
		}
		catch(CoreException e) {
			log.error(e.getMessage(), e);
			syncModuleMetadata(monitor);
		}
	}

	/**
	 * A change to any file except &quot;metadata.json&quot; is a checksum change.
	 * 
	 * @param delta
	 * @return
	 */
	private boolean isChecksumChange(IResourceDelta delta) {
		IResource resource = delta.getResource();
		if(!(resource instanceof IFile))
			return false;
		if(resource.getProjectRelativePath().equals(METADATA_JSON_PATH))
			return false;
		return true; // all other files are included in the checksum list.
	}

	private boolean isModuleMetadataDelta(IResourceDelta delta) {
		IResource resource = delta.getResource();
		if(resource instanceof IFile && !resource.isDerived()) {
			IPath relPath = delta.getProjectRelativePath();
			return MODULEFILE_PATH.equals(relPath) || METADATA_JSON_PATH.equals(relPath);
		}
		return false;
	}

	/**
	 * Deletes all problem markers set by this builder.
	 */
	private void removeErrorMarkers() {
		IFile m = getProject().getFile(MODULEFILE_PATH);
		try {
			if(m.exists())
				m.deleteMarkers(PUPPET_MODULE_PROBLEM_MARKER_TYPE, true, IResource.DEPTH_ZERO);
		}
		catch(CoreException e) {
			// nevermind, the resource may not even be there...
			// meaningless to have elaborate existence checks etc...
		}

		m = getProject().getFile(METADATA_JSON_PATH);
		try {
			if(!m.isDerived() && m.exists())
				m.deleteMarkers(PUPPET_MODULE_PROBLEM_MARKER_TYPE, true, IResource.DEPTH_ZERO);
		}
		catch(CoreException e) {
		}

		try {
			getProject().deleteMarkers(PUPPET_MODULE_PROBLEM_MARKER_TYPE, true, IResource.DEPTH_ZERO);
		}
		catch(CoreException e) {
		}
	}

	/**
	 * Resolves the dependencies against projects in the workspace.
	 * Sets error markers when there are unresolved dependencies.
	 * 
	 * @param handle
	 * @return
	 */
	private List<IProject> resolveDependencies(Metadata metadata, IFile moduleMetadataFile, IProgressMonitor monitor) {
		List<IProject> result = Lists.newArrayList();

		// parse the 'Modulefile' or 'metadata.json' and get full name and version, use this as name of target entry
		try {
			for(Dependency d : metadata.getDependencies()) {
				checkCancel(monitor);
				IProject best = getBestMatchingProject(d.getName(), d.getVersionRequirement(), tracer, monitor);
				if(best != null) {
					if(!result.contains(best))
						result.add(best);
				}
				else {
					VersionRange vr = d.getVersionRequirement();
					createErrorMarker(moduleMetadataFile, "Unresolved dependency :'" + d.getName() + (vr == null
							? ""
							: ("' version: " + vr)), d);
				}
			}
		}
		catch(Exception e) {
			if(log.isDebugEnabled())
				log.debug("Error while resolving dependencies: '" + moduleMetadataFile + "'", e);
		}
		return result;
	}

	private void syncModulefileAndReferences(final IProgressMonitor monitor) {
		IProject project = getProject();
		if(!isAccessiblePuppetProject(project))
			return;

		if(tracer.isTracing())
			tracer.trace("Syncing modulefile with project");

		File projectDir = project.getLocation().toFile();
		if(!forge.hasModuleMetadata(projectDir, null)) {
			// puppet project without modulefile should reference the target project
			syncProjectReferences(Lists.newArrayList(getProjectByName(PPUiConstants.PPTP_TARGET_PROJECT_NAME)), monitor);
			return;
		}

		checkCancel(monitor);
		SubMonitor subMon = SubMonitor.convert(monitor, 4);

		// get metadata
		Metadata metadata;
		File[] extractionSource = new File[1];
		IFile metadataResource = project.getFile(METADATA_JSON_NAME);
		boolean metadataDerived = metadataResource.isDerived();

		if(metadataDerived) {
			try {
				// Delete this file. It will be recreated from other
				// sources.
				metadataResource.delete(true, subMon.newChild(1));
			}
			catch(CoreException e) {
				log.error("Unable to delete metadata.json", e);
			}
		}
		else {
			// The one that will be created should be considered to
			// be derived
			metadataDerived = !metadataResource.exists();
		}

		Diagnostic diagnostic = new Diagnostic();
		try {
			// Load metadata, types, checksums etc.
			metadata = forge.createFromModuleDirectory(projectDir, true, null, extractionSource, diagnostic);
		}
		catch(Exception e) {
			createErrorMarker(project, "Can not parse Modulefile or other metadata source: " + e.getMessage(), null);
			if(log.isDebugEnabled())
				log.debug("Could not parse module description: '" + project.getName() + "'", e);
			return; // give up - errors have been logged.
		}

		if(metadata == null) {
			createErrorMarker(project, "Unable to find Modulefile or other metadata source", null);
			return;
		}

		// Find the resource used for metadata extraction
		File extractionSourceFile = extractionSource[0];
		IPath extractionSourcePath = Path.fromOSString(extractionSourceFile.getAbsolutePath());
		IFile moduleFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(extractionSourcePath);

		createResourceMarkers(moduleFile, diagnostic);

		// sync version and name project data
		Version version = null;
		ModuleName moduleName = null;
		if(metadata != null) {
			version = metadata.getVersion();
			moduleName = metadata.getName();
		}

		if(version == null)
			version = Version.create("0.0.0");

		if(moduleName != null) {
			moduleName = moduleName.withSeparator('-');
			if(!project.getName().toLowerCase().contains(moduleName.getName().toString().toLowerCase()))
				createWarningMarker(moduleFile, "Mismatched name - project does not reflect module: '" + moduleName +
						"'", null);
		}

		try {
			IProject p = getProject();
			String storedVersion = p.getPersistentProperty(PROJECT_PROPERTY_MODULEVERSION);
			String vstr = version.toString();
			if(!vstr.equals(storedVersion))
				p.setPersistentProperty(PROJECT_PROPERTY_MODULEVERSION, vstr);

			String storedName = p.getPersistentProperty(PROJECT_PROPERTY_MODULENAME);
			if(moduleName == null) {
				if(storedName != null)
					p.setPersistentProperty(PROJECT_PROPERTY_MODULENAME, null);
			}
			else {
				String mstr = moduleName.toString();
				if(!mstr.equals(storedName))
					p.setPersistentProperty(PROJECT_PROPERTY_MODULENAME, mstr.toString());
			}
		}
		catch(CoreException e1) {
			log.error("Could not set version or symbolic module name of project", e1);
		}

		List<IProject> resolutions = resolveDependencies(metadata, moduleFile, subMon.newChild(1));
		// add the TP project
		resolutions.add(getProjectByName(PPUiConstants.PPTP_TARGET_PROJECT_NAME));

		syncProjectReferences(resolutions, subMon.newChild(1));

		// Sync the built .json version
		if(metadataDerived) {
			try {
				// Recreate the metadata.json file
				File mf = new File(project.getLocation().toFile(), METADATA_JSON_NAME);
				forge.saveJSONMetadata(metadata, mf);
				// must refresh the file as it was written outside the resource framework
				try {
					metadataResource.refreshLocal(IResource.DEPTH_ZERO, subMon.newChild(1));
				}
				catch(CoreException e) {
					log.error("Could not refresh 'metadata.json'", e);
				}

				try {
					metadataResource.setDerived(true, monitor);
				}
				catch(CoreException e) {
					log.error("Could not make 'metadata.json' derived", e);
				}

			}
			catch(IOException e) {
				createErrorMarker(moduleFile, "Error while writing 'metadata.json': " + e.getMessage(), null);
				log.error("Could not build 'metadata.json' for: '" + moduleFile + "'", e);
			}
		}
		if(monitor != null)
			monitor.done();
	}

	private void syncModuleMetadata(final IProgressMonitor monitor) {
		syncModulefileAndReferences(monitor);
		checkCircularDependencies(monitor);
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
			// Trigger full rebuild once we're done here
			new PPBuildJob(getWorkspaceRoot().getWorkspace()).schedule();
		}
		catch(CoreException e) {
			log.error("Can not sync project's dynamic dependencies", e);
		}
	}

	private void visit(IProject p, List<IProject> visited, List<IProject> circular) throws CoreException {
		if(!p.isAccessible())
			return;
		if(visited.contains(p))
			return;
		visited.add(p);
		IProject root = visited.get(0);
		for(IProject dep : p.getReferencedProjects()) {
			if(dep.equals(root)) {
				circular.add(p);
				return;
			}
			visit(dep, visited, circular);
		}

	}
}
