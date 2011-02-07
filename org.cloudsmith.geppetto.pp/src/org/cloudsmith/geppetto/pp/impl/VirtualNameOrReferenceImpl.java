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
import org.cloudsmith.geppetto.pp.VirtualNameOrReference;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Virtual Name Or Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.VirtualNameOrReferenceImpl#getValue <em>Value</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.VirtualNameOrReferenceImpl#isExported <em>Exported</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class VirtualNameOrReferenceImpl extends LiteralExpressionImpl implements VirtualNameOrReference {
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #isExported() <em>Exported</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isExported()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXPORTED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExported() <em>Exported</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isExported()
	 * @generated
	 * @ordered
	 */
	protected boolean exported = EXPORTED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected VirtualNameOrReferenceImpl() {
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
			case PPPackage.VIRTUAL_NAME_OR_REFERENCE__VALUE:
				return getValue();
			case PPPackage.VIRTUAL_NAME_OR_REFERENCE__EXPORTED:
				return isExported();
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
			case PPPackage.VIRTUAL_NAME_OR_REFERENCE__VALUE:
				return VALUE_EDEFAULT == null
						? value != null
						: !VALUE_EDEFAULT.equals(value);
			case PPPackage.VIRTUAL_NAME_OR_REFERENCE__EXPORTED:
				return exported != EXPORTED_EDEFAULT;
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
			case PPPackage.VIRTUAL_NAME_OR_REFERENCE__VALUE:
				setValue((String) newValue);
				return;
			case PPPackage.VIRTUAL_NAME_OR_REFERENCE__EXPORTED:
				setExported((Boolean) newValue);
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
		return PPPackage.Literals.VIRTUAL_NAME_OR_REFERENCE;
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
			case PPPackage.VIRTUAL_NAME_OR_REFERENCE__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case PPPackage.VIRTUAL_NAME_OR_REFERENCE__EXPORTED:
				setExported(EXPORTED_EDEFAULT);
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
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isExported() {
		return exported;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setExported(boolean newExported) {
		boolean oldExported = exported;
		exported = newExported;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.VIRTUAL_NAME_OR_REFERENCE__EXPORTED, oldExported, exported));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.VIRTUAL_NAME_OR_REFERENCE__VALUE, oldValue, value));
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
		result.append(" (value: ");
		result.append(value);
		result.append(", exported: ");
		result.append(exported);
		result.append(')');
		return result.toString();
	}

} // VirtualNameOrReferenceImpl
