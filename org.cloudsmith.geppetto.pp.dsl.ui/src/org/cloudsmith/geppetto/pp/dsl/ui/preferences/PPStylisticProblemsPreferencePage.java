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
package org.cloudsmith.geppetto.pp.dsl.ui.preferences;

import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.AbstractPreferencePage;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.ValidationPreferenceFieldEditor;

/**
 * A preference pane for stylistic problems
 * 
 */
public class PPStylisticProblemsPreferencePage extends AbstractPreferencePage {

	@Override
	protected void createFieldEditors() {

		// case and selector
		this.addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_CASE_DEFAULT_LAST, "Case statement where a 'default' is not last",
			getFieldEditorParent()));
		this.addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_SELECTOR_DEFAULT_LAST, "Selector expression where a 'default' is not last",
			getFieldEditorParent()));

		// strings and interpolation
		this.addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_DQ_STRING_NOT_REQUIRED, "Strings that do not require double quoting",
			getFieldEditorParent()));
		this.addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_DQ_STRING_NOT_REQUIRED_VAR, "Strings containing a single interpolation",
			getFieldEditorParent()));
		this.addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_UNBRACED_INTERPOLATION, "Interpolated variables without braces",
			getFieldEditorParent()));

		// Resources
		this.addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_UNQUOTED_RESOURCE_TITLE, "Unquoted resource titles", getFieldEditorParent()));

	}

}
