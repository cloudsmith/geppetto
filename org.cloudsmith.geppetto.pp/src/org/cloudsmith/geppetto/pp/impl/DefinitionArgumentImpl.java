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

import org.cloudsmith.geppetto.pp.DefinitionArgument;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Definition Argument</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.DefinitionArgumentImpl#getArgName <em>Arg Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.DefinitionArgumentImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class DefinitionArgumentImpl extends EObjectImpl implements DefinitionArgument {
	/**
	 * The default value of the '{@link #getArgName() <em>Arg Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getArgName()
	 * @generated
	 * @ordered
	 */
	protected static final String ARG_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getArgName() <em>Arg Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getArgName()
	 * @generated
	 * @ordered
	 */
	protected String argName = ARG_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected Expression value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected DefinitionArgumentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetValue(Expression newValue, NotificationChain msgs) {
		Expression oldValue = value;
		value = newValue;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.DEFINITION_ARGUMENT__VALUE, oldValue, newValue);
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
			case PPPackage.DEFINITION_ARGUMENT__ARG_NAME:
				return getArgName();
			case PPPackage.DEFINITION_ARGUMENT__VALUE:
				return getValue();
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
			case PPPackage.DEFINITION_ARGUMENT__VALUE:
				return basicSetValue(null, msgs);
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
			case PPPackage.DEFINITION_ARGUMENT__ARG_NAME:
				return ARG_NAME_EDEFAULT == null
						? argName != null
						: !ARG_NAME_EDEFAULT.equals(argName);
			case PPPackage.DEFINITION_ARGUMENT__VALUE:
				return value != null;
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
			case PPPackage.DEFINITION_ARGUMENT__ARG_NAME:
				setArgName((String) newValue);
				return;
			case PPPackage.DEFINITION_ARGUMENT__VALUE:
				setValue((Expression) newValue);
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
			case PPPackage.DEFINITION_ARGUMENT__ARG_NAME:
				setArgName(ARG_NAME_EDEFAULT);
				return;
			case PPPackage.DEFINITION_ARGUMENT__VALUE:
				setValue((Expression) null);
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
	public String getArgName() {
		return argName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setArgName(String newArgName) {
		String oldArgName = argName;
		argName = newArgName;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.DEFINITION_ARGUMENT__ARG_NAME, oldArgName, argName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setValue(Expression newValue) {
		if(newValue != value) {
			NotificationChain msgs = null;
			if(value != null)
				msgs = ((InternalEObject) value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.DEFINITION_ARGUMENT__VALUE, null, msgs);
			if(newValue != null)
				msgs = ((InternalEObject) newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.DEFINITION_ARGUMENT__VALUE, null, msgs);
			msgs = basicSetValue(newValue, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.DEFINITION_ARGUMENT__VALUE, newValue, newValue));
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
		result.append(" (argName: ");
		result.append(argName);
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
		return PPPackage.Literals.DEFINITION_ARGUMENT;
	}

} // DefinitionArgumentImpl
