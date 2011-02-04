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

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.Type#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Type#getProperties <em>Properties</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Type#getProviders <em>Providers</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.forge.ForgePackage#getType()
 * @model
 * @generated
 */
public interface Type extends Documented {
	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.forge.Parameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getType_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Parameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.forge.Property}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Properties</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getType_Properties()
	 * @model containment="true"
	 * @generated
	 */
	EList<Property> getProperties();

	/**
	 * Returns the value of the '<em><b>Providers</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.forge.Provider}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Providers</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Providers</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getType_Providers()
	 * @model containment="true"
	 * @generated
	 */
	EList<Provider> getProviders();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model exceptions="org.cloudsmith.geppetto.forge.IOException" providerDirDataType="org.cloudsmith.geppetto.forge.File"
	 * @generated
	 */
	void loadProvider(File providerDir) throws IOException;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model exceptions="org.cloudsmith.geppetto.forge.IOException" typeFileDataType="org.cloudsmith.geppetto.forge.File"
	 * @generated
	 */
	void loadTypeFile(File typeFile) throws IOException;

} // Type
