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
package org.cloudsmith.geppetto.forge.impl;

import org.cloudsmith.geppetto.forge.ForgePreferences;
import org.cloudsmith.geppetto.forge.client.ForgeAPIPreferencesBean;

public class ForgePreferencesBean extends ForgeAPIPreferencesBean implements ForgePreferences {
	private static final long serialVersionUID = 1L;

	private String cacheLocation;

	public ForgePreferencesBean() {
	}

	public ForgePreferencesBean(ForgePreferences copy) {
		super(copy);
		this.cacheLocation = copy.getCacheLocation();
	}

	@Override
	public String getCacheLocation() {
		return cacheLocation;
	}

	public void setCacheLocation(String cacheLocation) {
		this.cacheLocation = cacheLocation;
	}
}
