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
package org.cloudsmith.geppetto.pp;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Double Quoted String</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.DoubleQuotedString#getTextExpression <em>Text Expression</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage#getDoubleQuotedString()
 * @model
 * @generated
 */
public interface DoubleQuotedString extends StringExpression, IQuotedString {
	/**
	 * Returns the value of the '<em><b>Text Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text Expression</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Text Expression</em>' containment reference.
	 * @see #setTextExpression(TextExpression)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getDoubleQuotedString_TextExpression()
	 * @model containment="true"
	 * @generated
	 */
	TextExpression getTextExpression();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.DoubleQuotedString#getTextExpression <em>Text Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Text Expression</em>' containment reference.
	 * @see #getTextExpression()
	 * @generated
	 */
	void setTextExpression(TextExpression value);

} // DoubleQuotedString
