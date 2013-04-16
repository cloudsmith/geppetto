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
package org.cloudsmith.geppetto.pp.impl;

import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.MethodCall;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Method Call</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.MethodCallImpl#isParenthesized <em>Parenthesized</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.MethodCallImpl#getMethodExpr <em>Method Expr</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class MethodCallImpl extends WithLambdaExpressionImpl implements MethodCall {
	/**
	 * The default value of the '{@link #isParenthesized() <em>Parenthesized</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isParenthesized()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PARENTHESIZED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isParenthesized() <em>Parenthesized</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isParenthesized()
	 * @generated
	 * @ordered
	 */
	protected boolean parenthesized = PARENTHESIZED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMethodExpr() <em>Method Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getMethodExpr()
	 * @generated
	 * @ordered
	 */
	protected Expression methodExpr;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MethodCallImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetMethodExpr(Expression newMethodExpr, NotificationChain msgs) {
		Expression oldMethodExpr = methodExpr;
		methodExpr = newMethodExpr;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.METHOD_CALL__METHOD_EXPR, oldMethodExpr, newMethodExpr);
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
			case PPPackage.METHOD_CALL__PARENTHESIZED:
				return isParenthesized();
			case PPPackage.METHOD_CALL__METHOD_EXPR:
				return getMethodExpr();
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
			case PPPackage.METHOD_CALL__METHOD_EXPR:
				return basicSetMethodExpr(null, msgs);
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
			case PPPackage.METHOD_CALL__PARENTHESIZED:
				return parenthesized != PARENTHESIZED_EDEFAULT;
			case PPPackage.METHOD_CALL__METHOD_EXPR:
				return methodExpr != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch(featureID) {
			case PPPackage.METHOD_CALL__PARENTHESIZED:
				setParenthesized((Boolean) newValue);
				return;
			case PPPackage.METHOD_CALL__METHOD_EXPR:
				setMethodExpr((Expression) newValue);
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
		return PPPackage.Literals.METHOD_CALL;
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
			case PPPackage.METHOD_CALL__PARENTHESIZED:
				setParenthesized(PARENTHESIZED_EDEFAULT);
				return;
			case PPPackage.METHOD_CALL__METHOD_EXPR:
				setMethodExpr((Expression) null);
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
	public Expression getMethodExpr() {
		return methodExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isParenthesized() {
		return parenthesized;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setMethodExpr(Expression newMethodExpr) {
		if(newMethodExpr != methodExpr) {
			NotificationChain msgs = null;
			if(methodExpr != null)
				msgs = ((InternalEObject) methodExpr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.METHOD_CALL__METHOD_EXPR, null, msgs);
			if(newMethodExpr != null)
				msgs = ((InternalEObject) newMethodExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.METHOD_CALL__METHOD_EXPR, null, msgs);
			msgs = basicSetMethodExpr(newMethodExpr, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.METHOD_CALL__METHOD_EXPR, newMethodExpr, newMethodExpr));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setParenthesized(boolean newParenthesized) {
		boolean oldParenthesized = parenthesized;
		parenthesized = newParenthesized;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.METHOD_CALL__PARENTHESIZED, oldParenthesized, parenthesized));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if(eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (parenthesized: ");
		result.append(parenthesized);
		result.append(')');
		return result.toString();
	}

} // MethodCallImpl
