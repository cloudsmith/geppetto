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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Type</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.Type#getContributingFragments <em>Contributing Fragments</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getType()
 * @model
 * @generated
 */
public interface Type extends AbstractType {

	/**
	 * Returns the value of the '<em><b>Contributing Fragments</b></em>' reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.pp.pptp.TypeFragment}.
	 * It is bidirectional and its opposite is '{@link org.cloudsmith.geppetto.pp.pptp.TypeFragment#getMadeContributionTo
	 * <em>Made Contribution To</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contributing Fragments</em>' reference list isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Contributing Fragments</em>' reference list.
	 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getType_ContributingFragments()
	 * @see org.cloudsmith.geppetto.pp.pptp.TypeFragment#getMadeContributionTo
	 * @model opposite="madeContributionTo"
	 * @generated
	 */
	EList<TypeFragment> getContributingFragments();

} // Type
