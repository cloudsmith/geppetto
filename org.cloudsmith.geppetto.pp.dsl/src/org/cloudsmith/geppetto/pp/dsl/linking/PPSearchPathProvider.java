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

import org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath.IConfigurableProvider;
import org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;

import com.google.inject.Singleton;

/**
 * Implementation of an {@link ISearchPathProvider} that returns a path based on a default path.
 * 
 */
@Singleton
public class PPSearchPathProvider implements ISearchPathProvider, IConfigurableProvider {

	private String defaultPath;

	private URI rootDirectory;

	private String environment;

	public PPSearchPathProvider() {
		defaultPath = "*";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider#configure(org.eclipse.emf.common.util.URI,
	 * org.eclipse.emf.common.util.URI)
	 */
	@Override
	public void configure(URI rootDirectory, String defaultPath, String environment) {
		if(defaultPath == null)
			defaultPath = "*";
		this.defaultPath = defaultPath;
		if(rootDirectory == null)
			throw new IllegalArgumentException("root directory must be specified");

		this.rootDirectory = rootDirectory;
		if(environment == null)
			environment = "production";
		this.environment = environment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider#get(org.eclipse.emf.ecore.resource.Resource)
	 */
	@Override
	public PPSearchPath get(Resource r) {
		return PPSearchPath.fromString(defaultPath, rootDirectory).evaluate(environment);
	}
}
