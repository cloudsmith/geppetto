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
package com.puppetlabs.geppetto.pp.dsl.ui.preferences.editors;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * A field editor for a ValidationPreference
 * 
 */
public class ValidationPreferenceFieldEditor extends ComboFieldEditor {
	private static final String[][] entryNamesAndValues = new String[][] {
			{ "Ignore", "Ignore" }, { "Warning", "Warning" }, { "Error", "Error" } };

	public ValidationPreferenceFieldEditor(String name, String labelText, Composite parent) {
		super(name, labelText, entryNamesAndValues, parent);
	}

}
