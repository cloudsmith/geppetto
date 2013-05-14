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
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
import org.cloudsmith.geppetto.pp.dsl.pptp.PptpRubyRuntimeModule;
import org.cloudsmith.geppetto.pp.dsl.ui.jdt_ersatz.ImagesOnFileSystemRegistry;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.cloudsmith.geppetto.ruby.jrubyparser.JRubyServices;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.xtext.util.Modules2;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.google.common.collect.Maps;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Adds support for PPTP Ruby by creating injectors and caching them using a language key
 * 
 */
public class PPDSLActivator extends PPActivator {
	public static final String PP_LANGUAGE_NAME = "org.cloudsmith.geppetto.pp.dsl.PP";

	private static BundleContext slaActivatorContext;

	private static final Logger logger = Logger.getLogger(PPDSLActivator.class);

	private static final QualifiedName LAST_BUILDER_VERSION = new QualifiedName(
		"org.cloudsmith.geppetto.dsl.ui", "builder.version");

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

	@Override
	protected Injector createInjector(String language) {
		try {
			Module runtimeModule = getRuntimeModule(language);
			Module sharedStateModule = getSharedStateModule();
			Module uiModule = getUiModule(language);
			Module forgeModule = getForgeModule();
			Module mergedModule = Modules2.mixin(runtimeModule, sharedStateModule, uiModule, forgeModule);
			return Guice.createInjector(mergedModule);
		}
		catch(Exception e) {
			logger.error("Failed to create injector for " + language);
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Failed to create injector for " + language, e);
		}
	}

	protected Module getForgeModule() {
		return ForgeService.getDefault().getForgeModule();
	}

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
		RubyHelper.setRubyServicesFactory(JRubyServices.FACTORY);

		String lastBuilderVersion = ResourcesPlugin.getWorkspace().getRoot().getPersistentProperty(LAST_BUILDER_VERSION);
		final Bundle bundle = context.getBundle();
		String currentVersion = bundle.getVersion().toString();
		if(lastBuilderVersion == null || !lastBuilderVersion.equals(currentVersion)) {
			// Workspace was built using another version of Geppetto so schedule a
			// clean rebuild
			Job buildJob = new Job("Workspace build") {
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						IWorkspace ws = ResourcesPlugin.getWorkspace();
						ws.build(IncrementalProjectBuilder.FULL_BUILD, monitor);
						ws.getRoot().setPersistentProperty(LAST_BUILDER_VERSION, bundle.getVersion().toString());
						return Status.OK_STATUS;
					}
					catch(CoreException e) {
						return e.getStatus();
					}
				}
			};
			buildJob.schedule();
		}
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
