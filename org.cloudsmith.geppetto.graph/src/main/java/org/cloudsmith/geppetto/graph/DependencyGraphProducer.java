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
package org.cloudsmith.geppetto.graph;

import java.io.File;
import java.io.OutputStream;

import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences;
import org.cloudsmith.geppetto.validation.runner.BuildResult;
import org.cloudsmith.geppetto.validation.runner.MetadataInfo;
import org.cloudsmith.graph.ICancel;

import com.google.common.collect.Multimap;

/**
 * Producer of dot graphs
 */
public interface DependencyGraphProducer {
	void produceGraph(ICancel cancel, String title, File[] roots, OutputStream output, BuildResult buildResult);

	void produceGraph(ICancel cancel, String title, File[] roots, OutputStream output, File root,
			Multimap<ModuleName, MetadataInfo> moduleData, AllModuleReferences exportData);
}
