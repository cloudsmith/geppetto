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
 * A representation of the model object '<em><b>Import Expression</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.puppetlabs.geppetto.pp.ImportExpression#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.puppetlabs.geppetto.pp.PPPackage#getImportExpression()
 * @model
 * @generated
 */
public interface ImportExpression extends Expression {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link com.puppetlabs.geppetto.pp.IQuotedString}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see com.puppetlabs.geppetto.pp.PPPackage#getImportExpression_Values()
	 * @model containment="true"
	 * @generated
	 */
	EList<IQuotedString> getValues();

} // ImportExpression
