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
package org.cloudsmith.geppetto.pp.pptp.impl;

import java.util.Collection;
import org.cloudsmith.geppetto.pp.pptp.Function;
import org.cloudsmith.geppetto.pp.pptp.MetaType;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.cloudsmith.geppetto.pp.pptp.TargetEntry;

import org.cloudsmith.geppetto.pp.pptp.Type;
import org.cloudsmith.geppetto.pp.pptp.TypeFragment;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Target Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.impl.TargetEntryImpl#getDescription <em>Description</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.impl.TargetEntryImpl#getFunctions <em>Functions</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.impl.TargetEntryImpl#getTypes <em>Types</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.impl.TargetEntryImpl#getVersion <em>Version</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.impl.TargetEntryImpl#getTypeFragments <em>Type Fragments</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.impl.TargetEntryImpl#getMetaType <em>Meta Type</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.impl.TargetEntryImpl#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public abstract class TargetEntryImpl extends EObjectImpl implements TargetEntry {
	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getFunctions() <em>Functions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFunctions()
	 * @generated
	 * @ordered
	 */
	protected EList<Function> functions;

	/**
	 * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<Type> types;

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
	 * The cached value of the '{@link #getTypeFragments() <em>Type Fragments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTypeFragments()
	 * @generated
	 * @ordered
	 */
	protected EList<TypeFragment> typeFragments;

	/**
	 * The cached value of the '{@link #getMetaType() <em>Meta Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getMetaType()
	 * @generated
	 * @ordered
	 */
	protected MetaType metaType;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TargetEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetMetaType(MetaType newMetaType, NotificationChain msgs) {
		MetaType oldMetaType = metaType;
		metaType = newMetaType;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPTPPackage.TARGET_ENTRY__META_TYPE, oldMetaType, newMetaType);
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
			case PPTPPackage.TARGET_ENTRY__DESCRIPTION:
				return getDescription();
			case PPTPPackage.TARGET_ENTRY__FUNCTIONS:
				return getFunctions();
			case PPTPPackage.TARGET_ENTRY__TYPES:
				return getTypes();
			case PPTPPackage.TARGET_ENTRY__VERSION:
				return getVersion();
			case PPTPPackage.TARGET_ENTRY__TYPE_FRAGMENTS:
				return getTypeFragments();
			case PPTPPackage.TARGET_ENTRY__META_TYPE:
				return getMetaType();
			case PPTPPackage.TARGET_ENTRY__LABEL:
				return getLabel();
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
			case PPTPPackage.TARGET_ENTRY__FUNCTIONS:
				return ((InternalEList<?>) getFunctions()).basicRemove(otherEnd, msgs);
			case PPTPPackage.TARGET_ENTRY__TYPES:
				return ((InternalEList<?>) getTypes()).basicRemove(otherEnd, msgs);
			case PPTPPackage.TARGET_ENTRY__TYPE_FRAGMENTS:
				return ((InternalEList<?>) getTypeFragments()).basicRemove(otherEnd, msgs);
			case PPTPPackage.TARGET_ENTRY__META_TYPE:
				return basicSetMetaType(null, msgs);
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
			case PPTPPackage.TARGET_ENTRY__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null
						? description != null
						: !DESCRIPTION_EDEFAULT.equals(description);
			case PPTPPackage.TARGET_ENTRY__FUNCTIONS:
				return functions != null && !functions.isEmpty();
			case PPTPPackage.TARGET_ENTRY__TYPES:
				return types != null && !types.isEmpty();
			case PPTPPackage.TARGET_ENTRY__VERSION:
				return VERSION_EDEFAULT == null
						? version != null
						: !VERSION_EDEFAULT.equals(version);
			case PPTPPackage.TARGET_ENTRY__TYPE_FRAGMENTS:
				return typeFragments != null && !typeFragments.isEmpty();
			case PPTPPackage.TARGET_ENTRY__META_TYPE:
				return metaType != null;
			case PPTPPackage.TARGET_ENTRY__LABEL:
				return LABEL_EDEFAULT == null
						? label != null
						: !LABEL_EDEFAULT.equals(label);
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
			case PPTPPackage.TARGET_ENTRY__DESCRIPTION:
				setDescription((String) newValue);
				return;
			case PPTPPackage.TARGET_ENTRY__FUNCTIONS:
				getFunctions().clear();
				getFunctions().addAll((Collection<? extends Function>) newValue);
				return;
			case PPTPPackage.TARGET_ENTRY__TYPES:
				getTypes().clear();
				getTypes().addAll((Collection<? extends Type>) newValue);
				return;
			case PPTPPackage.TARGET_ENTRY__VERSION:
				setVersion((String) newValue);
				return;
			case PPTPPackage.TARGET_ENTRY__TYPE_FRAGMENTS:
				getTypeFragments().clear();
				getTypeFragments().addAll((Collection<? extends TypeFragment>) newValue);
				return;
			case PPTPPackage.TARGET_ENTRY__META_TYPE:
				setMetaType((MetaType) newValue);
				return;
			case PPTPPackage.TARGET_ENTRY__LABEL:
				setLabel((String) newValue);
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
		return PPTPPackage.Literals.TARGET_ENTRY;
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
			case PPTPPackage.TARGET_ENTRY__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case PPTPPackage.TARGET_ENTRY__FUNCTIONS:
				getFunctions().clear();
				return;
			case PPTPPackage.TARGET_ENTRY__TYPES:
				getTypes().clear();
				return;
			case PPTPPackage.TARGET_ENTRY__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case PPTPPackage.TARGET_ENTRY__TYPE_FRAGMENTS:
				getTypeFragments().clear();
				return;
			case PPTPPackage.TARGET_ENTRY__META_TYPE:
				setMetaType((MetaType) null);
				return;
			case PPTPPackage.TARGET_ENTRY__LABEL:
				setLabel(LABEL_EDEFAULT);
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
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Function> getFunctions() {
		if(functions == null) {
			functions = new EObjectContainmentEList<Function>(Function.class, this, PPTPPackage.TARGET_ENTRY__FUNCTIONS);
		}
		return functions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MetaType getMetaType() {
		return metaType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<TypeFragment> getTypeFragments() {
		if(typeFragments == null) {
			typeFragments = new EObjectContainmentEList<TypeFragment>(
				TypeFragment.class, this, PPTPPackage.TARGET_ENTRY__TYPE_FRAGMENTS);
		}
		return typeFragments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Type> getTypes() {
		if(types == null) {
			types = new EObjectContainmentEList<Type>(Type.class, this, PPTPPackage.TARGET_ENTRY__TYPES);
		}
		return types;
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
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPTPPackage.TARGET_ENTRY__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PPTPPackage.TARGET_ENTRY__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setMetaType(MetaType newMetaType) {
		if(newMetaType != metaType) {
			NotificationChain msgs = null;
			if(metaType != null)
				msgs = ((InternalEObject) metaType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPTPPackage.TARGET_ENTRY__META_TYPE, null, msgs);
			if(newMetaType != null)
				msgs = ((InternalEObject) newMetaType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPTPPackage.TARGET_ENTRY__META_TYPE, null, msgs);
			msgs = basicSetMetaType(newMetaType, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPTPPackage.TARGET_ENTRY__META_TYPE, newMetaType, newMetaType));
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
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPTPPackage.TARGET_ENTRY__VERSION, oldVersion, version));
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
		result.append(" (description: ");
		result.append(description);
		result.append(", version: ");
		result.append(version);
		result.append(", label: ");
		result.append(label);
		result.append(')');
		return result.toString();
	}

} // TargetEntryImpl
