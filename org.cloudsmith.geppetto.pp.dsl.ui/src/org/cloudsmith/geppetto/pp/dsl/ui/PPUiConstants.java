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
package org.cloudsmith.geppetto.pp.dsl.ui;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;

/**
 * Constants for PP Ui
 * 
 */
public interface PPUiConstants {
	public static final String PLUGIN_ID = "org.cloudsmith.geppetto.pp.dsl.ui";

	public static final String DEBUG_OPTION_MODULEFILE = PLUGIN_ID + "/debug/modulefile";

	public static final QualifiedName PROJECT_PROPERTY_MODULEVERSION = new QualifiedName(PPUiConstants.PLUGIN_ID, "version");

	public static final QualifiedName PROJECT_PROPERTY_MODULENAME = new QualifiedName(PPUiConstants.PLUGIN_ID, "moduleName");

	public static final String MODULEFIILE_NAME = "Modulefile";

	public static final IPath MODULEFILE_PATH = new Path("Modulefile");

	public static final String PUPPET_MODULE_PROBLEM_MARKER_TYPE = "org.cloudsmith.geppetto.pp.dsl.ui.puppetModuleProblem";

	public static final String MODULEFILE_BUILDER_ID = "org.cloudsmith.geppetto.pp.dsl.ui.modulefileBuilder";
}
