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
package com.puppetlabs.geppetto.forge.maven.plugin;

import java.io.FileFilter;

import org.apache.maven.project.MavenProject;
import com.puppetlabs.geppetto.forge.MetadataExtractor;
import com.puppetlabs.geppetto.forge.impl.ForgeModule;

import com.google.inject.multibindings.Multibinder;

public class ForgeMavenModule extends ForgeModule {
	private final FileFilter fileFilter;

	private final MavenProject mavenProject;

	public ForgeMavenModule(FileFilter fileFilter, MavenProject mavenProject) {
		this.fileFilter = fileFilter;
		this.mavenProject = mavenProject;
	}

	@Override
	protected void addMetadataExtractors(Multibinder<MetadataExtractor> mdeBinder) {
		// We could theoretically add both but for now we only use the pomBased extractor
		// when there's complete absence of Modulefiles.
		super.addMetadataExtractors(mdeBinder);
		mdeBinder.addBinding().to(PomMetadataExtractor.class);
	}

	@Override
	protected void configure() {
		bind(MavenProject.class).toInstance(mavenProject);
		super.configure();
	}

	@Override
	protected FileFilter getFileFilter() {
		return fileFilter;
	}
}
