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
package org.cloudsmith.geppetto.pp;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Literal List</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.LiteralList#getElements <em>Elements</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.PPPackage#getLiteralList()
 * @model
 * @generated
 */
public interface LiteralList extends LiteralExpression {
	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.pp.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Elements</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.pp.PPPackage#getLiteralList_Elements()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getElements();

} // LiteralList
