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

import org.cloudsmith.geppetto.forge.Dependency;
import org.cloudsmith.geppetto.forge.ForgePackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.google.gson.annotations.Expose;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dependency</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.DependencyImpl#getName <em>Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.DependencyImpl#getVersionRequirement <em>Version Requirement</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.DependencyImpl#getRepository <em>Repository</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class DependencyImpl extends EObjectImpl implements Dependency {
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
	 * The default value of the '{@link #getVersionRequirement() <em>Version Requirement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getVersionRequirement()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_REQUIREMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersionRequirement() <em>Version Requirement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getVersionRequirement()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected String versionRequirement = VERSION_REQUIREMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getRepository() <em>Repository</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getRepository()
	 * @generated
	 * @ordered
	 */
	protected static final URI REPOSITORY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRepository() <em>Repository</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getRepository()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected URI repository = REPOSITORY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected DependencyImpl() {
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
			case ForgePackage.DEPENDENCY__NAME:
				return getName();
			case ForgePackage.DEPENDENCY__VERSION_REQUIREMENT:
				return getVersionRequirement();
			case ForgePackage.DEPENDENCY__REPOSITORY:
				return getRepository();
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
			case ForgePackage.DEPENDENCY__NAME:
				return NAME_EDEFAULT == null
						? name != null
						: !NAME_EDEFAULT.equals(name);
			case ForgePackage.DEPENDENCY__VERSION_REQUIREMENT:
				return VERSION_REQUIREMENT_EDEFAULT == null
						? versionRequirement != null
						: !VERSION_REQUIREMENT_EDEFAULT.equals(versionRequirement);
			case ForgePackage.DEPENDENCY__REPOSITORY:
				return REPOSITORY_EDEFAULT == null
						? repository != null
						: !REPOSITORY_EDEFAULT.equals(repository);
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
			case ForgePackage.DEPENDENCY__NAME:
				setName((String) newValue);
				return;
			case ForgePackage.DEPENDENCY__VERSION_REQUIREMENT:
				setVersionRequirement((String) newValue);
				return;
			case ForgePackage.DEPENDENCY__REPOSITORY:
				setRepository((URI) newValue);
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
			case ForgePackage.DEPENDENCY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ForgePackage.DEPENDENCY__VERSION_REQUIREMENT:
				setVersionRequirement(VERSION_REQUIREMENT_EDEFAULT);
				return;
			case ForgePackage.DEPENDENCY__REPOSITORY:
				setRepository(REPOSITORY_EDEFAULT);
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
	public URI getRepository() {
		return repository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getVersionRequirement() {
		return versionRequirement;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ForgePackage.DEPENDENCY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setRepository(URI newRepository) {
		URI oldRepository = repository;
		repository = newRepository;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, ForgePackage.DEPENDENCY__REPOSITORY, oldRepository, repository));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVersionRequirement(String newVersionRequirement) {
		String oldVersionRequirement = versionRequirement;
		versionRequirement = newVersionRequirement;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, ForgePackage.DEPENDENCY__VERSION_REQUIREMENT, oldVersionRequirement,
				versionRequirement));
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
		result.append(", versionRequirement: ");
		result.append(versionRequirement);
		result.append(", repository: ");
		result.append(repository);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ForgePackage.Literals.DEPENDENCY;
	}

} // DependencyImpl
