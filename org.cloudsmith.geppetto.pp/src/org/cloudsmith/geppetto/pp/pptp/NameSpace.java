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
package org.cloudsmith.geppetto.pp.pptp;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Name Space</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.NameSpace#isReserved <em>Reserved</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getNameSpace()
 * @model
 * @generated
 */
public interface NameSpace extends TargetElement, ITargetElementContainer {
	/**
	 * Returns the value of the '<em><b>Reserved</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reserved</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Reserved</em>' attribute.
	 * @see #setReserved(boolean)
	 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getNameSpace_Reserved()
	 * @model
	 * @generated
	 */
	boolean isReserved();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.pptp.NameSpace#isReserved <em>Reserved</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Reserved</em>' attribute.
	 * @see #isReserved()
	 * @generated
	 */
	void setReserved(boolean value);

} // NameSpace
