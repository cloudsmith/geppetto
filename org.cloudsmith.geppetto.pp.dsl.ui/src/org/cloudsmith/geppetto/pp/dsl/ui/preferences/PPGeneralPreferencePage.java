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
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * A simple preference page for selection of puppet target.
 * This is also the puppet root preference pane.
 * 
 * <p>
 * Note: Previous versions used "2.8" but this version will never be released as 2.7 is the last in the 2.x series. The implementation below corrects
 * this by updating the preferences from "2.8" to "3.0" if the value "2.8" is encountered.
 * </p>
 * 
 */
public class PPGeneralPreferencePage extends AbstractPreferencePage {

	/**
	 * Specialized version that corrects stored "2.8" value for the "3.0" release.
	 * 
	 */
	private static class PuppetVersionFieldEditor extends RadioGroupFieldEditor {

		/**
		 * @param puppetTargetVersion
		 * @param string
		 * @param i
		 * @param strings
		 * @param fieldEditorParent
		 */
		public PuppetVersionFieldEditor(String puppetTargetVersion, String string, int i, String[][] strings,
				Composite fieldEditorParent) {
			super(puppetTargetVersion, string, i, strings, fieldEditorParent);
		}

		/*
		 * (non-Javadoc)
		 * Method declared on FieldEditor.
		 */
		@Override
		protected void doLoad() {
			String v = getPreferenceStore().getString(getPreferenceName());
			if("2.8".equals(v)) {
				getPreferenceStore().setValue(getPreferenceName(), "3.0");
			}
			super.doLoad();
		}

		/*
		 * (non-Javadoc)
		 * Method declared on FieldEditor.
		 */
		@Override
		protected void doLoadDefault() {
			String v = getPreferenceStore().getDefaultString(getPreferenceName());
			if("2.8".equals(v)) {
				getPreferenceStore().setDefault(getPreferenceName(), "3.0");
			}
			super.doLoadDefault();
		}

	}

	@Override
	protected void createFieldEditors() {

		addField(new PuppetVersionFieldEditor(PPPreferenceConstants.PUPPET_TARGET_VERSION, //
			"Puppet target version", 1, //
			new String[][] { { "2.6", "2.6" }, //
					{ "2.7", "2.7" }, //
					{ "3.0", "3.0" }, // NOTE: 2.8 release does not exist, it got bumped to 3.0
					{ "3.2", "3.2" }, //
					{ "future", "future" }, // What ever the future is...
					{ "PE 2.0", "PE 2.0" }, //
			}, getFieldEditorParent()));

	}
}
