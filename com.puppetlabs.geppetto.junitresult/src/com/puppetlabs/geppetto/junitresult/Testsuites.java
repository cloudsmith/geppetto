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
package org.cloudsmith.geppetto.junitresult;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Testsuites</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <!-- begin-model-doc -->
 * The document root of a junitreport.
 * <!-- end-model-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuites#getTime <em>Time</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuites#getDisabled <em>Disabled</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuites()
 * @model
 * @generated
 */
public interface Testsuites extends AbstractAggregatedTest {
	/**
	 * Returns the value of the '<em><b>Disabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Disabled</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Disabled</em>' attribute.
	 * @see #setDisabled(int)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuites_Disabled()
	 * @model
	 * @generated
	 */
	int getDisabled();

	/**
	 * Returns the value of the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Time</em>' attribute.
	 * @see #setTime(double)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuites_Time()
	 * @model
	 * @generated
	 */
	double getTime();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testsuites#getDisabled <em>Disabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Disabled</em>' attribute.
	 * @see #getDisabled()
	 * @generated
	 */
	void setDisabled(int value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testsuites#getTime <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Time</em>' attribute.
	 * @see #getTime()
	 * @generated
	 */
	void setTime(double value);

} // Testsuites
