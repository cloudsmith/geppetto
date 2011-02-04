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

import java.net.URI;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Module Info</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.ModuleInfo#getFullName <em>Full Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.ModuleInfo#getName <em>Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.ModuleInfo#getProjectURL <em>Project URL</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.ModuleInfo#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.forge.ForgePackage#getModuleInfo()
 * @model
 * @generated
 */
public interface ModuleInfo extends EObject {
	/**
	 * Returns the value of the '<em><b>Full Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The full name of the module
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Full Name</em>' attribute.
	 * @see #setFullName(String)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getModuleInfo_FullName()
	 * @model
	 * @generated
	 */
	String getFullName();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The short name (id) for the module
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getModuleInfo_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Project URL</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The location of the module
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Project URL</em>' attribute.
	 * @see #setProjectURL(URI)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getModuleInfo_ProjectURL()
	 * @model dataType="org.cloudsmith.geppetto.forge.URI"
	 * @generated
	 */
	URI getProjectURL();

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
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getModuleInfo_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.ModuleInfo#getFullName <em>Full Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Full Name</em>' attribute.
	 * @see #getFullName()
	 * @generated
	 */
	void setFullName(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.ModuleInfo#getName <em>Name</em>}' attribute.
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
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.ModuleInfo#getProjectURL <em>Project URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Project URL</em>' attribute.
	 * @see #getProjectURL()
	 * @generated
	 */
	void setProjectURL(URI value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.ModuleInfo#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // ModuleInfo
