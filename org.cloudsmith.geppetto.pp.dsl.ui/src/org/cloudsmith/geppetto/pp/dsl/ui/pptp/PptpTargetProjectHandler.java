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
package org.cloudsmith.geppetto.pp.dsl.ui.pptp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;
import org.cloudsmith.geppetto.common.util.EclipseUtils;
import org.cloudsmith.geppetto.pp.dsl.target.PptpResourceUtil;
import org.cloudsmith.geppetto.pp.dsl.target.PuppetTarget;
import org.cloudsmith.geppetto.pp.dsl.ui.PPUiConstants;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.ui.XtextProjectHelper;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Handler of the hidden puppet target project.
 * Is used to check the state of the workspace and all projects with puppet nature.
 * 
 */
@Singleton
public class PptpTargetProjectHandler {

	@Inject
	PPPreferencesHelper preferenceHelper;

	@Inject
	IPreferenceStoreAccess storeAccess;

	private final static Logger log = Logger.getLogger(PptpTargetProjectHandler.class);

	/**
	 * Ensures that all projects with the PuppetNature has a dependency on the project
	 * defining the target platform.
	 * Calls {@link #ensureTargetProjectConfiguration()} if the target project is not created.
	 */
	public void ensureStateOfPuppetProjects(IProgressMonitor monitor) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject targetProject = workspace.getRoot().getProject(PPUiConstants.PPTP_TARGET_PROJECT_NAME);
		ensureTargetProjectConfiguration(monitor);
		IProject[] projects = workspace.getRoot().getProjects();
		SubMonitor ticker = SubMonitor.convert(monitor, projects.length);
		PROJECTS: for(IProject p : projects) {
			ticker.worked(1);
			// skip the target project itself, do not want to create a circular dependency
			if(p.equals(targetProject))
				continue;
			if(!p.isAccessible())
				continue;
			try {
				if(p.hasNature(PPUiConstants.PUPPET_NATURE_ID)) {
					IProjectDescription desc = p.getDescription();
					IProject[] dynamicReferences = desc.getDynamicReferences();
					for(int i = 0; i < dynamicReferences.length; i++) {
						if(dynamicReferences[i].equals(targetProject))
							continue PROJECTS;
					}
					IProject[] newDynamicReferences = new IProject[dynamicReferences.length + 1];
					System.arraycopy(dynamicReferences, 0, newDynamicReferences, 1, dynamicReferences.length);
					newDynamicReferences[0] = targetProject;
					desc.setDynamicReferences(newDynamicReferences);
					p.setDescription(desc, monitor);
				}
			}
			catch(CoreException e) {
				log.error("Could not determine nature of project: '" + p.getName() + "'", e);
			}
		}
	}

	public void ensureTargetProjectConfiguration(IProgressMonitor monitor) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		// While developing, keep this here to enable removal of hidden project in safe way
		IProject oldTargetProject = workspace.getRoot().getProject(PPUiConstants.OLD_PPTP_TARGET_PROJECT_NAME);
		if(oldTargetProject.exists())
			try {
				oldTargetProject.delete(true, monitor);
			}
			catch(CoreException e) {
				// ignore
			}

		IProject targetProject = workspace.getRoot().getProject(PPUiConstants.PPTP_TARGET_PROJECT_NAME);

		// make sure project exists
		if(!targetProject.exists()) {
			try {
				// TODO: Aaaargh: hidden projects are not built, needs to be hidden by using a name starting with "."
				// create (and hide it), and set natures
				// If not hidden this way, it may be displayed (and there is a race with setting it hidden
				// using setHidden(true). This avoids the race.
				// targetProject.create(null, IResource.HIDDEN, monitor);
				targetProject.create(monitor);
			}
			catch(CoreException e) {
				log.error("Could not create target platform project.", e);
				return;
			}
		}

		// make sure project is open
		try {
			if(!targetProject.isAccessible())
				targetProject.open(monitor);
		}
		catch(CoreException e) {
			log.error("Could not open target project!", e);
		}

		// make sure it is configured with the correct natures
		try {
			targetProject.setHidden(false);
			IProjectDescription desc = targetProject.getDescription();
			desc.setNatureIds(new String[] { XtextProjectHelper.NATURE_ID, PPUiConstants.PUPPET_NATURE_ID });
			targetProject.setDescription(desc, monitor);
		}
		catch(CoreException e) {
			log.error("Failed to configure target project", e);
		}

		URI uri;
		try {
			uri = PuppetTarget.forLiteral(preferenceHelper.getPptpVersion()).getPlatformURI();
		}
		catch(IllegalArgumentException e) {
			log.error(e.getMessage());
			uri = PuppetTarget.getDefault().getPlatformURI();
		}
		sync(targetProject, uri, "puppet-", monitor);
		sync(targetProject, PptpResourceUtil.getFacter_1_6(), "facter-", monitor);
	}

	public void initializePuppetTargetProject() {
		Job job = new Job("Checking Puppet Projects") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Checking Puppet Projects ...", 100);
				ensureStateOfPuppetProjects(monitor);
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setSystem(false);
		job.setPriority(Job.INTERACTIVE);
		job.schedule();

	}

	public void initializePuppetWorkspace() {
		Job job = new Job("Initializing Puppet Workspace") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Checking Puppet Projects ...", 100);
				// ensure that store is initialized before doing anything else
				preferenceHelper.initialize(storeAccess);
				ensureStateOfPuppetProjects(monitor);
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setSystem(false);
		job.setPriority(Job.INTERACTIVE);
		job.schedule();
	}

	private void sync(IProject targetProject, URI uri, String prefix, IProgressMonitor monitor) {
		// get a handle to the wanted target platform (.pptp) file from preferences
		//
		try {
			targetProject.refreshLocal(IFile.DEPTH_INFINITE, monitor);
			File pptpFile = EclipseUtils.getResourceAsFile(new URL(uri.toString()));
			IFile targetFile = targetProject.getFile(pptpFile.getName());
			if(targetFile.exists()) {
				if(pptpFile.lastModified() > targetFile.getLocalTimeStamp()) {
					targetFile.delete(IFile.FORCE | IFile.ALWAYS_DELETE_PROJECT_CONTENT, monitor);
					targetFile.refreshLocal(IFile.DEPTH_ZERO, monitor);
					targetFile.create(new FileInputStream(pptpFile), true, monitor);
				}
			}
			else {
				// delete all prefix-* resources already there (either none, or some other/older .pptp)
				// this makes it possible to keep several that are not managed
				for(IResource r : targetProject.members()) {
					if(r.getName().startsWith(prefix))
						r.delete(IFile.FORCE | IFile.ALWAYS_DELETE_PROJECT_CONTENT, monitor);
				}
				InputStream inputStream = new FileInputStream(pptpFile);
				targetFile.create(inputStream, true, monitor);
			}
		}
		catch(IOException e) {
			log.error("Could not get .pptp default for copying.", e);
		}
		catch(CoreException e) {
			log.error("Could not perform operation on target .pptp file.", e);
		}

	}
}
