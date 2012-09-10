/**
 * Copyright (c) 2011, 2012 Cloudsmith Inc. and other contributors, as listed below.
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

	public static final String ISSUE_PROPOSAL_SUFFIX = ".prop";

	public static final String ISSUE__RESOURCE_BAD_TYPE_FORMAT = ISSUE_PREFIX + "BadResourceTypeFormat";

	/**
	 * Unknown resource type without proposals attached.
	 */
	public static final String ISSUE__RESOURCE_UNKNOWN_TYPE = ISSUE_PREFIX + "UnknownResourceType";

	/**
	 * Unknown resource type with proposals attached.
	 */
	public static final String ISSUE__RESOURCE_UNKNOWN_TYPE_PROP = ISSUE__RESOURCE_UNKNOWN_TYPE + ISSUE_PROPOSAL_SUFFIX;

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

	public static final String ISSUE__UNSUPPORTED_OPERATOR = ISSUE_PREFIX + "UnsupportedOperator";

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

	/**
	 * with proposals.
	 */
	public static final String ISSUE__UNKNOWN_FUNCTION_REFERENCE_PROP = ISSUE__UNKNOWN_FUNCTION_REFERENCE +
			ISSUE_PROPOSAL_SUFFIX;

	public static final String ISSUE__NOT_RVALUE = ISSUE_PREFIX + "NotRightValue";

	public static final String ISSUE__UNSUPPORTED_REGEX_FLAGS = ISSUE_PREFIX + "UnsupportedRegexFlags";

	public static final String ISSUE__UNRECOGNIZED_ESCAPE = ISSUE_PREFIX + "UnrecognizedStringEscape";

	public static final String ISSUE__NOT_AT_TOPLEVEL_OR_CLASS = ISSUE_PREFIX + "NotAtTopLevelOrClass";

	public static final String ISSUE__RESOURCE_UNKNOWN_PROPERTY = ISSUE_PREFIX + "UnknownProperty";

	/**
	 * with proposals
	 */
	public static final String ISSUE__RESOURCE_UNKNOWN_PROPERTY_PROP = ISSUE__RESOURCE_UNKNOWN_PROPERTY +
			ISSUE_PROPOSAL_SUFFIX;

	public static final String ISSUE__RESOURCE_AMBIGUOUS_REFERENCE = ISSUE_PREFIX + "AmbigousReference";

	public static final String ISSUE__MISSING_COMMA = ISSUE_PREFIX + "MissingComma";

	public static final String ISSUE__REQUIRES_QUOTING = ISSUE_PREFIX + "RequiresQuoting";

	public static final String ISSUE__NOT_CLASSNAME = ISSUE_PREFIX + "NotClassName";

	public static final String ISSUE__NOT_ASSIGNMENT_OP = ISSUE_PREFIX + "NotAssignmentOp";

	public static final String ISSUE__RESOURCE_NAME_REDEFINITION = ISSUE_PREFIX + "ResourceNameRedefinition";

	public static final String ISSUE__NOT_CONSTANT = ISSUE_PREFIX + "NotConstant";

	public static final String ISSUE__RESOURCE_DEPRECATED_NAME_ALIAS = ISSUE_PREFIX + "ResourceDeprecatedNameAlias";

	public static final String ISSUE__RESOURCE_DUPLICATE_NAME_PARAMETER = ISSUE_PREFIX +
			"ResourceDuplicateNameParameter";

	public static final String ISSUE__CIRCULAR_INHERITENCE = ISSUE_PREFIX + "CircularInheritence";

	public static final String ISSUE__RESOURCE_DUPLICATE_PARAMETER = ISSUE_PREFIX + "DuplicateParameter";

	public static final String ISSUE__NOT_ON_PATH = ISSUE_PREFIX + "NotOnPath";

	public static final String ISSUE__ASSIGNMENT_OTHER_NAMESPACE = ISSUE_PREFIX + "AssignmentOtherNamespace";

	public static final String ISSUE__UNQUOTED_QUALIFIED_NAME = ISSUE_PREFIX + "UnquotedFQN";

	/**
	 * An unsupported expression that can be fixed by changing the expr to a string.
	 */
	public static final String ISSUE__UNSUPPORTED_EXPRESSION_STRING_OK = ISSUE_PREFIX + "UnsupportedExpression_String";

	public static final String ISSUE__UNQUALIFIED_VARIABLE = ISSUE_PREFIX + "UnqualifiedVariable";

	public static final String ISSUE__UNKNOWN_VARIABLE = ISSUE_PREFIX + "UnknownVariable";

	public static final String ISSUE__ASSIGNMENT_DECIMAL_VAR = ISSUE_PREFIX + "AssignmentDecimalVar";

	public static final String ISSUE__UNKNOWN_REGEXP = ISSUE_PREFIX + "UnknownRegexp";

	public static final String ISSUE__UNINITIALIZED_VARIABLE = ISSUE_PREFIX + "UninitializedVar";

	public static final String ISSUE__INTERPOLATED_HYPHEN = ISSUE_PREFIX + "InterpolatedHypen";

	public static final String ISSUE__HYPHEN_IN_NAME = ISSUE_PREFIX + "HyphenInName";

	public static final String ISSUE__NOT_NUMERIC = ISSUE_PREFIX + "NotNumeric";

	public static final String ISSUE__STRING_BOOLEAN = ISSUE_PREFIX + "StringBoolean";

	public static final String ISSUE__ENDCOMMA = ISSUE_PREFIX + "EndComma";

	public static final String ISSUE__DEFAULT_NOT_LAST = ISSUE_PREFIX + "DefaultNotLast";

	public static final String ISSUE__MISSING_DEFAULT = ISSUE_PREFIX + "MissingDefault";

	public static final String ISSUE__UNREACHABLE = ISSUE_PREFIX + "Unreachable";

	public static final String ISSUE__DUPLICATE_CASE = ISSUE_PREFIX + "DuplicateCase";

	public static final String ISSUE__DQ_STRING_NOT_REQUIRED = ISSUE_PREFIX + "SqStringNotRequired";

	public static final String ISSUE__DQ_STRING_NOT_REQUIRED_VAR = ISSUE_PREFIX + "SqStringNotRequiredVar";

	public static final String ISSUE__UNBRACED_INTERPOLATION = ISSUE_PREFIX + "UnbracedInterpolation";

	public static final String ISSUE__UNQUOTED_RESOURCE_TITLE = ISSUE_PREFIX + "UnquotedResourceTitle";

	public static final String ISSUE__NOT_INITIAL_LOWERCASE = ISSUE_PREFIX + "NotInitialLowerCase";

	public static final String ISSUE__DUPLICATE_PARAMETER = ISSUE_PREFIX + "DuplicateParameter";

	public static final String ISSUE_UNWANTED_ML_COMMENT = ISSUE_PREFIX + "UnwantedMLComment";

	public static final String ISSUE_RIGHT_TO_LEFT_RELATIONSHIP = ISSUE_PREFIX + "RightToLeftRelatinship";

	public static final String ISSUE__UNSUPPORTED_UNLESS = ISSUE_PREFIX + "UnsupportedUnless";

	public static final String ISSUE__UNQUOTED_INTERPOLATION = ISSUE_PREFIX + "UnquotedInterpolation";

	public static final String ISSUE__CIRCULAR_MODULE_DEPENDENCY = ISSUE_PREFIX + "CircularModuleDependency";

	public static final String ISSUE__RESERVED_NAME = ISSUE_PREFIX + "ReservedName";

	public static final String ISSUE__RESOURCE_DUPLICATE_ATTRIBUTE = ISSUE_PREFIX + "DuplicateAttribute";

	public static final String ISSUE__ASSIGNMENT_TO_VAR_NAMED_STRING = ISSUE_PREFIX + "AssignmentToVarNamedString";

	public static final String ISSUE__INHERITANCE_WITH_PARAMETERS = ISSUE_PREFIX + "InheritenceOfParameterizedClass";

	public static final String ISSUE__ENSURE_NOT_FIRST = ISSUE_PREFIX + "EnsureNotFirst";

}
