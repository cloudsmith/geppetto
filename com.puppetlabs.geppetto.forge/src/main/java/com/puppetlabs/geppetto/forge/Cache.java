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

import java.io.File;
import java.io.IOException;

import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.semver.Version;

/**
 * A local cache that sits in front of the Forge to avoid multiple downloads
 * of the same version of a module.
 */
public interface Cache {
	/**
	 * Delete all cached files.
	 * 
	 * @throws IOException
	 */
	void clean() throws IOException;

	/**
	 * Returns the location of this cache.
	 * 
	 * @return The directory where all files are cached.
	 */
	File getLocation();

	/**
	 * Retrieve the file for the given version of a module. If the file
	 * is found locally then that file is return. Otherwise an attempt
	 * is made to download the file from the remote Forge service.
	 * 
	 * @param qname
	 * @param version
	 * @return
	 * @throws IOException
	 */
	File retrieve(ModuleName qname, Version version) throws IOException;
}
