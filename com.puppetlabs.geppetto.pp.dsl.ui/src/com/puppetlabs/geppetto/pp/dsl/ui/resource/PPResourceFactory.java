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
package com.puppetlabs.geppetto.pp.dsl.ui.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IResourceFactory;
import org.eclipse.xtext.resource.XtextResource;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * A Provider of a PP specific Resource
 * 
 */
public class PPResourceFactory implements IResourceFactory {
	private Provider<PPResource> provider;

	@Inject
	public PPResourceFactory(Provider<PPResource> resourceProvider) {
		this.provider = resourceProvider;
	}

	public Resource createResource(URI uri) {
		XtextResource xtextResource = provider.get();
		xtextResource.setURI(uri);
		return xtextResource;
	}

}
