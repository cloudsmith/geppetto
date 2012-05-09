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
import java.util.Date;

import org.cloudsmith.geppetto.junitresult.JunitresultPackage;
import org.cloudsmith.geppetto.junitresult.Property;
import org.cloudsmith.geppetto.junitresult.Testcase;
import org.cloudsmith.geppetto.junitresult.Testsuite;

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
 * An implementation of the model object '<em><b>Testsuite</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl#getTestcases <em>Testcases</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl#getSystem_out <em>System out</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl#getSystem_err <em>System err</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl#getHostname <em>Hostname</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl#getTime <em>Time</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl#getTestsuites <em>Testsuites</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl#getDisabled <em>Disabled</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestsuiteImpl#getSkipped <em>Skipped</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TestsuiteImpl extends AbstractAggregatedTestImpl implements Testsuite {
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
	 * The cached value of the '{@link #getTestcases() <em>Testcases</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTestcases()
	 * @generated
	 * @ordered
	 */
	protected EList<Testcase> testcases;

	/**
	 * The default value of the '{@link #getSystem_out() <em>System out</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystem_out()
	 * @generated
	 * @ordered
	 */
	protected static final String SYSTEM_OUT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSystem_out() <em>System out</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystem_out()
	 * @generated
	 * @ordered
	 */
	protected String system_out = SYSTEM_OUT_EDEFAULT;

	/**
	 * The default value of the '{@link #getSystem_err() <em>System err</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystem_err()
	 * @generated
	 * @ordered
	 */
	protected static final String SYSTEM_ERR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSystem_err() <em>System err</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystem_err()
	 * @generated
	 * @ordered
	 */
	protected String system_err = SYSTEM_ERR_EDEFAULT;

	/**
	 * The default value of the '{@link #getHostname() <em>Hostname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHostname()
	 * @generated
	 * @ordered
	 */
	protected static final String HOSTNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHostname() <em>Hostname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHostname()
	 * @generated
	 * @ordered
	 */
	protected String hostname = HOSTNAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected static final Date TIMESTAMP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected Date timestamp = TIMESTAMP_EDEFAULT;

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
	 * The cached value of the '{@link #getTestsuites() <em>Testsuites</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTestsuites()
	 * @generated
	 * @ordered
	 */
	protected EList<Testsuite> testsuites;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final int ID_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected int id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackage() <em>Package</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected String package_ = PACKAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisabled() <em>Disabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisabled()
	 * @generated
	 * @ordered
	 */
	protected static final int DISABLED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDisabled() <em>Disabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisabled()
	 * @generated
	 * @ordered
	 */
	protected int disabled = DISABLED_EDEFAULT;

	/**
	 * The default value of the '{@link #getSkipped() <em>Skipped</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSkipped()
	 * @generated
	 * @ordered
	 */
	protected static final int SKIPPED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getSkipped() <em>Skipped</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSkipped()
	 * @generated
	 * @ordered
	 */
	protected int skipped = SKIPPED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TestsuiteImpl() {
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
			case JunitresultPackage.TESTSUITE__PROPERTIES:
				return getProperties();
			case JunitresultPackage.TESTSUITE__TESTCASES:
				return getTestcases();
			case JunitresultPackage.TESTSUITE__SYSTEM_OUT:
				return getSystem_out();
			case JunitresultPackage.TESTSUITE__SYSTEM_ERR:
				return getSystem_err();
			case JunitresultPackage.TESTSUITE__HOSTNAME:
				return getHostname();
			case JunitresultPackage.TESTSUITE__TIMESTAMP:
				return getTimestamp();
			case JunitresultPackage.TESTSUITE__TIME:
				return getTime();
			case JunitresultPackage.TESTSUITE__TESTSUITES:
				return getTestsuites();
			case JunitresultPackage.TESTSUITE__ID:
				return getId();
			case JunitresultPackage.TESTSUITE__PACKAGE:
				return getPackage();
			case JunitresultPackage.TESTSUITE__DISABLED:
				return getDisabled();
			case JunitresultPackage.TESTSUITE__SKIPPED:
				return getSkipped();
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
			case JunitresultPackage.TESTSUITE__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
			case JunitresultPackage.TESTSUITE__TESTCASES:
				return ((InternalEList<?>)getTestcases()).basicRemove(otherEnd, msgs);
			case JunitresultPackage.TESTSUITE__TESTSUITES:
				return ((InternalEList<?>)getTestsuites()).basicRemove(otherEnd, msgs);
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
			case JunitresultPackage.TESTSUITE__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case JunitresultPackage.TESTSUITE__TESTCASES:
				return testcases != null && !testcases.isEmpty();
			case JunitresultPackage.TESTSUITE__SYSTEM_OUT:
				return SYSTEM_OUT_EDEFAULT == null ? system_out != null : !SYSTEM_OUT_EDEFAULT.equals(system_out);
			case JunitresultPackage.TESTSUITE__SYSTEM_ERR:
				return SYSTEM_ERR_EDEFAULT == null ? system_err != null : !SYSTEM_ERR_EDEFAULT.equals(system_err);
			case JunitresultPackage.TESTSUITE__HOSTNAME:
				return HOSTNAME_EDEFAULT == null ? hostname != null : !HOSTNAME_EDEFAULT.equals(hostname);
			case JunitresultPackage.TESTSUITE__TIMESTAMP:
				return TIMESTAMP_EDEFAULT == null ? timestamp != null : !TIMESTAMP_EDEFAULT.equals(timestamp);
			case JunitresultPackage.TESTSUITE__TIME:
				return TIME_EDEFAULT == null ? time != null : !TIME_EDEFAULT.equals(time);
			case JunitresultPackage.TESTSUITE__TESTSUITES:
				return testsuites != null && !testsuites.isEmpty();
			case JunitresultPackage.TESTSUITE__ID:
				return id != ID_EDEFAULT;
			case JunitresultPackage.TESTSUITE__PACKAGE:
				return PACKAGE_EDEFAULT == null ? package_ != null : !PACKAGE_EDEFAULT.equals(package_);
			case JunitresultPackage.TESTSUITE__DISABLED:
				return disabled != DISABLED_EDEFAULT;
			case JunitresultPackage.TESTSUITE__SKIPPED:
				return skipped != SKIPPED_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JunitresultPackage.TESTSUITE__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends Property>)newValue);
				return;
			case JunitresultPackage.TESTSUITE__TESTCASES:
				getTestcases().clear();
				getTestcases().addAll((Collection<? extends Testcase>)newValue);
				return;
			case JunitresultPackage.TESTSUITE__SYSTEM_OUT:
				setSystem_out((String)newValue);
				return;
			case JunitresultPackage.TESTSUITE__SYSTEM_ERR:
				setSystem_err((String)newValue);
				return;
			case JunitresultPackage.TESTSUITE__HOSTNAME:
				setHostname((String)newValue);
				return;
			case JunitresultPackage.TESTSUITE__TIMESTAMP:
				setTimestamp((Date)newValue);
				return;
			case JunitresultPackage.TESTSUITE__TIME:
				setTime((String)newValue);
				return;
			case JunitresultPackage.TESTSUITE__TESTSUITES:
				getTestsuites().clear();
				getTestsuites().addAll((Collection<? extends Testsuite>)newValue);
				return;
			case JunitresultPackage.TESTSUITE__ID:
				setId((Integer)newValue);
				return;
			case JunitresultPackage.TESTSUITE__PACKAGE:
				setPackage((String)newValue);
				return;
			case JunitresultPackage.TESTSUITE__DISABLED:
				setDisabled((Integer)newValue);
				return;
			case JunitresultPackage.TESTSUITE__SKIPPED:
				setSkipped((Integer)newValue);
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
		return JunitresultPackage.Literals.TESTSUITE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case JunitresultPackage.TESTSUITE__PROPERTIES:
				getProperties().clear();
				return;
			case JunitresultPackage.TESTSUITE__TESTCASES:
				getTestcases().clear();
				return;
			case JunitresultPackage.TESTSUITE__SYSTEM_OUT:
				setSystem_out(SYSTEM_OUT_EDEFAULT);
				return;
			case JunitresultPackage.TESTSUITE__SYSTEM_ERR:
				setSystem_err(SYSTEM_ERR_EDEFAULT);
				return;
			case JunitresultPackage.TESTSUITE__HOSTNAME:
				setHostname(HOSTNAME_EDEFAULT);
				return;
			case JunitresultPackage.TESTSUITE__TIMESTAMP:
				setTimestamp(TIMESTAMP_EDEFAULT);
				return;
			case JunitresultPackage.TESTSUITE__TIME:
				setTime(TIME_EDEFAULT);
				return;
			case JunitresultPackage.TESTSUITE__TESTSUITES:
				getTestsuites().clear();
				return;
			case JunitresultPackage.TESTSUITE__ID:
				setId(ID_EDEFAULT);
				return;
			case JunitresultPackage.TESTSUITE__PACKAGE:
				setPackage(PACKAGE_EDEFAULT);
				return;
			case JunitresultPackage.TESTSUITE__DISABLED:
				setDisabled(DISABLED_EDEFAULT);
				return;
			case JunitresultPackage.TESTSUITE__SKIPPED:
				setSkipped(SKIPPED_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackage() {
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Property> getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList<Property>(Property.class, this, JunitresultPackage.TESTSUITE__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSystem_err() {
		return system_err;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSystem_out() {
		return system_out;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Testcase> getTestcases() {
		if (testcases == null) {
			testcases = new EObjectContainmentEList<Testcase>(Testcase.class, this, JunitresultPackage.TESTSUITE__TESTCASES);
		}
		return testcases;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Testsuite> getTestsuites() {
		if (testsuites == null) {
			testsuites = new EObjectContainmentEList<Testsuite>(Testsuite.class, this, JunitresultPackage.TESTSUITE__TESTSUITES);
		}
		return testsuites;
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
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHostname(String newHostname) {
		String oldHostname = hostname;
		hostname = newHostname;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTSUITE__HOSTNAME, oldHostname, hostname));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(int newId) {
		int oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTSUITE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackage(String newPackage) {
		String oldPackage = package_;
		package_ = newPackage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTSUITE__PACKAGE, oldPackage, package_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDisabled() {
		return disabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisabled(int newDisabled) {
		int oldDisabled = disabled;
		disabled = newDisabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTSUITE__DISABLED, oldDisabled, disabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getSkipped() {
		return skipped;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSkipped(int newSkipped) {
		int oldSkipped = skipped;
		skipped = newSkipped;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTSUITE__SKIPPED, oldSkipped, skipped));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSystem_err(String newSystem_err) {
		String oldSystem_err = system_err;
		system_err = newSystem_err;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTSUITE__SYSTEM_ERR, oldSystem_err, system_err));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSystem_out(String newSystem_out) {
		String oldSystem_out = system_out;
		system_out = newSystem_out;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTSUITE__SYSTEM_OUT, oldSystem_out, system_out));
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
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTSUITE__TIME, oldTime, time));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimestamp(Date newTimestamp) {
		Date oldTimestamp = timestamp;
		timestamp = newTimestamp;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JunitresultPackage.TESTSUITE__TIMESTAMP, oldTimestamp, timestamp));
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
		result.append(" (system_out: ");
		result.append(system_out);
		result.append(", system_err: ");
		result.append(system_err);
		result.append(", hostname: ");
		result.append(hostname);
		result.append(", timestamp: ");
		result.append(timestamp);
		result.append(", time: ");
		result.append(time);
		result.append(", id: ");
		result.append(id);
		result.append(", package: ");
		result.append(package_);
		result.append(", disabled: ");
		result.append(disabled);
		result.append(", skipped: ");
		result.append(skipped);
		result.append(')');
		return result.toString();
	}

} // TestsuiteImpl
