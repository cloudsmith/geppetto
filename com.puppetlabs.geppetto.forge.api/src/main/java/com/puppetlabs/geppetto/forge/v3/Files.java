/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 * 
 */
package com.puppetlabs.geppetto.forge.v3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.puppetlabs.geppetto.forge.model.VersionedName;

/**
 * Endpoint capable of performing downloads
 */
public interface Files {

	/**
	 * @param owner
	 *            The Module owner.
	 * @param name
	 *            The name of the Module.
	 * @param version
	 *            The version of the module Release
	 * @return The content of a particular release
	 * @throws IOException
	 */
	InputStream download(VersionedName release) throws IOException;

	/**
	 * @param owner
	 *            The Module owner.
	 * @param name
	 *            The name of the Module.
	 * @param version
	 *            The version of the module Release
	 * @param output
	 *            The stream that will receive the file content
	 * @return The content of a particular release
	 * @throws IOException
	 */
	void download(VersionedName release, OutputStream output) throws IOException;

}
