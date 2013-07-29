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
package org.cloudsmith.geppetto.pp.dsl.ui.jdt_ersatz;

import org.cloudsmith.geppetto.pp.dsl.ui.internal.PPDSLActivator;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
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
		useJDT = PPDSLActivator.isJavaEnabled();
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
			overlay = getErrorImageDescriptor();
		else
			overlay = getWarningImageDescriptor();

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

	private ImageDescriptor getErrorImageDescriptor() {
		ImageDescriptor result = PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
			ISharedImages.IMG_DEC_FIELD_ERROR);
		// TODO: remove workaround see https://bugs.eclipse.org/bugs/show_bug.cgi?id=304397
		return result != null
				? result
				: JFaceResources.getImageRegistry().getDescriptor("org.eclipse.jface.fieldassist.IMG_DEC_FIELD_ERROR");
	}

	private ImageDescriptor getWarningImageDescriptor() {
		ImageDescriptor result = PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
			ISharedImages.IMG_DEC_FIELD_WARNING);
		// TODO: remove workaround see https://bugs.eclipse.org/bugs/show_bug.cgi?id=304397
		return result != null
				? result
				: JFaceResources.getImageRegistry().getDescriptor("org.eclipse.jface.fieldassist.IMG_DEC_FIELD_WARNING");
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
