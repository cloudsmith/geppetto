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
package org.cloudsmith.geppetto.pp;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Manifest</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.PuppetManifest#getLeadingSpaceAndComments <em>Leading Space And Comments</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage#getPuppetManifest()
 * @model
 * @generated
 */
public interface PuppetManifest extends ExpressionBlock {

	/**
	 * Returns the value of the '<em><b>Leading Space And Comments</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Leading Space And Comments</em>' containment reference isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Leading Space And Comments</em>' containment reference.
	 * @see #setLeadingSpaceAndComments(OWS)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getPuppetManifest_LeadingSpaceAndComments()
	 * @model containment="true"
	 * @generated
	 */
	OWS getLeadingSpaceAndComments();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.PuppetManifest#getLeadingSpaceAndComments <em>Leading Space And Comments</em>}'
	 * containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Leading Space And Comments</em>' containment reference.
	 * @see #getLeadingSpaceAndComments()
	 * @generated
	 */
	void setLeadingSpaceAndComments(OWS value);
} // PuppetManifest
