/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.junitresult.impl;

import java.util.Collection;

import org.cloudsmith.geppetto.junitresult.JunitresultPackage;
import org.cloudsmith.geppetto.junitresult.Testsuite;
import org.cloudsmith.geppetto.junitresult.Testsuites;

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
 * An implementation of the model object '<em><b>Testsuites</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuitesImpl#getTestsuites <em>Testsuites</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuitesImpl#getTime <em>Time</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuitesImpl#getDisabled <em>Disabled</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TestsuitesImpl extends AbstractAggregatedTestImpl implements Testsuites {
	/**
	 * The cached value of the '{@link #getTestsuites() <em>Testsuites</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTestsuites()
	 * @generated
	 * @ordered
	 */
	protected EList<Testsuite> testsuites;

	/**
	 * The default value of the '{@link #getTime() <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTime()
	 * @generated
	 * @ordered
	 */
	protected static final double TIME_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getTime() <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTime()
	 * @generated
	 * @ordered
	 */
	protected double time = TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisabled() <em>Disabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDisabled()
	 * @generated
	 * @ordered
	 */
	protected static final int DISABLED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDisabled() <em>Disabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDisabled()
	 * @generated
	 * @ordered
	 */
	protected int disabled = DISABLED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TestsuitesImpl() {
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
			case JunitresultPackage.TESTSUITES__TESTSUITES:
				return getTestsuites();
			case JunitresultPackage.TESTSUITES__TIME:
				return getTime();
			case JunitresultPackage.TESTSUITES__DISABLED:
				return getDisabled();
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
			case JunitresultPackage.TESTSUITES__TESTSUITES:
				return ((InternalEList<?>) getTestsuites()).basicRemove(otherEnd, msgs);
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
			case JunitresultPackage.TESTSUITES__TESTSUITES:
				return testsuites != null && !testsuites.isEmpty();
			case JunitresultPackage.TESTSUITES__TIME:
				return time != TIME_EDEFAULT;
			case JunitresultPackage.TESTSUITES__DISABLED:
				return disabled != DISABLED_EDEFAULT;
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
			case JunitresultPackage.TESTSUITES__TESTSUITES:
				getTestsuites().clear();
				getTestsuites().addAll((Collection<? extends Testsuite>) newValue);
				return;
			case JunitresultPackage.TESTSUITES__TIME:
				setTime((Double) newValue);
				return;
			case JunitresultPackage.TESTSUITES__DISABLED:
				setDisabled((Integer) newValue);
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
		return JunitresultPackage.Literals.TESTSUITES;
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
			case JunitresultPackage.TESTSUITES__TESTSUITES:
				getTestsuites().clear();
				return;
			case JunitresultPackage.TESTSUITES__TIME:
				setTime(TIME_EDEFAULT);
				return;
			case JunitresultPackage.TESTSUITES__DISABLED:
				setDisabled(DISABLED_EDEFAULT);
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
	public int getDisabled() {
		return disabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Testsuite> getTestsuites() {
		if(testsuites == null) {
			testsuites = new EObjectContainmentEList<Testsuite>(
				Testsuite.class, this, JunitresultPackage.TESTSUITES__TESTSUITES);
		}
		return testsuites;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public double getTime() {
		return time;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setDisabled(int newDisabled) {
		int oldDisabled = disabled;
		disabled = newDisabled;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, JunitresultPackage.TESTSUITES__DISABLED, oldDisabled, disabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTime(double newTime) {
		double oldTime = time;
		time = newTime;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTSUITES__TIME, oldTime, time));
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
		result.append(" (time: ");
		result.append(time);
		result.append(", disabled: ");
		result.append(disabled);
		result.append(')');
		return result.toString();
	}

} // TestsuitesImpl
