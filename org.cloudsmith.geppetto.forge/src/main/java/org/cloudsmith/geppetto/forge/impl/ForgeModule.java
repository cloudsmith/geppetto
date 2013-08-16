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

import static com.google.inject.name.Names.named;
import static org.cloudsmith.geppetto.forge.Forge.MODULE_FILE_FILTER;
import static org.cloudsmith.geppetto.forge.util.ModuleUtils.DEFAULT_FILE_FILTER;

import java.io.FileFilter;

import org.cloudsmith.geppetto.forge.ERB;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.MetadataExtractor;
import org.cloudsmith.geppetto.forge.client.GsonModule;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class ForgeModule extends AbstractModule {
	protected void addMetadataExtractors(Multibinder<MetadataExtractor> mdeBinder) {
		mdeBinder.addBinding().to(MetadataJSONExtractor.class);
		mdeBinder.addBinding().to(ModulefileExtractor.class);
	}

	@Override
	protected void configure() {
		install(GsonModule.INSTANCE);
		bind(Forge.class).to(ForgeImpl.class);
		bind(ERB.class).to(ERBImpl.class);
		bind(FileFilter.class).annotatedWith(named(MODULE_FILE_FILTER)).toInstance(getFileFilter());
		Multibinder<MetadataExtractor> mdeBinder = Multibinder.newSetBinder(binder(), MetadataExtractor.class);
		addMetadataExtractors(mdeBinder);
	}

	protected FileFilter getFileFilter() {
		return DEFAULT_FILE_FILTER;
	}
}
