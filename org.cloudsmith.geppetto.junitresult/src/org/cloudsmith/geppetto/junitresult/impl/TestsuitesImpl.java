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

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

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
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TestsuitesImpl extends EObjectImpl implements Testsuites {
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
		}
		super.eUnset(featureID);
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

} // TestsuitesImpl
