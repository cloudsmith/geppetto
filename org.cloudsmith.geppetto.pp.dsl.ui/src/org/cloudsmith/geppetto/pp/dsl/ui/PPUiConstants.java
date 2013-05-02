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
	String PLUGIN_ID = "org.cloudsmith.geppetto.pp.dsl.ui";

	String DEBUG_OPTION_MODULEFILE = PLUGIN_ID + "/debug/modulefile";

	QualifiedName PROJECT_PROPERTY_MODULEVERSION = new QualifiedName(PPUiConstants.PLUGIN_ID, "version");

	QualifiedName PROJECT_PROPERTY_MODULENAME = new QualifiedName(PPUiConstants.PLUGIN_ID, "moduleName");

	String MODULEFIILE_NAME = "Modulefile";

	String METADATA_JSON_NAME = "metadata.json";

	IPath MODULEFILE_PATH = new Path(MODULEFIILE_NAME);

	IPath METADATA_JSON_PATH = new Path(METADATA_JSON_NAME);

	String PUPPET_MODULE_PROBLEM_MARKER_TYPE = "org.cloudsmith.geppetto.pp.dsl.ui.puppetModuleProblem";

	String PUPPET_TASK_MARKER_TYPE = "org.cloudsmith.geppetto.pp.dsl.ui.puppetTaskMarker";

	String MODULEFILE_BUILDER_ID = "org.cloudsmith.geppetto.pp.dsl.ui.modulefileBuilder";

	/**
	 * Name of hidden project that contains the target platform .pptp file.
	 */
	String PPTP_TARGET_PROJECT_NAME = ".org_cloudsmith_geppetto_pptp_target";

	/**
	 * Name of project to be deleted on startup (hack).
	 */
	String OLD_PPTP_TARGET_PROJECT_NAME = "org_cloudsmith_geppetto_pptp_target";

	String PUPPET_NATURE_ID = "org.cloudsmith.geppetto.pp.dsl.ui.puppetNature";

	String DEBUG_OPTION_PARSER = PLUGIN_ID + "/debug/parser";

}
