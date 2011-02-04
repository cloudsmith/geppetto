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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Text Expression</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.TextExpression#getTrailing <em>Trailing</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.TextExpression#getLeading <em>Leading</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage#getTextExpression()
 * @model abstract="true"
 * @generated
 */
public interface TextExpression extends EObject {

	/**
	 * Returns the value of the '<em><b>Leading</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Leading</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Leading</em>' containment reference.
	 * @see #setLeading(TextExpression)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getTextExpression_Leading()
	 * @model containment="true"
	 * @generated
	 */
	TextExpression getLeading();

	/**
	 * Returns the value of the '<em><b>Trailing</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Trailing</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Trailing</em>' containment reference.
	 * @see #setTrailing(TextExpression)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getTextExpression_Trailing()
	 * @model containment="true"
	 * @generated
	 */
	TextExpression getTrailing();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.TextExpression#getLeading <em>Leading</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Leading</em>' containment reference.
	 * @see #getLeading()
	 * @generated
	 */
	void setLeading(TextExpression value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.TextExpression#getTrailing <em>Trailing</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Trailing</em>' containment reference.
	 * @see #getTrailing()
	 * @generated
	 */
	void setTrailing(TextExpression value);
} // TextExpression
