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
	public static final String ISSUE_PREFIX = "org.cloudsmith.geppetto.pp.dsl.validation.issue.";

	public static final String ISSUE__RESOURCE_BAD_TYPE_FORMAT = ISSUE_PREFIX + "BadResourceTypeFormat";

	public static final String ISSUE__RESOURCE_UNKNOWN_TYPE = ISSUE_PREFIX + "UnknownResourceType";

	public static final String ISSUE__RESOURCE_MULTIPLE_BODIES = ISSUE_PREFIX + "ResouceMultipleBodies";

	public static final String ISSUE__RESOURCE_WITH_TITLE = ISSUE_PREFIX + "ResourceDefaultWithTitle";

	public static final String ISSUE__RESOURCE_WITHOUT_TITLE = ISSUE_PREFIX + "ResourceWithoutTitle";

	public static final String ISSUE__RESOURCE_WITH_ADDITIONS = ISSUE_PREFIX + "ResourceWithAttributeAdditions";

	public static final String ISSUE__RESOURCE_REFERENCE_NO_REFERENCE = ISSUE_PREFIX + "ResourceOverrideNoReference";

	public static final String ISSUE__RESOURCE_OVERRIDE_BAD_REFERENCE = ISSUE_PREFIX + "ResourceOverrideBadReference";

	public static final String ISSUE__NOT_CLASSREF = ISSUE_PREFIX + "NotClassReference";

	public static final String ISSUE__DEPRECATED_REFERENCE = ISSUE_PREFIX + "DeprecatedReference";

	public static final String ISSUE__RESOURCE_REFERENCE_NO_PARAMETERS = ISSUE_PREFIX + "ResourceReferenceNoParameters";

	public static final String ISSUE__RESOURCE_NOT_VIRTUALIZEABLE = ISSUE_PREFIX + "NotVirtualizeable";

	/**
	 * The given type of expression is not supported at the given place.
	 */
	public static final String ISSUE__UNSUPPORTED_EXPRESSION = ISSUE_PREFIX + "UnsupportedExpression";

	/**
	 * An expression feature that is optional in the grammar/model is not optional in the concrete syntax.
	 */
	public static final String ISSUE__REQUIRED_EXPRESSION = ISSUE_PREFIX + "RequiredExpression";

	public static final String ISSUE__NOT_NAME = ISSUE_PREFIX + "NotNameCompliant";

	public static final String ISSUE__NOT_STRING = ISSUE_PREFIX + "NotStringCompliant";

	public static final String ISSUE__NOT_NAME_OR_REF = ISSUE_PREFIX + "NeitherNameNorReference";

	/**
	 * This should not happen if a model is parsed from text, but can occur when manually constructing a model.
	 */
	public static final String ISSUE__ILLEGAL_OP = ISSUE_PREFIX + "IllegalOperator";

	public static final String ISSUE__NOT_REGEX = ISSUE_PREFIX + "Badlyformedregularexpression";

	public static final String ISSUE__NOT_ASSIGNABLE = ISSUE_PREFIX + "NotAssignable";

	public static final String ISSUE__RESERVED_WORD = ISSUE_PREFIX + "ReservedWord";

	public static final String ISSUE__NOT_VARNAME = ISSUE_PREFIX + "NotVariableName";

	public static final String ISSUE__NOT_APPENDABLE = ISSUE_PREFIX + "NotAppendable";

	public static final String ISSUE__NULL_EXPRESSION = ISSUE_PREFIX + "NullExpression";

	public static final String ISSUE__NOT_TOPLEVEL = ISSUE_PREFIX + "NotTopLevelExpression";

	public static final String ISSUE__UNKNOWN_FUNCTION_REFERENCE = ISSUE_PREFIX + "ReferenceToUnknownfunction";

	public static final String ISSUE__NOT_RVALUE = ISSUE_PREFIX + "NotRightValue";

	public static final String ISSUE__UNSUPPORTED_REGEX_FLAGS = ISSUE_PREFIX + "UnsupportedRegexFlags";

	public static final String ISSUE__UNRECOGNIZED_ESCAPE = ISSUE_PREFIX + "UnrecognizedStringEscape";

	public static final String ISSUE__NOT_AT_TOPLEVEL_OR_CLASS = ISSUE_PREFIX + "NotAtTopLevelOrClass";

	public static final String ISSUE__RESOURCE_UNKNOWN_PROPERTY = ISSUE_PREFIX + "UnknownProperty";

	public static final String ISSUE__RESOURCE_AMBIGUOUS_REFERENCE = ISSUE_PREFIX + "AmbigousReference";

	public static final String ISSUE__MISSING_COMMA = ISSUE_PREFIX + "MissingCommad";

	public static final String ISSUE__REQUIRES_QUOTING = ISSUE_PREFIX + "RequiresQuoting";

	public static final String ISSUE__NOT_CLASSNAME = ISSUE_PREFIX + "NotClassName";

	public static final String ISSUE__NOT_ASSIGNMENT_OP = ISSUE_PREFIX + "NotAssignmentOp";

	public static final String ISSUE__RESOURCE_NAME_REDEFINITION = ISSUE_PREFIX + "ResourceNameRedefinition";

}
