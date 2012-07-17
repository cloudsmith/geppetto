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
package org.cloudsmith.geppetto.pp.dsl.ui.preferences;

import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.AbstractPreferencePage;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.ValidationPreferenceFieldEditor;

/**
 * A preference pane for potential problems.
 * 
 */
public class PPPotentialProblemsPreferencePage extends AbstractPreferencePage {

	@Override
	protected void createFieldEditors() {

		this.addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_CIRCULAR_DEPENDENCY, "Circular Module Dependency", getFieldEditorParent()));
		this.addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_INTERPOLATED_HYPHEN, "Interpolated hyphen without surrounding {}",
			getFieldEditorParent()));
		this.addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_BOOLEAN_STRING, "Strings containing \"false\" or \"true\"",
			getFieldEditorParent()));
		this.addField(new ValidationPreferenceFieldEditor(
			PPPreferenceConstants.PROBLEM_MISSING_DEFAULT, "Missing 'default' in selector", getFieldEditorParent()));
	}

}
