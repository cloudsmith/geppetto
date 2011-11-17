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

import org.eclipse.core.resources.IResource;
import org.eclipse.xtext.ui.editor.AbstractDirtyStateAwareEditorCallback;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com.google.inject.Inject;

/**
 * Turns on puppet and xtext natures when a file opens if these natures are not already in effect on the project.
 */
public class NatureAddingEditorCallback extends AbstractDirtyStateAwareEditorCallback {
	@Inject
	private ToggleNatureAction toggleNature;

	@Override
	public void afterCreatePartControl(XtextEditor editor) {
		super.afterCreatePartControl(editor);
		IResource resource = editor.getResource();
		if(resource != null && !toggleNature.hasNature(resource.getProject()) && resource.getProject().isAccessible() &&
				!resource.getProject().isHidden()) {
			toggleNature.toggleNature(resource.getProject(), true);
		}
	}

}
