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

import org.eclipse.jface.preference.PathEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.xtext.ui.editor.preferences.AbstractPreferencePage;

/**
 * A simple preference page for search path and environment
 * 
 */
public class PPPathPreferencePage extends AbstractPreferencePage {
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

	static class PromptDialog extends Dialog {

		PromptDialog(Shell parent) {
			this(parent, 0);
		}

		PromptDialog(Shell parent, int style) {
			super(parent, style);
		}

		void prompt(String title, String text, String check, final String[] value, final int[] checkValue,
				final int[] result) {
			Shell parent = getParent();

			final Shell shell = new Shell(parent, SWT.SHEET | SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			if(title != null)
				shell.setText(title);
			GridLayout gridLayout = new GridLayout();
			shell.setLayout(gridLayout);
			Label label = new Label(shell, SWT.WRAP);
			label.setText(text);
			GridData data = new GridData();
			Monitor monitor = parent.getMonitor();
			int maxWidth = monitor.getBounds().width * 2 / 3;
			int width = label.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
			data.widthHint = Math.min(width, maxWidth);
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			label.setLayoutData(data);

			final Text valueText = new Text(shell, SWT.BORDER);
			if(value[0] != null)
				valueText.setText(value[0]);
			data = new GridData();
			width = valueText.computeSize(500, SWT.DEFAULT).x;
			if(width > maxWidth)
				data.widthHint = maxWidth;
			else
				data.widthHint = width;
			data.minimumWidth = 300;
			data.horizontalAlignment = GridData.FILL;
			data.grabExcessHorizontalSpace = true;
			valueText.setTextLimit(200);
			valueText.setLayoutData(data);

			final Button[] buttons = new Button[3];
			Listener listener = new Listener() {
				public void handleEvent(Event event) {
					if(buttons[0] != null)
						checkValue[0] = buttons[0].getSelection()
								? 1
								: 0;
					value[0] = valueText.getText();
					result[0] = event.widget == buttons[1]
							? 1
							: 0;
					shell.close();
				}
			};
			if(check != null) {
				buttons[0] = new Button(shell, SWT.CHECK);
				buttons[0].setText(check);
				buttons[0].setSelection(checkValue[0] != 0);
				data = new GridData();
				data.horizontalAlignment = GridData.BEGINNING;
				buttons[0].setLayoutData(data);
			}
			Composite composite = new Composite(shell, SWT.NONE);
			data = new GridData();
			data.horizontalAlignment = GridData.CENTER;
			composite.setLayoutData(data);
			composite.setLayout(new GridLayout(2, true));
			buttons[1] = new Button(composite, SWT.PUSH);
			buttons[1].setText(SWT.getMessage("SWT_OK")); //$NON-NLS-1$
			buttons[1].setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			buttons[1].addListener(SWT.Selection, listener);
			buttons[2] = new Button(composite, SWT.PUSH);
			buttons[2].setText(SWT.getMessage("SWT_Cancel")); //$NON-NLS-1$
			buttons[2].setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			buttons[2].addListener(SWT.Selection, listener);

			shell.pack();
			shell.open();
			Display display = parent.getDisplay();
			while(!shell.isDisposed()) {
				if(!display.readAndDispatch())
					display.sleep();
			}
		}
	}

	@Override
	protected void createFieldEditors() {
		PPPathEditor pathField = new PPPathEditor(PPPreferenceConstants.PUPPET_PROJECT_PATH, //
			"Search Path", getFieldEditorParent());
		addField(pathField);
	}

}
