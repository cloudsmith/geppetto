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
package com.puppetlabs.geppetto.pp.pptp;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>TP Variable</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.puppetlabs.geppetto.pp.pptp.TPVariable#isAssignable <em>Assignable</em>}</li>
 * <li>{@link com.puppetlabs.geppetto.pp.pptp.TPVariable#getPattern <em>Pattern</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.puppetlabs.geppetto.pp.pptp.PPTPPackage#getTPVariable()
 * @model
 * @generated
 */
public interface TPVariable extends TargetElement {
	/**
	 * Returns the value of the '<em><b>Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If not null, this a java regexp pattern for what may follow after the name, the name can be seen as the prefix.
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Pattern</em>' attribute.
	 * @see #setPattern(String)
	 * @see com.puppetlabs.geppetto.pp.pptp.PPTPPackage#getTPVariable_Pattern()
	 * @model
	 * @generated
	 */
	String getPattern();

	/**
	 * Returns the value of the '<em><b>Assignable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assignable</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Assignable</em>' attribute.
	 * @see #setAssignable(boolean)
	 * @see com.puppetlabs.geppetto.pp.pptp.PPTPPackage#getTPVariable_Assignable()
	 * @model
	 * @generated
	 */
	boolean isAssignable();

	/**
	 * Sets the value of the '{@link com.puppetlabs.geppetto.pp.pptp.TPVariable#isAssignable <em>Assignable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Assignable</em>' attribute.
	 * @see #isAssignable()
	 * @generated
	 */
	void setAssignable(boolean value);

	/**
	 * Sets the value of the '{@link com.puppetlabs.geppetto.pp.pptp.TPVariable#getPattern <em>Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Pattern</em>' attribute.
	 * @see #getPattern()
	 * @generated
	 */
	void setPattern(String value);

} // TPVariable
