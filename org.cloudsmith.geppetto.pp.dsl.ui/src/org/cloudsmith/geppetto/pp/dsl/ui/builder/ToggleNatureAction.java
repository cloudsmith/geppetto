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

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.cloudsmith.geppetto.pp.dsl.ui.PPUiConstants;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.xtext.ui.XtextProjectHelper;

import com.google.common.collect.Lists;

public class ToggleNatureAction implements IObjectActionDelegate {
	private final static Logger log = Logger.getLogger(ToggleNatureAction.class);

	private ISelection selection;

	private boolean turnOn;

	private void addNature(IProject project) {
		// Also add XtextNature if not already set
		boolean addXtextNature = !XtextProjectHelper.hasNature(project);

		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();

			final int newCount = 1 + (addXtextNature
					? 1
					: 0);
			// Add the nature
			String[] newNatures = new String[natures.length + newCount];
			System.arraycopy(natures, 0, newNatures, newCount, natures.length);
			newNatures[0] = PPUiConstants.PUPPET_NATURE_ID;
			if(addXtextNature)
				newNatures[1] = XtextProjectHelper.NATURE_ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		}
		catch(CoreException e) {
			log.error("Failed adding Puppet Project Nature", e);
		}

	}

	/**
	 * Returns selected accessible projects
	 * 
	 * @param selection
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<IProject> getSelectedProjects(ISelection selection) {
		List<IProject> projects = Lists.newArrayList();
		if(selection instanceof IStructuredSelection) {
			for(Iterator it = ((IStructuredSelection) selection).iterator(); it.hasNext();) {
				Object element = it.next();
				IProject project = null;
				if(element instanceof IProject) {
					project = (IProject) element;
				}
				else if(element instanceof IAdaptable) {
					project = (IProject) ((IAdaptable) element).getAdapter(IProject.class);
				}
				if(project != null && project.isAccessible()) {
					projects.add(project);
				}
			}
		}
		return projects;
	}

	protected boolean hasNature(IProject project) {
		try {
			IProjectDescription description = project.getDescription();

			String[] natures = description.getNatureIds();

			for(int i = 0; i < natures.length; ++i)
				if(PPUiConstants.PUPPET_NATURE_ID.equals(natures[i]))
					return true;
		}
		catch(CoreException e) {
			log.error("Error while trying to obtain nature of project", e);
		}
		return false;
	}

	private void removeNature(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();

			for(int i = 0; i < natures.length; ++i) {
				if(PPUiConstants.PUPPET_NATURE_ID.equals(natures[i])) {
					// Remove the nature
					String[] newNatures = new String[natures.length - 1];
					System.arraycopy(natures, 0, newNatures, 0, i);
					System.arraycopy(natures, i + 1, newNatures, i, natures.length - i - 1);
					description.setNatureIds(newNatures);
					project.setDescription(description, null);
					return;
				}
			}
		}
		catch(CoreException e) {
			log.error("Failed adding Puppet Project Nature", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@SuppressWarnings("rawtypes")
	public void run(IAction action) {
		if(selection instanceof IStructuredSelection) {
			for(Iterator it = ((IStructuredSelection) selection).iterator(); it.hasNext();) {
				Object element = it.next();
				IProject project = null;
				if(element instanceof IProject) {
					project = (IProject) element;
				}
				else if(element instanceof IAdaptable) {
					project = (IProject) ((IAdaptable) element).getAdapter(IProject.class);
				}
				if(project != null) {
					toggleNature(project);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 * org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		List<IProject> projects = getSelectedProjects(selection);

		// enable the action if at least one project is selected
		action.setEnabled(projects.size() != 0);
		if(projects.size() == 0) {
			// Set a default text (for disabled state)
			action.setText("Toggle Puppet Project Nature");
		}
		else {
			// If one of the projects do not have the nature, then the action is a
			// set nature on all
			this.turnOn = false;
			for(IProject p : projects)
				if(!hasNature(p))
					turnOn = true;
			if(turnOn)
				action.setText("Add Puppet Project Nature");
			else
				action.setText("Remove Puppet Project Nature");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 * org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * Toggles nature on a project
	 * 
	 * @param project
	 *            to have sample nature added or removed
	 */
	protected void toggleNature(IProject project) {

		if(hasNature(project)) {
			if(!turnOn)
				removeNature(project);
		}
		else if(turnOn)
			addNature(project);
	}

	protected void toggleNature(IProject project, boolean on) {
		if(hasNature(project)) {
			if(!on)
				removeNature(project);
		}
		else if(on)
			addNature(project);

	}
}
