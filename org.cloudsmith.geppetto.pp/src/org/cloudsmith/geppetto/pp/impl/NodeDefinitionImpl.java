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

import java.util.Collection;

import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.NodeDefinition;
import org.cloudsmith.geppetto.pp.PPPackage;
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
 * An implementation of the model object '<em><b>Node Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.NodeDefinitionImpl#getHostNames <em>Host Names</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.NodeDefinitionImpl#getParentName <em>Parent Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.NodeDefinitionImpl#getStatements <em>Statements</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class NodeDefinitionImpl extends ExpressionImpl implements NodeDefinition {
	/**
	 * The cached value of the '{@link #getHostNames() <em>Host Names</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getHostNames()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> hostNames;

	/**
	 * The cached value of the '{@link #getParentName() <em>Parent Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getParentName()
	 * @generated
	 * @ordered
	 */
	protected Expression parentName;

	/**
	 * The cached value of the '{@link #getStatements() <em>Statements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getStatements()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> statements;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected NodeDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetParentName(Expression newParentName, NotificationChain msgs) {
		Expression oldParentName = parentName;
		parentName = newParentName;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.NODE_DEFINITION__PARENT_NAME, oldParentName, newParentName);
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
			case PPPackage.NODE_DEFINITION__HOST_NAMES:
				return getHostNames();
			case PPPackage.NODE_DEFINITION__PARENT_NAME:
				return getParentName();
			case PPPackage.NODE_DEFINITION__STATEMENTS:
				return getStatements();
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
			case PPPackage.NODE_DEFINITION__HOST_NAMES:
				return ((InternalEList<?>) getHostNames()).basicRemove(otherEnd, msgs);
			case PPPackage.NODE_DEFINITION__PARENT_NAME:
				return basicSetParentName(null, msgs);
			case PPPackage.NODE_DEFINITION__STATEMENTS:
				return ((InternalEList<?>) getStatements()).basicRemove(otherEnd, msgs);
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
			case PPPackage.NODE_DEFINITION__HOST_NAMES:
				return hostNames != null && !hostNames.isEmpty();
			case PPPackage.NODE_DEFINITION__PARENT_NAME:
				return parentName != null;
			case PPPackage.NODE_DEFINITION__STATEMENTS:
				return statements != null && !statements.isEmpty();
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
			case PPPackage.NODE_DEFINITION__HOST_NAMES:
				getHostNames().clear();
				getHostNames().addAll((Collection<? extends Expression>) newValue);
				return;
			case PPPackage.NODE_DEFINITION__PARENT_NAME:
				setParentName((Expression) newValue);
				return;
			case PPPackage.NODE_DEFINITION__STATEMENTS:
				getStatements().clear();
				getStatements().addAll((Collection<? extends Expression>) newValue);
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
	public void eUnset(int featureID) {
		switch(featureID) {
			case PPPackage.NODE_DEFINITION__HOST_NAMES:
				getHostNames().clear();
				return;
			case PPPackage.NODE_DEFINITION__PARENT_NAME:
				setParentName((Expression) null);
				return;
			case PPPackage.NODE_DEFINITION__STATEMENTS:
				getStatements().clear();
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
	public EList<Expression> getHostNames() {
		if(hostNames == null) {
			hostNames = new EObjectContainmentEList<Expression>(
				Expression.class, this, PPPackage.NODE_DEFINITION__HOST_NAMES);
		}
		return hostNames;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Expression getParentName() {
		return parentName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Expression> getStatements() {
		if(statements == null) {
			statements = new EObjectContainmentEList<Expression>(
				Expression.class, this, PPPackage.NODE_DEFINITION__STATEMENTS);
		}
		return statements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setParentName(Expression newParentName) {
		if(newParentName != parentName) {
			NotificationChain msgs = null;
			if(parentName != null)
				msgs = ((InternalEObject) parentName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.NODE_DEFINITION__PARENT_NAME, null, msgs);
			if(newParentName != null)
				msgs = ((InternalEObject) newParentName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.NODE_DEFINITION__PARENT_NAME, null, msgs);
			msgs = basicSetParentName(newParentName, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.NODE_DEFINITION__PARENT_NAME, newParentName, newParentName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PPPackage.Literals.NODE_DEFINITION;
	}

} // NodeDefinitionImpl
