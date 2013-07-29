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
package org.cloudsmith.geppetto.pp;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Case Expression</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.CaseExpression#getSwitchExpr <em>Switch Expr</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.CaseExpression#getCases <em>Cases</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage#getCaseExpression()
 * @model
 * @generated
 */
public interface CaseExpression extends Expression {
	/**
	 * Returns the value of the '<em><b>Cases</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.pp.Case}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cases</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Cases</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getCaseExpression_Cases()
	 * @model containment="true"
	 * @generated
	 */
	EList<Case> getCases();

	/**
	 * Returns the value of the '<em><b>Switch Expr</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Switch Expr</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Switch Expr</em>' containment reference.
	 * @see #setSwitchExpr(Expression)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getCaseExpression_SwitchExpr()
	 * @model containment="true"
	 * @generated
	 */
	Expression getSwitchExpr();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.CaseExpression#getSwitchExpr <em>Switch Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Switch Expr</em>' containment reference.
	 * @see #getSwitchExpr()
	 * @generated
	 */
	void setSwitchExpr(Expression value);

} // CaseExpression
