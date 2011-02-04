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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Body</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.ResourceBody#getAttributes <em>Attributes</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.ResourceBody#getNameExpr <em>Name Expr</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage#getResourceBody()
 * @model
 * @generated
 */
public interface ResourceBody extends EObject {
	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Attributes</em>' containment reference.
	 * @see #setAttributes(AttributeOperations)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getResourceBody_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	AttributeOperations getAttributes();

	/**
	 * Returns the value of the '<em><b>Name Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name Expr</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name Expr</em>' containment reference.
	 * @see #setNameExpr(Expression)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getResourceBody_NameExpr()
	 * @model containment="true"
	 * @generated
	 */
	Expression getNameExpr();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.ResourceBody#getAttributes <em>Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Attributes</em>' containment reference.
	 * @see #getAttributes()
	 * @generated
	 */
	void setAttributes(AttributeOperations value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.ResourceBody#getNameExpr <em>Name Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name Expr</em>' containment reference.
	 * @see #getNameExpr()
	 * @generated
	 */
	void setNameExpr(Expression value);

} // ResourceBody
