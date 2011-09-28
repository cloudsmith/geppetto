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
package org.cloudsmith.geppetto.catalog;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Parameter</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.catalog.CatalogResourceParameter#getName <em>Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.CatalogResourceParameter#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalogResourceParameter()
 * @model
 * @generated
 */
public interface CatalogResourceParameter extends EObject {
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
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalogResourceParameter_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Value</em>' attribute list.
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalogResourceParameter_Value()
	 * @model required="true"
	 * @generated
	 */
	EList<String> getValue();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.catalog.CatalogResourceParameter#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // CatalogResourceParameter
