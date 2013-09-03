/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.pp.pptp.impl;

import java.util.Collection;

import com.puppetlabs.geppetto.pp.pptp.ITargetElementContainer;
import com.puppetlabs.geppetto.pp.pptp.NameSpace;
import com.puppetlabs.geppetto.pp.pptp.PPTPPackage;
import com.puppetlabs.geppetto.pp.pptp.TargetElement;

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
 * An implementation of the model object '<em><b>Name Space</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link com.puppetlabs.geppetto.pp.pptp.impl.NameSpaceImpl#getContents <em>Contents</em>}</li>
 * <li>{@link com.puppetlabs.geppetto.pp.pptp.impl.NameSpaceImpl#isReserved <em>Reserved</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class NameSpaceImpl extends TargetElementImpl implements NameSpace {
	/**
	 * The cached value of the '{@link #getContents() <em>Contents</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getContents()
	 * @generated
	 * @ordered
	 */
	protected EList<TargetElement> contents;

	/**
	 * The default value of the '{@link #isReserved() <em>Reserved</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isReserved()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RESERVED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReserved() <em>Reserved</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isReserved()
	 * @generated
	 * @ordered
	 */
	protected boolean reserved = RESERVED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected NameSpaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if(baseClass == ITargetElementContainer.class) {
			switch(derivedFeatureID) {
				case PPTPPackage.NAME_SPACE__CONTENTS:
					return PPTPPackage.ITARGET_ELEMENT_CONTAINER__CONTENTS;
				default:
					return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if(baseClass == ITargetElementContainer.class) {
			switch(baseFeatureID) {
				case PPTPPackage.ITARGET_ELEMENT_CONTAINER__CONTENTS:
					return PPTPPackage.NAME_SPACE__CONTENTS;
				default:
					return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
			case PPTPPackage.NAME_SPACE__CONTENTS:
				return getContents();
			case PPTPPackage.NAME_SPACE__RESERVED:
				return isReserved();
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
			case PPTPPackage.NAME_SPACE__CONTENTS:
				return ((InternalEList<?>) getContents()).basicRemove(otherEnd, msgs);
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
			case PPTPPackage.NAME_SPACE__CONTENTS:
				return contents != null && !contents.isEmpty();
			case PPTPPackage.NAME_SPACE__RESERVED:
				return reserved != RESERVED_EDEFAULT;
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
			case PPTPPackage.NAME_SPACE__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends TargetElement>) newValue);
				return;
			case PPTPPackage.NAME_SPACE__RESERVED:
				setReserved((Boolean) newValue);
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
		return PPTPPackage.Literals.NAME_SPACE;
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
			case PPTPPackage.NAME_SPACE__CONTENTS:
				getContents().clear();
				return;
			case PPTPPackage.NAME_SPACE__RESERVED:
				setReserved(RESERVED_EDEFAULT);
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
	public EList<TargetElement> getContents() {
		if(contents == null) {
			contents = new EObjectContainmentEList<TargetElement>(
				TargetElement.class, this, PPTPPackage.NAME_SPACE__CONTENTS);
		}
		return contents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isReserved() {
		return reserved;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setReserved(boolean newReserved) {
		boolean oldReserved = reserved;
		reserved = newReserved;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPTPPackage.NAME_SPACE__RESERVED, oldReserved, reserved));
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
		result.append(" (reserved: ");
		result.append(reserved);
		result.append(')');
		return result.toString();
	}

} // NameSpaceImpl
