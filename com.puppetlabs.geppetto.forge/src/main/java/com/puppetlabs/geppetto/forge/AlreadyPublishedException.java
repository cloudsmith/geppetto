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

import org.cloudsmith.geppetto.forge.client.ForgeException;

/**
 * Exception thrown when an attempt is made to publish a module that has already
 * been published at its current version
 */
public class AlreadyPublishedException extends ForgeException {
	private static final long serialVersionUID = 1L;

	public AlreadyPublishedException(String msg) {
		super(msg);
	}
}
