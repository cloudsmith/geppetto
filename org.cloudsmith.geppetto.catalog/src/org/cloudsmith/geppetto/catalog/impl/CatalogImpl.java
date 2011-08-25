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

import org.cloudsmith.geppetto.catalog.Catalog;
import org.cloudsmith.geppetto.catalog.CatalogEdge;
import org.cloudsmith.geppetto.catalog.CatalogMetadata;
import org.cloudsmith.geppetto.catalog.CatalogPackage;
import org.cloudsmith.geppetto.catalog.CatalogResource;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

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
