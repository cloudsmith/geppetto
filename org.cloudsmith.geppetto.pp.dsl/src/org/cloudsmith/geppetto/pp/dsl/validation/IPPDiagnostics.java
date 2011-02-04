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
package org.cloudsmith.geppetto.pp.dsl.validation;

/**
 * Interface declaring diagnostic codess.
 * These can be used for association of quick fixes with errors and warnings.
 * 
 */
public interface IPPDiagnostics {
	public static final String ISSUE__RESOURCE_BAD_TYPE_FORMAT = "Bad Resource Type Format";

	public static final String ISSUE__RESOURCE_UNKNOWN_TYPE = "Unknown Resource Type";

	public static final String ISSUE__RESOURCE_MULTIPLE_BODIES = "Resouce Multiple Bodies";

	public static final String ISSUE__RESOURCE_WITH_TITLE = "Resource Default With Title";

	public static final String ISSUE__RESOURCE_WITHOUT_TITLE = "Resource Without Title";

	public static final String ISSUE__RESOURCE_WITH_ADDITIONS = "Resource With Attribute Additions";

	public static final String ISSUE__RESOURCE_REFERENCE_NO_REFERENCE = "Resource Override No Reference";

	public static final String ISSUE__RESOURCE_OVERRIDE_BAD_REFERENCE = "Resource Override Bad Reference";

	public static final String ISSUE__NOT_CLASSREF = "Not Class Reference";

	public static final String ISSUE__DEPRECATED_REFERENCE = "Deprecated Reference";

	public static final String ISSUE__RESOURCE_REFERENCE_NO_PARAMETERS = "Resource Reference No Parameters";

	public static final String ISSUE__RESOURCE_NOT_VIRTUALIZEABLE = "Not Virtualizeable";

	/**
	 * The given type of expression is not supported at the given place.
	 */
	public static final String ISSUE__UNSUPPORTED_EXPRESSION = "Unsupported Expression";

	/**
	 * An expression feature that is optional in the grammar/model is not optional in the concrete syntax.
	 */
	public static final String ISSUE__REQUIRED_EXPRESSION = "Required Expression";

	public static final String ISSUE__NOT_NAME = "Not Name Compliant";

	public static final String ISSUE__NOT_STRING = "Not String Compliant";

	public static final String ISSUE__NOT_NAME_OR_REF = "Neither Name Nor Reference";

	/**
	 * This should not happen if a model is parsed from text, but can occur when manually constructing a model.
	 */
	public static final String ISSUE__ILLEGAL_OP = "Illegal Operator";

	public static final String ISSUE__NOT_REGEX = "Badly formed regular expression";

	public static final String ISSUE__NOT_ASSIGNABLE = "Not Assignable";

	public static final String ISSUE__RESERVED_WORD = "Reserved Word";

	public static final String ISSUE__NOT_VARNAME = "Not Variable Name";

	public static final String ISSUE__NOT_APPENDABLE = "Not Appendable";

	public static final String ISSUE__NULL_EXPRESSION = "Null Expression";

	public static final String ISSUE__NOT_TOPLEVEL = "Not Top Level Expression";

	public static final String ISSUE__UNKNOWN_FUNCTION_REFERENCE = "Reference to unknown function";

	public static final String ISSUE__NOT_RVALUE = "Not a Right Value";

	public static final String ISSUE__UNSUPPORTED_REGEX_FLAGS = "Unsupported Regex Flags";

}
