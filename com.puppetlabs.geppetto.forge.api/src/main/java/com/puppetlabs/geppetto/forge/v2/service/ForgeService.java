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
package com.puppetlabs.geppetto.forge.v2.service;

import java.io.IOException;

import com.puppetlabs.geppetto.forge.v2.model.HalLink;

/**
 * Interface implemented by all v2 services
 */
public interface ForgeService {

	/**
	 * Cleanly abort the currently executing request. This method does nothing if there is
	 * no executing request.
	 */
	void abortCurrentRequest();

	/**
	 * Resolves a HAL link into a proper instance.
	 * 
	 * @param link
	 *            The link to resolve
	 * @param type
	 *            The type of the instance
	 * @return The resolved instance.
	 * @throws IOException
	 *             If the link could not be resolved.
	 */
	<T> T resolveLink(HalLink link, Class<T> type) throws IOException;

}
