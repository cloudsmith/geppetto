package org.cloudsmith.geppetto.pp.dsl.ui.preferences.editors;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * A field editor for an integer type preference.
 */
public class CopyOfIntegerFieldEditor extends StringFieldEditor {
	private int minValidValue = 0;

	private int maxValidValue = Integer.MAX_VALUE;

	private static final int DEFAULT_TEXT_LIMIT = 10;

	private ControlDecoration valueTextDecorator;

	/**
	 * Creates a new integer field editor
	 */
	protected CopyOfIntegerFieldEditor() {
	}

	/**
	 * Creates an integer field editor.
	 * 
	 * @param name
	 *            the name of the preference this field editor works on
	 * @param labelText
	 *            the label text of the field editor
	 * @param parent
	 *            the parent of the field editor's control
	 */
	public CopyOfIntegerFieldEditor(String name, String labelText, Composite parent) {
		this(name, labelText, parent, DEFAULT_TEXT_LIMIT);
	}

	/**
	 * Creates an integer field editor.
	 * To set the number of allowed characters use {@link #setTextLimit(int)}.
	 * 
	 * @param name
	 *            the name of the preference this field editor works on
	 * @param labelText
	 *            the label text of the field editor
	 * @param parent
	 *            the parent of the field editor's control
	 * @param width
	 *            the width of the field in characters
	 */
	public CopyOfIntegerFieldEditor(String name, String labelText, Composite parent, int width) {
		super(name, labelText, width, parent);
		init(name, labelText);
		setTextLimit(UNLIMITED);
		setEmptyStringAllowed(false);
		setErrorMessage(JFaceResources.getString("IntegerFieldEditor.errorMessage"));//$NON-NLS-1$
		createControl(parent);
	}

	/*
	 * (non-Javadoc)
	 * Method declared on StringFieldEditor.
	 * Checks whether the entered String is a valid integer or not.
	 */
	@Override
	protected boolean checkState() {

		Text text = getTextControl();

		if(text == null) {
			return false;
		}

		String numberString = text.getText();
		try {
			int number = Integer.valueOf(numberString).intValue();
			if(number >= minValidValue && number <= maxValidValue) {
				clearErrorMessage();
				return true;
			}

			showErrorMessage();
			return false;

		}
		catch(NumberFormatException e1) {
			showErrorMessage();
		}

		return false;
	}

	// @Override
	// protected void clearErrorMessage() {
	// // valueTextDecorator.hide();
	// }

	private ControlDecoration createDecorator(Text text, String message) {
		ControlDecoration controlDecoration = new ControlDecoration(text, SWT.LEFT | SWT.TOP);
		controlDecoration.setDescriptionText(message);
		FieldDecoration fieldDecoration = FieldDecorationRegistry.getDefault().getFieldDecoration(
			FieldDecorationRegistry.DEC_ERROR);
		controlDecoration.setImage(fieldDecoration.getImage());
		return controlDecoration;
	}

	/*
	 * (non-Javadoc)
	 * Method declared on FieldEditor.
	 */
	@Override
	protected void doLoad() {
		Text text = getTextControl();
		if(text != null) {
			int value = getPreferenceStore().getInt(getPreferenceName());
			text.setText(String.valueOf(value));//$NON-NLS-1$
			oldValue = "" + value; //$NON-NLS-1$
		}

	}

	/*
	 * (non-Javadoc)
	 * Method declared on FieldEditor.
	 */
	@Override
	protected void doLoadDefault() {
		Text text = getTextControl();
		if(text != null) {
			int value = getPreferenceStore().getDefaultInt(getPreferenceName());
			text.setText("" + value);//$NON-NLS-1$
		}
		valueChanged();
	}

	/*
	 * (non-Javadoc)
	 * Method declared on FieldEditor.
	 */
	@Override
	protected void doStore() {
		Text text = getTextControl();
		if(text != null) {
			Integer i = new Integer(text.getText());
			getPreferenceStore().setValue(getPreferenceName(), i.intValue());
		}
	}

	/**
	 * Returns this field editor's current value as an integer.
	 * 
	 * @return the value
	 * @exception NumberFormatException
	 *                if the <code>String</code> does not
	 *                contain a parsable integer
	 */
	public int getIntValue() throws NumberFormatException {
		return new Integer(getStringValue()).intValue();
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see org.eclipse.jface.preference.StringFieldEditor#getTextControl(org.eclipse.swt.widgets.Composite)
	// */
	// @Override
	// public Text getTextControl(Composite parent) {
	// Text t0 = this.getTextControl();
	// Text t2 = super.getTextControl(parent);
	// if(t0 == null) {
	// // valueTextDecorator = createDecorator(t2, "initial");
	// // valueTextDecorator.setMarginWidth(5);
	// // valueTextDecorator.hide();
	// }
	// return t2;
	// }

	/**
	 * Sets the range of valid values for this field.
	 * 
	 * @param min
	 *            the minimum allowed value (inclusive)
	 * @param max
	 *            the maximum allowed value (inclusive)
	 */
	public void setValidRange(int min, int max) {
		minValidValue = min;
		maxValidValue = max;
		setErrorMessage(JFaceResources.format("IntegerFieldEditor.errorMessageRange", //$NON-NLS-1$
			new Object[] { new Integer(min), new Integer(max) }));
	}

	// @Override
	// protected void showErrorMessage(String msg) {
	// // valueTextDecorator.setDescriptionText(msg);
	// // valueTextDecorator.show();
	// }
}
