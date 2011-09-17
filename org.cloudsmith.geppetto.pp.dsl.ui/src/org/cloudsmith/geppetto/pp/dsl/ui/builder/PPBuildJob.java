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

import org.apache.log4j.Logger;
import org.cloudsmith.geppetto.pp.dsl.ui.PPUiConstants;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.xtext.ui.editor.preferences.AbstractPreferencePage;

import com.google.common.collect.Lists;

/**
 * The PPBuildJob is used when there is a need to rebuild projects (such as after preference changes).
 * 
 */
public class PPBuildJob extends Job {

	final private IProject[] projects;

	private static final Logger log = Logger.getLogger(AbstractPreferencePage.class);

	public PPBuildJob(IProject... projects) {
		super("Building Puppet Projects");
		this.projects = projects;
		setPriority(Job.BUILD);

	}

	public PPBuildJob(IWorkspace workspace) {
		super("Building Puppet Projects");
		List<IProject> puppetProjects = Lists.newArrayList();
		for(IProject p : workspace.getRoot().getProjects())
			try {
				if(!p.isAccessible())
					continue;
				if(p.getNature(PPUiConstants.PUPPET_NATURE_ID) != null)
					puppetProjects.add(p);
			}
			catch(CoreException e) {
				log.error("Failed to get puppet nature information from project", e);
			}
		this.projects = puppetProjects.toArray(new IProject[puppetProjects.size()]);
		setPriority(Job.BUILD);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		final SubMonitor ticker = SubMonitor.convert(monitor, projects.length * 100);
		for(IProject p : projects) {
			try {
				ticker.setTaskName("Cleaning project " + p.getName());
				p.build(IncrementalProjectBuilder.CLEAN_BUILD, ticker.newChild(100));
			}
			catch(CoreException e) {
				return e.getStatus();
			}
		}
		for(IProject p : projects) {
			try {
				ticker.setTaskName("Building project " + p.getName());
				p.build(IncrementalProjectBuilder.FULL_BUILD, ticker.newChild(100));
			}
			catch(CoreException e) {
				return e.getStatus();
			}
		}
		return Status.OK_STATUS;
	}
}
