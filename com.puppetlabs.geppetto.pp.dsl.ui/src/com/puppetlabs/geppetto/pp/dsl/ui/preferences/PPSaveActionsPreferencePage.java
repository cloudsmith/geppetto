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
package com.puppetlabs.geppetto.pp.dsl.ui.preferences;

import com.puppetlabs.geppetto.pp.dsl.ui.preferences.editors.AbstractPreferencePage;
import org.eclipse.jface.preference.BooleanFieldEditor;

/**
 * Preference Page for Save Actions
 * 
 */
public class PPSaveActionsPreferencePage extends AbstractPreferencePage {

	@Override
	protected void createFieldEditors() {
		this.addField(new BooleanFieldEditor(PPPreferenceConstants.SAVE_ACTION_FORMAT, //
			"Format", getFieldEditorParent()));
		this.addField(new BooleanFieldEditor(PPPreferenceConstants.SAVE_ACTION_TRIM_LINES, //
			"Trim lines from trailing whitespace", getFieldEditorParent()));
		this.addField(new BooleanFieldEditor(PPPreferenceConstants.SAVE_ACTION_REPLACE_FUNKY_SPACES, //
			"Replace non regular whitespace", getFieldEditorParent()));
		this.addField(new BooleanFieldEditor(PPPreferenceConstants.SAVE_ACTION_ENSURE_ENDS_WITH_NL, //
			"Ensure last line ends with line break", getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.editor.preferences.AbstractPreferencePage#qualifiedName()
	 */
	@Override
	protected String qualifiedName() {
		return PPPreferenceConstants.SAVE_ACTIONS_ID;
	}

}
