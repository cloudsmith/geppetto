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
package org.cloudsmith.geppetto.pp.dsl.ui.preferences.data;

import org.cloudsmith.geppetto.pp.dsl.ui.preferences.PPPreferenceConstants;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Singleton;

/**
 * @author henrik
 * 
 */
@Singleton
public class FormatterGeneralPreferences extends AbstractPreferenceData {

	IPreferenceStoreAccess access;

	@Override
	protected void doInitialize(IPreferenceStore store) {
		// formatting
		store.setDefault(PPPreferenceConstants.FORMATTER_INDENTSIZE, "2");
		store.setDefault(PPPreferenceConstants.FORMATTER_MAXWIDTH, "132");
	}

	/**
	 * Get the global preference (or the default) for "indentation size" preference.
	 * 
	 * @return size of indent (spaces) measured in number of characters
	 */
	public int getIndentSize() {
		return getInt(PPPreferenceConstants.FORMATTER_INDENTSIZE);
	}

	/**
	 * Get the effective preference (or the default) for "indentation size" preference.
	 * 
	 * @return size of indent (spaces) measured in number of characters
	 */
	public int getIndentSize(IResource r) {
		return getInt(r, PPPreferenceConstants.FORMATTER_INDENTSIZE);
	}

	/**
	 * Get the global preference (or the default) for "indentation size" preference.
	 * 
	 * @return size of indent (spaces) measured in number of characters
	 */
	public int getPreferredMaxWidth() {
		return getInt(PPPreferenceConstants.FORMATTER_MAXWIDTH);
	}

	/**
	 * Get the effective preference (or the default) for "indentation size" preference.
	 * 
	 * @return size of indent (spaces) measured in number of characters
	 */
	public int getPreferredMaxWidth(IResource r) {
		return getInt(r, PPPreferenceConstants.FORMATTER_MAXWIDTH);
	}

	@Override
	protected String getUseProjectSettingsID() {
		return PPPreferenceConstants.FORMATTER_GENERAL_USE_PROJECT_SETTINGS;
	}

}
