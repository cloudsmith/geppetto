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
package org.cloudsmith.geppetto.pp.dsl.ui.internal;

import org.cloudsmith.geppetto.pp.dsl.pptp.PptpRubyRuntimeModule;
import org.cloudsmith.geppetto.pp.dsl.ui.pptp.PptpResourceUiServiceProvider;

/**
 * UI Runtime module for Ruby PPTP variant.
 * 
 */
public class PptpRubyUIModule extends PptpRubyRuntimeModule {

	public Class<? extends org.eclipse.xtext.ui.resource.IResourceUIServiceProvider> bindIResourceUIServiceProvider() {
		return PptpResourceUiServiceProvider.class;
		// return org.eclipse.xtext.ui.resource.DefaultResourceUIServiceProvider.class;
	}

	// must bind a description label provider...
	public void configureResourceUIServiceLabelProvider(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.jface.viewers.ILabelProvider.class).annotatedWith(
			org.eclipse.xtext.ui.resource.ResourceServiceDescriptionLabelProvider.class).to(
			org.cloudsmith.geppetto.pp.dsl.ui.labeling.PPDescriptionLabelProvider.class);
	}

}
