/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ui.linked;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;

/**
 * PP Specific Editor Customizer.
 * 
 */
public class PPEditorCustomizer extends DefaultExtXtextEditorCustomizer {
	/**
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.linked.DefaultExtXtextEditorCustomizer#customEditorTitle(org.eclipse.ui.IEditorInput)
	 */
	@Override
	public String customEditorTitle(IEditorInput input) {
		String result = null;
		if(input != null) {
			if(input instanceof FileEditorInput) {
				IFile source = ((FileEditorInput) input).getFile();
				if("init.pp".equals(source.getName()) || "site.pp".equals(source.getName())) {
					IPath path = source.getFullPath();
					String modulename = "";
					boolean modulesSeen = false;
					for(int i = 0; i < path.segmentCount(); i++) {
						if("modules".equals(path.segment(i)))
							modulesSeen = true;
						else if(modulesSeen) {
							modulename = path.segment(i);
							break;
						}
					}
					if(modulename.length() < 1) {
						modulename = source.getProject().getName();
					}
					result = modulename + "::" + source.getName();
				}
			}

		}
		return result;
	}
}
