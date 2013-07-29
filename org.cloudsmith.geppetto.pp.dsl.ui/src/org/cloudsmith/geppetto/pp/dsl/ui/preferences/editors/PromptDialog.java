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
package org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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

public class PromptDialog extends Dialog {

	private Text valueText;

	private Button[] buttons;

	private ControlDecoration valueTextDecorator;

	protected String errorMessage = "";

	public PromptDialog(Shell parent) {
		this(parent, 0);
	}

	public PromptDialog(Shell parent, int style) {
		super(parent, style);
	}

	public void clearError() {
		if(valueTextDecorator != null) {
			valueTextDecorator.hide();
		}
	}

	private ControlDecoration createDecorator(Text text, String message) {
		ControlDecoration controlDecoration = new ControlDecoration(text, SWT.LEFT | SWT.TOP);
		controlDecoration.setDescriptionText(message);
		FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault().getFieldDecoration(
			FieldDecorationRegistry.DEC_ERROR);
		controlDecoration.setImage(fieldDecoration.getImage());
		return controlDecoration;
	}

	private void disableOk() {
		if(buttons != null && buttons[1] != null)
			buttons[1].setEnabled(false);
	}

	private void enableOk() {
		if(buttons != null && buttons[1] != null)
			buttons[1].setEnabled(true);
	}

	protected String getErrorMessage() {
		return errorMessage;
	}

	protected boolean isValid(String text) {
		return true;
	}

	protected boolean isValueRequired() {
		return true;
	}

	public void prompt(String title, String text, String check, final String[] value, final int[] checkValue,
			final int[] result) {
		Shell parent = getParent();

		final Shell shell = new Shell(parent, SWT.SHEET | SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		if(title != null)
			shell.setText(title);

		GridLayout gridLayout = new GridLayout();
		shell.setLayout(gridLayout);
		Label label = new Label(shell, SWT.WRAP | SWT.BOLD);
		label.setText(text);
		label.setFont(JFaceResources.getBannerFont());
		GridData data = new GridData();
		Monitor monitor = parent.getMonitor();
		int maxWidth = monitor.getBounds().width * 2 / 3;
		int width = label.computeSize(SWT.DEFAULT, SWT.DEFAULT).x;
		data.widthHint = Math.min(width, maxWidth);
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.horizontalIndent = 15;
		data.verticalIndent = 15;

		label.setLayoutData(data);

		valueText = new Text(shell, SWT.BORDER);
		valueTextDecorator = createDecorator(valueText, "initial");
		valueTextDecorator.setMarginWidth(5);
		valueTextDecorator.hide();

		valueText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				updateStatus();
			}
		});

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
		data.horizontalIndent = 15;

		data.grabExcessHorizontalSpace = true;
		valueText.setTextLimit(200);
		valueText.setLayoutData(data);

		buttons = new Button[3];
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
			data.horizontalIndent = 15;
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

		// make RETURN trigger OK
		shell.setDefaultButton(buttons[1]);

		// update status based on current value
		updateStatus();

		shell.pack();
		shell.open();
		Display display = parent.getDisplay();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch())
				display.sleep();
		}
	}

	public void setError(String message) {
		valueTextDecorator.setDescriptionText(message);
		valueTextDecorator.show();
		valueTextDecorator.showHoverText(message);
	}

	protected void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	private void updateStatus() {
		String theText = valueText.getText();
		if(isValueRequired()) {
			if(theText.trim().length() < 1) {
				// do not report as an error, just disable ok, but
				// must clear any previous error.
				clearError();
				disableOk();
				return;
			}
		}
		if(isValid(theText)) {
			clearError();
			enableOk();
		}
		else {
			setError(getErrorMessage());
			disableOk();
		}
	}
}
