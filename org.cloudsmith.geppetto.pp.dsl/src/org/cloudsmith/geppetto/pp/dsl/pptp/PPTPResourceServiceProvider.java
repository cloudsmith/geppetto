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
package org.cloudsmith.geppetto.pp.dsl.pptp;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.parser.IEncodingProvider;
import org.eclipse.xtext.resource.IResourceDescription.Manager;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.validation.IResourceValidator;

/**
 * A Resource Provider for PPTP models (Puppet Target Platform).
 * 
 */
public class PPTPResourceServiceProvider implements IResourceServiceProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.IResourceServiceProvider#canHandle(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public boolean canHandle(URI uri) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.IResourceServiceProvider#get(java.lang.Class)
	 */
	@Override
	public <T> T get(Class<T> t) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.IResourceServiceProvider#getContainerManager()
	 */
	@Override
	public org.eclipse.xtext.resource.IContainer.Manager getContainerManager() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.IResourceServiceProvider#getEncodingProvider()
	 */
	@Override
	public IEncodingProvider getEncodingProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.IResourceServiceProvider#getResourceDescriptionManager()
	 */
	@Override
	public Manager getResourceDescriptionManager() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Return a 'null' validator (i.e. 'nothing to validate').
	 */
	@Override
	public IResourceValidator getResourceValidator() {
		return IResourceValidator.NULL;
	}

}
