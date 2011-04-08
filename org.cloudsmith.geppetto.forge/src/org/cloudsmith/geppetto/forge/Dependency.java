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
 * A representation of the model object '<em><b>Dependency</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.Dependency#getName <em>Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Dependency#getRepository <em>Repository</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Dependency#getVersionRequirement <em>Version Requirement</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.forge.ForgePackage#getDependency()
 * @model
 * @generated
 */
public interface Dependency extends EObject {
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
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getDependency_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Repository</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repository</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Repository</em>' attribute.
	 * @see #setRepository(URI)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getDependency_Repository()
	 * @model dataType="org.cloudsmith.geppetto.forge.URI"
	 * @generated
	 */
	URI getRepository();

	/**
	 * Returns the value of the '<em><b>Version Requirement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version Requirement</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Version Requirement</em>' containment reference.
	 * @see #setVersionRequirement(VersionRequirement)
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getDependency_VersionRequirement()
	 * @model containment="true"
	 * @generated
	 */
	VersionRequirement getVersionRequirement();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model nameRequired="true"
	 * @generated
	 */
	boolean matches(String name, String version);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.Dependency#getName <em>Name</em>}' attribute.
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
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.Dependency#getRepository <em>Repository</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Repository</em>' attribute.
	 * @see #getRepository()
	 * @generated
	 */
	void setRepository(URI value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.forge.Dependency#getVersionRequirement <em>Version Requirement</em>}' containment
	 * reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Version Requirement</em>' containment reference.
	 * @see #getVersionRequirement()
	 * @generated
	 */
	void setVersionRequirement(VersionRequirement value);

} // Dependency
