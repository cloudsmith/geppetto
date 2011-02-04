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

import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.DefinitionArgumentList;
import org.cloudsmith.geppetto.pp.Expression;
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
 * An implementation of the model object '<em><b>Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.DefinitionImpl#getClassName <em>Class Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.DefinitionImpl#getArguments <em>Arguments</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.impl.DefinitionImpl#getStatements <em>Statements</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class DefinitionImpl extends ExpressionImpl implements Definition {
	/**
	 * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected String className = CLASS_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getArguments() <em>Arguments</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getArguments()
	 * @generated
	 * @ordered
	 */
	protected DefinitionArgumentList arguments;

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
	protected DefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetArguments(DefinitionArgumentList newArguments, NotificationChain msgs) {
		DefinitionArgumentList oldArguments = arguments;
		arguments = newArguments;
		if(eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(
				this, Notification.SET, PPPackage.DEFINITION__ARGUMENTS, oldArguments, newArguments);
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
			case PPPackage.DEFINITION__CLASS_NAME:
				return getClassName();
			case PPPackage.DEFINITION__ARGUMENTS:
				return getArguments();
			case PPPackage.DEFINITION__STATEMENTS:
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
			case PPPackage.DEFINITION__ARGUMENTS:
				return basicSetArguments(null, msgs);
			case PPPackage.DEFINITION__STATEMENTS:
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
			case PPPackage.DEFINITION__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null
						? className != null
						: !CLASS_NAME_EDEFAULT.equals(className);
			case PPPackage.DEFINITION__ARGUMENTS:
				return arguments != null;
			case PPPackage.DEFINITION__STATEMENTS:
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
			case PPPackage.DEFINITION__CLASS_NAME:
				setClassName((String) newValue);
				return;
			case PPPackage.DEFINITION__ARGUMENTS:
				setArguments((DefinitionArgumentList) newValue);
				return;
			case PPPackage.DEFINITION__STATEMENTS:
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
			case PPPackage.DEFINITION__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
				return;
			case PPPackage.DEFINITION__ARGUMENTS:
				setArguments((DefinitionArgumentList) null);
				return;
			case PPPackage.DEFINITION__STATEMENTS:
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
	public DefinitionArgumentList getArguments() {
		return arguments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getClassName() {
		return className;
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
				Expression.class, this, PPPackage.DEFINITION__STATEMENTS);
		}
		return statements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setArguments(DefinitionArgumentList newArguments) {
		if(newArguments != arguments) {
			NotificationChain msgs = null;
			if(arguments != null)
				msgs = ((InternalEObject) arguments).eInverseRemove(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.DEFINITION__ARGUMENTS, null, msgs);
			if(newArguments != null)
				msgs = ((InternalEObject) newArguments).eInverseAdd(this, EOPPOSITE_FEATURE_BASE -
						PPPackage.DEFINITION__ARGUMENTS, null, msgs);
			msgs = basicSetArguments(newArguments, msgs);
			if(msgs != null)
				msgs.dispatch();
		}
		else if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.DEFINITION__ARGUMENTS, newArguments, newArguments));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setClassName(String newClassName) {
		String oldClassName = className;
		className = newClassName;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, PPPackage.DEFINITION__CLASS_NAME, oldClassName, className));
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
		result.append(" (className: ");
		result.append(className);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PPPackage.Literals.DEFINITION;
	}

} // DefinitionImpl
