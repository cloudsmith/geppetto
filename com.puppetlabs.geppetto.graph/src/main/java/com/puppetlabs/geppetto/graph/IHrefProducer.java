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
package org.cloudsmith.geppetto.graph;

import java.io.File;

import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences.Export;
import org.cloudsmith.geppetto.validation.runner.MetadataInfo;
import org.eclipse.core.runtime.IPath;

/**
 * Interface for Object to HREF.
 * 
 */
public interface IHrefProducer {
	/**
	 * HREF to an Export.
	 * 
	 * @param e
	 * @param root
	 * @return
	 */
	public String href(Export e, File root);

	/**
	 * HREF to a Module.
	 * 
	 * @param e
	 * @param root
	 * @return
	 */
	public String href(MetadataInfo moduleInfo, File root);

	/**
	 * A href for an edge from idFrom to idTo.
	 * 
	 * @param splitEdge
	 * 
	 * @return
	 */
	public String hrefForEdge(String idFrom, String idTo, boolean splitEdge);

	/**
	 * A href for an edge from idFrom to puppet runtime.
	 * 
	 * @return
	 */
	public String hrefForEdgeToPptp(String idFrom);

	/**
	 * A href for an edge from idFrom to an unresolved module
	 * 
	 * @param splitEdge
	 * 
	 * @return
	 */
	public String hrefForEdgeToUnresolved(String idFrom, boolean splitEdge);

	/**
	 * HREF to an unresolved name in the given module. This information can be used to lookup all
	 * the actual references to the unresolved name from locations within the module. As it is not possible
	 * to get a single link it is expected that only a "smart" javascript based reference makes use of
	 * this method.
	 * 
	 * @param fromModuleName - the name of the module containing unresolved references
	 * @param name - the name of the unresolved
	 * @return
	 */
	public String hrefForUnresolved(ModuleName fromModuleName, String name);

	/**
	 * HREF to a Manifest
	 * 
	 * @param f
	 *        absolute path to the file (on some remote system)
	 * @param root
	 *        absolute path to the root of files (on some remote system)
	 * @param line
	 *        line number in the given file f
	 * @return
	 */
	public String hrefToManifest(IPath f, IPath root, int line);

}
