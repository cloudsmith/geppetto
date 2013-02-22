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
package org.cloudsmith.geppetto.forge.util;

import static org.cloudsmith.geppetto.forge.impl.MetadataImpl.DEFAULT_EXCLUDES_PATTERN;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to find all existing modules given a root to search from. The
 * root itself can be a module.
 */
public class ModuleFinder {

	private final File modulesRoot;

	public ModuleFinder(File modulesRoot) {
		this.modulesRoot = modulesRoot;
	}

	private boolean findModuleFiles(File[] files, List<File> moduleFiles) {
		if(files != null) {
			int idx = files.length;
			while(--idx >= 0) {
				String name = files[idx].getName();
				if("Modulefile".equals(name) || "metadata.json".equals(name))
					return true;
			}

			idx = files.length;
			while(--idx >= 0) {
				File file = files[idx];
				String name = file.getName();
				if(DEFAULT_EXCLUDES_PATTERN.matcher(name).matches())
					continue;

				if(findModuleFiles(file.listFiles(), moduleFiles))
					moduleFiles.add(file);
			}
		}
		return false;
	}

	/**
	 * Scan for valid directories containing "Modulefile" or "metadata.json" files.
	 * A directory that contains such a file will not be scanned in turn.
	 * 
	 * @return A list of directories where such files were found
	 */
	protected List<File> findModuleRoots() {
		List<File> moduleRoots = new ArrayList<File>();
		if(findModuleFiles(modulesRoot.listFiles(), moduleRoots))
			// The repository is a module in itself
			moduleRoots.add(modulesRoot);
		return moduleRoots;
	}
}
