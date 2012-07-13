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
import java.util.ArrayList;
import java.util.StringTokenizer;

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
		 * The PathEditor uses a platform specific path separator, which is not good
		 * for platform agnostic preferences checked into repos. Since the search path
		 * being edited here is not a system path, and does not contain windows drives etc,
		 * it will work just fine with colon.
		 */
		private static final String pathSeparator = ":";

		/**
		 * @param puppetProjectPath
		 * @param string
		 * @param fieldEditorParent
		 */
		public PPPathEditor(String name, String label, Composite fieldEditorParent) {
			super(name, label, "-", fieldEditorParent);
		}

		/*
		 * (non-Javadoc)
		 * Method declared on ListEditor.
		 * Creates a single string from the given array by separating each
		 * string with the appropriate OS-specific path separator.
		 */
		@Override
		protected String createList(String[] items) {
			StringBuffer path = new StringBuffer("");//$NON-NLS-1$

			for(int i = 0; i < items.length; i++) {
				path.append(items[i]);
				path.append(pathSeparator);
			}
			return path.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors.ListEditor#getEditedInput(java.lang.String)
		 */
		@Override
		protected String getEditedInput(String input) {
			PromptDialog dialog = new PromptDialog(getShell(), SWT.SHEET) {
				@Override
				protected boolean isValid(String text) {
					boolean result = true;
					if(text.contains(":")) {
						result = false;
						setErrorMessage("Colon ':' is not allowed");
					}
					else if(text.contains("//")) {
						result = false;
						setErrorMessage("Empty segment '//' not allowed");
					}
					return result;
				}
			};
			String[] value = new String[] { input };
			int[] allSubdirs = new int[] { input.endsWith("/*")
					? 1
					: 0 };
			int[] okCancel = new int[] { 1 };

			dialog.prompt(
				"Edit Path Segement", "Edit relative path", "Search all subdirectories", value, allSubdirs, okCancel);
			if(okCancel[0] == 0)
				return input;
			String result = value[0].trim();
			if(allSubdirs[0] == 0) {
				if(result.endsWith("/*"))
					result = result.substring(0, result.length() - 2);
			}
			else if(!result.endsWith("/*"))
				result += "/*";
			return result;
		}

		@Override
		protected String getNewInputObject() {
			PromptDialog dialog = new PromptDialog(getShell(), SWT.SHEET) {
				@Override
				protected boolean isValid(String text) {
					boolean result = true;
					if(text.contains(":")) {
						result = false;
						setErrorMessage("Colon ':' is not allowed");
					}
					else if(text.contains("//")) {
						result = false;
						setErrorMessage("Empty segment '//' not allowed");
					}
					return result;
				}
			};
			String[] value = new String[] { "" };
			int[] allSubdirs = new int[] { 1 };
			int[] okCancel = new int[] { 1 };

			dialog.prompt(
				"Add Path Segement", "Enter new relative path", "Search all subdirectories", value, allSubdirs,
				okCancel);
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

		/*
		 * (non-Javadoc)
		 * Method declared on ListEditor.
		 */
		@Override
		protected String[] parseString(String stringList) {
			StringTokenizer st = new StringTokenizer(stringList, pathSeparator + "\n\r");//$NON-NLS-1$
			ArrayList<String> v = new ArrayList<String>();
			while(st.hasMoreElements()) {
				v.add((String) st.nextElement());
			}
			return v.toArray(new String[v.size()]);
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
