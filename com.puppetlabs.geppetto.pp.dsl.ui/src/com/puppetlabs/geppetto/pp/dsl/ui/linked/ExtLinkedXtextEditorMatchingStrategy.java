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

package com.puppetlabs.geppetto.pp.dsl.ui.linked;

import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Matches unlinked and linked files.
 * 
 */
public class ExtLinkedXtextEditorMatchingStrategy implements IEditorMatchingStrategy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IEditorMatchingStrategy#matches(org.eclipse.ui.IEditorReference, org.eclipse.ui.IEditorInput)
	 */
	public boolean matches(IEditorReference editor, IEditorInput input) {
		IEditorPart part = (IEditorPart) editor.getPart(false);
		if(part == null)
			return false; // TODO: may be wrong if part not restored See EditorManager.findEditors
		IEditorInput editorInput = part.getEditorInput();
		if(editorInput instanceof FileEditorInput && input instanceof FileStoreEditorInput) {
			FileEditorInput fei = (FileEditorInput) editorInput;
			FileStoreEditorInput fsei = (FileStoreEditorInput) input;
			if(fei.getFile().isLinked() && fei.getURI().equals(fsei.getURI()))
				return true;
		}
		return editorInput.equals(input);
	}

}
