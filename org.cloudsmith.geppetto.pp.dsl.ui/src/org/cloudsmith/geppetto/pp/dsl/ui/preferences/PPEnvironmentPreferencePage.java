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

import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.xtext.ui.editor.preferences.AbstractPreferencePage;

/**
 * A simple preference page for selection of puppet target.
 * This is also the puppet root preference pane.
 * 
 */
public class PPEnvironmentPreferencePage extends AbstractPreferencePage {
	private static final String PAGE_ID = "org.cloudsmith.geppetto.pp.dsl.PP.environment";

	@Override
	protected void createFieldEditors() {

		this.addField(new StringFieldEditor(
			PPPreferenceConstants.PUPPET_ENVIRONMENT, "Environment", getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.editor.preferences.AbstractPreferencePage#qualifiedName()
	 */
	@Override
	protected String qualifiedName() {
		return PAGE_ID;
	}
}
