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
package org.cloudsmith.geppetto.pp.impl;

import org.cloudsmith.geppetto.pp.OWS;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Manifest</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.PuppetManifestImpl#getLeadingSpaceAndComments <em>Leading Space And Comments</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class PuppetManifestImpl extends ExpressionBlockImpl implements PuppetManifest {
	/**
	 * The cached value of the '{@link #getLeadingSpaceAndComments() <em>Leading Space And Comments</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLeadingSpaceAndComments()
	 * @generated
	 * @ordered
	 */
	protected OWS leadingSpaceAndComments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected PuppetManifestImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetLeadingSpaceAndComments(OWS newLeadingSpaceAndComments, NotificationChain msgs) {
		OWS oldLeadingSpaceAndComments = leadingSpaceAndComments;
		leadingSpaceAndComments = newLeadingSpaceAndComments;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.PUPPET_MANIFEST__LEADING_SPACE_AND_COMMENTS,
				oldLeadingSpaceAndComments, newLeadingSpaceAndComments);
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
			case PPPackage.PUPPET_MANIFEST__LEADING_SPACE_AND_COMMENTS:
				return getLeadingSpaceAndComments();
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
			case PPPackage.PUPPET_MANIFEST__LEADING_SPACE_AND_COMMENTS:
				return basicSetLeadingSpaceAndComments(null, msgs);
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
			case PPPackage.PUPPET_MANIFEST__LEADING_SPACE_AND_COMMENTS:
				return leadingSpaceAndComments != null;
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
			case PPPackage.PUPPET_MANIFEST__LEADING_SPACE_AND_COMMENTS:
				setLeadingSpaceAndComments((OWS) newValue);
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
		return PPPackage.Literals.PUPPET_MANIFEST;
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
			case PPPackage.PUPPET_MANIFEST__LEADING_SPACE_AND_COMMENTS:
				setLeadingSpaceAndComments((OWS) null);
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
	public OWS getLeadingSpaceAndComments() {
		return leadingSpaceAndComments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLeadingSpaceAndComments(OWS newLeadingSpaceAndComments) {
		if(newLeadingSpaceAndComments != leadingSpaceAndComments) {
			NotificationChain msgs = null;
			if(leadingSpaceAndComments != null)
				msgs = ((InternalEObject) leadingSpaceAndComments).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.PUPPET_MANIFEST__LEADING_SPACE_AND_COMMENTS, null, msgs);
			if(newLeadingSpaceAndComments != null)
				msgs = ((InternalEObject) newLeadingSpaceAndComments).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.PUPPET_MANIFEST__LEADING_SPACE_AND_COMMENTS, null, msgs);
			msgs = basicSetLeadingSpaceAndComments(newLeadingSpaceAndComments, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.PUPPET_MANIFEST__LEADING_SPACE_AND_COMMENTS,
				newLeadingSpaceAndComments, newLeadingSpaceAndComments));
	}

} // PuppetManifestImpl
