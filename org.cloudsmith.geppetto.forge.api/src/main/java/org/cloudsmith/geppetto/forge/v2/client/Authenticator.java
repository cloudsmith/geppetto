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
package org.cloudsmith.geppetto.forge.v2.client;

import java.io.IOException;

public interface Authenticator {
	interface AuthResponse {
		String getScopes();

		String getToken();
	}

	AuthResponse authenticate(String user, String password) throws IOException;
}
