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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cache</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.Cache#getLocation <em>Location</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Cache#getRepository <em>Repository</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.forge.ForgePackage#getCache()
 * @model
 * @generated
 */
public interface Cache extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model exceptions="org.cloudsmith.geppetto.forge.IOException"
	 * @generated
	 */
	void clean() throws IOException;

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Location</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getCache_Location()
	 * @model dataType="org.cloudsmith.geppetto.forge.File" changeable="false"
	 * @generated
	 */
	File getLocation();

	/**
	 * Returns the value of the '<em><b>Repository</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repository</em>' reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Repository</em>' reference.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getCache_Repository()
	 * @model resolveProxies="false" required="true" transient="true" changeable="false"
	 * @generated
	 */
	Repository getRepository();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model dataType="org.cloudsmith.geppetto.forge.File" required="true" exceptions="org.cloudsmith.geppetto.forge.IOException"
	 * @generated
	 */
	File retrieve(String fileName) throws IOException;

} // Cache
