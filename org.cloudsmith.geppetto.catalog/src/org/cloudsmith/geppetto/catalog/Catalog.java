/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package org.cloudsmith.geppetto.catalog;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Catalog</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.catalog.Catalog#getName <em>Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.Catalog#getVersion <em>Version</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.Catalog#getResources <em>Resources</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.Catalog#getClasses <em>Classes</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.Catalog#getMetadata <em>Metadata</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.Catalog#getEdges <em>Edges</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalog()
 * @model
 * @generated
 */
public interface Catalog extends Taggable {
	/**
	 * Returns the value of the '<em><b>Classes</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Classes</em>' attribute list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Classes</em>' attribute list.
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalog_Classes()
	 * @model
	 * @generated
	 */
	EList<String> getClasses();

	/**
	 * Returns the value of the '<em><b>Edges</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.catalog.CatalogEdge}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Edges</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Edges</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalog_Edges()
	 * @model containment="true"
	 * @generated
	 */
	EList<CatalogEdge> getEdges();

	/**
	 * Returns the value of the '<em><b>Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metadata</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Metadata</em>' containment reference.
	 * @see #setMetadata(CatalogMetadata)
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalog_Metadata()
	 * @model containment="true"
	 * @generated
	 */
	CatalogMetadata getMetadata();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The host name this is a catalog for.
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalog_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Resources</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.catalog.CatalogResource}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resources</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Resources</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalog_Resources()
	 * @model containment="true"
	 * @generated
	 */
	EList<CatalogResource> getResources();

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The catalog version. Used for testing whether a catalog is up to date.
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.cloudsmith.geppetto.catalog.CatalogPackage#getCatalog_Version()
	 * @model
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.catalog.Catalog#getMetadata <em>Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Metadata</em>' containment reference.
	 * @see #getMetadata()
	 * @generated
	 */
	void setMetadata(CatalogMetadata value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.catalog.Catalog#getName <em>Name</em>}' attribute.
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
	 * Sets the value of the '{@link org.cloudsmith.geppetto.catalog.Catalog#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // Catalog
