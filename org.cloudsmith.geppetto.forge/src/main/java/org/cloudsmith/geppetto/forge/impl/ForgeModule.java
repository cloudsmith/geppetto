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
package org.cloudsmith.geppetto.forge.impl;

import java.io.FileFilter;

import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.ERB;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.ForgePreferences;
import org.cloudsmith.geppetto.forge.MetadataExtractor;
import org.cloudsmith.geppetto.forge.client.ForgeHttpModule;
import org.cloudsmith.geppetto.forge.util.ModuleUtils;

import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

public class ForgeModule extends ForgeHttpModule {
	public ForgeModule(ForgePreferences forgePreferences) {
		super(forgePreferences);
	}

	protected void addMetadataExtractors(Multibinder<MetadataExtractor> mdeBinder) {
		mdeBinder.addBinding().to(MetadataJSONExtractor.class);
		mdeBinder.addBinding().to(ModulefileExtractor.class);
	}

	@Override
	protected void configure() {
		super.configure();
		bind(Forge.class).to(ForgeImpl.class);
		if(getPreferences().getBaseURL() != null)
			bind(Cache.class).to(CacheImpl.class);

		bind(ERB.class).to(ERBImpl.class);
		bind(FileFilter.class).annotatedWith(Names.named(Forge.MODULE_FILE_FILTER)).toInstance(getFileFilter());
		bind(ForgePreferences.class).toInstance((ForgePreferences) getPreferences());
		Multibinder<MetadataExtractor> mdeBinder = Multibinder.newSetBinder(binder(), MetadataExtractor.class);
		addMetadataExtractors(mdeBinder);
	}

	protected FileFilter getFileFilter() {
		return ModuleUtils.DEFAULT_FILE_FILTER;
	}
}
