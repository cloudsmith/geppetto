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
package com.puppetlabs.geppetto.graph;

import java.io.File;

import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.validation.runner.AllModuleReferences.Export;
import com.puppetlabs.geppetto.validation.runner.MetadataInfo;

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
