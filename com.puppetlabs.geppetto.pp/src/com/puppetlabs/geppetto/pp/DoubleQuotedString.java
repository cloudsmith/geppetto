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
package com.puppetlabs.geppetto.pp;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Double Quoted String</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.puppetlabs.geppetto.pp.DoubleQuotedString#getStringPart <em>String Part</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.puppetlabs.geppetto.pp.PPPackage#getDoubleQuotedString()
 * @model
 * @generated
 */
public interface DoubleQuotedString extends StringExpression, IQuotedString {
	/**
	 * Returns the value of the '<em><b>String Part</b></em>' containment reference list.
	 * The list contents are of type {@link com.puppetlabs.geppetto.pp.TextExpression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>String Part</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>String Part</em>' containment reference list.
	 * @see com.puppetlabs.geppetto.pp.PPPackage#getDoubleQuotedString_StringPart()
	 * @model containment="true"
	 * @generated
	 */
	EList<TextExpression> getStringPart();

} // DoubleQuotedString
