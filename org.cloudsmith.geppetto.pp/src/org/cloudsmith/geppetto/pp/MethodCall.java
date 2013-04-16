/**
 * Copyright (c) 2011, 2013 Cloudsmith Inc. and other contributors, as listed below.
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
 * A representation of the model object '<em><b>Method Call</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.MethodCall#isParenthesized <em>Parenthesized</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.MethodCall#getMethodExpr <em>Method Expr</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage#getMethodCall()
 * @model
 * @generated
 */
public interface MethodCall extends WithLambdaExpression {

	/**
	 * Returns the value of the '<em><b>Method Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method Expr</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Method Expr</em>' containment reference.
	 * @see #setMethodExpr(Expression)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getMethodCall_MethodExpr()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expression getMethodExpr();

	/**
	 * Returns the value of the '<em><b>Parenthesized</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parenthesized</em>' attribute isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parenthesized</em>' attribute.
	 * @see #setParenthesized(boolean)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getMethodCall_Parenthesized()
	 * @model
	 * @generated
	 */
	boolean isParenthesized();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.MethodCall#getMethodExpr <em>Method Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Method Expr</em>' containment reference.
	 * @see #getMethodExpr()
	 * @generated
	 */
	void setMethodExpr(Expression value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.MethodCall#isParenthesized <em>Parenthesized</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Parenthesized</em>' attribute.
	 * @see #isParenthesized()
	 * @generated
	 */
	void setParenthesized(boolean value);
} // MethodCall
