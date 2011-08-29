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

import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.xtext.ui.editor.preferences.AbstractPreferencePage;

/**
 * A simple preference page for selection of puppet target.
 * 
 */
public class PPGeneralPreferencePage extends AbstractPreferencePage {

	@Override
	protected void createFieldEditors() {

		addField(new RadioGroupFieldEditor(PPPreferenceConstants.PUPPET_TARGET_VERSION, //
			"Puppet target version", 1, //
			new String[][] { { "2.6", "2.6" }, //
					{ "2.7", "2.7" }, //
			}, getFieldEditorParent()));
	}
}
