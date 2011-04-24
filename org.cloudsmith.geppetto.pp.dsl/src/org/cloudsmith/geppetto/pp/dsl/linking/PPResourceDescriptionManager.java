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

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionManager;

/**
 * Overrides the default to provide a PPResourceDescription instead of the default
 * 
 */
public class PPResourceDescriptionManager extends DefaultResourceDescriptionManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.impl.DefaultResourceDescriptionManager#internalGetResourceDescription(org.eclipse.emf.ecore.resource.Resource,
	 * org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy)
	 */
	@Override
	protected IResourceDescription internalGetResourceDescription(Resource resource,
			IDefaultResourceDescriptionStrategy strategy) {
		return new PPResourceDescription(resource, strategy, getCache());

	}
}
