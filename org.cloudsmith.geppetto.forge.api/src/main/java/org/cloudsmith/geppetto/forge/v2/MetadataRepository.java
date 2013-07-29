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
package org.cloudsmith.geppetto.forge.v2;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.forge.v2.model.Release;
import org.cloudsmith.geppetto.semver.Version;

/**
 * This class provides the methods needed to perform dependency resolution.
 */
public interface MetadataRepository {
	/**
	 * Perform a deep resolution to get all modules in the transitive scope that
	 * extends from the given <code>depenency</code>.
	 * 
	 * @param dependency
	 *            The root of the transitive scope to resolve
	 * @param unresolvedCollector
	 *            Set that will received all dependencies that could not be resolved
	 * @return The list of resolved modules.
	 */
	Collection<Release> deepResolve(Dependency dependency, Set<Dependency> unresolvedCollector) throws IOException;

	/**
	 * Return the best candidate for the given dependency
	 * 
	 * @param dependency
	 * @return The release that is the best match for the dependency or <code>null</code> if no match was found
	 */
	Release resolve(Dependency dependency) throws IOException;

	/**
	 * Resolve an exact version of a module.
	 * 
	 * @param name
	 *            The name of the module
	 * @param version
	 *            The desired version
	 * @return The release that corresponds to the given module and version or <code>null</code> when no such release exists.
	 */
	Release resolve(ModuleName name, Version version) throws IOException;
}
