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

import java.util.Date;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Testsuite</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuite#getProperties <em>Properties</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuite#getTestcases <em>Testcases</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuite#getSystem_out <em>System out</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuite#getSystem_err <em>System err</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuite#getHostname <em>Hostname</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuite#getTimestamp <em>Timestamp</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuite#getTime <em>Time</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuite#getTestsuites <em>Testsuites</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuite#getId <em>Id</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuite#getPackage <em>Package</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuite#getDisabled <em>Disabled</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuite#getSkipped <em>Skipped</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuite()
 * @model
 * @generated
 */
public interface Testsuite extends AbstractAggregatedTest {
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
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuite_Disabled()
	 * @model
	 * @generated
	 */
	int getDisabled();

	/**
	 * Returns the value of the '<em><b>Hostname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hostname</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Hostname</em>' attribute.
	 * @see #setHostname(String)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuite_Hostname()
	 * @model
	 * @generated
	 */
	String getHostname();

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Id used by a testsuite when testsuite is part of a junitreport.
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(int)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuite_Id()
	 * @model
	 * @generated
	 */
	int getId();

	/**
	 * Returns the value of the '<em><b>Package</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Package is used by a testsuite when testsuite is part of a junitreport.
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Package</em>' attribute.
	 * @see #setPackage(String)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuite_Package()
	 * @model
	 * @generated
	 */
	String getPackage();

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.junitresult.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuite_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getProperties();

	/**
	 * Returns the value of the '<em><b>Skipped</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Skipped</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Skipped</em>' attribute.
	 * @see #setSkipped(int)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuite_Skipped()
	 * @model
	 * @generated
	 */
	int getSkipped();

	/**
	 * Returns the value of the '<em><b>System err</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>System err</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>System err</em>' attribute.
	 * @see #setSystem_err(String)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuite_System_err()
	 * @model
	 * @generated
	 */
	String getSystem_err();

	/**
	 * Returns the value of the '<em><b>System out</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>System out</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>System out</em>' attribute.
	 * @see #setSystem_out(String)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuite_System_out()
	 * @model
	 * @generated
	 */
	String getSystem_out();

	/**
	 * Returns the value of the '<em><b>Testcases</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.junitresult.Testcase}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Testcases</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Testcases</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuite_Testcases()
	 * @model containment="true"
	 * @generated
	 */
	EList<Testcase> getTestcases();

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
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuite_Testsuites()
	 * @model containment="true"
	 * @generated
	 */
	EList<Testsuite> getTestsuites();

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
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuite_Time()
	 * @model
	 * @generated
	 */
	double getTime();

	/**
	 * Returns the value of the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timestamp</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Timestamp</em>' attribute.
	 * @see #setTimestamp(Date)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuite_Timestamp()
	 * @model
	 * @generated
	 */
	Date getTimestamp();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getDisabled <em>Disabled</em>}' attribute.
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
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getHostname <em>Hostname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Hostname</em>' attribute.
	 * @see #getHostname()
	 * @generated
	 */
	void setHostname(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(int value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getPackage <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Package</em>' attribute.
	 * @see #getPackage()
	 * @generated
	 */
	void setPackage(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getSkipped <em>Skipped</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Skipped</em>' attribute.
	 * @see #getSkipped()
	 * @generated
	 */
	void setSkipped(int value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getSystem_err <em>System err</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>System err</em>' attribute.
	 * @see #getSystem_err()
	 * @generated
	 */
	void setSystem_err(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getSystem_out <em>System out</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>System out</em>' attribute.
	 * @see #getSystem_out()
	 * @generated
	 */
	void setSystem_out(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getTime <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Time</em>' attribute.
	 * @see #getTime()
	 * @generated
	 */
	void setTime(double value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testsuite#getTimestamp <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Timestamp</em>' attribute.
	 * @see #getTimestamp()
	 * @generated
	 */
	void setTimestamp(Date value);

} // Testsuite
