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

import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.AbstractPreferencePage;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.IntegerFieldEditor;

/**
 * This is the puppet root preference pane for formatting preferences as well as the editor pane for
 * the two most basic formatter related preferences (indent size, and preferred width).
 * 
 */
public class PPFormatterPreferencePage extends AbstractPreferencePage {

	@Override
	protected void createFieldEditors() {

		IntegerFieldEditor indentSizeFieldEditor = new IntegerFieldEditor(
			PPPreferenceConstants.FORMATTER_INDENTSIZE, "Indentation size", getFieldEditorParent(), 2, 4);
		indentSizeFieldEditor.setEmptyStringAllowed(false);
		indentSizeFieldEditor.setTextLimit(2);
		indentSizeFieldEditor.setValidRange(2, 16);
		addField(indentSizeFieldEditor);

		IntegerFieldEditor maxWidthFieldEditor = new IntegerFieldEditor(
			PPPreferenceConstants.FORMATTER_MAXWIDTH, "Preferred max width", getFieldEditorParent(), 3, 4);
		maxWidthFieldEditor.setEmptyStringAllowed(false);
		indentSizeFieldEditor.setTextLimit(3);
		maxWidthFieldEditor.setValidRange(40, 255);
		addField(maxWidthFieldEditor);

	}
}
