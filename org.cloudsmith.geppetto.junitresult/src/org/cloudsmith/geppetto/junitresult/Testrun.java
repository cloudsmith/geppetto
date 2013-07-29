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
 * A representation of the model object '<em><b>Testrun</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <!-- begin-model-doc -->
 * Testrun is the document root when the content is created as an export from an Eclipse JUnit run. (Don't know if this is used or supported
 * elsewhere).
 * <!-- end-model-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testrun#getProject <em>Project</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testrun#getStarted <em>Started</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testrun#getIgnored <em>Ignored</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestrun()
 * @model
 * @generated
 */
public interface Testrun extends AbstractAggregatedTest {
	/**
	 * Returns the value of the '<em><b>Ignored</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ignored</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ignored</em>' attribute.
	 * @see #setIgnored(int)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestrun_Ignored()
	 * @model required="true"
	 * @generated
	 */
	int getIgnored();

	/**
	 * Returns the value of the '<em><b>Project</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Project</em>' attribute.
	 * @see #setProject(String)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestrun_Project()
	 * @model required="true"
	 * @generated
	 */
	String getProject();

	/**
	 * Returns the value of the '<em><b>Started</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Started</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Started</em>' attribute.
	 * @see #setStarted(int)
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestrun_Started()
	 * @model required="true"
	 * @generated
	 */
	int getStarted();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testrun#getIgnored <em>Ignored</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Ignored</em>' attribute.
	 * @see #getIgnored()
	 * @generated
	 */
	void setIgnored(int value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testrun#getProject <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Project</em>' attribute.
	 * @see #getProject()
	 * @generated
	 */
	void setProject(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.junitresult.Testrun#getStarted <em>Started</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Started</em>' attribute.
	 * @see #getStarted()
	 * @generated
	 */
	void setStarted(int value);

} // Testrun
