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
package com.puppetlabs.geppetto.catalog;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Taggable</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.puppetlabs.geppetto.catalog.Taggable#getTags <em>Tags</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.puppetlabs.geppetto.catalog.CatalogPackage#getTaggable()
 * @model abstract="true"
 * @generated
 */
public interface Taggable extends EObject {
	/**
	 * Returns the value of the '<em><b>Tags</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tags</em>' attribute list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Tags</em>' attribute list.
	 * @see com.puppetlabs.geppetto.catalog.CatalogPackage#getTaggable_Tags()
	 * @model
	 * @generated
	 */
	EList<String> getTags();

} // Taggable
