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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Testcase</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testcase#getSkipped <em>Skipped</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testcase#getName <em>Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testcase#getClassname <em>Classname</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testcase#getTime <em>Time</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testcase#getSystem_out <em>System out</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testcase#getSystem_err <em>System err</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testcase#getStatus <em>Status</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testcase#getAssertions <em>Assertions</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testcase#getFailures <em>Failures</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testcase#getErrors <em>Errors</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestcase()
 * @model
 * @generated
 */
public interface Testcase extends EObject {
	/**
	 * Returns the value of the '<em><b>Assertions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Assertions</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Assertions</em>' attribute.
	 * @see #setAssertions(String)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestcase_Assertions()
	 * @model
	 * @generated
	 */
	String getAssertions();

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
	 * @model
	 * @generated
	 */
	String getClassname();

	/**
	 * Returns the value of the '<em><b>Errors</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.junitresult.Error}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Errors</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Errors</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestcase_Errors()
	 * @model containment="true"
	 * @generated
	 */
	EList<org.cloudsmith.geppetto.junitresult.Error> getErrors();

	/**
	 * Returns the value of the '<em><b>Failures</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.junitresult.Failure}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Failures</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Failures</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestcase_Failures()
	 * @model containment="true"
	 * @generated
	 */
	EList<Failure> getFailures();

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
	 * Returns the value of the '<em><b>Skipped</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Skipped</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Skipped</em>' containment reference.
	 * @see #setSkipped(Skipped)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestcase_Skipped()
	 * @model containment="true"
	 * @generated
	 */
	Skipped getSkipped();

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see #setStatus(String)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestcase_Status()
	 * @model
	 * @generated
	 */
	String getStatus();

	/**
	 * Returns the value of the '<em><b>System err</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>System err</em>' attribute list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>System err</em>' attribute list.
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestcase_System_err()
	 * @model
	 * @generated
	 */
	EList<String> getSystem_err();

	/**
	 * Returns the value of the '<em><b>System out</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>System out</em>' attribute list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>System out</em>' attribute list.
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestcase_System_out()
	 * @model
	 * @generated
	 */
	EList<String> getSystem_out();

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
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestcase_Time()
	 * @model
	 * @generated
	 */
	double getTime();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testcase#getAssertions <em>Assertions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Assertions</em>' attribute.
	 * @see #getAssertions()
	 * @generated
	 */
	void setAssertions(String value);

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
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testcase#getSkipped <em>Skipped</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Skipped</em>' containment reference.
	 * @see #getSkipped()
	 * @generated
	 */
	void setSkipped(Skipped value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testcase#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Status</em>' attribute.
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(String value);

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
	void setTime(double value);

} // Testcase
