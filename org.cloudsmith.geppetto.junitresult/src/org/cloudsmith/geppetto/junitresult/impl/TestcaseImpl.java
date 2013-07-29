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
package org.cloudsmith.geppetto.junitresult.impl;

import java.util.Collection;
import org.cloudsmith.geppetto.junitresult.Failure;
import org.cloudsmith.geppetto.junitresult.JunitresultPackage;
import org.cloudsmith.geppetto.junitresult.Skipped;
import org.cloudsmith.geppetto.junitresult.Testcase;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Testcase</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl#getSkipped <em>Skipped</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl#getName <em>Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl#getClassname <em>Classname</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl#getTime <em>Time</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl#getSystem_out <em>System out</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl#getSystem_err <em>System err</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl#getStatus <em>Status</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl#getAssertions <em>Assertions</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl#getFailures <em>Failures</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestcaseImpl#getErrors <em>Errors</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TestcaseImpl extends EObjectImpl implements Testcase {
	/**
	 * The cached value of the '{@link #getSkipped() <em>Skipped</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSkipped()
	 * @generated
	 * @ordered
	 */
	protected Skipped skipped;

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
	 * The default value of the '{@link #getClassname() <em>Classname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getClassname()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASSNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassname() <em>Classname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getClassname()
	 * @generated
	 * @ordered
	 */
	protected String classname = CLASSNAME_EDEFAULT;

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
	 * The cached value of the '{@link #getSystem_out() <em>System out</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSystem_out()
	 * @generated
	 * @ordered
	 */
	protected EList<String> system_out;

	/**
	 * The cached value of the '{@link #getSystem_err() <em>System err</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSystem_err()
	 * @generated
	 * @ordered
	 */
	protected EList<String> system_err;

	/**
	 * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected static final String STATUS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected String status = STATUS_EDEFAULT;

	/**
	 * The default value of the '{@link #getAssertions() <em>Assertions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getAssertions()
	 * @generated
	 * @ordered
	 */
	protected static final String ASSERTIONS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAssertions() <em>Assertions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getAssertions()
	 * @generated
	 * @ordered
	 */
	protected String assertions = ASSERTIONS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFailures() <em>Failures</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFailures()
	 * @generated
	 * @ordered
	 */
	protected EList<Failure> failures;

	/**
	 * The cached value of the '{@link #getErrors() <em>Errors</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getErrors()
	 * @generated
	 * @ordered
	 */
	protected EList<org.cloudsmith.geppetto.junitresult.Error> errors;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TestcaseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetSkipped(Skipped newSkipped, NotificationChain msgs) {
		Skipped oldSkipped = skipped;
		skipped = newSkipped;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, JunitresultPackage.TESTCASE__SKIPPED, oldSkipped, newSkipped);
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
			case JunitresultPackage.TESTCASE__SKIPPED:
				return getSkipped();
			case JunitresultPackage.TESTCASE__NAME:
				return getName();
			case JunitresultPackage.TESTCASE__CLASSNAME:
				return getClassname();
			case JunitresultPackage.TESTCASE__TIME:
				return getTime();
			case JunitresultPackage.TESTCASE__SYSTEM_OUT:
				return getSystem_out();
			case JunitresultPackage.TESTCASE__SYSTEM_ERR:
				return getSystem_err();
			case JunitresultPackage.TESTCASE__STATUS:
				return getStatus();
			case JunitresultPackage.TESTCASE__ASSERTIONS:
				return getAssertions();
			case JunitresultPackage.TESTCASE__FAILURES:
				return getFailures();
			case JunitresultPackage.TESTCASE__ERRORS:
				return getErrors();
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
			case JunitresultPackage.TESTCASE__SKIPPED:
				return basicSetSkipped(null, msgs);
			case JunitresultPackage.TESTCASE__FAILURES:
				return ((InternalEList<?>) getFailures()).basicRemove(otherEnd, msgs);
			case JunitresultPackage.TESTCASE__ERRORS:
				return ((InternalEList<?>) getErrors()).basicRemove(otherEnd, msgs);
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
			case JunitresultPackage.TESTCASE__SKIPPED:
				return skipped != null;
			case JunitresultPackage.TESTCASE__NAME:
				return NAME_EDEFAULT == null
						? name != null
						: !NAME_EDEFAULT.equals(name);
			case JunitresultPackage.TESTCASE__CLASSNAME:
				return CLASSNAME_EDEFAULT == null
						? classname != null
						: !CLASSNAME_EDEFAULT.equals(classname);
			case JunitresultPackage.TESTCASE__TIME:
				return time != TIME_EDEFAULT;
			case JunitresultPackage.TESTCASE__SYSTEM_OUT:
				return system_out != null && !system_out.isEmpty();
			case JunitresultPackage.TESTCASE__SYSTEM_ERR:
				return system_err != null && !system_err.isEmpty();
			case JunitresultPackage.TESTCASE__STATUS:
				return STATUS_EDEFAULT == null
						? status != null
						: !STATUS_EDEFAULT.equals(status);
			case JunitresultPackage.TESTCASE__ASSERTIONS:
				return ASSERTIONS_EDEFAULT == null
						? assertions != null
						: !ASSERTIONS_EDEFAULT.equals(assertions);
			case JunitresultPackage.TESTCASE__FAILURES:
				return failures != null && !failures.isEmpty();
			case JunitresultPackage.TESTCASE__ERRORS:
				return errors != null && !errors.isEmpty();
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
			case JunitresultPackage.TESTCASE__SKIPPED:
				setSkipped((Skipped) newValue);
				return;
			case JunitresultPackage.TESTCASE__NAME:
				setName((String) newValue);
				return;
			case JunitresultPackage.TESTCASE__CLASSNAME:
				setClassname((String) newValue);
				return;
			case JunitresultPackage.TESTCASE__TIME:
				setTime((Double) newValue);
				return;
			case JunitresultPackage.TESTCASE__SYSTEM_OUT:
				getSystem_out().clear();
				getSystem_out().addAll((Collection<? extends String>) newValue);
				return;
			case JunitresultPackage.TESTCASE__SYSTEM_ERR:
				getSystem_err().clear();
				getSystem_err().addAll((Collection<? extends String>) newValue);
				return;
			case JunitresultPackage.TESTCASE__STATUS:
				setStatus((String) newValue);
				return;
			case JunitresultPackage.TESTCASE__ASSERTIONS:
				setAssertions((String) newValue);
				return;
			case JunitresultPackage.TESTCASE__FAILURES:
				getFailures().clear();
				getFailures().addAll((Collection<? extends Failure>) newValue);
				return;
			case JunitresultPackage.TESTCASE__ERRORS:
				getErrors().clear();
				getErrors().addAll((Collection<? extends org.cloudsmith.geppetto.junitresult.Error>) newValue);
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
		return JunitresultPackage.Literals.TESTCASE;
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
			case JunitresultPackage.TESTCASE__SKIPPED:
				setSkipped((Skipped) null);
				return;
			case JunitresultPackage.TESTCASE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case JunitresultPackage.TESTCASE__CLASSNAME:
				setClassname(CLASSNAME_EDEFAULT);
				return;
			case JunitresultPackage.TESTCASE__TIME:
				setTime(TIME_EDEFAULT);
				return;
			case JunitresultPackage.TESTCASE__SYSTEM_OUT:
				getSystem_out().clear();
				return;
			case JunitresultPackage.TESTCASE__SYSTEM_ERR:
				getSystem_err().clear();
				return;
			case JunitresultPackage.TESTCASE__STATUS:
				setStatus(STATUS_EDEFAULT);
				return;
			case JunitresultPackage.TESTCASE__ASSERTIONS:
				setAssertions(ASSERTIONS_EDEFAULT);
				return;
			case JunitresultPackage.TESTCASE__FAILURES:
				getFailures().clear();
				return;
			case JunitresultPackage.TESTCASE__ERRORS:
				getErrors().clear();
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
	public String getAssertions() {
		return assertions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getClassname() {
		return classname;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<org.cloudsmith.geppetto.junitresult.Error> getErrors() {
		if(errors == null) {
			errors = new EObjectContainmentEList<org.cloudsmith.geppetto.junitresult.Error>(
				org.cloudsmith.geppetto.junitresult.Error.class, this, JunitresultPackage.TESTCASE__ERRORS);
		}
		return errors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Failure> getFailures() {
		if(failures == null) {
			failures = new EObjectContainmentEList<Failure>(Failure.class, this, JunitresultPackage.TESTCASE__FAILURES);
		}
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
	public Skipped getSkipped() {
		return skipped;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<String> getSystem_err() {
		if(system_err == null) {
			system_err = new EDataTypeUniqueEList<String>(String.class, this, JunitresultPackage.TESTCASE__SYSTEM_ERR);
		}
		return system_err;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<String> getSystem_out() {
		if(system_out == null) {
			system_out = new EDataTypeUniqueEList<String>(String.class, this, JunitresultPackage.TESTCASE__SYSTEM_OUT);
		}
		return system_out;
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
	public void setAssertions(String newAssertions) {
		String oldAssertions = assertions;
		assertions = newAssertions;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, JunitresultPackage.TESTCASE__ASSERTIONS, oldAssertions, assertions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setClassname(String newClassname) {
		String oldClassname = classname;
		classname = newClassname;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, JunitresultPackage.TESTCASE__CLASSNAME, oldClassname, classname));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTCASE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSkipped(Skipped newSkipped) {
		if(newSkipped != skipped) {
			NotificationChain msgs = null;
			if(skipped != null)
				msgs = ((InternalEObject) skipped).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						JunitresultPackage.TESTCASE__SKIPPED, null, msgs);
			if(newSkipped != null)
				msgs = ((InternalEObject) newSkipped).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						JunitresultPackage.TESTCASE__SKIPPED, null, msgs);
			msgs = basicSetSkipped(newSkipped, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, JunitresultPackage.TESTCASE__SKIPPED, newSkipped, newSkipped));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setStatus(String newStatus) {
		String oldStatus = status;
		status = newStatus;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, JunitresultPackage.TESTCASE__STATUS, oldStatus, status));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTCASE__TIME, oldTime, time));
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
		result.append(", classname: ");
		result.append(classname);
		result.append(", time: ");
		result.append(time);
		result.append(", system_out: ");
		result.append(system_out);
		result.append(", system_err: ");
		result.append(system_err);
		result.append(", status: ");
		result.append(status);
		result.append(", assertions: ");
		result.append(assertions);
		result.append(')');
		return result.toString();
	}

} // TestcaseImpl
