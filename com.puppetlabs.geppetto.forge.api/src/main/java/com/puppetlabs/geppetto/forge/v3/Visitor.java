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
import java.lang.reflect.InvocationTargetException;

import com.puppetlabs.geppetto.forge.model.Entity;
import com.puppetlabs.geppetto.forge.v3.ForgeService.Compare;
import com.puppetlabs.geppetto.forge.v3.impl.AbstractForgeService;

/**
 * Visitor used by the {@link AbstractForgeService#accept(Compare, SortBy, boolean, Visitor)} method.
 * 
 * @param <T>
 */
public interface Visitor<T extends Entity> {
	/**
	 * Visit the given entity.
	 * 
	 * @param entity
	 *            The entity to visit
	 * @throws IOException
	 */
	void visit(T entity, ProgressMonitor progressMonitor) throws InvocationTargetException;
}
