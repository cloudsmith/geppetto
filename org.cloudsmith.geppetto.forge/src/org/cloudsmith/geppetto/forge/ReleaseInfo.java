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
package org.cloudsmith.geppetto.forge;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Release Info</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.ReleaseInfo#getFile <em>File</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.ReleaseInfo#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.forge.ForgePackage#getReleaseInfo()
 * @model
 * @generated
 */
public interface ReleaseInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>File</em>' attribute.
	 * @see #setFile(String)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getReleaseInfo_File()
	 * @model
	 * @generated
	 */
	String getFile();

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getReleaseInfo_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.ReleaseInfo#getFile <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>File</em>' attribute.
	 * @see #getFile()
	 * @generated
	 */
	void setFile(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.ReleaseInfo#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // ReleaseInfo
