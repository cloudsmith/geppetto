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

import org.eclipse.emf.common.util.EList;

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
 * <li>{@link org.cloudsmith.geppetto.junitresult.Testsuites#getTestsuites <em>Testsuites</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuites()
 * @model
 * @generated
 */
public interface Testsuites extends JunitResult {
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
	 * @see org.cloudsmith.geppetto.junitresult.JunitresultPackage#getTestsuites_Testsuites()
	 * @model containment="true"
	 * @generated
	 */
	EList<Testsuite> getTestsuites();

} // Testsuites
