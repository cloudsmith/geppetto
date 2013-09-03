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
package com.puppetlabs.geppetto.pp.pptp;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ITarget Element Container</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link com.puppetlabs.geppetto.pp.pptp.ITargetElementContainer#getContents <em>Contents</em>}</li>
 * </ul>
 * </p>
 * 
 * @see com.puppetlabs.geppetto.pp.pptp.PPTPPackage#getITargetElementContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ITargetElementContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Contents</b></em>' containment reference list.
	 * The list contents are of type {@link com.puppetlabs.geppetto.pp.pptp.TargetElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contents</em>' containment reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Contents</em>' containment reference list.
	 * @see com.puppetlabs.geppetto.pp.pptp.PPTPPackage#getITargetElementContainer_Contents()
	 * @model containment="true"
	 * @generated
	 */
	EList<TargetElement> getContents();

} // ITargetElementContainer
