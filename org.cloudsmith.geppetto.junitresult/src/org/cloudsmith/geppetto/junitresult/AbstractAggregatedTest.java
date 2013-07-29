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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Aggregated Test</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getName <em>Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getTests <em>Tests</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getFailures <em>Failures</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getErrors <em>Errors</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getTestsuites <em>Testsuites</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getAbstractAggregatedTest()
 * @model abstract="true"
 * @generated
 */
public interface AbstractAggregatedTest extends JunitResult {
	/**
	 * Returns the value of the '<em><b>Errors</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Errors</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Errors</em>' attribute.
	 * @see #setErrors(int)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getAbstractAggregatedTest_Errors()
	 * @model
	 * @generated
	 */
	int getErrors();

	/**
	 * Returns the value of the '<em><b>Failures</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Failures</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Failures</em>' attribute.
	 * @see #setFailures(int)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getAbstractAggregatedTest_Failures()
	 * @model
	 * @generated
	 */
	int getFailures();

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
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getAbstractAggregatedTest_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Tests</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tests</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Tests</em>' attribute.
	 * @see #setTests(int)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getAbstractAggregatedTest_Tests()
	 * @model required="true"
	 * @generated
	 */
	int getTests();

	/**
	 * Returns the value of the '<em><b>Testsuites</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.junitresult.Testsuite}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Testsuites</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Testsuites</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getAbstractAggregatedTest_Testsuites()
	 * @model containment="true"
	 * @generated
	 */
	EList<Testsuite> getTestsuites();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getErrors <em>Errors</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Errors</em>' attribute.
	 * @see #getErrors()
	 * @generated
	 */
	void setErrors(int value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getFailures <em>Failures</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Failures</em>' attribute.
	 * @see #getFailures()
	 * @generated
	 */
	void setFailures(int value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getName <em>Name</em>}' attribute.
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
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest#getTests <em>Tests</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Tests</em>' attribute.
	 * @see #getTests()
	 * @generated
	 */
	void setTests(int value);

} // AbstractAggregatedTest
