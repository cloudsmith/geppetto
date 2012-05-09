/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.junitresult;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Testcase</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testcase#getNegativeResult <em>Negative Result</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testcase#getClassname <em>Classname</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testcase#getName <em>Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testcase#getTime <em>Time</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestcase()
 * @model
 * @generated
 */
public interface Testcase extends EObject {
	/**
	 * Returns the value of the '<em><b>Classname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classname</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Classname</em>' attribute.
	 * @see #setClassname(String)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestcase_Classname()
	 * @model required="true"
	 * @generated
	 */
	String getClassname();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestcase_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Negative Result</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Negative Result</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Negative Result</em>' containment reference.
	 * @see #setNegativeResult(NegativeResult)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestcase_NegativeResult()
	 * @model containment="true"
	 * @generated
	 */
	NegativeResult getNegativeResult();

	/**
	 * Returns the value of the '<em><b>Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Time</em>' attribute.
	 * @see #setTime(String)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestcase_Time()
	 * @model required="true"
	 * @generated
	 */
	String getTime();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testcase#getClassname <em>Classname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Classname</em>' attribute.
	 * @see #getClassname()
	 * @generated
	 */
	void setClassname(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testcase#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testcase#getNegativeResult <em>Negative Result</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Negative Result</em>' containment reference.
	 * @see #getNegativeResult()
	 * @generated
	 */
	void setNegativeResult(NegativeResult value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testcase#getTime <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Time</em>' attribute.
	 * @see #getTime()
	 * @generated
	 */
	void setTime(String value);

} // Testcase
