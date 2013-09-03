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
package com.puppetlabs.geppetto.pp.dsl.ui.preferences.data;

import com.puppetlabs.geppetto.pp.dsl.ui.preferences.PPPreferenceConstants;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;

import com.google.inject.Singleton;

/**
 * Manages the Formatter General preferences.
 * 
 */
@Singleton
public class FormatterGeneralPreferences extends AbstractPreferenceData {

	IPropertyChangeListener pChangeListener;

	public static final String FORMATTER_MAXWIDTH = "formatterPreferredWidth";

	public static final String FORMATTER_INDENTSIZE = "formatterIndentSize";

	public static final String FORMATTER_GENERAL_ID = "com.puppetlabs.geppetto.pp.dsl.PP.formatter";

	public static final String FORMATTER_GENERAL_USE_PROJECT_SETTINGS = FORMATTER_GENERAL_ID + "." +
			PPPreferenceConstants.USE_PROJECT_SETTINGS;

	public static final String FORMATTER_SPACES_FOR_TABS = "formatterSpacesForTabs";

	@Override
	protected void doInitialize(IPreferenceStore store) {
		// formatting
		store.setDefault(FormatterGeneralPreferences.FORMATTER_INDENTSIZE, "2");
		store.setDefault(FormatterGeneralPreferences.FORMATTER_MAXWIDTH, "132");
		store.setDefault(FormatterGeneralPreferences.FORMATTER_SPACES_FOR_TABS, true);
	}

	/**
	 * Get the global preference (or the default) for "indentation size" preference.
	 * 
	 * @return size of indent (spaces) measured in number of characters
	 */
	public int getIndentSize() {
		return getInt(FormatterGeneralPreferences.FORMATTER_INDENTSIZE);
	}

	/**
	 * Get the effective preference (or the default) for "indentation size" preference.
	 * 
	 * @return size of indent (spaces) measured in number of characters
	 */
	public int getIndentSize(IResource r) {
		return getInt(r, FormatterGeneralPreferences.FORMATTER_INDENTSIZE);
	}

	/**
	 * Get the global preference (or the default) for "indentation size" preference.
	 * 
	 * @return size of indent (spaces) measured in number of characters
	 */
	public int getPreferredMaxWidth() {
		return getInt(FormatterGeneralPreferences.FORMATTER_MAXWIDTH);
	}

	/**
	 * Get the effective preference (or the default) for "indentation size" preference.
	 * 
	 * @return size of indent (spaces) measured in number of characters
	 */
	public int getPreferredMaxWidth(IResource r) {
		return getInt(r, FormatterGeneralPreferences.FORMATTER_MAXWIDTH);
	}

	/**
	 * Get the global (or the default) for "replace tab input with spaces" preference.
	 * 
	 * @return boolean true if tabs should be expanded to spaces
	 */
	public boolean getSpacesForTabs() {
		return getBoolean(FormatterGeneralPreferences.FORMATTER_SPACES_FOR_TABS);
	}

	/**
	 * Get the effective preference (or the default) for "replace tab input with spaces" preference.
	 * 
	 * @return boolean true if tabs should be expanded to spaces
	 */
	public boolean getSpacesForTabs(IResource r) {
		return getContextualBoolean(r, FormatterGeneralPreferences.FORMATTER_SPACES_FOR_TABS);
	}

	@Override
	protected String getUseProjectSettingsID() {
		return FormatterGeneralPreferences.FORMATTER_GENERAL_USE_PROJECT_SETTINGS;
	}

}
