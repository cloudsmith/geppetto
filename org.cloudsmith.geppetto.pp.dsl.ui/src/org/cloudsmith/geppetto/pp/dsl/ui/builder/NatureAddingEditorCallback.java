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
package org.cloudsmith.geppetto.pp.dsl.ui.builder;

import org.eclipse.core.resources.IResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.validation.ValidatingEditorCallback;

import com.google.inject.Inject;

/**
 * Turns on puppet and xtext natures when a file opens if these natures are not already in effect on the project.
 */
public class NatureAddingEditorCallback extends ValidatingEditorCallback {
	@Inject
	private ToggleNatureAction toggleNature;

	@Override
	public void afterCreatePartControl(XtextEditor editor) {
		super.afterCreatePartControl(editor);
		IResource resource = editor.getResource();
		if(resource != null && resource.getProject().isAccessible() && !resource.getProject().isHidden() &&
				!toggleNature.hasNature(resource.getProject())) {
			toggleNature.toggleNature(resource.getProject(), true);
		}
	}

}
