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

import org.cloudsmith.geppetto.pp.DoubleQuotedString;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.TextExpression;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Double Quoted String</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.DoubleQuotedStringImpl#getTextExpression <em>Text Expression</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class DoubleQuotedStringImpl extends StringExpressionImpl implements DoubleQuotedString {
	/**
	 * The cached value of the '{@link #getTextExpression() <em>Text Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTextExpression()
	 * @generated
	 * @ordered
	 */
	protected TextExpression textExpression;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected DoubleQuotedStringImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetTextExpression(TextExpression newTextExpression, NotificationChain msgs) {
		TextExpression oldTextExpression = textExpression;
		textExpression = newTextExpression;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.DOUBLE_QUOTED_STRING__TEXT_EXPRESSION, oldTextExpression,
				newTextExpression);
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
			case PPPackage.DOUBLE_QUOTED_STRING__TEXT_EXPRESSION:
				return getTextExpression();
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
			case PPPackage.DOUBLE_QUOTED_STRING__TEXT_EXPRESSION:
				return basicSetTextExpression(null, msgs);
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
			case PPPackage.DOUBLE_QUOTED_STRING__TEXT_EXPRESSION:
				return textExpression != null;
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
			case PPPackage.DOUBLE_QUOTED_STRING__TEXT_EXPRESSION:
				setTextExpression((TextExpression) newValue);
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
		return PPPackage.Literals.DOUBLE_QUOTED_STRING;
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
			case PPPackage.DOUBLE_QUOTED_STRING__TEXT_EXPRESSION:
				setTextExpression((TextExpression) null);
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
	public TextExpression getTextExpression() {
		return textExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTextExpression(TextExpression newTextExpression) {
		if(newTextExpression != textExpression) {
			NotificationChain msgs = null;
			if(textExpression != null)
				msgs = ((InternalEObject) textExpression).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.DOUBLE_QUOTED_STRING__TEXT_EXPRESSION, null, msgs);
			if(newTextExpression != null)
				msgs = ((InternalEObject) newTextExpression).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.DOUBLE_QUOTED_STRING__TEXT_EXPRESSION, null, msgs);
			msgs = basicSetTextExpression(newTextExpression, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.DOUBLE_QUOTED_STRING__TEXT_EXPRESSION, newTextExpression,
				newTextExpression));
	}

} // DoubleQuotedStringImpl
