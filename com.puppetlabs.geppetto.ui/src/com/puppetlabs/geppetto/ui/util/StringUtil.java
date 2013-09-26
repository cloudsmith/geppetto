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

import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.v1.model.ModuleInfo;

public class StringUtil {

	public static String getModuleText(ModuleInfo module) {
		StringBuilder bld = new StringBuilder();
		if(module.getFullName() != null) {
			module.getFullName().withSeparator('-').toString(bld);
			bld.append(' ');
		}
		if(module.getVersion() != null)
			module.getVersion().toString(bld);
		return bld.toString();
	}

	public static String getUser(ModuleInfo module) {
		ModuleName fullName = module.getFullName();
		return fullName == null
				? null
				: fullName.getOwner();
	}

}
