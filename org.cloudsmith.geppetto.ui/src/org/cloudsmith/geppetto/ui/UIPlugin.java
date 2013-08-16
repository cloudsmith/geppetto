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
package org.cloudsmith.geppetto.ui;

import static org.cloudsmith.geppetto.pp.dsl.ui.internal.PPActivator.ORG_CLOUDSMITH_GEPPETTO_PP_DSL_PP;

import java.text.MessageFormat;

import org.cloudsmith.geppetto.forge.client.ForgeHttpModule;
import org.cloudsmith.geppetto.forge.impl.ForgeServiceModule;
import org.cloudsmith.geppetto.pp.dsl.ui.internal.PPActivator;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.PPPreferencesHelper;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.ui.EclipseUIPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;

/**
 * This is the central singleton for the Geppetto UI plugin.
 */
public final class UIPlugin extends EMFPlugin implements BundleActivator {
	private Injector injector;

	private BundleContext context;

	private EclipseUIPlugin plugin;

	private static UIPlugin INSTANCE;

	public static Image createImage(String file) {
		return getImageDesc(file).createImage();
	}

	public static IStatus createStatus(int severity, String message, Exception e) {
		return new Status(severity, getInstance().getContext().getBundle().getSymbolicName(), message, e);
	}

	public static ImageDescriptor getImageDesc(String file) {
		return ExtendedImageRegistry.INSTANCE.getImageDescriptor(getInstance().getImage(file));
	}

	public static UIPlugin getInstance() {
		UIPlugin instance = INSTANCE;
		if(instance == null)
			throw new IllegalStateException("Bundle is not active");
		return instance;
	}

	public static String getLocalString(String key, Object... params) {
		String msg = getInstance().getString(key);
		return params.length == 0
				? msg
				: MessageFormat.format(msg, params);
	}

	public static void logException(String message, Exception e) {
		UIPlugin instance = INSTANCE;
		if(instance == null) {
			e.printStackTrace();
			return;
		}
		Bundle bundle = instance.getContext().getBundle();
		Platform.getLog(bundle).log(new Status(IStatus.ERROR, bundle.getSymbolicName(), message, e));
	}

	public UIPlugin() {
		super(new ResourceLocator[] {});
	}

	public BundleContext getContext() {
		return context;
	}

	public Injector getInjector() {
		synchronized(this) {
			if(injector == null) {
				final Injector ppInjector = PPActivator.getInstance().getInjector(ORG_CLOUDSMITH_GEPPETTO_PP_DSL_PP);
				injector = ppInjector.createChildInjector(new ForgeServiceModule(), new ForgeHttpModule() {
					@Override
					protected String getBaseURL() {
						return ppInjector.getInstance(PPPreferencesHelper.class).getForgeURI();
					}
				});
			}
		}
		return injector;
	}

	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	@Override
	public void start(BundleContext context) throws Exception {
		this.context = context;
		this.plugin = new EclipseUIPlugin() {
		};
		INSTANCE = this;
	}

	@Override
	public synchronized void stop(BundleContext context) throws Exception {
		INSTANCE = null;
		plugin = null;
		injector = null;
		context = null;
	}
}
