/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.pptp;

import java.io.File;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Target Element</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.TargetElement#getFile <em>File</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getTargetElement()
 * @model abstract="true"
 * @generated
 */
public interface TargetElement extends INamed, IDocumented {

	/**
	 * Returns the value of the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * File relative to TargetEntry. May be null if irrelevant (for predefined targets).
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>File</em>' attribute.
	 * @see #setFile(File)
	 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getTargetElement_File()
	 * @model dataType="org.cloudsmith.geppetto.pp.pptp.File"
	 * @generated
	 */
	File getFile();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.pptp.TargetElement#getFile <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>File</em>' attribute.
	 * @see #getFile()
	 * @generated
	 */
	void setFile(File value);
} // TargetElement
