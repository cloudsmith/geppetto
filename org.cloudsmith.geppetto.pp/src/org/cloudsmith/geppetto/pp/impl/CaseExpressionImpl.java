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

import org.cloudsmith.geppetto.pp.Case;
import org.cloudsmith.geppetto.pp.CaseExpression;
import org.cloudsmith.geppetto.pp.Expression;
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
 * An implementation of the model object '<em><b>Case Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.CaseExpressionImpl#getSwitchExpr <em>Switch Expr</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.CaseExpressionImpl#getCases <em>Cases</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CaseExpressionImpl extends ExpressionImpl implements CaseExpression {
	/**
	 * The cached value of the '{@link #getSwitchExpr() <em>Switch Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSwitchExpr()
	 * @generated
	 * @ordered
	 */
	protected Expression switchExpr;

	/**
	 * The cached value of the '{@link #getCases() <em>Cases</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getCases()
	 * @generated
	 * @ordered
	 */
	protected EList<Case> cases;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CaseExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetSwitchExpr(Expression newSwitchExpr, NotificationChain msgs) {
		Expression oldSwitchExpr = switchExpr;
		switchExpr = newSwitchExpr;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.CASE_EXPRESSION__SWITCH_EXPR, oldSwitchExpr, newSwitchExpr);
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
			case PPPackage.CASE_EXPRESSION__SWITCH_EXPR:
				return getSwitchExpr();
			case PPPackage.CASE_EXPRESSION__CASES:
				return getCases();
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
			case PPPackage.CASE_EXPRESSION__SWITCH_EXPR:
				return basicSetSwitchExpr(null, msgs);
			case PPPackage.CASE_EXPRESSION__CASES:
				return ((InternalEList<?>) getCases()).basicRemove(otherEnd, msgs);
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
			case PPPackage.CASE_EXPRESSION__SWITCH_EXPR:
				return switchExpr != null;
			case PPPackage.CASE_EXPRESSION__CASES:
				return cases != null && !cases.isEmpty();
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
			case PPPackage.CASE_EXPRESSION__SWITCH_EXPR:
				setSwitchExpr((Expression) newValue);
				return;
			case PPPackage.CASE_EXPRESSION__CASES:
				getCases().clear();
				getCases().addAll((Collection<? extends Case>) newValue);
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
			case PPPackage.CASE_EXPRESSION__SWITCH_EXPR:
				setSwitchExpr((Expression) null);
				return;
			case PPPackage.CASE_EXPRESSION__CASES:
				getCases().clear();
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
	public EList<Case> getCases() {
		if(cases == null) {
			cases = new EObjectContainmentEList<Case>(Case.class, this, PPPackage.CASE_EXPRESSION__CASES);
		}
		return cases;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression getSwitchExpr() {
		return switchExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSwitchExpr(Expression newSwitchExpr) {
		if(newSwitchExpr != switchExpr) {
			NotificationChain msgs = null;
			if(switchExpr != null)
				msgs = ((InternalEObject) switchExpr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.CASE_EXPRESSION__SWITCH_EXPR, null, msgs);
			if(newSwitchExpr != null)
				msgs = ((InternalEObject) newSwitchExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.CASE_EXPRESSION__SWITCH_EXPR, null, msgs);
			msgs = basicSetSwitchExpr(newSwitchExpr, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.CASE_EXPRESSION__SWITCH_EXPR, newSwitchExpr, newSwitchExpr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PPPackage.Literals.CASE_EXPRESSION;
	}

} // CaseExpressionImpl
