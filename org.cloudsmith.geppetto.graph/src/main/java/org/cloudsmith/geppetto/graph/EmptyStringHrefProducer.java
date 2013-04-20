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
package org.cloudsmith.geppetto.graph;

import java.io.File;

import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences.Export;
import org.cloudsmith.geppetto.validation.runner.MetadataInfo;
import org.eclipse.core.runtime.IPath;

/**
 * A HREF provider that does not provide any links.
 * 
 */
public class EmptyStringHrefProducer implements IHrefProducer {

	@Override
	public String href(Export e, File root) {
		return "";
	}

	@Override
	public String href(MetadataInfo moduleInfo, File root) {
		return "";
	}

	@Override
	public String hrefForEdge(String idFrom, String idTo, boolean splitEdge) {
		return "";
	}

	@Override
	public String hrefForEdgeToPptp(String idFrom) {
		return "";
	}

	@Override
	public String hrefForEdgeToUnresolved(String idFrom, boolean splitEdge) {
		return "";
	}

	@Override
	public String hrefForUnresolved(ModuleName fromModuleName, String name) {
		return "";
	}

	@Override
	public String hrefToManifest(IPath f, IPath root, int line) {
		return "";
	}

}
