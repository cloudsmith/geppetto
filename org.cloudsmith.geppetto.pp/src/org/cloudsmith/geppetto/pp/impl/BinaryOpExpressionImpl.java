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

import org.cloudsmith.geppetto.pp.BinaryOpExpression;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Binary Op Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.BinaryOpExpressionImpl#getOpName <em>Op Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class BinaryOpExpressionImpl extends BinaryExpressionImpl implements BinaryOpExpression {
	/**
	 * The default value of the '{@link #getOpName() <em>Op Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getOpName()
	 * @generated
	 * @ordered
	 */
	protected static final String OP_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOpName() <em>Op Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getOpName()
	 * @generated
	 * @ordered
	 */
	protected String opName = OP_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected BinaryOpExpressionImpl() {
		super();
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
			case PPPackage.BINARY_OP_EXPRESSION__OP_NAME:
				return getOpName();
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
	public boolean eIsSet(int featureID) {
		switch(featureID) {
			case PPPackage.BINARY_OP_EXPRESSION__OP_NAME:
				return OP_NAME_EDEFAULT == null
						? opName != null
						: !OP_NAME_EDEFAULT.equals(opName);
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
			case PPPackage.BINARY_OP_EXPRESSION__OP_NAME:
				setOpName((String) newValue);
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
			case PPPackage.BINARY_OP_EXPRESSION__OP_NAME:
				setOpName(OP_NAME_EDEFAULT);
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
	public String getOpName() {
		return opName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setOpName(String newOpName) {
		String oldOpName = opName;
		opName = newOpName;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.BINARY_OP_EXPRESSION__OP_NAME, oldOpName, opName));
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
		result.append(" (opName: ");
		result.append(opName);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PPPackage.Literals.BINARY_OP_EXPRESSION;
	}

} // BinaryOpExpressionImpl
