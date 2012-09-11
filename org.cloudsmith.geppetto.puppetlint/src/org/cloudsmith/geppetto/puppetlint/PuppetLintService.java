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
package org.cloudsmith.geppetto.puppetlint;

import org.cloudsmith.geppetto.puppetlint.impl.Activator;

/**
 * A service that enables files and folders to be examined by <a href="http://http://puppet-lint.com/">puppet-lint</a>.
 */
public abstract class PuppetLintService {
	/**
	 * @return The singleton instance of this service
	 */
	public static PuppetLintService getInstance() {
		return Activator.getInstance();
	}

	/**
	 * @return A new PuppetLintRunner
	 */
	public abstract PuppetLintRunner getPuppetLintRunner();
}
