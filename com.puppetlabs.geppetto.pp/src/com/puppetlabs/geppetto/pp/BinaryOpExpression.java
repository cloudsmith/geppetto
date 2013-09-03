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
package com.puppetlabs.geppetto.pp;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Binary Op Expression</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.puppetlabs.geppetto.pp.BinaryOpExpression#getOpName <em>Op Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.puppetlabs.geppetto.pp.PPPackage#getBinaryOpExpression()
 * @model abstract="true"
 * @generated
 */
public interface BinaryOpExpression extends BinaryExpression {
	/**
	 * Returns the value of the '<em><b>Op Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Op Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Op Name</em>' attribute.
	 * @see #setOpName(String)
	 * @see com.puppetlabs.geppetto.pp.PPPackage#getBinaryOpExpression_OpName()
	 * @model
	 * @generated
	 */
	String getOpName();

	/**
	 * Sets the value of the '{@link com.puppetlabs.geppetto.pp.BinaryOpExpression#getOpName <em>Op Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Op Name</em>' attribute.
	 * @see #getOpName()
	 * @generated
	 */
	void setOpName(String value);

} // BinaryOpExpression
