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

import java.lang.reflect.Type;
import java.net.URI;

import org.cloudsmith.geppetto.forge.Dependency;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgePackage;
import org.cloudsmith.geppetto.forge.VersionRequirement;
import org.cloudsmith.geppetto.forge.util.JsonUtils;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dependency</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.DependencyImpl#getName <em>Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.DependencyImpl#getRepository <em>Repository</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.DependencyImpl#getVersionRequirement <em>Version Requirement</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class DependencyImpl extends EObjectImpl implements Dependency {
	public static class JsonAdapter extends JsonUtils.ContainerDeserializer<Dependency> implements
			JsonSerializer<Dependency> {

		private static String getString(JsonObject jsonObj, String key) {
			JsonElement json = jsonObj.get(key);
			if(json == null)
				return null;
			String value = json.getAsString();
			return value.length() == 0
					? null
					: value;
		}

		private static void putString(JsonObject jsonObj, String key, String value) {
			if(value == null)
				value = "";
			jsonObj.addProperty(key, value);
		}

		@Override
		public Dependency deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			Dependency result = ForgeFactory.eINSTANCE.createDependency();
			JsonObject jsonObj = json.getAsJsonObject();
			result.setName(getString(jsonObj, "name"));
			String repo = getString(jsonObj, "repository");
			if(repo != null)
				result.setRepository(URI.create(repo));

			String vr = getString(jsonObj, "version_requirement");
			if(vr == null)
				// Test the camel case too since they were serialized them that way due to bug #302
				vr = getString(jsonObj, "versionRequirement");

			result.setVersionRequirement(VersionRequirementImpl.parseVersionRequirement(vr));
			return result;
		}

		@Override
		public JsonElement serialize(Dependency src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject result = new JsonObject();
			putString(result, "name", src.getName());
			URI repository = src.getRepository();
			if(repository != null)
				putString(result, "repository", repository.toString());
			VersionRequirement vr = src.getVersionRequirement();
			if(vr != null)
				putString(result, "version_requirement", vr.toString());
			return result;
		}
	}

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
	 * The cached value of the '{@link #getVersionRequirement() <em>Version Requirement</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getVersionRequirement()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected VersionRequirement versionRequirement;

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
	public NotificationChain basicSetVersionRequirement(VersionRequirement newVersionRequirement, NotificationChain msgs) {
		VersionRequirement oldVersionRequirement = versionRequirement;
		versionRequirement = newVersionRequirement;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, ForgePackage.DEPENDENCY__VERSION_REQUIREMENT, oldVersionRequirement,
				newVersionRequirement);
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
			case ForgePackage.DEPENDENCY__NAME:
				return getName();
			case ForgePackage.DEPENDENCY__REPOSITORY:
				return getRepository();
			case ForgePackage.DEPENDENCY__VERSION_REQUIREMENT:
				return getVersionRequirement();
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
			case ForgePackage.DEPENDENCY__VERSION_REQUIREMENT:
				return basicSetVersionRequirement(null, msgs);
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
			case ForgePackage.DEPENDENCY__NAME:
				return NAME_EDEFAULT == null
						? name != null
						: !NAME_EDEFAULT.equals(name);
			case ForgePackage.DEPENDENCY__REPOSITORY:
				return REPOSITORY_EDEFAULT == null
						? repository != null
						: !REPOSITORY_EDEFAULT.equals(repository);
			case ForgePackage.DEPENDENCY__VERSION_REQUIREMENT:
				return versionRequirement != null;
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
			case ForgePackage.DEPENDENCY__REPOSITORY:
				setRepository((URI) newValue);
				return;
			case ForgePackage.DEPENDENCY__VERSION_REQUIREMENT:
				setVersionRequirement((VersionRequirement) newValue);
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
		return ForgePackage.Literals.DEPENDENCY;
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
			case ForgePackage.DEPENDENCY__REPOSITORY:
				setRepository(REPOSITORY_EDEFAULT);
				return;
			case ForgePackage.DEPENDENCY__VERSION_REQUIREMENT:
				setVersionRequirement((VersionRequirement) null);
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
	public VersionRequirement getVersionRequirement() {
		return versionRequirement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public boolean matches(String name, String version) {
		if((name == null || getName() == null) && name != getName())
			return false;

		if(!name.equals(getName()))
			return false;

		if(versionRequirement == null)
			return true;

		return versionRequirement.matches(version);
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
	public void setVersionRequirement(VersionRequirement newVersionRequirement) {
		if(newVersionRequirement != versionRequirement) {
			NotificationChain msgs = null;
			if(versionRequirement != null)
				msgs = ((InternalEObject) versionRequirement).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						ForgePackage.DEPENDENCY__VERSION_REQUIREMENT, null, msgs);
			if(newVersionRequirement != null)
				msgs = ((InternalEObject) newVersionRequirement).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						ForgePackage.DEPENDENCY__VERSION_REQUIREMENT, null, msgs);
			msgs = basicSetVersionRequirement(newVersionRequirement, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, ForgePackage.DEPENDENCY__VERSION_REQUIREMENT, newVersionRequirement,
				newVersionRequirement));
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
		result.append(", repository: ");
		result.append(repository);
		result.append(')');
		return result.toString();
	}

} // DependencyImpl
