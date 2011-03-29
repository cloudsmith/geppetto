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

import java.util.Collection;

import org.cloudsmith.geppetto.pp.pptp.IDocumented;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.cloudsmith.geppetto.pp.pptp.Parameter;
import org.cloudsmith.geppetto.pp.pptp.Property;
import org.cloudsmith.geppetto.pp.pptp.Type;

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
 * An implementation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.cloudsmith.geppetto.pp.pptp.impl.TypeImpl#getEReference0 <em>EReference0</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.pp.pptp.impl.TypeImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.pp.pptp.impl.TypeImpl#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TypeImpl extends TargetElementImpl implements Type {
	/**
	 * The cached value of the '{@link #getEReference0() <em>EReference0</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEReference0()
	 * @generated
	 * @ordered
	 */
	protected IDocumented eReference0;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> properties;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> parameters;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PPTPPackage.Literals.TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDocumented getEReference0() {
		if(eReference0 != null && eReference0.eIsProxy()) {
			InternalEObject oldEReference0 = (InternalEObject) eReference0;
			eReference0 = (IDocumented) eResolveProxy(oldEReference0);
			if(eReference0 != oldEReference0) {
				if(eNotificationRequired())
					eNotify(new ENotificationImpl(
						this, Notification.RESOLVE, PPTPPackage.TYPE__EREFERENCE0, oldEReference0, eReference0));
			}
		}
		return eReference0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDocumented basicGetEReference0() {
		return eReference0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEReference0(IDocumented newEReference0) {
		IDocumented oldEReference0 = eReference0;
		eReference0 = newEReference0;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPTPPackage.TYPE__EREFERENCE0, oldEReference0, eReference0));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Property> getProperties() {
		if(properties == null) {
			properties = new EObjectContainmentEList<Property>(Property.class, this, PPTPPackage.TYPE__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Parameter> getParameters() {
		if(parameters == null) {
			parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, PPTPPackage.TYPE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch(featureID) {
			case PPTPPackage.TYPE__PROPERTIES:
				return ((InternalEList<?>) getProperties()).basicRemove(otherEnd, msgs);
			case PPTPPackage.TYPE__PARAMETERS:
				return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch(featureID) {
			case PPTPPackage.TYPE__EREFERENCE0:
				if(resolve)
					return getEReference0();
				return basicGetEReference0();
			case PPTPPackage.TYPE__PROPERTIES:
				return getProperties();
			case PPTPPackage.TYPE__PARAMETERS:
				return getParameters();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch(featureID) {
			case PPTPPackage.TYPE__EREFERENCE0:
				setEReference0((IDocumented) newValue);
				return;
			case PPTPPackage.TYPE__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends Property>) newValue);
				return;
			case PPTPPackage.TYPE__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Parameter>) newValue);
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
			case PPTPPackage.TYPE__EREFERENCE0:
				setEReference0((IDocumented) null);
				return;
			case PPTPPackage.TYPE__PROPERTIES:
				getProperties().clear();
				return;
			case PPTPPackage.TYPE__PARAMETERS:
				getParameters().clear();
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
			case PPTPPackage.TYPE__EREFERENCE0:
				return eReference0 != null;
			case PPTPPackage.TYPE__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case PPTPPackage.TYPE__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //TypeImpl
