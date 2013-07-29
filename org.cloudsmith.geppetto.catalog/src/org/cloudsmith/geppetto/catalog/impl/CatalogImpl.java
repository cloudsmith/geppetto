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

import java.util.Collection;

import org.cloudsmith.geppetto.catalog.Catalog;
import org.cloudsmith.geppetto.catalog.CatalogEdge;
import org.cloudsmith.geppetto.catalog.CatalogFactory;
import org.cloudsmith.geppetto.catalog.CatalogMetadata;
import org.cloudsmith.geppetto.catalog.CatalogPackage;
import org.cloudsmith.geppetto.catalog.CatalogResource;
import org.cloudsmith.geppetto.catalog.util.CatalogJsonSerializer;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Catalog</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogImpl#getName <em>Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogImpl#getVersion <em>Version</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogImpl#getResources <em>Resources</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogImpl#getClasses <em>Classes</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogImpl#getMetadata <em>Metadata</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogImpl#getEdges <em>Edges</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CatalogImpl extends TaggableImpl implements Catalog {
	public static class JsonAdapter extends CatalogJsonSerializer.ContainerDeserializer<Catalog> implements
			JsonSerializer<Catalog> {

		private static java.lang.reflect.Type listOfClassesType = new TypeToken<EList<String>>() {
		}.getType();

		private static java.lang.reflect.Type listOfTagsType = new TypeToken<EList<String>>() {
		}.getType();

		private static java.lang.reflect.Type listOfResourcesType = new TypeToken<EList<CatalogResource>>() {
		}.getType();

		private static java.lang.reflect.Type listOfEdgesType = new TypeToken<EList<CatalogEdge>>() {
		}.getType();

		private static CatalogMetadata getMetadata(JsonObject jsonObj, String key, JsonDeserializationContext context) {
			JsonElement json = jsonObj.get(key);
			if(json == null)
				return null;
			CatalogMetadataImpl.JsonAdapter a = new CatalogMetadataImpl.JsonAdapter();
			return a.deserialize(json, CatalogMetadataImpl.class, context);

		}

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
		public Catalog deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			final Catalog result = CatalogFactory.eINSTANCE.createCatalog();
			JsonObject jsonObj = json.getAsJsonObject();

			// Check the document type
			String documentType = getString(jsonObj, "document_type");
			if(!"Catalog".equals(documentType))
				throw new IllegalArgumentException("JSON document must be of 'Catalog' type");

			result.setMetadata(getMetadata(jsonObj, "metadata", context));

			// all the data is under a 'data' key
			JsonElement data = jsonObj.get("data");
			if(data == null)
				return result;
			if(!(data instanceof JsonObject))
				throw new IllegalStateException("Document 'data' is not a single object");

			// continue serialization under data
			jsonObj = data.getAsJsonObject();
			result.setName(getString(jsonObj, "name"));
			result.setVersion(getString(jsonObj, "version"));

			json = jsonObj.get("tags");
			if(json != null)
				deserializeInto(json, result.getTags(), String.class, context);

			json = jsonObj.get("classes");
			if(json != null)
				deserializeInto(json, result.getClasses(), String.class, context);

			json = jsonObj.get("resources");
			if(json != null)
				deserializeInto(json, result.getResources(), CatalogResourceImpl.class, context);

			json = jsonObj.get("edges");
			if(json != null)
				deserializeInto(json, result.getEdges(), CatalogEdgeImpl.class, context);

			return result;
		}

		@Override
		public JsonElement serialize(Catalog src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
			final JsonObject docResult = new JsonObject();
			final CatalogImpl cat = (CatalogImpl) src;
			final JsonObject result = new JsonObject();

			putString(docResult, "document_type", "Catalog");

			putString(result, "name", cat.getName());
			putString(result, "version", cat.getVersion());

			if(cat.classes != null)
				result.add("classes", context.serialize(cat.classes, listOfClassesType));

			if(cat.tags != null)
				result.add("tags", context.serialize(cat.tags, listOfTagsType));

			if(cat.resources != null)
				result.add("resources", context.serialize(cat.resources, listOfResourcesType));

			if(cat.edges != null)
				result.add("edges", context.serialize(cat.edges, listOfEdgesType));

			docResult.add("data", result);
			if(cat.getMetadata() != null)
				docResult.add("metadata", new CatalogMetadataImpl.JsonAdapter().serialize(
					cat.getMetadata(), CatalogMetadataImpl.class, context));

			return docResult;
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
	protected String version = VERSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getResources() <em>Resources</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getResources()
	 * @generated
	 * @ordered
	 */
	protected EList<CatalogResource> resources;

	/**
	 * The cached value of the '{@link #getClasses() <em>Classes</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getClasses()
	 * @generated
	 * @ordered
	 */
	protected EList<String> classes;

	/**
	 * The cached value of the '{@link #getMetadata() <em>Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getMetadata()
	 * @generated
	 * @ordered
	 */
	protected CatalogMetadata metadata;

	/**
	 * The cached value of the '{@link #getEdges() <em>Edges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getEdges()
	 * @generated
	 * @ordered
	 */
	protected EList<CatalogEdge> edges;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CatalogImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetMetadata(CatalogMetadata newMetadata, NotificationChain msgs) {
		CatalogMetadata oldMetadata = metadata;
		metadata = newMetadata;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, CatalogPackage.CATALOG__METADATA, oldMetadata, newMetadata);
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
			case CatalogPackage.CATALOG__NAME:
				return getName();
			case CatalogPackage.CATALOG__VERSION:
				return getVersion();
			case CatalogPackage.CATALOG__RESOURCES:
				return getResources();
			case CatalogPackage.CATALOG__CLASSES:
				return getClasses();
			case CatalogPackage.CATALOG__METADATA:
				return getMetadata();
			case CatalogPackage.CATALOG__EDGES:
				return getEdges();
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
			case CatalogPackage.CATALOG__RESOURCES:
				return ((InternalEList<?>) getResources()).basicRemove(otherEnd, msgs);
			case CatalogPackage.CATALOG__METADATA:
				return basicSetMetadata(null, msgs);
			case CatalogPackage.CATALOG__EDGES:
				return ((InternalEList<?>) getEdges()).basicRemove(otherEnd, msgs);
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
			case CatalogPackage.CATALOG__NAME:
				return NAME_EDEFAULT == null
						? name != null
						: !NAME_EDEFAULT.equals(name);
			case CatalogPackage.CATALOG__VERSION:
				return VERSION_EDEFAULT == null
						? version != null
						: !VERSION_EDEFAULT.equals(version);
			case CatalogPackage.CATALOG__RESOURCES:
				return resources != null && !resources.isEmpty();
			case CatalogPackage.CATALOG__CLASSES:
				return classes != null && !classes.isEmpty();
			case CatalogPackage.CATALOG__METADATA:
				return metadata != null;
			case CatalogPackage.CATALOG__EDGES:
				return edges != null && !edges.isEmpty();
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
			case CatalogPackage.CATALOG__NAME:
				setName((String) newValue);
				return;
			case CatalogPackage.CATALOG__VERSION:
				setVersion((String) newValue);
				return;
			case CatalogPackage.CATALOG__RESOURCES:
				getResources().clear();
				getResources().addAll((Collection<? extends CatalogResource>) newValue);
				return;
			case CatalogPackage.CATALOG__CLASSES:
				getClasses().clear();
				getClasses().addAll((Collection<? extends String>) newValue);
				return;
			case CatalogPackage.CATALOG__METADATA:
				setMetadata((CatalogMetadata) newValue);
				return;
			case CatalogPackage.CATALOG__EDGES:
				getEdges().clear();
				getEdges().addAll((Collection<? extends CatalogEdge>) newValue);
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
		return CatalogPackage.Literals.CATALOG;
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
			case CatalogPackage.CATALOG__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CatalogPackage.CATALOG__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case CatalogPackage.CATALOG__RESOURCES:
				getResources().clear();
				return;
			case CatalogPackage.CATALOG__CLASSES:
				getClasses().clear();
				return;
			case CatalogPackage.CATALOG__METADATA:
				setMetadata((CatalogMetadata) null);
				return;
			case CatalogPackage.CATALOG__EDGES:
				getEdges().clear();
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
	public EList<String> getClasses() {
		if(classes == null) {
			classes = new EDataTypeUniqueEList<String>(String.class, this, CatalogPackage.CATALOG__CLASSES);
		}
		return classes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<CatalogEdge> getEdges() {
		if(edges == null) {
			edges = new EObjectContainmentEList<CatalogEdge>(CatalogEdge.class, this, CatalogPackage.CATALOG__EDGES);
		}
		return edges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CatalogMetadata getMetadata() {
		return metadata;
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
	public EList<CatalogResource> getResources() {
		if(resources == null) {
			resources = new EObjectContainmentEList<CatalogResource>(
				CatalogResource.class, this, CatalogPackage.CATALOG__RESOURCES);
		}
		return resources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setMetadata(CatalogMetadata newMetadata) {
		if(newMetadata != metadata) {
			NotificationChain msgs = null;
			if(metadata != null)
				msgs = ((InternalEObject) metadata).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						CatalogPackage.CATALOG__METADATA, null, msgs);
			if(newMetadata != null)
				msgs = ((InternalEObject) newMetadata).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						CatalogPackage.CATALOG__METADATA, null, msgs);
			msgs = basicSetMetadata(newMetadata, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, CatalogPackage.CATALOG__METADATA, newMetadata, newMetadata));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CatalogPackage.CATALOG__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CatalogPackage.CATALOG__VERSION, oldVersion, version));
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
		result.append(", version: ");
		result.append(version);
		result.append(", classes: ");
		result.append(classes);
		result.append(')');
		return result.toString();
	}

} // CatalogImpl
