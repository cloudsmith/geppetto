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
package org.cloudsmith.geppetto.catalog.impl;

import org.cloudsmith.geppetto.catalog.CatalogEdge;
import org.cloudsmith.geppetto.catalog.CatalogFactory;
import org.cloudsmith.geppetto.catalog.CatalogPackage;
import org.cloudsmith.geppetto.catalog.util.CatalogJsonSerializer;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogEdgeImpl#getTarget <em>Target</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogEdgeImpl#getSource <em>Source</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CatalogEdgeImpl extends EObjectImpl implements CatalogEdge {
	public static class JsonAdapter extends CatalogJsonSerializer.ContainerDeserializer<CatalogEdge> implements
			JsonSerializer<CatalogEdge> {

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
		public CatalogEdge deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			final CatalogEdge result = CatalogFactory.eINSTANCE.createCatalogEdge();
			JsonObject jsonObj = json.getAsJsonObject();

			result.setTarget(getString(jsonObj, "target"));
			result.setSource(getString(jsonObj, "source"));
			return result;
		}

		@Override
		public JsonElement serialize(CatalogEdge src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
			final JsonObject result = new JsonObject();
			final CatalogEdgeImpl cat = (CatalogEdgeImpl) src;

			putString(result, "target", cat.getTarget());
			putString(result, "soure", cat.getSource());

			return result;
		}
	}

	/**
	 * The default value of the '{@link #getTarget() <em>Target</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected String target = TARGET_EDEFAULT;

	/**
	 * The default value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected String source = SOURCE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CatalogEdgeImpl() {
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
			case CatalogPackage.CATALOG_EDGE__TARGET:
				return getTarget();
			case CatalogPackage.CATALOG_EDGE__SOURCE:
				return getSource();
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
			case CatalogPackage.CATALOG_EDGE__TARGET:
				return TARGET_EDEFAULT == null
						? target != null
						: !TARGET_EDEFAULT.equals(target);
			case CatalogPackage.CATALOG_EDGE__SOURCE:
				return SOURCE_EDEFAULT == null
						? source != null
						: !SOURCE_EDEFAULT.equals(source);
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
			case CatalogPackage.CATALOG_EDGE__TARGET:
				setTarget((String) newValue);
				return;
			case CatalogPackage.CATALOG_EDGE__SOURCE:
				setSource((String) newValue);
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
		return CatalogPackage.Literals.CATALOG_EDGE;
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
			case CatalogPackage.CATALOG_EDGE__TARGET:
				setTarget(TARGET_EDEFAULT);
				return;
			case CatalogPackage.CATALOG_EDGE__SOURCE:
				setSource(SOURCE_EDEFAULT);
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
	public String getSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSource(String newSource) {
		String oldSource = source;
		source = newSource;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, CatalogPackage.CATALOG_EDGE__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTarget(String newTarget) {
		String oldTarget = target;
		target = newTarget;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, CatalogPackage.CATALOG_EDGE__TARGET, oldTarget, target));
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
		result.append(" (target: ");
		result.append(target);
		result.append(", source: ");
		result.append(source);
		result.append(')');
		return result.toString();
	}

} // CatalogEdgeImpl
