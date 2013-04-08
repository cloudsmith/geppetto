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

import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.semver.Version;

public class ModuleInfo {
	private ModuleName name;

	private Version version;

	public ModuleInfo(ModuleName name, Version version) {
		super();
		this.name = name;
		this.version = version;
	}

	public ModuleName getName() {
		return name;
	}

	public Version getVersion() {
		return version;
	}

	public void setName(ModuleName name) {
		this.name = name;
	}

	public void setVersion(Version version) {
		this.version = version;
	}
}
