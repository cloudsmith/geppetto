/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
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

import org.cloudsmith.geppetto.pp.dsl.formatting.IBreakAndAlignAdvice.WhenToApply;
import org.cloudsmith.geppetto.pp.dsl.formatting.IBreakAndAlignAdvice.WhenToApplyForDefinition;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.data.BreakAndAlignPreferences;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.AbstractPreferencePage;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.EnumPreferenceFieldEditor;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.IntegerFieldEditor;

/**
 * This is the preference pane for break and alignment formatting.
 * 
 */
public class PPBreakAndAlignPreferencePage extends AbstractPreferencePage {

	@Override
	protected void createFieldEditors() {

		EnumPreferenceFieldEditor definitionParameters = new EnumPreferenceFieldEditor(
			WhenToApplyForDefinition.class, BreakAndAlignPreferences.FORMATTER_ALIGN_DEFINITION_PARAMS, //
			"Align Class and Definition Parameters", getFieldEditorParent());
		addField(definitionParameters);

		EnumPreferenceFieldEditor lists = new EnumPreferenceFieldEditor(
			WhenToApply.class, BreakAndAlignPreferences.FORMATTER_ALIGN_LISTS, //
			"Align List Elements", getFieldEditorParent());
		addField(lists);

		EnumPreferenceFieldEditor hashes = new EnumPreferenceFieldEditor(
			WhenToApply.class, BreakAndAlignPreferences.FORMATTER_ALIGN_HASHES, //
			"Align Hash Elements", getFieldEditorParent());
		addField(hashes);

		IntegerFieldEditor clusterMax = new IntegerFieldEditor(
			BreakAndAlignPreferences.FORMATTER_ALIGN_CLUSTERWIDTH, "Max Alignment Padding", getFieldEditorParent(), 3,
			4);
		clusterMax.setEmptyStringAllowed(false);
		clusterMax.setTextLimit(3);
		clusterMax.setValidRange(0, 255);
		addField(clusterMax);

	}

	@Override
	protected String qualifiedName() {
		return BreakAndAlignPreferences.FORMATTER_ALIGN_ID;
	}

}
