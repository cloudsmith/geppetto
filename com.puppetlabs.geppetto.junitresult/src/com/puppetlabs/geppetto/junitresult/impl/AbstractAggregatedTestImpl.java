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
package com.puppetlabs.geppetto.junitresult.impl;

import java.util.Collection;
import com.puppetlabs.geppetto.junitresult.AbstractAggregatedTest;
import com.puppetlabs.geppetto.junitresult.JunitresultPackage;

import com.puppetlabs.geppetto.junitresult.Testsuite;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Aggregated Test</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link com.puppetlabs.geppetto.junitresult.impl.AbstractAggregatedTestImpl#getName <em>Name</em>}</li>
 * <li>{@link com.puppetlabs.geppetto.junitresult.impl.AbstractAggregatedTestImpl#getTests <em>Tests</em>}</li>
 * <li>{@link com.puppetlabs.geppetto.junitresult.impl.AbstractAggregatedTestImpl#getFailures <em>Failures</em>}</li>
 * <li>{@link com.puppetlabs.geppetto.junitresult.impl.AbstractAggregatedTestImpl#getErrors <em>Errors</em>}</li>
 * <li>{@link com.puppetlabs.geppetto.junitresult.impl.AbstractAggregatedTestImpl#getTestsuites <em>Testsuites</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class AbstractAggregatedTestImpl extends EObjectImpl implements AbstractAggregatedTest {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getTests() <em>Tests</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTests()
	 * @generated
	 * @ordered
	 */
	protected static final int TESTS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getTests() <em>Tests</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTests()
	 * @generated
	 * @ordered
	 */
	protected int tests = TESTS_EDEFAULT;

	/**
	 * The default value of the '{@link #getFailures() <em>Failures</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFailures()
	 * @generated
	 * @ordered
	 */
	protected static final int FAILURES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getFailures() <em>Failures</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFailures()
	 * @generated
	 * @ordered
	 */
	protected int failures = FAILURES_EDEFAULT;

	/**
	 * The default value of the '{@link #getErrors() <em>Errors</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getErrors()
	 * @generated
	 * @ordered
	 */
	protected static final int ERRORS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getErrors() <em>Errors</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getErrors()
	 * @generated
	 * @ordered
	 */
	protected int errors = ERRORS_EDEFAULT;

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
	protected AbstractAggregatedTestImpl() {
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
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__NAME:
				return getName();
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__TESTS:
				return getTests();
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__FAILURES:
				return getFailures();
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__ERRORS:
				return getErrors();
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__TESTSUITES:
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
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__TESTSUITES:
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
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__NAME:
				return NAME_EDEFAULT == null
						? name != null
						: !NAME_EDEFAULT.equals(name);
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__TESTS:
				return tests != TESTS_EDEFAULT;
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__FAILURES:
				return failures != FAILURES_EDEFAULT;
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__ERRORS:
				return errors != ERRORS_EDEFAULT;
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__TESTSUITES:
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
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__NAME:
				setName((String) newValue);
				return;
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__TESTS:
				setTests((Integer) newValue);
				return;
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__FAILURES:
				setFailures((Integer) newValue);
				return;
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__ERRORS:
				setErrors((Integer) newValue);
				return;
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__TESTSUITES:
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
		return JunitresultPackage.Literals.ABSTRACT_AGGREGATED_TEST;
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
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__NAME:
				setName(NAME_EDEFAULT);
				return;
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__TESTS:
				setTests(TESTS_EDEFAULT);
				return;
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__FAILURES:
				setFailures(FAILURES_EDEFAULT);
				return;
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__ERRORS:
				setErrors(ERRORS_EDEFAULT);
				return;
			case JunitresultPackage.ABSTRACT_AGGREGATED_TEST__TESTSUITES:
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
	public int getErrors() {
		return errors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getFailures() {
		return failures;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getTests() {
		return tests;
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
				Testsuite.class, this, JunitresultPackage.ABSTRACT_AGGREGATED_TEST__TESTSUITES);
		}
		return testsuites;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setErrors(int newErrors) {
		int oldErrors = errors;
		errors = newErrors;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, JunitresultPackage.ABSTRACT_AGGREGATED_TEST__ERRORS, oldErrors, errors));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setFailures(int newFailures) {
		int oldFailures = failures;
		failures = newFailures;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, JunitresultPackage.ABSTRACT_AGGREGATED_TEST__FAILURES, oldFailures, failures));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, JunitresultPackage.ABSTRACT_AGGREGATED_TEST__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTests(int newTests) {
		int oldTests = tests;
		tests = newTests;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, JunitresultPackage.ABSTRACT_AGGREGATED_TEST__TESTS, oldTests, tests));
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
		result.append(" (name: ");
		result.append(name);
		result.append(", tests: ");
		result.append(tests);
		result.append(", failures: ");
		result.append(failures);
		result.append(", errors: ");
		result.append(errors);
		result.append(')');
		return result.toString();
	}

} // AbstractAggregatedTestImpl
