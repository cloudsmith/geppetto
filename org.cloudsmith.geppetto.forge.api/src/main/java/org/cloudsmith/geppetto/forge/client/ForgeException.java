/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.forge.client;

import java.io.IOException;

/**
 * Exception thrown by the API methods
 */
public class ForgeException extends IOException {
	private static final long serialVersionUID = 1L;

	public ForgeException(String message) {
		super(message);
	}

	public ForgeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ForgeException(Throwable cause) {
		super(cause);
	}
}
