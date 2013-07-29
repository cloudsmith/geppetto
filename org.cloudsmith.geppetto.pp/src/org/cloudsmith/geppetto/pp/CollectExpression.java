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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Collect Expression</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.CollectExpression#getClassReference <em>Class Reference</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.CollectExpression#getQuery <em>Query</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.pp.CollectExpression#getAttributes <em>Attributes</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage#getCollectExpression()
 * @model
 * @generated
 */
public interface CollectExpression extends Expression {
	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attributes</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Attributes</em>' containment reference.
	 * @see #setAttributes(AttributeOperations)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getCollectExpression_Attributes()
	 * @model containment="true"
	 * @generated
	 */
	AttributeOperations getAttributes();

	/**
	 * Returns the value of the '<em><b>Class Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Reference</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Class Reference</em>' containment reference.
	 * @see #setClassReference(Expression)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getCollectExpression_ClassReference()
	 * @model containment="true"
	 * @generated
	 */
	Expression getClassReference();

	/**
	 * Returns the value of the '<em><b>Query</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Query</em>' containment reference isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Query</em>' containment reference.
	 * @see #setQuery(ICollectQuery)
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getCollectExpression_Query()
	 * @model containment="true"
	 * @generated
	 */
	ICollectQuery getQuery();

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.CollectExpression#getAttributes <em>Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Attributes</em>' containment reference.
	 * @see #getAttributes()
	 * @generated
	 */
	void setAttributes(AttributeOperations value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.CollectExpression#getClassReference <em>Class Reference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Class Reference</em>' containment reference.
	 * @see #getClassReference()
	 * @generated
	 */
	void setClassReference(Expression value);

	/**
	 * Sets the value of the '{@link org.cloudsmith.geppetto.pp.CollectExpression#getQuery <em>Query</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Query</em>' containment reference.
	 * @see #getQuery()
	 * @generated
	 */
	void setQuery(ICollectQuery value);

} // CollectExpression
