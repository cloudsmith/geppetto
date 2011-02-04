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

import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.TextExpression;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Text Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.TextExpressionImpl#getTrailing <em>Trailing</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.TextExpressionImpl#getLeading <em>Leading</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class TextExpressionImpl extends EObjectImpl implements TextExpression {
	/**
	 * The cached value of the '{@link #getTrailing() <em>Trailing</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTrailing()
	 * @generated
	 * @ordered
	 */
	protected TextExpression trailing;

	/**
	 * The cached value of the '{@link #getLeading() <em>Leading</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLeading()
	 * @generated
	 * @ordered
	 */
	protected TextExpression leading;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TextExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetLeading(TextExpression newLeading, NotificationChain msgs) {
		TextExpression oldLeading = leading;
		leading = newLeading;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.TEXT_EXPRESSION__LEADING, oldLeading, newLeading);
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
	public NotificationChain basicSetTrailing(TextExpression newTrailing, NotificationChain msgs) {
		TextExpression oldTrailing = trailing;
		trailing = newTrailing;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.TEXT_EXPRESSION__TRAILING, oldTrailing, newTrailing);
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
			case PPPackage.TEXT_EXPRESSION__TRAILING:
				return getTrailing();
			case PPPackage.TEXT_EXPRESSION__LEADING:
				return getLeading();
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
			case PPPackage.TEXT_EXPRESSION__TRAILING:
				return basicSetTrailing(null, msgs);
			case PPPackage.TEXT_EXPRESSION__LEADING:
				return basicSetLeading(null, msgs);
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
			case PPPackage.TEXT_EXPRESSION__TRAILING:
				return trailing != null;
			case PPPackage.TEXT_EXPRESSION__LEADING:
				return leading != null;
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
			case PPPackage.TEXT_EXPRESSION__TRAILING:
				setTrailing((TextExpression) newValue);
				return;
			case PPPackage.TEXT_EXPRESSION__LEADING:
				setLeading((TextExpression) newValue);
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
			case PPPackage.TEXT_EXPRESSION__TRAILING:
				setTrailing((TextExpression) null);
				return;
			case PPPackage.TEXT_EXPRESSION__LEADING:
				setLeading((TextExpression) null);
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
	public TextExpression getLeading() {
		return leading;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TextExpression getTrailing() {
		return trailing;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLeading(TextExpression newLeading) {
		if(newLeading != leading) {
			NotificationChain msgs = null;
			if(leading != null)
				msgs = ((InternalEObject) leading).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.TEXT_EXPRESSION__LEADING, null, msgs);
			if(newLeading != null)
				msgs = ((InternalEObject) newLeading).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.TEXT_EXPRESSION__LEADING, null, msgs);
			msgs = basicSetLeading(newLeading, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.TEXT_EXPRESSION__LEADING, newLeading, newLeading));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTrailing(TextExpression newTrailing) {
		if(newTrailing != trailing) {
			NotificationChain msgs = null;
			if(trailing != null)
				msgs = ((InternalEObject) trailing).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.TEXT_EXPRESSION__TRAILING, null, msgs);
			if(newTrailing != null)
				msgs = ((InternalEObject) newTrailing).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.TEXT_EXPRESSION__TRAILING, null, msgs);
			msgs = basicSetTrailing(newTrailing, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.TEXT_EXPRESSION__TRAILING, newTrailing, newTrailing));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PPPackage.Literals.TEXT_EXPRESSION;
	}

} // TextExpressionImpl
