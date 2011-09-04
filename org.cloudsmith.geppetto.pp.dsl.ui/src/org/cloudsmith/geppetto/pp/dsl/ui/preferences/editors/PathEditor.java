/*******************************************************************************
 * Copyright (c) 2000, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Cloudsmith - edit capability
 *******************************************************************************/
package org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;

/**
 * A field editor to edit directory paths.
 */
public class PathEditor extends ListEditor {

	/**
	 * The last path, or <code>null</code> if none.
	 */
	private String lastPath;

	/**
	 * The special label text for directory chooser,
	 * or <code>null</code> if none.
	 */
	private String dirChooserLabelText;

	/**
	 * Creates a new path field editor
	 */
	protected PathEditor() {
	}

	/**
	 * Creates a path field editor.
	 * 
	 * @param name
	 *            the name of the preference this field editor works on
	 * @param labelText
	 *            the label text of the field editor
	 * @param dirChooserLabelText
	 *            the label text displayed for the directory chooser
	 * @param parent
	 *            the parent of the field editor's control
	 */
	public PathEditor(String name, String labelText, String dirChooserLabelText, Composite parent) {
		init(name, labelText);
		this.dirChooserLabelText = dirChooserLabelText;
		createControl(parent);
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
			path.append(File.pathSeparator);
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
		PromptDialog dialog = new PromptDialog(getShell(), SWT.SHEET);
		String[] value = new String[] { input };
		int[] allSubdirs = new int[] { input.endsWith("/*")
				? 1
				: 0 };
		int[] okCancel = new int[] { 1 };

		dialog.prompt(
			"Edit path segement", "Enter relative path", "Search all subdirectories", value, allSubdirs, okCancel);
		if(okCancel[0] == 0)
			return input;
		if(allSubdirs[0] == 0)
			return value[0].trim();
		String result = value[0].trim();
		if(!result.endsWith("/*"))
			result += "/*";
		return result;
	}

	/*
	 * (non-Javadoc)
	 * Method declared on ListEditor.
	 * Creates a new path element by means of a directory dialog.
	 */
	@Override
	protected String getNewInputObject() {

		DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SHEET);
		if(dirChooserLabelText != null) {
			dialog.setMessage(dirChooserLabelText);
		}
		if(lastPath != null) {
			if(new File(lastPath).exists()) {
				dialog.setFilterPath(lastPath);
			}
		}
		String dir = dialog.open();
		if(dir != null) {
			dir = dir.trim();
			if(dir.length() == 0) {
				return null;
			}
			lastPath = dir;
		}
		return dir;
	}

	/*
	 * (non-Javadoc)
	 * Method declared on ListEditor.
	 */
	@Override
	protected String[] parseString(String stringList) {
		StringTokenizer st = new StringTokenizer(stringList, File.pathSeparator + "\n\r");//$NON-NLS-1$
		ArrayList<String> v = new ArrayList<String>();
		while(st.hasMoreElements()) {
			v.add((String) st.nextElement());
		}
		return v.toArray(new String[v.size()]);
	}
}
