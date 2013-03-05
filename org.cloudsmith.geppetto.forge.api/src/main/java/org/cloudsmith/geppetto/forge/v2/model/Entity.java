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
package org.cloudsmith.geppetto.forge.v2.model;

import org.cloudsmith.geppetto.forge.v2.client.GsonProvider;

/**
 * Super class of all model entitites. Provides basic JSON capability.
 */
public class Entity {
	/**
	 * Produces a JSON representation of the Entity
	 * 
	 * @return The JSON string
	 */
	@Override
	public final String toString() {
		return GsonProvider.toJSON(this);
	}
}
