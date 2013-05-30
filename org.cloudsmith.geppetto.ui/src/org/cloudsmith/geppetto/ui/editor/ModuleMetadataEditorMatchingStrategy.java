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
package org.cloudsmith.geppetto.ui.editor;

import static org.cloudsmith.geppetto.forge.Forge.METADATA_JSON_NAME;
import static org.cloudsmith.geppetto.forge.Forge.MODULEFILE_NAME;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;

public class ModuleMetadataEditorMatchingStrategy implements IEditorMatchingStrategy {

	@Override
	public boolean matches(IEditorReference editorRef, IEditorInput input) {
		if(!(input instanceof IFileEditorInput))
			return false;

		try {
			IEditorInput refInput = editorRef.getEditorInput();
			if(!(refInput instanceof IFileEditorInput))
				return false;

			if(input.equals(refInput))
				return true;

			IFile file = ((IFileEditorInput) input).getFile();
			IFile refFile = ((IFileEditorInput) refInput).getFile();
			if(!file.getParent().equals(refFile.getParent()))
				return false;

			if(file.isDerived() && METADATA_JSON_NAME.equals(file.getName()))
				return MODULEFILE_NAME.equals(refFile.getName());

			if(MODULEFILE_NAME.equals(file.getName()))
				return refFile.isDerived() && METADATA_JSON_NAME.equals(refFile.getName());
			return false;
		}
		catch(PartInitException e) {
			return false;
		}
	}
}
