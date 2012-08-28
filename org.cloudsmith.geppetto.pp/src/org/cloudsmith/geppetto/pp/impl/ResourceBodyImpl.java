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

import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource Body</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.cloudsmith.geppetto.pp.impl.ResourceBodyImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.cloudsmith.geppetto.pp.impl.ResourceBodyImpl#getNameExpr <em>Name Expr</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ResourceBodyImpl extends EObjectImpl implements ResourceBody {
	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected AttributeOperations attributes;

	/**
	 * The cached value of the '{@link #getNameExpr() <em>Name Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameExpr()
	 * @generated
	 * @ordered
	 */
	protected Expression nameExpr;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ResourceBodyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAttributes(AttributeOperations newAttributes, NotificationChain msgs) {
		AttributeOperations oldAttributes = attributes;
		attributes = newAttributes;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.RESOURCE_BODY__ATTRIBUTES, oldAttributes, newAttributes);
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
	 * @generated
	 */
	public NotificationChain basicSetNameExpr(Expression newNameExpr, NotificationChain msgs) {
		Expression oldNameExpr = nameExpr;
		nameExpr = newNameExpr;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.RESOURCE_BODY__NAME_EXPR, oldNameExpr, newNameExpr);
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
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch(featureID) {
			case PPPackage.RESOURCE_BODY__ATTRIBUTES:
				return getAttributes();
			case PPPackage.RESOURCE_BODY__NAME_EXPR:
				return getNameExpr();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch(featureID) {
			case PPPackage.RESOURCE_BODY__ATTRIBUTES:
				return basicSetAttributes(null, msgs);
			case PPPackage.RESOURCE_BODY__NAME_EXPR:
				return basicSetNameExpr(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch(featureID) {
			case PPPackage.RESOURCE_BODY__ATTRIBUTES:
				return attributes != null;
			case PPPackage.RESOURCE_BODY__NAME_EXPR:
				return nameExpr != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch(featureID) {
			case PPPackage.RESOURCE_BODY__ATTRIBUTES:
				setAttributes((AttributeOperations) newValue);
				return;
			case PPPackage.RESOURCE_BODY__NAME_EXPR:
				setNameExpr((Expression) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PPPackage.Literals.RESOURCE_BODY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch(featureID) {
			case PPPackage.RESOURCE_BODY__ATTRIBUTES:
				setAttributes((AttributeOperations) null);
				return;
			case PPPackage.RESOURCE_BODY__NAME_EXPR:
				setNameExpr((Expression) null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeOperations getAttributes() {
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getNameExpr() {
		return nameExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAttributes(AttributeOperations newAttributes) {
		if(newAttributes != attributes) {
			NotificationChain msgs = null;
			if(attributes != null)
				msgs = ((InternalEObject) attributes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.RESOURCE_BODY__ATTRIBUTES, null, msgs);
			if(newAttributes != null)
				msgs = ((InternalEObject) newAttributes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.RESOURCE_BODY__ATTRIBUTES, null, msgs);
			msgs = basicSetAttributes(newAttributes, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.RESOURCE_BODY__ATTRIBUTES, newAttributes, newAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameExpr(Expression newNameExpr) {
		if(newNameExpr != nameExpr) {
			NotificationChain msgs = null;
			if(nameExpr != null)
				msgs = ((InternalEObject) nameExpr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.RESOURCE_BODY__NAME_EXPR, null, msgs);
			if(newNameExpr != null)
				msgs = ((InternalEObject) newNameExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.RESOURCE_BODY__NAME_EXPR, null, msgs);
			msgs = basicSetNameExpr(newNameExpr, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.RESOURCE_BODY__NAME_EXPR, newNameExpr, newNameExpr));
	}

} // ResourceBodyImpl
