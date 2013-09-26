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
package com.puppetlabs.geppetto.graph;

import java.io.File;
import java.io.OutputStream;

import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.validation.runner.AllModuleReferences;
import com.puppetlabs.geppetto.validation.runner.BuildResult;
import com.puppetlabs.geppetto.validation.runner.MetadataInfo;
import com.puppetlabs.graph.ICancel;
import com.google.common.collect.Multimap;

/**
 * Producer of dot graphs
 */
public interface DependencyGraphProducer {
	void produceGraph(ICancel cancel, String title, File[] roots, OutputStream output, BuildResult buildResult,
			Diagnostic chain);

	void produceGraph(ICancel cancel, String title, File[] roots, OutputStream output, File root,
			Multimap<ModuleName, MetadataInfo> moduleData, AllModuleReferences exportData, Diagnostic chain);
}
