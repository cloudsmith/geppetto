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
package org.cloudsmith.geppetto.forge.v2;

import org.cloudsmith.geppetto.forge.v2.client.ForgeHttpModule;
import org.cloudsmith.geppetto.forge.v2.client.ForgePreferences;
import org.cloudsmith.geppetto.forge.v2.service.ModuleService;
import org.cloudsmith.geppetto.forge.v2.service.ReleaseService;
import org.cloudsmith.geppetto.forge.v2.service.TagService;
import org.cloudsmith.geppetto.forge.v2.service.UserService;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author thhal
 * 
 */
public class Forge {
	private final Injector injector;

	public Forge(final ForgePreferences forgePreferences) {
		this(Guice.createInjector(new ForgeHttpModule() {
			@Override
			protected ForgePreferences getForgePreferences() {
				return forgePreferences;
			}
		}));
	}

	public Forge(Injector injector) {
		this.injector = injector;
	}

	public Gson createGson() {
		return injector.getInstance(Gson.class);
	}

	public MetadataRepository createMetadataRepository() {
		return injector.getInstance(MetadataRepository.class);
	}

	public ModuleService createModuleService() {
		return injector.getInstance(ModuleService.class);
	}

	public ReleaseService createReleaseService() {
		return injector.getInstance(ReleaseService.class);
	}

	public TagService createTagService() {
		return injector.getInstance(TagService.class);
	}

	public UserService createUserService() {
		return injector.getInstance(UserService.class);
	}
}
