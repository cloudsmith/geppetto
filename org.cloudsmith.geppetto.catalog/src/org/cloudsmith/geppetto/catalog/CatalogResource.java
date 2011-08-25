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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.catalog.CatalogResource#getFile <em>File</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.CatalogResource#getLine <em>Line</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.CatalogResource#getType <em>Type</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.CatalogResource#getTitle <em>Title</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.CatalogResource#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.CatalogResource#isVirtual <em>Virtual</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.CatalogResource#isExported <em>Exported</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalogResource()
 * @model
 * @generated
 */
public interface CatalogResource extends Taggable {
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
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalogResource_File()
	 * @model
	 * @generated
	 */
	String getFile();

	/**
	 * Returns the value of the '<em><b>Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Line</em>' attribute.
	 * @see #setLine(String)
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalogResource_Line()
	 * @model
	 * @generated
	 */
	String getLine();

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.catalog.CatalogResourceParameter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalogResource_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<CatalogResourceParameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Title</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalogResource_Title()
	 * @model required="true"
	 * @generated
	 */
	String getTitle();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalogResource_Type()
	 * @model required="true"
	 * @generated
	 */
	String getType();

	/**
	 * Returns the value of the '<em><b>Exported</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exported</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Exported</em>' attribute.
	 * @see #setExported(boolean)
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalogResource_Exported()
	 * @model
	 * @generated
	 */
	boolean isExported();

	/**
	 * Returns the value of the '<em><b>Virtual</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Virtual</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Virtual</em>' attribute.
	 * @see #setVirtual(boolean)
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalogResource_Virtual()
	 * @model
	 * @generated
	 */
	boolean isVirtual();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.catalog.CatalogResource#isExported <em>Exported</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Exported</em>' attribute.
	 * @see #isExported()
	 * @generated
	 */
	void setExported(boolean value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.catalog.CatalogResource#getFile <em>File</em>}' attribute.
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
	 * Sets the value of the '{@link org.cloudsmith.geppetto.catalog.CatalogResource#getLine <em>Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Line</em>' attribute.
	 * @see #getLine()
	 * @generated
	 */
	void setLine(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.catalog.CatalogResource#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
	void setTitle(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.catalog.CatalogResource#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.catalog.CatalogResource#isVirtual <em>Virtual</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Virtual</em>' attribute.
	 * @see #isVirtual()
	 * @generated
	 */
	void setVirtual(boolean value);

} // CatalogResource
