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
package org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * A field editor for a ValidationPreference
 * 
 */
public class EnumPreferenceFieldEditor extends ComboFieldEditor {
	// private static final String[][] entryNamesAndValues = new String[][] {
	// { "Ignore", "Ignore" }, { "Warning", "Warning" }, { "Error", "Error" } };

	public static <T extends Enum<T>> String[][] getEntryNamesAndValues(Class<T> enumType) {
		T[] constants = enumType.getEnumConstants();

		String[][] entryNamesAndValues = new String[constants.length][2];
		int i = 0;
		for(T c : constants)
			entryNamesAndValues[i++] = new String[] { c.toString(), c.toString() };
		return entryNamesAndValues;
	}

	public <T extends Enum<T>> EnumPreferenceFieldEditor(Class<T> enumType, String name, String labelText,
			Composite parent) {
		super(name, labelText, getEntryNamesAndValues(enumType), parent);

	}
}
