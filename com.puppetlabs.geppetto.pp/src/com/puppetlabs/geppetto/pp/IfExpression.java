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
package com.puppetlabs.geppetto.pp;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>If Expression</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.puppetlabs.geppetto.pp.IfExpression#getCondExpr <em>Cond Expr</em>}</li>
 * <li>{@link com.puppetlabs.geppetto.pp.IfExpression#getThenStatements <em>Then Statements</em>}</li>
 * <li>{@link com.puppetlabs.geppetto.pp.IfExpression#getElseStatement <em>Else Statement</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.puppetlabs.geppetto.pp.PPPackage#getIfExpression()
 * @model
 * @generated
 */
public interface IfExpression extends Expression {
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
	 * @see com.puppetlabs.geppetto.pp.PPPackage#getIfExpression_CondExpr()
	 * @model containment="true"
	 * @generated
	 */
	Expression getCondExpr();

	/**
	 * Returns the value of the '<em><b>Else Statement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Else Statement</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Else Statement</em>' containment reference.
	 * @see #setElseStatement(Expression)
	 * @see com.puppetlabs.geppetto.pp.PPPackage#getIfExpression_ElseStatement()
	 * @model containment="true"
	 * @generated
	 */
	Expression getElseStatement();

	/**
	 * Returns the value of the '<em><b>Then Statements</b></em>' containment reference list.
	 * The list contents are of type {@link com.puppetlabs.geppetto.pp.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Then Statements</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Then Statements</em>' containment reference list.
	 * @see com.puppetlabs.geppetto.pp.PPPackage#getIfExpression_ThenStatements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getThenStatements();

	/**
	 * Sets the value of the '{@link com.puppetlabs.geppetto.pp.IfExpression#getCondExpr <em>Cond Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Cond Expr</em>' containment reference.
	 * @see #getCondExpr()
	 * @generated
	 */
	void setCondExpr(Expression value);

	/**
	 * Sets the value of the '{@link com.puppetlabs.geppetto.pp.IfExpression#getElseStatement <em>Else Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Else Statement</em>' containment reference.
	 * @see #getElseStatement()
	 * @generated
	 */
	void setElseStatement(Expression value);

} // IfExpression
