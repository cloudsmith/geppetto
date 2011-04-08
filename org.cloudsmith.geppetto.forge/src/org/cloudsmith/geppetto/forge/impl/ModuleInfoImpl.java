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
package org.cloudsmith.geppetto.forge.impl;

import java.net.URI;

import org.cloudsmith.geppetto.forge.ForgePackage;
import org.cloudsmith.geppetto.forge.ModuleInfo;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Module Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.ModuleInfoImpl#getFullName <em>Full Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.ModuleInfoImpl#getName <em>Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.ModuleInfoImpl#getProjectURL <em>Project URL</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.ModuleInfoImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ModuleInfoImpl extends EObjectImpl implements ModuleInfo {
	/**
	 * The default value of the '{@link #getFullName() <em>Full Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFullName()
	 * @generated
	 * @ordered
	 */
	protected static final String FULL_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFullName() <em>Full Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFullName()
	 * @generated
	 * @ordered
	 */
	@Expose
	@SerializedName("full_name")
	protected String fullName = FULL_NAME_EDEFAULT;

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
	@Expose
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getProjectURL() <em>Project URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getProjectURL()
	 * @generated
	 * @ordered
	 */
	protected static final URI PROJECT_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectURL() <em>Project URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getProjectURL()
	 * @generated
	 * @ordered
	 */
	@Expose
	@SerializedName("project_url")
	protected URI projectURL = PROJECT_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected String version = VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ModuleInfoImpl() {
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
			case ForgePackage.MODULE_INFO__FULL_NAME:
				return getFullName();
			case ForgePackage.MODULE_INFO__NAME:
				return getName();
			case ForgePackage.MODULE_INFO__PROJECT_URL:
				return getProjectURL();
			case ForgePackage.MODULE_INFO__VERSION:
				return getVersion();
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
			case ForgePackage.MODULE_INFO__FULL_NAME:
				return FULL_NAME_EDEFAULT == null
						? fullName != null
						: !FULL_NAME_EDEFAULT.equals(fullName);
			case ForgePackage.MODULE_INFO__NAME:
				return NAME_EDEFAULT == null
						? name != null
						: !NAME_EDEFAULT.equals(name);
			case ForgePackage.MODULE_INFO__PROJECT_URL:
				return PROJECT_URL_EDEFAULT == null
						? projectURL != null
						: !PROJECT_URL_EDEFAULT.equals(projectURL);
			case ForgePackage.MODULE_INFO__VERSION:
				return VERSION_EDEFAULT == null
						? version != null
						: !VERSION_EDEFAULT.equals(version);
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
			case ForgePackage.MODULE_INFO__FULL_NAME:
				setFullName((String) newValue);
				return;
			case ForgePackage.MODULE_INFO__NAME:
				setName((String) newValue);
				return;
			case ForgePackage.MODULE_INFO__PROJECT_URL:
				setProjectURL((URI) newValue);
				return;
			case ForgePackage.MODULE_INFO__VERSION:
				setVersion((String) newValue);
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
		return ForgePackage.Literals.MODULE_INFO;
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
			case ForgePackage.MODULE_INFO__FULL_NAME:
				setFullName(FULL_NAME_EDEFAULT);
				return;
			case ForgePackage.MODULE_INFO__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ForgePackage.MODULE_INFO__PROJECT_URL:
				setProjectURL(PROJECT_URL_EDEFAULT);
				return;
			case ForgePackage.MODULE_INFO__VERSION:
				setVersion(VERSION_EDEFAULT);
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
	@Override
	public String getFullName() {
		return fullName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public URI getProjectURL() {
		return projectURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setFullName(String newFullName) {
		String oldFullName = fullName;
		fullName = newFullName;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, ForgePackage.MODULE_INFO__FULL_NAME, oldFullName, fullName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ForgePackage.MODULE_INFO__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setProjectURL(URI newProjectURL) {
		URI oldProjectURL = projectURL;
		projectURL = newProjectURL;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, ForgePackage.MODULE_INFO__PROJECT_URL, oldProjectURL, projectURL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, ForgePackage.MODULE_INFO__VERSION, oldVersion, version));
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
		result.append(" (fullName: ");
		result.append(fullName);
		result.append(", name: ");
		result.append(name);
		result.append(", projectURL: ");
		result.append(projectURL);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} // ModuleInfoImpl
