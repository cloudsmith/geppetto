/**
 * Copyright (c) 2011, 2012 Cloudsmith Inc. and other contributors, as listed below.
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
 * A representation of the model object '<em><b>Unless Expression</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.UnlessExpression#getCondExpr <em>Cond Expr</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.UnlessExpression#getThenStatements <em>Then Statements</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage#getUnlessExpression()
 * @model
 * @generated
 */
public interface UnlessExpression extends Expression {
	/**
	 * Returns the value of the '<em><b>Cond Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cond Expr</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Cond Expr</em>' containment reference.
	 * @see #setCondExpr(Expression)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getUnlessExpression_CondExpr()
	 * @model containment="true"
	 * @generated
	 */
	Expression getCondExpr();

	/**
	 * Returns the value of the '<em><b>Then Statements</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.pp.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Then Statements</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Then Statements</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getUnlessExpression_ThenStatements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getThenStatements();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.UnlessExpression#getCondExpr <em>Cond Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Cond Expr</em>' containment reference.
	 * @see #getCondExpr()
	 * @generated
	 */
	void setCondExpr(Expression value);

} // UnlessExpression
