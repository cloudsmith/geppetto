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
 * A representation of the model object '<em><b>Type Fragment</b></em>'.
 * <!-- end-user-doc -->
 * 
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.pp.pptp.TypeFragment#getMadeContributionTo <em>Made Contribution To</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getTypeFragment()
 * @model
 * @generated
 */
public interface TypeFragment extends AbstractType {
	/**
	 * Returns the value of the '<em><b>Made Contribution To</b></em>' reference list.
	 * The list contents are of type {@link org.cloudsmith.geppetto.pp.pptp.Type}.
	 * It is bidirectional and its opposite is '{@link org.cloudsmith.geppetto.pp.pptp.Type#getContributingFragments <em>Contributing Fragments</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Reference to a type the TypeFragment has made a contribution to (by copying its parameters and properties).
	 * It is possible that a fragment contributes to multiple types (in the case there are errors and there are two instances with the same name).
	 * <!-- end-model-doc -->
	 * 
	 * @return the value of the '<em>Made Contribution To</em>' reference list.
	 * @see org.cloudsmith.geppetto.pp.pptp.PPTPPackage#getTypeFragment_MadeContributionTo()
	 * @see org.cloudsmith.geppetto.pp.pptp.Type#getContributingFragments
	 * @model opposite="contributingFragments"
	 * @generated
	 */
	EList<Type> getMadeContributionTo();

} // TypeFragment
