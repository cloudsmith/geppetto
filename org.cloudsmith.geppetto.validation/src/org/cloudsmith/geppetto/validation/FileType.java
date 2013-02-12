/**
 * Copyright (c) 2013 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.validation;


/**
 * A representation of the literals of the enumeration '<em><b>File Type</b></em>',
 * and utility methods for working with them.
 */
public enum FileType {
	/**
	 * Detect if file is a single source file, or a directory, and if this directory has a "modules" subdirectory in it, interpret
	 * this director as
	 * being a PUPPET_ROOT, else a MODULE_ROOT.
	 */
	DETECT,
	
	/**
	 * The file must be a directory, and its modules are expected to be found in a "modules" subdirectory. Manifests are expected
	 * to be in a "manifests" directory, etc.
	 */
	PUPPET_ROOT,
	
	/**
	 * Expects the file to be a directory, and that it complies with a module's layout. It is not expected that there is a
	 * subdirectory called "modules".
	 */
	MODULE_ROOT,
	
	/**
	 * The file should be a single source file or type ".pp", ".rb" or "Modulefile".
	 */
	SINGLE_SOURCE_FILE;
}
