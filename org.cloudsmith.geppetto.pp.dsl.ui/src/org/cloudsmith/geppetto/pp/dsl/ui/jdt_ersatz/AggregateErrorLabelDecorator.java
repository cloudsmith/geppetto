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
package org.cloudsmith.geppetto.pp.dsl.ui.jdt_ersatz;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * Simple decorator for error and warning (right now hacking/testing).
 * 
 */
public class AggregateErrorLabelDecorator implements ILightweightLabelDecorator {

	private static String decoratorId = "org.cloudsmith.geppetto.pp.dsl.ui.errorDecorator";

	private IResourceChangeListener listener = null;

	private boolean useJDT;

	public AggregateErrorLabelDecorator() {
		useJDT = isJavaEnabled();
		if(useJDT)
			return;
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		listener = new IResourceChangeListener() {

			@Override
			public void resourceChanged(IResourceChangeEvent event) {
				IMarkerDelta[] markerDeltas = event.findMarkerDeltas(IMarker.PROBLEM, true);
				if(markerDeltas.length > 0)
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							PlatformUI.getWorkbench().getDecoratorManager().update(decoratorId);

						}
					});
			}

		};
		workspace.addResourceChangeListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void addListener(ILabelProviderListener listener) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ILightweightLabelDecorator#decorate(java.lang.Object, org.eclipse.jface.viewers.IDecoration)
	 */
	@Override
	public void decorate(Object element, IDecoration decoration) {
		if(useJDT)
			return;
		if(element instanceof IResource == false)
			return;

		// // should be installed for IFolder and IProject, but better check
		// if(!(element instanceof IFolder || element instanceof IProject))
		// return;

		// get the max severity from markers
		IResource resource = (IResource) element;
		if(!resource.isAccessible())
			return;

		int severity = -1;
		try {
			severity = resource.findMaxProblemSeverity(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
		}
		catch(CoreException e) {
			// TODO should be logged - (should not really happen)
			e.printStackTrace();
		}
		if(severity < IMarker.SEVERITY_WARNING) {
			decoration.addOverlay(null, IDecoration.BOTTOM_LEFT);
			return;
		}

		ImageDescriptor overlay = null;
		if(severity == IMarker.SEVERITY_ERROR)
			overlay = PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_DEC_FIELD_ERROR);
		else
			overlay = PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
				ISharedImages.IMG_DEC_FIELD_WARNING);

		decoration.addOverlay(overlay, IDecoration.BOTTOM_LEFT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		if(listener != null)
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(listener);

	}

	/**
	 * org.eclipse.jdt.core is added as an optional dependency in o.c.g.pp.dsl.ui and if JDT is present in
	 * the runtime, there is no need for the AggregateErrorLabel to do anything.
	 * 
	 * @return true if JDT is present.
	 */
	private boolean isJavaEnabled() {
		try {
			org.eclipse.jdt.core.JavaCore.class.getName();
			return true;
		}
		catch(Throwable e) {
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	@Override
	public void removeListener(ILabelProviderListener listener) {

	}

}
