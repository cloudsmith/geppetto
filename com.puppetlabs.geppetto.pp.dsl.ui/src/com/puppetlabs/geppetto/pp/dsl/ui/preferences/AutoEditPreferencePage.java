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
package org.cloudsmith.geppetto.pp.dsl.ui.preferences;

import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.AbstractPreferencePage;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.xtext.ui.editor.preferences.fields.CheckBoxGroupFieldEditor;

/**
 * A Preference pane for auto edit settings (insert of matches pairs [] {} () ...)
 */
public class AutoEditPreferencePage extends AbstractPreferencePage {

	@Override
	protected void createFieldEditors() {
		// Automatically Complete
		//
		addField(new CheckBoxGroupFieldEditor(//
			PPPreferenceConstants.AUTO_EDIT_STRATEGY, "Automatically Complete:", 1, new String[][] {
					{ "[ ]", String.valueOf(PPPreferencesHelper.AUTO_INSERT_BRACKETS) }, //
					{ "{ }", String.valueOf(PPPreferencesHelper.AUTO_INSERT_BRACES) }, //
					{ "( )", String.valueOf(PPPreferencesHelper.AUTO_INSERT_PARENTHESES) }, //
					{ "\" \"", String.valueOf(PPPreferencesHelper.AUTO_INSERT_DQ) }, //
					{ "' '", String.valueOf(PPPreferencesHelper.AUTO_INSERT_SQ) }, //
					{ "/* */", String.valueOf(PPPreferencesHelper.AUTO_INSERT_COMMENT) }, //
			}, getFieldEditorParent(), true) {
			@Override
			protected String calculateResult(String[][] settings) {
				int result = 0;
				for(int i = 0; i < settings.length; i++) {
					String[] row = settings[i];
					String value = row[1];
					String checked = row[2];
					if(Boolean.valueOf(checked)) {
						result += Integer.valueOf(value);
					}
				}
				return String.valueOf(~result);
			}

			@Override
			protected void doStore() {
				if(result == null) {
					// Do NOT do this - it overwrites the stored preference if they are unchanged
					// getPreferenceStore().setToDefault(getPreferenceName());
					return;
				}
				getPreferenceStore().setValue(getPreferenceName(), result);
			}

			@Override
			protected boolean isSelected(String fieldName, String valueToSet) {
				int value = 0;
				try {
					value = Integer.valueOf(valueToSet);
					value = ~value;
				}
				catch(NumberFormatException nfe) {
					// ignore preference value
					return false;
				}
				if(value == 0)
					return false;
				if(fieldName.equals("[ ]"))
					return (value & PPPreferencesHelper.AUTO_INSERT_BRACKETS) == PPPreferencesHelper.AUTO_INSERT_BRACKETS;
				else if(fieldName.equals("{ }"))
					return (value & PPPreferencesHelper.AUTO_INSERT_BRACES) == PPPreferencesHelper.AUTO_INSERT_BRACES;
				else if(fieldName.equals("( )"))
					return (value & PPPreferencesHelper.AUTO_INSERT_PARENTHESES) == PPPreferencesHelper.AUTO_INSERT_PARENTHESES;
				else if(fieldName.equals("\" \""))
					return (value & PPPreferencesHelper.AUTO_INSERT_DQ) == PPPreferencesHelper.AUTO_INSERT_DQ;
				else if(fieldName.equals("' '"))
					return (value & PPPreferencesHelper.AUTO_INSERT_SQ) == PPPreferencesHelper.AUTO_INSERT_SQ;
				else if(fieldName.equals("/* */"))
					return (value & PPPreferencesHelper.AUTO_INSERT_COMMENT) == PPPreferencesHelper.AUTO_INSERT_COMMENT;
				return false;
			}
		});

		BooleanFieldEditor completeBlocks = new BooleanFieldEditor(
			PPPreferenceConstants.AUTO_EDIT_COMPLETE_COMPOUND_BLOCKS, //
			"Automatically complete opened (), {}, [] blocks.", //
			getFieldEditorParent());
		addField(completeBlocks);

	}

}
