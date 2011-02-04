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
 * A representation of the model object '<em><b>Binary Expression</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.BinaryExpression#getLeftExpr <em>Left Expr</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.BinaryExpression#getRightExpr <em>Right Expr</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage#getBinaryExpression()
 * @model abstract="true"
 * @generated
 */
public interface BinaryExpression extends Expression {
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
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getBinaryExpression_LeftExpr()
	 * @model containment="true"
	 * @generated
	 */
	Expression getLeftExpr();

	/**
	 * Returns the value of the '<em><b>Right Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Right Expr</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Right Expr</em>' containment reference.
	 * @see #setRightExpr(Expression)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getBinaryExpression_RightExpr()
	 * @model containment="true"
	 * @generated
	 */
	Expression getRightExpr();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.BinaryExpression#getLeftExpr <em>Left Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Left Expr</em>' containment reference.
	 * @see #getLeftExpr()
	 * @generated
	 */
	void setLeftExpr(Expression value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.BinaryExpression#getRightExpr <em>Right Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Right Expr</em>' containment reference.
	 * @see #getRightExpr()
	 * @generated
	 */
	void setRightExpr(Expression value);

} // BinaryExpression
