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

import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.HashEntry;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Hash Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.HashEntryImpl#getKey <em>Key</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.HashEntryImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class HashEntryImpl extends EObjectImpl implements HashEntry {
	/**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected Expression key;

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
	protected HashEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetKey(Expression newKey, NotificationChain msgs) {
		Expression oldKey = key;
		key = newKey;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.HASH_ENTRY__KEY, oldKey, newKey);
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
	public NotificationChain basicSetValue(Expression newValue, NotificationChain msgs) {
		Expression oldValue = value;
		value = newValue;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.HASH_ENTRY__VALUE, oldValue, newValue);
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
			case PPPackage.HASH_ENTRY__KEY:
				return getKey();
			case PPPackage.HASH_ENTRY__VALUE:
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
			case PPPackage.HASH_ENTRY__KEY:
				return basicSetKey(null, msgs);
			case PPPackage.HASH_ENTRY__VALUE:
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
			case PPPackage.HASH_ENTRY__KEY:
				return key != null;
			case PPPackage.HASH_ENTRY__VALUE:
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
			case PPPackage.HASH_ENTRY__KEY:
				setKey((Expression) newValue);
				return;
			case PPPackage.HASH_ENTRY__VALUE:
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
			case PPPackage.HASH_ENTRY__KEY:
				setKey((Expression) null);
				return;
			case PPPackage.HASH_ENTRY__VALUE:
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
	public Expression getKey() {
		return key;
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
	public void setKey(Expression newKey) {
		if(newKey != key) {
			NotificationChain msgs = null;
			if(key != null)
				msgs = ((InternalEObject) key).eInverseRemove(
					this, EOPPOSITE_FEATURE_BASE - PPPackage.HASH_ENTRY__KEY, null, msgs);
			if(newKey != null)
				msgs = ((InternalEObject) newKey).eInverseAdd(
					this, EOPPOSITE_FEATURE_BASE - PPPackage.HASH_ENTRY__KEY, null, msgs);
			msgs = basicSetKey(newKey, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PPPackage.HASH_ENTRY__KEY, newKey, newKey));
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
						PPPackage.HASH_ENTRY__VALUE, null, msgs);
			if(newValue != null)
				msgs = ((InternalEObject) newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.HASH_ENTRY__VALUE, null, msgs);
			msgs = basicSetValue(newValue, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PPPackage.HASH_ENTRY__VALUE, newValue, newValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PPPackage.Literals.HASH_ENTRY;
	}

} // HashEntryImpl
