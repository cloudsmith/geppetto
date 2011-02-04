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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.Repository#getRepository <em>Repository</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Repository#getCacheKey <em>Cache Key</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.forge.ForgePackage#getRepository()
 * @model
 * @generated
 */
public interface Repository extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @model dataType="org.cloudsmith.geppetto.forge.HttpURLConnection" exceptions="org.cloudsmith.geppetto.forge.IOException"
	 * @generated
	 */
	HttpURLConnection connect(HttpMethod method, String urlSuffix) throws IOException;

	/**
	 * Returns the value of the '<em><b>Cache Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache Key</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Cache Key</em>' attribute.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getRepository_CacheKey()
	 * @model required="true" transient="true" changeable="false"
	 * @generated
	 */
	String getCacheKey();

	/**
	 * Returns the value of the '<em><b>Repository</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The repository location
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Repository</em>' attribute.
	 * @see org.cloudsmith.geppetto.forge.ForgePackage#getRepository_Repository()
	 * @model dataType="org.cloudsmith.geppetto.forge.URI" required="true" changeable="false"
	 * @generated
	 */
	URI getRepository();

} // Repository
