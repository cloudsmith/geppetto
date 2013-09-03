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

import org.cloudsmith.geppetto.pp.dsl.formatting.IBreakAndAlignAdvice.WhenToApply;
import org.cloudsmith.geppetto.pp.dsl.formatting.IBreakAndAlignAdvice.WhenToApplyForDefinition;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.data.BreakAndAlignPreferences;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.AbstractPreferencePage;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.EnumPreferenceFieldEditor;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.IntegerFieldEditor;
import org.eclipse.jface.preference.BooleanFieldEditor;

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

		// LabelFieldEditor caseLabel = new LabelFieldEditor("Format case statements:", getFieldEditorParent());
		// addField(caseLabel);
		// Group caseGroup = new Group(getFieldEditorParent(), SWT.SHADOW_IN);
		// GridDataFactory.defaultsFor(caseGroup).grab(true, false).span(2, 1).applyTo(caseGroup);
		// caseGroup.setLayout(new GridLayout());
		//
		// Composite parent = new Composite(caseGroup, SWT.NULL);
		// GridDataFactory.defaultsFor(parent).grab(true, false).span(2, 1).indent(5, 0).applyTo(parent);
		// caseGroup.setLayout(new GridLayout());

		BooleanFieldEditor alignCases = new BooleanFieldEditor(BreakAndAlignPreferences.FORMATTER_ALIGN_CASES, //
			"Align Case expressions on :", //
			getFieldEditorParent());
		addField(alignCases);

		BooleanFieldEditor compactCase = new BooleanFieldEditor(BreakAndAlignPreferences.FORMATTER_COMPACT_CASES, //
			"Compact Case expressions when possible", //
			getFieldEditorParent());
		addField(compactCase);

		BooleanFieldEditor compactResource = new BooleanFieldEditor(
			BreakAndAlignPreferences.FORMATTER_COMPACT_RESOURCES, //
			"Compact Resources when possible", //
			getFieldEditorParent());
		addField(compactResource);

		BooleanFieldEditor alignAssignments = new BooleanFieldEditor(
			BreakAndAlignPreferences.FORMATTER_ALIGN_ASSIGNMENTS, //
			"Align Assignments on = and +=", //
			getFieldEditorParent());
		addField(alignAssignments);

		IntegerFieldEditor clusterMax = new IntegerFieldEditor(BreakAndAlignPreferences.FORMATTER_ALIGN_CLUSTERWIDTH, //
			"Max Alignment Padding", getFieldEditorParent(), //
			3, 4);
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
