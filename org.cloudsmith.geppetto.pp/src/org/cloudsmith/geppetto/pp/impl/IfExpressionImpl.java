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
package org.cloudsmith.geppetto.pp.impl;

import java.util.Collection;

import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.IfExpression;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>If Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.IfExpressionImpl#getCondExpr <em>Cond Expr</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.IfExpressionImpl#getThenStatements <em>Then Statements</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.IfExpressionImpl#getElseStatement <em>Else Statement</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class IfExpressionImpl extends ExpressionImpl implements IfExpression {
	/**
	 * The cached value of the '{@link #getCondExpr() <em>Cond Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getCondExpr()
	 * @generated
	 * @ordered
	 */
	protected Expression condExpr;

	/**
	 * The cached value of the '{@link #getThenStatements() <em>Then Statements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getThenStatements()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> thenStatements;

	/**
	 * The cached value of the '{@link #getElseStatement() <em>Else Statement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getElseStatement()
	 * @generated
	 * @ordered
	 */
	protected Expression elseStatement;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected IfExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetCondExpr(Expression newCondExpr, NotificationChain msgs) {
		Expression oldCondExpr = condExpr;
		condExpr = newCondExpr;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.IF_EXPRESSION__COND_EXPR, oldCondExpr, newCondExpr);
			if(msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetElseStatement(Expression newElseStatement, NotificationChain msgs) {
		Expression oldElseStatement = elseStatement;
		elseStatement = newElseStatement;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.IF_EXPRESSION__ELSE_STATEMENT, oldElseStatement, newElseStatement);
			if(msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch(featureID) {
			case PPPackage.IF_EXPRESSION__COND_EXPR:
				return getCondExpr();
			case PPPackage.IF_EXPRESSION__THEN_STATEMENTS:
				return getThenStatements();
			case PPPackage.IF_EXPRESSION__ELSE_STATEMENT:
				return getElseStatement();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch(featureID) {
			case PPPackage.IF_EXPRESSION__COND_EXPR:
				return basicSetCondExpr(null, msgs);
			case PPPackage.IF_EXPRESSION__THEN_STATEMENTS:
				return ((InternalEList<?>) getThenStatements()).basicRemove(otherEnd, msgs);
			case PPPackage.IF_EXPRESSION__ELSE_STATEMENT:
				return basicSetElseStatement(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch(featureID) {
			case PPPackage.IF_EXPRESSION__COND_EXPR:
				return condExpr != null;
			case PPPackage.IF_EXPRESSION__THEN_STATEMENTS:
				return thenStatements != null && !thenStatements.isEmpty();
			case PPPackage.IF_EXPRESSION__ELSE_STATEMENT:
				return elseStatement != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch(featureID) {
			case PPPackage.IF_EXPRESSION__COND_EXPR:
				setCondExpr((Expression) newValue);
				return;
			case PPPackage.IF_EXPRESSION__THEN_STATEMENTS:
				getThenStatements().clear();
				getThenStatements().addAll((Collection<? extends Expression>) newValue);
				return;
			case PPPackage.IF_EXPRESSION__ELSE_STATEMENT:
				setElseStatement((Expression) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch(featureID) {
			case PPPackage.IF_EXPRESSION__COND_EXPR:
				setCondExpr((Expression) null);
				return;
			case PPPackage.IF_EXPRESSION__THEN_STATEMENTS:
				getThenStatements().clear();
				return;
			case PPPackage.IF_EXPRESSION__ELSE_STATEMENT:
				setElseStatement((Expression) null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression getCondExpr() {
		return condExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression getElseStatement() {
		return elseStatement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Expression> getThenStatements() {
		if(thenStatements == null) {
			thenStatements = new EObjectContainmentEList<Expression>(
				Expression.class, this, PPPackage.IF_EXPRESSION__THEN_STATEMENTS);
		}
		return thenStatements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setCondExpr(Expression newCondExpr) {
		if(newCondExpr != condExpr) {
			NotificationChain msgs = null;
			if(condExpr != null)
				msgs = ((InternalEObject) condExpr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.IF_EXPRESSION__COND_EXPR, null, msgs);
			if(newCondExpr != null)
				msgs = ((InternalEObject) newCondExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.IF_EXPRESSION__COND_EXPR, null, msgs);
			msgs = basicSetCondExpr(newCondExpr, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.IF_EXPRESSION__COND_EXPR, newCondExpr, newCondExpr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setElseStatement(Expression newElseStatement) {
		if(newElseStatement != elseStatement) {
			NotificationChain msgs = null;
			if(elseStatement != null)
				msgs = ((InternalEObject) elseStatement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.IF_EXPRESSION__ELSE_STATEMENT, null, msgs);
			if(newElseStatement != null)
				msgs = ((InternalEObject) newElseStatement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.IF_EXPRESSION__ELSE_STATEMENT, null, msgs);
			msgs = basicSetElseStatement(newElseStatement, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.IF_EXPRESSION__ELSE_STATEMENT, newElseStatement, newElseStatement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PPPackage.Literals.IF_EXPRESSION;
	}

} // IfExpressionImpl
