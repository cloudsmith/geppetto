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
package com.puppetlabs.geppetto.ruby.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;

/**
 * Factory for a Ruby (.rb) resource for translation of puppet specific ruby
 * code to puppet "target platform" model.
 * 
 */
public class PptpRubyResourceFactory implements Factory {

	@Override
	public Resource createResource(URI uri) {
		return new PptpRubyResource(uri);
	}

}
