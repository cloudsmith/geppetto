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

import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;
import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
import org.cloudsmith.geppetto.pp.dsl.pptp.PptpRubyRuntimeModule;
import org.cloudsmith.geppetto.pp.dsl.ui.jdt_ersatz.ImagesOnFileSystemRegistry;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import org.osgi.framework.BundleContext;

import com.google.common.collect.Maps;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Adds support for PPTP Ruby by creating injectors and caching them using a language key
 * 
 */
public class PPDSLActivator extends PPActivator {
	public static final String PP_LANGUAGE_NAME = "org.cloudsmith.geppetto.pp.dsl.PP";

	private static BundleContext slaActivatorContext;

	public static PPDSLActivator getDefault() {
		return (PPDSLActivator) getInstance();
	}

	/**
	 * org.eclipse.jdt.core is added as an optional dependency in o.c.g.pp.dsl.ui and if JDT is present in
	 * the runtime, there is no need for the AggregateErrorLabel to do anything.
	 * 
	 * @return true if JDT is present.
	 */
	public static boolean isJavaEnabled() {
		if(slaActivatorContext == null)
			return false;
		try {
			slaActivatorContext.getBundle().loadClass("org.eclipse.jdt.core.JavaCore");
			return true;
		}
		catch(Throwable e) {
		}
		return false;
	}

	private Map<String, Injector> injectors = Collections.synchronizedMap(Maps.<String, Injector> newHashMapWithExpectedSize(1));

	private ImagesOnFileSystemRegistry imagesOnFileSystemRegistry;

	public ImagesOnFileSystemRegistry getImagesOnFSRegistry() {
		return imagesOnFileSystemRegistry;
	}

	@Override
	public Injector getInjector(String language) {
		if(ORG_CLOUDSMITH_GEPPETTO_PP_DSL_PP.equals(language))
			return super.getInjector(language);

		synchronized(injectors) {
			Injector injector = injectors.get(language);
			if(injector == null) {
				injectors.put(language, injector = createInjector(language));
			}
			return injector;
		}
	}

	public Injector getPPInjector() {
		return this.getInjector(PP_LANGUAGE_NAME);
	}

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
			return new PptpUIModule();
		return super.getRuntimeModule(grammar);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.internal.PPActivator#getUiModule(java.lang.String)
	 */
	@Override
	protected Module getUiModule(String grammar) {
		if(PPDSLConstants.PPTP_LANGUAGE_NAME.equals(grammar))
			return new PptpUIModule(); // Modules.EMPTY_MODULE;
		else if(PPDSLConstants.PPTP_RUBY_LANGUAGE_NAME.equals(grammar))
			return new PptpRubyUIModule();

		return super.getUiModule(grammar);
	}

	protected void registerInjectorFor(String language) throws Exception {
		injectors.put(language, createInjector(language));
		// override(override(getRuntimeModule(language)).with(getSharedStateModule())).with(getUiModule(language))));
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		slaActivatorContext = context;
		try {
			registerInjectorFor(PPDSLConstants.PPTP_RUBY_LANGUAGE_NAME);
			registerInjectorFor(PPDSLConstants.PPTP_LANGUAGE_NAME);
		}
		catch(Exception e) {
			Logger.getLogger(getClass()).error(e.getMessage(), e);
			throw e;
		}
		imagesOnFileSystemRegistry = new ImagesOnFileSystemRegistry();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		PPPreferencesHelper preferenceHelper = getInjector(PP_LANGUAGE_NAME).getInstance(PPPreferencesHelper.class);
		preferenceHelper.stop();
		slaActivatorContext = null;
		imagesOnFileSystemRegistry.dispose();
		super.stop(context);
	}
}
