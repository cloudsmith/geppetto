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
package org.cloudsmith.geppetto.pp.pptp;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Puppet Target</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.cloudsmith.geppetto.pp.pptp.PuppetTarget#getEntries <em>Entries</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getPuppetTarget()
 * @model
 * @generated
 */
public interface PuppetTarget extends EObject {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.pp.pptp.TargetEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getPuppetTarget_Entries()
	 * @model containment="true"
	 * @generated
	 */
	EList<TargetEntry> getEntries();

} // PuppetTarget
