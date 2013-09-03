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
package org.cloudsmith.geppetto.forge.v1.model;

import org.cloudsmith.geppetto.semver.Version;

import com.google.gson.annotations.Expose;

public class Release {
	@Expose
	private Version version;

	public Version getVersion() {
		return version;
	}
}
