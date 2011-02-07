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
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.ParameterizedExpression;
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
 * An implementation of the model object '<em><b>Parameterized Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.ParameterizedExpressionImpl#getLeftExpr <em>Left Expr</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.ParameterizedExpressionImpl#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class ParameterizedExpressionImpl extends ExpressionImpl implements ParameterizedExpression {
	/**
	 * The cached value of the '{@link #getLeftExpr() <em>Left Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLeftExpr()
	 * @generated
	 * @ordered
	 */
	protected Expression leftExpr;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> parameters;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ParameterizedExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetLeftExpr(Expression newLeftExpr, NotificationChain msgs) {
		Expression oldLeftExpr = leftExpr;
		leftExpr = newLeftExpr;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.PARAMETERIZED_EXPRESSION__LEFT_EXPR, oldLeftExpr, newLeftExpr);
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
			case PPPackage.PARAMETERIZED_EXPRESSION__LEFT_EXPR:
				return getLeftExpr();
			case PPPackage.PARAMETERIZED_EXPRESSION__PARAMETERS:
				return getParameters();
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
			case PPPackage.PARAMETERIZED_EXPRESSION__LEFT_EXPR:
				return basicSetLeftExpr(null, msgs);
			case PPPackage.PARAMETERIZED_EXPRESSION__PARAMETERS:
				return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
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
			case PPPackage.PARAMETERIZED_EXPRESSION__LEFT_EXPR:
				return leftExpr != null;
			case PPPackage.PARAMETERIZED_EXPRESSION__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
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
			case PPPackage.PARAMETERIZED_EXPRESSION__LEFT_EXPR:
				setLeftExpr((Expression) newValue);
				return;
			case PPPackage.PARAMETERIZED_EXPRESSION__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Expression>) newValue);
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
	protected EClass eStaticClass() {
		return PPPackage.Literals.PARAMETERIZED_EXPRESSION;
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
			case PPPackage.PARAMETERIZED_EXPRESSION__LEFT_EXPR:
				setLeftExpr((Expression) null);
				return;
			case PPPackage.PARAMETERIZED_EXPRESSION__PARAMETERS:
				getParameters().clear();
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
	public Expression getLeftExpr() {
		return leftExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Expression> getParameters() {
		if(parameters == null) {
			parameters = new EObjectContainmentEList<Expression>(
				Expression.class, this, PPPackage.PARAMETERIZED_EXPRESSION__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLeftExpr(Expression newLeftExpr) {
		if(newLeftExpr != leftExpr) {
			NotificationChain msgs = null;
			if(leftExpr != null)
				msgs = ((InternalEObject) leftExpr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.PARAMETERIZED_EXPRESSION__LEFT_EXPR, null, msgs);
			if(newLeftExpr != null)
				msgs = ((InternalEObject) newLeftExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.PARAMETERIZED_EXPRESSION__LEFT_EXPR, null, msgs);
			msgs = basicSetLeftExpr(newLeftExpr, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.PARAMETERIZED_EXPRESSION__LEFT_EXPR, newLeftExpr, newLeftExpr));
	}

} // ParameterizedExpressionImpl
