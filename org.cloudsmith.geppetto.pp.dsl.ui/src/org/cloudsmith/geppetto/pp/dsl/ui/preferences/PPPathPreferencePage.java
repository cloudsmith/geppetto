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

//import org.eclipse.jface.preference.PathEditor;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.PathEditor;
import org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.PromptDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * A simple preference page for search path and environment
 * 
 */
public class PPPathPreferencePage extends AbstractRebuildingPreferencePage {
	private static class PPPathEditor extends PathEditor {

		/**
		 * @param puppetProjectPath
		 * @param string
		 * @param fieldEditorParent
		 */
		public PPPathEditor(String name, String label, Composite fieldEditorParent) {
			super(name, label, "-", fieldEditorParent);
		}

		@Override
		protected String getNewInputObject() {
			PromptDialog dialog = new PromptDialog(getShell(), SWT.SHEET);
			String[] value = new String[] { "" };
			int[] allSubdirs = new int[] { 1 };
			int[] okCancel = new int[] { 1 };

			dialog.prompt(
				"Add path segement", "Enter relative path", "Search all subdirectories", value, allSubdirs, okCancel);
			if(okCancel[0] == 0)
				return null;
			if(allSubdirs[0] == 0)
				return value[0].trim();
			String result = value[0].trim();
			if(!result.endsWith("/*"))
				result += "/*";
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.preference.FieldEditor#load()
		 */
		@Override
		public void load() {
			// must clear before loading since a switch to project specific otherwise gets
			// the default content + the project specific content.
			getList().removeAll();
			super.load();
		}
	}

	private static final String PAGE_ID = "org.cloudsmith.geppetto.pp.dsl.PP.searchPath";

	@Override
	protected void createFieldEditors() {
		PPPathEditor pathField = new PPPathEditor(getPreferenceId(), //
			"Search Path", getFieldEditorParent());
		addField(pathField);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.ui.preferences.AbstractRebuildingPreferencePage#getPreferenceId()
	 */
	@Override
	protected String getPreferenceId() {
		return PPPreferenceConstants.PUPPET_PROJECT_PATH;
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
