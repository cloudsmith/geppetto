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

import org.cloudsmith.geppetto.junitresult.Failure;
import org.cloudsmith.geppetto.junitresult.JunitresultPackage;
import org.cloudsmith.geppetto.junitresult.NegativeResult;
import org.cloudsmith.geppetto.junitresult.Testcase;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Testcase</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl#getNegativeResult <em>Negative Result</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl#getClassname <em>Classname</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl#getTime <em>Time</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TestcaseImpl extends EObjectImpl implements Testcase {
	/**
	 * The cached value of the '{@link #getNegativeResult() <em>Negative Result</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNegativeResult()
	 * @generated
	 * @ordered
	 */
	protected NegativeResult negativeResult;

	/**
	 * The default value of the '{@link #getClassname() <em>Classname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassname()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASSNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassname() <em>Classname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassname()
	 * @generated
	 * @ordered
	 */
	protected String classname = CLASSNAME_EDEFAULT;

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
	 * The default value of the '{@link #getTime() <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTime()
	 * @generated
	 * @ordered
	 */
	protected static final String TIME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTime() <em>Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTime()
	 * @generated
	 * @ordered
	 */
	protected String time = TIME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TestcaseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JunitresultPackage.TESTCASE__NEGATIVE_RESULT:
				return getNegativeResult();
			case JunitresultPackage.TESTCASE__CLASSNAME:
				return getClassname();
			case JunitresultPackage.TESTCASE__NAME:
				return getName();
			case JunitresultPackage.TESTCASE__TIME:
				return getTime();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case JunitresultPackage.TESTCASE__NEGATIVE_RESULT:
				return basicSetNegativeResult(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case JunitresultPackage.TESTCASE__NEGATIVE_RESULT:
				return negativeResult != null;
			case JunitresultPackage.TESTCASE__CLASSNAME:
				return CLASSNAME_EDEFAULT == null ? classname != null : !CLASSNAME_EDEFAULT.equals(classname);
			case JunitresultPackage.TESTCASE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case JunitresultPackage.TESTCASE__TIME:
				return TIME_EDEFAULT == null ? time != null : !TIME_EDEFAULT.equals(time);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JunitresultPackage.TESTCASE__NEGATIVE_RESULT:
				setNegativeResult((NegativeResult)newValue);
				return;
			case JunitresultPackage.TESTCASE__CLASSNAME:
				setClassname((String)newValue);
				return;
			case JunitresultPackage.TESTCASE__NAME:
				setName((String)newValue);
				return;
			case JunitresultPackage.TESTCASE__TIME:
				setTime((String)newValue);
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
	protected EClass eStaticClass() {
		return JunitresultPackage.Literals.TESTCASE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NegativeResult getNegativeResult() {
		return negativeResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNegativeResult(NegativeResult newNegativeResult, NotificationChain msgs) {
		NegativeResult oldNegativeResult = negativeResult;
		negativeResult = newNegativeResult;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTCASE__NEGATIVE_RESULT, oldNegativeResult, newNegativeResult);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNegativeResult(NegativeResult newNegativeResult) {
		if (newNegativeResult != negativeResult) {
			NotificationChain msgs = null;
			if (negativeResult != null)
				msgs = ((InternalEObject)negativeResult).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - JunitresultPackage.TESTCASE__NEGATIVE_RESULT, null, msgs);
			if (newNegativeResult != null)
				msgs = ((InternalEObject)newNegativeResult).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - JunitresultPackage.TESTCASE__NEGATIVE_RESULT, null, msgs);
			msgs = basicSetNegativeResult(newNegativeResult, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTCASE__NEGATIVE_RESULT, newNegativeResult, newNegativeResult));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case JunitresultPackage.TESTCASE__NEGATIVE_RESULT:
				setNegativeResult((NegativeResult)null);
				return;
			case JunitresultPackage.TESTCASE__CLASSNAME:
				setClassname(CLASSNAME_EDEFAULT);
				return;
			case JunitresultPackage.TESTCASE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case JunitresultPackage.TESTCASE__TIME:
				setTime(TIME_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getClassname() {
		return classname;
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
	public String getTime() {
		return time;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClassname(String newClassname) {
		String oldClassname = classname;
		classname = newClassname;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTCASE__CLASSNAME, oldClassname, classname));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTCASE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTime(String newTime) {
		String oldTime = time;
		time = newTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTCASE__TIME, oldTime, time));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (classname: ");
		result.append(classname);
		result.append(", name: ");
		result.append(name);
		result.append(", time: ");
		result.append(time);
		result.append(')');
		return result.toString();
	}

} // TestcaseImpl
