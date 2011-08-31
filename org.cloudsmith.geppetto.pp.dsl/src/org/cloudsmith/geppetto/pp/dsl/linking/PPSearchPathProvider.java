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
package org.cloudsmith.geppetto.pp.dsl.linking;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.google.inject.Singleton;

/**
 * @author henrik
 * 
 */
@Singleton
public class PPSearchPathProvider implements PPSearchPath.ISearchPathProvider {

	private String defaultPath;

	private URI pptpContainer;

	private URI rootDirectory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider#configure(org.eclipse.emf.common.util.URI,
	 * org.eclipse.emf.common.util.URI)
	 */
	@Override
	public void configure(URI pptpContainer, URI rootDirectory, String defaultPath) {
		if(defaultPath == null)
			defaultPath = "*";
		this.defaultPath = defaultPath;
		this.rootDirectory = rootDirectory;
		this.pptpContainer = pptpContainer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider#get(org.eclipse.emf.ecore.resource.Resource)
	 */
	@Override
	public PPSearchPath get(Resource r) {
		// TODO: construct entry for pptp
		return PPSearchPath.fromString(defaultPath, rootDirectory);
	}
}
