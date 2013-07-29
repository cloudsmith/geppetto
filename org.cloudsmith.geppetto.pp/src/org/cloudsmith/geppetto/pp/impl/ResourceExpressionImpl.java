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
package org.cloudsmith.geppetto.pp.impl;

import java.util.Collection;

import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.ResourceExpressionImpl#getResourceExpr <em>Resource Expr</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.ResourceExpressionImpl#getResourceData <em>Resource Data</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ResourceExpressionImpl extends ExpressionImpl implements ResourceExpression {
	/**
	 * The cached value of the '{@link #getResourceExpr() <em>Resource Expr</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getResourceExpr()
	 * @generated
	 * @ordered
	 */
	protected Expression resourceExpr;

	/**
	 * The cached value of the '{@link #getResourceData() <em>Resource Data</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getResourceData()
	 * @generated
	 * @ordered
	 */
	protected EList<ResourceBody> resourceData;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ResourceExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetResourceExpr(Expression newResourceExpr, NotificationChain msgs) {
		Expression oldResourceExpr = resourceExpr;
		resourceExpr = newResourceExpr;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.RESOURCE_EXPRESSION__RESOURCE_EXPR, oldResourceExpr, newResourceExpr);
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
			case PPPackage.RESOURCE_EXPRESSION__RESOURCE_EXPR:
				return getResourceExpr();
			case PPPackage.RESOURCE_EXPRESSION__RESOURCE_DATA:
				return getResourceData();
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
			case PPPackage.RESOURCE_EXPRESSION__RESOURCE_EXPR:
				return basicSetResourceExpr(null, msgs);
			case PPPackage.RESOURCE_EXPRESSION__RESOURCE_DATA:
				return ((InternalEList<?>) getResourceData()).basicRemove(otherEnd, msgs);
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
			case PPPackage.RESOURCE_EXPRESSION__RESOURCE_EXPR:
				return resourceExpr != null;
			case PPPackage.RESOURCE_EXPRESSION__RESOURCE_DATA:
				return resourceData != null && !resourceData.isEmpty();
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
			case PPPackage.RESOURCE_EXPRESSION__RESOURCE_EXPR:
				setResourceExpr((Expression) newValue);
				return;
			case PPPackage.RESOURCE_EXPRESSION__RESOURCE_DATA:
				getResourceData().clear();
				getResourceData().addAll((Collection<? extends ResourceBody>) newValue);
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
		return PPPackage.Literals.RESOURCE_EXPRESSION;
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
			case PPPackage.RESOURCE_EXPRESSION__RESOURCE_EXPR:
				setResourceExpr((Expression) null);
				return;
			case PPPackage.RESOURCE_EXPRESSION__RESOURCE_DATA:
				getResourceData().clear();
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
	public EList<ResourceBody> getResourceData() {
		if(resourceData == null) {
			resourceData = new EObjectContainmentEList<ResourceBody>(
				ResourceBody.class, this, PPPackage.RESOURCE_EXPRESSION__RESOURCE_DATA);
		}
		return resourceData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression getResourceExpr() {
		return resourceExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setResourceExpr(Expression newResourceExpr) {
		if(newResourceExpr != resourceExpr) {
			NotificationChain msgs = null;
			if(resourceExpr != null)
				msgs = ((InternalEObject) resourceExpr).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.RESOURCE_EXPRESSION__RESOURCE_EXPR, null, msgs);
			if(newResourceExpr != null)
				msgs = ((InternalEObject) newResourceExpr).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.RESOURCE_EXPRESSION__RESOURCE_EXPR, null, msgs);
			msgs = basicSetResourceExpr(newResourceExpr, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.RESOURCE_EXPRESSION__RESOURCE_EXPR, newResourceExpr, newResourceExpr));
	}

} // ResourceExpressionImpl
