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
 * A representation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.puppetlabs.geppetto.pp.pptp.Function#isRValue <em>RValue</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.puppetlabs.geppetto.pp.pptp.PPTPPackage#getFunction()
 * @model
 * @generated
 */
public interface Function extends IDocumented, TargetElement {
	/**
	 * Returns the value of the '<em><b>RValue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>RValue</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>RValue</em>' attribute.
	 * @see #setRValue(boolean)
	 * @see com.puppetlabs.geppetto.pp.pptp.PPTPPackage#getFunction_RValue()
	 * @model
	 * @generated
	 */
	boolean isRValue();

	/**
	 * Sets the value of the '{@link com.puppetlabs.geppetto.pp.pptp.Function#isRValue <em>RValue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>RValue</em>' attribute.
	 * @see #isRValue()
	 * @generated
	 */
	void setRValue(boolean value);

} // Function
