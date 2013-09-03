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
package com.puppetlabs.geppetto.catalog.impl;

import java.util.Collection;

import com.puppetlabs.geppetto.catalog.CatalogFactory;
import com.puppetlabs.geppetto.catalog.CatalogPackage;
import com.puppetlabs.geppetto.catalog.CatalogResourceParameter;
import com.puppetlabs.geppetto.catalog.util.CatalogJsonSerializer;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource Parameter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link com.puppetlabs.geppetto.catalog.impl.CatalogResourceParameterImpl#getName <em>Name</em>}</li>
 * <li>{@link com.puppetlabs.geppetto.catalog.impl.CatalogResourceParameterImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CatalogResourceParameterImpl extends EObjectImpl implements CatalogResourceParameter {
	/**
	 * TODO: This serializer/deserializer is broken (and unused).
	 * 
	 */
	public static class JsonAdapter extends CatalogJsonSerializer.ContainerDeserializer<CatalogResourceParameter>
			implements JsonSerializer<CatalogResourceParameter> {
		private static java.lang.reflect.Type listOfStringType = new TypeToken<EList<String>>() {
		}.getType();

		private static String getString(JsonObject jsonObj, String key) {
			JsonElement json = jsonObj.get(key);
			if(json == null)
				return null;
			String value = json.getAsString();

			// unset values are null, not empty strings
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
		public CatalogResourceParameter deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			final CatalogResourceParameter result = CatalogFactory.eINSTANCE.createCatalogResourceParameter();
			JsonObject jsonObj = json.getAsJsonObject();

			result.setName(getString(jsonObj, "name"));
			JsonObject valueObj = jsonObj.getAsJsonObject("value");
			if(valueObj.isJsonArray())
				deserializeInto(valueObj, result.getValue(), String.class, context);
			else
				result.getValue().add(valueObj.getAsString());

			return result;
		}

		@Override
		public JsonElement serialize(CatalogResourceParameter src, java.lang.reflect.Type typeOfSrc,
				JsonSerializationContext context) {
			final JsonObject result = new JsonObject();
			final CatalogResourceParameterImpl cat = (CatalogResourceParameterImpl) src;

			putString(result, "name", cat.getName());
			if(cat.getValue().size() == 1)
				putString(result, "value", cat.getValue().get(0));
			else
				result.add("value", context.serialize(cat.getValue(), listOfStringType));

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
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected EList<String> value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CatalogResourceParameterImpl() {
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
			case CatalogPackage.CATALOG_RESOURCE_PARAMETER__NAME:
				return getName();
			case CatalogPackage.CATALOG_RESOURCE_PARAMETER__VALUE:
				return getValue();
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
			case CatalogPackage.CATALOG_RESOURCE_PARAMETER__NAME:
				return NAME_EDEFAULT == null
						? name != null
						: !NAME_EDEFAULT.equals(name);
			case CatalogPackage.CATALOG_RESOURCE_PARAMETER__VALUE:
				return value != null && !value.isEmpty();
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
			case CatalogPackage.CATALOG_RESOURCE_PARAMETER__NAME:
				setName((String) newValue);
				return;
			case CatalogPackage.CATALOG_RESOURCE_PARAMETER__VALUE:
				getValue().clear();
				getValue().addAll((Collection<? extends String>) newValue);
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
		return CatalogPackage.Literals.CATALOG_RESOURCE_PARAMETER;
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
			case CatalogPackage.CATALOG_RESOURCE_PARAMETER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CatalogPackage.CATALOG_RESOURCE_PARAMETER__VALUE:
				getValue().clear();
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
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<String> getValue() {
		if(value == null) {
			value = new EDataTypeUniqueEList<String>(
				String.class, this, CatalogPackage.CATALOG_RESOURCE_PARAMETER__VALUE);
		}
		return value;
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
				this, Notification.SET, CatalogPackage.CATALOG_RESOURCE_PARAMETER__NAME, oldName, name));
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
		result.append(", value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

} // CatalogResourceParameterImpl
