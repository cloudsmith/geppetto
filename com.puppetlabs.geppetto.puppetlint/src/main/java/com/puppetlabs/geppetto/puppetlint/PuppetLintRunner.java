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
package org.cloudsmith.geppetto.puppetlint;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * An interface to the <a href="http://puppet-lint.com">puppet-lint</a> program.
 */
public interface PuppetLintRunner {
	/**
	 * An issue produced by puppet-lint
	 */
	interface Issue {

		/**
		 * @return the checkName
		 */
		public abstract String getCheckName();

		/**
		 * @return the lineNumber
		 */
		public abstract int getLineNumber();

		/**
		 * @return the message
		 */
		public abstract String getMessage();

		/**
		 * @return the path
		 */
		public abstract String getPath();

		/**
		 * @return the severity
		 */
		public abstract Severity getSeverity();

	}

	/**
	 * Options that can be passed to {@link PuppetLintRunner#run(File, Option...)}
	 */
	enum Option {
		/** Fail on warnings */
		FailOnWarnings("fail-on-warnings"),

		/** Skip the arrow_alignment check */
		NoArrowAlignmentCheck("no-arrow_alignment-check"),

		/** Skip the autoloader_layout check */
		NoAutoloaderLayoutCheck("no-autoloader_layout-check"),

		/** Skip the case_without_default check */
		NoCaseWithoutDefaultCheck("no-case_without_default-check"),

		/** Skip the documentation check */
		NoDocumentationCheck("no-documentation-check"),

		/** Skip the double_quoted_strings check */
		NoDoubleQuotedStringsCheck("no-double_quoted_strings-check"),

		/** Skip the duplicate_params check */
		NoDuplicateParamsCheck("no-duplicate_params-check"),

		/** Skip the 80chars check */
		NoEightyCharsCheck("no-80chars-check"),

		/** Skip the ensure_first_param check */
		NoEnsureFirstParamCheck("no-ensure_first_param-check"),

		/** Skip the ensure_not_symlink_target check */
		NoEnsureNotSymlinkTargetCheck("no-ensure_not_symlink_target-check"),

		/** Skip the file_mode check */
		NoFileModeCheck("no-file_mode-check"),

		/** Skip the hard_tabs check */
		NoHardTabsCheck("no-hard_tabs-check"),

		/** Skip the inherits_across_namespaces check */
		NoInheritsAcrossNamespacesCheck("no-inherits_across_namespaces-check"),

		/** Skip the names_containing_dash check */
		NoNamesContainingDashCheck("no-names_containing_dash-check"),

		/** Skip the nested_classes_or_defines check */
		NoNestedClassesOrDefinesCheck("no-nested_classes_or_defines-check"),

		/** Skip the only_variable_string check */
		NoOnlyVariableStringCheck("no-only_variable_string-check"),

		/** Skip the parameterised_classes check */
		NoParameterisedClassesCheck("no-parameterised_classes-check"),

		/** Skip the parameter_order check */
		NoParameterOrderCheck("no-parameter_order-check"),

		/** Skip the quoted_booleans check */
		NoQuotedBooleansCheck("no-quoted_booleans-check"),

		/** Skip the right_to_left_relationship check */
		NoRightToLeftRelationshipCheck("no-right_to_left_relationship-check"),

		/** Skip the selector_inside_resource check */
		NoSelectorInsideResourceCheck("no-selector_inside_resource-check"),

		/** Skip the single_quote_string_with_variables check */
		NoSingleQuoteStringWithVariablesCheck("no-single_quote_string_with_variables-check"),

		/** Skip the slash_comments check */
		NoSlashCommentsCheck("no-slash_comments-check"),

		/** Skip the star_comments check */
		NoStarCommentsCheck("no-star_comments-check"),

		/** Skip the trailing_whitespace check */
		NoTrailingWhitespaceCheck("no-trailing_whitespace-check"),

		/** Include or skip the 2sp_soft_tabs check */
		NoTwoSpaceSoftTabsCheck("no-2sp_soft_tabs-check"),

		/** Skip the unquoted_file_mode check */
		NoUnquotedFileModeCheck("no-unquoted_file_mode-check"),

		/** Skip the unquoted_resource_title check */
		NoUnquotedResourceTitleCheck("no-unquoted_resource_title-check"),

		/** Skip the variable_contains_dash check */
		NoVariableContainsDashCheck("no-variable_contains_dash-check"),

		/** Skip the variable_scope check */
		NoVariableScopeCheck("no-variable_scope-check"),

		/** Skip the variables_not_enclosed check */
		NoVariablesNotEnclosedCheck("no-variables_not_enclosed-check");

		private final String cmdLineOption;

		private Option(String cmdLineOption) {
			this.cmdLineOption = cmdLineOption;
		}

		/**
		 * @return The command line option without the leading two dashes
		 */
		@Override
		public String toString() {
			return cmdLineOption;
		}
	}

	enum Severity {
		WARNING, ERROR
	}

	/**
	 * @return The version of the puppet-lint program.
	 * @throws IOException
	 *             if the program cannot execute or is unable to produce the version information
	 */
	String getVersion() throws IOException;

	/**
	 * Run puppet lint on the specified directory or file
	 * 
	 * @param fileOrDirectory
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	List<Issue> run(File fileOrDirectory, Option... options) throws IOException;
}
