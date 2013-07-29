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
package org.cloudsmith.geppetto.validation;

/**
 * Constants for ValidationService
 * 
 */
public interface IValidationConstants {
	String ISSUE_PREFIX = "org.cloudsmith.geppetto.validation.issue.";

	String ISSUE__UNEXPECTED_SUBMODULE_DIRECTORY = ISSUE_PREFIX + "UnexpectedSubmoduleDirectory";

	String ISSUE__MISSING_MODULEFILE = ISSUE_PREFIX + "MissingModulefile";

	String ISSUE__MODULEFILE_PARSE_ERROR = ISSUE_PREFIX + "ModulefileParseError";

	String ISSUE__MODULEFILE_NO_NAME = ISSUE_PREFIX + "ModulefileNoName";

	String ISSUE__MODULEFILE_NO_VERSION = ISSUE_PREFIX + "ModulefileNoVersion";

	String ISSUE__MODULEFILE_VERSION_ERROR = ISSUE_PREFIX + "ModulefileVersionError";

	String ISSUE__MODULEFILE_UNSATISFIED_DEPENDENCY = ISSUE_PREFIX + "ModulefileUnsatisfiedDependency";

	String ISSUE__MODULEFILE_REDEFINITION = ISSUE_PREFIX + "ModulefileRedefinition";

	String ISSUE__MODULEFILE_DEPENDENCY_ERROR = ISSUE_PREFIX + "ModulefileDependencyError";

}
