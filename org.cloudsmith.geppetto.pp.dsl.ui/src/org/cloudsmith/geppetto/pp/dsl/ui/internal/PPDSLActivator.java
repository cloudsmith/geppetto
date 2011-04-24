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
package org.cloudsmith.geppetto.pp.dsl.ui.internal;

import org.apache.log4j.Logger;
import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
import org.cloudsmith.geppetto.pp.dsl.pptp.PptpRubyRuntimeModule;
import org.cloudsmith.geppetto.pp.dsl.pptp.PptpRuntimeModule;
import org.osgi.framework.BundleContext;

import com.google.inject.Module;
import com.google.inject.util.Modules;

/**
 * Adds support for PPTP Ruby by creating injectors and caching them using a language key
 * 
 */
public class PPDSLActivator extends PPActivator {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.internal.PPActivator#getRuntimeModule(java.lang.String)
	 */
	@Override
	protected Module getRuntimeModule(String grammar) {
		if(PPDSLConstants.PPTP_RUBY_LANGUAGE_NAME.equals(grammar))
			return new PptpRubyRuntimeModule();
		else if(PPDSLConstants.PPTP_LANGUAGE_NAME.equals(grammar))
			return new PptpRuntimeModule();
		return super.getRuntimeModule(grammar);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.internal.PPActivator#getUiModule(java.lang.String)
	 */
	@Override
	protected Module getUiModule(String grammar) {
		if(PPDSLConstants.PPTP_RUBY_LANGUAGE_NAME.equals(grammar) || PPDSLConstants.PPTP_LANGUAGE_NAME.equals(grammar))
			return Modules.EMPTY_MODULE;

		return super.getUiModule(grammar);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		try {
			registerInjectorFor(PPDSLConstants.PPTP_RUBY_LANGUAGE_NAME);
			registerInjectorFor(PPDSLConstants.PPTP_LANGUAGE_NAME);
		}
		catch(Exception e) {
			Logger.getLogger(getClass()).error(e.getMessage(), e);
			throw e;
		}
	}
}
