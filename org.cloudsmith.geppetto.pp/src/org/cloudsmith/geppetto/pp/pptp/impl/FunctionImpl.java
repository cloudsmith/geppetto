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
package org.cloudsmith.geppetto.pp.pptp.impl;

import org.cloudsmith.geppetto.pp.pptp.Function;
import org.cloudsmith.geppetto.pp.pptp.INamed;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.cloudsmith.geppetto.pp.pptp.TargetElement;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.cloudsmith.geppetto.pp.pptp.impl.FunctionImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.pp.pptp.impl.FunctionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.pp.pptp.impl.FunctionImpl#isRValue <em>RValue</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FunctionImpl extends EObjectImpl implements Function {
	/**
	 * The default value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected static final String DOCUMENTATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
	protected String documentation = DOCUMENTATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isRValue() <em>RValue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRValue()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RVALUE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRValue() <em>RValue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRValue()
	 * @generated
	 * @ordered
	 */
	protected boolean rValue = RVALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PPTPPackage.Literals.FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDocumentation() {
		return documentation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDocumentation(String newDocumentation) {
		String oldDocumentation = documentation;
		documentation = newDocumentation;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPTPPackage.FUNCTION__DOCUMENTATION, oldDocumentation, documentation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PPTPPackage.FUNCTION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRValue() {
		return rValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRValue(boolean newRValue) {
		boolean oldRValue = rValue;
		rValue = newRValue;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PPTPPackage.FUNCTION__RVALUE, oldRValue, rValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch(featureID) {
			case PPTPPackage.FUNCTION__DOCUMENTATION:
				return getDocumentation();
			case PPTPPackage.FUNCTION__NAME:
				return getName();
			case PPTPPackage.FUNCTION__RVALUE:
				return isRValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch(featureID) {
			case PPTPPackage.FUNCTION__DOCUMENTATION:
				setDocumentation((String) newValue);
				return;
			case PPTPPackage.FUNCTION__NAME:
				setName((String) newValue);
				return;
			case PPTPPackage.FUNCTION__RVALUE:
				setRValue((Boolean) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch(featureID) {
			case PPTPPackage.FUNCTION__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case PPTPPackage.FUNCTION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PPTPPackage.FUNCTION__RVALUE:
				setRValue(RVALUE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch(featureID) {
			case PPTPPackage.FUNCTION__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null
						? documentation != null
						: !DOCUMENTATION_EDEFAULT.equals(documentation);
			case PPTPPackage.FUNCTION__NAME:
				return NAME_EDEFAULT == null
						? name != null
						: !NAME_EDEFAULT.equals(name);
			case PPTPPackage.FUNCTION__RVALUE:
				return rValue != RVALUE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if(baseClass == INamed.class) {
			switch(derivedFeatureID) {
				case PPTPPackage.FUNCTION__NAME:
					return PPTPPackage.INAMED__NAME;
				default:
					return -1;
			}
		}
		if(baseClass == TargetElement.class) {
			switch(derivedFeatureID) {
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if(baseClass == INamed.class) {
			switch(baseFeatureID) {
				case PPTPPackage.INAMED__NAME:
					return PPTPPackage.FUNCTION__NAME;
				default:
					return -1;
			}
		}
		if(baseClass == TargetElement.class) {
			switch(baseFeatureID) {
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if(eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (documentation: ");
		result.append(documentation);
		result.append(", name: ");
		result.append(name);
		result.append(", rValue: ");
		result.append(rValue);
		result.append(')');
		return result.toString();
	}

} //FunctionImpl
