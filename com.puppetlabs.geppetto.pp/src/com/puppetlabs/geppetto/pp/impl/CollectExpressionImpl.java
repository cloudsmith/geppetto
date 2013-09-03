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
package com.puppetlabs.geppetto.pp.impl;

import com.puppetlabs.geppetto.pp.AttributeOperations;
import com.puppetlabs.geppetto.pp.CollectExpression;
import com.puppetlabs.geppetto.pp.Expression;
import com.puppetlabs.geppetto.pp.ICollectQuery;
import com.puppetlabs.geppetto.pp.PPPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Collect Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link com.puppetlabs.geppetto.pp.impl.CollectExpressionImpl#getClassReference <em>Class Reference</em>}</li>
 * <li>{@link com.puppetlabs.geppetto.pp.impl.CollectExpressionImpl#getQuery <em>Query</em>}</li>
 * <li>{@link com.puppetlabs.geppetto.pp.impl.CollectExpressionImpl#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class CollectExpressionImpl extends ExpressionImpl implements CollectExpression {
	/**
	 * The cached value of the '{@link #getClassReference() <em>Class Reference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getClassReference()
	 * @generated
	 * @ordered
	 */
	protected Expression classReference;

	/**
	 * The cached value of the '{@link #getQuery() <em>Query</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getQuery()
	 * @generated
	 * @ordered
	 */
	protected ICollectQuery query;

	/**
	 * The cached value of the '{@link #getAttributes() <em>Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getAttributes()
	 * @generated
	 * @ordered
	 */
	protected AttributeOperations attributes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CollectExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetAttributes(AttributeOperations newAttributes, NotificationChain msgs) {
		AttributeOperations oldAttributes = attributes;
		attributes = newAttributes;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.COLLECT_EXPRESSION__ATTRIBUTES, oldAttributes, newAttributes);
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
	public NotificationChain basicSetClassReference(Expression newClassReference, NotificationChain msgs) {
		Expression oldClassReference = classReference;
		classReference = newClassReference;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.COLLECT_EXPRESSION__CLASS_REFERENCE, oldClassReference,
				newClassReference);
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
	public NotificationChain basicSetQuery(ICollectQuery newQuery, NotificationChain msgs) {
		ICollectQuery oldQuery = query;
		query = newQuery;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.COLLECT_EXPRESSION__QUERY, oldQuery, newQuery);
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
			case PPPackage.COLLECT_EXPRESSION__CLASS_REFERENCE:
				return getClassReference();
			case PPPackage.COLLECT_EXPRESSION__QUERY:
				return getQuery();
			case PPPackage.COLLECT_EXPRESSION__ATTRIBUTES:
				return getAttributes();
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
			case PPPackage.COLLECT_EXPRESSION__CLASS_REFERENCE:
				return basicSetClassReference(null, msgs);
			case PPPackage.COLLECT_EXPRESSION__QUERY:
				return basicSetQuery(null, msgs);
			case PPPackage.COLLECT_EXPRESSION__ATTRIBUTES:
				return basicSetAttributes(null, msgs);
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
			case PPPackage.COLLECT_EXPRESSION__CLASS_REFERENCE:
				return classReference != null;
			case PPPackage.COLLECT_EXPRESSION__QUERY:
				return query != null;
			case PPPackage.COLLECT_EXPRESSION__ATTRIBUTES:
				return attributes != null;
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
			case PPPackage.COLLECT_EXPRESSION__CLASS_REFERENCE:
				setClassReference((Expression) newValue);
				return;
			case PPPackage.COLLECT_EXPRESSION__QUERY:
				setQuery((ICollectQuery) newValue);
				return;
			case PPPackage.COLLECT_EXPRESSION__ATTRIBUTES:
				setAttributes((AttributeOperations) newValue);
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
		return PPPackage.Literals.COLLECT_EXPRESSION;
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
			case PPPackage.COLLECT_EXPRESSION__CLASS_REFERENCE:
				setClassReference((Expression) null);
				return;
			case PPPackage.COLLECT_EXPRESSION__QUERY:
				setQuery((ICollectQuery) null);
				return;
			case PPPackage.COLLECT_EXPRESSION__ATTRIBUTES:
				setAttributes((AttributeOperations) null);
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
	public AttributeOperations getAttributes() {
		return attributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression getClassReference() {
		return classReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ICollectQuery getQuery() {
		return query;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setAttributes(AttributeOperations newAttributes) {
		if(newAttributes != attributes) {
			NotificationChain msgs = null;
			if(attributes != null)
				msgs = ((InternalEObject) attributes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.COLLECT_EXPRESSION__ATTRIBUTES, null, msgs);
			if(newAttributes != null)
				msgs = ((InternalEObject) newAttributes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.COLLECT_EXPRESSION__ATTRIBUTES, null, msgs);
			msgs = basicSetAttributes(newAttributes, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.COLLECT_EXPRESSION__ATTRIBUTES, newAttributes, newAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setClassReference(Expression newClassReference) {
		if(newClassReference != classReference) {
			NotificationChain msgs = null;
			if(classReference != null)
				msgs = ((InternalEObject) classReference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.COLLECT_EXPRESSION__CLASS_REFERENCE, null, msgs);
			if(newClassReference != null)
				msgs = ((InternalEObject) newClassReference).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.COLLECT_EXPRESSION__CLASS_REFERENCE, null, msgs);
			msgs = basicSetClassReference(newClassReference, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.COLLECT_EXPRESSION__CLASS_REFERENCE, newClassReference,
				newClassReference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setQuery(ICollectQuery newQuery) {
		if(newQuery != query) {
			NotificationChain msgs = null;
			if(query != null)
				msgs = ((InternalEObject) query).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.COLLECT_EXPRESSION__QUERY, null, msgs);
			if(newQuery != null)
				msgs = ((InternalEObject) newQuery).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.COLLECT_EXPRESSION__QUERY, null, msgs);
			msgs = basicSetQuery(newQuery, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.COLLECT_EXPRESSION__QUERY, newQuery, newQuery));
	}

} // CollectExpressionImpl
