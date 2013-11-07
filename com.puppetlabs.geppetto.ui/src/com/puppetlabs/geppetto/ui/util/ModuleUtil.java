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
package com.puppetlabs.geppetto.ui.util;

import java.net.URI;

import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.v3.model.Module;
import com.puppetlabs.geppetto.forge.v3.model.Release;
import com.puppetlabs.geppetto.semver.Version;

public class ModuleUtil {

	public static ModuleName getFullName(Module module) {
		URI uri = module.getUri();
		if(uri != null) {
			String path = uri.getPath();
			if(path != null) {
				int lastSlashIdx = path.lastIndexOf('/');
				if(lastSlashIdx >= 0)
					return ModuleName.fromString(path.substring(lastSlashIdx + 1));
			}
		}
		return null;
	}

	public static Version getModuleVersion(Module module) {
		Release release = module.getCurrentRelease();
		return release == null
				? null
				: release.getVersion();
	}

	public static String getText(Module module) {
		StringBuilder bld = new StringBuilder();
		ModuleName fullName = getFullName(module);
		if(fullName != null)
			fullName.toString(bld);

		Version version = getModuleVersion(module);
		if(version != null) {
			bld.append(' ');
			version.toString(bld);
		}
		return bld.toString();
	}

	public static String getUser(Module module) {
		ModuleName fullName = getFullName(module);
		return fullName == null
				? null
				: fullName.getOwner();
	}
}
