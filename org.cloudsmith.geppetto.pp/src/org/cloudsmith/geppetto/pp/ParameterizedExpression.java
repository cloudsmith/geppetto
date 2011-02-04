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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameterized Expression</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.ParameterizedExpression#getLeftExpr <em>Left Expr</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.ParameterizedExpression#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage#getParameterizedExpression()
 * @model abstract="true"
 * @generated
 */
public interface ParameterizedExpression extends Expression {
	/**
	 * Returns the value of the '<em><b>Left Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Left Expr</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Left Expr</em>' containment reference.
	 * @see #setLeftExpr(Expression)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getParameterizedExpression_LeftExpr()
	 * @model containment="true"
	 * @generated
	 */
	Expression getLeftExpr();

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.pp.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getParameterizedExpression_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getParameters();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.ParameterizedExpression#getLeftExpr <em>Left Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Left Expr</em>' containment reference.
	 * @see #getLeftExpr()
	 * @generated
	 */
	void setLeftExpr(Expression value);

} // ParameterizedExpression
