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
package org.cloudsmith.geppetto.pp;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node Definition</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.NodeDefinition#getHostNames <em>Host Names</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.NodeDefinition#getParentName <em>Parent Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.NodeDefinition#getStatements <em>Statements</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage#getNodeDefinition()
 * @model
 * @generated
 */
public interface NodeDefinition extends Expression {
	/**
	 * Returns the value of the '<em><b>Host Names</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.pp.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Host Names</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Host Names</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getNodeDefinition_HostNames()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getHostNames();

	/**
	 * Returns the value of the '<em><b>Parent Name</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Name</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Parent Name</em>' containment reference.
	 * @see #setParentName(Expression)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getNodeDefinition_ParentName()
	 * @model containment="true"
	 * @generated
	 */
	Expression getParentName();

	/**
	 * Returns the value of the '<em><b>Statements</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.pp.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Statements</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Statements</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getNodeDefinition_Statements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getStatements();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.NodeDefinition#getParentName <em>Parent Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Parent Name</em>' containment reference.
	 * @see #getParentName()
	 * @generated
	 */
	void setParentName(Expression value);

} // NodeDefinition
