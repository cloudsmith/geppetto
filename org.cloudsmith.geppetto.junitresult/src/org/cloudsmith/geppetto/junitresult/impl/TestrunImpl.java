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

import org.cloudsmith.geppetto.junitresult.JunitresultPackage;
import org.cloudsmith.geppetto.junitresult.Testrun;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Testrun</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestrunImpl#getProject <em>Project</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestrunImpl#getStarted <em>Started</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.junitresult.impl.TestrunImpl#getIgnored <em>Ignored</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TestrunImpl extends AbstractAggregatedTestImpl implements Testrun {
	/**
	 * The default value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProject() <em>Project</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getProject()
	 * @generated
	 * @ordered
	 */
	protected String project = PROJECT_EDEFAULT;

	/**
	 * The default value of the '{@link #getStarted() <em>Started</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getStarted()
	 * @generated
	 * @ordered
	 */
	protected static final int STARTED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStarted() <em>Started</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getStarted()
	 * @generated
	 * @ordered
	 */
	protected int started = STARTED_EDEFAULT;

	/**
	 * The default value of the '{@link #getIgnored() <em>Ignored</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getIgnored()
	 * @generated
	 * @ordered
	 */
	protected static final int IGNORED_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getIgnored() <em>Ignored</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getIgnored()
	 * @generated
	 * @ordered
	 */
	protected int ignored = IGNORED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TestrunImpl() {
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
			case JunitresultPackage.TESTRUN__PROJECT:
				return getProject();
			case JunitresultPackage.TESTRUN__STARTED:
				return getStarted();
			case JunitresultPackage.TESTRUN__IGNORED:
				return getIgnored();
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
			case JunitresultPackage.TESTRUN__PROJECT:
				return PROJECT_EDEFAULT == null
						? project != null
						: !PROJECT_EDEFAULT.equals(project);
			case JunitresultPackage.TESTRUN__STARTED:
				return started != STARTED_EDEFAULT;
			case JunitresultPackage.TESTRUN__IGNORED:
				return ignored != IGNORED_EDEFAULT;
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
			case JunitresultPackage.TESTRUN__PROJECT:
				setProject((String) newValue);
				return;
			case JunitresultPackage.TESTRUN__STARTED:
				setStarted((Integer) newValue);
				return;
			case JunitresultPackage.TESTRUN__IGNORED:
				setIgnored((Integer) newValue);
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
		return JunitresultPackage.Literals.TESTRUN;
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
			case JunitresultPackage.TESTRUN__PROJECT:
				setProject(PROJECT_EDEFAULT);
				return;
			case JunitresultPackage.TESTRUN__STARTED:
				setStarted(STARTED_EDEFAULT);
				return;
			case JunitresultPackage.TESTRUN__IGNORED:
				setIgnored(IGNORED_EDEFAULT);
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
	public int getIgnored() {
		return ignored;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getProject() {
		return project;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getStarted() {
		return started;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setIgnored(int newIgnored) {
		int oldIgnored = ignored;
		ignored = newIgnored;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, JunitresultPackage.TESTRUN__IGNORED, oldIgnored, ignored));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setProject(String newProject) {
		String oldProject = project;
		project = newProject;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, JunitresultPackage.TESTRUN__PROJECT, oldProject, project));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setStarted(int newStarted) {
		int oldStarted = started;
		started = newStarted;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, JunitresultPackage.TESTRUN__STARTED, oldStarted, started));
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
		result.append(" (project: ");
		result.append(project);
		result.append(", started: ");
		result.append(started);
		result.append(", ignored: ");
		result.append(ignored);
		result.append(')');
		return result.toString();
	}

} // TestrunImpl
