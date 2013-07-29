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
package org.cloudsmith.geppetto.forge;

import org.cloudsmith.geppetto.forge.client.ForgeAPIPreferences;

public interface ForgePreferences extends ForgeAPIPreferences {
	/**
	 * <p>
	 * Returns the location of the local Module cache. The cache is used to avoid multiple downloads of the same file.
	 * </p>
	 * <p>
	 * The default value for this preference is &lt;user home&gt;/.puppet/var/puppet-module/cache/&lt;MD5 hash of remote URL&gt;
	 * </p>
	 * 
	 * @return The location of the module cache.
	 */
	public String getCacheLocation();
}
