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
package org.cloudsmith.geppetto.catalog.impl;

import java.util.Collection;
import java.util.Map;

import org.cloudsmith.geppetto.catalog.CatalogFactory;
import org.cloudsmith.geppetto.catalog.CatalogPackage;
import org.cloudsmith.geppetto.catalog.CatalogResource;
import org.cloudsmith.geppetto.catalog.CatalogResourceParameter;
import org.cloudsmith.geppetto.catalog.util.CatalogJsonSerializer;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogResourceImpl#getFile <em>File</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogResourceImpl#getLine <em>Line</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogResourceImpl#getType <em>Type</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogResourceImpl#getTitle <em>Title</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogResourceImpl#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogResourceImpl#isVirtual <em>Virtual</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.catalog.impl.CatalogResourceImpl#isExported <em>Exported</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CatalogResourceImpl extends TaggableImpl implements CatalogResource {
	public static class JsonAdapter extends CatalogJsonSerializer.ContainerDeserializer<CatalogResource> implements
			JsonSerializer<CatalogResource> {

		private static java.lang.reflect.Type listOfParametersType = new TypeToken<EList<CatalogResourceParameter>>() {
		}.getType();

		private static java.lang.reflect.Type listOfTagsType = new TypeToken<EList<String>>() {
		}.getType();

		private static java.lang.reflect.Type listOfStringType = new TypeToken<EList<String>>() {
		}.getType();

		private static boolean getBoolean(JsonObject jsonObj, String key) {
			JsonElement json = jsonObj.get(key);
			if(json == null)
				return false;
			return json.getAsBoolean();
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

		private static void putBoolean(JsonObject jsonObj, String key, boolean value) {
			jsonObj.addProperty(key, value);
		}

		private static void putString(JsonObject jsonObj, String key, String value) {
			if(value == null)
				value = "";
			jsonObj.addProperty(key, value);
		}

		@Override
		public CatalogResource deserialize(JsonElement json, java.lang.reflect.Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {
			final CatalogResource result = CatalogFactory.eINSTANCE.createCatalogResource();
			JsonObject jsonObj = json.getAsJsonObject();

			result.setFile(getString(jsonObj, "file"));
			result.setLine(getString(jsonObj, "line"));
			result.setType(getString(jsonObj, "type"));
			result.setTitle(getString(jsonObj, "title"));
			result.setExported(getBoolean(jsonObj, "exported"));
			result.setVirtual(getBoolean(jsonObj, "virtual"));

			json = jsonObj.get("tags");
			if(json != null)
				deserializeInto(json, result.getTags(), String.class, context);

			json = jsonObj.get("parameters");
			// if(json != null) {
			// deserializeInto(json, result.getParameters(), CatalogResourceParameter.class, context);
			// }
			if(json != null) {
				JsonObject parameterHash = json.getAsJsonObject();
				EList<CatalogResourceParameter> pList = result.getParameters();
				for(Map.Entry<String, JsonElement> entry : parameterHash.entrySet()) {
					CatalogResourceParameter rp = CatalogFactory.eINSTANCE.createCatalogResourceParameter();
					rp.setName(entry.getKey());
					if(entry.getValue().isJsonArray()) {
						deserializeInto(entry.getValue(), rp.getValue(), String.class, context);
						// StringBuilder values = new StringBuilder();
						// JsonArray multiValue = entry.getValue().getAsJsonArray();
						// for(JsonElement element : multiValue)
						// values.append(element.getAsString());
						// values.append(", ");
						// if(values.length() > 1)
						// values.setLength(values.length() - 2);
						// rp.setValue(values.toString());
					}
					else if(entry.getValue().isJsonPrimitive()) {
						rp.getValue().add(entry.getValue().getAsString());
					}
					else if(entry.getValue().isJsonNull())
						rp.getValue().add("(null)");
					else
						rp.getValue().add("DATA"); // don't know how to deserialize in this case, could be anything
					pList.add(rp);
				}

			}

			return result;
		}

		@Override
		public JsonElement serialize(CatalogResource src, java.lang.reflect.Type typeOfSrc,
				JsonSerializationContext context) {
			final JsonObject result = new JsonObject();
			final CatalogResourceImpl cat = (CatalogResourceImpl) src;

			putString(result, "file", cat.getFile());
			putString(result, "line", cat.getLine());
			putString(result, "type", cat.getType());
			putString(result, "title", cat.getTitle());
			putBoolean(result, "exported", cat.isExported());
			putBoolean(result, "virtual", cat.isVirtual());

			if(cat.parameters != null) {
				JsonObject jsonParameters = new JsonObject();
				for(CatalogResourceParameter p : cat.parameters) {
					JsonElement valueElement;
					if(p.getValue().size() == 1)
						valueElement = new JsonPrimitive(p.getValue().get(0));
					else
						valueElement = context.serialize(p.getValue(), listOfStringType);
					jsonParameters.add(p.getName(), valueElement);
				}
				result.add("parameters", jsonParameters);
			}

			if(cat.tags != null)
				result.add("tags", context.serialize(cat.tags, listOfTagsType));

			return result;
		}
	}

	/**
	 * The default value of the '{@link #getFile() <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
	protected static final String FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFile() <em>File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFile()
	 * @generated
	 * @ordered
	 */
	protected String file = FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLine() <em>Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLine()
	 * @generated
	 * @ordered
	 */
	protected static final String LINE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLine() <em>Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLine()
	 * @generated
	 * @ordered
	 */
	protected String line = LINE_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected String title = TITLE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<CatalogResourceParameter> parameters;

	/**
	 * The default value of the '{@link #isVirtual() <em>Virtual</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isVirtual()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VIRTUAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isVirtual() <em>Virtual</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isVirtual()
	 * @generated
	 * @ordered
	 */
	protected boolean virtual = VIRTUAL_EDEFAULT;

	/**
	 * The default value of the '{@link #isExported() <em>Exported</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isExported()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EXPORTED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isExported() <em>Exported</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #isExported()
	 * @generated
	 * @ordered
	 */
	protected boolean exported = EXPORTED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CatalogResourceImpl() {
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
			case CatalogPackage.CATALOG_RESOURCE__FILE:
				return getFile();
			case CatalogPackage.CATALOG_RESOURCE__LINE:
				return getLine();
			case CatalogPackage.CATALOG_RESOURCE__TYPE:
				return getType();
			case CatalogPackage.CATALOG_RESOURCE__TITLE:
				return getTitle();
			case CatalogPackage.CATALOG_RESOURCE__PARAMETERS:
				return getParameters();
			case CatalogPackage.CATALOG_RESOURCE__VIRTUAL:
				return isVirtual();
			case CatalogPackage.CATALOG_RESOURCE__EXPORTED:
				return isExported();
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
			case CatalogPackage.CATALOG_RESOURCE__PARAMETERS:
				return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
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
			case CatalogPackage.CATALOG_RESOURCE__FILE:
				return FILE_EDEFAULT == null
						? file != null
						: !FILE_EDEFAULT.equals(file);
			case CatalogPackage.CATALOG_RESOURCE__LINE:
				return LINE_EDEFAULT == null
						? line != null
						: !LINE_EDEFAULT.equals(line);
			case CatalogPackage.CATALOG_RESOURCE__TYPE:
				return TYPE_EDEFAULT == null
						? type != null
						: !TYPE_EDEFAULT.equals(type);
			case CatalogPackage.CATALOG_RESOURCE__TITLE:
				return TITLE_EDEFAULT == null
						? title != null
						: !TITLE_EDEFAULT.equals(title);
			case CatalogPackage.CATALOG_RESOURCE__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case CatalogPackage.CATALOG_RESOURCE__VIRTUAL:
				return virtual != VIRTUAL_EDEFAULT;
			case CatalogPackage.CATALOG_RESOURCE__EXPORTED:
				return exported != EXPORTED_EDEFAULT;
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
			case CatalogPackage.CATALOG_RESOURCE__FILE:
				setFile((String) newValue);
				return;
			case CatalogPackage.CATALOG_RESOURCE__LINE:
				setLine((String) newValue);
				return;
			case CatalogPackage.CATALOG_RESOURCE__TYPE:
				setType((String) newValue);
				return;
			case CatalogPackage.CATALOG_RESOURCE__TITLE:
				setTitle((String) newValue);
				return;
			case CatalogPackage.CATALOG_RESOURCE__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends CatalogResourceParameter>) newValue);
				return;
			case CatalogPackage.CATALOG_RESOURCE__VIRTUAL:
				setVirtual((Boolean) newValue);
				return;
			case CatalogPackage.CATALOG_RESOURCE__EXPORTED:
				setExported((Boolean) newValue);
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
		return CatalogPackage.Literals.CATALOG_RESOURCE;
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
			case CatalogPackage.CATALOG_RESOURCE__FILE:
				setFile(FILE_EDEFAULT);
				return;
			case CatalogPackage.CATALOG_RESOURCE__LINE:
				setLine(LINE_EDEFAULT);
				return;
			case CatalogPackage.CATALOG_RESOURCE__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case CatalogPackage.CATALOG_RESOURCE__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case CatalogPackage.CATALOG_RESOURCE__PARAMETERS:
				getParameters().clear();
				return;
			case CatalogPackage.CATALOG_RESOURCE__VIRTUAL:
				setVirtual(VIRTUAL_EDEFAULT);
				return;
			case CatalogPackage.CATALOG_RESOURCE__EXPORTED:
				setExported(EXPORTED_EDEFAULT);
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
	public String getFile() {
		return file;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getLine() {
		return line;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<CatalogResourceParameter> getParameters() {
		if(parameters == null) {
			parameters = new EObjectContainmentEList<CatalogResourceParameter>(
				CatalogResourceParameter.class, this, CatalogPackage.CATALOG_RESOURCE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isExported() {
		return exported;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isVirtual() {
		return virtual;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setExported(boolean newExported) {
		boolean oldExported = exported;
		exported = newExported;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, CatalogPackage.CATALOG_RESOURCE__EXPORTED, oldExported, exported));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setFile(String newFile) {
		String oldFile = file;
		file = newFile;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CatalogPackage.CATALOG_RESOURCE__FILE, oldFile, file));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLine(String newLine) {
		String oldLine = line;
		line = newLine;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CatalogPackage.CATALOG_RESOURCE__LINE, oldLine, line));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setTitle(String newTitle) {
		String oldTitle = title;
		title = newTitle;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, CatalogPackage.CATALOG_RESOURCE__TITLE, oldTitle, title));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CatalogPackage.CATALOG_RESOURCE__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setVirtual(boolean newVirtual) {
		boolean oldVirtual = virtual;
		virtual = newVirtual;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, CatalogPackage.CATALOG_RESOURCE__VIRTUAL, oldVirtual, virtual));
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
		result.append(" (file: ");
		result.append(file);
		result.append(", line: ");
		result.append(line);
		result.append(", type: ");
		result.append(type);
		result.append(", title: ");
		result.append(title);
		result.append(", virtual: ");
		result.append(virtual);
		result.append(", exported: ");
		result.append(exported);
		result.append(')');
		return result.toString();
	}

} // CatalogResourceImpl
